package com.fykj._b._core.sysrole.vo;

import com.fykj.kernel._c.model.JInputModel;
/**
 * 
 * @author gejj
 *
 */
public class SysRoleCriteriaInVO implements JInputModel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
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
