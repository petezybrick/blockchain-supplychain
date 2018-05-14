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


public class LotCanineOrcVo extends BaseOrcVo {
	private static final Logger logger = LogManager.getLogger(LotCanineOrcVo.class);
	private String lotCanineUuid;
	private String manufacturerLotNumber;
	private Timestamp lotFilledDate;


	public LotCanineOrcVo() {
	}


	public LotCanineOrcVo(ResultSet rs) throws SQLException {
		this.lotCanineUuid = rs.getString("lot_canine_uuid");
		this.manufacturerLotNumber = rs.getString("manufacturer_lot_number");
		this.lotFilledDate = rs.getTimestamp("lot_filled_date");
	}


	public LotCanineOrcVo(LotCanineVo fromVo) throws SQLException {
		this.lotCanineUuid = fromVo.getLotCanineUuid();
		this.manufacturerLotNumber = fromVo.getManufacturerLotNumber();
		this.lotFilledDate = fromVo.getLotFilledDate();
	}


	@Override
	public LotCanineOrcVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
			return new LotCanineOrcVo()
				.setLotCanineUuid((String)objs.get(0))
				.setManufacturerLotNumber((String)objs.get(1))
				.setLotFilledDate((Timestamp)objs.get(2))
				;
		} else throw new Exception("Invalid schema version 1.0");
	}


	@Override
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.lotCanineUuid = (String)objs.get(0);
		this.manufacturerLotNumber = (String)objs.get(1);
		this.lotFilledDate = (Timestamp)objs.get(2);
	}


	@Override
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(lotCanineUuid);
		objs.add(manufacturerLotNumber);
		objs.add(lotFilledDate);
		return objs;
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


	public LotCanineOrcVo setLotCanineUuid( String lotCanineUuid ) {
		this.lotCanineUuid = lotCanineUuid;
		return this;
	}
	public LotCanineOrcVo setManufacturerLotNumber( String manufacturerLotNumber ) {
		this.manufacturerLotNumber = manufacturerLotNumber;
		return this;
	}
	public LotCanineOrcVo setLotFilledDate( Timestamp lotFilledDate ) {
		this.lotFilledDate = lotFilledDate;
		return this;
	}
}
