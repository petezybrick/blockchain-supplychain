package com.petezybrick.bcsc.webui;

import io.swagger.client.model.LotIngredientItem;
import io.swagger.client.model.LotSupplierBlockItem;
import io.swagger.client.model.LotTreeItem;

public class LotTreeToDetailTree {

	public static String createDetailTreeHtml5(LotTreeItem lotTreeItem) {
		StringBuilder sb = new StringBuilder();
		for (LotIngredientItem lotIngredientItem : lotTreeItem.getLotIngredientItems()) {
			sb.append("<details style='font-weight:bold;'>");
			if( lotIngredientItem.getLotSupplierBlockItems().size() > 0 ) sb.append("<summary>");
			sb.append( lotIngredientItem.getIngredientName());
			if( lotIngredientItem.getLotSupplierBlockItems().size() > 0 ) sb.append("</summary>");
			
			for (LotSupplierBlockItem lotSupplierBlockItem : lotIngredientItem.getLotSupplierBlockItems()) {
				sb.append("<details style='font-weight:normal;margin-left:25px'><summary>")
					.append(lotSupplierBlockItem.getSupplierName()).append("</summary>");
				sb.append("<div style='margin-left:25px'>")
				.append("Origin Country: ").append(lotSupplierBlockItem.getCountry())
					.append(", State/Province: ").append(lotSupplierBlockItem.getStateProvince())
					.append("</div>");

				sb.append("<details style='margin-left:25px'><summary>Supplier Details</summary>");				
				sb.append("<div style='margin-left:25px'>")
					.append("Lot Number: ").append(lotSupplierBlockItem.getSupplierLotNumber()).append("</div>");
				sb.append("<div style='margin-left:25px'>")
					.append("DUNS Number: ").append(lotSupplierBlockItem.getDunsNumber()).append("</div>");
				sb.append("</details>");
				
				sb.append("<details style='margin-left:25px'><summary>Blockchain Details</summary>");
				sb.append("<div style='margin-left:25px'>")
					.append("PrevHash: ").append(lotSupplierBlockItem.getPreviousHash()).append("</div>");
				sb.append("<div style='margin-left:25px'>")
					.append("Hash: ").append(lotSupplierBlockItem.getHash()).append("</div>");
				sb.append("</details>");

				sb.append("</details>");				
			}
			sb.append("</details>");				
		}
		return sb.toString();
	}

}

//<details><summary>1. Node</summary>
//<div style="margin-left:25px">1.1 Item</div>
//<div style="margin-left:25px">1.2 Item</div>
//<details style="margin-left:25px"><summary>1.3 Node</summary>
//    <div style="margin-left:25px">1.3.1 Item</div>
//    <div style="margin-left:25px">1.3.2 Item</div>
//</details>
//</details>
//<details><summary>2. Node</summary>
//<div style="margin-left:25px">2.1 Item</div>
//<div style="margin-left:25px">2.2 Item</div>
//</details>
//<details><summary>3. Node</summary>
//<div style="margin-left:25px">3.1 Item</div>
//</details>