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


public class CustomerOrcVo extends BaseOrcVo {
	private static final Logger logger = LogManager.getLogger(CustomerOrcVo.class);
	private String customerUuid;
	private String firstName;
	private String lastName;
	private String emailAddress;


	public CustomerOrcVo() {
	}


	public CustomerOrcVo(ResultSet rs) throws SQLException {
		this.customerUuid = rs.getString("customer_uuid");
		this.firstName = rs.getString("first_name");
		this.lastName = rs.getString("last_name");
		this.emailAddress = rs.getString("email_address");
	}


	public CustomerOrcVo(CustomerVo fromVo) throws SQLException {
		this.customerUuid = fromVo.getCustomerUuid();
		this.firstName = fromVo.getFirstName();
		this.lastName = fromVo.getLastName();
		this.emailAddress = fromVo.getEmailAddress();
	}


	@Override
	public CustomerOrcVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
			return new CustomerOrcVo()
				.setCustomerUuid((String)objs.get(0))
				.setFirstName((String)objs.get(1))
				.setLastName((String)objs.get(2))
				.setEmailAddress((String)objs.get(3))
				;
		} else throw new Exception("Invalid schema version 1.0");
	}


	@Override
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.customerUuid = (String)objs.get(0);
		this.firstName = (String)objs.get(1);
		this.lastName = (String)objs.get(2);
		this.emailAddress = (String)objs.get(3);
	}


	@Override
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(customerUuid);
		objs.add(firstName);
		objs.add(lastName);
		objs.add(emailAddress);
		return objs;
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


	public CustomerOrcVo setCustomerUuid( String customerUuid ) {
		this.customerUuid = customerUuid;
		return this;
	}
	public CustomerOrcVo setFirstName( String firstName ) {
		this.firstName = firstName;
		return this;
	}
	public CustomerOrcVo setLastName( String lastName ) {
		this.lastName = lastName;
		return this;
	}
	public CustomerOrcVo setEmailAddress( String emailAddress ) {
		this.emailAddress = emailAddress;
		return this;
	}
}
