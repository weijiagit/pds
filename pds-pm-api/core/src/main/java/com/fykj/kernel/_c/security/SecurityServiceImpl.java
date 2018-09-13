package com.fykj.kernel._c.security;

import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import com.fykj.kernel._c.service.ServiceSupport;

@Service(value="com.fykj.platform.server.security.SecurityServiceImpl")
public class SecurityServiceImpl extends ServiceSupport implements SecurityService {

	@Override
	public String encriptyByMD5(String plainText) {
		return string2MD5(plainText);
	}
	
	/*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }    

    /**
	 * RSA加密  ( RETURN BASE64 STRING)
	 */
	@Override
	public String encrypt(byte[] data) throws Exception {
		return JDESedeCipher.get().encrypt(new String(data));
	}

	/**
	 * RSA解密
	 */
	@Override
	public String decrypt(byte[] raw) throws Exception {
		return JDESedeCipher.get().decrypt(new String(raw));
	}

	
	public String SHA1(String decript) throws Exception {
		MessageDigest digest = java.security.MessageDigest
				.getInstance("SHA-1");
		digest.update(decript.getBytes());
		byte messageDigest[] = digest.digest();
		// Create Hex String
		StringBuffer hexString = new StringBuffer();
		// 字节数组转换为 十六进制 数
		for (int i = 0; i < messageDigest.length; i++) {
			String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexString.append(0);
			}
			hexString.append(shaHex);
		}
		return hexString.toString().toUpperCase();
	}
	
}
