package com.fykj.kernel.tree.treeview;

import java.util.List;

import com.fykj.kernel.tree.model.JEntity;
import com.fykj.kernel.tree.model.JTree;
import com.fykj.kernel.tree.model.JTreeNode;

public interface JTreeNodeInterface {

	/**
	 * 简单基础的生成JSON树
	 * 
	 * @param dataList
	 *            读取层次数据结果集列表
	 * @return
	 */
	public List<JTreeNode> generateTreeNode(List<? extends JTree> dataList,List<? extends JEntity> entitys);
	
	/**
	 * 获取带有访问权限的JSONTreeNode  
	 * 权限控制方式
	 * @param dataList
	 * @param entitys
	 * @return
	 */
	public List<JTreeNode> generateLimitTreeNode(List<? extends JTree> dataList,List<? extends JEntity> entitys);

//	public List<JTreeNode> generateAllTreeNode(List<? extends JTree> dataList, List<MenuModelJEntity> entitys);
	
}
