package com.petezybrick.bcsc.service.utils;

import java.io.File;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.petezybrick.bcsc.common.config.SupplyBlockchainConfig;
import com.petezybrick.bcsc.service.database.LotIngredientVo;
import com.petezybrick.bcsc.service.database.LotSupplierBlockVo;
import com.petezybrick.bcsc.service.database.LotTreeVo;
import com.petezybrick.bcsc.service.orc.OrcCommon;

public class BcscServiceUtils {
	private static final Logger logger = LogManager.getLogger(BcscServiceUtils.class);
	
	public static Path initHdfs( Configuration conf, String targetPath, String targetNameExt ) throws Exception {
		String hdfsUri = SupplyBlockchainConfig.getInstance().getHdfsUri();
		conf.set("fs.defaultFS", hdfsUri);
		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
		System.setProperty("HADOOP_USER_NAME", SupplyBlockchainConfig.getInstance().getHdfsUri() );
		System.setProperty("hadoop.home.dir", "/");
		String hdfsPath = targetPath.substring(6);
		Path newFolderPath = new Path(hdfsUri + hdfsPath);
		return new Path(newFolderPath + "/" + targetNameExt);
	}
	
	
	public static String deleteTargetFolder( String targetPath ) throws Exception {
		logger.debug("targetPath {}", targetPath );
		Configuration conf = new Configuration();
		
		if( targetPath.startsWith("hdfs")) {
			initHdfs( conf, targetPath, "" );
			String hdfsUri = SupplyBlockchainConfig.getInstance().getHdfsUri() ;
			FileSystem fs = FileSystem.get(URI.create(hdfsUri), conf);
			String hdfsPath = targetPath.substring(6);
			Path newFolderPath = new Path(hdfsUri + hdfsPath);
			if (fs.exists(newFolderPath)) fs.delete(newFolderPath, true);
			return newFolderPath.toString();
		} else if( targetPath.startsWith("s3")) {
			return targetPath;
		} else {
			FileUtils.deleteDirectory( new File(targetPath));
			return targetPath;
		}
	}

	public static void dumpLotTreeItemToConsole( LotTreeVo lotTreeItem ) throws Exception {
		System.out.println( "Manufacturer Lot Number: " + lotTreeItem.getManufacturerLotNumber() + 
				", Manufacturer Filled Date: " + lotTreeItem.getManufacturerLotFilledDate());
		for( LotIngredientVo lotIngredientItem :  lotTreeItem.getLotIngredientItems()) {
			System.out.println("\t" + lotIngredientItem.getIngredientName());
			for(LotSupplierBlockVo lotSupplierBlockItem : lotIngredientItem.getLotSupplierBlockItems() ) {
				System.out.println("\t\t" + lotSupplierBlockItem.getSupplierName() );
				System.out.println("\t\t\tOrigin Country: " + lotSupplierBlockItem.getCountry() + ", State/Province: " + lotSupplierBlockItem.getStateProvince());
				System.out.println("\t\t\tDUNS Number: " + lotSupplierBlockItem.getDunsNumber());
				System.out.println("\t\t\tBlockChain PrevHash: " + lotSupplierBlockItem.getPreviousHash() + ", Hash: " + lotSupplierBlockItem.getHash());
				System.out.println("\t\t\tCategory: " + lotSupplierBlockItem.getSupplierCategory() + ", SubCategory: " + lotSupplierBlockItem.getSupplierSubCategory());
				System.out.println("\t\t\tSupplier Lot Number: " + lotSupplierBlockItem.getSupplierLotNumber() );
			}
		}
	}

}
