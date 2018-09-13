/**
 * 
 */
package com.fykj._b._core.element.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * @author zhengzw
 *
 */
public class ElementPageInVO implements JInputModel {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5861995755418487203L;

	private String name;
	
	private String funcId;

	private String menuId;
	
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

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
}
