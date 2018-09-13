package com.fykj._b._core.sysparam.vo;

import com.fykj.kernel._c.model.JInputModel;

public class SysParamCriteriaInVO implements JInputModel {

	private static final long serialVersionUID = 1L;

	private String code;
	
	private String value;
	
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
