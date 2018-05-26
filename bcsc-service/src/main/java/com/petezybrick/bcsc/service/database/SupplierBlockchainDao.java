package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;


public class SupplierBlockchainDao {
	private static final Logger logger = LogManager.getLogger(SupplierBlockchainDao.class);
	private static String sqlDeleteAll = "DELETE FROM supplier_blockchain";
	private static String sqlDeleteByPk = "DELETE FROM supplier_blockchain WHERE supplier_blockchain_uuid=?";
	private static String sqlInsert = "INSERT INTO supplier_blockchain (supplier_blockchain_uuid,supplier_type,update_ts) VALUES (?,?,?)";
	private static String sqlFindByPk = "SELECT supplier_blockchain_uuid,supplier_type,insert_ts,update_ts FROM supplier_blockchain WHERE supplier_blockchain_uuid=?";


	public static void deleteAll( ) throws Exception {
		try (Connection con = SupplierDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}
	
	
	public static void insertBatchList( Connection con, List<SupplierBlockchainVo> supplierBlockchainVos ) throws Exception {
		final int BATCH_SIZE = 1000;
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int cnt = 0;
			for( SupplierBlockchainVo supplierBlockchainVo : supplierBlockchainVos ) {
				int offset = 1;
				pstmt.setString( offset++, supplierBlockchainVo.getSupplierBlockchainUuid() );
				pstmt.setString( offset++, supplierBlockchainVo.getSupplierType() );
				pstmt.setTimestamp( offset++, supplierBlockchainVo.getUpdateTs() );
				pstmt.addBatch();
				if( cnt % BATCH_SIZE == 0 ) {
					pstmt.executeBatch();
				}
				if( supplierBlockchainVo.getSupplierBlockVos() != null ) {
					SupplierBlockDao.insertBatchList( con, supplierBlockchainVo.getSupplierBlockVos() );
				}
			}
			pstmt.executeBatch();
		}
	}

	
	public static void insertBatchMode( Connection con, SupplierBlockchainVo supplierBlockchainVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockchainVo.getSupplierBlockchainUuid() );
			pstmt.setString( offset++, supplierBlockchainVo.getSupplierType() );
			pstmt.setTimestamp( offset++, supplierBlockchainVo.getUpdateTs() );
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

	public static void insert( SupplyBlockchainConfig supplyBlockchainConfig, SupplierBlockchainVo supplierBlockchainVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockchainVo.getSupplierBlockchainUuid() );
			pstmt.setString( offset++, supplierBlockchainVo.getSupplierType() );
			pstmt.setTimestamp( offset++, supplierBlockchainVo.getUpdateTs() );
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

	public static void deleteByPk( SupplyBlockchainConfig supplyBlockchainConfig, SupplierBlockchainVo supplierBlockchainVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockchainVo.getSupplierBlockchainUuid() );
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

	public static void deleteBatchMode( Connection con, SupplierBlockchainVo supplierBlockchainVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockchainVo.getSupplierBlockchainUuid() );
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

	public static SupplierBlockchainVo findByPk( SupplyBlockchainConfig supplyBlockchainConfig, SupplierBlockchainVo supplierBlockchainVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockchainVo.getSupplierBlockchainUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierBlockchainVo(rs);
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
