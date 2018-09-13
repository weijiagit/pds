/**
 * 
 */
package com.fykj._b._core.menu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.fykj.TreeConstant;
import com.fykj._b._core.menu.model.SysMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj._b._core.menu.service.MenuService;
import com.fykj._b._core.menu.vo.MenuTreeTemplate;
import com.fykj._b._core.menu.vo.SysMenuOutVO;
import com.fykj._b._core.tree.JTreeNode;
import com.fykj._b._core.tree.JTreeNodeMenu;
import com.fykj._b._core.tree.JTreeUtils;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.util.Copy;

/**
 * @author zhengzw
 *
 */
@Service
@Transactional
public class MenuServiceImpl extends ServiceSupport implements MenuService {

	private SingleEntityManager<SysMenu> internalMenuServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(SysMenu.class);

	@Override
	public List<JTreeNode> getMenuTreeByUser(String userId) {
		String sql = "select t.id as id, t.pid as pid, t.name as name, t.url as url, \n"
				+ " t.layout as layout, t.sequence as sequence, t.cls as cls \n" + "  from t_sys_menu t\n"
				+ " where t.is_available = :isAvailable\n" + "   and exists (select 1 from t_sys_user_role t1\n"
				+ " left join t_sys_role_menu t2 on t2.role_id = t1.role_id and t2.is_available = :isAvailable\n"
				+ " where t.id = t2.menu_id and t1.user_id = :userId\n" + " and t1.is_available = :isAvailable) ORDER BY t.sequence";

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("userId", userId);

		List<SysMenuOutVO> list = nativeQuery().setSql(sql).setParams(params).models(SysMenuOutVO.class);

		List<MenuTreeTemplate> data = new ArrayList<MenuTreeTemplate>();
		for (SysMenuOutVO vo : list) {
			data.add(Copy.simpleCopy(vo, MenuTreeTemplate.class));
		}

		return JTreeUtils.buildTree("-1", data, JTreeNodeMenu.class,TreeConstant.TREE_TYPE_LOCAL);
	}

	@Override
	public List<JTreeNode> getMenuTree() {
		List<SysMenu> list = internalMenuServiceImpl.singleEntityQuery2().condition()
				.equals("isAvailable", Availability.available.ordinal()).ready().order().asc("sequence").ready().models();

		List<MenuTreeTemplate> data = new ArrayList<MenuTreeTemplate>();
		for (SysMenu menu : list) {
			data.add(Copy.simpleCopy(menu, MenuTreeTemplate.class));
		}

		return JTreeUtils.buildTree("-1", data, JTreeNodeMenu.class,TreeConstant.TREE_TYPE_LOCAL);
	}

	@Override
	public SysMenu saveMenu(SysMenu sysMenu) {
		internalMenuServiceImpl.saveOnly(sysMenu);
		return sysMenu;
	}

	@Override
	public SysMenu getMenuById(String id) {
		SysMenu sysMenu = internalMenuServiceImpl.getById(id);
		return sysMenu;
	}

	@Override
	public void editMenu(SysMenu sysMenu) {
		SysMenu sm = internalMenuServiceImpl.getById(sysMenu.getId());
		sm.setName(sysMenu.getName());
		sm.setUrl(sysMenu.getUrl());
		sm.setCls(sysMenu.getCls());
		sm.setLayout(sysMenu.getLayout());
		sm.setSequence(sysMenu.getSequence());

		internalMenuServiceImpl.updateOnly(sm);
	}

	@Override
	public void deleteMenu(String id) {
		if (StringUtils.isNotBlank(id)) {
			SysMenu sysMenu = internalMenuServiceImpl.getById(id);

			internalMenuServiceImpl.delete(sysMenu);
		}
	}
}
