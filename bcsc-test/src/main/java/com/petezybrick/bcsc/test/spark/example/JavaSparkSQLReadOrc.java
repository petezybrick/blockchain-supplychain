package com.petezybrick.bcsc.test.spark.example;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.SparkSession;

public class JavaSparkSQLReadOrc {
	private static String hiveMetastoreUri;

	public static void main(String[] args) throws AnalysisException {
		hiveMetastoreUri = args[0];
		// $example on:init_session$
		SparkConf conf = new SparkConf().setAppName("Hive ORC Reader").set("hive.metastore.uris", hiveMetastoreUri);
		if (args.length > 1)
			conf.setMaster(args[1]);

		SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();

		spark.sql("SHOW DATABASES").show();
		spark.sql("USE db_bcsc").show();
		spark.sql("SHOW TABLES").show();
		String query = "select s.supplier_uuid, s.supplier_name, count(*) as cnt from lot_canine lc " + 
			"inner join customer_loyalty cl on cl.manufacturer_lot_number=lc.manufacturer_lot_number and cl.desc_type='C' " + 
			"inner join map_lot_canine_supplier_blockchain mlcsb on mlcsb.lot_canine_uuid=lc.lot_canine_uuid " + 
			"inner join supplier_blockchain sbc on sbc.supplier_blockchain_uuid=mlcsb.supplier_blockchain_uuid " + 
			"inner join supplier_block sb on sb.supplier_blockchain_uuid=sbc.supplier_blockchain_uuid " + 
			"inner join supplier s on s.supplier_uuid=sb.supplier_uuid " + 
			"group by s.supplier_uuid, s.supplier_name " + 
			"order by cnt desc " + 
			"limit 25";
		spark.sql(query).show();

		spark.stop();
	}

}
