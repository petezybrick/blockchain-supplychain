package com.petezybrick.bcsc.webui;

import java.security.Security;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.database.SupplierDataSource;

public class Bootstrap extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			SupplyBlockchainConfig supplyBlockchainConfig = SupplyBlockchainConfig.getInstance(System.getenv("ENV"), System.getenv("CONTACT_POINT"),
					System.getenv("KEYSPACE_NAME"));
			SupplierDataSource.getInstance(supplyBlockchainConfig);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
