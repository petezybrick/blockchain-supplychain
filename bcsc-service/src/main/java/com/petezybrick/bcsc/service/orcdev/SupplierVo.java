package com.petezybrick.bcsc.service.orcdev;


import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplierVo {
	private static final Logger logger = LogManager.getLogger(SupplierVo.class);
	private String supplierUuid;
	private String dunsNumber;
	private String supplierName;
	private String supplierCategory;
	private String supplierSubCategory;
	private String stateProvince;
	private String country;
	private String encodedPublicKey;


	public SupplierVo() {
	}


	public SupplierVo(ResultSet rs) throws SQLException {
		this.supplierUuid = rs.getString("supplier_uuid");
		this.dunsNumber = rs.getString("duns_number");
		this.supplierName = rs.getString("supplier_name");
		this.supplierCategory = rs.getString("supplier_category");
		this.supplierSubCategory = rs.getString("supplier_sub_category");
		this.stateProvince = rs.getString("state_province");
		this.country = rs.getString("country");
		this.encodedPublicKey = rs.getString("encoded_public_key");
	}


	public String getSupplierUuid() {
		return this.supplierUuid;
	}
	public String getDunsNumber() {
		return this.dunsNumber;
	}
	public String getSupplierName() {
		return this.supplierName;
	}
	public String getSupplierCategory() {
		return this.supplierCategory;
	}
	public String getSupplierSubCategory() {
		return this.supplierSubCategory;
	}
	public String getStateProvince() {
		return this.stateProvince;
	}
	public String getCountry() {
		return this.country;
	}
	public String getEncodedPublicKey() {
		return this.encodedPublicKey;
	}


	public SupplierVo setSupplierUuid( String supplierUuid ) {
		this.supplierUuid = supplierUuid;
		return this;
	}
	public SupplierVo setDunsNumber( String dunsNumber ) {
		this.dunsNumber = dunsNumber;
		return this;
	}
	public SupplierVo setSupplierName( String supplierName ) {
		this.supplierName = supplierName;
		return this;
	}
	public SupplierVo setSupplierCategory( String supplierCategory ) {
		this.supplierCategory = supplierCategory;
		return this;
	}
	public SupplierVo setSupplierSubCategory( String supplierSubCategory ) {
		this.supplierSubCategory = supplierSubCategory;
		return this;
	}
	public SupplierVo setStateProvince( String stateProvince ) {
		this.stateProvince = stateProvince;
		return this;
	}
	public SupplierVo setCountry( String country ) {
		this.country = country;
		return this;
	}
	public SupplierVo setEncodedPublicKey( String encodedPublicKey ) {
		this.encodedPublicKey = encodedPublicKey;
		return this;
	}
}

// SupplierVo supplierVo = new SupplierVo()
//	 .setSupplierUuid("xxx")
//	 .setDunsNumber("xxx")
//	 .setSupplierName("xxx")
//	 .setSupplierCategory("xxx")
//	 .setSupplierSubCategory("xxx")
//	 .setStateProvince("xxx")
//	 .setCountry("xxx")
//	 .setEncodedPublicKey("xxx")
//	 ;
