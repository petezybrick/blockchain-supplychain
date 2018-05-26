package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CustomerLoyaltyDao {
	private static final Logger logger = LogManager.getLogger(CustomerLoyaltyDao.class);
	private static String sqlDeleteAll = "DELETE FROM customer_loyalty";
	private static String sqlDeleteByPk = "DELETE FROM customer_loyalty WHERE customer_loyalty_uuid=?";
	private static String sqlInsert = "INSERT INTO customer_loyalty (customer_loyalty_uuid,customer_uuid,desc_type,desc_text,manufacturer_lot_number) VALUES (?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT customer_loyalty_uuid,customer_uuid,desc_type,desc_text,manufacturer_lot_number,insert_ts,update_ts FROM customer_loyalty WHERE customer_loyalty_uuid=?";
	private static String sqlFindComplaints = "SELECT customer_loyalty_uuid,customer_uuid,desc_type,desc_text,manufacturer_lot_number,insert_ts,update_ts FROM customer_loyalty WHERE customer_loyalty_uuid=?";

	public static void insertBatchList( Connection con, List<CustomerLoyaltyVo> customerLoyaltyVos ) throws Exception {
		final int BATCH_SIZE = 1000;
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int cnt = 0;
			for( CustomerLoyaltyVo customerLoyaltyVo : customerLoyaltyVos ) {
				int offset = 1;
				pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
				pstmt.setString( offset++, customerLoyaltyVo.getCustomerUuid() );
				pstmt.setString( offset++, customerLoyaltyVo.getDescType() );
				pstmt.setString( offset++, customerLoyaltyVo.getDescText() );
				pstmt.setString( offset++, customerLoyaltyVo.getManufacturerLotNumber() );
				pstmt.addBatch();
				if( cnt % BATCH_SIZE == 0 ) {
					pstmt.executeBatch();
				}
			}
			pstmt.executeBatch();
		}
	}


	public static void insertBatchMode( Connection con, CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert)){
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerUuid() );
			pstmt.setString( offset++, customerLoyaltyVo.getDescType() );
			pstmt.setString( offset++, customerLoyaltyVo.getDescText() );
			pstmt.setString( offset++, customerLoyaltyVo.getManufacturerLotNumber() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} 
	}

	
	public static void insert( CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		try (	Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlInsert);	){
			con.setAutoCommit(false);
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerUuid() );
			pstmt.setString( offset++, customerLoyaltyVo.getDescType() );
			pstmt.setString( offset++, customerLoyaltyVo.getDescText() );
			pstmt.setString( offset++, customerLoyaltyVo.getManufacturerLotNumber() );
			pstmt.execute();
			con.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void deleteByPk( CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		try (	Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlDeleteByPk);	){
			con.setAutoCommit(true);
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void deleteBatchMode( Connection con, CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(sqlDeleteByPk);){
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static CustomerLoyaltyVo findByPk( CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		try (	Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlFindByPk);	){
			con.setAutoCommit(true);
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new CustomerLoyaltyVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	
	public static List<CustomerLoyaltyAllVo> findComplaints( ) throws Exception {
		try (	Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlFindComplaints);) {
			con.setAutoCommit(true);
			int offset = 1;
			// TODO: date criteria based on insert_ts
			//pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			ResultSet rs = pstmt.executeQuery();
			List<CustomerLoyaltyAllVo> customerLoyaltyAllVos = new ArrayList<CustomerLoyaltyAllVo>();
			while( rs.next() ) customerLoyaltyAllVos.add( new CustomerLoyaltyAllVo(rs) );
			return customerLoyaltyAllVos;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	
	public static void deleteAll( ) throws Exception {
		try (Connection con = SupplierDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}
	
	public static void deleteAll( Connection con ) throws Exception {
		try (Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}

}
