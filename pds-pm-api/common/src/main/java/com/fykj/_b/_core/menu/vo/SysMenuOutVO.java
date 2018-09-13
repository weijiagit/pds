/**
 * 
 */
package com.fykj._b._core.menu.vo;

import com.fykj.kernel._c.model.JOutputModel;

/**
 * @author zhengzw
 *
 */
public class SysMenuOutVO implements JOutputModel {

	private static final long serialVersionUID = 839679075606737499L;

	private String id;
	
	private String pid;
	
	private String name;
	
	private String url;
	
	private String layout;
	
	private int sequence;
	
	private String cls;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}
}
