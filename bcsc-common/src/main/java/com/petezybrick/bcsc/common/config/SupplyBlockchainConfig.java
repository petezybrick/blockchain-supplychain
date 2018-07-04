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
package com.petezybrick.bcsc.common.config;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.petezybrick.bcsc.common.utils.BlockchainUtils;


/**
 * The Class SupplyBlockchainConfig.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public class SupplyBlockchainConfig implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2980557216488140670L;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SupplyBlockchainConfig.class);
	
	/** The master config json key. */
	private String supplyBlockchainConfigJsonKey;
	
	/** The contact point. */
	@JsonIgnore
	private String contactPoint;
	
	/** The keyspace name. */
	@JsonIgnore
	private String keyspaceName;
	
	/** The master config. */
	private static SupplyBlockchainConfig supplyBlockchainConfig;
	
	/** The jdbc driver class name. */
	private String jdbcDriverClassNameSupplier;
	
	/** The jdbc insert block size. */
	private Integer jdbcInsertBlockSizeSupplier;
	
	/** The jdbc login. */
	private String jdbcLoginSupplier;
	
	/** The jdbc password. */
	private String jdbcPasswordSupplier;
	
	/** The jdbc url. */
	private String jdbcUrlSupplier;	
	
	/** The jdbc driver class name. */
	private String jdbcDriverClassNamePresto;
	
	/** The jdbc login. */
	private String jdbcLoginPresto;
	
	/** The jdbc password. */
	private String jdbcPasswordPresto;
	
	/** The jdbc url. */
	private String jdbcUrlPresto;	
	
	/** The jdbc driver class name. */
	private String jdbcDriverClassNameHive;
	
	/** The jdbc login. */
	private String jdbcLoginHive;
	
	/** The jdbc password. */
	private String jdbcPasswordHive;
	
	/** The jdbc url. */
	private String jdbcUrlHive;	
	
	private String hdfsUri;
	private String hadoopUserName;
	private String hiveMetastoreUri;
	private String targetPathBcscData;


	/**
	 * Instantiates a new master config.
	 */
	public SupplyBlockchainConfig() {
		
	}


	/**
	 * Instantiates a new master config.
	 */
