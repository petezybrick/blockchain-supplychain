package com.petezybrick.bcsc.webui;

import java.security.Security;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.database.SupplierDataSource;

public class Bootstrap extends HttpServlet {
	private static final Logger logger = LogManager.getLogger(Bootstrap.class);
	private static final long serialVersionUID = -8767531775104562766L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			logger.info("+++++ Starting");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			SupplyBlockchainConfig supplyBlockchainConfig = SupplyBlockchainConfig.getInstance(System.getenv("ENV"), System.getenv("CONTACT_POINT"),
					System.getenv("KEYSPACE_NAME"));
			SupplierDataSource.getInstance(supplyBlockchainConfig);
			logger.info("+++++ Started");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
