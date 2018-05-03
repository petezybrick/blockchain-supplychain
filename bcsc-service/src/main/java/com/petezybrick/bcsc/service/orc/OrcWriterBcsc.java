package com.petezybrick.bcsc.service.orc;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OrcWriterBcsc {

	// TODO: how to handle dates/times
	
	public static void main(String [ ] args) throws Exception
	{
		// Initialize Test Data
		List<String> persons = Arrays.asList( 
				"1|Adams|Abigail|F|1960-01-02|2018-01-02T11:22:33",
				"2|Baker|Benjamin|M|1965-02-01|2018-02-03T11:33:44",
				"3|Charles|Charlene|F|1955-03-04|2018-04-05T11:44:55",
				"4|Dempsey|David|M|1970-04-05|2018-06-07T11:00:11"
				);
		
		List<PersonOrcVo> personOrcVos = new ArrayList<PersonOrcVo>();
		for( String person : persons ) {
			String[] tokens = person.split("[|]");
			
			Timestamp ts = new Timestamp(LocalDateTime.parse(tokens[5]).toInstant(ZoneOffset.UTC).toEpochMilli() );
			System.out.println(ts);
			
			PersonOrcVo personOrcVo = new PersonOrcVo()
					.setPersonId(Integer.parseInt(tokens[0]))
					.setLastName(tokens[1])
					.setFirstName(tokens[2])
					.setGender(tokens[3])
					.setBirthDate( LocalDate.parse(tokens[4]) )
					.setCreatedAt( new Timestamp(LocalDateTime.parse(tokens[5]).toInstant(ZoneOffset.UTC).toEpochMilli() ) )
					;	
			personOrcVos.add( personOrcVo );
		}		
		List<List<Object>> personRowCols = PersonOrcDao.createRowsCols(personOrcVos);
		String pathNameExt = "/tmp/person.orc";
		String schemaName = "person";
		String schemaVersion  = "1.0";
		OrcCommon.write( pathNameExt, schemaName, schemaVersion, personRowCols );
	}

}
