package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SupplierTransactionVo {
	private static final Logger logger = LogManager.getLogger(SupplierTransactionVo.class);
	private String supplierTransactionUuid;
	private String supplierBlockTransactionUuid;
	private String supplierUuid;
	private String supplierLotNumber;
	private String itemNumber;
	private String description;
	private int qty;
	private String units;
	private Timestamp shippedDateIso8601;
	private Timestamp rcvdDateIso8601;
	private Timestamp insertTs;
	private Timestamp updateTs;


	public SupplierTransactionVo() {
	}


	public SupplierTransactionVo(ResultSet rs) throws SQLException {
		this.supplierTransactionUuid = rs.getString("supplier_transaction_uuid");
		this.supplierBlockTransactionUuid = rs.getString("supplier_block_transaction_uuid");
		this.supplierUuid = rs.getString("supplier_uuid");
		this.supplierLotNumber = rs.getString("supplier_lot_number");
		this.itemNumber = rs.getString("item_number");
		this.description = rs.getString("description");
		this.qty = rs.getInt("qty");
		this.units = rs.getString("units");
		this.shippedDateIso8601 = rs.getTimestamp("shipped_date_iso8601");
		this.rcvdDateIso8601 = rs.getTimestamp("rcvd_date_iso8601");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getSupplierTransactionUuid() {
		return this.supplierTransactionUuid;
	}
	public String getSupplierBlockTransactionUuid() {
		return this.supplierBlockTransactionUuid;
	}
	public String getSupplierUuid() {
		return this.supplierUuid;
	}
	public String getSupplierLotNumber() {
		return this.supplierLotNumber;
	}
	public String getItemNumber() {
		return this.itemNumber;
	}
	public String getDescription() {
		return this.description;
	}
	public int getQty() {
		return this.qty;
	}
	public String getUnits() {
		return this.units;
	}
	public Timestamp getShippedDateIso8601() {
		return this.shippedDateIso8601;
	}
	public Timestamp getRcvdDateIso8601() {
		return this.rcvdDateIso8601;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public SupplierTransactionVo setSupplierTransactionUuid( String supplierTransactionUuid ) {
		this.supplierTransactionUuid = supplierTransactionUuid;
		return this;
	}
	public SupplierTransactionVo setSupplierBlockTransactionUuid( String supplierBlockTransactionUuid ) {
		this.supplierBlockTransactionUuid = supplierBlockTransactionUuid;
		return this;
	}
	public SupplierTransactionVo setSupplierUuid( String supplierUuid ) {
		this.supplierUuid = supplierUuid;
		return this;
	}
	public SupplierTransactionVo setSupplierLotNumber( String supplierLotNumber ) {
		this.supplierLotNumber = supplierLotNumber;
		return this;
	}
	public SupplierTransactionVo setItemNumber( String itemNumber ) {
		this.itemNumber = itemNumber;
		return this;
	}
	public SupplierTransactionVo setDescription( String description ) {
		this.description = description;
		return this;
	}
	public SupplierTransactionVo setQty( int qty ) {
		this.qty = qty;
		return this;
	}
	public SupplierTransactionVo setUnits( String units ) {
		this.units = units;
		return this;
	}
	public SupplierTransactionVo setShippedDateIso8601( Timestamp shippedDateIso8601 ) {
		this.shippedDateIso8601 = shippedDateIso8601;
		return this;
	}
	public SupplierTransactionVo setRcvdDateIso8601( Timestamp rcvdDateIso8601 ) {
		this.rcvdDateIso8601 = rcvdDateIso8601;
		return this;
	}
	public SupplierTransactionVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public SupplierTransactionVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((insertTs == null) ? 0 : insertTs.hashCode());
		result = prime * result + ((itemNumber == null) ? 0 : itemNumber.hashCode());
		result = prime * result + qty;
		result = prime * result + ((rcvdDateIso8601 == null) ? 0 : rcvdDateIso8601.hashCode());
		result = prime * result + ((shippedDateIso8601 == null) ? 0 : shippedDateIso8601.hashCode());
		result = prime * result + ((supplierBlockTransactionUuid == null) ? 0 : supplierBlockTransactionUuid.hashCode());
		result = prime * result + ((supplierLotNumber == null) ? 0 : supplierLotNumber.hashCode());
		result = prime * result + ((supplierTransactionUuid == null) ? 0 : supplierTransactionUuid.hashCode());
		result = prime * result + ((supplierUuid == null) ? 0 : supplierUuid.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		result = prime * result + ((updateTs == null) ? 0 : updateTs.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupplierTransactionVo other = (SupplierTransactionVo) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (insertTs == null) {
			if (other.insertTs != null)
				return false;
		} else if (!insertTs.equals(other.insertTs))
			return false;
		if (itemNumber == null) {
			if (other.itemNumber != null)
				return false;
		} else if (!itemNumber.equals(other.itemNumber))
			return false;
		if (qty != other.qty)
			return false;
		if (rcvdDateIso8601 == null) {
			if (other.rcvdDateIso8601 != null)
				return false;
		} else if (!rcvdDateIso8601.equals(other.rcvdDateIso8601))
			return false;
		if (shippedDateIso8601 == null) {
			if (other.shippedDateIso8601 != null)
				return false;
		} else if (!shippedDateIso8601.equals(other.shippedDateIso8601))
			return false;
		if (supplierBlockTransactionUuid == null) {
			if (other.supplierBlockTransactionUuid != null)
				return false;
		} else if (!supplierBlockTransactionUuid.equals(other.supplierBlockTransactionUuid))
			return false;
		if (supplierLotNumber == null) {
			if (other.supplierLotNumber != null)
				return false;
		} else if (!supplierLotNumber.equals(other.supplierLotNumber))
			return false;
		if (supplierTransactionUuid == null) {
			if (other.supplierTransactionUuid != null)
				return false;
		} else if (!supplierTransactionUuid.equals(other.supplierTransactionUuid))
			return false;
		if (supplierUuid == null) {
			if (other.supplierUuid != null)
				return false;
		} else if (!supplierUuid.equals(other.supplierUuid))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		if (updateTs == null) {
			if (other.updateTs != null)
				return false;
		} else if (!updateTs.equals(other.updateTs))
			return false;
		return true;
	}
}

// SupplierTransactionVo supplierTransactionVo = new SupplierTransactionVo()
//	 .setSupplierTransactionUuid("xxx")
//	 .setSupplierBlockTransactionUuid("xxx")
//	 .setSupplierUuid("xxx")
//	 .setSupplierLotNumber("xxx")
//	 .setItemNumber("xxx")
//	 .setDescription("xxx")
//	 .setQty("xxx")
//	 .setUnits("xxx")
//	 .setShippedDateIso8601("xxx")
//	 .setRcvdDateIso8601("xxx")
//	 .setInsertTs("xxx")
//	 .setUpdateTs("xxx")
//	 ;
