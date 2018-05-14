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


public class SupplierBlockchainOrcVo extends BaseOrcVo {
	private static final Logger logger = LogManager.getLogger(SupplierBlockchainOrcVo.class);
	private String supplierBlockchainUuid;
	private String supplierType;


	public SupplierBlockchainOrcVo() {
	}


	public SupplierBlockchainOrcVo(ResultSet rs) throws SQLException {
		this.supplierBlockchainUuid = rs.getString("supplier_blockchain_uuid");
		this.supplierType = rs.getString("supplier_type");
	}


	public SupplierBlockchainOrcVo(SupplierBlockchainVo fromVo) throws SQLException {
		this.supplierBlockchainUuid = fromVo.getSupplierBlockchainUuid();
		this.supplierType = fromVo.getSupplierType();
	}


	@Override
	public SupplierBlockchainOrcVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
			return new SupplierBlockchainOrcVo()
				.setSupplierBlockchainUuid((String)objs.get(0))
				.setSupplierType((String)objs.get(1))
				;
		} else throw new Exception("Invalid schema version 1.0");
	}


	@Override
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.supplierBlockchainUuid = (String)objs.get(0);
		this.supplierType = (String)objs.get(1);
	}


	@Override
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(supplierBlockchainUuid);
		objs.add(supplierType);
		return objs;
	}


	public String getSupplierBlockchainUuid() {
		return this.supplierBlockchainUuid;
	}
	public String getSupplierType() {
		return this.supplierType;
	}


	public SupplierBlockchainOrcVo setSupplierBlockchainUuid( String supplierBlockchainUuid ) {
		this.supplierBlockchainUuid = supplierBlockchainUuid;
		return this;
	}
	public SupplierBlockchainOrcVo setSupplierType( String supplierType ) {
		this.supplierType = supplierType;
		return this;
	}
}
