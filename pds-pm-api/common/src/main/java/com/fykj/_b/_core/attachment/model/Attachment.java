package com.fykj._b._core.attachment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;


/**
 * Auto Generated Entity
 * 
 * @author zhangj
 * 
 */
@Entity
@Table(name = "t_attachment")
public class Attachment extends AbstractEntity {

	@Column(name = "_desc")
	private String desc;

	@Column(name = "path")
	private String path;

	@Column(name = "name")
	private String name;
	
	@Column(name = "suffix")
	private String suffix;

	@Column(name = "fk_id")
	private String fkId;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}
}