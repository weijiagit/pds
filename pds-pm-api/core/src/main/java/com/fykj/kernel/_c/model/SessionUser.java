/**
 * 
 */
package com.fykj.kernel._c.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * @author J
 *
 */
public class SessionUser implements JModel {

	/**
	 * the primary key of the login user , uuid
	 */
	@NotEmpty(message = "ID 不允许为空!")
	private String id;
	
	/**
	 * user name to loginin 
	 */
	@NotEmpty(message = "USERNAME 不允许为空!")
	private String userName;

	/**
	 * user name to be display 
	 */
	private String natureName;
	
	private String errorCode;
	private String errorMessage;
	private String loginMessageId;
	
	
	private String password;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNatureName() {
		return natureName;
	}

	public void setNatureName(String natureName) {
		this.natureName = natureName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static final SessionUser DEFUALT=new SessionUser();
	static{
//		DEFUALT.setId("SYS-ID");
		DEFUALT.setId("cf14ed35-cadd-45aa-97d6-f292f90df5a7");
		DEFUALT.setNatureName("系统超级管理员");
		DEFUALT.setUserName("admin");
	}
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getLoginMessageId() {
		return loginMessageId;
	}

	public void setLoginMessageId(String loginMessageId) {
		this.loginMessageId = loginMessageId;
	}
}
