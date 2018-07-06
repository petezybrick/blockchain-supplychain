package com.petezybrick.bcsc.service.test.orc;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.Security;

import org.apache.spark.SparkConf;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.sparksql.CustomerComplaintService;

public class TestCustomerComplaintService {
	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		ClassLoader cl = ClassLoader.getSystemClassLoader();

		URL[] urls = ((URLClassLoader) cl).getURLs();

		for (URL url : urls) {
			System.out.println(url.getFile());
		}

		SparkConf conf = new SparkConf().setAppName("Hive ORC Reader");
		System.out.println(conf);
		System.out.println("AFTER");
		String env = args[0];
		String contactPoint = args[1];
		String keyspaceName = args[2];
		SupplyBlockchainConfig.getInstance(env, contactPoint, keyspaceName);
		String sparkMaster = (args.length > 3) ? args[3] : null;
		CustomerComplaintService.populateCustomerSupplierComplaints(sparkMaster);
	}

}
