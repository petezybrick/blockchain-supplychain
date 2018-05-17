package com.petezybrick.bcsc.sim.run;

import java.io.File;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.petezybrick.bcsc.common.config.CassandraBaseDao;
import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.common.utils.BlockchainUtils;
import com.petezybrick.bcsc.service.database.LotCanineDao;
import com.petezybrick.bcsc.service.database.LotCanineVo;
import com.petezybrick.bcsc.service.database.MapLotCanineSupplierBlockchainDao;
import com.petezybrick.bcsc.service.database.MapLotCanineSupplierBlockchainVo;
import com.petezybrick.bcsc.service.database.PooledDataSource;
import com.petezybrick.bcsc.service.database.SupplierBlockDao;
import com.petezybrick.bcsc.service.database.SupplierBlockTransactionDao;
import com.petezybrick.bcsc.service.database.SupplierBlockTransactionVo;
import com.petezybrick.bcsc.service.database.SupplierBlockVo;
import com.petezybrick.bcsc.service.database.SupplierBlockchainDao;
import com.petezybrick.bcsc.service.database.SupplierBlockchainVo;
import com.petezybrick.bcsc.service.database.SupplierDao;
import com.petezybrick.bcsc.service.database.SupplierTransactionDao;
import com.petezybrick.bcsc.service.database.SupplierTransactionVo;
import com.petezybrick.bcsc.service.database.SupplierVo;
import com.petezybrick.bcsc.service.orc.LotCanineOrcDao;
import com.petezybrick.bcsc.service.orc.LotCanineOrcVo;
import com.petezybrick.bcsc.service.orc.MapLotCanineSupplierBlockchainOrcDao;
import com.petezybrick.bcsc.service.orc.MapLotCanineSupplierBlockchainOrcVo;
import com.petezybrick.bcsc.service.orc.OrcCommon;
import com.petezybrick.bcsc.service.orc.SupplierBlockOrcDao;
import com.petezybrick.bcsc.service.orc.SupplierBlockOrcVo;
import com.petezybrick.bcsc.service.orc.SupplierBlockTransactionOrcDao;
import com.petezybrick.bcsc.service.orc.SupplierBlockTransactionOrcVo;
import com.petezybrick.bcsc.service.orc.SupplierBlockchainOrcDao;
import com.petezybrick.bcsc.service.orc.SupplierBlockchainOrcVo;
import com.petezybrick.bcsc.service.orc.SupplierOrcDao;
import com.petezybrick.bcsc.service.orc.SupplierOrcVo;
import com.petezybrick.bcsc.service.orc.SupplierTransactionOrcDao;
import com.petezybrick.bcsc.service.orc.SupplierTransactionOrcVo;
import com.petezybrick.bcsc.sim.run.SimBlockchainSequenceItem.DescCatSubcatItem;

