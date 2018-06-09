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
package com.petezybrick.bcsc.service.hive;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;



/**
 * The Class PooledDataSource.
 */
public class HiveDataSource {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HiveDataSource.class);
	
	/** The pooled data source. */
	private static HiveDataSource pooledDataSource;
	
	
	/**
	 * Instantiates a new pooled data source.
	 *
	 * @param masterConfig the master config
	 * @throws Exception the exception
	 */
	private HiveDataSource( SupplyBlockchainConfig masterConfig ) throws Exception {
		logger.debug("JDBC: login {}, class {}, url {}", masterConfig.getJdbcLoginHive(), masterConfig.getJdbcDriverClassNameHive(), masterConfig.getJdbcUrlHive());
	}
		
		
	/**
	 * Gets the single instance of PooledDataSource.
	 *
	 * @param masterConfig the master config
	 * @return single instance of PooledDataSource
	 * @throws Exception the exception
	 */
	public static HiveDataSource getInstance( SupplyBlockchainConfig masterConfig ) throws Exception {
		if(pooledDataSource != null ) return pooledDataSource;
		else {
			pooledDataSource = new HiveDataSource(masterConfig);
			return pooledDataSource;
		}
	}
	
	public static HiveDataSource getInstance( ) throws Exception {
		if(pooledDataSource != null ) return pooledDataSource;
		else {
			pooledDataSource = new HiveDataSource(SupplyBlockchainConfig.getInstance());
			return pooledDataSource;
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
			Class.forName(SupplyBlockchainConfig.getInstance().getJdbcDriverClassNameHive());
			// Connect to Hive
			Connection con = DriverManager.getConnection(SupplyBlockchainConfig.getInstance().getJdbcUrlHive(),
					SupplyBlockchainConfig.getInstance().getJdbcLoginHive(),
					SupplyBlockchainConfig.getInstance().getJdbcPasswordHive());
			return con;
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
		logger.warn("AutoCommit not supported for Hive");
	}
	
}
