/**
 * 
 */
package com.fykj._b._core.dictionary.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class DictionaryAddInVO implements JInputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5091165155587984834L;
	
	@NotNull(message = "字典名称不允许为空!")
	@NotEmpty(message = "字典名称不允许为空!")
	@Length(max = 255, message = "字典名称最大长度支持255个字符")
	private String name;
	
	@NotNull(message = "字典代码不允许为空!")
	@NotEmpty(message = "字典代码不允许为空!")
	@Length(max = 255, message = "字典代码最大长度支持255个字符")
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
