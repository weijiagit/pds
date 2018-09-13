/**
 * 
 */
package com.fykj._b._core.tree;

import java.util.ArrayList;
import java.util.List;

import com.fykj.TreeConstant;

/**
 * @author zhengzw
 *
 */
public abstract class JTreeNode {

	private List<JTreeNode> children;
	
	private List<JTreeNode> nodes ;

	private Object entity;

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getPid();

	public abstract void setPid(String pid);

	public abstract String getText();

	public abstract void setText(String text);

	public List<JTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<JTreeNode> children) {
		this.children = children;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public boolean isParent(JTreeTemplate template,String type) {
		if(TreeConstant.TREE_TYPE_LOCAL.equals(type)){
			return getId().equals(template.getPid());
		}else{
			return getId().equals(template.getRemoteParentId());
		}
		
	}
	
	public void addChildren(JTreeNode node) {
		if(children == null) {
			children = new ArrayList<JTreeNode>();
		}
		children.add(node);
	}

	public List<JTreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<JTreeNode> nodes) {
		this.nodes = nodes;
	}
	
	public void addNodes(JTreeNode node) {
		if(nodes == null) {
			nodes = new ArrayList<JTreeNode>();
		}
		nodes.add(node);
	}
}
