package com.petezybrick.bcsc.service.orc;

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
import org.apache.orc.TypeDescription.Category;

/**
 * Modifiedj example from
 * https://orc.apache.org/docs/core-java.html
 *
 * Created by mreyes on 6/5/16.
 */
public class OrcReaderBcscBackup {
	public static void main(String [ ] args) throws Exception
	{
		Configuration conf = new Configuration();
		Reader reader = OrcFile.createReader(new Path("/tmp/person.orc"),
				OrcFile.readerOptions(conf));
		String schemaName = reader.getMetadataValue(OrcSchemaMgr.METADATA_SCHEMA_NAME_KEY).toString();
		String schemaVersion = reader.getMetadataValue(OrcSchemaMgr.METADATA_SCHEMA_VERSION_KEY).toString();

		RecordReader rows = reader.rows();
		VectorizedRowBatch batch = reader.getSchema().createRowBatch();
		
		// TODO: Crosscheck schema here
		List<ColumnVector> colVectors = new ArrayList<ColumnVector>();
		for( int i=0 ; i<reader.getSchema().getFieldNames().size() ; i++ ) {
			colVectors.add( batch.cols[i]);
		}
		
		List<BaseOrcVo> orcVos = new ArrayList<BaseOrcVo>();
		PersonOrcVo orcVo = new PersonOrcVo();

		List<List<Object>> rowsCols = new ArrayList<List<Object>>();
		while (rows.nextBatch(batch)) {
			for(int row=0; row < batch.size; row++) {
				List<Object> personCols = new ArrayList<Object>();
				for( int col=0 ; col<reader.getSchema().getFieldNames().size() ; col++ ) {
					Category category = reader.getSchema().getChildren().get(col).getCategory();
					if( category == Category.INT ) {
						LongColumnVector lcv = (LongColumnVector)colVectors.get(col);
						personCols.add( (int)lcv.vector[row]);
					} else if( category == Category.STRING ) {
						BytesColumnVector bcv = (BytesColumnVector)colVectors.get(col);
						personCols.add( new String(bcv.vector[row], bcv.start[row], bcv.length[row]) );
					} else if( category == Category.TIMESTAMP ) {
						TimestampColumnVector tcv = (TimestampColumnVector)colVectors.get(col);
						personCols.add( tcv.getTimestampAsLong(row));
					}
				}
				BaseOrcVo newOrcVo = orcVo.createInstance(personCols, schemaVersion);
				orcVos.add(newOrcVo);
				System.out.println( (PersonOrcVo)newOrcVo);
			}
		}
		rows.close();
	}
	

}
