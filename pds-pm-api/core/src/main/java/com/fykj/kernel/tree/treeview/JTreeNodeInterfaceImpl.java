package com.fykj.kernel.tree.treeview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel.tree.model.JEntity;
import com.fykj.kernel.tree.model.JTree;
import com.fykj.kernel.tree.model.JTreeNode;

@Service
public class JTreeNodeInterfaceImpl extends ServiceSupport implements JTreeNodeInterface {

	@Override
	public List<JTreeNode> generateTreeNode(List<? extends JTree> dataList, List<? extends JEntity> entitys) {
		return generateLimitTreeNode(dataList, entitys, false);
	}

	@Override
	public List<JTreeNode> generateLimitTreeNode(List<? extends JTree> dataList, List<? extends JEntity> entitys) {
		return generateLimitTreeNode(dataList, entitys, true);
	}
	

/*	@Override
	public List<JTreeNode> generateAllTreeNode(List<? extends JTree> dataList, List<MenuModelJEntity> entitys) {
		// TODO Auto-generated method stub
		return null;
	}*/

	List<JTreeNode> generateLimitTreeNode(List<? extends JTree> dataList, List<? extends JEntity> entitys, boolean limitType) {
		List<JTreeNode> jTreeNodes = new ArrayList<JTreeNode>();
		Map<String, ? extends JEntity> entityMaps = getEntityMaps(entitys);
		for (JTree jTree : dataList) {
			JTreeNode simpleCopy = simpleCopy(jTree, entityMaps, limitType);
			if(null != simpleCopy ){
				jTreeNodes.add(simpleCopy);
			}
		}
		return jTreeNodes;
	}

	/**
	 * 不做 children's 赋值
	 *  单节点TreeNode
	 * @param jTree
	 * @param entityMaps
	 * @param limitType
	 * @return
	 */
	private JTreeNode simpleCopy(JTree jTree, Map<String, ? extends JEntity> entityMaps, boolean limitType) {
		JTreeNode jnode = new JTreeNode();
		JEntity jEntity = entityMaps.get(jTree.getEntityId());
		jnode.setEntity(jEntity);

		if (limitType && null == jEntity) {
//			jnode.setChildrens(null);
	return null;
		} 
		jnode.setExpand(jTree.isExpand());
		jnode.setSequence(jTree.getSequence());
		jnode.setParentId(jTree.getPid());
		jnode.setId(jTree.getId());

		entityMaps.remove(jTree.getEntityId());
		return jnode;
	}

	private Map<String, ? extends JEntity> getEntityMaps(List<? extends JEntity> entitys) {
		if (null == entitys) {
			entitys  = new ArrayList<JEntity>();
		}
		Map<String, JEntity> backSource = new HashMap<String, JEntity>();
		for (JEntity jEntity : entitys) {
			backSource.put(jEntity.getKey(), jEntity);
		}
		return backSource;
	}

}