public class GenSimSuppliers {
	private static final Logger logger = LogManager.getLogger(GenSimSuppliers.class);
	private static final Random random = new Random();
	public static final int NUM_SIM_EACH_SUPPLIER = 30;
	public static final String[] triples = { "Brewers Rice Farms, LLC", "farm", "rice", "Chicken Meal Farms, LLC", "farm", "chicken", "Wheat Farms, LLC",
			"farm", "wheat", "Corn Gluten Farms, LLC", "farm", "corn", "Oat Groats Farms, LLC", "farm", "oat", "Fish Oil Farms, LLC", "farm", "fish",
			"Beet Pulp Farms, LLC", "farm", "beet", "Fertilizer Suppliers, LLC", "supplier", "fertilizer", "Seed Suppliers, LLC", "supplier", "seed",
			"Fish Fry Suppliers, LLC", "supplier", "fish_fry", "Fish Feed Suppliers, LLC", "supplier", "fish_feed",
			"Chicken Feed Suppliers, LLC", "supplier", "chicken_feed", "Chick Suppliers, LLC",
			"supplier", "chicks", "Brewers Rice Cooperative, LLC", "distributor", "rice", "Chicken Meal Cooperative, LLC", "distributor", "chicken",
			"Wheat Cooperative, LLC", "distributor", "wheat", "Corn Gluten Cooperative, LLC", "distributor", "corn", "Oat Groats Cooperative, LLC",
			"distributor", "oat", "Fish Oil Cooperative, LLC", "distributor", "fish_oil", "Beet Pulp Cooperative, LLC", "distributor", "beet", };
	public static final int NUM_SIM_SUPPLIERS = triples.length / 3;
	public static final int NUM_SIM_CHAINS_PER_SOURCE = 10;
	public static final int NUM_SIM_LOTS_PER_CHAIN_PER_SOURCE = 5;
	public static final int NUM_SIM_LOTS = 10;
	public static final int NUM_SIM_PROD_WEEKS = 8;
	// Demo/learning purposes only - use Keystore
	public static final String encodedPrivateKey = 
		"307B020100301306072A8648CE3D020106082A8648CE3D0301010461305F020101041815D67A1C8826B62A54AD050B65A9812470C04B5E25FDAA1DA00A06082A8648CE3D030101A13403320004F127F659E0B608FC1145E152DC54F1EA152824D21343AC0869077EB70837D9C70EEB9174D87D0AA89BF4C8AD5668402E";
	public static final String encodedPublicKey = 
		"3049301306072A8648CE3D020106082A8648CE3D03010103320004F127F659E0B608FC1145E152DC54F1EA152824D21343AC0869077EB70837D9C70EEB9174D87D0AA89BF4C8AD5668402E";
	public static final String encryptionAlgorithm = "ECDSA";

	private KeyFactory keyFactory;
	private PrivateKey privateKeyFrom;
	private PublicKey publicKeyFrom;
	private ZonedDateTime simDate;
	private int simdDateMinutesOffset;
	private String pathSimBlockchainSequenceItemsJson;
	private SupplyBlockchainConfig config;

