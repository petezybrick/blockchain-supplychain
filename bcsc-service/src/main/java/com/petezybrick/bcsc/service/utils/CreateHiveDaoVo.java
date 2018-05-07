/**
 *    Copyright 2016, 2017 Peter Zybrick and others.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * 
 * @author  Pete Zybrick
 * @version 1.0.0, 2017-09
 * 
 */
package com.petezybrick.bcsc.service.utils;

import java.io.File;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.orc.TypeDescription;
import org.apache.orc.TypeDescription.Category;

import com.petezybrick.bcsc.service.orc.BaseOrcVo;
import com.petezybrick.bcsc.service.orc.OrcCommon;
import com.petezybrick.bcsc.service.orc.OrcSchemaMgr;


/**
 * The Class CreateBasicDaoVo.
 */
public class CreateHiveDaoVo {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CreateHiveDaoVo.class);
	private String targetFolder;
	private String packageName;
	
	/**
 * The main method.
 *
 * @param args the arguments
 */
public static void main(String[] args) {
		try {
			CreateHiveDaoVo generateVos = new CreateHiveDaoVo( args[0], args[1] );
			generateVos.process();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


	public CreateHiveDaoVo( String targetFolder, String packageName ) throws Exception {
		this.targetFolder = targetFolder;
		this.packageName = packageName;
	}

	
	/**
	 * Process.
	 *
	 * @throws Exception the exception
	 */
	public void process() throws Exception {
		Map<String, List<ColumnItem>> columnItemsByTableName = new HashMap<String, List<ColumnItem>>();
		Set<String> tableNameVersions = OrcSchemaMgr.mapOrcSchemas.keySet();
		try {
			// get the column info for each table
			for (String tableNameVersion : tableNameVersions) {
				System.out.println("processing " + tableNameVersion );
				
				List<ColumnItem> columnItems = new ArrayList<ColumnItem>();
				columnItemsByTableName.put(tableNameVersion, columnItems);
				TypeDescription schema = OrcSchemaMgr.mapOrcSchemas.get(tableNameVersion);

				for( int i=0 ; i<schema.getFieldNames().size() ; i++ ) {
					String columnName = schema.getFieldNames().get(i);
					Category category = schema.getChildren().get(i).getCategory();
					int decimalDigits = 0;	// TODO
					
					HiveTypeItem hiveTypeItem = findHiveType( category, decimalDigits );
					ColumnItem columnItem = new ColumnItem()
							.setName(columnName)
							.setDecimalDigits(decimalDigits)
							.setJavaType(hiveTypeItem.getDataType())
							.setJdbcGet(hiveTypeItem.getGetter())
							.setJdbcSet(hiveTypeItem.getSetter())
							;
					columnItems.add(columnItem);
				}
			}

		} catch (Exception e) {
			throw e;
		} finally {

		}

		// create VO
		for( Map.Entry<String, List<ColumnItem>> entry : columnItemsByTableName.entrySet() ) {
			List<String> voClassRows = new ArrayList<String>();
			String[] tokens = entry.getKey().split("[|]");
			String tableName = tokens[0]; 
			String tableVersion = tokens[1]; 

			String className = tableNameToClass(tableName) + "Vo";
			voClassRows.add("package " + packageName + ";\n");
			voClassRows.add("");
			voClassRows.add("import java.nio.ByteBuffer;");
			voClassRows.add("import java.sql.ResultSet;");
			voClassRows.add("import java.sql.SQLException;");
			voClassRows.add("import java.sql.Timestamp;");
			voClassRows.add("import org.apache.logging.log4j.LogManager;");
			voClassRows.add("import org.apache.logging.log4j.Logger;");
			voClassRows.add("");
			voClassRows.add("");
			voClassRows.add("public class " + className + " extends BaseOrcVo {");
			voClassRows.add("\tprivate static final Logger logger = LogManager.getLogger(" + className + ".class);");
			for( ColumnItem columnItem : entry.getValue() ) {
				String javaName = columnNameToAttribute( columnItem.getName() );
				voClassRows.add("\tprivate " + columnItem.getJavaType() + " " + javaName + ";");
			}
			// Constructors
			voClassRows.add("\n\n\tpublic " + className + "() {" );
			voClassRows.add("\t}" );
			voClassRows.add("\n\n\tpublic " + className + "(ResultSet rs) throws SQLException {" );
			for( ColumnItem columnItem : entry.getValue() ) {
				String javaName = columnNameToAttribute( columnItem.getName() );
				voClassRows.add("\t\tthis." + javaName + " = rs." + columnItem.getJdbcGet() + columnItem.getName() + "\");" );
			}
			voClassRows.add("\t}" );

			///////////////////////////////////////////////////
			voClassRows.add("\t@Override");
			voClassRows.add("\tpublic PersonOrcVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {");
			voClassRows.add("\t\tif( \"1.0\".equals(schemaVersion ) ) {");
			voClassRows.add("\t\t\treturn new PersonOrcVo()");
			voClassRows.add("\t\t\t\t.setPersonId((Integer)objs.get(0))");
			voClassRows.add("\t\t\t\t.setLastName( (String)objs.get(1) )");
			voClassRows.add("\t\t\t\t.setFirstName( (String)objs.get(2) )");
			voClassRows.add("\t\t\t\t;");
			voClassRows.add("\t\t} else throw new Exception(\"Invalid schema version \"" + tableVersion + ");" );
			voClassRows.add("\t}");

			
//			// TODO: some automated way to ensure the same order as the schema
//			public List<Object> toObjectList() throws Exception {
//				List<Object> objs = new ArrayList<Object>();
//				objs.add(personId);
//				objs.add(lastName);
//				objs.add(firstName);
//				return objs;
//			}
//			
//			
//			public void fromObjectList( List<Object> objs ) throws Exception {
//				this.personId = (Integer)objs.get(0);
//				this.lastName = (String)objs.get(1);
//				this.firstName = (String)objs.get(2);
//				this.gender = (String)objs.get(3);
//			}			
//			
			
			
			
			
			//////////////////////////////////////////////////////
			
			
			// Getters
			voClassRows.add("\n" );
			for( ColumnItem columnItem : entry.getValue() ) {
				String javaName = columnNameToAttribute( columnItem.getName() );
				String getter = "get" + javaName.substring(0, 1).toUpperCase() + javaName.substring(1);
				voClassRows.add("\tpublic " + columnItem.getJavaType() + " " + getter + "() {");
				voClassRows.add("\t\treturn this." + javaName + ";");
				voClassRows.add("\t}");
			}
			// Setters
			voClassRows.add("\n" );
			for( ColumnItem columnItem : entry.getValue() ) {
				String javaName = columnNameToAttribute( columnItem.getName() );
				String setter = "set" + javaName.substring(0, 1).toUpperCase() + javaName.substring(1);
				voClassRows.add("\tpublic " + className + " " + setter + "( " + columnItem.getJavaType() + " " + javaName + " ) {");
				voClassRows.add("\t\tthis." + javaName + " = " + javaName  + ";");
				voClassRows.add("\t\treturn this;");
				voClassRows.add("\t}");
			}
				
			voClassRows.add( "}" );
			
			// Empty builder
			voClassRows.add( "" );
			String instanceName = className.substring(0,1).toLowerCase() + className.substring(1);
			voClassRows.add( "// " + className + " " + instanceName + " = new " + className + "()");
			for( ColumnItem columnItem : entry.getValue() ) {
				String javaName = columnNameToAttribute( columnItem.getName() );
				voClassRows.add( "//\t .set" + javaName.substring(0, 1).toUpperCase() + javaName.substring(1) + "(\"xxx\")" );
			}
			voClassRows.add( "//\t ;" );

			
			File output = new File(this.targetFolder + className + ".java");
			output.delete();
			FileUtils.writeLines(output, voClassRows);
			System.out.println("===================================================");
			for( String voClassRow : voClassRows ) {
				System.out.println(voClassRow);
			}
		}

		// create DAO
		for( Map.Entry<String, List<ColumnItem>> entry : columnItemsByTableName.entrySet() ) {
			List<String> daoClassRows = new ArrayList<String>();
			String[] tokens = entry.getKey().split("[|]");
			String tableName = tokens[0]; 
			String tableVersion = tokens[1]; 
			String className = tableNameToClass(tableName) + "Dao";
			daoClassRows.add("package " + packageName + ";");
			daoClassRows.add("");
			daoClassRows.add("import java.sql.ResultSet;");
			daoClassRows.add("import java.sql.Connection;");
			daoClassRows.add("import java.sql.PreparedStatement;");
			daoClassRows.add("import java.util.List;");
			daoClassRows.add("import org.apache.logging.log4j.LogManager;");
			daoClassRows.add("import org.apache.logging.log4j.Logger;");	
			daoClassRows.add("import com.petezybrick.bcsc.service.database.PooledDataSource;");
			daoClassRows.add("import com.petezybrick.bcsc.service.orc.OrcCommon;" );

			daoClassRows.add("");
			daoClassRows.add("");
			daoClassRows.add("public class " + className + " {");
			daoClassRows.add("\tprivate static final Logger logger = LogManager.getLogger(" + className + ".class);");
			daoClassRows.add("\tprivate static final String schemaName = \"" + tableName + "\";" );
			daoClassRows.add("\tprivate static final String schemaVersion = \"" + tableVersion + "\";" );

			boolean isFirst = false;
			StringBuilder sbFind = new StringBuilder().append("\tprivate static String sqlFindTemplate = \"SELECT ");
			isFirst = true;
			for( ColumnItem columnItem : entry.getValue() ) {
				if( !isFirst ) {
					sbFind.append(",");
				} else isFirst = false;
				sbFind.append(columnItem.getName());
			}
			sbFind.append(" FROM ").append(tableName).append(" WHERE <Criteria Here>");
			sbFind.append("\";");			
			daoClassRows.add(sbFind.toString());
			daoClassRows.add("");
			
			String voName = tableNameToClass(tableName) + "Vo";
			String instanceName = voName.substring(0,1).toLowerCase() + voName.substring(1);
			
			daoClassRows.add("");
			daoClassRows.add("");
			daoClassRows.add("\tpublic static void writeOrc( String pathNameExt, List<List<Object>> rowsCols ) throws Exception {" );
			daoClassRows.add("\t\tOrcCommon.write( pathNameExt, schemaName, schemaVersion, rowsCols );" );
			daoClassRows.add("\t}" );
			
			daoClassRows.add("\tpublic static " + voName + " findByTemplate( " + voName + " " + instanceName + " ) throws Exception {");
			daoClassRows.add("\t\ttry( Connection con = PooledDataSource.getInstance().getConnection();" );
			daoClassRows.add("\t\t     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {" );
			daoClassRows.add("\t\t\tcon.setAutoCommit(true);");		
			daoClassRows.add("\t\t\tint offset = 1;");
			daoClassRows.add("\t\t\t// pstmt.setString(offset++, " + instanceName + ".getAttribute( );");
			daoClassRows.add("\t\t\tResultSet rs = pstmt.executeQuery();");
			daoClassRows.add("\t\t\tif( rs.next() ) return new " + voName + "(rs);");
			daoClassRows.add("\t\t\telse return null;");
			daoClassRows.add("\t\t} catch (Exception e) {");
			daoClassRows.add("\t\t\tlogger.error(e.getMessage(), e);");
			daoClassRows.add("\t\t\tthrow e;");
			daoClassRows.add("\t\t}");
			daoClassRows.add("\t}");		
			daoClassRows.add("}" );
			File output = new File(this.targetFolder + className + ".java");
			output.delete();
			FileUtils.writeLines(output, daoClassRows);
			System.out.println("===================================================");
			for( String voClassRow : daoClassRows ) {
				System.out.println(voClassRow);
			}
		}
	}

	
	/**
	 * Table name to class.
	 *
	 * @param tableName the table name
	 * @return the string
	 */
	public static String tableNameToClass( String tableName ) {
		return commonTableAttr( tableName, 0 );
	}
	
	/**
	 * Column name to attribute.
	 *
	 * @param columnName the column name
	 * @return the string
	 */
	public static String columnNameToAttribute( String columnName ) {
		return commonTableAttr( columnName, 1 );
	}
	
	/**
	 * Common table attr.
	 *
	 * @param name the name
	 * @param startOffset the start offset
	 * @return the string
	 */
	public static String commonTableAttr( String name, int startOffset ) {
		name = name.toLowerCase();
		StringBuilder targetName = new StringBuilder();
		String to;
		boolean isNextUpper = true;
		if( startOffset == 1 ) {
			targetName.append(name.substring(0,1));
			isNextUpper = false;
		}
		for( int i=startOffset ; i<name.length() ; i++ ) {
			if( isNextUpper ) {
				targetName.append(name.substring(i,i+1).toUpperCase());
				isNextUpper = false;
			} else {
				if( name.substring(i,i+1).equals("_")) isNextUpper = true;
				else targetName.append(name.substring(i,i+1));
			}  
		}
		
		return targetName.toString();
	}
	
	
	/**
	 * Find jdbc types.
	 *
	 * @param sqlType the sql type
	 * @param columnSize the column size
	 * @param decimalDigits the decimal digits
	 * @return the jdbc type item
	 */
	public static HiveTypeItem findHiveType( Category category, int decimalDigits ) throws Exception {
		switch (category) {
		case BINARY:
			return new HiveTypeItem().setDataType("ByteBuffer").setGetter("getBytes(\"").setSetter("setBytes(");
		case BOOLEAN:
			return new HiveTypeItem().setDataType("Boolean").setGetter("getBoolean(\"").setSetter("setBoolean(");
		case DATE:
			return new HiveTypeItem().setDataType("Date").setGetter("getDate(\"").setSetter("setDate(");
		case DOUBLE:
			return new HiveTypeItem().setDataType("Double").setGetter("getDouble(\"").setSetter("setDouble(");
		case FLOAT:
			return new HiveTypeItem().setDataType("Float").setGetter("getFloat(\"").setSetter("setFloat(");
		case INT:
			return new HiveTypeItem().setDataType("Integer").setGetter("getInt(\"").setSetter("setInteger(");
		case SHORT:
			return new HiveTypeItem().setDataType("Integer").setGetter("getInt(\"").setSetter("setInteger(");
		case LONG:
			return new HiveTypeItem().setDataType("Long").setGetter("getLong(\"").setSetter("setLong(");
		case TIMESTAMP:
			return new HiveTypeItem().setDataType("Timestamp").setGetter("getTimestamp(\"").setSetter("setTimestamp(");
		case STRING:
		case CHAR:
		case VARCHAR:
			return new HiveTypeItem().setDataType("String").setGetter("getString(\"").setSetter("setString(");
		case DECIMAL: {
			return new HiveTypeItem().setDataType("BigDecimal").setGetter("getBigDecimal(\"").setSetter("setBigDecimal(");
		}
		// Not supported for this application
		case BYTE:
		case LIST:
		case MAP:
		case UNION:
		case STRUCT:
			throw new Exception("Unsupported category for this application: " + category);
		default:
			throw new IllegalArgumentException("Unknown type " + category);
		}
	}
	
	
	/**
	 * The Class JdbcTypeItem.
	 */
	public static class HiveTypeItem {
		
		/** The data type. */
		private String dataType;
		
		/** The getter. */
		private String getter;
		
		/** The setter. */
		private String setter;
		
		/**
		 * Gets the data type.
		 *
		 * @return the data type
		 */
		public String getDataType() {
			return dataType;
		}
		
		/**
		 * Gets the getter.
		 *
		 * @return the getter
		 */
		public String getGetter() {
			return getter;
		}
		
		/**
		 * Gets the setter.
		 *
		 * @return the setter
		 */
		public String getSetter() {
			return setter;
		}
		
		/**
		 * Sets the data type.
		 *
		 * @param dataType the data type
		 * @return the jdbc type item
		 */
		public HiveTypeItem setDataType(String dataType) {
			this.dataType = dataType;
			return this;
		}
		
		/**
		 * Sets the getter.
		 *
		 * @param getter the getter
		 * @return the jdbc type item
		 */
		public HiveTypeItem setGetter(String getter) {
			this.getter = getter;
			return this;
		}
		
		/**
		 * Sets the setter.
		 *
		 * @param setter the setter
		 * @return the jdbc type item
		 */
		public HiveTypeItem setSetter(String setter) {
			this.setter = setter;
			return this;
		}
		
	}

	/**
	 * The Class ColumnItem.
	 */
	public static class ColumnItem {
		
		/** The name. */
		private String name;
		
		/** The type. */
		private int type;
		
		/** The size. */
		private long size;
		
		/** The decimal digits. */
		private int decimalDigits;
		
		/** The java type. */
		private String javaType;
		
		/** The jdbc get. */
		private String jdbcGet;
		
		/** The jdbc set. */
		private String jdbcSet;
		
		/** The pk. */
		private boolean pk;

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public int getType() {
			return type;
		}

		/**
		 * Gets the size.
		 *
		 * @return the size
		 */
		public long getSize() {
			return size;
		}

		/**
		 * Gets the decimal digits.
		 *
		 * @return the decimal digits
		 */
		public int getDecimalDigits() {
			return decimalDigits;
		}

		/**
		 * Sets the name.
		 *
		 * @param name the name
		 * @return the column item
		 */
		public ColumnItem setName(String name) {
			this.name = name;
			return this;
		}

		/**
		 * Sets the type.
		 *
		 * @param type the type
		 * @return the column item
		 */
		public ColumnItem setType(int type) {
			this.type = type;
			return this;
		}

		/**
		 * Sets the size.
		 *
		 * @param size the size
		 * @return the column item
		 */
		public ColumnItem setSize(long size) {
			this.size = size;
			return this;
		}

		/**
		 * Sets the decimal digits.
		 *
		 * @param decimalDigits the decimal digits
		 * @return the column item
		 */
		public ColumnItem setDecimalDigits(int decimalDigits) {
			this.decimalDigits = decimalDigits;
			return this;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ColumnItem [name=" + name + ", type=" + type + ", size=" + size + ", decimalDigits=" + decimalDigits
					+ ", javaType=" + javaType + ", jdbcGet=" + jdbcGet + ", jdbcSet=" + jdbcSet + "]";
		}

		/**
		 * Gets the java type.
		 *
		 * @return the java type
		 */
		public String getJavaType() {
			return javaType;
		}

		/**
		 * Gets the jdbc get.
		 *
		 * @return the jdbc get
		 */
		public String getJdbcGet() {
			return jdbcGet;
		}

		/**
		 * Sets the java type.
		 *
		 * @param javaType the java type
		 * @return the column item
		 */
		public ColumnItem setJavaType(String javaType) {
			this.javaType = javaType;
			return this;
		}

		/**
		 * Sets the jdbc get.
		 *
		 * @param jdbcGet the jdbc get
		 * @return the column item
		 */
		public ColumnItem setJdbcGet(String jdbcGet) {
			this.jdbcGet = jdbcGet;
			return this;
		}

		/**
		 * Gets the jdbc set.
		 *
		 * @return the jdbc set
		 */
		public String getJdbcSet() {
			return jdbcSet;
		}

		/**
		 * Sets the jdbc set.
		 *
		 * @param jdbcSet the jdbc set
		 * @return the column item
		 */
		public ColumnItem setJdbcSet(String jdbcSet) {
			this.jdbcSet = jdbcSet;
			return this;
		}

		/**
		 * Checks if is pk.
		 *
		 * @return true, if is pk
		 */
		public boolean isPk() {
			return pk;
		}

		/**
		 * Sets the pk.
		 *
		 * @param pk the pk
		 * @return the column item
		 */
		public ColumnItem setPk(boolean pk) {
			this.pk = pk;
			return this;
		}

	}

}
