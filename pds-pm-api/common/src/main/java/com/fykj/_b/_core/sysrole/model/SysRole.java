package com.fykj._b._core.sysrole.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;
/**
 * 系统角色
 * @author gejj
 *
 */
@Entity
@Table(name = "t_sys_role")
public class SysRole extends AbstractEntity {
	
	@Column(name="name")
	private String name;
	@Column(name="description")
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
