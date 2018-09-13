/**
 * 
 */
package com.fykj._b._core.sysuser.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class SysUserAddInVO implements JInputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4226273234912174675L;

	@NotNull(message = "用户账号不允许为空!")
	@NotEmpty(message = "用户账号不允许为空!")
	@Length(max = 255, message = "用户账号最大支持长度255个字符!")
	private String userAccount;

	@NotNull(message = "用户名称不允许为空!")
	@NotEmpty(message = "用户名称不允许为空!")
	@Length(max = 255, message = "用户名称最大支持长度255个字符!")
	private String name;
	
	@Length(max = 255, message = "邮箱最大支持长度255个字符!")
	private String email;

	private String password;
	
	private String depId;
	private String depName;
	
	private String depIdOther;

	@Length(max = 255, message = "用户描述最大支持长度255个字符!")
	private String description;

	@NotNull(message = "用户禁用标识不允许为空!")
	@NotEmpty(message = "用户禁用标识不允许为空!")
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

	public String getDepIdOther() {
		return depIdOther;
	}

	public void setDepIdOther(String depIdOther) {
		this.depIdOther = depIdOther;
	}
	
}
