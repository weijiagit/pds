/**
 * 
 */
package com.fykj._b._core.tree;

import java.io.Serializable;

/**
 * @author zhengzw
 *
 */
public class JTreeNodeMenu extends JTreeNode implements Serializable {
	
	private static final long serialVersionUID = -7449208697521796508L;

	private String id;
	
	private String pid;

	private String text;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getPid() {
		return pid;
	}

	@Override
	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}
}
