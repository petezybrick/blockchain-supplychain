package com.petezybrick.bcsc.common.utils;

import java.security.Security;

import com.petezybrick.bcsc.common.config.CassandraBaseDao;
import com.petezybrick.bcsc.common.config.ConfigDao;
import com.petezybrick.bcsc.common.config.ConfigVo;
import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;

public class InitLoadConfig {

	public static void main(String[] args) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			ConfigVo configVo = createConfigVo();
			InitLoadConfig.loadSingle( configVo );
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

	}
	
	public static void loadSingle(ConfigVo configVo) throws Exception {
		try {
			String keyspaceName = "bcsc";
			CassandraBaseDao.connect("127.0.0.1", keyspaceName);
			ConfigDao.dropKeyspace(keyspaceName);
			ConfigDao.createKeyspace(keyspaceName, "SimpleStrategy", 3);			
			ConfigDao.useKeyspace(keyspaceName);
			ConfigDao.dropTable();
			ConfigDao.createTable();
			ConfigDao.insertConfig(configVo);
		} catch( Exception e ) {
			throw e;
		} finally {
			ConfigDao.disconnect();
		}
	}

	public static ConfigVo createConfigVo() throws Exception {
		SupplyBlockchainConfig supplyBlockchainConfig = new SupplyBlockchainConfig()
				.setContactPoint("127.0.0.1")
				.setKeyspaceName("bcsc")
				.setJdbcDriverClassName("com.mysql.jdbc.Driver")
				.setJdbcInsertBlockSize(1000)
				.setJdbcLogin("supplier")
				.setJdbcPassword("Password*8")
				.setJdbcUrl("jdbc:mysql://localhost:3307/db_supplier")
				.setSupplyBlockchainConfigJsonKey("dev");
        String rawJson = BlockchainUtils.objectMapper.writeValueAsString(supplyBlockchainConfig);

		ConfigVo configVo = new ConfigVo("dev", rawJson );
		return configVo;
	}

}
