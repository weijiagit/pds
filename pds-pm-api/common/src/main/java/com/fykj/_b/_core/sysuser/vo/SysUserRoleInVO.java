/**
 * 
 */
package com.fykj._b._core.sysuser.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class SysUserRoleInVO implements JInputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8675542993687218331L;
	
	@NotNull(message = "用户ID不能为空!")
	@NotEmpty(message = "用户ID不能为空!")
	private String userId;
	
	@NotNull(message = "用户关联角色ID不能为空!")
	@NotEmpty(message = "用户关联角色ID不能为空!")
	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