//	private SupplyBlockchainConfig() {
//		
//	}

	
	/**
	 * Gets the single instance of SupplyBlockchainConfig.
	 *
	 * @return single instance of SupplyBlockchainConfig
	 * @throws Exception the exception
	 */
	public static SupplyBlockchainConfig getInstance( ) throws Exception {
		if( SupplyBlockchainConfig.supplyBlockchainConfig == null ) throw new Exception("SupplyBlockchainConfig was never initialized");
			return SupplyBlockchainConfig.supplyBlockchainConfig;
	}

		
	/**
	 * Gets the single instance of SupplyBlockchainConfig.
	 *
	 * @param supplyBlockchainConfigJsonKey the master config json key
	 * @param contactPoint the contact point
	 * @param keyspaceName the keyspace name
	 * @return single instance of SupplyBlockchainConfig
	 * @throws Exception the exception
	 */
	public static SupplyBlockchainConfig getInstance( String supplyBlockchainConfigJsonKey, String contactPoint, String keyspaceName ) throws Exception {
		if( SupplyBlockchainConfig.supplyBlockchainConfig != null ) return SupplyBlockchainConfig.supplyBlockchainConfig;
		SupplyBlockchainConfig supplyBlockchainConfigNew = null;
		final int RETRY_MINUTES = 10;
		long maxWait = System.currentTimeMillis() + (RETRY_MINUTES * 60 * 1000);
		Exception exception = null;
		logger.info("Instantiating SupplyBlockchainConfig");
		if( keyspaceName == null ) keyspaceName = CassandraBaseDao.DEFAULT_KEYSPACE_NAME;
		
		while( true ) {
			try {
				ConfigDao.connect(contactPoint, keyspaceName);
				String rawJson = ConfigDao.findConfigJson(supplyBlockchainConfigJsonKey);
				supplyBlockchainConfigNew = BlockchainUtils.objectMapper.readValue(rawJson, SupplyBlockchainConfig.class);
				supplyBlockchainConfigNew.setContactPoint(contactPoint);
				supplyBlockchainConfigNew.setKeyspaceName(keyspaceName);
				SupplyBlockchainConfig.supplyBlockchainConfig = supplyBlockchainConfigNew;
				return supplyBlockchainConfigNew;
			} catch(Exception e ) {
				exception = e;
				logger.warn(e.getMessage());
			} finally {
				ConfigDao.disconnect();
			}
			if( System.currentTimeMillis() > maxWait ) break;
			logger.debug("retrying Cassandra connection");
			try { Thread.sleep(5000); } catch(Exception e) {}
		}
		throw exception;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public static Logger getLogger() {
		return logger;
	}


	public String getSupplyBlockchainConfigJsonKey() {
		return supplyBlockchainConfigJsonKey;
	}


	public String getContactPoint() {
		return contactPoint;
	}


	public String getKeyspaceName() {
		return keyspaceName;
	}

	
	public String getJdbcDriverClassNameSupplier() {
		return jdbcDriverClassNameSupplier;
	}


	public Integer getJdbcInsertBlockSizeSupplier() {
		return jdbcInsertBlockSizeSupplier;
	}


	public String getJdbcLoginSupplier() {
		return jdbcLoginSupplier;
	}


	public String getJdbcPasswordSupplier() {
		return jdbcPasswordSupplier;
	}


	public String getJdbcUrlSupplier() {
		return jdbcUrlSupplier;
	}


	public SupplyBlockchainConfig setSupplyBlockchainConfigJsonKey(String supplyBlockchainConfigJsonKey) {
		this.supplyBlockchainConfigJsonKey = supplyBlockchainConfigJsonKey;
		return this;
	}


	public SupplyBlockchainConfig setContactPoint(String contactPoint) {
		this.contactPoint = contactPoint;
		return this;
	}


	public SupplyBlockchainConfig setKeyspaceName(String keyspaceName) {
		this.keyspaceName = keyspaceName;
		return this;
	}


	public SupplyBlockchainConfig setJdbcDriverClassNameSupplier(String jdbcDriverClassNameSupplier) {
		this.jdbcDriverClassNameSupplier = jdbcDriverClassNameSupplier;
		return this;
	}


	public SupplyBlockchainConfig setJdbcInsertBlockSizeSupplier(Integer jdbcInsertBlockSizeSupplier) {
		this.jdbcInsertBlockSizeSupplier = jdbcInsertBlockSizeSupplier;
		return this;
	}


	public SupplyBlockchainConfig setJdbcLoginSupplier(String jdbcLoginSupplier) {
		this.jdbcLoginSupplier = jdbcLoginSupplier;
		return this;
	}


	public SupplyBlockchainConfig setJdbcPasswordSupplier(String jdbcPasswordSupplier) {
		this.jdbcPasswordSupplier = jdbcPasswordSupplier;
		return this;
	}


	public SupplyBlockchainConfig setJdbcUrlSupplier(String jdbcUrlSupplier) {
		this.jdbcUrlSupplier = jdbcUrlSupplier;
		return this;
	}


	@Override
	public String toString() {
		return "SupplyBlockchainConfig [supplyBlockchainConfigJsonKey=" + supplyBlockchainConfigJsonKey + ", contactPoint=" + contactPoint + ", keyspaceName="
				+ keyspaceName + ", jdbcDriverClassNameSupplier=" + jdbcDriverClassNameSupplier + ", jdbcInsertBlockSizeSupplier=" + jdbcInsertBlockSizeSupplier
				+ ", jdbcLoginSupplier=" + jdbcLoginSupplier + ", jdbcPasswordSupplier=" + jdbcPasswordSupplier + ", jdbcUrlSupplier=" + jdbcUrlSupplier
				+ ", jdbcDriverClassNamePresto=" + jdbcDriverClassNamePresto + ", jdbcLoginPresto=" + jdbcLoginPresto + ", jdbcPasswordPresto="
				+ jdbcPasswordPresto + ", jdbcUrlPresto=" + jdbcUrlPresto + ", jdbcDriverClassNameHive=" + jdbcDriverClassNameHive + ", jdbcLoginHive="
				+ jdbcLoginHive + ", jdbcPasswordHive=" + jdbcPasswordHive + ", jdbcUrlHive=" + jdbcUrlHive + ", hdfsUri=" + hdfsUri + ", hadoopUserName="
				+ hadoopUserName + ", hiveMetastoreUri=" + hiveMetastoreUri + "]";
	}


	public String getHdfsUri() {
		return hdfsUri;
	}


	public SupplyBlockchainConfig setHdfsUri(String hdfsUri) {
		this.hdfsUri = hdfsUri;
		return this;
	}


	public String getHadoopUserName() {
		return hadoopUserName;
	}


	public SupplyBlockchainConfig setHadoopUserName(String hadoopUserName) {
		this.hadoopUserName = hadoopUserName;
		return this;
	}


	public String getJdbcDriverClassNamePresto() {
		return jdbcDriverClassNamePresto;
	}


	public String getJdbcLoginPresto() {
		return jdbcLoginPresto;
	}


	public String getJdbcPasswordPresto() {
		return jdbcPasswordPresto;
	}


	public String getJdbcUrlPresto() {
		return jdbcUrlPresto;
	}


	public SupplyBlockchainConfig setJdbcDriverClassNamePresto(String jdbcDriverClassNamePresto) {
		this.jdbcDriverClassNamePresto = jdbcDriverClassNamePresto;
		return this;
	}


	public SupplyBlockchainConfig setJdbcLoginPresto(String jdbcLoginPresto) {
		this.jdbcLoginPresto = jdbcLoginPresto;
		return this;
	}


	public SupplyBlockchainConfig setJdbcPasswordPresto(String jdbcPasswordPresto) {
		this.jdbcPasswordPresto = jdbcPasswordPresto;
		return this;
	}


	public SupplyBlockchainConfig setJdbcUrlPresto(String jdbcUrlPresto) {
		this.jdbcUrlPresto = jdbcUrlPresto;
		return this;
	}


	public String getJdbcDriverClassNameHive() {
		return jdbcDriverClassNameHive;
	}


	public String getJdbcLoginHive() {
		return jdbcLoginHive;
	}


	public String getJdbcPasswordHive() {
		return jdbcPasswordHive;
	}


	public String getJdbcUrlHive() {
		return jdbcUrlHive;
	}


	public SupplyBlockchainConfig setJdbcDriverClassNameHive(String jdbcDriverClassNameHive) {
		this.jdbcDriverClassNameHive = jdbcDriverClassNameHive;
		return this;
	}


	public SupplyBlockchainConfig setJdbcLoginHive(String jdbcLoginHive) {
		this.jdbcLoginHive = jdbcLoginHive;
		return this;
	}


	public SupplyBlockchainConfig setJdbcPasswordHive(String jdbcPasswordHive) {
		this.jdbcPasswordHive = jdbcPasswordHive;
		return this;
	}


	public SupplyBlockchainConfig setJdbcUrlHive(String jdbcUrlHive) {
		this.jdbcUrlHive = jdbcUrlHive;
		return this;
	}


	public String getHiveMetastoreUri() {
		return hiveMetastoreUri;
	}


	public SupplyBlockchainConfig setHiveMetastoreUri(String hiveMetastoreUri) {
		this.hiveMetastoreUri = hiveMetastoreUri;
		return this;
	}


	public String getTargetPathBcscData() {
		return targetPathBcscData;
	}


	public SupplyBlockchainConfig setTargetPathBcscData(String targetPathBcscData) {
		this.targetPathBcscData = targetPathBcscData;
		return this;
	}
	

}
