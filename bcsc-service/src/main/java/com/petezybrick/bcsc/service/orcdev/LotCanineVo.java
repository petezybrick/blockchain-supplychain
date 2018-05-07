package com.petezybrick.bcsc.service.orcdev;


import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LotCanineVo {
	private static final Logger logger = LogManager.getLogger(LotCanineVo.class);
	private String lotCanineUuid;
	private String manufacturerLotNumber;
	private Timestamp lotFilledDate;


	public LotCanineVo() {
	}


	public LotCanineVo(ResultSet rs) throws SQLException {
		this.lotCanineUuid = rs.getString("lot_canine_uuid");
		this.manufacturerLotNumber = rs.getString("manufacturer_lot_number");
		this.lotFilledDate = rs.getTimestamp("lot_filled_date");
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
}

// LotCanineVo lotCanineVo = new LotCanineVo()
//	 .setLotCanineUuid("xxx")
//	 .setManufacturerLotNumber("xxx")
//	 .setLotFilledDate("xxx")
//	 ;
