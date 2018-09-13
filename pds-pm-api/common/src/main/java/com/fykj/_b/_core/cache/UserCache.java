package com.fykj._b._core.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj._b._core.element.service.PageElementService;
import com.fykj._b._core.element.vo.SysUserElementOutVO;
import com.fykj._b._core.menu.service.MenuService;
import com.fykj._b._core.sysrole.service.SysRoleService;
import com.fykj._b._core.sysrole.vo.SysRoleGetOutVO;
import com.fykj._b._core.sysuser.vo.UserCacheVo;
import com.fykj._b._core.tree.JTreeNode;
import com.fykj.kernel._c.cache.EhCacheService;

@Component
public class UserCache {
	
	@Autowired(required=false)
	private EhCacheService<String,Object> ehCacheService;
	
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private PageElementService pageElementService;

	
	public void load(String token,UserCacheVo cacheVo){
		// 角色信息
		List<SysRoleGetOutVO> list = sysRoleService.getSysRoleByUserId(cacheVo.getUserId());
		cacheVo.setRoles(list);

		// 菜单
		List<JTreeNode> menuList = menuService.getMenuTreeByUser(cacheVo.getUserId());
		cacheVo.setMenuList(menuList);

		// 页面元素
		List<SysUserElementOutVO> elementList = pageElementService.getElementByUser(cacheVo.getUserId());
		cacheVo.setElementList(elementList);
					
		
		ehCacheService.put(token, cacheVo);
	}
	
	public UserCacheVo getUserCacheVo(String token){
		Object cache = ehCacheService.get(token);
		if(cache != null){

			return (UserCacheVo)cache;

		}
		return null;
	}
}
