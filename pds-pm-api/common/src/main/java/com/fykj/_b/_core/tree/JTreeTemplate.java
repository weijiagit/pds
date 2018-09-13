/**
 * 
 */
package com.fykj._b._core.tree;

/**
 * @author zhengzw 树结构模板，要求需要生成树形结构实体类实现此模板
 */
public interface JTreeTemplate {
	
	public String getRemoteId();

	public String getRemoteParentId();
	
	public String getId();
	
	public String getPid();

	public String getText();

	public Integer getSequence();
}
