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
			ConfigVo configVoDevDocker = createConfigVoDevDocker();
			InitLoadConfig.load( );			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	
	public static void load() throws Exception {
		try {
			String keyspaceName = "bcsc";
			CassandraBaseDao.connect("127.0.0.1", keyspaceName);
//			ConfigDao.dropKeyspace(keyspaceName);
//			ConfigDao.createKeyspace(keyspaceName, "SimpleStrategy", 3);			
			ConfigDao.useKeyspace(keyspaceName);
			ConfigDao.dropTable();
			ConfigDao.createTable();
			ConfigDao.insertConfig(createConfigVoDevDocker());
			ConfigDao.insertConfig(createConfigVoDevLocalhost());
		} catch( Exception e ) {
			throw e;
		} finally {
			ConfigDao.disconnect();
		}
	}

	
	public static ConfigVo createConfigVoDevDocker() throws Exception {
		SupplyBlockchainConfig supplyBlockchainConfig = new SupplyBlockchainConfig()
				.setContactPoint("127.0.0.1")
				.setKeyspaceName("bcsc")
				.setJdbcDriverClassName("com.mysql.jdbc.Driver")
				.setJdbcInsertBlockSize(1000)
				.setJdbcLogin("supplier")
				.setJdbcPassword("Password*8")
				.setJdbcUrl("jdbc:mysql://bcsc-mysql:3306/db_supplier")
				.setSupplyBlockchainConfigJsonKey("dev_docker")
				.setHdfsUri("hdfs://bcsc-hive-namenode:9000")
				.setHadoopUserName("bcsc");
        String rawJson = BlockchainUtils.objectMapper.writeValueAsString(supplyBlockchainConfig);
		ConfigVo configVo = new ConfigVo("dev_docker", rawJson );
		return configVo;
	}

	
	public static ConfigVo createConfigVoDevLocalhost() throws Exception {
		SupplyBlockchainConfig supplyBlockchainConfig = new SupplyBlockchainConfig()
				.setContactPoint("127.0.0.1")
				.setKeyspaceName("bcsc")
				.setJdbcDriverClassName("com.mysql.jdbc.Driver")
				.setJdbcInsertBlockSize(1000)
				.setJdbcLogin("supplier")
				.setJdbcPassword("Password*8")
				.setJdbcUrl("jdbc:mysql://localhost:3307/db_supplier")
				.setSupplyBlockchainConfigJsonKey("dev_localhost")
				.setHdfsUri("hdfs://localhost:9000")
				.setHadoopUserName("bcsc");
        String rawJson = BlockchainUtils.objectMapper.writeValueAsString(supplyBlockchainConfig);
		ConfigVo configVo = new ConfigVo("dev_localhost", rawJson );
		return configVo;
	}

}
