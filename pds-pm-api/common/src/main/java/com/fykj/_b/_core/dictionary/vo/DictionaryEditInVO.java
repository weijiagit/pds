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
public class DictionaryEditInVO implements JInputModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 958771162733857710L;
	
	@NotEmpty(message = "字典主键不允许为空")
	@NotNull(message = "字典主键不允许为空")
	private String id;
	
	@NotEmpty(message = "字典名称不允许为空")
	@NotNull(message = "字典名称不允许为空")
	@Length(max = 255, message = "字典名称最大支持255个字符")
	private String name;

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
}
