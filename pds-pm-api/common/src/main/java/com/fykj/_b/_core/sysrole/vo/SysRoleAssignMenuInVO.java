/**
 * 
 */
package com.fykj._b._core.sysrole.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class SysRoleAssignMenuInVO implements JInputModel {

	/**
	 * 2655680292512872158L
	 */
	private static final long serialVersionUID = 2655680292512872158L;
	
	@NotNull(message = "角色主键不允许为空")
	@NotEmpty(message = "角色主键不允许为空")
	private String roleId;
	
	private String selected;
	
	private String undetermined;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getUndetermined() {
		return undetermined;
	}

	public void setUndetermined(String undetermined) {
		this.undetermined = undetermined;
	}
}
