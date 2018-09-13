/**
 * 
 */
package com.fykj._b._core.menu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;

/**
 * @author zhengzw
 *
 */
@Entity
@Table(name = "t_sys_menu")
public class SysMenu extends AbstractEntity {
	
	@Column(name = "pid")
	private String pid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "layout")
	private String layout;
	
	@Column(name = "cls")
	private String cls;

	@Column(name = "url")
	private String url;
	
	@Column(name = "sequence")
	private Integer sequence;

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

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
