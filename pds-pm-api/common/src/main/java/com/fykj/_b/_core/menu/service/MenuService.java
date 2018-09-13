/**
 * 
 */
package com.fykj._b._core.menu.service;

import java.util.List;

import com.fykj._b._core.menu.model.SysMenu;
import com.fykj._b._core.tree.JTreeNode;

/**
 * @author zhengzw
 *
 */
public interface MenuService {

	public List<JTreeNode> getMenuTreeByUser(String userId);

	public List<JTreeNode> getMenuTree();

	public SysMenu saveMenu(SysMenu sysMenu);

	public SysMenu getMenuById(String id);

	public void editMenu(SysMenu sysMenu);

	public void deleteMenu(String id);
}
