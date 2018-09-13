/**
 * 
 */
package com.fykj._b._core.element.service.impl;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.fykj._b._core.element.vo.ElementPageInVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj._b._core.element.model.PageElement;
import com.fykj._b._core.element.model.SysRoleElement;
import com.fykj._b._core.element.service.PageElementService;
import com.fykj._b._core.element.vo.ElementPageOutVO;
import com.fykj._b._core.element.vo.SysRoleElementOutVo;
import com.fykj._b._core.element.vo.SysUserElementOutVO;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;

/**
 * @author zhengzw
 *
 */
@Service
@Transactional
public class PageElementSerivceImpl extends ServiceSupport implements PageElementService {

	private SingleEntityManager<PageElement> internalPageElementServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(PageElement.class);

	private SingleEntityManager<SysRoleElement> internalSysRoleElementServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(SysRoleElement.class);

	@Override
	public PageElement saveElement(PageElement pageElement) {
		internalPageElementServiceImpl.saveOnly(pageElement);
		return pageElement;
	}

	@Override
	public JPage<ElementPageOutVO> getElementPage(ElementPageInVO vo, SimplePageRequest page) {
		StringBuilder sql = new StringBuilder("select ");
		sql.append(
				" t.id as id, t.name as name, t.func_id as funcId, t1.id as menuId, t1.name as menuName, t.description as description ");
		sql.append("  from t_sys_element t ");
		sql.append("  left join t_sys_menu t1 on t1.id = t.menu_id and t1.is_available = :isAvailable ");
		sql.append(" where t.is_available = :isAvailable ");

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());

		if (StringUtils.isNotBlank(vo.getName())) {
			sql.append(" and t.name like :name ");
			params.put("name", "%" + vo.getName() + "%");
		}

		if (StringUtils.isNotBlank(vo.getFuncId())) {
			sql.append(" and t.func_id = :funcId ");
			params.put("funcId", vo.getFuncId());
		}
		
		if (StringUtils.isNotBlank(vo.getMenuId())) {
			sql.append(" and t1.id = :menuId ");
			params.put("menuId", vo.getMenuId());
		}


		return nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, ElementPageOutVO.class);
	}

	@Override
	public ElementPageOutVO getElementById(String id) {
		StringBuilder sql = new StringBuilder("select ");
		sql.append(
				" t.id as id, t.name as name, t.func_id as funcId, t1.id as menuId, t1.name as menuName, t.description as description ");
		sql.append("  from t_sys_element t ");
		sql.append("  left join t_sys_menu t1 on t1.id = t.menu_id and t1.is_available = :isAvailable ");
		sql.append(" where t.is_available = :isAvailable ");
		sql.append(" and t.id = :id ");

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("id", id);

		ElementPageOutVO pageElement = nativeQuery().setSql(sql.toString()).setParams(params)
				.model(ElementPageOutVO.class);

		return pageElement;
	}

	@Override
	public void editElement(PageElement pageElement) {
		PageElement element = internalPageElementServiceImpl.getById(pageElement.getId());
		element.setName(pageElement.getName());
		element.setFuncId(pageElement.getFuncId());
		element.setDescription(pageElement.getDescription());
		element.setMenuId(pageElement.getMenuId());
		internalPageElementServiceImpl.updateOnly(element);
	}

	@Override
	public void deleteElement(String id) {
		if (StringUtils.isNotBlank(id)) {
			PageElement pageElement = internalPageElementServiceImpl.getById(id);
			internalPageElementServiceImpl.delete(pageElement);
		}
	}

	@Override
	public void deleteElement(String[] ids) {
		for (String id : ids) {
			deleteElement(id);
		}
	}

	@Override
	public List<SysRoleElementOutVo> getRoleGrantElements(String roleId) {
		String sql = "select t.id as id, t1.name as name, t1.func_id as funcId, t2.name as menuName, t1.description as description\n"
				+ "  from t_sys_role_element t\n"
				+ "  left join t_sys_element t1 on t1.id = t.element_id and t1.is_available = :isAvailable\n"
				+ "  left join t_sys_menu t2 on t1.menu_id = t2.id and t2.is_available = :isAvailable\n"
				+ " where t.role_id = :roleId and t.is_available = :isAvailable";

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("roleId", roleId);

		List<SysRoleElementOutVo> page = nativeQuery().setSql(sql).setParams(params).models(SysRoleElementOutVo.class);

		return page;
	}

	@Override
	public List<SysRoleElementOutVo> getRoleNotGrantElements(String roleId) {
		String sql = "select t.id as id, t.name as name, t.func_id as funcId, t1.name as menuName, t.description as description\n"
				+ "  from t_sys_element t\n"
				+ "  left join t_sys_menu t1 on t1.id = t.menu_id and t1.is_available = 1\n"
				+ " where not exists (select 1 from t_sys_role_element t2 where t2.element_id = t.id and t2.role_id = :roleId and t2.is_available = :isAvailable)\n"
				+ "   and t.is_available = :isAvailable";

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("roleId", roleId);

		List<SysRoleElementOutVo> page = nativeQuery().setSql(sql).setParams(params).models(SysRoleElementOutVo.class);

		return page;
	}

	@Override
	public void deleteRoleElement(String id) {
		if (StringUtils.isNotBlank(id)) {
			SysRoleElement sre = internalSysRoleElementServiceImpl.getById(id);

			internalSysRoleElementServiceImpl.delete(sre);
		}
	}

	@Override
	public SysRoleElement saveSysRoleElement(String roleId, String elementId) {
		if (StringUtils.isNotBlank(roleId) && StringUtils.isNoneBlank(elementId)) {
			SysRoleElement sysRoleElement = new SysRoleElement();
			sysRoleElement.setRoleId(roleId);
			sysRoleElement.setElementId(elementId);

			internalSysRoleElementServiceImpl.saveOnly(sysRoleElement);

			return sysRoleElement;
		}
		return null;
	}

	@Override
	public List<SysUserElementOutVO> getElementByUser(String userId) {
		String sql = "select t.id as id, t.name as name, t.func_id as funcId, t.menu_id as menuId, t.description as description from t_sys_element t\n"
				+ " where t.is_available = :isAvailable \n" + "   and exists (select 1 from t_sys_user_role t1 \n"
				+ " left join t_sys_role_element t2 on t2.role_id = t1.role_id and t2.is_available = :isAvailable\n"
				+ " where t.id = t2.element_id and t1.user_id = :userId\n" + " and t1.is_available = :isAvailable)";

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("userId", userId);

		List<SysUserElementOutVO> list = nativeQuery().setSql(sql).setParams(params).models(SysUserElementOutVO.class);

		return list;
	}
	
	@Override
	public PageElement getElementByFuncId(String funcId) {
		if (StringUtils.isBlank(funcId)){
			return null;
		}

		return internalPageElementServiceImpl.singleEntityQuery2()
											 .conditionDefault()
											 .equals("funcId", funcId)
											 .ready()
											 .model() ;
	}
}
