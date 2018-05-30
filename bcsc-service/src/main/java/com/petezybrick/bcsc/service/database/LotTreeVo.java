package com.petezybrick.bcsc.service.database;

import java.sql.Timestamp;
import java.util.List;

public class LotTreeVo {
	private String lotCanineUuid;
	private String manufacturerLotNumber;
	private Timestamp manufacturerLotFilledDate;
	private List<LotIngredientVo> lotIngredientVos;
	
	
	public String getManufacturerLotNumber() {
		return manufacturerLotNumber;
	}
	public Timestamp getManufacturerLotFilledDate() {
		return manufacturerLotFilledDate;
	}
	public List<LotIngredientVo> getLotIngredientItems() {
		return lotIngredientVos;
	}
	public LotTreeVo setManufacturerLotNumber(String manufacturerLotNumber) {
		this.manufacturerLotNumber = manufacturerLotNumber;
		return this;
	}
	public LotTreeVo setManufacturerLotFilledDate(Timestamp manufacturerLotFilledDate) {
		this.manufacturerLotFilledDate = manufacturerLotFilledDate;
		return this;
	}
	public LotTreeVo setLotIngredientItems(List<LotIngredientVo> lotIngredientVos) {
		this.lotIngredientVos = lotIngredientVos;
		return this;
	}
	@Override
	public String toString() {
		return "LotTreeVo [lotCanineUuid=" + lotCanineUuid + ", manufacturerLotNumber=" + manufacturerLotNumber + ", manufacturerLotFilledDate="
				+ manufacturerLotFilledDate + ", lotIngredientVos=" + lotIngredientVos + "]";
	}
	public String getLotCanineUuid() {
		return lotCanineUuid;
	}
	public LotTreeVo setLotCanineUuid(String lotCanineUuid) {
		this.lotCanineUuid = lotCanineUuid;
		return this;
	}

	
	
}
