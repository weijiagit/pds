/**
 * 
 */
package com.fykj._b._core.urlresources.service.impl;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.fykj._b._core.urlresources.model.URLResources;
import com.fykj._b._core.urlresources.service.URLResourcesService;
import com.fykj._b._core.urlresources.vo.SysRoleResourcesOutVo;
import com.fykj._b._core.urlresources.vo.URLResourcesPageInVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj._b._core.urlresources.model.SysRoleResource;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.kernel.springjpa.query2.JCondition;

/**
 * @author zhengzw
 *
 */
@Service
@Transactional
public class URLResourcesServiceImpl extends ServiceSupport implements URLResourcesService {

	private SingleEntityManager<URLResources> internalURLResourcesServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(URLResources.class);

	private SingleEntityManager<SysRoleResource> internalSysRoleResourcesServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(SysRoleResource.class);

	@Override
	public JPage<URLResources> getUrlResourcesPage(URLResourcesPageInVO vo, SimplePageRequest page) {
		JCondition jc = internalURLResourcesServiceImpl.singleEntityQuery2().conditionDefault();
		if (StringUtils.isNotBlank(vo.getName())) {
			jc.likes("name", vo.getName());
		}
		if (StringUtils.isNotBlank(vo.getUrl())) {
			jc.equals("url", vo.getUrl());
		}
		JPage<URLResources> pages = jc.ready().modelPage(page);
		return pages;
	}

	@Override
	public URLResources getUrlResourcesById(String id) {
		URLResources res = internalURLResourcesServiceImpl.getById(id);
		return res;
	}

	@Override
	public URLResources saveUrlResources(URLResources res) {
		internalURLResourcesServiceImpl.saveOnly(res);
		return res;
	}

	@Override
	public void editUrlResources(URLResources res) {
		URLResources urlResources = internalURLResourcesServiceImpl.getById(res.getId());
		urlResources.setName(res.getName());
		urlResources.setUrl(res.getUrl());
		urlResources.setDescription(res.getDescription());
		internalURLResourcesServiceImpl.updateOnly(urlResources);
	}

	@Override
	public void deleteUrlResources(String id) {
		if (StringUtils.isNotBlank(id)) {
			URLResources res = internalURLResourcesServiceImpl.getById(id);
			internalURLResourcesServiceImpl.delete(res);
		}
	}

	@Override
	public void deleteUrlResources(String[] ids) {
		for (String id : ids) {
			deleteUrlResources(id);
		}
	}

	@Override
	public List<SysRoleResourcesOutVo> getRoleGrantResources(String roleId) {
		String sql = "select t.id as id, t1.name as name, t1.url as url, t1.description as description\n"
				+ "  from t_sys_role_resources t \n"
				+ "  left join t_sys_resources t1 on t1.id = t.resources_id and t1.is_available = :isAvailable\n"
				+ " where t.role_id = :roleId and t.is_available = :isAvailable";

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("roleId", roleId);

		List<SysRoleResourcesOutVo> page = nativeQuery().setSql(sql.toString()).setParams(params)
				.models(SysRoleResourcesOutVo.class);
		return page;
	}

	@Override
	public List<SysRoleResourcesOutVo> getRoleNotGrantResources(String roleId) {
		StringBuilder sql = new StringBuilder(" select ");
		sql.append(" t.id as id, t.name as name, t.url as url, t.description as description ");
		sql.append(" from t_sys_resources t where not exists ");
		sql.append(" (select 1 from t_sys_role_resources t1 where t1.resources_id = t.id and t1.role_id = :roleId"
				+ " and t1.is_available = :isAvailable) ");
		sql.append(" and t.is_available = :isAvailable ");

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("roleId", roleId);

		List<SysRoleResourcesOutVo> page = nativeQuery().setSql(sql.toString()).setParams(params)
				.models(SysRoleResourcesOutVo.class);
		return page;
	}

	@Override
	public void deleteRoleResources(String id) {
		if (StringUtils.isNotBlank(id)) {
			SysRoleResource sre = internalSysRoleResourcesServiceImpl.getById(id);
			internalSysRoleResourcesServiceImpl.delete(sre);
		}
	}

	@Override
	public SysRoleResource saveSysRoleResource(String roleId, String resourcesId) {
		if (StringUtils.isNotBlank(roleId) && StringUtils.isNoneBlank(resourcesId)) {
			SysRoleResource sysRoleResource = new SysRoleResource();
			sysRoleResource.setRoleId(roleId);
			sysRoleResource.setResourcesId(resourcesId);
			internalSysRoleResourcesServiceImpl.saveOnly(sysRoleResource);
			return sysRoleResource;
		}
		return null;
	}
}
