package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;


public class MapLotCanineSupplierBlockchainDao {
	private static String sqlDeleteAll = "DELETE FROM map_lot_canine_supplier_blockchain";
	private static final Logger logger = LogManager.getLogger(MapLotCanineSupplierBlockchainDao.class);
	private static String sqlDeleteByPk = "DELETE FROM map_lot_canine_supplier_blockchain WHERE map_lot_canine_supplier_blockchain_uuid=?";
	private static String sqlInsert = "INSERT INTO map_lot_canine_supplier_blockchain (map_lot_canine_supplier_blockchain_uuid,lot_canine_uuid,supplier_blockchain_uuid,ingredient_sequence,ingredient_name,update_ts) VALUES (?,?,?,?,?,?)";
	private static String sqlFindByPk = "SELECT map_lot_canine_supplier_blockchain_uuid,lot_canine_uuid,supplier_blockchain_uuid,ingredient_sequence,ingredient_name,insert_ts,update_ts FROM map_lot_canine_supplier_blockchain WHERE map_lot_canine_supplier_blockchain_uuid=?";


	public static void deleteAll( ) throws Exception {
		try (Connection con = SupplierDataSource.getInstance().getConnection();
				Statement stmt = con.createStatement();){
			con.setAutoCommit(true);
			stmt.execute( sqlDeleteAll );
		}
	}

	
	public static void insertBatchList( Connection con, List<MapLotCanineSupplierBlockchainVo> mapLotCanineSupplierBlockchainVos ) throws Exception {
		final int BATCH_SIZE = 1000;
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int cnt = 0;
			for( MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo : mapLotCanineSupplierBlockchainVos ) {
				int offset = 1;
				pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getMapLotCanineSupplierBlockchainUuid() );
				pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getLotCanineUuid() );
				pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getSupplierBlockchainUuid() );
				pstmt.setInt( offset++, mapLotCanineSupplierBlockchainVo.getIngredientSequence() );
				pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getIngredientName() );
				pstmt.setTimestamp( offset++, mapLotCanineSupplierBlockchainVo.getUpdateTs() );
				pstmt.addBatch();
				if( cnt % BATCH_SIZE == 0 ) {
					pstmt.executeBatch();
				}
			}
			pstmt.executeBatch();
		}
	}


	public static void insertBatchMode( Connection con, MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getMapLotCanineSupplierBlockchainUuid() );
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getLotCanineUuid() );
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getSupplierBlockchainUuid() );
			pstmt.setInt( offset++, mapLotCanineSupplierBlockchainVo.getIngredientSequence() );
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getIngredientName() );
			pstmt.setTimestamp( offset++, mapLotCanineSupplierBlockchainVo.getUpdateTs() );
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

	public static void insert( SupplyBlockchainConfig supplyBlockchainConfig, MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sqlInsert);
			int offset = 1;
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getMapLotCanineSupplierBlockchainUuid() );
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getLotCanineUuid() );
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getSupplierBlockchainUuid() );
			pstmt.setInt( offset++, mapLotCanineSupplierBlockchainVo.getIngredientSequence() );
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getIngredientName() );
			pstmt.setTimestamp( offset++, mapLotCanineSupplierBlockchainVo.getUpdateTs() );
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

	public static void deleteByPk( SupplyBlockchainConfig supplyBlockchainConfig, MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getMapLotCanineSupplierBlockchainUuid() );
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

	public static void deleteBatchMode( Connection con, MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo ) throws Exception {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sqlDeleteByPk);
			int offset = 1;
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getMapLotCanineSupplierBlockchainUuid() );
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

	public static MapLotCanineSupplierBlockchainVo findByPk( SupplyBlockchainConfig supplyBlockchainConfig, MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo ) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SupplierDataSource.getInstance(supplyBlockchainConfig).getConnection();
			con.setAutoCommit(true);
			pstmt = con.prepareStatement(sqlFindByPk);
			int offset = 1;
			pstmt.setString( offset++, mapLotCanineSupplierBlockchainVo.getMapLotCanineSupplierBlockchainUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new MapLotCanineSupplierBlockchainVo(rs);
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
