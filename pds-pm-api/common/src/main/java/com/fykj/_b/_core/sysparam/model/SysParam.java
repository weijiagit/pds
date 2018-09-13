package com.fykj._b._core.sysparam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;

@Entity
@Table(name = "t_sys_param")
public class SysParam  extends AbstractEntity{

	@Column(name = "_code")
	private String code;
	
	@Column(name = "_value")
	private String value;
	
	@Column(name = "_desc")
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
