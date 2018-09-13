package com.fykj.kernel.tree.vo;

import java.util.List;

import com.fykj.kernel._c.model.JModel;

/**
 *  view object Tree
 * @author 张军
 *
 */
public class JTreeTemplate implements JModel {

	private static final long serialVersionUID = 326292837897699545L;

	private String id;

	/**
	 * 是否展开
	 */
	private boolean expand = false;

	private Object entity;

	private List<JTreeTemplate> childrens;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public List<JTreeTemplate> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<JTreeTemplate> childrens) {
		this.childrens = childrens;
	}
	
	

}
