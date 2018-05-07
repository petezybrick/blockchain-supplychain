package com.petezybrick.bcsc.service.orcdev;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.petezybrick.bcsc.service.database.PooledDataSource;
import com.petezybrick.bcsc.service.orc.OrcCommon;


public class SupplierBlockTransactionDao {
	private static final Logger logger = LogManager.getLogger(SupplierBlockTransactionDao.class);
	private static final String schemaName = "supplier_block_transaction";
	private static final String schemaVersion = "1.0";
	private static String sqlFindTemplate = "SELECT supplier_block_transaction_uuid,supplier_block_uuid,transaction_id,encoded_public_key_from,encoded_public_key_to,signature,transaction_sequence FROM supplier_block_transaction WHERE <Criteria Here>";



	public static void writeOrc( String pathNameExt, List<List<Object>> rowsCols ) throws Exception {
		OrcCommon.write( pathNameExt, schemaName, schemaVersion, rowsCols );
	}
	public static SupplierBlockTransactionVo findByTemplate( SupplierBlockTransactionVo supplierBlockTransactionVo ) throws Exception {
		try( Connection con = PooledDataSource.getInstance().getConnection();
		     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {
			con.setAutoCommit(true);
			int offset = 1;
			// pstmt.setString(offset++, supplierBlockTransactionVo.getAttribute( );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierBlockTransactionVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
