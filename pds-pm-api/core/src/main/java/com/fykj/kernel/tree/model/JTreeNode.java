package com.fykj.kernel.tree.model;

import java.util.List;

/**
 * 
 * @author 张军
 *
 */
public class JTreeNode {
	
	private String id;
	
	/**
	 * 是否展开
	 */
	private boolean expand = false;
	
	private String parentId;
	
	private	Object entity;

	private	List<JTreeNode> childrens;
	
	/**
	 * 是否含有子类(默认 true) flase 既不去向下检索
	 */
	private boolean subclass = true;
	/**
	 * 排序
	 */
	private Integer sequence;



	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

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

	public List<JTreeNode> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<JTreeNode> childrens) {
		if(null!=childrens){
			this.childrens = childrens;
			setSubclass(true);
		}else{
			setSubclass(false);	
		}
	}

	public boolean isSubclass() {
		return subclass;
	}


	private void setSubclass(boolean subclass) {
		this.subclass = subclass;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	
}
