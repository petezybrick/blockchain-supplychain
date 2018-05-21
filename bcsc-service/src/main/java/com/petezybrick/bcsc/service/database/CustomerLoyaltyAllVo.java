package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CustomerLoyaltyAllVo {
	private static final Logger logger = LogManager.getLogger(CustomerLoyaltyAllVo.class);
	private String customerLoyaltyUuid;
	private String customerUuid;
	private String descType;
	private String descText;
	private String manufacturerLotNumber;
	private String firstName;
	private String lastName;
	private String emailAddress;


	public CustomerLoyaltyAllVo() {
	}


	public CustomerLoyaltyAllVo(ResultSet rs) throws SQLException {
		this.customerLoyaltyUuid = rs.getString("customer_loyalty_uuid");
		this.customerUuid = rs.getString("customer_uuid");
		this.descType = rs.getString("desc_type");
		this.descText = rs.getString("desc_text");
		this.manufacturerLotNumber = rs.getString("manufacturer_lot_number");
		this.firstName = rs.getString("first_name");
		this.lastName = rs.getString("last_name");
		this.emailAddress = rs.getString("email_address");
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


	public CustomerLoyaltyAllVo setCustomerLoyaltyUuid( String customerLoyaltyUuid ) {
		this.customerLoyaltyUuid = customerLoyaltyUuid;
		return this;
	}
	public CustomerLoyaltyAllVo setCustomerUuid( String customerUuid ) {
		this.customerUuid = customerUuid;
		return this;
	}
	public CustomerLoyaltyAllVo setDescType( String descType ) {
		this.descType = descType;
		return this;
	}
	public CustomerLoyaltyAllVo setDescText( String descText ) {
		this.descText = descText;
		return this;
	}
	public CustomerLoyaltyAllVo setManufacturerLotNumber( String manufacturerLotNumber ) {
		this.manufacturerLotNumber = manufacturerLotNumber;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public CustomerLoyaltyAllVo setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}


	public CustomerLoyaltyAllVo setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}


	public CustomerLoyaltyAllVo setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}
}
