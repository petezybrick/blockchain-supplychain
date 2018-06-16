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


public class CustomerLoyaltyOrcVo extends BaseOrcVo {
	private static final Logger logger = LogManager.getLogger(CustomerLoyaltyOrcVo.class);
	private String customerLoyaltyUuid;
	private String customerUuid;
	private String descType;
	private String descText;
	private String manufacturerLotNumber;


	public CustomerLoyaltyOrcVo() {
	}


	public CustomerLoyaltyOrcVo(ResultSet rs) throws SQLException {
		this.customerLoyaltyUuid = rs.getString("customer_loyalty_uuid");
		this.customerUuid = rs.getString("customer_uuid");
		this.descType = rs.getString("desc_type");
		this.descText = rs.getString("desc_text");
		this.manufacturerLotNumber = rs.getString("manufacturer_lot_number");
	}


	public CustomerLoyaltyOrcVo(CustomerLoyaltyVo fromVo) throws SQLException {
		this.customerLoyaltyUuid = fromVo.getCustomerLoyaltyUuid();
		this.customerUuid = fromVo.getCustomerUuid();
		this.descType = fromVo.getDescType();
		this.descText = fromVo.getDescText();
		this.manufacturerLotNumber = fromVo.getManufacturerLotNumber();
	}


	@Override
	public CustomerLoyaltyOrcVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
			return new CustomerLoyaltyOrcVo()
				.setCustomerLoyaltyUuid((String)objs.get(0))
				.setCustomerUuid((String)objs.get(1))
				.setDescType((String)objs.get(2))
				.setDescText((String)objs.get(3))
				.setManufacturerLotNumber((String)objs.get(4))
				;
		} else throw new Exception("Invalid schema version 1.0");
	}


	@Override
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.customerLoyaltyUuid = (String)objs.get(0);
		this.customerUuid = (String)objs.get(1);
		this.descType = (String)objs.get(2);
		this.descText = (String)objs.get(3);
		this.manufacturerLotNumber = (String)objs.get(4);
	}


	@Override
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(customerLoyaltyUuid);
		objs.add(customerUuid);
		objs.add(descType);
		objs.add(descText);
		objs.add(manufacturerLotNumber);
		return objs;
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


	public CustomerLoyaltyOrcVo setCustomerLoyaltyUuid( String customerLoyaltyUuid ) {
		this.customerLoyaltyUuid = customerLoyaltyUuid;
		return this;
	}
	public CustomerLoyaltyOrcVo setCustomerUuid( String customerUuid ) {
		this.customerUuid = customerUuid;
		return this;
	}
	public CustomerLoyaltyOrcVo setDescType( String descType ) {
		this.descType = descType;
		return this;
	}
	public CustomerLoyaltyOrcVo setDescText( String descText ) {
		this.descText = descText;
		return this;
	}
	public CustomerLoyaltyOrcVo setManufacturerLotNumber( String manufacturerLotNumber ) {
		this.manufacturerLotNumber = manufacturerLotNumber;
		return this;
	}
}
