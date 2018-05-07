package com.petezybrick.bcsc.service.orcdev;


import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplierBlockVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockVo.class);
	private String supplierBlockUuid;
	private String supplierBlockchainUuid;
	private String hash;
	private String previousHash;
	private Timestamp blockTimestamp;
	private Integer blockSequence;


	public SupplierBlockVo() {
	}


	public SupplierBlockVo(ResultSet rs) throws SQLException {
		this.supplierBlockUuid = rs.getString("supplier_block_uuid");
		this.supplierBlockchainUuid = rs.getString("supplier_blockchain_uuid");
		this.hash = rs.getString("hash");
		this.previousHash = rs.getString("previous_hash");
		this.blockTimestamp = rs.getTimestamp("block_timestamp");
		this.blockSequence = rs.getInt("block_sequence");
	}


	public String getSupplierBlockUuid() {
		return this.supplierBlockUuid;
	}
	public String getSupplierBlockchainUuid() {
		return this.supplierBlockchainUuid;
	}
	public String getHash() {
		return this.hash;
	}
	public String getPreviousHash() {
		return this.previousHash;
	}
	public Timestamp getBlockTimestamp() {
		return this.blockTimestamp;
	}
	public Integer getBlockSequence() {
		return this.blockSequence;
	}


	public SupplierBlockVo setSupplierBlockUuid( String supplierBlockUuid ) {
		this.supplierBlockUuid = supplierBlockUuid;
		return this;
	}
	public SupplierBlockVo setSupplierBlockchainUuid( String supplierBlockchainUuid ) {
		this.supplierBlockchainUuid = supplierBlockchainUuid;
		return this;
	}
	public SupplierBlockVo setHash( String hash ) {
		this.hash = hash;
		return this;
	}
	public SupplierBlockVo setPreviousHash( String previousHash ) {
		this.previousHash = previousHash;
		return this;
	}
	public SupplierBlockVo setBlockTimestamp( Timestamp blockTimestamp ) {
		this.blockTimestamp = blockTimestamp;
		return this;
	}
	public SupplierBlockVo setBlockSequence( Integer blockSequence ) {
		this.blockSequence = blockSequence;
		return this;
	}
}

// SupplierBlockVo supplierBlockVo = new SupplierBlockVo()
//	 .setSupplierBlockUuid("xxx")
//	 .setSupplierBlockchainUuid("xxx")
//	 .setHash("xxx")
//	 .setPreviousHash("xxx")
//	 .setBlockTimestamp("xxx")
//	 .setBlockSequence("xxx")
//	 ;
