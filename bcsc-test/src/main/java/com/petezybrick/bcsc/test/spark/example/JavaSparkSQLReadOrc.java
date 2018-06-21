package com.petezybrick.bcsc.test.spark.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class JavaSparkSQLReadOrc {
	private static final Logger logger = Logger.getLogger(JavaSparkSQLReadOrc.class.getName());
	private static String hiveMetastoreUri;

	public static void main(String[] args) throws AnalysisException {
		hiveMetastoreUri = args[0];
		// $example on:init_session$
		SparkConf conf = new SparkConf().setAppName("Hive ORC Reader").set("hive.metastore.uris", hiveMetastoreUri);
		if (args.length > 1)
			conf.setMaster(args[1]);

		SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();

//		spark.sql("SHOW DATABASES").show();
//		spark.sql("SHOW TABLES").show();
		spark.sql("USE db_bcsc");
		String query = "select s.supplier_uuid, s.supplier_name, count(*) as cnt from lot_canine lc " + 
			"inner join customer_loyalty cl on cl.manufacturer_lot_number=lc.manufacturer_lot_number and cl.desc_type='C' " + 
			"inner join map_lot_canine_supplier_blockchain mlcsb on mlcsb.lot_canine_uuid=lc.lot_canine_uuid " + 
			"inner join supplier_blockchain sbc on sbc.supplier_blockchain_uuid=mlcsb.supplier_blockchain_uuid " + 
			"inner join supplier_block sb on sb.supplier_blockchain_uuid=sbc.supplier_blockchain_uuid " + 
			"inner join supplier s on s.supplier_uuid=sb.supplier_uuid " + 
			"group by s.supplier_uuid, s.supplier_name " + 
			"order by cnt desc " + 
			"limit 25";
		Dataset<Row> rs = spark.sql(query);
		rs.write().format("ORC").save("hdfs://localhost:9000/user/bcsc/pzt1");
		//rs.write().format("JSON").save("/tmp/pztx1_json");
	
		spark.stop();
	}
	
	public static class SupplierCount {
		private String supplierUuid;
		private String supplierName;
		private Long cnt;
		
		public SupplierCount( Row row ) {
			this.supplierUuid = row.getAs("supplier_uuid");
			this.supplierName = row.getAs("supplier_name");
			this.cnt = row.getAs("cnt");
		}

		public String getSupplierUuid() {
			return supplierUuid;
		}

		public String getSupplierName() {
			return supplierName;
		}

		public Long getCnt() {
			return cnt;
		}

		public SupplierCount setSupplierUuid(String supplierUuid) {
			this.supplierUuid = supplierUuid;
			return this;
		}

		public SupplierCount setSupplierName(String supplierName) {
			this.supplierName = supplierName;
			return this;
		}

		public SupplierCount setCnt(Long cnt) {
			this.cnt = cnt;
			return this;
		}

		@Override
		public String toString() {
			return "SupplierCount [supplierUuid=" + supplierUuid + ", supplierName=" + supplierName + ", cnt=" + cnt + "]";
		}
	}

}
