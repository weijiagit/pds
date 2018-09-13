/**
 * 
 */
package com.fykj._b._core.sysrole.vo;

import com.fykj.kernel._c.model.JOutputModel;

/**
 * @author zhengzw
 *
 */
public class SysUserRoleOutVO implements JOutputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3576007203980043963L;
	
	private String id;
	
	private String userId;
	
	private String userAccount;
	
	private String userName;
	
	private String userDescription;
	
	private String userDisabled;
	
	private String roleId;
	
	private String roleName;
	
	private String roleDescription;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getUserDisabled() {
		return userDisabled;
	}

	public void setUserDisabled(String userDisabled) {
		this.userDisabled = userDisabled;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
}
