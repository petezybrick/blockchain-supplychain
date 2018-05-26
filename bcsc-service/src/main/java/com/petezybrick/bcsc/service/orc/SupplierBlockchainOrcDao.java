package com.petezybrick.bcsc.service.orc;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.petezybrick.bcsc.service.database.SupplierDataSource;
import com.petezybrick.bcsc.service.orc.OrcCommon;


public class SupplierBlockchainOrcDao {
	private static final Logger logger = LogManager.getLogger(SupplierBlockchainOrcDao.class);
	private static final String schemaName = "supplier_blockchain";
	private static final String schemaVersion = "1.0";
	private static String sqlFindTemplate = "SELECT supplier_blockchain_uuid,supplier_type FROM supplier_blockchain WHERE <Criteria Here>";



	public static void writeOrc( String targetPath, String targetNameExt, List<List<Object>> rowsCols ) throws Exception {
		OrcCommon.write( targetPath, targetNameExt, schemaName, schemaVersion, rowsCols );
	}


	public static SupplierBlockchainOrcVo findByTemplate( SupplierBlockchainOrcVo supplierBlockchainOrcVo ) throws Exception {
		try( Connection con = SupplierDataSource.getInstance().getConnection();
		     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {
			con.setAutoCommit(true);
			int offset = 1;
			// pstmt.setString(offset++, supplierBlockchainOrcVo.getAttribute( );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierBlockchainOrcVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
