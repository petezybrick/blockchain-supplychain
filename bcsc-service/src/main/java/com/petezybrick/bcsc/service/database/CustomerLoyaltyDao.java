package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;


public class CustomerLoyaltyDao {
	private static final Logger logger = LogManager.getLogger(CustomerLoyaltyDao.class);
	private static String sqlDeleteByPk = "DELETE FROM customer_loyalty WHERE customer_loyalty_uuid=?";
	private static String sqlInsert = "INSERT INTO customer_loyalty (customer_loyalty_uuid,customer_uuid,desc_type,desc_text,manufacturer_lot_number,update_ts) VALUES (?,?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT customer_loyalty_uuid,customer_uuid,desc_type,desc_text,manufacturer_lot_number,insert_ts,update_ts FROM customer_loyalty WHERE customer_loyalty_uuid=?";
	private static String sqlFindComplaints = "SELECT customer_loyalty_uuid,customer_uuid,desc_type,desc_text,manufacturer_lot_number,insert_ts,update_ts FROM customer_loyalty WHERE customer_loyalty_uuid=?";

	public static void insertBatchMode( Connection con, CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerUuid() );
			pstmt.setString( offset++, customerLoyaltyVo.getDescType() );
			pstmt.setString( offset++, customerLoyaltyVo.getDescText() );
			pstmt.setString( offset++, customerLoyaltyVo.getManufacturerLotNumber() );
			pstmt.setTimestamp( offset++, customerLoyaltyVo.getUpdateTs() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (pstmt != null)
				pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
		}
	}

	public static void insert( SupplyBlockchainConfig supplyBlockchainConfig, CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerUuid() );
			pstmt.setString( offset++, customerLoyaltyVo.getDescType() );
			pstmt.setString( offset++, customerLoyaltyVo.getDescText() );
			pstmt.setString( offset++, customerLoyaltyVo.getManufacturerLotNumber() );
			pstmt.setTimestamp( offset++, customerLoyaltyVo.getUpdateTs() );
			pstmt.execute();
			con.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if( con != null ) {
				try {
					con.rollback();
				} catch(Exception erb ) {
					logger.warn(e.getMessage(), e);
				}
			}
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception exCon) {
				logger.warn(exCon.getMessage());
			}
		}
	}

	public static void deleteByPk( SupplyBlockchainConfig supplyBlockchainConfig, CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if( con != null ) {
				try {
					con.rollback();
				} catch(Exception erb ) {
					logger.warn(e.getMessage(), e);
				}
			}
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception exCon) {
				logger.warn(exCon.getMessage());
			}
		}
	}

	public static void deleteBatchMode( Connection con, CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
		}
	}

	public static CustomerLoyaltyVo findByPk( SupplyBlockchainConfig supplyBlockchainConfig, CustomerLoyaltyVo customerLoyaltyVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, customerLoyaltyVo.getCustomerLoyaltyUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new CustomerLoyaltyVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				logger.warn(e);
			}
		}
	}

	
	public static List<CustomerLoyaltyAllVo> findComplaints( ) throws Exception {
		try (	Connection con = PooledDataSource.getInstance().getConnection();
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
}
