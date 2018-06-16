package com.petezybrick.bcsc.service.orc;

import java.util.HashMap;
import java.util.Map;

import org.apache.orc.TypeDescription;

public class OrcSchemaMgr {
	public static final String METADATA_SCHEMA_NAME_KEY = "schema_name";
	public static final String METADATA_SCHEMA_VERSION_KEY = "schema_version";
	public static final Map<String,TypeDescription> mapOrcSchemas = new HashMap<String,TypeDescription>();
	
	static {
		mapOrcSchemas.put("supplier|1.0", TypeDescription.fromString("struct<supplier_uuid:STRING,duns_number:STRING,supplier_name:STRING,supplier_category:STRING,supplier_sub_category:STRING,state_province:STRING,country:STRING,encoded_public_key:STRING>"));
		mapOrcSchemas.put("supplier_blockchain|1.0", TypeDescription.fromString("struct<supplier_blockchain_uuid:STRING,supplier_type:STRING>"));
		mapOrcSchemas.put("supplier_block|1.0", TypeDescription.fromString("struct<supplier_block_uuid:STRING,supplier_blockchain_uuid:STRING,supplier_uuid:STRING,hash:STRING,previous_hash:STRING,block_timestamp:TIMESTAMP,block_sequence:INT>"));
		mapOrcSchemas.put("supplier_block_transaction|1.0", TypeDescription.fromString("struct<supplier_block_transaction_uuid:STRING,supplier_block_uuid:STRING,transaction_id:STRING,encoded_public_key_from:STRING,encoded_public_key_to:STRING,signature:BINARY,transaction_sequence:INT>"));
		mapOrcSchemas.put("supplier_transaction|1.0", TypeDescription.fromString("struct<supplier_transaction_uuid:STRING,supplier_block_transaction_uuid:STRING,supplier_uuid:STRING,supplier_lot_number:STRING,item_number:STRING,description:STRING,qty:INT,units:STRING,shipped_date_iso8601:TIMESTAMP,rcvd_date_iso8601:TIMESTAMP>"));
		mapOrcSchemas.put("lot_canine|1.0", TypeDescription.fromString("struct<lot_canine_uuid:STRING,manufacturer_lot_number:STRING,lot_filled_date:TIMESTAMP>"));
		mapOrcSchemas.put("map_lot_canine_supplier_blockchain|1.0", TypeDescription.fromString("struct<map_lot_canine_supplier_blockchain_uuid:STRING,lot_canine_uuid:STRING,supplier_blockchain_uuid:STRING,ingredient_sequence:INT,ingredient_name:STRING>"));
		mapOrcSchemas.put("customer|1.0", TypeDescription.fromString("struct<customer_uuid:STRING,first_name:STRING,last_name:STRING,email_address:STRING>"));
		mapOrcSchemas.put("customer_loyalty|1.0", TypeDescription.fromString("struct<customer_loyalty_uuid:STRING,customer_uuid:STRING,desc_type:STRING,desc_text:STRING,manufacturer_lot_number:STRING>"));

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
