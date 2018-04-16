package com.petezybrick.bcsc.common.core;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.petezybrick.bcsc.common.utils.BlockchainUtils;

public class SupplierBlockTransaction {
	private String supplierBlockTransactionUuid;
	private String supplierBlockUuid;
	private String transactionId;
	private PublicKey publicKeyFrom;
	private PublicKey publicKeyTo;
	private SupplierTransaction supplierTransaction;
	private byte[] signature;
	private int transactionSequence = 0;

	// Constructor:
	public SupplierBlockTransaction( ) {
//		this.publicKeyFrom = publicKeyFrom;
//		this.publicKeyTo = publicKeyTo;
//		this.supplierTransaction = supplierTransaction;
//		this.transactionSequence = transactionSequence;
//		transactionId = calculateHash();
//		this.generateSignature( privateKeyFrom );
	}

	// This Calculates the transaction hash (which will be used as its Id)
	public SupplierBlockTransaction updateTransactionId() {
		this.transactionId = BlockchainUtils.applySha256(BlockchainUtils.getEncodedStringFromKey(publicKeyFrom)
				+ BlockchainUtils.getEncodedStringFromKey(publicKeyTo) + supplierTransaction.hashCode() + transactionSequence);
		return this;
	}

	// Signs all the data we dont wish to be tampered with.
	public SupplierBlockTransaction generateSignature(PrivateKey privateKey) {
		String data = BlockchainUtils.getEncodedStringFromKey(publicKeyFrom) + BlockchainUtils.getEncodedStringFromKey(publicKeyTo)
				+ supplierTransaction;
		signature = BlockchainUtils.applyECDSASig(privateKey, data);
		return this;
	}

	// Verifies the data we signed hasnt been tampered with
	public boolean verifySignature() {
		String data = BlockchainUtils.getEncodedStringFromKey(publicKeyFrom) + BlockchainUtils.getEncodedStringFromKey(publicKeyTo)
				+ supplierTransaction;
		return BlockchainUtils.verifyECDSASig(publicKeyFrom, data, signature);
	}

	// Returns true if new transaction could be created.
	public boolean processTransaction() {

		if (verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		updateTransactionId();
		return true;
	}

	@Override
	public String toString() {
		return "SupplyChainTransaction [transactionId=" + transactionId + ", supplierTransaction=" + supplierTransaction + ", sequence=" + transactionSequence + "]";
	}

	public String getSupplierBlockTransactionUuid() {
		return supplierBlockTransactionUuid;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public PublicKey getPublicKeyFrom() {
		return publicKeyFrom;
	}

	public PublicKey getPublicKeyTo() {
		return publicKeyTo;
	}

	public SupplierTransaction getSupplierTransaction() {
		return supplierTransaction;
	}

	public byte[] getSignature() {
		return signature;
	}

	public int getTransactionSequence() {
		return transactionSequence;
	}

	public SupplierBlockTransaction setSupplierBlockTransactionUuid(String supplierBlockTransactionUuid) {
		this.supplierBlockTransactionUuid = supplierBlockTransactionUuid;
		return this;
	}

	public SupplierBlockTransaction setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

	public SupplierBlockTransaction setPublicKeyFrom(PublicKey publicKeyFrom) {
		this.publicKeyFrom = publicKeyFrom;
		return this;
	}

	public SupplierBlockTransaction setPublicKeyTo(PublicKey publicKeyTo) {
		this.publicKeyTo = publicKeyTo;
		return this;
	}

	public SupplierBlockTransaction setSupplierTransaction(SupplierTransaction supplierTransaction) {
		this.supplierTransaction = supplierTransaction;
		return this;
	}

	public SupplierBlockTransaction setSignature(byte[] signature) {
		this.signature = signature;
		return this;
	}

	public SupplierBlockTransaction setTransactionSequence(int transactionSequence) {
		this.transactionSequence = transactionSequence;
		return this;
	}



}