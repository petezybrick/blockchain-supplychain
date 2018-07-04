package com.petezybrick.bcsc.service.test.orc;

import java.security.Security;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.sparksql.CustomerComplaintService;

public class TestCustomerComplaintService {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		String env = args[0];
		String contactPoint = args[1];
		String keyspaceName = args[2];
		SupplyBlockchainConfig.getInstance( env, contactPoint, keyspaceName );
		String sparkMaster = (args.length>3) ? args[3] : null;
		TestCustomerComplaintService.populateCustomerSupplierComplaints( sparkMaster );
	}
	
	
	public static void populateCustomerSupplierComplaints( String sparkMaster ) throws Exception {
		SparkConf conf = new SparkConf().setAppName("Hive ORC Reader")
				.set("hive.metastore.uris", SupplyBlockchainConfig.getInstance().getHiveMetastoreUri())
				.set("ConfVars.HIVE_STATS_JDBC_TIMEOUT", "0");
		if (sparkMaster != null )
			conf.setMaster(sparkMaster);
		SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		CustomerComplaintService.populateCustomerSupplierComplaints( conf, spark );
		spark.stop();
	}

}
