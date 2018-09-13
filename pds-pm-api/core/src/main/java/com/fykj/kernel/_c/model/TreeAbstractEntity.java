/**
 * 
 */
package com.fykj.kernel._c.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * 一种抽象实体类，提供ID和版本属性，以及基本的持久化方法
 * 
 * @author xiongp
 */
@MappedSuperclass
public abstract class TreeAbstractEntity extends AbstractEntity {

	private static final long serialVersionUID = -7251117760545546759L;
	
	@Column(name = "name")
	private String name;

	@Column(name = "sequence")
	private int sequence;

	@Column(name = "level")
	private int level;

	@Column(name = "path")
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
