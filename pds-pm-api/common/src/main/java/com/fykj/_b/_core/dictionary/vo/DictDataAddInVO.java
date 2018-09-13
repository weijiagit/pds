/**
 * 
 */
package com.fykj._b._core.dictionary.vo;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class DictDataAddInVO implements JInputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7321002641257122205L;
	
	@NotEmpty(message = "字典类型代码不允许为空")
	@NotNull(message = "字典类型代码不允许为空")
	@Length(max = 255, message = "字典类型长度最大支持255个字符")
	private String code;
	
	@NotNull(message = "字典描述不允许为空")
	@NotEmpty(message = "字典描述不允许为空")
	@Length(max = 255, message = "字典描述长度最大支持255个字符")
	private String name;
	
	@NotNull(message = "字典键值不允许为空")
	@NotEmpty(message = "字典键值不允许为空")
	@Length(max = 255, message = "字典键值长度最大支持255个字符")
	private String value;
	
	@NotNull(message = "字典排序不允许为空")
	@NotEmpty(message = "字典排序不允许为空")
	@Digits(integer = 2, fraction = 0)
	@Min(value = 1, message = "字典排序最小值为1")
	private int sequence;

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
