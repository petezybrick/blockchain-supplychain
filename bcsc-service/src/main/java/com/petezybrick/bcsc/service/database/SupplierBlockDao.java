package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;


public class SupplierBlockDao {
	private static final Logger logger = LogManager.getLogger(SupplierBlockDao.class);
	private static String sqlDeleteAll = "DELETE FROM supplier_block";
	private static String sqlDeleteByPk = "DELETE FROM supplier_block WHERE supplier_block_uuid=?";
	private static String sqlInsert = "INSERT INTO supplier_block (supplier_block_uuid,supplier_blockchain_uuid,hash,previous_hash,block_timestamp,block_sequence,update_ts) VALUES (?,?,?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT supplier_block_uuid,supplier_blockchain_uuid,hash,previous_hash,block_timestamp,block_sequence,insert_ts,update_ts FROM supplier_block WHERE supplier_block_uuid=?";


	public static void deleteAll( ) throws Exception {
		try (Connection con = PooledDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}
	
	
	public static void insertBatchList( Connection con, List<SupplierBlockVo> supplierBlockVos ) throws Exception {
		final int BATCH_SIZE = 1000;
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int cnt = 0;
			for( SupplierBlockVo supplierBlockVo : supplierBlockVos ) {
				int offset = 1;
				pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
				pstmt.setString( offset++, supplierBlockVo.getSupplierBlockchainUuid() );
				pstmt.setString( offset++, supplierBlockVo.getHash() );
				pstmt.setString( offset++, supplierBlockVo.getPreviousHash() );
				pstmt.setTimestamp( offset++, supplierBlockVo.getBlockTimestamp() );
				pstmt.setInt( offset++, supplierBlockVo.getBlockSequence() );
				pstmt.setTimestamp( offset++, supplierBlockVo.getUpdateTs() );
				pstmt.addBatch();
				if( cnt % BATCH_SIZE == 0 ) {
					pstmt.executeBatch();
				}
				if( supplierBlockVo.getSupplierBlockTransactionVo() != null ) {
					SupplierBlockTransactionDao.insertBatchMode( con, supplierBlockVo.getSupplierBlockTransactionVo());
				}
			}
			pstmt.executeBatch();
		}
	}

	
	public static void insertBatchMode( Connection con, SupplierBlockVo supplierBlockVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockchainUuid() );
			pstmt.setString( offset++, supplierBlockVo.getHash() );
			pstmt.setString( offset++, supplierBlockVo.getPreviousHash() );
			pstmt.setTimestamp( offset++, supplierBlockVo.getBlockTimestamp() );
			pstmt.setInt( offset++, supplierBlockVo.getBlockSequence() );
			pstmt.setTimestamp( offset++, supplierBlockVo.getUpdateTs() );
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

	public static void insert( SupplyBlockchainConfig supplyBlockchainConfig, SupplierBlockVo supplierBlockVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockchainUuid() );
			pstmt.setString( offset++, supplierBlockVo.getHash() );
			pstmt.setString( offset++, supplierBlockVo.getPreviousHash() );
			pstmt.setTimestamp( offset++, supplierBlockVo.getBlockTimestamp() );
			pstmt.setInt( offset++, supplierBlockVo.getBlockSequence() );
			pstmt.setTimestamp( offset++, supplierBlockVo.getUpdateTs() );
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

	public static void deleteByPk( SupplyBlockchainConfig supplyBlockchainConfig, SupplierBlockVo supplierBlockVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
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

	public static void deleteBatchMode( Connection con, SupplierBlockVo supplierBlockVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
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

	public static SupplierBlockVo findByPk( SupplyBlockchainConfig supplyBlockchainConfig, SupplierBlockVo supplierBlockVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockVo.getSupplierBlockUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierBlockVo(rs);
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
