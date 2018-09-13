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
public class JTreeUtils {

	/**
	 * 根据指定节点生成树
	 * 
	 * @param root
	 * @param data
	 */
	public static List<JTreeNode> buildTree(String root, List<? extends JTreeTemplate> data,
			Class<? extends JTreeNode> clazz,String type) {

		List<JTreeNode> list = new ArrayList<JTreeNode>();

		try {
			if (null == data || data.size() == 0) {
				return list;
			}

			JTreeNode node = null;

			for (JTreeTemplate template : data) {
				String id="";
				String pid="";
				if(TreeConstant.TREE_TYPE_LOCAL.equals(type)){
					id=template.getId();
					pid=template.getPid();
				}else{
					id=template.getRemoteId();
					pid=template.getRemoteParentId();
				}
				if (pid.equals(root)) {
					node = clazz.newInstance();

					node.setId(id);
					node.setPid(pid);
					node.setText(template.getText());
					node.setEntity(template);

					list.add(node);
					// data.remove(template);

					addNode(node, data, clazz,type);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	private static void addNode(JTreeNode pnode, List<? extends JTreeTemplate> data, Class<? extends JTreeNode> clazz,String type) {

		try {
			JTreeNode node = null;
			
			for (JTreeTemplate template : data) {
				String id="";
				String pid="";
				if(TreeConstant.TREE_TYPE_LOCAL.equals(type)){
					id=template.getId();
					pid=template.getPid();
				}else{
					id=template.getRemoteId();
					pid=template.getRemoteParentId();
				}
				
				node = clazz.newInstance();
				node.setId(id);
				node.setPid(pid);
				node.setText(template.getText());
				node.setEntity(template);
				
				if (pnode.isParent(template,type)) {
					pnode.addChildren(node);
					pnode.addNodes(node);
					addNode(node, data, clazz,type);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}