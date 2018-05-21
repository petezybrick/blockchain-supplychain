package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CustomerVo {
	private static final Logger logger = LogManager.getLogger(CustomerVo.class);
	private String customerUuid;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Timestamp insertTs;
	private Timestamp updateTs;


	public CustomerVo() {
	}


	public CustomerVo(ResultSet rs) throws SQLException {
		this.customerUuid = rs.getString("customer_uuid");
		this.firstName = rs.getString("first_name");
		this.lastName = rs.getString("last_name");
		this.emailAddress = rs.getString("email_address");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getCustomerUuid() {
		return this.customerUuid;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public String getEmailAddress() {
		return this.emailAddress;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public CustomerVo setCustomerUuid( String customerUuid ) {
		this.customerUuid = customerUuid;
		return this;
	}
	public CustomerVo setFirstName( String firstName ) {
		this.firstName = firstName;
		return this;
	}
	public CustomerVo setLastName( String lastName ) {
		this.lastName = lastName;
		return this;
	}
	public CustomerVo setEmailAddress( String emailAddress ) {
		this.emailAddress = emailAddress;
		return this;
	}
	public CustomerVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public CustomerVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}
}

// CustomerVo customerVo = new CustomerVo()
//	 .setCustomerUuid("xxx")
//	 .setFirstName("xxx")
//	 .setLastName("xxx")
//	 .setEmailAddress("xxx")
//	 .setInsertTs("xxx")
//	 .setUpdateTs("xxx")
//	 ;
