package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CustomerLoyaltyVo {
	private static final Logger logger = LogManager.getLogger(CustomerLoyaltyVo.class);
	private String customerLoyaltyUuid;
	private String customerUuid;
	private String descType;
	private String descText;
	private String manufacturerLotNumber;
	private Timestamp insertTs;
	private Timestamp updateTs;


	public CustomerLoyaltyVo() {
	}


	public CustomerLoyaltyVo(ResultSet rs) throws SQLException {
		this.customerLoyaltyUuid = rs.getString("customer_loyalty_uuid");
		this.customerUuid = rs.getString("customer_uuid");
		this.descType = rs.getString("desc_type");
		this.descText = rs.getString("desc_text");
		this.manufacturerLotNumber = rs.getString("manufacturer_lot_number");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getCustomerLoyaltyUuid() {
		return this.customerLoyaltyUuid;
	}
	public String getCustomerUuid() {
		return this.customerUuid;
	}
	public String getDescType() {
		return this.descType;
	}
	public String getDescText() {
		return this.descText;
	}
	public String getManufacturerLotNumber() {
		return this.manufacturerLotNumber;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public CustomerLoyaltyVo setCustomerLoyaltyUuid( String customerLoyaltyUuid ) {
		this.customerLoyaltyUuid = customerLoyaltyUuid;
		return this;
	}
	public CustomerLoyaltyVo setCustomerUuid( String customerUuid ) {
		this.customerUuid = customerUuid;
		return this;
	}
	public CustomerLoyaltyVo setDescType( String descType ) {
		this.descType = descType;
		return this;
	}
	public CustomerLoyaltyVo setDescText( String descText ) {
		this.descText = descText;
		return this;
	}
	public CustomerLoyaltyVo setManufacturerLotNumber( String manufacturerLotNumber ) {
		this.manufacturerLotNumber = manufacturerLotNumber;
		return this;
	}
	public CustomerLoyaltyVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public CustomerLoyaltyVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}
}

// CustomerLoyaltyVo customerLoyaltyVo = new CustomerLoyaltyVo()
//	 .setCustomerLoyaltyUuid("xxx")
//	 .setCustomerUuid("xxx")
//	 .setDescType("xxx")
//	 .setDescText("xxx")
//	 .setManufacturerLotNumber("xxx")
//	 .setInsertTs("xxx")
//	 .setUpdateTs("xxx")
//	 ;
