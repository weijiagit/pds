/**
 * 
 */
package com.fykj._b._core.sms;

/**
 * ClassName: SmsContent
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月31日 上午11:06:20
 *
 */

public interface SmsContent {

	/**
	 * 短信验证码信息 ？ 填充字符
	 */
	public static String dxyzm ="[你问我答] 短信验证码?，请及时完成验证。";
	
	public static String xgmsy ="[你问我答] 您正在修改密码， 验证码?，请及时提交验证码，切勿将验证码泄露于他人。";
	
	/**
	 * 
	 * getContent:
	 * @param type
	 * @param ms
	 * @return
	 * @author 张军
	 */
	public String getContent(String[] ms);
}
