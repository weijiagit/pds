package com.fykj._b._core.sysuser.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

public class UpdatePasswordInVo implements JInputModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2005012424787362344L;
	
	@NotNull(message = "原密码不允许为空!")
	@NotEmpty(message = "原密码不允许为空!")
	private String oldPassword;
	
	@NotNull(message = "新密码不允许为空!")
	@NotEmpty(message = "新密码不允许为空!")
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	

}
