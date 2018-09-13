/**
 * 
 */
package com.fykj._b._core.sysuser.vo;


import com.fykj.kernel._c.model.JOutputModel;

/**
 * @author fanxl
 *
 */
public class SysUserOutVO implements JOutputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4226273234912174675L;

	private String userAccount;
	
	private String password;

	private String name;
	
	private String email;

	private String depId;
	private String depName;

	private String description;

	private String disabled;
	
	private String disabledDict;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getDisabledDict() {
		return disabledDict;
	}

	public void setDisabledDict(String disabledDict) {
		this.disabledDict = disabledDict;
	}
	
}
