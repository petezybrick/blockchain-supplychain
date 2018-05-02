package com.petezybrick.bcsc.service.orc;

import java.io.File;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import org.apache.orc.TypeDescription.Category;

/**
 * Modifiedj example from
 * https://orc.apache.org/docs/core-java.html
 *
 * Created by mreyes on 6/5/16.
 */
public class OrcWriterBcsc {
	private static Random rand = new Random();

	// TODO: how to handle dates/times
	
	public static void main(String [ ] args) throws Exception
	{
		// Initialize Test Data
		List<String> persons = Arrays.asList( 
				"1|Adams|Abigail|F|1960-01-02",
				"2|Baker|Benjamin|M|1965-02-01",
				"3|Charles|Charlene|F|1955-03-04",
				"4|Dempsey|David|M|1970-04-05"
				);
		
		List<PersonOrcVo> personOrcVos = new ArrayList<PersonOrcVo>();
		for( String person : persons ) {
			String[] tokens = person.split("[|]");
			PersonOrcVo personOrcVo = new PersonOrcVo()
					.setPersonId(Integer.parseInt(tokens[0]))
					.setLastName(tokens[1])
					.setFirstName(tokens[2])
					.setGender(tokens[3])
					.setBirthDate(new Timestamp(System.currentTimeMillis() ));	// TODO: handle Dates
			personOrcVos.add( personOrcVo );
		}		
		List<List<Object>> personRowCols = PersonOrcDao.createRowsCols(personOrcVos);

		Configuration conf = new Configuration();
		String schemaName = "person";
		String schemaVersion  = "1.0";
		TypeDescription schema = OrcSchemaMgr.mapOrcSchemas.get( schemaName + "|" + schemaVersion );
		
		List<ColumnVector> colVectors = new ArrayList<ColumnVector>();
		VectorizedRowBatch batch = schema.createRowBatch();
		for( int i=0 ; i<schema.getFieldNames().size() ; i++ ) {
			colVectors.add( batch.cols[i]);
		}
		
		new File("/tmp/person.orc").delete();
		Writer writer = OrcFile.createWriter(new Path("/tmp/person.orc"),
				OrcFile.writerOptions(conf)
						.setSchema(schema));
		writer.addUserMetadata(OrcSchemaMgr.METADATA_SCHEMA_NAME_KEY, ByteBuffer.wrap( schemaName.getBytes() ));		
		writer.addUserMetadata(OrcSchemaMgr.METADATA_SCHEMA_VERSION_KEY, ByteBuffer.wrap( schemaVersion.getBytes() ));		

		for( List<Object> personCols : personRowCols ) {
			int row = batch.size++;
			System.out.println("++++++++++++++++++++++");
			for( int col=0 ; col<schema.getFieldNames().size() ; col++ ) {
				Object colValue = personCols.get(col).toString();
				String fieldName = schema.getFieldNames().get(col);
				String category = schema.getChildren().get(col).getCategory().getName();
				System.out.println( fieldName + " " + colValue + " " + category);
				ColumnVector cv = colVectors.get(col);
				// TODO: set here based on data type and row
				if( schema.getChildren().get(col).getCategory() == Category.STRING ) {
					String value = (String)personCols.get(col);
					BytesColumnVector bcv = (BytesColumnVector)cv;
					bcv.setVal(row, value.getBytes());
				} else if( schema.getChildren().get(col).getCategory() == Category.INT ) {
					Integer value = (Integer)personCols.get(col);
					LongColumnVector lcv = (LongColumnVector)cv;
					lcv.vector[row] = value;
				} else if( schema.getChildren().get(col).getCategory() == Category.TIMESTAMP ) {
					Timestamp value = (Timestamp)personCols.get(col);
					TimestampColumnVector tcv = (TimestampColumnVector)cv;
					tcv.set(row, value);
				}
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
}
