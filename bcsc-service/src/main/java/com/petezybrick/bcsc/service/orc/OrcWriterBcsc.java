package com.petezybrick.bcsc.service.orc;

import java.io.File;
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
	
	public static void main(String [ ] args) throws java.io.IOException
	{
		// Initialize Test Data
		List<String> persons = Arrays.asList( 
				"1|Adams|Abigail|F|1960-01-02",
				"2|Baker|Benjamin|M|1965-02-01",
				"3|Charles|Charlene|F|1955-03-04",
				"4|Dempsey|David|M|1970-04-05"
				);

		// define the header once, which must correspond to the order of columns in the schema
		List<String> hdr = Arrays.asList("person_id", "last_name", "first_name", "gender", "birth_date");
		List<List<Object>> personRowCols = new ArrayList<List<Object>>();
		for( String person : persons ) {
			List<Object> personCols = new ArrayList<Object>();
			personRowCols.add(personCols);
			String[] tokens = person.split("[|]");
			personCols.add( Integer.parseInt(tokens[0]));
			personCols.add( tokens[1]);
			personCols.add( tokens[2]);
			personCols.add( tokens[3]);
			personCols.add( new Timestamp(System.currentTimeMillis() ));	// TODO: handle Dates
		}

		
		
		Configuration conf = new Configuration();
		TypeDescription schema = OrcSchemaMgr.mapOrcSchemas.get("person");
		
		new File("/tmp/person.orc").delete();
		Writer writer = OrcFile.createWriter(new Path("/tmp/person.orc"),
				OrcFile.writerOptions(conf)
						.setSchema(schema));
		
		List<ColumnVector> colVectors = new ArrayList<ColumnVector>();
		VectorizedRowBatch batch = schema.createRowBatch();
		for( int i=0 ; i<schema.getFieldNames().size() ; i++ ) {
			colVectors.add( batch.cols[i]);
		}
		
		for( int row=0 ; row<personRowCols.size() ; row++ ) {
			List<Object> personCols = personRowCols.get(row);
			System.out.println("++++++++++++++++++++++");
			for( int col=0 ; col<schema.getFieldNames().size() ; col++ ) {
				Object colValue = personCols.get(col).toString();
				String fieldName = schema.getFieldNames().get(col);
				String category = schema.getChildren().get(col).getCategory().getName();
				if( schema.getChildren().get(col).getCategory() == Category.STRING ) {
					colValue = String.valueOf(personCols.get(col));
				}
				System.out.println( fieldName + " " + colValue + " " + category);
				ColumnVector cv = colVectors.get(col);
				// TODO: set here based on data type and row
			}
		}
		
		
//		for( String person : persons ) {
//			int row = batch.size++;
//			String[] tokens = person.split("[|]");
//			vPersonId.vector[row] = Integer.parseInt(tokens[0]);
//			vLastName.setVal(row, tokens[1].getBytes());
//			vFirstName.setVal(row, tokens[2].getBytes());
//			vGender.setVal(row, tokens[3].getBytes());
//			Timestamp ts = new Timestamp(System.currentTimeMillis());
//			vBirthDate.set(row, ts);
//			if (batch.size == batch.getMaxSize()) {
//				writer.addRowBatch(batch);
//				batch.reset();
//			}
//		}
//
//		if (batch.size != 0) {
//			writer.addRowBatch(batch);
//			batch.reset();
//		}
		writer.close();
	}
}
