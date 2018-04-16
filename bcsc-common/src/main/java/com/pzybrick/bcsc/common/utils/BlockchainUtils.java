package com.pzybrick.bcsc.common.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.uuid.Generators;
import com.pzybrick.bcsc.common.core.BcscConstants;

public class BlockchainUtils {
	private static final Logger logger = LogManager.getLogger(BlockchainUtils.class);
	public static final ObjectMapper objectMapper;
	private static KeyFactory keyFactory;


	/**
	 * Instantiates a new omh router handler spark batch impl.
	 *
	 * @throws Exception the exception
	 */
	static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
        	keyFactory = KeyFactory.getInstance(BcscConstants.encryptionAlgorithm); 
        } catch( Exception e ) {
        	logger.error("Exception creating KeyFactory, {}", e.getMessage(), e );
        	System.exit(8);
        }
	}

	public static String generateSortableUuid() throws Exception {
		String rawUuid = Generators.timeBasedGenerator().generate().toString();
		String sortableUuid = rawUuid.substring(14, 18) + "-" +  rawUuid.substring(9, 13) + "-" +  rawUuid.substring(0,8) + "-" +  
				rawUuid.substring(19,23) + "-" +  rawUuid.substring(24);
		//116af1b0-2ca4-11e8-9911-4b0c67764edc
		//012345678911234567892123456789312345
		return sortableUuid;
	}
	
	
	
	// Applies Sha256 to a string and returns the result.
	public static String applySha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Applies sha256 to our input,
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain
															// hash as
															// hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Applies ECDSA Signature and returns the result ( as bytes ).
	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}

	// Verifies a String signature
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getEncodedStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	public static synchronized PrivateKey genPrivateKey( String encodedKey ) throws Exception {
		return keyFactory.generatePrivate(  new PKCS8EncodedKeySpec( BlockchainUtils.toByteArray(encodedKey) ) );
	}
	
	public static synchronized PublicKey genPublicKey( String encodedKey ) throws Exception {
		return keyFactory.generatePublic(new X509EncodedKeySpec( BlockchainUtils.toByteArray(encodedKey) ));
	}
	

	public static String toHexString(byte[] array) {
	    return DatatypeConverter.printHexBinary(array);
	}

	public static byte[] toByteArray(String s) {
	    return DatatypeConverter.parseHexBinary(s);
	}
	
	
	public static KeyPair generateKeyPair() throws Exception {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			// Initialize the key generator and generate a KeyPair
			keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
	    	KeyPair keyPair = keyGen.generateKeyPair();
	    	return keyPair;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
