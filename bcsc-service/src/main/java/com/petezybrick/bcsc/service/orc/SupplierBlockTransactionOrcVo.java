package com.petezybrick.bcsc.service.orc;


import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.petezybrick.bcsc.service.database.*;


public class SupplierBlockTransactionOrcVo extends BaseOrcVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockTransactionOrcVo.class);
	private String supplierBlockTransactionUuid;
	private String supplierBlockUuid;
	private String transactionId;
	private String encodedPublicKeyFrom;
	private String encodedPublicKeyTo;
	private ByteBuffer signature;
	private Integer transactionSequence;


	public SupplierBlockTransactionOrcVo() {
	}


	public SupplierBlockTransactionOrcVo(ResultSet rs) throws SQLException {
		this.supplierBlockTransactionUuid = rs.getString("supplier_block_transaction_uuid");
		this.supplierBlockUuid = rs.getString("supplier_block_uuid");
		this.transactionId = rs.getString("transaction_id");
		this.encodedPublicKeyFrom = rs.getString("encoded_public_key_from");
		this.encodedPublicKeyTo = rs.getString("encoded_public_key_to");
		this.signature = ByteBuffer.wrap( rs.getBytes("signature") );
		this.transactionSequence = rs.getInt("transaction_sequence");
	}


	public SupplierBlockTransactionOrcVo(SupplierBlockTransactionVo fromVo) throws SQLException {
		this.supplierBlockTransactionUuid = fromVo.getSupplierBlockTransactionUuid();
		this.supplierBlockUuid = fromVo.getSupplierBlockUuid();
		this.transactionId = fromVo.getTransactionId();
		this.encodedPublicKeyFrom = fromVo.getEncodedPublicKeyFrom();
		this.encodedPublicKeyTo = fromVo.getEncodedPublicKeyTo();
		this.signature = ByteBuffer.wrap( fromVo.getSignature() );
		this.transactionSequence = fromVo.getTransactionSequence();
	}


	@Override
	public SupplierBlockTransactionOrcVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
			return new SupplierBlockTransactionOrcVo()
				.setSupplierBlockTransactionUuid((String)objs.get(0))
				.setSupplierBlockUuid((String)objs.get(1))
				.setTransactionId((String)objs.get(2))
				.setEncodedPublicKeyFrom((String)objs.get(3))
				.setEncodedPublicKeyTo((String)objs.get(4))
				.setSignature((ByteBuffer)objs.get(5))
				.setTransactionSequence((Integer)objs.get(6))
				;
		} else throw new Exception("Invalid schema version 1.0");
	}


	@Override
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.supplierBlockTransactionUuid = (String)objs.get(0);
		this.supplierBlockUuid = (String)objs.get(1);
		this.transactionId = (String)objs.get(2);
		this.encodedPublicKeyFrom = (String)objs.get(3);
		this.encodedPublicKeyTo = (String)objs.get(4);
		this.signature = (ByteBuffer)objs.get(5);
		this.transactionSequence = (Integer)objs.get(6);
	}


	@Override
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(supplierBlockTransactionUuid);
		objs.add(supplierBlockUuid);
		objs.add(transactionId);
		objs.add(encodedPublicKeyFrom);
		objs.add(encodedPublicKeyTo);
		objs.add(signature);
		objs.add(transactionSequence);
		return objs;
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


	public SupplierBlockTransactionOrcVo setSupplierBlockTransactionUuid( String supplierBlockTransactionUuid ) {
		this.supplierBlockTransactionUuid = supplierBlockTransactionUuid;
		return this;
	}
	public SupplierBlockTransactionOrcVo setSupplierBlockUuid( String supplierBlockUuid ) {
		this.supplierBlockUuid = supplierBlockUuid;
		return this;
	}
	public SupplierBlockTransactionOrcVo setTransactionId( String transactionId ) {
		this.transactionId = transactionId;
		return this;
	}
	public SupplierBlockTransactionOrcVo setEncodedPublicKeyFrom( String encodedPublicKeyFrom ) {
		this.encodedPublicKeyFrom = encodedPublicKeyFrom;
		return this;
	}
	public SupplierBlockTransactionOrcVo setEncodedPublicKeyTo( String encodedPublicKeyTo ) {
		this.encodedPublicKeyTo = encodedPublicKeyTo;
		return this;
	}
	public SupplierBlockTransactionOrcVo setSignature( ByteBuffer signature ) {
		this.signature = signature;
		return this;
	}
	public SupplierBlockTransactionOrcVo setTransactionSequence( Integer transactionSequence ) {
		this.transactionSequence = transactionSequence;
		return this;
	}
}
