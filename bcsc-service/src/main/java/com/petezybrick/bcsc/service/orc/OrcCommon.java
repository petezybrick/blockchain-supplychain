package com.petezybrick.bcsc.service.orc;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.common.type.HiveDecimal;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.yarn.webapp.GenericExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import org.apache.orc.TypeDescription;
import org.apache.orc.TypeDescription.Category;
import org.apache.orc.Writer;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.common.utils.BlockchainUtils;
import com.petezybrick.bcsc.service.hive.HivePooledDataSource;

public class OrcCommon {
	private static final Logger logger = LogManager.getLogger(OrcCommon.class);

	
	public static List<BaseOrcVo> read(String targetPath, String targetNameExt, BaseOrcVo baseOrcVo) throws Exception {
		Configuration conf = new Configuration();
		Path readPath = null;
		
		if( targetPath.startsWith("hdfs:")) {
			readPath = initHdfs( conf, targetPath, targetNameExt );
		} else if( targetPath.startsWith("s3") ) {
			
		} else {
			readPath = new Path(targetPath + "/" + targetNameExt);
		}
		
		List<BaseOrcVo> orcVos = new ArrayList<BaseOrcVo>();
		Reader reader = OrcFile.createReader( readPath, OrcFile.readerOptions(conf));

		String schemaName = BaseOrcVo.roByteBufferToString(reader.getMetadataValue(OrcSchemaMgr.METADATA_SCHEMA_NAME_KEY));
		String schemaVersion = BaseOrcVo.roByteBufferToString(reader.getMetadataValue(OrcSchemaMgr.METADATA_SCHEMA_VERSION_KEY));
		RecordReader recordReader = reader.rows();
		VectorizedRowBatch batch = reader.getSchema().createRowBatch();
		OrcSchemaMgr.validateSchema(schemaName, schemaVersion, reader.getSchema());
		
		List<ColumnVector> colVectors = new ArrayList<ColumnVector>();
		for (int i = 0; i < reader.getSchema().getFieldNames().size(); i++) {
			colVectors.add(batch.cols[i]);
		}

		while (recordReader.nextBatch(batch)) {
			for (int rowOffset = 0; rowOffset < batch.size; rowOffset++) {
				List<Object> colValues = new ArrayList<Object>();
				for (int colOffset = 0; colOffset < reader.getSchema().getFieldNames().size(); colOffset++) {
					Category category = reader.getSchema().getChildren().get(colOffset).getCategory();
					addColumnVectorByCategory( category, colValues, colVectors, rowOffset, colOffset);
				}
				BaseOrcVo newOrcVo = baseOrcVo.createInstance(colValues, schemaVersion);
				orcVos.add(newOrcVo);
			}
		}
		recordReader.close();
		return orcVos;
	}

	
	public static void write( String targetPath, String targetNameExt, String schemaName, String schemaVersion, List<List<Object>> rowsCols ) throws Exception {
		Configuration conf = new Configuration();
		Path path = null;
		
		if( targetPath.startsWith("hdfs")) {
			path = initHdfs( conf, targetPath, targetNameExt );
			String hdfsUri = SupplyBlockchainConfig.getInstance().getHdfsUri() ;
			FileSystem fs = FileSystem.get(URI.create(hdfsUri), conf);
			String hdfsPath = targetPath.substring(6);
			Path newFolderPath = new Path(hdfsUri + hdfsPath);
			if (!fs.exists(newFolderPath)) fs.mkdirs(newFolderPath);
			if( fs.exists( path ) ) fs.delete(path, false);
		} else if( targetPath.startsWith("s3")) {
			
		} else {
			FileUtils.forceMkdir( new File(targetPath));
			new File(targetPath + "/" + targetNameExt).delete();
			path = new Path(targetPath + "/" + targetNameExt);
		}
		
		
		TypeDescription schema = OrcSchemaMgr.mapOrcSchemas.get( schemaName + "|" + schemaVersion );		
		List<ColumnVector> colVectors = new ArrayList<ColumnVector>();
		VectorizedRowBatch batch = schema.createRowBatch();
		for( int i=0 ; i<schema.getFieldNames().size() ; i++ ) {
			colVectors.add( batch.cols[i]);
		}
		Writer writer = OrcFile.createWriter( path,
				OrcFile.writerOptions(conf)
						.setSchema(schema));
		writer.addUserMetadata(OrcSchemaMgr.METADATA_SCHEMA_NAME_KEY, ByteBuffer.wrap( schemaName.getBytes() ));		
		writer.addUserMetadata(OrcSchemaMgr.METADATA_SCHEMA_VERSION_KEY, ByteBuffer.wrap( schemaVersion.getBytes() ));		

		for( List<Object> colValues : rowsCols ) {
			int rowOffset = batch.size++;
			for( int colOffset=0 ; colOffset<schema.getFieldNames().size() ; colOffset++ ) {
				ColumnVector colVector = colVectors.get(colOffset);
				Category category = schema.getChildren().get(colOffset).getCategory();
				writeColumnVectorByCategory( category, colValues, colVector, rowOffset, colOffset);
			}
			if (batch.size == batch.getMaxSize()) {
				writer.addRowBatch(batch);
				batch.reset();
			}
		}
		
		if (batch.size != 0) {
			writer.addRowBatch(batch);
			batch.reset();
		}
		writer.close();
	}
	
	
	private static void addColumnVectorByCategory(Category category, List<Object> colValues, List<ColumnVector> colVectors,
			int rowOffset, int colOffset) throws Exception {
		switch (category) {
		case BINARY:
			BytesColumnVector bcb = (BytesColumnVector) colVectors.get(colOffset);
			colValues.add( ByteBuffer.wrap( bcb.vector[rowOffset],bcb.start[rowOffset], bcb.length[rowOffset] ) );
			break;
		case BOOLEAN:
			LongColumnVector lcvb = (LongColumnVector) colVectors.get(colOffset);
			colValues.add( new Boolean( lcvb.vector[rowOffset] == 1 ? true : false ) );
			break;
		case DATE:
			LongColumnVector lcvDate = (LongColumnVector)colVectors.get(colOffset);
			colValues.add( LocalDate.ofEpochDay( lcvDate.vector[rowOffset] ) );		
			break;
		case DOUBLE:
			DoubleColumnVector dcvd = (DoubleColumnVector) colVectors.get(colOffset);
			colValues.add((Double) dcvd.vector[rowOffset]);
			break;
		case FLOAT:
			DoubleColumnVector dcvf= (DoubleColumnVector) colVectors.get(colOffset);
			colValues.add( new Float( dcvf.vector[rowOffset] ) );
			break;
		case INT:
		case SHORT:
			LongColumnVector lcvInt = (LongColumnVector) colVectors.get(colOffset);
			colValues.add((int) lcvInt.vector[rowOffset]);
			break;
		case LONG:
			LongColumnVector lcvLong = (LongColumnVector) colVectors.get(colOffset);
			colValues.add(lcvLong.vector[rowOffset]);
			break;
		case TIMESTAMP:
			TimestampColumnVector tcv = (TimestampColumnVector) colVectors.get(colOffset);
			colValues.add( new Timestamp(tcv.getTime(rowOffset)) );
			break;
		case STRING:
		case CHAR:
		case VARCHAR:
			BytesColumnVector bcvs = (BytesColumnVector) colVectors.get(colOffset);
			colValues.add(new String(bcvs.vector[rowOffset], bcvs.start[rowOffset], bcvs.length[rowOffset]));
			break;
		case DECIMAL: {
			DecimalColumnVector dcv = (DecimalColumnVector) colVectors.get(colOffset);
			HiveDecimalWritable wdw = dcv.vector[rowOffset];
			colValues.add(  wdw.getHiveDecimal().bigDecimalValue() );		
			break;
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
	

	private static void writeColumnVectorByCategory(Category category, List<Object> colValues, ColumnVector colVector,
			int rowOffset, int colOffset) throws Exception {
		switch (category) {
		case BINARY:
			BytesColumnVector bcvb = (BytesColumnVector)colVector;
			bcvb.setVal(rowOffset, ((ByteBuffer)colValues.get(colOffset)).array());
			break;
		case BOOLEAN:
			LongColumnVector lcvb = (LongColumnVector)colVector;
			lcvb.vector[rowOffset] = ( (Boolean)colValues.get(colOffset) ? 1 : 0);
			break;
		case DATE:
			LocalDate lclDate = (LocalDate)colValues.get(colOffset);
			LongColumnVector lcvd = (LongColumnVector)colVector;
			lcvd.vector[rowOffset] = lclDate.toEpochDay();
			break;
		case DOUBLE:
			DoubleColumnVector dcvd = (DoubleColumnVector)colVector;
			dcvd.vector[rowOffset] = (Double)colValues.get(colOffset);
			break;
		case FLOAT:
			DoubleColumnVector dcvf = (DoubleColumnVector)colVector;
			dcvf.vector[rowOffset] = (Float)colValues.get(colOffset);
			break;
		case INT:
		case SHORT:
			LongColumnVector lcvi = (LongColumnVector)colVector;
			lcvi.vector[rowOffset] = (Integer)colValues.get(colOffset);
			break;
		case LONG:
			LongColumnVector lcvl = (LongColumnVector)colVector;
			lcvl.vector[rowOffset] = (Long)colValues.get(colOffset);
			break;
		case TIMESTAMP:
			TimestampColumnVector tcv = (TimestampColumnVector)colVector;
			tcv.set(rowOffset, (Timestamp)colValues.get(colOffset));
			break;
		case STRING:
		case CHAR:
		case VARCHAR:
			BytesColumnVector bcvs = (BytesColumnVector)colVector;
			bcvs.setVal(rowOffset, ((String)colValues.get(colOffset)).getBytes());
			break;
		case DECIMAL: {
			DecimalColumnVector dcv = (DecimalColumnVector)colVector;
			HiveDecimal hd = HiveDecimal.create( (BigDecimal)colValues.get(colOffset) );
			dcv.vector[ rowOffset ] = new HiveDecimalWritable(hd);
			break;
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

	
	private static Path initHdfs( Configuration conf, String targetPath, String targetNameExt ) throws Exception {
		String hdfsUri = SupplyBlockchainConfig.getInstance().getHdfsUri();
		conf.set("fs.defaultFS", hdfsUri);
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		System.setProperty("HADOOP_USER_NAME", SupplyBlockchainConfig.getInstance().getHdfsUri() );
		System.setProperty("hadoop.home.dir", "/");
		String hdfsPath = targetPath.substring(6);
		Path newFolderPath = new Path(hdfsUri + hdfsPath);
		return new Path(newFolderPath + "/" + targetNameExt);
	}

}
