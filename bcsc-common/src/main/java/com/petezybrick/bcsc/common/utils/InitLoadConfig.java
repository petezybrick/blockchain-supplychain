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
				.setJdbcDriverClassNameSupplier("com.mysql.jdbc.Driver")
				.setJdbcInsertBlockSizeSupplier(1000)
				.setJdbcLoginSupplier("supplier")
				.setJdbcPasswordSupplier("Password*8")
				.setJdbcUrlSupplier("jdbc:mysql://bcsc-mysql:3306/db_supplier")
				.setJdbcDriverClassNamePresto("com.facebook.presto.jdbc.PrestoDriver")
				.setJdbcLoginPresto("test")
				.setJdbcPasswordPresto("")
				.setJdbcUrlPresto("jdbc:presto://bcsc-presto-coordinator:8080/cat_bcsc/db_bcsc")
				.setJdbcDriverClassNameHive("org.apache.hive.jdbc.HiveDriver")
				.setJdbcLoginHive("")
				.setJdbcPasswordHive("")
				.setJdbcUrlHive("jdbc:hive2://bcsc-hive-namenode:10000/db_bcsc")
				.setSupplyBlockchainConfigJsonKey("dev_docker")
				.setHdfsUri("hdfs://bcsc-hive-namenode:9000")
				.setHadoopUserName("bcsc")
				.setHiveMetastoreUri( "thrift://bcsc-hive-namenode:9083" )
				.setTargetPathBcscData( "hdfs://user/bcsc/bcsc_data/" )
				.setSchemaBcscData("db_bcsc");
        String rawJson = BlockchainUtils.objectMapper.writeValueAsString(supplyBlockchainConfig);
		ConfigVo configVo = new ConfigVo("dev_docker", rawJson );
		return configVo;
	}

	
	public static ConfigVo createConfigVoDevLocalhost() throws Exception {
		SupplyBlockchainConfig supplyBlockchainConfig = new SupplyBlockchainConfig()
				.setContactPoint("127.0.0.1")
				.setKeyspaceName("bcsc")
				.setJdbcDriverClassNameSupplier("com.mysql.jdbc.Driver")
				.setJdbcInsertBlockSizeSupplier(1000)
				.setJdbcLoginSupplier("supplier")
				.setJdbcPasswordSupplier("Password*8")
				.setJdbcUrlSupplier("jdbc:mysql://localhost:3307/db_supplier")
				.setJdbcDriverClassNamePresto("com.facebook.presto.jdbc.PrestoDriver")
				.setJdbcLoginPresto("test")
				.setJdbcPasswordPresto("")
				.setJdbcUrlPresto("jdbc:presto://localhost:8090/cat_bcsc/db_bcsc")
				.setJdbcDriverClassNameHive("org.apache.hive.jdbc.HiveDriver")
				.setJdbcLoginHive("")
				.setJdbcPasswordHive("")
				.setJdbcUrlHive("jdbc:hive2://localhost:10000/db_bcsc")
				.setSupplyBlockchainConfigJsonKey("dev_localhost")
				.setHdfsUri("hdfs://localhost:9000")
				.setHadoopUserName("bcsc")
				.setHiveMetastoreUri( "thrift://bcsc-hive-namenode:9083" )
				.setTargetPathBcscData( "hdfs://user/bcsc/bcsc_data/" )
				.setSchemaBcscData("db_bcsc");
        String rawJson = BlockchainUtils.objectMapper.writeValueAsString(supplyBlockchainConfig);
		ConfigVo configVo = new ConfigVo("dev_localhost", rawJson );
		return configVo;
	}

}
