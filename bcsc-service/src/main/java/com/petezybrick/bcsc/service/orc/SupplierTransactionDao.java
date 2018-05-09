package com.petezybrick.bcsc.service.orc;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.petezybrick.bcsc.service.database.PooledDataSource;
import com.petezybrick.bcsc.service.orc.OrcCommon;


public class SupplierTransactionDao {
	private static final Logger logger = LogManager.getLogger(SupplierTransactionDao.class);
	private static final String schemaName = "supplier_transaction";
	private static final String schemaVersion = "1.0";
	private static String sqlFindTemplate = "SELECT supplier_transaction_uuid,supplier_block_transaction_uuid,supplier_uuid,supplier_lot_number,item_number,description,qty,units,shipped_date_iso8601,rcvd_date_iso8601 FROM supplier_transaction WHERE <Criteria Here>";



	public static void writeOrc( String pathNameExt, List<List<Object>> rowsCols ) throws Exception {
		OrcCommon.write( pathNameExt, schemaName, schemaVersion, rowsCols );
	}
	public static SupplierTransactionVo findByTemplate( SupplierTransactionVo supplierTransactionVo ) throws Exception {
		try( Connection con = PooledDataSource.getInstance().getConnection();
		     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {
			con.setAutoCommit(true);
			int offset = 1;
			// pstmt.setString(offset++, supplierTransactionVo.getAttribute( );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierTransactionVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
