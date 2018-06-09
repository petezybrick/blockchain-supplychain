package com.petezybrick.bcsc.service.orc;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.service.database.SupplierBlockVo;


public class SupplierBlockOrcVo extends BaseOrcVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockOrcVo.class);
	private String supplierBlockUuid;
	private String supplierBlockchainUuid;
	private String supplierUuid;
	private String hash;
	private String previousHash;
	private Timestamp blockTimestamp;
	private Integer blockSequence;


	public SupplierBlockOrcVo() {
	}


	public SupplierBlockOrcVo(ResultSet rs) throws SQLException {
		this.supplierBlockUuid = rs.getString("supplier_block_uuid");
		this.supplierBlockchainUuid = rs.getString("supplier_blockchain_uuid");
		this.supplierUuid = rs.getString("supplier_uuid");
		this.hash = rs.getString("hash");
		this.previousHash = rs.getString("previous_hash");
		this.blockTimestamp = rs.getTimestamp("block_timestamp");
		this.blockSequence = rs.getInt("block_sequence");
	}


	public SupplierBlockOrcVo(SupplierBlockVo fromVo) throws SQLException {
		this.supplierBlockUuid = fromVo.getSupplierBlockUuid();
		this.supplierBlockchainUuid = fromVo.getSupplierBlockchainUuid();
		this.supplierUuid = fromVo.getSupplierUuid();
		this.hash = fromVo.getHash();
		this.previousHash = fromVo.getPreviousHash();
		this.blockTimestamp = fromVo.getBlockTimestamp();
		this.blockSequence = fromVo.getBlockSequence();
	}


	@Override
	public SupplierBlockOrcVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
			return new SupplierBlockOrcVo()
				.setSupplierBlockUuid((String)objs.get(0))
				.setSupplierBlockchainUuid((String)objs.get(1))
				.setSupplierUuid((String)objs.get(2))
				.setHash((String)objs.get(3))
				.setPreviousHash((String)objs.get(4))
				.setBlockTimestamp((Timestamp)objs.get(5))
				.setBlockSequence((Integer)objs.get(6))
				;
		} else throw new Exception("Invalid schema version 1.0");
	}


	@Override
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.supplierBlockUuid = (String)objs.get(0);
		this.supplierBlockchainUuid = (String)objs.get(1);
		this.supplierUuid = (String)objs.get(2);
		this.hash = (String)objs.get(3);
		this.previousHash = (String)objs.get(4);
		this.blockTimestamp = (Timestamp)objs.get(5);
		this.blockSequence = (Integer)objs.get(6);
	}


	@Override
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(supplierBlockUuid);
		objs.add(supplierBlockchainUuid);
		objs.add(supplierUuid);
		objs.add(hash);
		objs.add(previousHash);
		objs.add(blockTimestamp);
		objs.add(blockSequence);
		return objs;
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


	public SupplierBlockOrcVo setSupplierBlockUuid( String supplierBlockUuid ) {
		this.supplierBlockUuid = supplierBlockUuid;
		return this;
	}
	public SupplierBlockOrcVo setSupplierBlockchainUuid( String supplierBlockchainUuid ) {
		this.supplierBlockchainUuid = supplierBlockchainUuid;
		return this;
	}
	public SupplierBlockOrcVo setHash( String hash ) {
		this.hash = hash;
		return this;
	}
	public SupplierBlockOrcVo setPreviousHash( String previousHash ) {
		this.previousHash = previousHash;
		return this;
	}
	public SupplierBlockOrcVo setBlockTimestamp( Timestamp blockTimestamp ) {
		this.blockTimestamp = blockTimestamp;
		return this;
	}
	public SupplierBlockOrcVo setBlockSequence( Integer blockSequence ) {
		this.blockSequence = blockSequence;
		return this;
	}


	public String getSupplierUuid() {
		return supplierUuid;
	}


	public SupplierBlockOrcVo setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
		return this;
	}
}
