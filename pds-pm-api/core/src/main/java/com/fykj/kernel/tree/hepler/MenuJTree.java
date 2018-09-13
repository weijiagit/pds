package com.fykj.kernel.tree.hepler;

import com.fykj.kernel.tree.model.JTree;

public class MenuJTree implements JTree {

	private static final long serialVersionUID = -6527565228151994003L;

	private String id;

	/**
	 * 是否展开
	 */
	private boolean expand = false;

	private String parentId;

	private String entityId;

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

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}


	public boolean isSubclass() {
		return subclass;
	}


	@Override
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	@Override
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	@Override
	public String getPid() {
		return parentId;
	}

}
