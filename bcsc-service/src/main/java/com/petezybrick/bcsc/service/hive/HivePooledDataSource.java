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

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;



/**
 * The Class PooledDataSource.
 */
public class HivePooledDataSource {
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HivePooledDataSource.class);
	
	/** The pooled data source. */
	private static HivePooledDataSource pooledDataSource;
	
	/** The bds. */
	private BasicDataSource bds;
	
	
	/**
	 * Instantiates a new pooled data source.
	 *
	 * @param masterConfig the master config
	 * @throws Exception the exception
	 */
	private HivePooledDataSource( SupplyBlockchainConfig masterConfig ) throws Exception {
		logger.debug("JDBC: login {}, class {}, url {}", masterConfig.getJdbcLoginHive(), masterConfig.getJdbcDriverClassNameHive(), masterConfig.getJdbcUrlHive());
		bds = new BasicDataSource();
		bds.setDriverClassName(masterConfig.getJdbcDriverClassNameHive());
		bds.setUrl(masterConfig.getJdbcUrlHive());
		bds.setUsername(masterConfig.getJdbcLoginHive());
		bds.setPassword(masterConfig.getJdbcPasswordHive());
		// Optimize for bulk inserts
		bds.setDefaultAutoCommit(false);
	}
		
		
	/**
	 * Gets the single instance of PooledDataSource.
	 *
	 * @param masterConfig the master config
	 * @return single instance of PooledDataSource
	 * @throws Exception the exception
	 */
	public static HivePooledDataSource getInstance( SupplyBlockchainConfig masterConfig ) throws Exception {
		if(pooledDataSource != null ) return pooledDataSource;
		else {
			pooledDataSource = new HivePooledDataSource(masterConfig);
			return pooledDataSource;
		}
	}
	
	public static HivePooledDataSource getInstance( ) throws Exception {
		if(pooledDataSource != null ) return pooledDataSource;
		else throw new Exception("SupplyBlockchainConfig not initialized");
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
