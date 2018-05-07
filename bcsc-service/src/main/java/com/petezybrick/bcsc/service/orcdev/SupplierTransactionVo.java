package com.petezybrick.bcsc.service.orcdev;


import java.nio.ByteBuffer;
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
	private Integer qty;
	private String units;
	private Timestamp shippedDateIso8601;
	private Timestamp rcvdDateIso8601;


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
	public Integer getQty() {
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
	public SupplierTransactionVo setQty( Integer qty ) {
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
//	 ;
