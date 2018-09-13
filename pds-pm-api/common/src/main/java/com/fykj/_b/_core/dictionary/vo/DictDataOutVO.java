/**
 * 
 */
package com.fykj._b._core.dictionary.vo;

import com.fykj.kernel._c.model.JOutputModel;

/**
 * @author zhengzw
 *
 */
public class DictDataOutVO implements JOutputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7818937693908507650L;
	
	private String dictId;
	
	private String dictName;
	
	private String dictCode;
	
	private String id;
	
	private String name;
	
	private String value;
	
	private int sequence;

	public String getDictId() {
		return dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
