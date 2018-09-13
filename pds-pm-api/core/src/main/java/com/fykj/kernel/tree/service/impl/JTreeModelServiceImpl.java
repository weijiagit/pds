package com.fykj.kernel.tree.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fykj.kernel.BusinessExceptionUtil;
import com.fykj.kernel._c.service.JServiceLazyProxy;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.kernel.tree.hepler.MenuModelJEntity;
import com.fykj.kernel.tree.hepler.TreeTypeEnum;
import com.fykj.kernel.tree.model.JEntity;
import com.fykj.kernel.tree.model.JTree;
import com.fykj.kernel.tree.model.JTreeModel;
import com.fykj.kernel.tree.model.JTreeNode;
import com.fykj.kernel.tree.service.JTreeModelService;
import com.fykj.kernel.tree.treeview.JTreeNodeInterface;
import com.fykj.kernel.tree.treeview.JsonTreeInterface;
import com.fykj.kernel.tree.vo.JTreeTemplate;
import com.fykj.util.JStringUtils;

@Service
public class JTreeModelServiceImpl extends ServiceSupport implements JTreeModelService {

	private SingleEntityManager<JTreeModel> internalJTreeModelServiceImpl
	=SingleEntityManagerGetter.get().getInstance(JTreeModel.class);

	
	JsonTreeInterface<?> JsonTreeInterface = JServiceLazyProxy.proxy(JsonTreeInterface.class);
	JTreeNodeInterface jTreeNodeInterface = JServiceLazyProxy.proxy(JTreeNodeInterface.class);

	@Override
	public List<JTreeModel> getTreeModelsByJTreeType( TreeTypeEnum type) {
		List<JTreeModel> models = internalJTreeModelServiceImpl.singleEntityQuery2().condition().equals("treeType", type).ready().models();
		return models;
	}

	@Override
	public JTreeModel saveNodeTreeModel( JTreeModel jTreeModel) {

		if (JStringUtils.isNullOrEmpty(jTreeModel.getPid())) {
			return saveRootTreeModel( jTreeModel);
		//	BusinessExceptionUtil.throwException(new Exception("数据模型错误  pid 不可以同时为 null"));
		}
		//以code字段作为校验标准
		if(JStringUtils.isNotNullOrEmpty(jTreeModel.getCode())){
			//校验父节点是否存在
			if (checkTreeModelByParam(TreeTypeEnum.Html_Menu,"and t.code = '"+jTreeModel.getPid()+"' ")!=1) {
				BusinessExceptionUtil.throwException(new Exception("当前节点父节点不存在  code:"+jTreeModel.getCode()+",pid:"+jTreeModel.getPid()));
			}
			//校验当前结点是否存在
			if (checkTreeModelByParam(TreeTypeEnum.Html_Menu,"and t.code = '"+jTreeModel.getCode()+"' ")!=0) {
				BusinessExceptionUtil.throwException(new Exception("当前节点已存在  code:"+jTreeModel.getCode()+",pid:"+jTreeModel.getPid()));
			}
			
		}else{//以id 字段为校验标准
			BusinessExceptionUtil.throwException(new Exception("当前节点code为null  code:"+jTreeModel.getCode()+",pid:"+jTreeModel.getPid()));
		}
	
		
		internalJTreeModelServiceImpl.saveOnly( jTreeModel);

		return jTreeModel;
	}

	@Override
	public JTreeModel saveRootTreeModel( JTreeModel jTreeModel) {
		try {
			if(JStringUtils.isNullOrEmpty(jTreeModel.getCode())){
				BusinessExceptionUtil.throwException(new Exception("code is null or empty"));
			}
			if (checkTreeModelByParam(jTreeModel.getTreeType(), null) <= 0) {
				internalJTreeModelServiceImpl.saveOnly( jTreeModel);
				return jTreeModel;
			} else {
				BusinessExceptionUtil.throwException(new Exception(jTreeModel.getTreeType().getValue() + " root JsonTree is there in db"));
			}
			return jTreeModel;
		} catch (Exception e) {
			BusinessExceptionUtil.throwException(e);
		}
		return jTreeModel;
	}

	@Override
	public Boolean relevanceTreeModel( TreeTypeEnum typeEnum, String keyId, String entityId) {
		try {
			JTreeModel model;
		List<JTreeModel> models = internalJTreeModelServiceImpl.singleEntityQuery2().condition().equals("treeType", typeEnum).equals("code",keyId).ready().models();
		if(null==models || models.size()==0){
	
			BusinessExceptionUtil.throwException(new RuntimeException("当前结点不存在"));
			return false;
		}	else{
			model =models.get(0);
			model.setEntityId(entityId);
			internalJTreeModelServiceImpl.saveOnly( model);
			return true;
		}

		} catch (Exception e) {
			BusinessExceptionUtil.throwException(e);
		}
		return false;
	}

	/**
	 * dd
	 * 
	 * @param treeType
	 * @param pkey
	 * @param keyType
	 *            <b> keyType true pkey 和 nodekey 的指向 为数据库表中字段 code 
	 * @param nodeKey
	 * @return
	 */
	private int checkTreeModelByParam(TreeTypeEnum treeType,String sql) {

		StringBuffer sb = new StringBuffer();
		sb.append("select count(0)  from  t_jTreeModel t where 1=1");
		if (JStringUtils.isNotNullOrEmpty(treeType.getValue())) {
			sb.append("  and t.treetype ='" + treeType.getValue() + "'");
		}
			if (JStringUtils.isNotNullOrEmpty(sql)) {
				sb.append(sql);
			
		}
		Integer model = nativeQuery().setSql(sb.toString()).setSingle(true).model();
		return model;

	}

	@Override
	public JTreeTemplate getHtmlMenuJtreeJson( List<? extends JTree> dataList, List<? extends JEntity> entitys) {
		try {
			List<JTreeNode> generateLimitTreeNode = jTreeNodeInterface.generateLimitTreeNode(dataList, entitys);
			JTreeTemplate generateJTreeTemplate = JsonTreeInterface.generateJTreeTemplate(generateLimitTreeNode);
			return generateJTreeTemplate;
		} catch (Exception e) {
			BusinessExceptionUtil.throwException(e);
		}
		return null;
	}

	@Override
	public JTreeTemplate getAllHtmlMenuJtreeJson( List<? extends JTree> dataList, List<MenuModelJEntity> entitys) {
		try {
			List<JTreeNode> generateLimitTreeNode = jTreeNodeInterface.generateTreeNode(dataList, entitys);
			JTreeTemplate generateJTreeTemplate = JsonTreeInterface.generateJTreeTemplate(generateLimitTreeNode);
			return generateJTreeTemplate;
		} catch (Exception e) {
			BusinessExceptionUtil.throwException(e);
		}
		return null;
	}

	@Override
	public JTreeTemplate getMenuJtree( List<? extends JTree> dataList) {
		try {
			List<JTreeNode> generateTreeNode = jTreeNodeInterface.generateTreeNode(dataList, null);
			JTreeTemplate generateJTreeTemplate = JsonTreeInterface.generateJTreeTemplate(generateTreeNode);
			return generateJTreeTemplate;
		} catch (Exception e) {
			BusinessExceptionUtil.throwException(e);
		}
		return null;
	}

}
