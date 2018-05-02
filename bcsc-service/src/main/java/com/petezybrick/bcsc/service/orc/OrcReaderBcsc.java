package com.petezybrick.bcsc.service.orc;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
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
import org.apache.orc.TypeDescription.Category;

/**
 * Modifiedj example from https://orc.apache.org/docs/core-java.html
 *
 * Created by mreyes on 6/5/16.
 */
public class OrcReaderBcsc {
	public static void main(String[] args) throws Exception {
		OrcReaderBcsc orcReaderBcsc = new OrcReaderBcsc();
		orcReaderBcsc.process();
	}

	public void process() throws Exception {
		final PersonOrcVo personOrcVo = new PersonOrcVo();
		List<BaseOrcVo> personOrcVos = commonOrcRead("/tmp/person.orc", personOrcVo);
		personOrcVos.forEach(pov -> System.out.println((PersonOrcVo) pov));
	}

	public List<BaseOrcVo> commonOrcRead(String pathNameExt, BaseOrcVo baseOrcVo) throws Exception {
		List<BaseOrcVo> orcVos = new ArrayList<BaseOrcVo>();

		Configuration conf = new Configuration();
		Reader reader = OrcFile.createReader(new Path(pathNameExt), OrcFile.readerOptions(conf));

		String schemaName = BaseOrcVo.roByteBufferToString(reader.getMetadataValue(OrcSchemaMgr.METADATA_SCHEMA_NAME_KEY));
		String schemaVersion = BaseOrcVo.roByteBufferToString(reader.getMetadataValue(OrcSchemaMgr.METADATA_SCHEMA_VERSION_KEY));
		// TODO: Crosscheck schema here

		RecordReader recordReader = reader.rows();
		VectorizedRowBatch batch = reader.getSchema().createRowBatch();

		List<ColumnVector> colVectors = new ArrayList<ColumnVector>();
		for (int i = 0; i < reader.getSchema().getFieldNames().size(); i++) {
			colVectors.add(batch.cols[i]);
		}

		List<List<Object>> rowsCols = new ArrayList<List<Object>>();
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

	private void addColumnVectorByCategory(Category category, List<Object> colValues, List<ColumnVector> colVectors,
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
}
