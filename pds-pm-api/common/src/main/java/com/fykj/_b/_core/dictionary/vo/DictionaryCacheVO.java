package com.fykj._b._core.dictionary.vo;

import com.fykj.kernel._c.model.JOutputModel;

public class DictionaryCacheVO implements JOutputModel{

	private static final long serialVersionUID = 1L;
	
	private String dictCode;
	
	private String dictName;
	
	private String dictDataName;
	
	private String dictDataValue;

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictDataName() {
		return dictDataName;
	}

	public void setDictDataName(String dictDataName) {
		this.dictDataName = dictDataName;
	}

	public String getDictDataValue() {
		return dictDataValue;
	}

	public void setDictDataValue(String dictDataValue) {
		this.dictDataValue = dictDataValue;
	}

}
