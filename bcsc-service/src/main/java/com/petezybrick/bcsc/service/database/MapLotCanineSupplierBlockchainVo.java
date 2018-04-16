package com.petezybrick.bcsc.service.database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MapLotCanineSupplierBlockchainVo {
	private static final Logger logger = LogManager.getLogger(MapLotCanineSupplierBlockchainVo.class);
	private String mapLotCanineSupplierBlockchainUuid;
	private String lotCanineUuid;
	private String supplierBlockchainUuid;
	private int ingredientSequence;
	private String ingredientName;
	private Timestamp insertTs;
	private Timestamp updateTs;


	public MapLotCanineSupplierBlockchainVo() {
	}


	public MapLotCanineSupplierBlockchainVo(ResultSet rs) throws SQLException {
		this.mapLotCanineSupplierBlockchainUuid = rs.getString("map_lot_canine_supplier_blockchain_uuid");
		this.lotCanineUuid = rs.getString("lot_canine_uuid");
		this.supplierBlockchainUuid = rs.getString("supplier_blockchain_uuid");
		this.ingredientSequence = rs.getInt("ingredient_sequence");
		this.ingredientName = rs.getString("ingredient_name");
		this.insertTs = rs.getTimestamp("insert_ts");
		this.updateTs = rs.getTimestamp("update_ts");
	}


	public String getMapLotCanineSupplierBlockchainUuid() {
		return this.mapLotCanineSupplierBlockchainUuid;
	}
	public String getLotCanineUuid() {
		return this.lotCanineUuid;
	}
	public String getSupplierBlockchainUuid() {
		return this.supplierBlockchainUuid;
	}
	public int getIngredientSequence() {
		return this.ingredientSequence;
	}
	public String getIngredientName() {
		return this.ingredientName;
	}
	public Timestamp getInsertTs() {
		return this.insertTs;
	}
	public Timestamp getUpdateTs() {
		return this.updateTs;
	}


	public MapLotCanineSupplierBlockchainVo setMapLotCanineSupplierBlockchainUuid( String mapLotCanineSupplierBlockchainUuid ) {
		this.mapLotCanineSupplierBlockchainUuid = mapLotCanineSupplierBlockchainUuid;
		return this;
	}
	public MapLotCanineSupplierBlockchainVo setLotCanineUuid( String lotCanineUuid ) {
		this.lotCanineUuid = lotCanineUuid;
		return this;
	}
	public MapLotCanineSupplierBlockchainVo setSupplierBlockchainUuid( String supplierBlockchainUuid ) {
		this.supplierBlockchainUuid = supplierBlockchainUuid;
		return this;
	}
	public MapLotCanineSupplierBlockchainVo setIngredientSequence( int ingredientSequence ) {
		this.ingredientSequence = ingredientSequence;
		return this;
	}
	public MapLotCanineSupplierBlockchainVo setIngredientName( String ingredientName ) {
		this.ingredientName = ingredientName;
		return this;
	}
	public MapLotCanineSupplierBlockchainVo setInsertTs( Timestamp insertTs ) {
		this.insertTs = insertTs;
		return this;
	}
	public MapLotCanineSupplierBlockchainVo setUpdateTs( Timestamp updateTs ) {
		this.updateTs = updateTs;
		return this;
	}
}

// MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo = new MapLotCanineSupplierBlockchainVo()
//	 .setMapLotCanineSupplierBlockchainUuid("xxx")
//	 .setLotCanineUuid("xxx")
//	 .setSupplierBlockchainUuid("xxx")
//	 .setIngredientSequence("xxx")
//	 .setIngredientName("xxx")
//	 .setInsertTs("xxx")
//	 .setUpdateTs("xxx")
//	 ;
