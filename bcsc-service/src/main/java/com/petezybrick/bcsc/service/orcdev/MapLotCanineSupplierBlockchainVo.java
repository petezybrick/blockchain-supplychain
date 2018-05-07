package com.petezybrick.bcsc.service.orcdev;


import java.nio.ByteBuffer;
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
	private Integer ingredientSequence;
	private String ingredientName;


	public MapLotCanineSupplierBlockchainVo() {
	}


	public MapLotCanineSupplierBlockchainVo(ResultSet rs) throws SQLException {
		this.mapLotCanineSupplierBlockchainUuid = rs.getString("map_lot_canine_supplier_blockchain_uuid");
		this.lotCanineUuid = rs.getString("lot_canine_uuid");
		this.supplierBlockchainUuid = rs.getString("supplier_blockchain_uuid");
		this.ingredientSequence = rs.getInt("ingredient_sequence");
		this.ingredientName = rs.getString("ingredient_name");
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
	public Integer getIngredientSequence() {
		return this.ingredientSequence;
	}
	public String getIngredientName() {
		return this.ingredientName;
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
	public MapLotCanineSupplierBlockchainVo setIngredientSequence( Integer ingredientSequence ) {
		this.ingredientSequence = ingredientSequence;
		return this;
	}
	public MapLotCanineSupplierBlockchainVo setIngredientName( String ingredientName ) {
		this.ingredientName = ingredientName;
		return this;
	}
}

// MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo = new MapLotCanineSupplierBlockchainVo()
//	 .setMapLotCanineSupplierBlockchainUuid("xxx")
//	 .setLotCanineUuid("xxx")
//	 .setSupplierBlockchainUuid("xxx")
//	 .setIngredientSequence("xxx")
//	 .setIngredientName("xxx")
//	 ;
