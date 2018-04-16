package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;


public class SupplierBlockTransactionDao {
	private static final Logger logger = LogManager.getLogger(SupplierBlockTransactionDao.class);
	private static String sqlDeleteAll = "DELETE FROM supplier_block_transaction";
	private static String sqlDeleteByPk = "DELETE FROM supplier_block_transaction WHERE supplier_block_transaction_uuid=?";
	private static String sqlInsert = "INSERT INTO supplier_block_transaction (supplier_block_transaction_uuid,supplier_block_uuid,transaction_id,encoded_public_key_from,encoded_public_key_to,signature,transaction_sequence,update_ts) VALUES (?,?,?,?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT supplier_block_transaction_uuid,supplier_block_uuid,transaction_id,encoded_public_key_from,encoded_public_key_to,signature,transaction_sequence,insert_ts,update_ts FROM supplier_block_transaction WHERE supplier_block_transaction_uuid=?";


	public static void deleteAll( ) throws Exception {
		try (Connection con = PooledDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}
	
	
	public static void insertBatchList( Connection con, List<SupplierBlockTransactionVo> supplierBlockTransactionVos ) throws Exception {
		final int BATCH_SIZE = 1000;
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int cnt = 0;
			con.setAutoCommit(false);
			for( SupplierBlockTransactionVo supplierBlockTransactionVo : supplierBlockTransactionVos ) {
				int offset = 1;
				pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockTransactionUuid() );
				pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockUuid() );
				pstmt.setString( offset++, supplierBlockTransactionVo.getTransactionId() );
				pstmt.setString( offset++, supplierBlockTransactionVo.getEncodedPublicKeyFrom() );
				pstmt.setString( offset++, supplierBlockTransactionVo.getEncodedPublicKeyTo() );
				pstmt.setBytes( offset++, supplierBlockTransactionVo.getSignature() );
				pstmt.setInt( offset++, supplierBlockTransactionVo.getTransactionSequence() );
				pstmt.setTimestamp( offset++, supplierBlockTransactionVo.getUpdateTs() );
				pstmt.addBatch();
				if( cnt % BATCH_SIZE == 0 ) {
					pstmt.executeBatch();
					con.commit();
				}
				if( supplierBlockTransactionVo.getSupplierTransactionVo() != null ) { 
					SupplierTransactionDao.insertBatchMode( con, supplierBlockTransactionVo.getSupplierTransactionVo());
				}
			}
			pstmt.executeBatch();
			con.commit();
		}
	}

	
	public static void insertBatchMode( Connection con, SupplierBlockTransactionVo supplierBlockTransactionVo ) throws Exception {		
		try ( PreparedStatement pstmt = con.prepareStatement(sqlInsert); ) {
			int offset = 1;
			pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockTransactionUuid() );
			pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockUuid() );
			pstmt.setString( offset++, supplierBlockTransactionVo.getTransactionId() );
			pstmt.setString( offset++, supplierBlockTransactionVo.getEncodedPublicKeyFrom() );
			pstmt.setString( offset++, supplierBlockTransactionVo.getEncodedPublicKeyTo() );
			pstmt.setBytes( offset++, supplierBlockTransactionVo.getSignature() );
			pstmt.setInt( offset++, supplierBlockTransactionVo.getTransactionSequence() );
			pstmt.setTimestamp( offset++, supplierBlockTransactionVo.getUpdateTs() );
			pstmt.execute();
			if( supplierBlockTransactionVo.getSupplierTransactionVo() != null ) { 
				SupplierTransactionDao.insertBatchMode( con, supplierBlockTransactionVo.getSupplierTransactionVo());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} 
	}
	

	public static void insert( SupplierBlockTransactionVo supplierBlockTransactionVo ) throws Exception {
		try (Connection con = PooledDataSource.getInstance().getConnection();) {
			con.setAutoCommit(true);
			insert( con, supplierBlockTransactionVo );
		}
	}
		

	public static void insert( Connection con, SupplierBlockTransactionVo supplierBlockTransactionVo ) throws Exception {
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert) ){
			int offset = 1;
			pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockTransactionUuid() );
			pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockUuid() );
			pstmt.setString( offset++, supplierBlockTransactionVo.getTransactionId() );
			pstmt.setString( offset++, supplierBlockTransactionVo.getEncodedPublicKeyFrom() );
			pstmt.setString( offset++, supplierBlockTransactionVo.getEncodedPublicKeyTo() );
			pstmt.setBytes( offset++, supplierBlockTransactionVo.getSignature() );
			pstmt.setInt( offset++, supplierBlockTransactionVo.getTransactionSequence() );
			pstmt.setTimestamp( offset++, supplierBlockTransactionVo.getUpdateTs() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void deleteByPk( SupplyBlockchainConfig supplyBlockchainConfig, SupplierBlockTransactionVo supplierBlockTransactionVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockTransactionUuid() );
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

	public static void deleteBatchMode( Connection con, SupplierBlockTransactionVo supplierBlockTransactionVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockTransactionUuid() );
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

	public static SupplierBlockTransactionVo findByPk( SupplyBlockchainConfig supplyBlockchainConfig, SupplierBlockTransactionVo supplierBlockTransactionVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = PooledDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, supplierBlockTransactionVo.getSupplierBlockTransactionUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierBlockTransactionVo(rs);
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
