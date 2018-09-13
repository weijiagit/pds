package com.fykj._b._core.sysuser.vo;

import java.util.List;

import com.fykj._b._core.element.vo.SysUserElementOutVO;
import com.fykj._b._core.sysrole.vo.SysRoleGetOutVO;
import com.fykj._b._core.tree.JTreeNode;

public class UserCacheVo {
	
	private String userId;
	private String name;
	private String UserName;
	private List<SysRoleGetOutVO> roles;
	private List<JTreeNode> menuList;
	private List<SysUserElementOutVO> elementList;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public List<SysRoleGetOutVO> getRoles() {
		return roles;
	}
	public void setRoles(List<SysRoleGetOutVO> roles) {
		this.roles = roles;
	}
	public List<JTreeNode> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<JTreeNode> menuList) {
		this.menuList = menuList;
	}
	public List<SysUserElementOutVO> getElementList() {
		return elementList;
	}
	public void setElementList(List<SysUserElementOutVO> elementList) {
		this.elementList = elementList;
	}
	
}
