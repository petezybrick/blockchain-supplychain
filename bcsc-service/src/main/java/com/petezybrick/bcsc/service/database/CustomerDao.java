package com.petezybrick.bcsc.service.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CustomerDao {
	private static final Logger logger = LogManager.getLogger(CustomerDao.class);
	private static String sqlDeleteAll = "DELETE FROM customer";
	private static String sqlDeleteByPk = "DELETE FROM customer WHERE customer_uuid=?";
	private static String sqlInsert = "INSERT INTO customer (customer_uuid,first_name,last_name,email_address) VALUES (?,?,?,?)";
	private static String sqlFindByPk = "SELECT customer_uuid,first_name,last_name,email_address,insert_ts,update_ts FROM customer WHERE customer_uuid=?";

	public static void insertBatchList( Connection con, List<CustomerVo> customerVos ) throws Exception {
		final int BATCH_SIZE = 1000;
		try (PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int cnt = 0;
			for( CustomerVo customerVo : customerVos ) {
				int offset = 1;
				pstmt.setString( offset++, customerVo.getCustomerUuid() );
				pstmt.setString( offset++, customerVo.getFirstName() );
				pstmt.setString( offset++, customerVo.getLastName() );
				pstmt.setString( offset++, customerVo.getEmailAddress() );
				pstmt.addBatch();
				if( cnt % BATCH_SIZE == 0 ) {
					pstmt.executeBatch();
				}
			}
			pstmt.executeBatch();
		}
	}


	public static void insertBatchMode( Connection con, CustomerVo customerVo ) throws Exception {
		try  (PreparedStatement pstmt = con.prepareStatement(sqlInsert);){
			int offset = 1;
			pstmt.setString( offset++, customerVo.getCustomerUuid() );
			pstmt.setString( offset++, customerVo.getFirstName() );
			pstmt.setString( offset++, customerVo.getLastName() );
			pstmt.setString( offset++, customerVo.getEmailAddress() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void insert( CustomerVo customerVo ) throws Exception {
		try (	Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlInsert);	){
			con.setAutoCommit(false);
			int offset = 1;
			pstmt.setString( offset++, customerVo.getCustomerUuid() );
			pstmt.setString( offset++, customerVo.getFirstName() );
			pstmt.setString( offset++, customerVo.getLastName() );
			pstmt.setString( offset++, customerVo.getEmailAddress() );
			pstmt.execute();
			con.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void deleteByPk( CustomerVo customerVo ) throws Exception {
		try (	Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlDeleteByPk);	){
			con.setAutoCommit(true);
			int offset = 1;
			pstmt.setString( offset++, customerVo.getCustomerUuid() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static void deleteBatchMode( Connection con, CustomerVo customerVo ) throws Exception {
		try (	PreparedStatement pstmt = con.prepareStatement(sqlDeleteByPk);	){
			int offset = 1;
			pstmt.setString( offset++, customerVo.getCustomerUuid() );
			pstmt.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public static CustomerVo findByPk( CustomerVo customerVo ) throws Exception {
		try (	Connection con = SupplierDataSource.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(sqlFindByPk);	){
			con.setAutoCommit(true);
			int offset = 1;
			pstmt.setString( offset++, customerVo.getCustomerUuid() );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new CustomerVo(rs);
			else return null;
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
