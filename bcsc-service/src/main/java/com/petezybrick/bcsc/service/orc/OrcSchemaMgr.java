package com.petezybrick.bcsc.service.orc;

import java.util.HashMap;
import java.util.Map;

import org.apache.orc.TypeDescription;

public class OrcSchemaMgr {
	public static final String METADATA_SCHEMA_NAME_KEY = "schema_name";
	public static final String METADATA_SCHEMA_VERSION_KEY = "schema_version";
	public static final Map<String,TypeDescription>mapOrcSchemas = new HashMap<String,TypeDescription>();
	
	static {
		mapOrcSchemas.put("person|1.0", TypeDescription.createStruct()
				.addField("person_id", TypeDescription.createInt())
				.addField("last_name", TypeDescription.createString())
				.addField("first_name", TypeDescription.createString())
				.addField("gender", TypeDescription.createString())
				.addField("birth_date", TypeDescription.createDate())
				.addField("created_at", TypeDescription.createTimestamp())
		);
	}

	public static void validateSchema( String schemaName, String schemaVersion, TypeDescription schemaIn ) 
			throws SchemaFieldMismatchException {
		TypeDescription schemaBase = mapOrcSchemas.get(schemaName + "|" + schemaVersion);
		if( schemaBase == null ) throw new SchemaFieldMismatchException("Schema not found: name " + schemaName + ", version " + schemaVersion );
		StringBuilder sbErrors = new StringBuilder();
		if(	schemaBase.getChildren().size() != schemaIn.getChildren().size() ) {
			sbErrors.append("Count mismatch, schemaIn has ").append(schemaIn.getChildren().size() )
				.append(" fields, schemaBase has ").append(schemaBase.getChildren().size() )
				.append(" fields.");
			throw new SchemaFieldMismatchException(sbErrors.toString());
		}
		// TODO: compare the categories and field attributes, must be exactly the same		
		if( sbErrors.length() > 0 ) throw new SchemaFieldMismatchException(sbErrors.toString());
	}
}
