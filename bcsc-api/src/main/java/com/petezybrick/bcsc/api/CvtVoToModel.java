package com.petezybrick.bcsc.api;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.petezybrick.bcsc.service.database.LotIngredientVo;
import com.petezybrick.bcsc.service.database.LotSupplierBlockVo;
import com.petezybrick.bcsc.service.database.LotTreeVo;

import io.swagger.model.LotIngredientItem;
import io.swagger.model.LotSupplierBlockItem;
import io.swagger.model.LotTreeItem;

public class CvtVoToModel {
	public static String timeTimestampToUtcString( Timestamp ts ) {
		Instant instant = Instant.ofEpochMilli(ts.getTime());
		return DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.of("UTC")).format(instant);
	}

	public static LotTreeItem cvtLotTree( LotTreeVo lotTreeVo ) throws Exception {
		LotTreeItem lotTreeItem = new LotTreeItem();
		lotTreeItem.setManufacturerLotFilledDate( timeTimestampToUtcString(lotTreeVo.getManufacturerLotFilledDate()) );
		lotTreeItem.setManufacturerLotNumber(lotTreeVo.getManufacturerLotNumber());
		List<LotIngredientItem>  lotIngredientItems = new ArrayList<LotIngredientItem>();
		lotTreeItem.setLotIngredientItems(lotIngredientItems);
		for( LotIngredientVo lotIngredientVo : lotTreeVo.getLotIngredientItems() ) {
			LotIngredientItem lotIngredientItem = new LotIngredientItem();
			lotIngredientItems.add( lotIngredientItem );
			lotIngredientItem.setIngredientName(lotIngredientVo.getIngredientName());
			lotIngredientItem.setIngredientSequence(lotIngredientVo.getIngredientSequence());
			List<LotSupplierBlockItem> lotSupplierBlockItems = new ArrayList<LotSupplierBlockItem>();
			lotIngredientItem.setLotSupplierBlockItems(lotSupplierBlockItems);
			for( LotSupplierBlockVo lotSupplierBlockVo : lotIngredientVo.getLotSupplierBlockItems() ) {
				LotSupplierBlockItem lotSupplierBlockItem = new LotSupplierBlockItem();
				lotSupplierBlockItems.add(lotSupplierBlockItem);
				lotSupplierBlockItem.setBlockSequence(lotSupplierBlockVo.getBlockSequence());
				lotSupplierBlockItem.setHash(lotSupplierBlockVo.getHash());
				lotSupplierBlockItem.setPreviousHash(lotSupplierBlockVo.getPreviousHash());
				lotSupplierBlockItem.setSupplierLotNumber(lotSupplierBlockVo.getSupplierLotNumber());
				lotSupplierBlockItem.setSupplierCategory(lotSupplierBlockVo.getSupplierCategory());
				lotSupplierBlockItem.setSupplierSubCategory(lotSupplierBlockVo.getSupplierSubCategory());
				lotSupplierBlockItem.setSupplierName(lotSupplierBlockVo.getSupplierName());
				lotSupplierBlockItem.setDunsNumber(lotSupplierBlockVo.getDunsNumber());
				lotSupplierBlockItem.setCountry(lotSupplierBlockVo.getCountry());
				lotSupplierBlockItem.setStateProvince(lotSupplierBlockVo.getStateProvince());
			}
		}
		
		return lotTreeItem;
	}
}
