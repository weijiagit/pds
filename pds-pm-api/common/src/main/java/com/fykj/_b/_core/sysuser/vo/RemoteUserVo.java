package com.fykj._b._core.sysuser.vo;

import com.fykj.kernel._c.model.JInputModel;
import com.fykj.kernel._c.model.JOutputModel;

public class RemoteUserVo implements JOutputModel, JInputModel{
	
	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userAccount;
	private String name;
	private String email;
	private String depId;
	private String depIdOther;
	private String depName;
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepIdOther() {
		return depIdOther;
	}
	public void setDepIdOther(String depIdOther) {
		this.depIdOther = depIdOther;
	}
	

}
