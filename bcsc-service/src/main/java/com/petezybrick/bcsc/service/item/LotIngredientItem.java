package com.petezybrick.bcsc.service.item;

import java.util.ArrayList;
import java.util.List;

public class LotIngredientItem {
	private Integer ingredientSequence;
	private String ingredientName;
	private List<LotSupplierBlockItem> lotSupplierBlockItems;
	
	
	public LotIngredientItem() {
		super();
		this.lotSupplierBlockItems = new ArrayList<LotSupplierBlockItem>();
	}
	
	public Integer getIngredientSequence() {
		return ingredientSequence;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public List<LotSupplierBlockItem> getLotSupplierBlockItems() {
		return lotSupplierBlockItems;
	}
	public LotIngredientItem setIngredientSequence(Integer ingredientSequence) {
		this.ingredientSequence = ingredientSequence;
		return this;
	}
	public LotIngredientItem setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
		return this;
	}
	public LotIngredientItem setLotSupplierBlockItems(List<LotSupplierBlockItem> lotSupplierBlockItems) {
		this.lotSupplierBlockItems = lotSupplierBlockItems;
		return this;
	}
	@Override
	public String toString() {
		return "LotIngredientItem [ingredientSequence=" + ingredientSequence + ", ingredientName=" + ingredientName + ", lotSupplierBlockItems="
				+ lotSupplierBlockItems + "]";
	}
	
	
}
