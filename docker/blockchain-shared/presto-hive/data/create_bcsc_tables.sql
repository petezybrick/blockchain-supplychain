DROP TABLE db_bcsc.supplier;
DROP TABLE db_bcsc.supplier_blockchain;
DROP TABLE db_bcsc.supplier_block;
DROP TABLE db_bcsc.supplier_block_transaction;
DROP TABLE db_bcsc.supplier_transaction;
DROP TABLE db_bcsc.lot_canine;
DROP TABLE db_bcsc.map_lot_canine_supplier_blockchain;
DROP DATABASE IF EXISTS db_bcsc;

CREATE DATABASE db_bcsc;

CREATE TABLE db_bcsc.supplier ( 
     supplier_uuid STRING
     ,duns_number STRING
     ,supplier_name STRING
     ,supplier_category STRING
     ,supplier_sub_category STRING
     ,state_province STRING
     ,country STRING
     ,encoded_public_key STRING
) STORED AS ORC LOCATION '/user/bcsc/bcsc_data/supplier';

CREATE TABLE db_bcsc.supplier_blockchain ( 
     supplier_blockchain_uuid STRING
     ,supplier_type STRING
) STORED AS ORC LOCATION '/user/bcsc/bcsc_data/supplier_blockchain';

CREATE TABLE db_bcsc.supplier_block ( 
     supplier_block_uuid STRING
     ,supplier_blockchain_uuid STRING
     ,hash STRING
     ,previous_hash STRING
     ,block_timestamp TIMESTAMP
     ,block_sequence INT
) STORED AS ORC LOCATION '/user/bcsc/bcsc_data/supplier_block';

CREATE TABLE db_bcsc.supplier_block_transaction ( 
     supplier_block_transaction_uuid STRING
     ,supplier_block_uuid STRING
     ,transaction_id STRING
     ,encoded_public_key_from STRING
     ,encoded_public_key_to STRING
     ,signature BINARY
     ,transaction_sequence INT
) STORED AS ORC LOCATION '/user/bcsc/bcsc_data/supplier_block_transaction';

CREATE TABLE db_bcsc.supplier_transaction ( 
     supplier_transaction_uuid STRING
     ,supplier_block_transaction_uuid STRING
     ,supplier_uuid STRING
     ,supplier_lot_number STRING
     ,item_number STRING
     ,description STRING
     ,qty INT
     ,units STRING
     ,shipped_date_iso8601 TIMESTAMP
     ,rcvd_date_iso8601 TIMESTAMP
) STORED AS ORC LOCATION '/user/bcsc/bcsc_data/supplier_transaction';

CREATE TABLE db_bcsc.lot_canine ( 
     lot_canine_uuid STRING
     ,manufacturer_lot_number STRING
     ,lot_filled_date TIMESTAMP
) STORED AS ORC LOCATION '/user/bcsc/bcsc_data/lot_canine';

CREATE TABLE db_bcsc.map_lot_canine_supplier_blockchain ( 
     map_lot_canine_supplier_blockchain_uuid STRING
     ,lot_canine_uuid STRING
     ,supplier_blockchain_uuid STRING
     ,ingredient_sequence INT
     ,ingredient_name STRING
) STORED AS ORC LOCATION '/user/bcsc/bcsc_data/map_lot_canine_supplier_blockchain';