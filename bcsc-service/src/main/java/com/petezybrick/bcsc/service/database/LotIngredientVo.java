package com.petezybrick.bcsc.service.database;

import java.util.ArrayList;
import java.util.List;

public class LotIngredientVo {
	private Integer ingredientSequence;
	private String ingredientName;
	private List<LotSupplierBlockVo> lotSupplierBlockItems;
	
	
	public LotIngredientVo() {
		super();
		this.lotSupplierBlockItems = new ArrayList<LotSupplierBlockVo>();
	}
	
	public Integer getIngredientSequence() {
		return ingredientSequence;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public List<LotSupplierBlockVo> getLotSupplierBlockItems() {
		return lotSupplierBlockItems;
	}
	public LotIngredientVo setIngredientSequence(Integer ingredientSequence) {
		this.ingredientSequence = ingredientSequence;
		return this;
	}
	public LotIngredientVo setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
		return this;
	}
	public LotIngredientVo setLotSupplierBlockItems(List<LotSupplierBlockVo> lotSupplierBlockItems) {
		this.lotSupplierBlockItems = lotSupplierBlockItems;
		return this;
	}
	@Override
	public String toString() {
		return "LotIngredientItem [ingredientSequence=" + ingredientSequence + ", ingredientName=" + ingredientName + ", lotSupplierBlockItems="
				+ lotSupplierBlockItems + "]";
	}
	
	
}
