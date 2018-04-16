package com.petezybrick.bcsc.service.item;

public class LotSupplierBlockItem {
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
	public LotSupplierBlockItem setBlockSequence(Integer blockSequence) {
		this.blockSequence = blockSequence;
		return this;
	}
	public LotSupplierBlockItem setHash(String hash) {
		this.hash = hash;
		return this;
	}
	public LotSupplierBlockItem setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
		return this;
	}
	public LotSupplierBlockItem setSupplierLotNumber(String supplierLotNumber) {
		this.supplierLotNumber = supplierLotNumber;
		return this;
	}
	public LotSupplierBlockItem setSupplierCategory(String supplierCategory) {
		this.supplierCategory = supplierCategory;
		return this;
	}
	public LotSupplierBlockItem setSupplierSubCategory(String supplierSubCategory) {
		this.supplierSubCategory = supplierSubCategory;
		return this;
	}
	public LotSupplierBlockItem setSupplierName(String supplierName) {
		this.supplierName = supplierName;
		return this;
	}
	public LotSupplierBlockItem setDunsNumber(String dunsNumber) {
		this.dunsNumber = dunsNumber;
		return this;
	}
	@Override
	public String toString() {
		return "LotSupplierBlockItem [blockSequence=" + blockSequence + ", hash=" + hash + ", previousHash=" + previousHash + ", supplierLotNumber="
				+ supplierLotNumber + ", supplierCategory=" + supplierCategory + ", supplierSubCategory=" + supplierSubCategory + ", supplierName="
				+ supplierName + ", dunsNumber=" + dunsNumber + ", country=" + country + ", stateProvince=" + stateProvince + "]";
	}
	public String getCountry() {
		return country;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public LotSupplierBlockItem setCountry(String country) {
		this.country = country;
		return this;
	}
	public LotSupplierBlockItem setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
		return this;
	}

	
}
