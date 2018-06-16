package com.petezybrick.bcsc.service.orc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.service.database.SupplierDataSource;


public class CustomerOrcDao {
	private static final Logger logger = LogManager.getLogger(CustomerOrcDao.class);
	private static final String schemaName = "customer";
	private static final String schemaVersion = "1.0";
	private static String sqlFindTemplate = "SELECT customer_uuid,first_name,last_name,email_address FROM customer WHERE <Criteria Here>";


	public static void writeOrc( String targetPath, String targetNameExt, List<List<Object>> rowsCols ) throws Exception {
		OrcCommon.write( targetPath, targetNameExt, schemaName, schemaVersion, rowsCols );
	}


	public static CustomerOrcVo findByTemplate( CustomerOrcVo customerOrcVo ) throws Exception {
		try( Connection con = SupplierDataSource.getInstance().getConnection();
		     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {
			con.setAutoCommit(true);
			int offset = 1;
			// pstmt.setString(offset++, customerOrcVo.getAttribute( );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new CustomerOrcVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
