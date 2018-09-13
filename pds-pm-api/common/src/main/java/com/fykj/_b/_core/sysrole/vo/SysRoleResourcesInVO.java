/**
 * 
 */
package com.fykj._b._core.sysrole.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class SysRoleResourcesInVO implements JInputModel {

	private static final long serialVersionUID = 7365173570984408694L;

	@NotEmpty(message = "角色主键不允许为空")
	@NotNull(message = "角色主键不允许为空")
	@Length(max = 36, message = "角色主键最大长度支持36个字符")
	private String roleId;
	
	@NotEmpty(message = "资源主键不允许为空")
	@NotNull(message = "资源主键不允许为空")
	@Length(max = 36, message = "资源主键最大长度支持36个字符")
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
