package com.petezybrick.bcsc.test.spark.example;

import java.security.Security;
import java.util.logging.Logger;

import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.utils.BcscServiceUtils;

public class JavaSparkSQLReadOrc {
	private static final Logger logger = Logger.getLogger(JavaSparkSQLReadOrc.class.getName());
	private static String hiveMetastoreUri;

	public static void main(String[] args) throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),
				System.getenv("KEYSPACE_NAME") );

		hiveMetastoreUri = args[0];
		// $example on:init_session$
		SparkConf conf = new SparkConf().setAppName("Hive ORC Reader").set("hive.metastore.uris", hiveMetastoreUri);
		if (args.length > 1)
			conf.setMaster(args[1]);

		SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		
//		spark.sql("SHOW DATABASES").show();
//		spark.sql("SHOW TABLES").show();
		spark.sql("USE db_bcsc");
		String query = "select s.supplier_uuid, s.supplier_name, count(*) as num_complaint from lot_canine lc " + 
			"inner join customer_loyalty cl on cl.manufacturer_lot_number=lc.manufacturer_lot_number and cl.desc_type='C' " + 
			"inner join map_lot_canine_supplier_blockchain mlcsb on mlcsb.lot_canine_uuid=lc.lot_canine_uuid " + 
			"inner join supplier_blockchain sbc on sbc.supplier_blockchain_uuid=mlcsb.supplier_blockchain_uuid " + 
			"inner join supplier_block sb on sb.supplier_blockchain_uuid=sbc.supplier_blockchain_uuid " + 
			"inner join supplier s on s.supplier_uuid=sb.supplier_uuid " + 
			"group by s.supplier_uuid, s.supplier_name " + 
			"order by num_complaint ";
		Dataset<Row> rs = spark.sql(query);
		// todo: write to temp folder then flip when done
		String targetPath =  "hdfs://user/bcsc/bcsc_data/customer_supplier_complaint";
		String newFolderPath = BcscServiceUtils.deleteTargetFolder(targetPath);
		rs.write().format("ORC").save(newFolderPath);
		//rs.write().format("JSON").save("/tmp/pztx1_json");
	
		spark.stop();
	}
	
	public static class CustomerSupplierComplaintVo {
		private String supplierUuid;
		private String supplierName;
		private Long numComplaint;
		
		public CustomerSupplierComplaintVo( Row row ) {
			this.supplierUuid = row.getAs("supplier_uuid");
			this.supplierName = row.getAs("supplier_name");
			this.numComplaint = row.getAs("num_complaint");
		}

		public String getSupplierUuid() {
			return supplierUuid;
		}

		public String getSupplierName() {
			return supplierName;
		}

		public Long getNumComplaint() {
			return numComplaint;
		}

		public CustomerSupplierComplaintVo setSupplierUuid(String supplierUuid) {
			this.supplierUuid = supplierUuid;
			return this;
		}

		public CustomerSupplierComplaintVo setSupplierName(String supplierName) {
			this.supplierName = supplierName;
			return this;
		}

		public CustomerSupplierComplaintVo setNumComplaint(Long numComplaint) {
			this.numComplaint = numComplaint;
			return this;
		}

		@Override
		public String toString() {
			return "CustomerSupplierComplaintVo [supplierUuid=" + supplierUuid + ", supplierName=" + supplierName + ", numComplaint=" + numComplaint + "]";
		}
	}

}
