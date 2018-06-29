package com.petezybrick.bcsc.service.test.orc;

import java.io.File;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.orc.TypeDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.petezybrick.bcsc.service.orc.BaseOrcVo;
import com.petezybrick.bcsc.service.orc.OrcCommon;
import com.petezybrick.bcsc.service.orc.OrcSchemaMgr;

public class TestOrcReadWrite {
	private List<String> persons;
	private String targetPath;
	private String targetNameExt;
	private String schemaName;
	private String schemaVersion;


	@BeforeClass
	public static void beforeClasse() throws Exception {
		OrcSchemaMgr.mapOrcSchemas.put("person|1.0", TypeDescription.createStruct()
				.addField("person_id", TypeDescription.createInt())
				.addField("last_name", TypeDescription.createString())
				.addField("first_name", TypeDescription.createString())
				.addField("gender", TypeDescription.createString())
				.addField("birth_date", TypeDescription.createDate())
				.addField("created_at", TypeDescription.createTimestamp())				
				.addField("person_byte", TypeDescription.createByte())
				.addField("person_boolean", TypeDescription.createBoolean())
				.addField("person_double", TypeDescription.createDouble())
				.addField("person_float", TypeDescription.createFloat())
				.addField("person_decimal", TypeDescription.createDecimal())
		);

	}
	
	@Before
	public void setUp() throws Exception {
		this.targetPath = "/tmp/";
		this.targetNameExt = "person.orc";
		this.schemaName = "person";
		this.schemaVersion  = "1.0";

		// Initialize Test Data
		this.persons = Arrays.asList( 
				"1|Adams|Abigail|F|1960-01-02|2018-01-02T11:22:33|testByte1|true|11.22|33.44|55.66",
				"2|Baker|Benjamin|M|1965-02-01|2018-02-03T11:33:44|testByte2|false|111.222|333.444|555.666",
				"3|Charles|Charlene|F|1955-03-04|2018-04-05T11:44:55|testByte3|true|1111.2222|3333.4444|5555.6666",
				"4|Dempsey|David|M|1970-04-05|2018-06-07T11:00:11|testByte4|false|11111.22222|33333.44444|55555.66666"
				);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWriteRead() throws Exception {
		try {
			List<PersonOrcVo> writePersonOrcVos = new ArrayList<PersonOrcVo>();
			for( String person : persons ) {
				String[] tokens = person.split("[|]");
				
				PersonOrcVo personOrcVo = new PersonOrcVo()
						.setPersonId(Integer.parseInt(tokens[0]))
						.setLastName(tokens[1])
						.setFirstName(tokens[2])
						.setGender(tokens[3])
						.setBirthDate( LocalDate.parse(tokens[4]) )
						.setCreatedAt( new Timestamp(LocalDateTime.parse(tokens[5]).toInstant(ZoneOffset.UTC).toEpochMilli() ) )
						.setPersonByte( ByteBuffer.wrap(tokens[6].getBytes() ) )
						.setPersonBoolean( Boolean.valueOf( tokens[7]) ) 
						.setPersonDouble( Double.valueOf( tokens[8])) 
						.setPersonFloat( Float.valueOf( tokens[9]) ) 
						.setPersonDecimal( new BigDecimal(tokens[10]) ) 
						;	
				writePersonOrcVos.add( personOrcVo );
			}		
			List<List<Object>> personRowCols = PersonOrcDao.createRowsCols(writePersonOrcVos);
			OrcCommon.write( targetPath, targetNameExt, schemaName, schemaVersion, personRowCols );
			
			final PersonOrcVo personOrcVo = new PersonOrcVo();
			List<BaseOrcVo> readPersonOrcVos = OrcCommon.read(targetPath, targetNameExt, personOrcVo);
			readPersonOrcVos.forEach(pov -> System.out.println((PersonOrcVo) pov));
			// TODO: compare values here
		} catch( Exception e ) {
			System.out.println(e);
		} finally {
			new File(targetPath + targetNameExt).delete();
		}
	}

}
