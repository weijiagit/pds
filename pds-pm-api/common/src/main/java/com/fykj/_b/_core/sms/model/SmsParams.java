/**
 * 
 */
package com.fykj._b._core.sms.model;

import com.fykj.kernel._c.model.JModel;

/**
 * ClassName: SmsParams
 * <pre>
 * Function: TODO
 * </pre>
 * @author 张军
 * @Date 2017年5月27日 下午2:59:00
 *
 */

public class SmsParams implements JModel{

	/**
	 * 账户名
	 */
	private String account;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 接收短信手机号码，只能提交1个号码
	 */
	private String mobile;
	
	/**
	 * 短信填充内容
	 */
	private String[] content;

	/**
	 * 账户名
	 * @return
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 账户名
	 * @param account
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 密码
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 接收短信手机号码，只能提交1个号码
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 接收短信手机号码，只能提交1个号码
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 短信内容（支持300个字的长短信，长短信按多条计费）
	 * @return
	 */
	public String[] getContent() {
		return content;
	}

	/**
	 * 短信内容（支持300个字的长短信，长短信按多条计费）
	 * @param content
	 */
	public void setContent(String[] content) {
		this.content = content;
	}
}
