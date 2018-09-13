package com.fykj.kernel.tree.service;

import java.util.List;

import com.fykj.kernel.tree.hepler.MenuModelJEntity;
import com.fykj.kernel.tree.hepler.TreeTypeEnum;
import com.fykj.kernel.tree.model.JEntity;
import com.fykj.kernel.tree.model.JTree;
import com.fykj.kernel.tree.model.JTreeModel;
import com.fykj.kernel.tree.vo.JTreeTemplate;

public interface JTreeModelService {

	JTreeTemplate getHtmlMenuJtreeJson( List<? extends JTree> dataList, List<? extends JEntity> entitys);

	JTreeTemplate getMenuJtree( List<? extends JTree> dataList);
	/**
	 * 根据 treeType 获取 JTreeModel
	 * @param serviceContext
	 * @param type
	 * @return
	 */
	List<JTreeModel> getTreeModelsByJTreeType( TreeTypeEnum type);

	/**
	 * root 节点目录添加
	 * @deprecated
	 * @param type
	 * @param jTreeModel
	 * @return
	 */
	JTreeModel saveRootTreeModel( JTreeModel jTreeModel);

	/**
	 * 如果 {@code JTreeModel#getPid()} 是null ， 则认为是root 节点添加
	 * 树节点的添加 {@code JTreeModel#getCode()} 如果不为null, 则校验他父节点是否存在是以code
	 * 字段 
	 * 
	 * @param serviceContext
	 * @param jTreeModel
	 *            {@link JTreeModel#getPid() } is not null
	 * @return
	 */
	JTreeModel saveNodeTreeModel( JTreeModel jTreeModel);

	/**
	 * 节点绑定  将Tree 的叶子关联到相应的node 节点上
	 * 
	 * @param serviceContext
	 * @param jTreeModel
	 * @return
	 */
	Boolean relevanceTreeModel(TreeTypeEnum typeEnum,String keyId,String entityId);

	JTreeTemplate getAllHtmlMenuJtreeJson( List<? extends JTree> jTreeNodes, List<MenuModelJEntity> entitys);

}
