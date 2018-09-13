package com.fykj.kernel.tree.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;
import com.fykj.kernel.tree.hepler.TreeTypeEnum;

/**
 * 平台 JTree 数据库模型
 * @author 张军
 *
 */
@Entity
@Table(name ="t_sys_tree")
public class JTreeModel extends AbstractEntity{

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name="treetype")
	private TreeTypeEnum treeType;
	
	/**
	 * 
	 */
	@Column(name="pid")
	private String pid;
	
	/**
	 * 
	 */
	@Column(name="code")
	private String code;
	
	/**
	 * 
	 */
	@Column(name="entityId")
	private String entityId;
	
	/**
	 * tree 是否展开
	 */
	@Column(name ="expand")
	private boolean expand = false;
	
	/**
	 * 
	 */
	@Column(name ="sequence")
	private Integer sequence;

	public TreeTypeEnum getTreeType() {
		return treeType;
	}

	public void setTreeType(TreeTypeEnum treeType) {
		this.treeType = treeType;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	
	
}
