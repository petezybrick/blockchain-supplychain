package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplierBlockchainVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockchainVo.class);
	private String supplierBlockchainUuid;
	private String supplierType;
	private Timestamp insertTs;
	private Timestamp updateTs;
	private List<SupplierBlockVo> supplierBlockVos;


	public SupplierBlockchainVo() {
	}


	public SupplierBlockchainVo(ResultSet rs) throws SQLException {
		this.supplierBlockchainUuid = rs.getString("supplier_blockchain_uuid");
		this.supplierType = rs.getString("supplier_type");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getSupplierBlockchainUuid() {
		return this.supplierBlockchainUuid;
	}
	public String getSupplierType() {
		return this.supplierType;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public SupplierBlockchainVo setSupplierBlockchainUuid( String supplierBlockchainUuid ) {
		this.supplierBlockchainUuid = supplierBlockchainUuid;
		return this;
	}
	public SupplierBlockchainVo setSupplierType( String supplierType ) {
		this.supplierType = supplierType;
		return this;
	}
	public SupplierBlockchainVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public SupplierBlockchainVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}


	public List<SupplierBlockVo> getSupplierBlockVos() {
		return supplierBlockVos;
	}


	public SupplierBlockchainVo setSupplierBlockVos(List<SupplierBlockVo> supplierBlockVos) {
		this.supplierBlockVos = supplierBlockVos;
		return this;
	}


	@Override
	public String toString() {
		return "SupplierBlockchainVo [supplierBlockchainUuid=" + supplierBlockchainUuid + ", supplierType=" + supplierType + ", insertTs=" + insertTs
				+ ", updateTs=" + updateTs + ", supplierBlockVos=" + supplierBlockVos + "]";
	}


}
