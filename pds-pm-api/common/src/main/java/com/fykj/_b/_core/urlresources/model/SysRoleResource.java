/**
 * 
 */
package com.fykj._b._core.urlresources.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;

/**
 * @author zhengzw
 *
 */
@Entity
@Table(name = "t_sys_role_resources")
public class SysRoleResource extends AbstractEntity {

	@Column(name = "role_id", length = 36)
	private String roleId;
	
	@Column(name = "resources_id", length = 36)
	private String resourcesId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getResourcesId() {
		return resourcesId;
	}

	public void setResourcesId(String resourcesId) {
		this.resourcesId = resourcesId;
	}
}
