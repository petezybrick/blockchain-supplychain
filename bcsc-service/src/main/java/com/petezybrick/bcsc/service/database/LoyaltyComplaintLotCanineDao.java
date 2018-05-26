package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoyaltyComplaintLotCanineDao {
	private static final Logger logger = LogManager.getLogger(LoyaltyComplaintLotCanineDao.class);
	// TODO: add date range on query
	private static String sqlFindComplaints = 
			"select " +
					"cl.customer_loyalty_uuid as customer_loyalty_uuid, " +
					"cl.customer_uuid as customer_uuid, " +
					"cl.desc_text as desc_text, " +
					"cl.manufacturer_lot_number as manufacturer_lot_number, " +
					"cl.insert_ts as insert_ts, " +
					"cl.update_ts as update_ts, " +
					"lc.lot_canine_uuid as lot_canine_uuid, " +
					"lc.lot_filled_date as lot_filled_date " +
					"from cat_bcsc_mysql.db_supplier.customer_loyalty cl " +
					"left outer join cat_bcsc_hive.db_bcsc.lot_canine lc on lc.manufacturer_lot_number=cl.manufacturer_lot_number " +
					"where cl.desc_type='C'";

	
	public static List<LoyaltyComplaintLotCanineVo> findComplaints( ) throws Exception {
		try ( Connection con = PrestoDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();
			){
			List<LoyaltyComplaintLotCanineVo> list = new ArrayList<LoyaltyComplaintLotCanineVo>();
			ResultSet rs = stmt.executeQuery(sqlFindComplaints);
			while( rs.next() ) {
				list.add( new LoyaltyComplaintLotCanineVo(rs) );
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
}
