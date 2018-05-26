package com.petezybrick.bcsc.test.base;

import java.security.Security;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.petezybrick.bcsc.common.config.CassandraBaseDao;
import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.database.LotIngredientVo;
import com.petezybrick.bcsc.service.database.LotSupplierBlockVo;
import com.petezybrick.bcsc.service.database.LotTreeVo;
import com.petezybrick.bcsc.service.database.SupplierDataSource;

public class TestBase {

	@BeforeClass
	public static void beforeClass() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SupplyBlockchainConfig supplyBlockchainConfig = 
				SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),System.getenv("KEYSPACE_NAME") );
		SupplierDataSource.getInstance(supplyBlockchainConfig);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		CassandraBaseDao.disconnect();
	}


}
