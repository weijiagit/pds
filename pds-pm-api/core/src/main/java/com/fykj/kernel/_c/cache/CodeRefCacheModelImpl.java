package com.fykj.kernel._c.cache;

public class CodeRefCacheModelImpl implements CodeRefCacheModel {
	
	private String type;
	
	private String code;
	
	private String name;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getUri() {
		return CodeRefCacheModelUtil.key(this);
	}

	@Override
	public String type() {
		return this.type;
	}

	@Override
	public String code() {
		return this.code;
	}

	@Override
	public String name() {
		return this.name;
	}

}
