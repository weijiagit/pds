package com.fykj.kernel.tree.hepler;

import com.fykj.kernel.tree.model.JEntity;

public class MenuModelJEntity implements JEntity {

	private String id;
	private static final long serialVersionUID = 2876476718612397022L;

	/**
	 * 菜单类型 MENU_RESOURCE PAGE_ELEMENT_RESOURCE
	 */
	private String cateGory;
	/**
	 * 树节点ID
	 */
	private String tid;
	/**
	 *菜单描述 
	 *@example 通用查询配置、数据源查询配置
	 */
	private String description;
	
	/**
	 *菜单 名称  
	 *@example 用户管理-添加
	 */
	private String name;
	
	
	private  String menuIcon;
	
	private String url;
	
	private String layoutId;

	private boolean relevance = false;
	public String getCateGory() {
		return cateGory;
	}

	public void setCateGory(String cateGory) {
		this.cateGory = cateGory;
	}


	public boolean isRelevance() {
		return relevance;
	}

	public void setRelevance(boolean relevance) {
		this.relevance = relevance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(String layoutId) {
		this.layoutId = layoutId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id;
	}

}
