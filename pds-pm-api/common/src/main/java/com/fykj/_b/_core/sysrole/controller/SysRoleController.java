package com.fykj._b._core.sysrole.controller;

import java.util.ArrayList;
import java.util.List;

import com.fykj._b._core.sysrole.vo.SysRoleElementInVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj._b._core.element.vo.SysRoleElementOutVo;
import com.fykj._b._core.sysrole.model.SysRole;
import com.fykj._b._core.sysrole.model.SysRoleMenu;
import com.fykj._b._core.sysrole.service.SysRoleService;
import com.fykj._b._core.sysrole.vo.SysRoleAddInVO;
import com.fykj._b._core.sysrole.vo.SysRoleAssignMenuInVO;
import com.fykj._b._core.sysrole.vo.SysRoleCriteriaInVO;
import com.fykj._b._core.sysrole.vo.SysRoleGetOutVO;
import com.fykj._b._core.sysrole.vo.SysRoleModifyInVO;
import com.fykj._b._core.sysrole.vo.SysRoleResourcesInVO;
import com.fykj._b._core.urlresources.vo.SysRoleResourcesOutVo;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.JPageUtil;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.util.Copy;
import com.fykj.web.model.SimplePageRequestVO;

/**
 * 
 * @author gejj
 *
 */
@Controller
@RequestMapping("/sysrole")
public class SysRoleController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 新增角色
	 * 
	 * @param sysRoleAddInVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path="/saveSysRole",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveSysRole(SysRoleAddInVO sysRoleAddInVO) throws Exception {
		SysRole sysRole = Copy.simpleCopy(sysRoleAddInVO, SysRole.class);
		sysRoleService.save(sysRole);
		return InvokeResult.success(sysRole.getId());
	}

	/**
	 * 查询角色分页
	 * 
	 * @param serviceContext
	 * @param sysRoleCriteriaInVO
	 * @param simplePageRequestVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path="/getPage",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getPage(SysRoleCriteriaInVO sysRoleCriteriaInVO, SimplePageRequestVO simplePageRequestVO) {
		JPage<SysRole> page = sysRoleService.getPage(sysRoleCriteriaInVO,
				new SimplePageRequest(simplePageRequestVO.getPage(), simplePageRequestVO.getSize()));
		List<SysRole> content = page.getContent();
		List<SysRoleGetOutVO> outContent = new ArrayList<SysRoleGetOutVO>();
		for (SysRole sysRole : content) {
			SysRoleGetOutVO sysRoleGetOutVO = Copy.simpleCopy(sysRole, SysRoleGetOutVO.class);
			outContent.add(sysRoleGetOutVO);
		}
		JPageUtil.replaceConent(page, outContent);
		return InvokeResult.success(page);
	}

	/**
	 * 根据ID获取角色
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(path="/getById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getById(String id) throws Exception {
		SysRole sysRole = sysRoleService.getById(id);
		SysRoleGetOutVO sysRoleGetOutVO = Copy.simpleCopy(sysRole, SysRoleGetOutVO.class);
		return InvokeResult.success(sysRoleGetOutVO);
	}

	/**
	 * 编辑角色
	 * 
	 * @param sysRoleModifyInVO
	 * @return
	 */
	@RequestMapping(path="/modify",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult modifyRole(SysRoleModifyInVO sysRoleModifyInVO) {
		SysRole sysRole = sysRoleService.getById(sysRoleModifyInVO.getId());
		Copy.simpleCopyExcludeNull(sysRoleModifyInVO, sysRole);
		sysRoleService.modify(sysRole);
		return InvokeResult.success(true);
	}

	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(path="/delete",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult delete(String ids) throws Exception {
		if (StringUtils.isBlank(ids)) {
			return InvokeResult.bys("为获取角色信息");
		}
		String[] idarry = ids.split(",");
		sysRoleService.delete(idarry);
		return InvokeResult.success(true);
	}

	/**
	 * 角色分配菜单
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/assignMenu",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult assignMenu(SysRoleAssignMenuInVO vo) throws Exception {
		String[] selected = vo.getSelected().split(",");
		String[] undetermined = vo.getUndetermined().split(",");
		sysRoleService.assignRoleMenu(vo.getRoleId(), selected, undetermined);
		return InvokeResult.success(true);
	}

	/**
	 * 获取已授权角色菜单
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(path="/getAssignRoleMenu",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getAssignRoleMenu(String roleId) throws Exception {
		List<SysRoleMenu> list = sysRoleService.getAssignRoleMenu(roleId);
		return InvokeResult.success(list);
	}

	/**
	 * 获取已授权角色资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysRoleURLResources_grant",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getAssignRoleURLResources_grant(String id) {
		List<SysRoleResourcesOutVo> list = sysRoleService.getRoleGrantResources(id);
		return InvokeResult.success(list);
	}

	/**
	 * 获取未授权角色资源
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysRoleURLResources_not_grant",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysRoleURLResources_not_grant(String id) {
		List<SysRoleResourcesOutVo> list = sysRoleService.getRoleNotGrantResources(id);
		return InvokeResult.success(list);
	}

	/**
	 * 删除角色资源
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(path="/deleteRoleResources",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult deleteRoleResources(String ids) throws Exception {
		if (StringUtils.isBlank(ids)) {
			return InvokeResult.bys("未获取角色资源信息");
		}
		String[] arr = ids.split(",");
		sysRoleService.deleteRoleResources(arr);
		return InvokeResult.success(true);
	}

	/**
	 * 添加角色资源
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/addRoleResources",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult addRoleResources(SysRoleResourcesInVO vo) {
		String[] arr = vo.getResourcesId().split(",");
		sysRoleService.saveSysRoleResource(vo.getRoleId(), arr);
		return InvokeResult.success(true);
	}

	/**
	 * 获取已授权角色元素
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysRoleElement_grant",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysRoleElement_grant(String id) {
		List<SysRoleElementOutVo> list = sysRoleService.getRoleGrantElement(id);
		return InvokeResult.success(list);
	}

	/**
	 * 获取未授权角色元素
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysRoleElement_not_grant",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysRoleElement_not_grant(String id) {
		List<SysRoleElementOutVo> list = sysRoleService.getRoleNotGrantElement(id);
		return InvokeResult.success(list);
	}

	/**
	 * 删除角色元素
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(path="/deleteRoleElement",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult deleteRoleElement(String ids) {
		if (StringUtils.isBlank(ids)) {
			return InvokeResult.bys("未获取角色元素信息");
		}
		String[] arr = ids.split(",");
		sysRoleService.deleteRoleElement(arr);
		return InvokeResult.success(true);
	}

	/**
	 * 添加角色元素
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(path="/addRoleElement",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult addRoleElement(SysRoleElementInVO vo) throws Exception {
		String[] arr = vo.getElementId().split(",");
		sysRoleService.saveSysRoleElement(vo.getRoleId(), arr);
		return InvokeResult.success(true);
	}
	
	/**
	 *  getSysRoles:(查询角色  不分页). 
	 *  @return_type:InvokeResult
	 *  @author zhangtian  
	 *  @param sysRoleCriteriaInVO
	 *  @return
	 */
	@RequestMapping(path="/getSysRoles",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysRoles(SysRoleCriteriaInVO sysRoleCriteriaInVO) throws Exception {
		List<SysRole> sysRoles = sysRoleService.getSysRoles(sysRoleCriteriaInVO) ;
		return InvokeResult.success(sysRoles) ;
	}
}
