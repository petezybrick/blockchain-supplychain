package com.petezybrick.bcsc.service.database;


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
	private Timestamp insertTs;
	private Timestamp updateTs;


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
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
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
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
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
	public SupplierVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public SupplierVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((dunsNumber == null) ? 0 : dunsNumber.hashCode());
		result = prime * result + ((encodedPublicKey == null) ? 0 : encodedPublicKey.hashCode());
		result = prime * result + ((insertTs == null) ? 0 : insertTs.hashCode());
		result = prime * result + ((stateProvince == null) ? 0 : stateProvince.hashCode());
		result = prime * result + ((supplierCategory == null) ? 0 : supplierCategory.hashCode());
		result = prime * result + ((supplierName == null) ? 0 : supplierName.hashCode());
		result = prime * result + ((supplierSubCategory == null) ? 0 : supplierSubCategory.hashCode());
		result = prime * result + ((supplierUuid == null) ? 0 : supplierUuid.hashCode());
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
		SupplierVo other = (SupplierVo) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (dunsNumber == null) {
			if (other.dunsNumber != null)
				return false;
		} else if (!dunsNumber.equals(other.dunsNumber))
			return false;
		if (encodedPublicKey == null) {
			if (other.encodedPublicKey != null)
				return false;
		} else if (!encodedPublicKey.equals(other.encodedPublicKey))
			return false;
		if (insertTs == null) {
			if (other.insertTs != null)
				return false;
		} else if (!insertTs.equals(other.insertTs))
			return false;
		if (stateProvince == null) {
			if (other.stateProvince != null)
				return false;
		} else if (!stateProvince.equals(other.stateProvince))
			return false;
		if (supplierCategory == null) {
			if (other.supplierCategory != null)
				return false;
		} else if (!supplierCategory.equals(other.supplierCategory))
			return false;
		if (supplierName == null) {
			if (other.supplierName != null)
				return false;
		} else if (!supplierName.equals(other.supplierName))
			return false;
		if (supplierSubCategory == null) {
			if (other.supplierSubCategory != null)
				return false;
		} else if (!supplierSubCategory.equals(other.supplierSubCategory))
			return false;
		if (supplierUuid == null) {
			if (other.supplierUuid != null)
				return false;
		} else if (!supplierUuid.equals(other.supplierUuid))
			return false;
		if (updateTs == null) {
			if (other.updateTs != null)
				return false;
		} else if (!updateTs.equals(other.updateTs))
			return false;
		return true;
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
//	 .setInsertTs("xxx")
//	 .setUpdateTs("xxx")
//	 ;
