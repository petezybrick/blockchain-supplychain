package com.petezybrick.bcsc.service.database;

public class LotSupplierBlockVo {
	private Integer blockSequence;
	private String hash;
	private String previousHash;
	private String supplierLotNumber;
	private String supplierCategory;
	private String supplierSubCategory;
	private String supplierName;
	private String dunsNumber;
	private String country;
	private String stateProvince;
	private String supplierBlockchainUuid;
	private String supplierBlockUuid;
	private String supplierBlockTransactionUuid;
	private String supplierUuid;
	
	
	public Integer getBlockSequence() {
		return blockSequence;
	}
	public String getHash() {
		return hash;
	}
	public String getPreviousHash() {
		return previousHash;
	}
	public String getSupplierLotNumber() {
		return supplierLotNumber;
	}
	public String getSupplierCategory() {
		return supplierCategory;
	}
	public String getSupplierSubCategory() {
		return supplierSubCategory;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getDunsNumber() {
		return dunsNumber;
	}
	public LotSupplierBlockVo setBlockSequence(Integer blockSequence) {
		this.blockSequence = blockSequence;
		return this;
	}
	public LotSupplierBlockVo setHash(String hash) {
		this.hash = hash;
		return this;
	}
	public LotSupplierBlockVo setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
		return this;
	}
	public LotSupplierBlockVo setSupplierLotNumber(String supplierLotNumber) {
		this.supplierLotNumber = supplierLotNumber;
		return this;
	}
	public LotSupplierBlockVo setSupplierCategory(String supplierCategory) {
		this.supplierCategory = supplierCategory;
		return this;
	}
	public LotSupplierBlockVo setSupplierSubCategory(String supplierSubCategory) {
		this.supplierSubCategory = supplierSubCategory;
		return this;
	}
	public LotSupplierBlockVo setSupplierName(String supplierName) {
		this.supplierName = supplierName;
		return this;
	}
	public LotSupplierBlockVo setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
		return this;
	}
	@Override
	public String toString() {
		return "LotSupplierBlockVo [blockSequence=" + blockSequence + ", hash=" + hash + ", previousHash=" + previousHash + ", supplierLotNumber="
				+ supplierLotNumber + ", supplierCategory=" + supplierCategory + ", supplierSubCategory=" + supplierSubCategory + ", supplierName="
				+ supplierName + ", dunsNumber=" + dunsNumber + ", country=" + country + ", stateProvince=" + stateProvince + ", supplierBlockchainUuid="
				+ supplierBlockchainUuid + ", supplierBlockUuid=" + supplierBlockUuid + ", supplierBlockTransactionUuid=" + supplierBlockTransactionUuid
				+ ", supplierUuid=" + supplierUuid + "]";
	}
	public String getCountry() {
		return country;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public LotSupplierBlockVo setCountry(String country) {
		this.country = country;
		return this;
	}
	public LotSupplierBlockVo setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
		return this;
	}
	public String getSupplierBlockchainUuid() {
		return supplierBlockchainUuid;
	}
	public String getSupplierBlockUuid() {
		return supplierBlockUuid;
	}
	public String getSupplierBlockTransactionUuid() {
		return supplierBlockTransactionUuid;
	}
	public String getSupplierUuid() {
		return supplierUuid;
	}
	public LotSupplierBlockVo setSupplierBlockchainUuid(String supplierBlockchainUuid) {
		this.supplierBlockchainUuid = supplierBlockchainUuid;
		return this;
	}
	public LotSupplierBlockVo setSupplierBlockUuid(String supplierBlockUuid) {
		this.supplierBlockUuid = supplierBlockUuid;
		return this;
	}
	public LotSupplierBlockVo setSupplierBlockTransactionUuid(String supplierBlockTransactionUuid) {
		this.supplierBlockTransactionUuid = supplierBlockTransactionUuid;
		return this;
	}
	public LotSupplierBlockVo setSupplierUuid(String supplierUuid) {
		this.supplierUuid = supplierUuid;
		return this;
	}
	
}
