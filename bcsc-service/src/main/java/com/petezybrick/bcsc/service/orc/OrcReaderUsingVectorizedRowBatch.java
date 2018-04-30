package com.petezybrick.bcsc.service.orc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;

/**
 * Modifiedj example from
 * https://orc.apache.org/docs/core-java.html
 *
 * Created by mreyes on 6/5/16.
 */
public class OrcReaderUsingVectorizedRowBatch {
	public static void main(String [ ] args) throws java.io.IOException
	{
		Configuration conf = new Configuration();
		Reader reader = OrcFile.createReader(new Path("/tmp/my-file.orc"),
				OrcFile.readerOptions(conf));

		RecordReader rows = reader.rows();
		VectorizedRowBatch batch = reader.getSchema().createRowBatch();

		while (rows.nextBatch(batch)) {
			LongColumnVector vPersonId = (LongColumnVector) batch.cols[0];
			BytesColumnVector vLastName = (BytesColumnVector) batch.cols[1];
			BytesColumnVector vFirstName = (BytesColumnVector) batch.cols[2];
			BytesColumnVector vGender = (BytesColumnVector) batch.cols[3];
			TimestampColumnVector vBirthDate = (TimestampColumnVector) batch.cols[4];

			for(int r=0; r < batch.size; r++) {
				int personId = (int) vPersonId.vector[r];
				String lastName = new String(vLastName.vector[r], vLastName.start[r], vLastName.length[r]);
				String firstName = new String(vFirstName.vector[r], vFirstName.start[r], vFirstName.length[r]);
				String gender = new String(vGender.vector[r], vGender.start[r], vGender.length[r]);
				Long birthDate = vBirthDate.getTimestampAsLong(r);
				
				System.out.println(personId + ", " + lastName + ", " + firstName + ", " + gender + ", " + birthDate );

			}
		}
		rows.close();
	}
}
