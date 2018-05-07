package com.petezybrick.bcsc.service.orcdev;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.petezybrick.bcsc.service.database.PooledDataSource;
import com.petezybrick.bcsc.service.orc.OrcCommon;


public class LotCanineDao {
	private static final Logger logger = LogManager.getLogger(LotCanineDao.class);
	private static final String schemaName = "lot_canine";
	private static final String schemaVersion = "1.0";
	private static String sqlFindTemplate = "SELECT lot_canine_uuid,manufacturer_lot_number,lot_filled_date FROM lot_canine WHERE <Criteria Here>";



	public static void writeOrc( String pathNameExt, List<List<Object>> rowsCols ) throws Exception {
		OrcCommon.write( pathNameExt, schemaName, schemaVersion, rowsCols );
	}
	public static LotCanineVo findByTemplate( LotCanineVo lotCanineVo ) throws Exception {
		try( Connection con = PooledDataSource.getInstance().getConnection();
		     PreparedStatement pstmt = con.prepareStatement(sqlFindTemplate); ) {
			con.setAutoCommit(true);
			int offset = 1;
			// pstmt.setString(offset++, lotCanineVo.getAttribute( );
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) return new LotCanineVo(rs);
			else return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
