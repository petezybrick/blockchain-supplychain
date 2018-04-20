package com.petezybrick.bcsc.apiclient.test;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.petezybrick.bcsc.service.database.LotCanineDao;
import com.petezybrick.bcsc.service.database.LotCanineVo;
import com.petezybrick.bcsc.service.item.LotIngredientItem;
import com.petezybrick.bcsc.service.item.LotSupplierBlockItem;
import com.petezybrick.bcsc.service.item.LotTreeItem;
import com.petezybrick.bcsc.test.base.TestBase;

import io.swagger.client.ApiClient;
import io.swagger.client.api.LotApi;
import io.swagger.client.auth.OAuth;

public class TestApiFindManLots extends TestBase {
	public static final String existsLotNumber = "20180108-1";

	@BeforeClass
	public static void beforeClass() throws Exception {
		TestBase.beforeClass();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testFound() throws Exception {        
		ApiClient apiClient = new ApiClient();
	    apiClient.setBasePath("http://localhost:8092/bcsc-webapi/v2");
	    
	    // Configure OAuth2 access token for authorization: petstore_auth
	    OAuth petnutrition_auth = (OAuth) apiClient.getAuthentication("petnutrition_auth");
	    petnutrition_auth.setAccessToken("special-key");
	
	    LotApi apiInstance = new LotApi(apiClient);
    	List<String> lotNumbers = apiInstance.findManLots("2018-04-01", "2018-04-15", 123);
    	System.out.println("Lot Numbers: " + lotNumbers);
		Assert.assertNotNull(lotNumbers);
		Assert.assertEquals(3, lotNumbers.size() );
		Assert.assertEquals(lotNumbers.get(0), "1111" );
		Assert.assertEquals(lotNumbers.get(1), "2222" );
		Assert.assertEquals(lotNumbers.get(2), "3333" );

	}

//	@Test
//	public void testNotFound() throws Exception {
//		LotCanineVo lotCanineVo = LotCanineDao.findByLotNumber(notExistsLogNumber);
//		Assert.assertNull(lotCanineVo);
//	}
	

//	@Test
//	public void testFoundBuildLotTree() throws Exception {
//		ApiClient apiClient = new ApiClient();
//	    apiClient.setBasePath("http://localhost:8092/bcsc-webapi/v2");
//	    
//	    // Configure OAuth2 access token for authorization: petstore_auth
//	    OAuth petnutrition_auth = (OAuth) apiClient.getAuthentication("petnutrition_auth");
//	    petnutrition_auth.setAccessToken("special-key");
//	
//	    LotApi apiInstance = new LotApi(apiClient);
//    	List<String> lotNumbers = apiInstance.findManLots("2018-04-01", "2018-04-15", 123);
//		LotTreeItem lotTreeItem = apiInstance.findLotIngreds(existsLotNumber);
//
//		dumpLotTreeItemToConsole( lotTreeItem );
//		Assert.assertNotNull(lotTreeItem);
//	}
	
//	public static void dumpLotTreeItemToConsole( LotTreeItem lotTreeItem ) throws Exception {
//		System.out.println( "Manufacturer Lot Number: " + lotTreeItem.getManufacturerLotNumber() + 
//				", Manufacturer Filled Date: " + lotTreeItem.getManufacturerLotFilledDate());
//		for( LotIngredientItem lotIngredientItem :  lotTreeItem.getLotIngredientItems()) {
//			System.out.println("\t" + lotIngredientItem.getIngredientName());
//			for(LotSupplierBlockItem lotSupplierBlockItem : lotIngredientItem.getLotSupplierBlockItems() ) {
//				System.out.println("\t\t" + lotSupplierBlockItem.getSupplierName() );
//				System.out.println("\t\t\tOrigin Country: " + lotSupplierBlockItem.getCountry() + ", State/Province: " + lotSupplierBlockItem.getStateProvince());
//				System.out.println("\t\t\tDUNS Number: " + lotSupplierBlockItem.getDunsNumber());
//				System.out.println("\t\t\tBlockChain PrevHash: " + lotSupplierBlockItem.getPreviousHash() + ", Hash: " + lotSupplierBlockItem.getHash());
//				System.out.println("\t\t\tCategory: " + lotSupplierBlockItem.getSupplierCategory() + ", SubCategory: " + lotSupplierBlockItem.getSupplierSubCategory());
//				System.out.println("\t\t\tSupplier Lot Number: " + lotSupplierBlockItem.getSupplierLotNumber() );
//			}
//		}
//	}
	
	
}