	public GenSimSuppliers(String pathSimBlockchainSequenceItemsJson) throws Exception {
		this.pathSimBlockchainSequenceItemsJson = pathSimBlockchainSequenceItemsJson;
		this.keyFactory = KeyFactory.getInstance(encryptionAlgorithm);
		privateKeyFrom = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(BlockchainUtils.toByteArray(encodedPrivateKey)));
		publicKeyFrom = keyFactory.generatePublic(new X509EncodedKeySpec(BlockchainUtils.toByteArray(encodedPublicKey)));
		simDate = ZonedDateTime.now().minusYears(1);
		simdDateMinutesOffset = 0;
	}

	public static void main(String[] args) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			GenSimSuppliers genSimSuppliers = new GenSimSuppliers(args[0]);
			genSimSuppliers.process();
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			CassandraBaseDao.disconnect();
		}
	}

	public void process() throws Exception {
		try {
			config = SupplyBlockchainConfig.getInstance( System.getenv("ENV"), System.getenv("CONTACT_POINT"),
					System.getenv("KEYSPACE_NAME") );
			PooledDataSource.getInstance( config ); 
			// Clear out old simulated data
			deleteAllTables();

			List<SupplierVo> supplierVos = GenSimSuppliers.createSupplierVos();
			// farm,rice supplier,seed supplier,fert, coop,rice, ABCPetFood
			Map<String, List<SupplierVo>> mapSupplierVos = new HashMap<String, List<SupplierVo>>();
			for (SupplierVo supplierVo : supplierVos) {
				String key = supplierVo.getSupplierCategory() + "|" + supplierVo.getSupplierSubCategory();
				List<SupplierVo> supplierVosByKey = mapSupplierVos.get(key);
				if (supplierVosByKey == null) {
					supplierVosByKey = new ArrayList<SupplierVo>();
					mapSupplierVos.put(key, supplierVosByKey);
				}
				supplierVosByKey.add(supplierVo);
			}
			// Create SupplyChainTransactions, where each block in the chain is
			// one of the random suppliers between 1 and NUM_SIM_EACH_SUPPLIER
			// and add each SupplyChainTransaction to a SupplierBlock to create
			// a block chain

			Map<String, List<SupplierBlockchainVo>> mapSimBlockchainsBySourceKey = new HashMap<String, List<SupplierBlockchainVo>>();
			List<SimBlockchainSequenceItem> simBlockchainSequenceItems = 
					BlockchainUtils.objectMapper.readValue(new File(pathSimBlockchainSequenceItemsJson),
					new TypeReference<List<SimBlockchainSequenceItem>>() {
					});

			List<SupplierBlockchainVo> allSupplierBlockchainVos = new ArrayList<SupplierBlockchainVo>();
			for (SimBlockchainSequenceItem simBlockchainSequenceItem : simBlockchainSequenceItems) {
				List<SupplierBlockchainVo> supplierBlockchainVos = genSimBlockchains(mapSupplierVos, simBlockchainSequenceItem);
				mapSimBlockchainsBySourceKey.put(simBlockchainSequenceItem.getSupplierType(), supplierBlockchainVos );
				allSupplierBlockchainVos.addAll(supplierBlockchainVos);
			}
			
			//createSupplierBlockchainsTables(allSupplierBlockchainVos);
			
			// a Lot of Canine Nutrition consists of Ingredients, each Ingredient comes from a chain of suppliers
			// 		For each ingredient type, i.e. Brewers Rice, there are multiple suppliers, aka multiple blockchains
			//		Pick a random supplier (blockchain) for each ingredient and add to the log
			List<String> supplierTypes = Arrays.asList("brewers_rice","chicken_meal","wheat","corn_gluten","oat_groats","beet_pulp","fish_oil");
			List<LotCanineVo> lotCanineVos = new ArrayList<LotCanineVo>();
			ZonedDateTime simProdWeek;
			simProdWeek = ZonedDateTime.of(2018, 1, 8, 0, 0, 0, 0, ZoneId.of("UTC"));
			final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
			for( int i=0 ; i<NUM_SIM_PROD_WEEKS ; i++) {
				String lotNumberRoot = simProdWeek.format(fmt) + "-";
				int lotNumberSeq = 1;
				for( int j=0 ; j<NUM_SIM_LOTS ; j++ ) {
					List<MapLotCanineSupplierBlockchainVo> mapLotCanineSupplierBlockchainVos = new ArrayList<MapLotCanineSupplierBlockchainVo>();
					String lotCanineUuid = BlockchainUtils.generateSortableUuid();
					int ingredientSequence = 1;
					for( String supplierType : supplierTypes ) {
						List<SupplierBlockchainVo> list = mapSimBlockchainsBySourceKey.get(supplierType);
						int offset = random.nextInt(list.size());
						//System.out.println(offset + " " + list.size());
						SupplierBlockchainVo supplierBlockchainVo = list.get(offset);
						String mapLotCanineSupplierBlockchainUuid = BlockchainUtils.generateSortableUuid();
						MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo = new MapLotCanineSupplierBlockchainVo()
								.setMapLotCanineSupplierBlockchainUuid(mapLotCanineSupplierBlockchainUuid)
								.setIngredientName(supplierType)
								.setIngredientSequence(ingredientSequence++)
								.setLotCanineUuid(lotCanineUuid)
								.setSupplierBlockchainUuid(supplierBlockchainVo.getSupplierBlockchainUuid())
								;
						mapLotCanineSupplierBlockchainVos.add(mapLotCanineSupplierBlockchainVo);
					}
					LotCanineVo lotCanineVo = new LotCanineVo()
							.setLotCanineUuid(lotCanineUuid)
							.setManufacturerLotNumber(lotNumberRoot +  lotNumberSeq)
							.setLotFilledDate( Timestamp.from( simProdWeek.toInstant()))
							.setMapLotCanineSupplierBlockchainVos(mapLotCanineSupplierBlockchainVos)
							;
					//System.out.println("=========================");
					System.out.println(lotCanineVo);
					lotCanineVos.add(lotCanineVo);
					lotNumberSeq++;
				}
				simProdWeek = simProdWeek.plusWeeks(1);
			}
			
			// ORC files
			// TODO: delete the target folder
			String targetPath = System.getProperty("java.io.tmpdir");
			targetPath = "hdfs://user/bcsc/bcsc_data/";
			OrcCommon.deleteTargetFolder( targetPath );
			if( !targetPath.endsWith("/")) targetPath = targetPath + "/";
			String targetNameExt = null;
			List<List<Object>> rowsColsSupplier = new ArrayList<List<Object>>();
			for( SupplierVo supplierVo : supplierVos ) rowsColsSupplier.add( new SupplierOrcVo(supplierVo).toObjectList());
			targetNameExt = "supplier/" + BlockchainUtils.generateSortableUuid();
			SupplierOrcDao.writeOrc(targetPath, targetNameExt, rowsColsSupplier);

			List<List<Object>> rowsColsSupplierBlockchain = new ArrayList<List<Object>>();
			List<List<Object>> rowsColsSupplierBlock = new ArrayList<List<Object>>();
			List<List<Object>> rowsColsSupplierBlockTransaction = new ArrayList<List<Object>>();
			List<List<Object>> rowsColsSupplierTransaction = new ArrayList<List<Object>>();
			for( SupplierBlockchainVo supplierBlockchainVo : allSupplierBlockchainVos ) { 
				rowsColsSupplierBlockchain.add( new SupplierBlockchainOrcVo(supplierBlockchainVo).toObjectList());
				if( supplierBlockchainVo.getSupplierBlockVos() != null ) {
					for( SupplierBlockVo supplierBlockVo : supplierBlockchainVo.getSupplierBlockVos() ) {
						rowsColsSupplierBlock.add( new SupplierBlockOrcVo(supplierBlockVo).toObjectList());
						if( supplierBlockVo.getSupplierBlockTransactionVo() != null ) {
							rowsColsSupplierBlockTransaction.add( 
								new SupplierBlockTransactionOrcVo(supplierBlockVo.getSupplierBlockTransactionVo()).toObjectList());
							if( supplierBlockVo.getSupplierBlockTransactionVo().getSupplierTransactionVo() != null ) { 
								rowsColsSupplierTransaction.add( 
										new SupplierTransactionOrcVo(supplierBlockVo.getSupplierBlockTransactionVo().getSupplierTransactionVo()).toObjectList());
							}
						}
					}
				}
			}
			targetNameExt = "supplier_blockchain/" + BlockchainUtils.generateSortableUuid();
			SupplierBlockchainOrcDao.writeOrc(targetPath, targetNameExt, rowsColsSupplierBlockchain);
			targetNameExt = "supplier_block/" + BlockchainUtils.generateSortableUuid();
			SupplierBlockOrcDao.writeOrc(targetPath, targetNameExt, rowsColsSupplierBlock);
			targetNameExt = "supplier_block_transaction/" + BlockchainUtils.generateSortableUuid();
			SupplierBlockTransactionOrcDao.writeOrc(targetPath, targetNameExt, rowsColsSupplierBlockTransaction);
			targetNameExt = "supplier_transaction/" + BlockchainUtils.generateSortableUuid();
			SupplierTransactionOrcDao.writeOrc(targetPath, targetNameExt, rowsColsSupplierTransaction);
			
			List<List<Object>> rowsColsLotCanine = new ArrayList<List<Object>>();
			List<List<Object>> rowsColsMapLotCanineSupplierBlockchainVo = new ArrayList<List<Object>>();
			for( LotCanineVo lotCanineVo : lotCanineVos ) {
				rowsColsLotCanine.add( new LotCanineOrcVo(lotCanineVo).toObjectList());
				if( lotCanineVo.getMapLotCanineSupplierBlockchainVos() != null ) {
					for( MapLotCanineSupplierBlockchainVo mapLotCanineSupplierBlockchainVo : lotCanineVo.getMapLotCanineSupplierBlockchainVos() ) {
						rowsColsMapLotCanineSupplierBlockchainVo.add( 
								new MapLotCanineSupplierBlockchainOrcVo(mapLotCanineSupplierBlockchainVo).toObjectList() );
					}
				}
			}
			targetNameExt = "lot_canine/" + BlockchainUtils.generateSortableUuid();
			LotCanineOrcDao.writeOrc(targetPath, targetNameExt, rowsColsLotCanine);
			targetNameExt = "map_lot_canine_supplier_blockchain/" + BlockchainUtils.generateSortableUuid();
			MapLotCanineSupplierBlockchainOrcDao.writeOrc(targetPath, targetNameExt, rowsColsMapLotCanineSupplierBlockchainVo);
			
			try (Connection con = PooledDataSource.getInstance().getConnection();){
				con.setAutoCommit(false);
				SupplierDao.insertBatchList( supplierVos );
				SupplierBlockchainDao.insertBatchList( con, allSupplierBlockchainVos );
				LotCanineDao.insertBatchList(con, lotCanineVos);
				con.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("+++++ Done +++++");
	}

	
	private List<SupplierBlockchainVo> genSimBlockchains(Map<String, List<SupplierVo>> mapSupplierVos, 
			SimBlockchainSequenceItem simBlockchainSequenceItem)
			throws Exception {
		List<SupplierBlockchainVo> supplierBlockChains = new ArrayList<SupplierBlockchainVo>();
		int lotSuffix = 1;
		for( int j=0 ; j<NUM_SIM_LOTS_PER_CHAIN_PER_SOURCE ; j++ ) {
			for (int i=0; i < NUM_SIM_CHAINS_PER_SOURCE; i++) {
				String supplierBlockChainUuid = BlockchainUtils.generateSortableUuid();
				int blockSequence = 0;
				String previousHash = "0";
				List<SupplierBlockVo> supplierBlocks = new ArrayList<SupplierBlockVo>();
				for (DescCatSubcatItem descCatSubcatItem : simBlockchainSequenceItem.getDescCatSubcatItems()) {
					String key = descCatSubcatItem.getCategory() + "|" + descCatSubcatItem.getSubCategory();
					List<SupplierVo> supplierVosByKey = mapSupplierVos.get(key);
					SupplierVo supplierVoRnd = supplierVosByKey.get(random.nextInt(NUM_SIM_EACH_SUPPLIER));
					String supplierTransactionUuid = BlockchainUtils.generateSortableUuid();
					String supplierBlockTransactionUuid = BlockchainUtils.generateSortableUuid();
					String supplierLot = "lot-" + descCatSubcatItem.getCategory() + "-" + descCatSubcatItem.getSubCategory() 
						+ "-" + lotSuffix;
					String itemNumber = String.format("%08d", lotSuffix + 100 );
					lotSuffix++;
					SupplierTransactionVo supplierTransactionVo = new SupplierTransactionVo()
							.setSupplierTransactionUuid(supplierTransactionUuid)
							.setSupplierBlockTransactionUuid(supplierBlockTransactionUuid)
							.setSupplierUuid(supplierVoRnd.getSupplierUuid())
							.setSupplierLotNumber(supplierLot).setItemNumber(itemNumber)
							.setDescription(descCatSubcatItem.getDesc()).setQty(100).setUnits("kg")
							.setShippedDateIso8601( Timestamp.from( simDate.plusMinutes(simdDateMinutesOffset++).toInstant()) )
							.setRcvdDateIso8601( Timestamp.from( simDate.plusMinutes(simdDateMinutesOffset++).toInstant()) );
					PublicKey publicKeyTo = keyFactory.generatePublic(new X509EncodedKeySpec(BlockchainUtils.toByteArray(supplierVoRnd.getEncodedPublicKey())));
					String supplierBlockUuid = BlockchainUtils.generateSortableUuid();
					SupplierBlockTransactionVo supplierBlockTransactionVo = new SupplierBlockTransactionVo()
							.setSupplierBlockTransactionUuid(supplierBlockTransactionUuid)
							.setSupplierBlockUuid(supplierBlockUuid)
							.setEncodedPublicKeyFrom( BlockchainUtils.getEncodedStringFromKey( publicKeyFrom) )
							.setEncodedPublicKeyTo( BlockchainUtils.getEncodedStringFromKey( publicKeyTo) )
							.setSupplierTransactionVo(supplierTransactionVo).setTransactionSequence(blockSequence).updateTransactionId().generateSignature(privateKeyFrom);
					SupplierBlockVo supplierBlockVo = new SupplierBlockVo()
							.setPreviousHash(previousHash).setSupplierBlockTransactionVo(supplierBlockTransactionVo)
							.setBlockSequence(blockSequence).setSupplierBlockUuid(supplierBlockUuid)
							.setSupplierBlockchainUuid(supplierBlockChainUuid)
							.setBlockTimestamp(Timestamp.from( simDate.plusMinutes(simdDateMinutesOffset++).toInstant()) )
							.updateHash();
					supplierBlocks.add(supplierBlockVo);
					blockSequence++;
					previousHash = supplierBlockVo.getHash();
				}
				System.out.println("+++++++++++++++++++++++++++++++++++");
				for (SupplierBlockVo supplierBlock : supplierBlocks) {
					System.out.println(supplierBlock);
				}
				supplierBlockChains.add(new SupplierBlockchainVo().setSupplierBlockchainUuid(supplierBlockChainUuid)
						.setSupplierType(simBlockchainSequenceItem.getSupplierType()).setSupplierBlockVos(supplierBlocks));
			}	
		}
		return supplierBlockChains;
	}

	
	public static List<SupplierVo> createSupplierVos() throws Exception {
		char prefChar = 'A';
		int dunsNumber = 1;
		List<SupplierVo> supplierVos = new ArrayList<SupplierVo>();
		for (int i = 0; i < NUM_SIM_EACH_SUPPLIER; i++) {
			for (int j = 0; j < triples.length; j += 3) {
				KeyPair keyPair = BlockchainUtils.generateKeyPair();
				String encodedPublicKey = BlockchainUtils.toHexString(keyPair.getPublic().getEncoded());
				String supplierName = prefChar + String.valueOf(i + 1) + " " + triples[j];
				SupplierVo supplierVo = new SupplierVo().setSupplierUuid(BlockchainUtils.generateSortableUuid()).setDunsNumber(String.format("%09d", dunsNumber++))
						.setSupplierName(supplierName).setSupplierCategory(triples[j + 1]).setSupplierSubCategory(triples[j + 2])
						.setStateProvince(randomStateProvince()).setCountry("US").setEncodedPublicKey(encodedPublicKey);
				System.out.println(supplierVo);
				supplierVos.add(supplierVo);
			}
			if (prefChar != 'Z')
				prefChar++;
			else
				prefChar = 'A';
		}

		return supplierVos;
	}

	public static String randomStateProvince() {
		final String[] abbrevs = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA",
				"MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
				"VA", "WA", "WV", "WI", "WY" };
		return abbrevs[random.nextInt(50)];
	}
	
	public static void deleteAllTables() throws Exception {
		MapLotCanineSupplierBlockchainDao.deleteAll();
		LotCanineDao.deleteAll();
		SupplierTransactionDao.deleteAll();
		SupplierBlockTransactionDao.deleteAll();
		SupplierBlockDao.deleteAll();
		SupplierBlockchainDao.deleteAll();
		SupplierDao.deleteAll();
	}
	
	
	private void createSupplierBlockchainsTables( List<SupplierBlockchainVo> supplierBlockchainVos) throws Exception {
		try (Connection con = PooledDataSource.getInstance().getConnection();){
			con.setAutoCommit(false);
			SupplierBlockchainDao.insertBatchList( con, supplierBlockchainVos );
			con.commit();
		}
	}

}
