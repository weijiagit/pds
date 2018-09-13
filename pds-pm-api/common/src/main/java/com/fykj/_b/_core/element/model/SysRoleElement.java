/**
 * 
 */
package com.fykj._b._core.element.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;

/**
 * @author zhengzw
 *
 */
@Entity
@Table(name = "t_sys_role_element")
public class SysRoleElement extends AbstractEntity {

	@Column(name = "role_id", length = 36)
	private String roleId;
	
	@Column(name = "element_id", length = 36)
	private String elementId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
}
