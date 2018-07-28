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


// TODO: Auto-generated Javadoc
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
	
	/** The hdfs uri. */
	private String hdfsUri;
	
	/** The hadoop user name. */
	private String hadoopUserName;
	
	/** The hive metastore uri. */
	private String hiveMetastoreUri;
	
	/** The target path bcsc data. */
	private String targetPathBcscData;
	
	/** The schema bcsc data. */
	private String schemaBcscData;


	/**
	 * Instantiates a new master config.
	 */
	public SupplyBlockchainConfig() {
		
	}


	/**
	 * Instantiates a new master config.
	 *
	 * @return single instance of SupplyBlockchainConfig
	 * @throws Exception the exception
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


	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * Gets the logger.
	 *
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}


	/**
	 * Gets the supply blockchain config json key.
	 *
	 * @return the supply blockchain config json key
	 */
	public String getSupplyBlockchainConfigJsonKey() {
		return supplyBlockchainConfigJsonKey;
	}


	/**
	 * Gets the contact point.
	 *
	 * @return the contact point
	 */
	public String getContactPoint() {
		return contactPoint;
	}


	/**
	 * Gets the keyspace name.
	 *
	 * @return the keyspace name
	 */
	public String getKeyspaceName() {
		return keyspaceName;
	}

	
	/**
	 * Gets the jdbc driver class name supplier.
	 *
	 * @return the jdbc driver class name supplier
	 */
	public String getJdbcDriverClassNameSupplier() {
		return jdbcDriverClassNameSupplier;
	}


	/**
	 * Gets the jdbc insert block size supplier.
	 *
	 * @return the jdbc insert block size supplier
	 */
	public Integer getJdbcInsertBlockSizeSupplier() {
		return jdbcInsertBlockSizeSupplier;
	}


	/**
	 * Gets the jdbc login supplier.
	 *
	 * @return the jdbc login supplier
	 */
	public String getJdbcLoginSupplier() {
		return jdbcLoginSupplier;
	}


	/**
	 * Gets the jdbc password supplier.
	 *
	 * @return the jdbc password supplier
	 */
	public String getJdbcPasswordSupplier() {
		return jdbcPasswordSupplier;
	}


	/**
	 * Gets the jdbc url supplier.
	 *
	 * @return the jdbc url supplier
	 */
	public String getJdbcUrlSupplier() {
		return jdbcUrlSupplier;
	}


	/**
	 * Sets the supply blockchain config json key.
	 *
	 * @param supplyBlockchainConfigJsonKey the supply blockchain config json key
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setSupplyBlockchainConfigJsonKey(String supplyBlockchainConfigJsonKey) {
		this.supplyBlockchainConfigJsonKey = supplyBlockchainConfigJsonKey;
		return this;
	}


	/**
	 * Sets the contact point.
	 *
	 * @param contactPoint the contact point
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setContactPoint(String contactPoint) {
		this.contactPoint = contactPoint;
		return this;
	}


	/**
	 * Sets the keyspace name.
	 *
	 * @param keyspaceName the keyspace name
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setKeyspaceName(String keyspaceName) {
		this.keyspaceName = keyspaceName;
		return this;
	}


	/**
	 * Sets the jdbc driver class name supplier.
	 *
	 * @param jdbcDriverClassNameSupplier the jdbc driver class name supplier
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcDriverClassNameSupplier(String jdbcDriverClassNameSupplier) {
		this.jdbcDriverClassNameSupplier = jdbcDriverClassNameSupplier;
		return this;
	}


	/**
	 * Sets the jdbc insert block size supplier.
	 *
	 * @param jdbcInsertBlockSizeSupplier the jdbc insert block size supplier
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcInsertBlockSizeSupplier(Integer jdbcInsertBlockSizeSupplier) {
		this.jdbcInsertBlockSizeSupplier = jdbcInsertBlockSizeSupplier;
		return this;
	}


	/**
	 * Sets the jdbc login supplier.
	 *
	 * @param jdbcLoginSupplier the jdbc login supplier
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcLoginSupplier(String jdbcLoginSupplier) {
		this.jdbcLoginSupplier = jdbcLoginSupplier;
		return this;
	}


	/**
	 * Sets the jdbc password supplier.
	 *
	 * @param jdbcPasswordSupplier the jdbc password supplier
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcPasswordSupplier(String jdbcPasswordSupplier) {
		this.jdbcPasswordSupplier = jdbcPasswordSupplier;
		return this;
	}


	/**
	 * Sets the jdbc url supplier.
	 *
	 * @param jdbcUrlSupplier the jdbc url supplier
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcUrlSupplier(String jdbcUrlSupplier) {
		this.jdbcUrlSupplier = jdbcUrlSupplier;
		return this;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SupplyBlockchainConfig [supplyBlockchainConfigJsonKey=" + supplyBlockchainConfigJsonKey + ", contactPoint=" + contactPoint + ", keyspaceName="
				+ keyspaceName + ", jdbcDriverClassNameSupplier=" + jdbcDriverClassNameSupplier + ", jdbcInsertBlockSizeSupplier=" + jdbcInsertBlockSizeSupplier
				+ ", jdbcLoginSupplier=" + jdbcLoginSupplier + ", jdbcPasswordSupplier=" + jdbcPasswordSupplier + ", jdbcUrlSupplier=" + jdbcUrlSupplier
				+ ", jdbcDriverClassNamePresto=" + jdbcDriverClassNamePresto + ", jdbcLoginPresto=" + jdbcLoginPresto + ", jdbcPasswordPresto="
				+ jdbcPasswordPresto + ", jdbcUrlPresto=" + jdbcUrlPresto + ", jdbcDriverClassNameHive=" + jdbcDriverClassNameHive + ", jdbcLoginHive="
				+ jdbcLoginHive + ", jdbcPasswordHive=" + jdbcPasswordHive + ", jdbcUrlHive=" + jdbcUrlHive + ", hdfsUri=" + hdfsUri + ", hadoopUserName="
				+ hadoopUserName + ", hiveMetastoreUri=" + hiveMetastoreUri + ", targetPathBcscData=" + targetPathBcscData + ", schemaBcscData="
				+ schemaBcscData + "]";
	}


	/**
	 * Gets the hdfs uri.
	 *
	 * @return the hdfs uri
	 */
	public String getHdfsUri() {
		return hdfsUri;
	}


	/**
	 * Sets the hdfs uri.
	 *
	 * @param hdfsUri the hdfs uri
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setHdfsUri(String hdfsUri) {
		this.hdfsUri = hdfsUri;
		return this;
	}


	/**
	 * Gets the hadoop user name.
	 *
	 * @return the hadoop user name
	 */
	public String getHadoopUserName() {
		return hadoopUserName;
	}


	/**
	 * Sets the hadoop user name.
	 *
	 * @param hadoopUserName the hadoop user name
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setHadoopUserName(String hadoopUserName) {
		this.hadoopUserName = hadoopUserName;
		return this;
	}


	/**
	 * Gets the jdbc driver class name presto.
	 *
	 * @return the jdbc driver class name presto
	 */
	public String getJdbcDriverClassNamePresto() {
		return jdbcDriverClassNamePresto;
	}


	/**
	 * Gets the jdbc login presto.
	 *
	 * @return the jdbc login presto
	 */
	public String getJdbcLoginPresto() {
		return jdbcLoginPresto;
	}


	/**
	 * Gets the jdbc password presto.
	 *
	 * @return the jdbc password presto
	 */
	public String getJdbcPasswordPresto() {
		return jdbcPasswordPresto;
	}


	/**
	 * Gets the jdbc url presto.
	 *
	 * @return the jdbc url presto
	 */
	public String getJdbcUrlPresto() {
		return jdbcUrlPresto;
	}


	/**
	 * Sets the jdbc driver class name presto.
	 *
	 * @param jdbcDriverClassNamePresto the jdbc driver class name presto
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcDriverClassNamePresto(String jdbcDriverClassNamePresto) {
		this.jdbcDriverClassNamePresto = jdbcDriverClassNamePresto;
		return this;
	}


	/**
	 * Sets the jdbc login presto.
	 *
	 * @param jdbcLoginPresto the jdbc login presto
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcLoginPresto(String jdbcLoginPresto) {
		this.jdbcLoginPresto = jdbcLoginPresto;
		return this;
	}


	/**
	 * Sets the jdbc password presto.
	 *
	 * @param jdbcPasswordPresto the jdbc password presto
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcPasswordPresto(String jdbcPasswordPresto) {
		this.jdbcPasswordPresto = jdbcPasswordPresto;
		return this;
	}


	/**
	 * Sets the jdbc url presto.
	 *
	 * @param jdbcUrlPresto the jdbc url presto
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcUrlPresto(String jdbcUrlPresto) {
		this.jdbcUrlPresto = jdbcUrlPresto;
		return this;
	}


	/**
	 * Gets the jdbc driver class name hive.
	 *
	 * @return the jdbc driver class name hive
	 */
	public String getJdbcDriverClassNameHive() {
		return jdbcDriverClassNameHive;
	}


	/**
	 * Gets the jdbc login hive.
	 *
	 * @return the jdbc login hive
	 */
	public String getJdbcLoginHive() {
		return jdbcLoginHive;
	}


	/**
	 * Gets the jdbc password hive.
	 *
	 * @return the jdbc password hive
	 */
	public String getJdbcPasswordHive() {
		return jdbcPasswordHive;
	}


	/**
	 * Gets the jdbc url hive.
	 *
	 * @return the jdbc url hive
	 */
	public String getJdbcUrlHive() {
		return jdbcUrlHive;
	}


	/**
	 * Sets the jdbc driver class name hive.
	 *
	 * @param jdbcDriverClassNameHive the jdbc driver class name hive
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcDriverClassNameHive(String jdbcDriverClassNameHive) {
		this.jdbcDriverClassNameHive = jdbcDriverClassNameHive;
		return this;
	}


	/**
	 * Sets the jdbc login hive.
	 *
	 * @param jdbcLoginHive the jdbc login hive
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcLoginHive(String jdbcLoginHive) {
		this.jdbcLoginHive = jdbcLoginHive;
		return this;
	}


	/**
	 * Sets the jdbc password hive.
	 *
	 * @param jdbcPasswordHive the jdbc password hive
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcPasswordHive(String jdbcPasswordHive) {
		this.jdbcPasswordHive = jdbcPasswordHive;
		return this;
	}


	/**
	 * Sets the jdbc url hive.
	 *
	 * @param jdbcUrlHive the jdbc url hive
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setJdbcUrlHive(String jdbcUrlHive) {
		this.jdbcUrlHive = jdbcUrlHive;
		return this;
	}


	/**
	 * Gets the hive metastore uri.
	 *
	 * @return the hive metastore uri
	 */
	public String getHiveMetastoreUri() {
		return hiveMetastoreUri;
	}


	/**
	 * Sets the hive metastore uri.
	 *
	 * @param hiveMetastoreUri the hive metastore uri
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setHiveMetastoreUri(String hiveMetastoreUri) {
		this.hiveMetastoreUri = hiveMetastoreUri;
		return this;
	}


	/**
	 * Gets the target path bcsc data.
	 *
	 * @return the target path bcsc data
	 */
	public String getTargetPathBcscData() {
		return targetPathBcscData;
	}


	/**
	 * Sets the target path bcsc data.
	 *
	 * @param targetPathBcscData the target path bcsc data
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setTargetPathBcscData(String targetPathBcscData) {
		this.targetPathBcscData = targetPathBcscData;
		return this;
	}


	/**
	 * Gets the schema bcsc data.
	 *
	 * @return the schema bcsc data
	 */
	public String getSchemaBcscData() {
		return schemaBcscData;
	}


	/**
	 * Sets the schema bcsc data.
	 *
	 * @param schemaBcscData the schema bcsc data
	 * @return the supply blockchain config
	 */
	public SupplyBlockchainConfig setSchemaBcscData(String schemaBcscData) {
		this.schemaBcscData = schemaBcscData;
		return this;
	}
	

}
