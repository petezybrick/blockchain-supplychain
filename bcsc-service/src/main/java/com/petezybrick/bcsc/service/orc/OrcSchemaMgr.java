package com.petezybrick.bcsc.service.orc;

import java.util.HashMap;
import java.util.Map;

import org.apache.orc.TypeDescription;

public class OrcSchemaMgr {
	public static final Map<String,TypeDescription>mapOrcSchemas = new HashMap<String,TypeDescription>();
	
	static {
		TypeDescription schemaPerson = TypeDescription.createStruct()
				.addField("person_id", TypeDescription.createInt())
				.addField("last_name", TypeDescription.createString())
				.addField("first_name", TypeDescription.createString())
				.addField("gender", TypeDescription.createString())
				.addField("birth_date", TypeDescription.createTimestamp());
		mapOrcSchemas.put("person", schemaPerson);
	}

}
