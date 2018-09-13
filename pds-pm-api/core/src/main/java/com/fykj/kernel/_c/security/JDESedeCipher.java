package com.fykj.kernel._c.security;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * DESede Cipher 
 * @author J
 *
 */
public class JDESedeCipher {

	private final Logger LOGGER= LoggerFactory.getLogger(JDESedeCipher.class);
	
	
	private final static String DESEDE = "DESede";
	private final static byte[] algorithmprama = { 9, 99, 88, 1, -1, -88, -99, -9 };
	
	private Cipher encryptCipher;
	private Cipher decryptCipher;
	
	private static JDESedeCipher appCipher;
	
	private JDESedeCipher(){}
	/**
	 * get default cipher , key is default as the class full name.
	 * <strong>Note that the method is only for test, as the key is unsafe.</strong>
	 * @return
	 */
	public static JDESedeCipher get(){
		if(appCipher==null){
			appCipher=new JDESedeCipher();
			String key=DeSedeCipherKeyUtil.getKey();
			appCipher=get(key);
		}
		return appCipher;
	}
	
	public static void main(String[] args) throws Exception {
		
		String string="1111";
		
		String base64Str=JDESedeCipher.get().encrypt(string);
		
		
		String str=JDESedeCipher.get().decrypt(base64Str);
		
		System.out.println("sstr----------->"+str);
		
	}
	
	/**
	 * an initialized new cipher returned to the caller every time.  
	 * <strong>The method should be used in the release production.</strong>
	 * @param key
	 * @return
	 */
	public static JDESedeCipher get(String key){
		try{
			JDESedeCipher appCipher=new JDESedeCipher();
			appCipher.encryptCipher=encrypt(key.getBytes());
			appCipher.decryptCipher=decrypt(key.getBytes());
			return appCipher;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] decodeBase64(String string) {
		return Base64.decodeBase64(string);
	}

	public static String encodeBase64String(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static String encodeBase64URLSafeString(byte[] bytes) {
		return Base64.encodeBase64URLSafeString(bytes);
	}
	
	public static String bytestoBASE64String(byte[] bytes) {
		return encodeBase64URLSafeString(bytes);
	}

	public String encrypt(String plain) {
		try{
			return bytestoBASE64String(this.encryptCipher.doFinal(plain.getBytes()));
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] base64stringtobytes(String string) {
		try {
			return decodeBase64(string);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String decrypt(String encrypted) {
		try{
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("ready to decrupt : ["+encrypted+"] , default charset->"+Charset.defaultCharset().name());
			}
			return new String(this.decryptCipher.doFinal(base64stringtobytes(encrypted)));
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Cipher encrypt(byte[] key) throws Exception {
		IvParameterSpec ivprama = new IvParameterSpec(algorithmprama);
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESEDE);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, ivprama);
		return cipher;
	}

	private static Cipher decrypt(byte[] key) throws Exception {
		IvParameterSpec ivprama = new IvParameterSpec(algorithmprama);
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESEDE);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey, ivprama);
		return cipher;
	}
}
