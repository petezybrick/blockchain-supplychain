package com.petezybrick.bcsc.service.orcdev;


import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplierBlockchainVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockchainVo.class);
	private String supplierBlockchainUuid;
	private String supplierType;


	public SupplierBlockchainVo() {
	}


	public SupplierBlockchainVo(ResultSet rs) throws SQLException {
		this.supplierBlockchainUuid = rs.getString("supplier_blockchain_uuid");
		this.supplierType = rs.getString("supplier_type");
	}


	public String getSupplierBlockchainUuid() {
		return this.supplierBlockchainUuid;
	}
	public String getSupplierType() {
		return this.supplierType;
	}


	public SupplierBlockchainVo setSupplierBlockchainUuid( String supplierBlockchainUuid ) {
		this.supplierBlockchainUuid = supplierBlockchainUuid;
		return this;
	}
	public SupplierBlockchainVo setSupplierType( String supplierType ) {
		this.supplierType = supplierType;
		return this;
	}
}

// SupplierBlockchainVo supplierBlockchainVo = new SupplierBlockchainVo()
//	 .setSupplierBlockchainUuid("xxx")
//	 .setSupplierType("xxx")
//	 ;
