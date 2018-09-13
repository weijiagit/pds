package com.fykj.kernel.tree.model;

import com.fykj.kernel._c.model.JModel;

public interface JTree  extends JModel{

	/**
	 *  id
	 * @return
	 */
	String getId();
	/**
	 * 应用实体Id
	 * @return
	 */
	String getEntityId();
	/**
	 * 父类Id
	 * @return
	 */
	String getPid();
	/**
	 * 节点内排序
	 * @return
	 */
	Integer getSequence();
	
	/**
	 * 是否展开
	 * @return
	 */
	boolean isExpand();
	
	
	
}
