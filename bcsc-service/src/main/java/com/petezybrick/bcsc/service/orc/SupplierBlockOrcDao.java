package com.petezybrick.bcsc.service.orc;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.petezybrick.bcsc.service.database.PooledDataSource;
import com.petezybrick.bcsc.service.orc.OrcCommon;


public class SupplierBlockOrcDao {
	private static final Logger logger = LogManager.getLogger(SupplierBlockOrcDao.class);
	private static final String schemaName = "supplier_block";
	private static final String schemaVersion = "1.0";
	private static String sqlFindTemplate = "SELECT supplier_block_uuid,supplier_blockchain_uuid,hash,previous_hash,block_timestamp,block_sequence FROM supplier_block WHERE <Criteria Here>";



	public static void writeOrc( String pathNameExt, List<List<Object>> rowsCols ) throws Exception {
		OrcCommon.write( pathNameExt, schemaName, schemaVersion, rowsCols );
	}


	public static SupplierBlockOrcVo findByTemplate( SupplierBlockOrcVo supplierBlockOrcVo ) throws Exception {
		try( Connection con = PooledDataSource.getInstance().getConnection();
		     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {
			con.setAutoCommit(true);
			int offset = 1;
			// pstmt.setString(offset++, supplierBlockOrcVo.getAttribute( );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new SupplierBlockOrcVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
