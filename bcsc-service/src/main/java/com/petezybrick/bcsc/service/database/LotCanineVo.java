package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LotCanineVo {
	private static final Logger logger = LogManager.getLogger(LotCanineVo.class);
	private String lotCanineUuid;
	private String manufacturerLotNumber;
	private Timestamp lotFilledDate;
	private Timestamp insertTs;
	private Timestamp updateTs;
	private List<MapLotCanineSupplierBlockchainVo> mapLotCanineSupplierBlockchainVos;


	public LotCanineVo() {
	}


	public LotCanineVo(ResultSet rs) throws SQLException {
		this.lotCanineUuid = rs.getString("lot_canine_uuid");
		this.manufacturerLotNumber = rs.getString("manufacturer_lot_number");
		this.lotFilledDate = rs.getTimestamp("lot_filled_date");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getLotCanineUuid() {
		return this.lotCanineUuid;
	}
	public String getManufacturerLotNumber() {
		return this.manufacturerLotNumber;
	}
	public Timestamp getLotFilledDate() {
		return this.lotFilledDate;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public LotCanineVo setLotCanineUuid( String lotCanineUuid ) {
		this.lotCanineUuid = lotCanineUuid;
		return this;
	}
	public LotCanineVo setManufacturerLotNumber( String manufacturerLotNumber ) {
		this.manufacturerLotNumber = manufacturerLotNumber;
		return this;
	}
	public LotCanineVo setLotFilledDate( Timestamp lotFilledDate ) {
		this.lotFilledDate = lotFilledDate;
		return this;
	}
	public LotCanineVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public LotCanineVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}


	public List<MapLotCanineSupplierBlockchainVo> getMapLotCanineSupplierBlockchainVos() {
		return mapLotCanineSupplierBlockchainVos;
	}


	public LotCanineVo setMapLotCanineSupplierBlockchainVos(List<MapLotCanineSupplierBlockchainVo> mapLotCanineSupplierBlockchainVos) {
		this.mapLotCanineSupplierBlockchainVos = mapLotCanineSupplierBlockchainVos;
		return this;
	}


	@Override
	public String toString() {
		return "LotCanineVo [lotCanineUuid=" + lotCanineUuid + ", manufacturerLotNumber=" + manufacturerLotNumber + ", lotFilledDate=" + lotFilledDate
				+ ", insertTs=" + insertTs + ", updateTs=" + updateTs + ", mapLotCanineSupplierBlockchainVos=" + mapLotCanineSupplierBlockchainVos + "]";
	}
}
