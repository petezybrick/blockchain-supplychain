package com.petezybrick.bcsc.service.orc;

import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;

/**
 * Modifiedj example from
 * https://orc.apache.org/docs/core-java.html
 *
 * Created by mreyes on 6/5/16.
 */
public class OrcWriterUsingVectorizedRowBatch {
	private static Random rand = new Random();

	// TODO: how to handle dates/times
	
	public static void main(String [ ] args) throws java.io.IOException
	{
		Configuration conf = new Configuration();
		TypeDescription schema = TypeDescription.createStruct()
				.addField("person_id", TypeDescription.createInt())
				.addField("last_name", TypeDescription.createString())
				.addField("first_name", TypeDescription.createString())
				.addField("gender", TypeDescription.createString())
				.addField("birth_date", TypeDescription.createTimestamp());
		
		new File("/tmp/my-file.orc").delete();
		Writer writer = OrcFile.createWriter(new Path("/tmp/my-file.orc"),
				OrcFile.writerOptions(conf)
						.setSchema(schema));

		VectorizedRowBatch batch = schema.createRowBatch();
		LongColumnVector vPersonId = (LongColumnVector) batch.cols[0];
		BytesColumnVector vLastName = (BytesColumnVector) batch.cols[1];
		BytesColumnVector vFirstName = (BytesColumnVector) batch.cols[2];
		BytesColumnVector vGender = (BytesColumnVector) batch.cols[3];
		TimestampColumnVector vBirthDate = (TimestampColumnVector) batch.cols[4];
		
		List<String> persons = Arrays.asList( 
				"1|Adams|Abigail|F|1960-01-02",
				"2|Baker|Benjamin|M|1965-02-01",
				"3|Charles|Charlene|F|1955-03-04",
				"4|Dempsey|David|M|1970-04-05"
				);
		for( String person : persons ) {
			int row = batch.size++;
			String[] tokens = person.split("[|]");
			vPersonId.vector[row] = Integer.parseInt(tokens[0]);
			vLastName.setVal(row, tokens[1].getBytes());
			vFirstName.setVal(row, tokens[2].getBytes());
			vGender.setVal(row, tokens[3].getBytes());
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			vBirthDate.set(row, ts);
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
