package com.petezybrick.bcsc.service.sparksql;

import java.security.Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.utils.BcscServiceUtils;

public class CustomerComplaintService {
	private static final Logger logger = LogManager.getLogger(CustomerComplaintService.class);

	public static void main(String[] args) throws Exception {
		logger.info("+++++ Job Starting +++++" );
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		String env = args[0];
		String contactPoint = args[1];
		String keyspaceName = args[2];
		SupplyBlockchainConfig.getInstance( env, contactPoint, keyspaceName );
		String sparkMaster = (args.length>3) ? args[3] : null;
		CustomerComplaintService.populateCustomerSupplierComplaints( sparkMaster );
	}
	
	
	public static void populateCustomerSupplierComplaints( String sparkMaster ) throws Exception {
		SparkConf conf = new SparkConf().setAppName("Hive ORC Reader")
				.set("hive.metastore.uris", SupplyBlockchainConfig.getInstance().getHiveMetastoreUri());
		if (sparkMaster != null )
			conf.setMaster(sparkMaster);
		SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		populateCustomerSupplierComplaints( conf, spark );
		spark.stop();
	}
	
	
	public static void populateCustomerSupplierComplaints( SparkConf conf, SparkSession spark ) throws Exception {
		String schema = SupplyBlockchainConfig.getInstance().getSchemaBcscData();
		String query = "select s.supplier_uuid, s.supplier_name, count(*) as num_complaint from $SCHEMA$.lot_canine lc " + 
				"inner join $SCHEMA$.customer_loyalty cl on cl.manufacturer_lot_number=lc.manufacturer_lot_number and cl.desc_type='C' " + 
				"inner join $SCHEMA$.map_lot_canine_supplier_blockchain mlcsb on mlcsb.lot_canine_uuid=lc.lot_canine_uuid " + 
				"inner join $SCHEMA$.supplier_blockchain sbc on sbc.supplier_blockchain_uuid=mlcsb.supplier_blockchain_uuid " + 
				"inner join $SCHEMA$.supplier_block sb on sb.supplier_blockchain_uuid=sbc.supplier_blockchain_uuid " + 
				"inner join $SCHEMA$.supplier s on s.supplier_uuid=sb.supplier_uuid " + 
				"group by s.supplier_uuid, s.supplier_name " + 
				"order by num_complaint ".replace("$SCHEMA$", schema);
		logger.info("+++++ Running Query: {} +++++", query);
		Dataset<Row> rs = spark.sql(query);
		// todo: write to temp folder then flip when done
		String targetPath =  SupplyBlockchainConfig.getInstance().getTargetPathBcscData() + "customer_supplier_complaint";
		String newFolderPath = BcscServiceUtils.deleteTargetFolder(targetPath);
		logger.info("+++++ Writing ORC files to folder: {}", newFolderPath );
		rs.write().format("ORC").save(newFolderPath);
		logger.info("+++++ Job Complete +++++" );
	}

}
