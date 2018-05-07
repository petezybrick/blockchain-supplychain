package com.petezybrick.bcsc.service.orcdev;


import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplierBlockTransactionVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockTransactionVo.class);
	private String supplierBlockTransactionUuid;
	private String supplierBlockUuid;
	private String transactionId;
	private String encodedPublicKeyFrom;
	private String encodedPublicKeyTo;
	private ByteBuffer signature;
	private Integer transactionSequence;


	public SupplierBlockTransactionVo() {
	}


	public SupplierBlockTransactionVo(ResultSet rs) throws SQLException {
		this.supplierBlockTransactionUuid = rs.getString("supplier_block_transaction_uuid");
		this.supplierBlockUuid = rs.getString("supplier_block_uuid");
		this.transactionId = rs.getString("transaction_id");
		this.encodedPublicKeyFrom = rs.getString("encoded_public_key_from");
		this.encodedPublicKeyTo = rs.getString("encoded_public_key_to");
		this.signature = ByteBuffer.wrap( rs.getBytes("signature") );
		this.transactionSequence = rs.getInt("transaction_sequence");
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
	public ByteBuffer getSignature() {
		return this.signature;
	}
	public Integer getTransactionSequence() {
		return this.transactionSequence;
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
	public SupplierBlockTransactionVo setSignature( ByteBuffer signature ) {
		this.signature = signature;
		return this;
	}
	public SupplierBlockTransactionVo setTransactionSequence( Integer transactionSequence ) {
		this.transactionSequence = transactionSequence;
		return this;
	}
}

// SupplierBlockTransactionVo supplierBlockTransactionVo = new SupplierBlockTransactionVo()
//	 .setSupplierBlockTransactionUuid("xxx")
//	 .setSupplierBlockUuid("xxx")
//	 .setTransactionId("xxx")
//	 .setEncodedPublicKeyFrom("xxx")
//	 .setEncodedPublicKeyTo("xxx")
//	 .setSignature("xxx")
//	 .setTransactionSequence("xxx")
//	 ;
