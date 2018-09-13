/**
 * 
 */
package com.fykj._b._core.element.vo;

import com.fykj.kernel._c.model.JOutputModel;

/**
 * @author zhengzw
 *
 */
public class SysRoleElementOutVo implements JOutputModel {
	
	private static final long serialVersionUID = 7760791759161537984L;

	private String id;
	
	private String name;
	
	private String funcId;
	
	private String menuName;
	
	private String description;

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

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
