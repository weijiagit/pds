/**
 * 
 */
package com.fykj._b._core.menu.vo;

import com.fykj._b._core.tree.JTreeTemplate;

/**
 * @author zhengzw
 *
 */
public class MenuTreeTemplate implements JTreeTemplate {
	
	private String id;
	
	private String pid;
	
	private String name;
	
	private String cls;
	
	private String layout;
	
	private String url;
	
	private Integer sequence;
	

	/* (non-Javadoc)
	 * @see com.fykj.platform.server.impl.menu.controller.JTreeTemplate#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.fykj.platform.server.impl.menu.controller.JTreeTemplate#getPid()
	 */
	@Override
	public String getPid() {
		return pid;
	}

	/* (non-Javadoc)
	 * @see com.fykj.platform.server.impl.menu.controller.JTreeTemplate#getText()
	 */
	@Override
	public String getText() {
		return getName();
	}
	
	@Override
	public Integer getSequence() {
		return sequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public String getRemoteId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteParentId() {
		// TODO Auto-generated method stub
		return null;
	}
}
