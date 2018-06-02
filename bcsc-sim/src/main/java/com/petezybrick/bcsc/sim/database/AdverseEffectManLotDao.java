package com.petezybrick.bcsc.sim.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.service.database.SupplierDataSource;


public class AdverseEffectManLotDao {
	private static final Logger logger = LogManager.getLogger(AdverseEffectManLotDao.class);
	private static String sqlDeleteAll = "DELETE FROM stage_adverse_effect_man_lot";
	private static String sqlInsert = "INSERT INTO stage_adverse_effect_man_lot (manufacturer_lot_number) VALUES (?)";
	private static String sqlFindAll = "SELECT manufacturer_lot_number FROM stage_adverse_effect_man_lot ORDER BY manufacturer_lot_number";

	
	public static void deleteAll( ) throws Exception {
		try (Connection con = SupplierDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}
	
	
	public static void insertBatchList( List<String> manLotNumbers ) throws Exception {
		final int BATCH_SIZE = 1000;
		try (   Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int cnt = 0;
			for( String manLotNumber : manLotNumbers ) {
				int offset = 1;
				pstmt.setString( offset++, manLotNumber );
				pstmt.addBatch();
				if( cnt % BATCH_SIZE == 0 ) {
					pstmt.executeBatch();
				}
			}
			pstmt.executeBatch();
		}
	}
	
	
	public static List<String> findAllManLotNumbers( ) throws Exception {
		try ( Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlFindAll);
			){
			con.setAutoCommit(true);
			List<String> list = new ArrayList<String>();
			ResultSet rs = pstmt.executeQuery();
			while( rs.next() ) {
				list.add(rs.getString(1));
			}
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
		}
	}
	
}
