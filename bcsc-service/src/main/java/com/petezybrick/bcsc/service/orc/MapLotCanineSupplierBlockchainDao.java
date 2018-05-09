package com.petezybrick.bcsc.service.orc;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.petezybrick.bcsc.service.database.PooledDataSource;
import com.petezybrick.bcsc.service.orc.OrcCommon;


public class MapLotCanineSupplierBlockchainDao {
	private static final Logger logger = LogManager.getLogger(MapLotCanineSupplierBlockchainDao.class);
	private static final String schemaName = "map_lot_canine_supplier_blockchain";
	private static final String schemaVersion = "1.0";
	private static String sqlFindTemplate = "SELECT map_lot_canine_supplier_blockchain_uuid,lot_canine_uuid,supplier_blockchain_uuid,ingredient_sequence,ingredient_name FROM map_lot_canine_supplier_blockchain WHERE <Criteria Here>";



	public static void writeOrc( String pathNameExt, List<List<Object>> rowsCols ) throws Exception {
		OrcCommon.write( pathNameExt, schemaName, schemaVersion, rowsCols );
	}
	public static MapLotCanineSupplierBlockchainVo findByTemplate( MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo ) throws Exception {
		try( Connection con = PooledDataSource.getInstance().getConnection();
		     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {
			con.setAutoCommit(true);
			int offset = 1;
			// pstmt.setString(offset++, mapLotCanineSupplierBlockchainVo.getAttribute( );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new MapLotCanineSupplierBlockchainVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
