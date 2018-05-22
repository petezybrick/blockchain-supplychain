package com.petezybrick.bcsc.sim.run;

import java.io.File;
import java.security.Security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.CassandraBaseDao;
import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.common.utils.BlockchainUtils;
import com.petezybrick.bcsc.service.database.CustomerDao;
import com.petezybrick.bcsc.service.database.CustomerLoyaltyDao;
import com.petezybrick.bcsc.service.database.CustomerLoyaltyVo;
import com.petezybrick.bcsc.service.database.CustomerVo;
import com.petezybrick.bcsc.service.database.LotCanineDao;
import com.petezybrick.bcsc.service.database.PooledDataSource;

public class GenSimComplaints {
	private static final Logger logger = LogManager.getLogger(GenSimComplaints.class);
	private static final Random random = new Random();
	private static final int NUM_CUSTOMER_COMPLAINTS = 100;
	private List<String> manuLotNumbers;
	private int numManuLotNumbers;
	private static final List<String> COMPLAINT_TEXTS = Arrays.asList("indigestion","refuses to eat","constipation");
	private static final int NUM_COMPLAINT_TEXTS = COMPLAINT_TEXTS.size();
			

	public static void main(String[] args) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			GenSimComplaints genSimComplaints = new GenSimComplaints();
			genSimComplaints.process( args[0] );
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		} finally {
			CassandraBaseDao.disconnect();
		}
	}
	
	
	public void process( String pathNameExtUsers ) throws Exception {
		long before = System.currentTimeMillis();
		logger.info("Start");
		final String LOYALTY_TYPE_COMPLAINT = "C";
		SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),
				System.getenv("KEYSPACE_NAME") );
		manuLotNumbers = LotCanineDao.findAllLotNumbers();
		numManuLotNumbers = manuLotNumbers.size();
		List<CustomerLoyaltyVo> customerLoyaltyVos = new ArrayList<CustomerLoyaltyVo>();
		List<CustomerVo> customerVos = createCustomers( pathNameExtUsers );
		for( int i=0 ; i<NUM_CUSTOMER_COMPLAINTS ; i++ ) {
			CustomerVo customerVo = customerVos.get(i);
			String manufacturerLotNumber = manuLotNumbers.get( random.nextInt(numManuLotNumbers));
			String complaintText = COMPLAINT_TEXTS.get(random.nextInt(NUM_COMPLAINT_TEXTS));
			customerLoyaltyVos.add( new CustomerLoyaltyVo()
					.setCustomerLoyaltyUuid(BlockchainUtils.generateSortableUuid())
					.setCustomerUuid(customerVo.getCustomerUuid())
					.setDescType(LOYALTY_TYPE_COMPLAINT)
					.setDescText(complaintText)
					.setManufacturerLotNumber(manufacturerLotNumber)
					);
		}
		try (Connection con = PooledDataSource.getInstance().getConnection();){
			logger.info("Deletes");
			CustomerLoyaltyDao.deleteAll( con );
			CustomerDao.deleteAll( con );
			logger.info("Insert Customers");
			CustomerDao.insertBatchList(con, customerVos);
			logger.info("Insert CustomerLoyaltys");
			CustomerLoyaltyDao.insertBatchList(con, customerLoyaltyVos);
			logger.info("Complete, elapsedMs {}", System.currentTimeMillis() - before);
		}
	}

	
	private List<CustomerVo> createCustomers( String pathNameExtUsers ) throws Exception {
		List<CustomerVo> customerVos = new ArrayList<CustomerVo>();
		List<String> rows = FileUtils.readLines(new File(pathNameExtUsers));
		int numCustomers = 0;
		for( String row : rows ) {
			String[] tokens = row.split("[\t]");
			customerVos.add( new CustomerVo()
					.setCustomerUuid(BlockchainUtils.generateSortableUuid())
					.setFirstName(tokens[0])
					.setLastName(tokens[1])
					.setEmailAddress(tokens[2])
					);
			if( ++numCustomers == NUM_CUSTOMER_COMPLAINTS) break;
		}
		return customerVos;
	}
	
}
