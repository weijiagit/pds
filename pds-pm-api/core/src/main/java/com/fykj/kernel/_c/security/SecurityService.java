package com.fykj.kernel._c.security;


public interface SecurityService {

	/**
	 * MD5加密
	 * @param plainText
	 * @return
	 */
	public String encriptyByMD5(String plainText);
	
	/**
	 * RSA加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String encrypt(byte[] data) throws Exception;
	
	/**
	 * RSA解密
	 * @param raw
	 * @return
	 * @throws Exception
	 */
	public String decrypt(byte[] raw) throws Exception;
	
	/**
	 * SHA-1 加密 ，  推送到统一支撑平台使用此API
	 * @param decript
	 * @return  密文大写
	 * @throws Exception
	 */
	public String SHA1(String decript) throws Exception;

}
