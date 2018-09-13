package com.fykj._b._core.sysrole.service;

import java.util.List;

import com.fykj._b._core.element.model.SysRoleElement;
import com.fykj._b._core.sysrole.vo.SysUserRoleOutVO;
import com.fykj._b._core.element.vo.SysRoleElementOutVo;
import com.fykj._b._core.sysrole.model.SysRole;
import com.fykj._b._core.sysrole.model.SysRoleMenu;
import com.fykj._b._core.sysrole.vo.SysRoleCriteriaInVO;
import com.fykj._b._core.sysrole.vo.SysRoleGetOutVO;
import com.fykj._b._core.urlresources.model.SysRoleResource;
import com.fykj._b._core.urlresources.vo.SysRoleResourcesOutVo;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;

/**
 * 
 * @author gejj
 *
 */
public interface SysRoleService {

	public JPage<SysRole> getPage(SysRoleCriteriaInVO sysRoleCriteriaInVO, SimplePageRequest simplePageRequest);
	
	public List<SysRole> getSysRoles(SysRoleCriteriaInVO sysRoleCriteriaInVO) ;

	public void save(SysRole sysRole);

	public void delete(String[] ids);

	public void modify(SysRole sysRole);

	public SysRole getById(String id);

	public boolean isRoleExists(String name);

	/**
	 * 查询用户角色信息
	 * 
	 * @param serviceContext
	 * @param id
	 */
	public List<SysUserRoleOutVO> getUserRoles(String id);

	/**
	 * 查询用户尚未拥有的角色
	 * 
	 * @param serviceContext
	 * @param id
	 */
	public List<SysUserRoleOutVO> getUserNotRoles(String id);

	public void assignRoleMenu(String roleId, String menuId, String checkState);

	public void assignRoleMenu(String roleId, String menuId);

	public void assignRoleMenu(String roleId, String[] menuIds);

	/**
	 * 
	 * @param context
	 * @param roleId
	 *            角色ID
	 * @param selected
	 *            全选节点数组
	 * @param undetermined
	 *            树节点半选节点数组
	 */
	public void assignRoleMenu(String roleId, String[] selected, String[] undetermined);

	public void deleteRoleMenuByRoleId(String roleId);

	public List<SysRoleMenu> getAssignRoleMenu(String roleId);

	public List<SysRoleResourcesOutVo> getRoleGrantResources(String roleId);

	public List<SysRoleResourcesOutVo> getRoleNotGrantResources(String roleId);

	public void deleteRoleResources(String id);

	public void deleteRoleResources(String[] ids);

	public SysRoleResource saveSysRoleResource(String roleId, String resourceId);

	public void saveSysRoleResource(String roleId, String[] resourceIds);

	public List<SysRoleElementOutVo> getRoleGrantElement(String roleId);

	public List<SysRoleElementOutVo> getRoleNotGrantElement(String roleId);

	public void deleteRoleElement(String id);

	public void deleteRoleElement(String[] ids);

	public SysRoleElement saveSysRoleElement(String roleId, String elementId);

	public void saveSysRoleElement(String roleId, String[] elementIds);

	public List<SysRoleGetOutVO> getSysRoleByUserId(String userId);
}
