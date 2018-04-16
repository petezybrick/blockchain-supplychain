package com.petezybrick.bcsc.sim.run;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class SimBlockchainSequenceItem {
	private String supplierType;
	private List<DescCatSubcatItem> descCatSubcatItems;
	

	public String getSupplierType() {
		return supplierType;
	}


	public List<DescCatSubcatItem> getDescCatSubcatItems() {
		return descCatSubcatItems;
	}


	public SimBlockchainSequenceItem setSupplierType(String supplierType) {
		this.supplierType = supplierType;
		return this;
	}


	public SimBlockchainSequenceItem setDescCatSubcatItems(List<DescCatSubcatItem> descCatSubcatItems) {
		this.descCatSubcatItems = descCatSubcatItems;
		return this;
	}
	
	public static class DescCatSubcatItem {
		private String desc;
		private String category;
		private String subCategory;
		public String getDesc() {
			return desc;
		}
		public String getCategory() {
			return category;
		}
		public String getSubCategory() {
			return subCategory;
		}
		public DescCatSubcatItem setDesc(String desc) {
			this.desc = desc;
			return this;
		}
		public DescCatSubcatItem setCategory(String category) {
			this.category = category;
			return this;
		}
		public DescCatSubcatItem setSubCategory(String subCategory) {
			this.subCategory = subCategory;
			return this;
		}
		@Override
		public String toString() {
			return "DescCatSubcatItem [desc=" + desc + ", category=" + category + ", subCategory=" + subCategory + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "SimBlockchainSequenceItem [supplierType=" + supplierType + ", descCatSubcatItems=" + descCatSubcatItems + "]";
	}

}
