package com.petezybrick.bcsc.service.orc;


import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplierVo extends BaseOrcVo {
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


	@Override
	public SupplierVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
			return new SupplierVo()
				.setSupplierUuid((String)objs.get(0))
				.setDunsNumber((String)objs.get(1))
				.setSupplierName((String)objs.get(2))
				.setSupplierCategory((String)objs.get(3))
				.setSupplierSubCategory((String)objs.get(4))
				.setStateProvince((String)objs.get(5))
				.setCountry((String)objs.get(6))
				.setEncodedPublicKey((String)objs.get(7))
				;
		} else throw new Exception("Invalid schema version 1.0");
	}


	@Override
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.supplierUuid = (String)objs.get(0);
		this.dunsNumber = (String)objs.get(1);
		this.supplierName = (String)objs.get(2);
		this.supplierCategory = (String)objs.get(3);
		this.supplierSubCategory = (String)objs.get(4);
		this.stateProvince = (String)objs.get(5);
		this.country = (String)objs.get(6);
		this.encodedPublicKey = (String)objs.get(7);
	}


	@Override
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(supplierUuid);
		objs.add(dunsNumber);
		objs.add(supplierName);
		objs.add(supplierCategory);
		objs.add(supplierSubCategory);
		objs.add(stateProvince);
		objs.add(country);
		objs.add(encodedPublicKey);
		return objs;
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
