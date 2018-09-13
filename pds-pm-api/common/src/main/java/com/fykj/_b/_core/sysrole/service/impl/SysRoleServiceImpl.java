package com.fykj._b._core.sysrole.service.impl;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.fykj._b._core.sysrole.vo.SysUserRoleOutVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj._b._core.element.model.SysRoleElement;
import com.fykj._b._core.element.service.PageElementService;
import com.fykj._b._core.element.vo.SysRoleElementOutVo;
import com.fykj._b._core.sysrole.SysRoleCodesTable;
import com.fykj._b._core.sysrole.model.SysRole;
import com.fykj._b._core.sysrole.model.SysRoleMenu;
import com.fykj._b._core.sysrole.service.SysRoleService;
import com.fykj._b._core.sysrole.vo.SysRoleCriteriaInVO;
import com.fykj._b._core.sysrole.vo.SysRoleGetOutVO;
import com.fykj._b._core.urlresources.model.SysRoleResource;
import com.fykj._b._core.urlresources.service.URLResourcesService;
import com.fykj._b._core.urlresources.vo.SysRoleResourcesOutVo;
import com.fykj.kernel.BusinessException;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.util.JStringUtils;

/**
 * 
 * @author gejj
 *
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceSupport implements SysRoleService {

	private SingleEntityManager<SysRole> internalSysRoleService = SingleEntityManagerGetter.get()
			.getInstance(SysRole.class);

	@Autowired
	private URLResourcesService resourcesService;

	@Autowired
	private PageElementService pageElementService;

	private SingleEntityManager<SysRoleMenu> internalSysRoleMenuServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(SysRoleMenu.class);

	@Override
	public JPage<SysRole> getPage(SysRoleCriteriaInVO sysRoleCriteriaInVO, SimplePageRequest simplePageRequest) {
		StringBuilder jpql = new StringBuilder("select t from SysRole t where t.isAvailable= :isAvailable ");
		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		if (JStringUtils.isNotNullOrEmpty(sysRoleCriteriaInVO.getName())) {
			jpql.append(" and t.name like :name ");
			params.put("name", "%" + sysRoleCriteriaInVO.getName() + "%");
		}
		if (JStringUtils.isNotNullOrEmpty(sysRoleCriteriaInVO.getDescription())) {
			jpql.append(" and t.description like :description ");
			params.put("description", "%" + sysRoleCriteriaInVO.getDescription() + "%");
		}
		jpql.append(" order by t.createDate desc ");

		JPage<SysRole> page = jpqlQuery().setJpql(jpql.toString()).setParams(params).setPageable(simplePageRequest)
				.modelPage();
		return page;
	}

	@Override
	public void save(SysRole sysRole) {
		if (isRoleExists(sysRole.getName())) {
			throw new BusinessException("当前角色名字已经存在,请使用其他角色名称!");
		}
		internalSysRoleService.saveOnly(sysRole);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			internalSysRoleService.delete(id);
		}
	}

	@Override
	public void modify(SysRole sysRole) {
		internalSysRoleService.updateOnly(sysRole);
	}

	@Override
	public SysRole getById(String id) {
		return internalSysRoleService.getById(id);
	}

	@Override
	public boolean isRoleExists(String name) {
		SysRole sysRole = internalSysRoleService.singleEntityQuery2().conditionDefault().equals("name", name).ready()
				.model();

		return sysRole != null;
	}

	@Override
	public List<SysUserRoleOutVO> getUserRoles(String id) {
		String sql = "select t.id, t.user_id as userId, t1.user_account as userAccount, \n"
				+ " t1.name as userName, t1.description as userDescription, t1.disabled as userDisabled, \n"
				+ " t.role_id as roleId, t2.name as roleName, t2.description as roleDescription\n"
				+ " from t_sys_user_role t\n"
				+ " left join t_sys_user t1 on t.user_id = t1.id and t1.is_available = :isAvailable\n"
				+ " left join t_sys_role t2 on t.role_id = t2.id and t2.is_available = :isAvailable\n"
				+ " where t.user_id = :id and t.is_available = :isAvailable";
		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("id", id);
		List<SysUserRoleOutVO> page = nativeQuery().setSql(sql).setParams(params).models(SysUserRoleOutVO.class);
		return page;
	}

	@Override
	public List<SysUserRoleOutVO> getUserNotRoles(String id) {
		String sql = "select t.id as id, t.id as roleId, t.name as roleName, t.description as roleDescription \n"
				+ "  from t_sys_role t \n" + " where not exists (select 1 \n" + "  from t_sys_user_role t1 \n"
				+ "  where t1.user_id = :id \n" + "  and t.id = t1.role_id \n"
				+ "  and t1.is_available = :isAvailable) \n" + "  and t.is_available = :isAvailable";

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("id", id);
		List<SysUserRoleOutVO> page = nativeQuery().setSql(sql).setParams(params).models(SysUserRoleOutVO.class);
		return page;
	}

	@Override
	public void assignRoleMenu(String roleId, String menuId) {
		assignRoleMenu(roleId, menuId, SysRoleCodesTable.MenuCheckState.SELECTED);
	}

	@Override
	public void assignRoleMenu(String roleId, String[] menuIds) {
		deleteRoleMenuByRoleId(roleId);
		for (String menuId : menuIds) {
			assignRoleMenu(roleId, menuId);
		}
	}

	@Override
	public void assignRoleMenu(String roleId, String menuId, String checkState) {
		if (StringUtils.isNotEmpty(roleId) && StringUtils.isNotEmpty(menuId)) {
			SysRoleMenu srm = new SysRoleMenu();
			srm.setRoleId(roleId);
			srm.setMenuId(menuId);
			srm.setCheckState(checkState);

			internalSysRoleMenuServiceImpl.saveOnly(srm);
		}
	}

	@Override
	public void assignRoleMenu(String roleId, String[] selected, String[] undetermined) {
		deleteRoleMenuByRoleId(roleId);
		if (null != selected && selected.length > 0) {
			assignRoleMenu(roleId, selected);
		}
		if (null != undetermined && undetermined.length > 0) {
			for (String str : undetermined) {
				assignRoleMenu(roleId, str, SysRoleCodesTable.MenuCheckState.UNDETERMINED);
			}
		}
	}

	@Override
	public void deleteRoleMenuByRoleId(String roleId) {
		List<SysRoleMenu> list = internalSysRoleMenuServiceImpl.singleEntityQuery2().conditionDefault()
				.equals("roleId", roleId).ready().models();
		if (null != list && !list.isEmpty()) {
			internalSysRoleMenuServiceImpl.deleteAllByModels(list);
		}
	}

	@Override
	public List<SysRoleMenu> getAssignRoleMenu(String roleId) {
		List<SysRoleMenu> list = internalSysRoleMenuServiceImpl.singleEntityQuery2().conditionDefault()
				.equals("roleId", roleId).ready().models();
		return list;
	}

	@Override
	public List<SysRoleResourcesOutVo> getRoleGrantResources(String roleId) {
		return resourcesService.getRoleGrantResources(roleId);
	}

	@Override
	public List<SysRoleResourcesOutVo> getRoleNotGrantResources(String roleId) {
		return resourcesService.getRoleNotGrantResources(roleId);
	}

	public void deleteRoleResources(String id) {
		if (StringUtils.isNotBlank(id)) {
			resourcesService.deleteRoleResources(id);
		}
	}

	@Override
	public void deleteRoleResources(String[] ids) {
		for (String id : ids) {
			deleteRoleResources(id);
		}
	}

	@Override
	public SysRoleResource saveSysRoleResource(String roleId, String resourcesId) {
		return resourcesService.saveSysRoleResource(roleId, resourcesId);
	}

	@Override
	public void saveSysRoleResource(String roleId, String[] resourceIds) {
		for (String resourceId : resourceIds) {
			saveSysRoleResource(roleId, resourceId);
		}
	}

	@Override
	public List<SysRoleElementOutVo> getRoleGrantElement(String roleId) {
		return pageElementService.getRoleGrantElements(roleId);
	}

	@Override
	public List<SysRoleElementOutVo> getRoleNotGrantElement(String roleId) {
		return pageElementService.getRoleNotGrantElements(roleId);
	}

	@Override
	public void deleteRoleElement(String id) {
		if (StringUtils.isNotBlank(id)) {
			pageElementService.deleteRoleElement(id);
		}
	}

	@Override
	public void deleteRoleElement(String[] ids) {
		for (String id : ids) {
			deleteRoleElement(id);
		}
	}

	@Override
	public SysRoleElement saveSysRoleElement(String roleId, String elementId) {
		return pageElementService.saveSysRoleElement(roleId, elementId);
	}

	@Override
	public void saveSysRoleElement(String roleId, String[] elementIds) {
		for (String elementId : elementIds) {
			saveSysRoleElement(roleId, elementId);
		}
	}

	@Override
	public List<SysRoleGetOutVO> getSysRoleByUserId(String userId) {
		String sql = "select t.id as id, t.name as name, t.description as description from t_sys_role t\n"
				+ " where exists (select 1 from t_sys_user_role t1 where t1.role_id = t.id and t1.user_id = :userId and t1.is_available = :isAvailable)\n"
				+ "   and t.is_available = :isAvailable";

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("userId", userId);

		List<SysRoleGetOutVO> page = nativeQuery().setSql(sql).setParams(params).models(SysRoleGetOutVO.class);
		return page;
	}

	@Override
	public List<SysRole> getSysRoles(SysRoleCriteriaInVO sysRoleCriteriaInVO) {
		List<SysRole> sysRoles = internalSysRoleService
									.singleEntityQuery2()
									.conditionDefault()
									.equals("name", JStringUtils.isNullOrEmpty(sysRoleCriteriaInVO.getName()) ? null : sysRoleCriteriaInVO.getName())
									.equals("isAvailable", 1)
									.ready()
									.models() ;
		return sysRoles;
	}
}
