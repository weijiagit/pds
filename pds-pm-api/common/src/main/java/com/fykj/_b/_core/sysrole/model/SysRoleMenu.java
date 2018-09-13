/**
 * 
 */
package com.fykj._b._core.sysrole.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;

/**
 * @author zhengzw
 *
 */
@Entity
@Table(name = "t_sys_role_menu")
public class SysRoleMenu extends AbstractEntity {
	
	@Column(name = "role_id", length = 36)
	private String roleId;
	
	@Column(name = "menu_id", length = 36)
	private String menuId;
	
	@Column(name = "check_state")
	private String checkState;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
}
