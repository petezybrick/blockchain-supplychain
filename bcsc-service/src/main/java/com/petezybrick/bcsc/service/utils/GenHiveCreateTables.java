package com.petezybrick.bcsc.service.utils;

import java.io.File;
import java.security.Security;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.orc.TypeDescription;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.hive.HiveDataSource;

public class GenHiveCreateTables {
	public static final String HIVE_DB_NAME = "db_bcsc";
	private Map<String,TypeDescription> mapOrcSchemas;
	private List<String> schemaStmts;
	private List<String> structStmts;
	private List<String> hiveCreateTables;
	private List<String> hiveDropTables;
	
	public static void main( String[] args ) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),
					System.getenv("KEYSPACE_NAME") );
			GenHiveCreateTables genHiveCreateTables = new GenHiveCreateTables();
			genHiveCreateTables.process( args );
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	
	public void process( String[] args) throws Exception {
		String pathSqlCreateTables = args[0];
		createHiveSchemas( pathSqlCreateTables );
	}

	
	private void createHiveSchemas( String pathSqlCreateTables ) throws Exception {
		mapOrcSchemas = new HashMap<String,TypeDescription>();
		schemaStmts = new ArrayList<String>();
		structStmts = new ArrayList<String>();
		hiveCreateTables = new ArrayList<String>();
		hiveDropTables = new ArrayList<String>();
		List<String> records = FileUtils.readLines(new File(pathSqlCreateTables));
		String version = "1.0";
		TypeDescription schema = null;
		boolean isCreateTable = false;
		StringBuilder structStmt = null;
		boolean isFirstColumn = false;
		String tableName = null;
		StringBuilder hiveCreateTable = null;
		
		for( String record : records ) {
			record = record.trim();
			if( record.startsWith("CREATE TABLE")) {
				isCreateTable = true;
				String[] tokens = record.split("[ ]");
				String tableNameVersion = tokens[2] + "|" + version;
				tableName = tokens[2];
				schemaStmts.add("mapOrcSchemas.put(\"" + tableNameVersion + "\", TypeDescription.createStruct()" );
				structStmt = new StringBuilder().append("mapOrcSchemas.put(\"").append(tableNameVersion)
						.append( "\", TypeDescription.fromString(\"struct<" ); 
				isFirstColumn = true;
				schema = TypeDescription.createStruct();
				mapOrcSchemas.put(tableNameVersion,  schema );
				hiveDropTables.add("DROP TABLE " + HIVE_DB_NAME + "." + tableName );
				hiveCreateTable = new StringBuilder().append("CREATE TABLE " + HIVE_DB_NAME + "." + tableName + " ( ");

			} else if(record.endsWith(";") ) {
				if( isCreateTable ) {
					schemaStmts.add("     );");
					schemaStmts.add("");
					hiveCreateTable.append(") STORED AS ORC LOCATION '/user/bcsc/bcsc_data/" + tableName + "'");
					hiveCreateTables.add(hiveCreateTable.toString());
					structStmt.append(">\"));");
					structStmts.add(structStmt.toString());
				}
				isCreateTable = false;

			} else if( isCreateTable ) {
				String[] tokens = record.split("[ ]");
				String columnName = tokens[0];
				if( "insert_ts".equals(columnName) || "update_ts".equals(columnName) ) continue;
				SchemaPair schemaPair = findTypeDescription( tokens[1].toUpperCase() );
				schema.addField( columnName, schemaPair.getTypeDescription() );
				schemaStmts.add("     .addField(\"" + columnName + "\", " + schemaPair.getStatement() + ")" );
				String delim = "";
				if( !isFirstColumn ) {
					structStmt.append(",");
					delim = ", ";
				}
				else isFirstColumn = false;
				structStmt.append(columnName).append(":").append(findStruct(tokens[1].toUpperCase()) );
				hiveCreateTable.append(delim + columnName + " " + findStruct(tokens[1].toUpperCase()));
			}
		}
		
		schemaStmts.forEach( ss -> System.out.println(ss.toString()));
		System.out.println();
		structStmts.forEach( ss -> System.out.println(ss.toString()));
		System.out.println();
		execSqlStmts( hiveDropTables );
		System.out.println();
		execSqlStmts( hiveCreateTables );

//		put("person|1.0", TypeDescription.createStruct()
//				.addField("person_id", TypeDescription.createInt())

	}
	
	private void execSqlStmts( List<String> sqlStmts ) throws Exception {
		try (	Connection con = HiveDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement(); ) {
			for( String sqlStmt : sqlStmts ) {
				System.out.println("Executing: " + sqlStmt );
				//stmt.execute(sqlStmt);
			}
		}

	}
	
	private SchemaPair findTypeDescription( String dataType ) throws Exception {
		TypeDescription child = null;
		if( "INTEGER".equals(dataType)) return new SchemaPair( "TypeDescription.createInt()", TypeDescription.createInt() );
		else if( dataType.startsWith("CHAR") || dataType.startsWith("VARCHAR") ) 
			return new SchemaPair( "TypeDescription.createString()", TypeDescription.createString() );
		else if( "DATE".equals(dataType)) return new SchemaPair( "TypeDescription.createDate()", TypeDescription.createDate() );
		else if( dataType.startsWith("DATEIME") || dataType.startsWith("TIMESTAMP") ) 
			return new SchemaPair( "TypeDescription.createTimestamp()", TypeDescription.createTimestamp() );
		else if( dataType.startsWith("BINARY") || dataType.startsWith("VARBINARY") ) 
			return new SchemaPair( "TypeDescription.createBinary()", TypeDescription.createBinary() );
		else if( dataType.equals("BOOL") || dataType.equals("BOOLEAN") ) 
			return new SchemaPair( "TypeDescription.createBoolean()", TypeDescription.createBoolean() );
		else if( dataType.equals("DOUBLE") ) 
			return new SchemaPair( "TypeDescription.createDouble()", TypeDescription.createDouble() );
		else if( dataType.equals("FLOAT") ) 
			return new SchemaPair( "TypeDescription.createFloat()", TypeDescription.createFloat() );
		else if( dataType.equals("DECIMAL") ) 
			return new SchemaPair( "TypeDescription.createDecimal()", TypeDescription.createDecimal() );
		else throw new Exception("Unsupported data type " + dataType );
	}
	
	private String findStruct( String dataType ) throws Exception {
		if( "INTEGER".equals(dataType)) return "INT";
		else if( "BIGINT".equals(dataType) ) return "BIGINT";
		else if( dataType.startsWith("CHAR") || dataType.startsWith("VARCHAR") ) return "STRING";
		else if( "DATE".equals(dataType)) return "DATE";
		else if( dataType.startsWith("DATEIME") || dataType.startsWith("TIMESTAMP") ) return "TIMESTAMP";
		else if( dataType.startsWith("BINARY") || dataType.startsWith("VARBINARY") ) return "BINARY";
		else if( dataType.equals("BOOL") || dataType.equals("BOOLEAN") ) return "BOOLEAN";
		else if( dataType.equals("DOUBLE") ) return "DOUBLE";
		else if( dataType.equals("FLOAT") ) return "FLOAT";
		else if( dataType.equals("DECIMAL") ) return "DECIMAL";
		else throw new Exception("Unsupported data type " + dataType );
	}
	
	private static class SchemaPair {
		private String statement;
		private TypeDescription typeDescription;
		
		public SchemaPair(String statement, TypeDescription typeDescription) {
			super();
			this.statement = statement;
			this.typeDescription = typeDescription;
		}
		
		public String getStatement() {
			return statement;
		}
		public TypeDescription getTypeDescription() {
			return typeDescription;
		}
		public SchemaPair setStatement(String statement) {
			this.statement = statement;
			return this;
		}
		public SchemaPair setTypeDescription(TypeDescription typeDescription) {
			this.typeDescription = typeDescription;
			return this;
		}
		
	}
}
