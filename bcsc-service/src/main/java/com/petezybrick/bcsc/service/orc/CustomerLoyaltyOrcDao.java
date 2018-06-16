package com.petezybrick.bcsc.service.orc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.service.database.SupplierDataSource;


public class CustomerLoyaltyOrcDao {
	private static final Logger logger = LogManager.getLogger(CustomerLoyaltyOrcDao.class);
	private static final String schemaName = "customer_loyalty";
	private static final String schemaVersion = "1.0";
	private static String sqlFindTemplate = "SELECT customer_loyalty_uuid,customer_uuid,desc_type,desc_text,manufacturer_lot_number FROM customer_loyalty WHERE <Criteria Here>";



	public static void writeOrc( String targetPath, String targetNameExt, List<List<Object>> rowsCols ) throws Exception {
		OrcCommon.write( targetPath, targetNameExt, schemaName, schemaVersion, rowsCols );
	}


	public static CustomerLoyaltyOrcVo findByTemplate( CustomerLoyaltyOrcVo customerLoyaltyOrcVo ) throws Exception {
		try( Connection con = SupplierDataSource.getInstance().getConnection();
		     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {
			con.setAutoCommit(true);
			int offset = 1;
			// pstmt.setString(offset++, customerLoyaltyOrcVo.getAttribute( );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new CustomerLoyaltyOrcVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
