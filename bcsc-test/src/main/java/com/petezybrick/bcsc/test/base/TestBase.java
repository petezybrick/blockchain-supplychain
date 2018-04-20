package com.petezybrick.bcsc.test.base;

import java.security.Security;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.petezybrick.bcsc.common.config.CassandraBaseDao;
import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.database.PooledDataSource;
import com.petezybrick.bcsc.service.item.LotIngredientItem;
import com.petezybrick.bcsc.service.item.LotSupplierBlockItem;
import com.petezybrick.bcsc.service.item.LotTreeItem;

public class TestBase {

	@BeforeClass
	public static void beforeClass() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SupplyBlockchainConfig supplyBlockchainConfig = 
				SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),System.getenv("KEYSPACE_NAME") );
		PooledDataSource.getInstance(supplyBlockchainConfig);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		CassandraBaseDao.disconnect();
	}

	public static void dumpLotTreeItemToConsole( LotTreeItem lotTreeItem ) throws Exception {
		System.out.println( "Manufacturer Lot Number: " + lotTreeItem.getManufacturerLotNumber() + 
				", Manufacturer Filled Date: " + lotTreeItem.getManufacturerLotFilledDate());
		for( LotIngredientItem lotIngredientItem :  lotTreeItem.getLotIngredientItems()) {
			System.out.println("\t" + lotIngredientItem.getIngredientName());
			for(LotSupplierBlockItem lotSupplierBlockItem : lotIngredientItem.getLotSupplierBlockItems() ) {
				System.out.println("\t\t" + lotSupplierBlockItem.getSupplierName() );
				System.out.println("\t\t\tOrigin Country: " + lotSupplierBlockItem.getCountry() + ", State/Province: " + lotSupplierBlockItem.getStateProvince());
				System.out.println("\t\t\tDUNS Number: " + lotSupplierBlockItem.getDunsNumber());
				System.out.println("\t\t\tBlockChain PrevHash: " + lotSupplierBlockItem.getPreviousHash() + ", Hash: " + lotSupplierBlockItem.getHash());
				System.out.println("\t\t\tCategory: " + lotSupplierBlockItem.getSupplierCategory() + ", SubCategory: " + lotSupplierBlockItem.getSupplierSubCategory());
				System.out.println("\t\t\tSupplier Lot Number: " + lotSupplierBlockItem.getSupplierLotNumber() );
			}
		}
	}
}
