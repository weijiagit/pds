package com.fykj.pds.department.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fykj.kernel._c.model.AbstractEntity;


@Entity
@Table(name = "t_department")
public class Department extends AbstractEntity {
	
	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "name")
	private String name;

	@Column(name = "parent_id")
	private String pid;

	@Column(name = "remote_id")
	private String remoteId;
	
	@Column(name = "remote_parent_id")
	private String remoteParentId;
	
	@Transient
	private Boolean isRemoteParent;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}

	public String getRemoteParentId() {
		return remoteParentId;
	}

	public void setRemoteParentId(String remoteParentId) {
		this.remoteParentId = remoteParentId;
	}

	public Boolean getIsRemoteParent() {
		return isRemoteParent;
	}

	public void setIsRemoteParent(Boolean isRemoteParent) {
		this.isRemoteParent = isRemoteParent;
	}
}
