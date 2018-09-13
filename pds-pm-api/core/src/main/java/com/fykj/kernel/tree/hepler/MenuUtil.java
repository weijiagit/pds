package com.fykj.kernel.tree.hepler;

import java.util.ArrayList;
import java.util.List;

import com.fykj.kernel.tree.model.JTree;
import com.fykj.kernel.tree.model.JTreeModel;
import com.fykj.util.JStringUtils;

public class MenuUtil {
	/**
	 * simple cope treeModel to JTree {@link JTree}
	 * 
	 * @param treeModel
	 * @return
	 */
	public static List<MenuJTree> buildJTreeNode(List<JTreeModel> treeModel) {
		List<MenuJTree> backSource = new ArrayList<MenuJTree>();
		for (JTreeModel jTreeModel : treeModel) {
			backSource.add(buildJTreeNode(jTreeModel));
		}
		return backSource;
	}

	public static MenuJTree buildJTreeNode(JTreeModel treeModel) {
		try {
			MenuJTree jTreeNode = MenuJTree.class.newInstance();

			jTreeNode.setParentId(treeModel.getPid());
			jTreeNode.setSequence(treeModel.getSequence());
			jTreeNode.setExpand(treeModel.isExpand());
			jTreeNode.setEntityId(treeModel.getEntityId());
			if (JStringUtils.isNullOrEmpty(treeModel.getCode())) {
				jTreeNode.setId(treeModel.getId());
			} else {
				jTreeNode.setId(treeModel.getCode());
			}

			return jTreeNode;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
