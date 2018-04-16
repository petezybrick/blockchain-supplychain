package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.utils.BlockchainUtils;


public class SupplierBlockVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockVo.class);
	private String supplierBlockUuid;
	private String supplierBlockchainUuid;
	private String hash;
	private String previousHash;
	private Timestamp blockTimestamp;
	private int blockSequence;
	private Timestamp insertTs;
	private Timestamp updateTs;
	private SupplierBlockTransactionVo supplierBlockTransactionVo;


	public SupplierBlockVo() {
	}
	
	public SupplierBlockVo updateHash() {
		this.hash = BlockchainUtils.applySha256( 
			this.previousHash +
			blockTimestamp.toString() +
			this.supplierBlockTransactionVo.hashCode() + 
			Integer.toString(this.blockSequence)
			);
		return this;
	}
	
	public String calculateHash() {
		return BlockchainUtils.applySha256( 
			this.previousHash +
			blockTimestamp.toString() +
			this.supplierBlockTransactionVo.hashCode() + 
			Integer.toString(this.blockSequence)
			);
	}


	public SupplierBlockVo(ResultSet rs) throws SQLException {
		this.supplierBlockUuid = rs.getString("supplier_block_uuid");
		this.supplierBlockchainUuid = rs.getString("supplier_blockchain_uuid");
		this.hash = rs.getString("hash");
		this.previousHash = rs.getString("previous_hash");
		this.blockTimestamp = rs.getTimestamp("block_timestamp");
		this.blockSequence = rs.getInt("block_sequence");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
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
	public int getBlockSequence() {
		return this.blockSequence;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
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
	public SupplierBlockVo setBlockSequence( int blockSequence ) {
		this.blockSequence = blockSequence;
		return this;
	}
	public SupplierBlockVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public SupplierBlockVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}


	public SupplierBlockTransactionVo getSupplierBlockTransactionVo() {
		return supplierBlockTransactionVo;
	}


	public SupplierBlockVo setSupplierBlockTransactionVo(SupplierBlockTransactionVo supplierBlockTransactionVo) {
		this.supplierBlockTransactionVo = supplierBlockTransactionVo;
		return this;
	}


	@Override
	public String toString() {
		return "SupplierBlockVo [supplierBlockUuid=" + supplierBlockUuid + ", supplierBlockchainUuid=" + supplierBlockchainUuid + ", hash=" + hash
				+ ", previousHash=" + previousHash + ", blockTimestamp=" + blockTimestamp + ", blockSequence=" + blockSequence + ", insertTs=" + insertTs
				+ ", updateTs=" + updateTs + ", supplierBlockTransactionVo=" + supplierBlockTransactionVo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blockSequence;
		result = prime * result + ((blockTimestamp == null) ? 0 : blockTimestamp.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + ((insertTs == null) ? 0 : insertTs.hashCode());
		result = prime * result + ((previousHash == null) ? 0 : previousHash.hashCode());
		result = prime * result + ((supplierBlockTransactionVo == null) ? 0 : supplierBlockTransactionVo.hashCode());
		result = prime * result + ((supplierBlockUuid == null) ? 0 : supplierBlockUuid.hashCode());
		result = prime * result + ((supplierBlockchainUuid == null) ? 0 : supplierBlockchainUuid.hashCode());
		result = prime * result + ((updateTs == null) ? 0 : updateTs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplierBlockVo other = (SupplierBlockVo) obj;
		if (blockSequence != other.blockSequence)
			return false;
		if (blockTimestamp == null) {
			if (other.blockTimestamp != null)
				return false;
		} else if (!blockTimestamp.equals(other.blockTimestamp))
			return false;
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		if (insertTs == null) {
			if (other.insertTs != null)
				return false;
		} else if (!insertTs.equals(other.insertTs))
			return false;
		if (previousHash == null) {
			if (other.previousHash != null)
				return false;
		} else if (!previousHash.equals(other.previousHash))
			return false;
		if (supplierBlockTransactionVo == null) {
			if (other.supplierBlockTransactionVo != null)
				return false;
		} else if (!supplierBlockTransactionVo.equals(other.supplierBlockTransactionVo))
			return false;
		if (supplierBlockUuid == null) {
			if (other.supplierBlockUuid != null)
				return false;
		} else if (!supplierBlockUuid.equals(other.supplierBlockUuid))
			return false;
		if (supplierBlockchainUuid == null) {
			if (other.supplierBlockchainUuid != null)
				return false;
		} else if (!supplierBlockchainUuid.equals(other.supplierBlockchainUuid))
			return false;
		if (updateTs == null) {
			if (other.updateTs != null)
				return false;
		} else if (!updateTs.equals(other.updateTs))
			return false;
		return true;
	}
}
