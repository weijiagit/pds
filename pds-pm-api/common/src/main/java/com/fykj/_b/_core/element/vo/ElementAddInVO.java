/**
 * 
 */
package com.fykj._b._core.element.vo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class ElementAddInVO implements JInputModel {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2264927080998313570L;

	@NotEmpty(message = "页面元素名称不允许为空")
	@NotNull(message = "页面元素名称不允许为空")
	@Length(max = 255, message = "页面元素名称最大长度支持255个字符")
	private String name;
	
	@NotEmpty(message = "菜单主键不允许为空")
	@NotNull(message = "菜单主键不允许为空")
	@Length(max = 36, message = "页面元素名称最大长度支持36个字符")
	private String menuId;
	
	@NotEmpty(message = "页面元素标识不允许为空")
	@NotNull(message = "页面元素标识不允许为空")
	@Length(max = 255, message = "页面元素标识最大长度支持255个字符")
	private String funcId;
	
	@NotEmpty(message = "页面元素描述不允许为空")
	@NotNull(message = "页面元素描述不允许为空")
	@Length(max = 255, message = "页面元素描述最大长度支持255个字符")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
