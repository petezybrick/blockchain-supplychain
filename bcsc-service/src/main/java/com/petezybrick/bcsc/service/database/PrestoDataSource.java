/**
 *    Copyright 2016, 2017 Peter Zybrick and others.
 * 
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 * 
 *        http://www.apache.org/licenses/LICENSE-2.0
 * 
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 * 
 * @author  Pete Zybrick
 * @version 1.0.0, 2017-09
 * 
 */
package com.petezybrick.bcsc.service.database;

import java.sql.Connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;



/**
 * The Class PooledDataSource.
 */
public class PrestoDataSource {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(PrestoDataSource.class);
	
	/** The pooled data source. */
	private static PrestoDataSource prestoDataSource;
	
	/** The bds. */
	private BasicDataSource bds;
	
	
	/**
	 * Instantiates a new pooled data source.
	 *
	 * @param masterConfig the master config
	 * @throws Exception the exception
	 */
	private PrestoDataSource( SupplyBlockchainConfig masterConfig ) throws Exception {
		logger.debug("JDBC: login {}, class {}, url {}", masterConfig.getJdbcLoginPresto(), masterConfig.getJdbcDriverClassNamePresto(), masterConfig.getJdbcUrlPresto());
		bds = new BasicDataSource();
//		bds.setDriverClassName(masterConfig.getJdbcDriverClassName());
//		bds.setUrl(masterConfig.getJdbcUrl());
//		bds.setUsername(masterConfig.getJdbcLogin());
//		bds.setPassword(masterConfig.getJdbcPassword());		
		bds.setDriverClassName(masterConfig.getJdbcDriverClassNamePresto());
		bds.setUrl(masterConfig.getJdbcUrlPresto());
		bds.setUsername(masterConfig.getJdbcLoginPresto());
		bds.setPassword(masterConfig.getJdbcPasswordPresto());
		bds.setDefaultAutoCommit(true);
	}
		
		
	/**
	 * Gets the single instance of PooledDataSource.
	 *
	 * @param masterConfig the master config
	 * @return single instance of PooledDataSource
	 * @throws Exception the exception
	 */
	public static PrestoDataSource getInstance( SupplyBlockchainConfig masterConfig ) throws Exception {
		if(prestoDataSource != null ) return prestoDataSource;
		else {
			prestoDataSource = new PrestoDataSource(masterConfig);
			return prestoDataSource;
		}
	}
	
	public static PrestoDataSource getInstance( ) throws Exception {
		if(prestoDataSource != null ) return prestoDataSource;
		else {
			prestoDataSource = new PrestoDataSource(SupplyBlockchainConfig.getInstance());
			return prestoDataSource;
		}
	}
	
	
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws Exception the exception
	 */
	public Connection getConnection() throws Exception {
		try {
			return bds.getConnection();
		} catch( Exception e ) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Override default auto commit.
	 *
	 * @param newAutoCommit the new auto commit
	 */
	public void overrideDefaultAutoCommit( boolean newAutoCommit ) {
		bds.setDefaultAutoCommit(newAutoCommit);
	}
	
}
