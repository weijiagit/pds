package com.fykj.kernel.tree.treeview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fykj.kernel.BusinessExceptionUtil;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel.tree.model.JTreeNode;
import com.fykj.kernel.tree.vo.JTreeTemplate;
import com.fykj.util.JStringUtils;

@Service
public class JsonTreeInterfaceImpl extends ServiceSupport implements JsonTreeInterface<JTreeTemplate> {

	@Override
	public List<JTreeTemplate> generateJTreeTemplates(List<JTreeNode> treeNodes) {
		return null;
	}

	@Override
	public JTreeTemplate generateJTreeTemplate(List<JTreeNode> treeNodes) {
		return myGenerateJTreeTemplate(treeNodes, true);
	}

	private JTreeTemplate myGenerateJTreeTemplate(List<JTreeNode> treeNodes, boolean sort) {
		boolean doOneRootVo = true;
		List<JTreeNode> treeSouceNotRoot = new ArrayList<JTreeNode>();
		/**
		 * 第一次循环 check rootTree & make rootTree away other trees
		 */
		JTreeTemplate rootTree = null;
		for (JTreeNode treeSource : treeNodes) {

			if (JStringUtils.isNullOrEmpty(treeSource.getParentId())) {
				if (doOneRootVo) {
					doOneRootVo = false;
					rootTree = simpleCopy(treeSource);

				} else {
					BusinessExceptionUtil.throwException(new Exception("Tree have many RootTree" + treeSource.getId()));
				}

			} else {
				treeSouceNotRoot.add(treeSource);
			}
		}
		if(null == rootTree ){
			BusinessExceptionUtil.throwException(new RuntimeException("树的合法根节点不存在"));
		}
		List<JTreeNode> treeSourceByPid = getchildrenNode(treeSouceNotRoot, rootTree.getId());
		createJsonTree(rootTree, treeSourceByPid, treeSouceNotRoot, sort);
		return rootTree;
	}

	/**
	 * JtreeTemlate 的构建 是通过 {@link JsonTreeInterfaceImpl#simpleCopy(JTreeNode)}
	 * 
	 * @param rootTree
	 * @param treeSourceByPid
	 * @param treeSouceNotRoot
	 */
	private void createJsonTree(JTreeTemplate treeNode, List<JTreeNode> childrenNodes, List<JTreeNode> treeSouceNotRoot, boolean sort) {

		if (null == childrenNodes || childrenNodes.size() <= 0) {
			return;
		} else {

			List<JTreeTemplate> backList = new ArrayList<JTreeTemplate>();

			if (sort) {
				Collections.sort(childrenNodes, new ListSnDescComparator());
			}

			for (JTreeNode jTreeNode : childrenNodes) {
				JTreeTemplate simpleCopy = simpleCopy(jTreeNode);
				backList.add(simpleCopy);

				if (jTreeNode.isSubclass()) {

					createJsonTree(simpleCopy, getchildrenNode(treeSouceNotRoot, jTreeNode.getId()), treeSouceNotRoot, sort);

				} else {
					// 当前root 不符合有子类条件
				}
			}

			treeNode.setChildrens(backList);

		}
	}

	private List<JTreeNode> getchildrenNode(List<JTreeNode> treeSouceNotRoot, String id) {
		List<JTreeNode> backSouce = new ArrayList<JTreeNode>();
			Iterator<JTreeNode> iterator = treeSouceNotRoot.iterator();
			while (iterator.hasNext()) {
				JTreeNode obj = iterator.next();
				if (JStringUtils.isNotNullOrEmpty(obj.getParentId()) && obj.getParentId().equals(id)){
					backSouce.add(obj);
					iterator.remove();
				}
				//treeSouceNotRoot.remove(o);
			}
		/*for (JTreeNode Source : treeSouceNotRoot) {
			if (JStringUtils.isNotNullOrEmpty(Source.getParentId()) && Source.getParentId().equals(id))
				backSouce.add(Source);
		//	treeSouceNotRoot.remove(Source);
		}
		treeSouceNotRoot.remove(backSouce);*/
		return backSouce;
	}

	private JTreeTemplate simpleCopy(JTreeNode treeSource) {
		JTreeTemplate newInstance;
		try {
			newInstance = JTreeTemplate.class.newInstance();
			newInstance.setId(treeSource.getId());
			newInstance.setEntity(treeSource.getEntity());
			newInstance.setExpand(treeSource.isExpand());
			return newInstance;
		} catch (Exception e) {
			throw new RuntimeException();
		}

	};

}
