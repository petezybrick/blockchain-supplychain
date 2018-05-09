package com.petezybrick.bcsc.service.orc;


import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MapLotCanineSupplierBlockchainVo extends BaseOrcVo {
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


	@Override
	public MapLotCanineSupplierBlockchainVo createInstance(List<Object> objs, String schemaVersion ) throws Exception {
		if( "1.0".equals(schemaVersion ) ) {
			return new MapLotCanineSupplierBlockchainVo()
				.setMapLotCanineSupplierBlockchainUuid((String)objs.get(0))
				.setLotCanineUuid((String)objs.get(1))
				.setSupplierBlockchainUuid((String)objs.get(2))
				.setIngredientSequence((Integer)objs.get(3))
				.setIngredientName((String)objs.get(4))
				;
		} else throw new Exception("Invalid schema version 1.0");
	}


	@Override
	public void fromObjectList( List<Object> objs ) throws Exception {
		this.mapLotCanineSupplierBlockchainUuid = (String)objs.get(0);
		this.lotCanineUuid = (String)objs.get(1);
		this.supplierBlockchainUuid = (String)objs.get(2);
		this.ingredientSequence = (Integer)objs.get(3);
		this.ingredientName = (String)objs.get(4);
	}


	@Override
	public List<Object> toObjectList() throws Exception {
		List<Object> objs = new ArrayList<Object>();
		objs.add(mapLotCanineSupplierBlockchainUuid);
		objs.add(lotCanineUuid);
		objs.add(supplierBlockchainUuid);
		objs.add(ingredientSequence);
		objs.add(ingredientName);
		return objs;
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
