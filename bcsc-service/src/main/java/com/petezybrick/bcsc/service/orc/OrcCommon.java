package com.petezybrick.bcsc.service.orc;

import java.io.File;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import org.apache.orc.TypeDescription.Category;

public class OrcCommon {


	public static List<BaseOrcVo> read(String pathNameExt, BaseOrcVo baseOrcVo) throws Exception {
		List<BaseOrcVo> orcVos = new ArrayList<BaseOrcVo>();

		Configuration conf = new Configuration();
		Reader reader = OrcFile.createReader(new Path(pathNameExt), OrcFile.readerOptions(conf));

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

	
	public static void write( String pathNameExt, String schemaName, String schemaVersion, List<List<Object>> rowsCols ) throws Exception {
		Configuration conf = new Configuration();
		TypeDescription schema = OrcSchemaMgr.mapOrcSchemas.get( schemaName + "|" + schemaVersion );
		
		List<ColumnVector> colVectors = new ArrayList<ColumnVector>();
		VectorizedRowBatch batch = schema.createRowBatch();
		for( int i=0 ; i<schema.getFieldNames().size() ; i++ ) {
			colVectors.add( batch.cols[i]);
		}
		
		new File(pathNameExt).delete();
		Writer writer = OrcFile.createWriter(new Path(pathNameExt),
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
		case BYTE:
		case BINARY:
			break;
		case BOOLEAN:
			break;
		case DATE:
			break;
		case DOUBLE:
			break;
		case FLOAT:
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
			colValues.add(tcv.getTimestampAsLong(rowOffset));
			break;
		case STRING:
		case CHAR:
		case VARCHAR:
			BytesColumnVector bcv = (BytesColumnVector) colVectors.get(colOffset);
			colValues.add(new String(bcv.vector[rowOffset], bcv.start[rowOffset], bcv.length[rowOffset]));
			break;
		case DECIMAL: {
			break;
		}
		// Not supported for this application
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
		case BYTE:
		case BINARY:
			break;
		case BOOLEAN:
			break;
		case DATE:
			break;
		case DOUBLE:
			break;
		case FLOAT:
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
			BytesColumnVector bcv = (BytesColumnVector)colVector;
			bcv.setVal(rowOffset, ((String)colValues.get(colOffset)).getBytes());
			break;
		case DECIMAL: {
			break;
		}
		// Not supported for this application
		case LIST:
		case MAP:
		case UNION:
		case STRUCT:
			throw new Exception("Unsupported category for this application: " + category);
		default:
			throw new IllegalArgumentException("Unknown type " + category);
		}
	}
}
