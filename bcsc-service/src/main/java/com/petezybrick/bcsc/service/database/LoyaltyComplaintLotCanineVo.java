package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoyaltyComplaintLotCanineVo {
	private static final Logger logger = LogManager.getLogger(LoyaltyComplaintLotCanineVo.class);
	private String customerLoyaltyUuid;
	private String customerUuid;
	private String descText;
	private String lotCanineUuid;
	private String manufacturerLotNumber;
	private Timestamp lotFilledDate;
	private Timestamp insertTs;
	private Timestamp updateTs;


	public LoyaltyComplaintLotCanineVo() {
	}


	public LoyaltyComplaintLotCanineVo(ResultSet rs) throws SQLException {
		this.customerLoyaltyUuid = rs.getString("customer_loyalty_uuid");
		this.customerUuid = rs.getString("customer_uuid");
		this.descText = rs.getString("desc_text");
		this.lotCanineUuid = rs.getString("lot_canine_uuid");
		this.manufacturerLotNumber = rs.getString("manufacturer_lot_number");
		this.lotFilledDate = rs.getTimestamp("lot_filled_date");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getCustomerLoyaltyUuid() {
		return customerLoyaltyUuid;
	}


	public String getCustomerUuid() {
		return customerUuid;
	}


	public String getDescText() {
		return descText;
	}


	public String getLotCanineUuid() {
		return lotCanineUuid;
	}


	public String getManufacturerLotNumber() {
		return manufacturerLotNumber;
	}


	public Timestamp getLotFilledDate() {
		return lotFilledDate;
	}


	public Timestamp getInsertTs() {
		return insertTs;
	}


	public Timestamp getUpdateTs() {
		return updateTs;
	}


	public LoyaltyComplaintLotCanineVo setCustomerLoyaltyUuid(String customerLoyaltyUuid) {
		this.customerLoyaltyUuid = customerLoyaltyUuid;
		return this;
	}


	public LoyaltyComplaintLotCanineVo setCustomerUuid(String customerUuid) {
		this.customerUuid = customerUuid;
		return this;
	}


	public LoyaltyComplaintLotCanineVo setDescText(String descText) {
		this.descText = descText;
		return this;
	}


	public LoyaltyComplaintLotCanineVo setLotCanineUuid(String lotCanineUuid) {
		this.lotCanineUuid = lotCanineUuid;
		return this;
	}


	public LoyaltyComplaintLotCanineVo setManufacturerLotNumber(String manufacturerLotNumber) {
		this.manufacturerLotNumber = manufacturerLotNumber;
		return this;
	}


	public LoyaltyComplaintLotCanineVo setLotFilledDate(Timestamp lotFilledDate) {
		this.lotFilledDate = lotFilledDate;
		return this;
	}


	public LoyaltyComplaintLotCanineVo setInsertTs(Timestamp insertTs) {
		this.insertTs = insertTs;
		return this;
	}


	public LoyaltyComplaintLotCanineVo setUpdateTs(Timestamp updateTs) {
		this.updateTs = updateTs;
		return this;
	}


	@Override
	public String toString() {
		return "LoyaltyComplaintLotCanineVo [customerLoyaltyUuid=" + customerLoyaltyUuid + ", customerUuid=" + customerUuid + ", descText=" + descText
				+ ", lotCanineUuid=" + lotCanineUuid + ", manufacturerLotNumber=" + manufacturerLotNumber + ", lotFilledDate=" + lotFilledDate + ", insertTs="
				+ insertTs + ", updateTs=" + updateTs + "]";
	}

}
