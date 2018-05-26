package com.petezybrick.bcsc.service.test.find;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.petezybrick.bcsc.service.database.LotCanineDao;
import com.petezybrick.bcsc.service.database.LotCanineVo;
import com.petezybrick.bcsc.service.database.LotTreeVo;
import com.petezybrick.bcsc.service.database.LoyaltyComplaintLotCanineDao;
import com.petezybrick.bcsc.service.database.LoyaltyComplaintLotCanineVo;
import com.petezybrick.bcsc.test.base.TestBase;

public class TestLoyaltyComplaintLotCanine extends TestBase {

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
		List<LoyaltyComplaintLotCanineVo> loyaltyComplaintLotCanineVos = LoyaltyComplaintLotCanineDao.findComplaints();
		Assert.assertNotNull(loyaltyComplaintLotCanineVos);
		Assert.assertEquals(100, loyaltyComplaintLotCanineVos.size());
		loyaltyComplaintLotCanineVos.forEach( lc -> System.out.println(lc));
	}


	
	
}
