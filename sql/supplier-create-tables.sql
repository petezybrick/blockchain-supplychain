CALL PROC_DROP_FOREIGN_KEY('map_lot_canine_supplier_blockchain', 'fk_log_canine_supplier_bc_supplier');
CALL PROC_DROP_FOREIGN_KEY('map_lot_canine_supplier_blockchain', 'fk_log_canine_supplier_bc_canine');
CALL PROC_DROP_FOREIGN_KEY('supplier_transaction', 'fk_supplier_block_transaction');
CALL PROC_DROP_FOREIGN_KEY('supplier_transaction', 'fk_supplier_transaction_supplier');
CALL PROC_DROP_FOREIGN_KEY('supplier_block_transaction', 'fk_supplier_block');
CALL PROC_DROP_FOREIGN_KEY('supplier_block', 'fk_supplier_blockchain');


DROP TABLE IF EXISTS map_lot_canine_supplier_blockchain;
DROP TABLE IF EXISTS lot_canine;
DROP TABLE IF EXISTS supplier_blockchain;
DROP TABLE IF EXISTS supplier_block;
DROP TABLE IF EXISTS supplier_block_transaction;
DROP TABLE IF EXISTS supplier_transaction;
DROP TABLE IF EXISTS supplier;

CREATE TABLE supplier (
	supplier_uuid CHAR(36) NOT NULL PRIMARY KEY,
	duns_number char(10) not null,
	supplier_name VARCHAR(255) not null,
	supplier_category VARCHAR(255) not null,
	supplier_sub_category VARCHAR(255) not null,
	state_province VARCHAR(255) not null,
	country VARCHAR(255) not null,
	encoded_public_key VARCHAR(1024) not null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE supplier_blockchain (
	supplier_blockchain_uuid CHAR(36) NOT NULL PRIMARY KEY,
	supplier_type VARCHAR(255) not null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE supplier_block (
	supplier_block_uuid CHAR(36) NOT NULL PRIMARY KEY,
	supplier_blockchain_uuid CHAR(36) NOT NULL,
	hash varchar(1024) not null,
	previous_hash varchar(1024) not null,
	block_timestamp timestamp(3) not null,
	block_sequence integer not null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE supplier_block_transaction (
	supplier_block_transaction_uuid CHAR(36) NOT NULL PRIMARY KEY,
	supplier_block_uuid CHAR(36) NOT NULL,
	transaction_id varchar(1024) not null,
	encoded_public_key_from VARCHAR(1024) not null,
	encoded_public_key_to VARCHAR(1024) not null,
	signature varbinary(1024) not null,
	transaction_sequence integer not null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE supplier_transaction (
	supplier_transaction_uuid CHAR(36) NOT NULL PRIMARY KEY,
	supplier_block_transaction_uuid CHAR(36) NOT NULL,
	supplier_uuid CHAR(36) NOT NULL,
	supplier_lot_number VARCHAR(255) not null,
	item_number VARCHAR(255) not null,
	description VARCHAR(255) not null,
	qty INTEGER not null,
	units VARCHAR(255) not null,
	shipped_date_iso8601 timestamp(3) null DEFAULT null,
	rcvd_date_iso8601 timestamp(3) null DEFAULT null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE lot_canine (
	lot_canine_uuid CHAR(36) NOT NULL PRIMARY KEY,
	manufacturer_lot_number VARCHAR(255) not null,
	lot_filled_date timestamp(3) null DEFAULT null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE map_lot_canine_supplier_blockchain (
	map_lot_canine_supplier_blockchain_uuid CHAR(36) NOT NULL PRIMARY KEY,
	lot_canine_uuid CHAR(36) NOT NULL,
	supplier_blockchain_uuid CHAR(36) NOT NULL,
	ingredient_sequence integer not null,
	ingredient_name varchar(255) not null,
	insert_ts timestamp(3) DEFAULT CURRENT_TIMESTAMP(3),
	update_ts TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE supplier_transaction ADD CONSTRAINT fk_supplier_block_transaction 
	FOREIGN KEY (supplier_block_transaction_uuid) REFERENCES supplier_block_transaction(supplier_block_transaction_uuid)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE supplier_transaction ADD CONSTRAINT fk_supplier_transaction_supplier 
	FOREIGN KEY (supplier_uuid) REFERENCES supplier(supplier_uuid)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE supplier_block_transaction ADD CONSTRAINT fk_supplier_block 
	FOREIGN KEY (supplier_block_uuid) REFERENCES supplier_block(supplier_block_uuid)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE supplier_block ADD CONSTRAINT fk_supplier_blockchain 
	FOREIGN KEY (supplier_blockchain_uuid) REFERENCES supplier_blockchain(supplier_blockchain_uuid)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE map_lot_canine_supplier_blockchain ADD CONSTRAINT fk_log_canine_supplier_bc_canine 
	FOREIGN KEY (lot_canine_uuid) REFERENCES lot_canine(lot_canine_uuid)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE map_lot_canine_supplier_blockchain ADD CONSTRAINT fk_log_canine_supplier_bc_supplier 
	FOREIGN KEY (supplier_blockchain_uuid) REFERENCES supplier_blockchain(supplier_blockchain_uuid)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

	