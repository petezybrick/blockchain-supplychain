package com.petezybrick.bcsc.sim.run;

import java.io.File;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.CassandraBaseDao;
import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.database.PooledDataSource;

public class GenSimComplaints {
	private static final Logger logger = LogManager.getLogger(GenSimComplaints.class);
	private static final Random random = new Random();
	private static final int NUM_CUSTOMER_COMPLAINTS = 100;
	private static final List<String> MANU_LOT_NUMBERS = Arrays.asList("aa","bb","cc");
	private static final int NUM_MANU_LOT_NUMBERS = MANU_LOT_NUMBERS.size();
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
		SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),
				System.getenv("KEYSPACE_NAME") );
		PooledDataSource.getInstance( SupplyBlockchainConfig.getInstance() ); 
		List<CustomerComplaintVo> customerComplaintVos = new ArrayList<CustomerComplaintVo>();
		List<String> customerLogins = readCustomerLogins( pathNameExtUsers );
		for( int i=0 ; i<NUM_CUSTOMER_COMPLAINTS ; i++ ) {
			String customerLogin = customerLogins.get(i);
			String manufacturerLotNumber = MANU_LOT_NUMBERS.get( random.nextInt(NUM_MANU_LOT_NUMBERS));
			String complaintText = COMPLAINT_TEXTS.get(random.nextInt(NUM_COMPLAINT_TEXTS));
			customerComplaintVos.add( new CustomerComplaintVo().setCustomerLogin(customerLogin)
					.setManufacturerLotNumber(manufacturerLotNumber)
					.setComplaintText(complaintText) );
		}
		customerComplaintVos.forEach(ccv -> System.out.println(ccv.toString()));
	}

	
	private List<String> readCustomerLogins( String pathNameExtUsers ) throws Exception {
		List<String> users = new ArrayList<String>();
		List<String> rows = FileUtils.readLines(new File(pathNameExtUsers));
		for( String row : rows ) {
			String[] tokens = row.split("[\t]");
			users.add(tokens[2]);
		}
		return users;
	}
	
	private static class CustomerComplaintVo {
		private String customerLogin;
		private String manufacturerLotNumber;
		private String complaintText;
		
		public String getCustomerLogin() {
			return customerLogin;
		}
		public String getManufacturerLotNumber() {
			return manufacturerLotNumber;
		}
		public String getComplaintText() {
			return complaintText;
		}
		public CustomerComplaintVo setCustomerLogin(String customerLogin) {
			this.customerLogin = customerLogin;
			return this;
		}
		public CustomerComplaintVo setManufacturerLotNumber(String manufacturerLotNumber) {
			this.manufacturerLotNumber = manufacturerLotNumber;
			return this;
		}
		public CustomerComplaintVo setComplaintText(String complaintText) {
			this.complaintText = complaintText;
			return this;
		}
		@Override
		public String toString() {
			return "CustomerComplaintVo [customerLogin=" + customerLogin + ", manufacturerLotNumber=" + manufacturerLotNumber + ", complaintText="
					+ complaintText + "]";
		}
	}
}
