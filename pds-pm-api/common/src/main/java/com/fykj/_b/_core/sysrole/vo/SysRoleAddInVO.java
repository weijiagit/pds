package com.fykj._b._core.sysrole.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;
/**
 * 
 * @author gejj
 *
 */
public class SysRoleAddInVO implements JInputModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "角色名称不允许为空!")
	@NotEmpty(message = "角色名称不允许为空!")
	@Length(max = 255, message = "角色名称最大支持长度255个字符!")
	private String name;
	@Length(max = 255, message = "角色描述最大支持长度255个字符!")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
