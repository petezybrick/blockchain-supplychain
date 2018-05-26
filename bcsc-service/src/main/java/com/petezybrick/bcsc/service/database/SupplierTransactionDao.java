package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;


public class SupplierTransactionDao {
	private static final Logger logger = LogManager.getLogger(SupplierTransactionDao.class);
	private static String sqlDeleteAll = "DELETE FROM supplier_transaction";
	private static String sqlDeleteByPk = "DELETE FROM supplier_transaction WHERE supplier_transaction_uuid=?";
	private static String sqlInsert = "INSERT INTO supplier_transaction (supplier_transaction_uuid,supplier_block_transaction_uuid,supplier_uuid,supplier_lot_number,item_number,description,qty,units,shipped_date_iso8601,rcvd_date_iso8601,update_ts) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT supplier_transaction_uuid,supplier_block_transaction_uuid,supplier_uuid,supplier_lot_number,item_number,description,qty,units,shipped_date_iso8601,rcvd_date_iso8601,insert_ts,update_ts FROM supplier_transaction WHERE supplier_transaction_uuid=?";


	public static void deleteAll( ) throws Exception {
		try (Connection con = SupplierDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}
	
	public static void insertBatchMode( Connection con, SupplierTransactionVo supplierTransactionVo ) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert);) {
			int offset = 1;
			pstmt.setString( offset++, supplierTransactionVo.getSupplierTransactionUuid() );
			pstmt.setString( offset++, supplierTransactionVo.getSupplierBlockTransactionUuid() );
			pstmt.setString( offset++, supplierTransactionVo.getSupplierUuid() );
			pstmt.setString( offset++, supplierTransactionVo.getSupplierLotNumber() );
			pstmt.setString( offset++, supplierTransactionVo.getItemNumber() );
			pstmt.setString( offset++, supplierTransactionVo.getDescription() );
			pstmt.setInt( offset++, supplierTransactionVo.getQty() );
			pstmt.setString( offset++, supplierTransactionVo.getUnits() );
			pstmt.setTimestamp( offset++, supplierTransactionVo.getShippedDateIso8601() );
			pstmt.setTimestamp( offset++, supplierTransactionVo.getRcvdDateIso8601() );
			pstmt.setTimestamp( offset++, supplierTransactionVo.getUpdateTs() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void insert( SupplyBlockchainConfig supplyBlockchainConfig, SupplierTransactionVo supplierTransactionVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierTransactionVo.getSupplierTransactionUuid() );
			pstmt.setString( offset++, supplierTransactionVo.getSupplierBlockTransactionUuid() );
			pstmt.setString( offset++, supplierTransactionVo.getSupplierUuid() );
			pstmt.setString( offset++, supplierTransactionVo.getSupplierLotNumber() );
			pstmt.setString( offset++, supplierTransactionVo.getItemNumber() );
			pstmt.setString( offset++, supplierTransactionVo.getDescription() );
			pstmt.setInt( offset++, supplierTransactionVo.getQty() );
			pstmt.setString( offset++, supplierTransactionVo.getUnits() );
			pstmt.setTimestamp( offset++, supplierTransactionVo.getShippedDateIso8601() );
			pstmt.setTimestamp( offset++, supplierTransactionVo.getRcvdDateIso8601() );
			pstmt.setTimestamp( offset++, supplierTransactionVo.getUpdateTs() );
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

	public static void deleteByPk( SupplyBlockchainConfig supplyBlockchainConfig, SupplierTransactionVo supplierTransactionVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierTransactionVo.getSupplierTransactionUuid() );
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

	public static void deleteBatchMode( Connection con, SupplierTransactionVo supplierTransactionVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierTransactionVo.getSupplierTransactionUuid() );
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

	public static SupplierTransactionVo findByPk( SupplyBlockchainConfig supplyBlockchainConfig, SupplierTransactionVo supplierTransactionVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierTransactionVo.getSupplierTransactionUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierTransactionVo(rs);
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
}
