/**
 * 
 */
package com.fykj._b._core.menu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fykj._b._core.menu.model.SysMenu;
import com.fykj._b._core.menu.vo.SysMenuEditInVO;
import com.fykj._b._core.menu.vo.SysMenuSaveInVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj._b._core.menu.service.MenuService;
import com.fykj._b._core.tree.JTreeNode;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.util.Copy;

/**
 * @author zhengzw
 *
 */
@Controller
@RequestMapping("/sysmenu")
public class MenuController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private MenuService menuService;

	/**
	 * 获取父节点id
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getParentId",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getParentId(String id) throws Exception {
		Map<String, String> result = new HashMap<String, String>() ;
		result.put("parentId", id) ;
		return InvokeResult.success(result) ;
	}

	/**
	 * 根据用户获取菜单
	 * 
	 * @return
	 */
	@RequestMapping(path="/loadMenuTreeUser",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult loadMenuTreeUser() throws Exception {
		String userId = ServerSessionHolder.getSessionUser().getId();
		List<JTreeNode> list = menuService.getMenuTreeByUser(userId);
		return InvokeResult.success(list);
	}

	/**
	 * 加载菜单数
	 * 
	 * @return
	 */
	@RequestMapping(path="/loadMenuTree",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult loadMenuTree() throws Exception {
		List<JTreeNode> list = menuService.getMenuTree();
		return InvokeResult.success(list);
	}

	/**
	 * 保存菜单
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/saveMenu",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveMenu(SysMenuSaveInVO vo) throws Exception {
		SysMenu sysMenu = menuService.saveMenu(Copy.simpleCopy(vo, SysMenu.class));
		return InvokeResult.success(sysMenu);
	}

	/**
	 * 根据ID获取菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getMenuById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getMenuById(String id) throws Exception {
		SysMenu sysMenu = menuService.getMenuById(id);
		return InvokeResult.success(sysMenu);
	}

	/**
	 * 编辑菜单
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/editMenu",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult editMenu(SysMenuEditInVO vo) throws Exception {
		menuService.editMenu(Copy.simpleCopy(vo, SysMenu.class));
		return InvokeResult.success(true);
	}

	/**
	 * 删除菜单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/deleteMenu",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult deleteMenu(String id) throws Exception {
		menuService.deleteMenu(id);
		return InvokeResult.success(true);
	}
}
