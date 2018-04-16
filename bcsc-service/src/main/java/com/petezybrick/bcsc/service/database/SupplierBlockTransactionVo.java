package com.petezybrick.bcsc.service.database;


import java.security.PrivateKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.utils.BlockchainUtils;


public class SupplierBlockTransactionVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockTransactionVo.class);
	private String supplierBlockTransactionUuid;
	private String supplierBlockUuid;
	private String transactionId;
	private String encodedPublicKeyFrom;
	private String encodedPublicKeyTo;
	private byte[] signature;
	private int transactionSequence;
	private Timestamp insertTs;
	private Timestamp updateTs;
	private SupplierTransactionVo supplierTransactionVo;


	public SupplierBlockTransactionVo() {
	}


	// This Calculates the transaction hash (which will be used as its Id)
	public SupplierBlockTransactionVo updateTransactionId() {
		this.transactionId = BlockchainUtils.applySha256(
				encodedPublicKeyFrom + encodedPublicKeyTo + supplierTransactionVo.hashCode() + transactionSequence);
		return this;
	}

	// Signs all the data we dont wish to be tampered with.
	public SupplierBlockTransactionVo generateSignature(PrivateKey privateKey) {
		String data = encodedPublicKeyFrom + encodedPublicKeyTo + supplierTransactionVo.hashCode() + transactionSequence;
		signature = BlockchainUtils.applyECDSASig(privateKey, data);
		return this;
	}

	// Verifies the data we signed hasnt been tampered with
	public boolean verifySignature() throws Exception {
		String data = encodedPublicKeyFrom + encodedPublicKeyTo + supplierTransactionVo.hashCode() + transactionSequence;
		return BlockchainUtils.verifyECDSASig( BlockchainUtils.genPublicKey(encodedPublicKeyFrom), data, signature);
	}

	// Returns true if new transaction could be created.
	public boolean processTransaction() throws Exception {

		if (verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		updateTransactionId();
		return true;
	}

	public SupplierBlockTransactionVo(ResultSet rs) throws SQLException {
		this.supplierBlockTransactionUuid = rs.getString("supplier_block_transaction_uuid");
		this.supplierBlockUuid = rs.getString("supplier_block_uuid");
		this.transactionId = rs.getString("transaction_id");
		this.encodedPublicKeyFrom = rs.getString("encoded_public_key_from");
		this.encodedPublicKeyTo = rs.getString("encoded_public_key_to");
		this.signature = rs.getBytes("signature");
		this.transactionSequence = rs.getInt("transaction_sequence");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getSupplierBlockTransactionUuid() {
		return this.supplierBlockTransactionUuid;
	}
	public String getSupplierBlockUuid() {
		return this.supplierBlockUuid;
	}
	public String getTransactionId() {
		return this.transactionId;
	}
	public String getEncodedPublicKeyFrom() {
		return this.encodedPublicKeyFrom;
	}
	public String getEncodedPublicKeyTo() {
		return this.encodedPublicKeyTo;
	}
	public byte[] getSignature() {
		return this.signature;
	}
	public int getTransactionSequence() {
		return this.transactionSequence;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public SupplierBlockTransactionVo setSupplierBlockTransactionUuid( String supplierBlockTransactionUuid ) {
		this.supplierBlockTransactionUuid = supplierBlockTransactionUuid;
		return this;
	}
	public SupplierBlockTransactionVo setSupplierBlockUuid( String supplierBlockUuid ) {
		this.supplierBlockUuid = supplierBlockUuid;
		return this;
	}
	public SupplierBlockTransactionVo setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
		return this;
	}
	public SupplierBlockTransactionVo setEncodedPublicKeyFrom( String encodedPublicKeyFrom ) {
		this.encodedPublicKeyFrom = encodedPublicKeyFrom;
		return this;
	}
	public SupplierBlockTransactionVo setEncodedPublicKeyTo( String encodedPublicKeyTo ) {
		this.encodedPublicKeyTo = encodedPublicKeyTo;
		return this;
	}
	public SupplierBlockTransactionVo setSignature( byte[] signature ) {
		this.signature = signature;
		return this;
	}
	public SupplierBlockTransactionVo setTransactionSequence( int transactionSequence ) {
		this.transactionSequence = transactionSequence;
		return this;
	}
	public SupplierBlockTransactionVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public SupplierBlockTransactionVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}


	public SupplierTransactionVo getSupplierTransactionVo() {
		return supplierTransactionVo;
	}


	public SupplierBlockTransactionVo setSupplierTransactionVo(SupplierTransactionVo supplierTransactionVo) {
		this.supplierTransactionVo = supplierTransactionVo;
		return this;
	}


	@Override
	public String toString() {
		return "SupplierBlockTransactionVo [supplierBlockTransactionUuid=" + supplierBlockTransactionUuid + ", supplierBlockUuid=" + supplierBlockUuid
				+ ", transactionId=" + transactionId + ", encodedPublicKeyFrom=" + encodedPublicKeyFrom + ", encodedPublicKeyTo=" + encodedPublicKeyTo
				+ ", signature=" + Arrays.toString(signature) + ", transactionSequence=" + transactionSequence + ", insertTs=" + insertTs + ", updateTs="
				+ updateTs + ", supplierTransactionVo=" + supplierTransactionVo + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((encodedPublicKeyFrom == null) ? 0 : encodedPublicKeyFrom.hashCode());
		result = prime * result + ((encodedPublicKeyTo == null) ? 0 : encodedPublicKeyTo.hashCode());
		result = prime * result + ((insertTs == null) ? 0 : insertTs.hashCode());
		result = prime * result + Arrays.hashCode(signature);
		result = prime * result + ((supplierBlockTransactionUuid == null) ? 0 : supplierBlockTransactionUuid.hashCode());
		result = prime * result + ((supplierBlockUuid == null) ? 0 : supplierBlockUuid.hashCode());
		result = prime * result + ((supplierTransactionVo == null) ? 0 : supplierTransactionVo.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + transactionSequence;
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
		SupplierBlockTransactionVo other = (SupplierBlockTransactionVo) obj;
		if (encodedPublicKeyFrom == null) {
			if (other.encodedPublicKeyFrom != null)
				return false;
		} else if (!encodedPublicKeyFrom.equals(other.encodedPublicKeyFrom))
			return false;
		if (encodedPublicKeyTo == null) {
			if (other.encodedPublicKeyTo != null)
				return false;
		} else if (!encodedPublicKeyTo.equals(other.encodedPublicKeyTo))
			return false;
		if (insertTs == null) {
			if (other.insertTs != null)
				return false;
		} else if (!insertTs.equals(other.insertTs))
			return false;
		if (!Arrays.equals(signature, other.signature))
			return false;
		if (supplierBlockTransactionUuid == null) {
			if (other.supplierBlockTransactionUuid != null)
				return false;
		} else if (!supplierBlockTransactionUuid.equals(other.supplierBlockTransactionUuid))
			return false;
		if (supplierBlockUuid == null) {
			if (other.supplierBlockUuid != null)
				return false;
		} else if (!supplierBlockUuid.equals(other.supplierBlockUuid))
			return false;
		if (supplierTransactionVo == null) {
			if (other.supplierTransactionVo != null)
				return false;
		} else if (!supplierTransactionVo.equals(other.supplierTransactionVo))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		if (transactionSequence != other.transactionSequence)
			return false;
		if (updateTs == null) {
			if (other.updateTs != null)
				return false;
		} else if (!updateTs.equals(other.updateTs))
			return false;
		return true;
	}
}

