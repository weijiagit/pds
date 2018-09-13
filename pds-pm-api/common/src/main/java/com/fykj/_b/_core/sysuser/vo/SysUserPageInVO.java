package com.fykj._b._core.sysuser.vo;

import com.fykj.kernel._c.model.JInputModel;

public class SysUserPageInVO implements JInputModel{

/**
	 * 
	 */
	private static final long serialVersionUID = -8825627308115038898L;

	private String userAccount;
	
	private String name;
	
	private String description;
	
	private String disabled;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
