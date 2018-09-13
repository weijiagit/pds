package com.fykj._b._core.sysuser.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

@SuppressWarnings("serial")
public class RetrievePassowrd  implements JInputModel {

	
	@NotNull(message = "新密码不允许为空!")
	@NotEmpty(message = "新密码不允许为空!")
	private String newPassword;
	
	private String userAccount;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
