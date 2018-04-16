package com.petezybrick.bcsc.service.item;

import java.sql.Timestamp;
import java.util.List;

public class LotTreeItem {
	private String manufacturerLotNumber;
	private Timestamp manufacturerLotFilledDate;
	private List<LotIngredientItem> lotIngredientItems;
	
	
	public String getManufacturerLotNumber() {
		return manufacturerLotNumber;
	}
	public Timestamp getManufacturerLotFilledDate() {
		return manufacturerLotFilledDate;
	}
	public List<LotIngredientItem> getLotIngredientItems() {
		return lotIngredientItems;
	}
	public LotTreeItem setManufacturerLotNumber(String manufacturerLotNumber) {
		this.manufacturerLotNumber = manufacturerLotNumber;
		return this;
	}
	public LotTreeItem setManufacturerLotFilledDate(Timestamp manufacturerLotFilledDate) {
		this.manufacturerLotFilledDate = manufacturerLotFilledDate;
		return this;
	}
	public LotTreeItem setLotIngredientItems(List<LotIngredientItem> lotIngredientItems) {
		this.lotIngredientItems = lotIngredientItems;
		return this;
	}
	@Override
	public String toString() {
		return "LotTreeItem [manufacturerLotNumber=" + manufacturerLotNumber + ", manufacturerLotFilledDate=" + manufacturerLotFilledDate
				+ ", lotIngredientItems=" + lotIngredientItems + "]";
	}

	
	
}
