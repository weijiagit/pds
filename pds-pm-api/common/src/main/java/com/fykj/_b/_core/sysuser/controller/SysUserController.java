/**
 * 
 */
package com.fykj._b._core.sysuser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fykj.AppConfig;
import com.fykj._b._core.sysrole.vo.SysUserRoleOutVO;
import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj._b._core.sysuser.vo.RemoteUserVo;
import com.fykj._b._core.sysuser.vo.SysUserAddInVO;
import com.fykj._b._core.sysuser.vo.SysUserEditInVO;
import com.fykj._b._core.sysuser.vo.SysUserOutVO;
import com.fykj._b._core.sysuser.vo.UpdatePasswordInVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj._b._core.cache.DictionaryCacheHelper;
import com.fykj._b._core.sysuser.UserCodesTable;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.vo.SysUserPageInVO;
import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj._b._core.sysuser.vo.SysUserRoleInVO;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.util.Copy;
import com.fykj.web.model.SimplePageRequestVO;

/**
 * @author wg525
 *
 */
@Controller
@RequestMapping("/sysuser")
public class SysUserController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private DepartmentSerive departmentSerive;
	
	@Autowired
	private AppConfig._Config config;
	
	
	/**
	 * 编辑用户页面
	 */
	@RequestMapping(path="/toUserEdit",method=RequestMethod.GET)
	@ResponseBody
	//TODO 
	public InvokeResult toUserEdit(String id, Model model) {
		SysUser su = sysUserService.getSysUserById(id);
		Map<String, Object> result = new HashMap<String, Object>() ;
		result.put("user", su) ;
		result.put("disableds", DictionaryCacheHelper.getDictData(UserCodesTable.UserState.CODE)) ;
		return InvokeResult.success(result) ;
	}


	/**
	 * 保存用户
	 * 
	 * @param addInVO
	 * @return
	 */
	@RequestMapping(path="/saveSysUser",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveSysUser(SysUserAddInVO addInVO) {
		
		SysUser sysUser=sysUserService.saveSysUser(addInVO);
		return InvokeResult.success(sysUser.getId());
	}
	
	@RequestMapping(path="/saveListUser",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult saveListUser(String userAccounts) {
		String message=sysUserService.saveListUser(userAccounts);
		return InvokeResult.success(message);
	}
	

	/**
	 * 禁用用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(path="/disableSysUser",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult disableSysUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			return InvokeResult.bys("未获取用户信息");
		}
		String[] userIds = userId.split(",");
		sysUserService.disableSysUser(userIds);
		return InvokeResult.success(true);
	}

	/**
	 * 启用用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(path="/enableSysUser",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult enableSysUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			return InvokeResult.bys("未获取用户信息");
		}
		String[] userIds = userId.split(",");
		sysUserService.enableSysUser(userIds);
		return InvokeResult.success(true);
	}

	/**
	 * 用户列表
	 * 
	 * @param vo
	 * @param pageVo
	 * @return
	 */
	@RequestMapping(path="/getSysUserPage",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysUserPage(SysUserPageInVO vo, SimplePageRequestVO pageVo) {
		JPage<SysUserPageOutVO> page = sysUserService.getSysUserPage(vo,
				new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
		return InvokeResult.success(page);
	}

	/**
	 * 逻辑删除用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(path="/removeSysUser",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult removeSysUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			return InvokeResult.bys("未获取用户信息");
		}
		String[] userIds = userId.split(",");
		sysUserService.removeSysUser(userIds);
		return InvokeResult.success(true);
	}

	/**
	 * 重置用户密码
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(path="/resetPassword",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult resetPassword(String ids) {
		if (StringUtils.isBlank(ids)) {
			return InvokeResult.bys("未获取用户信息");
		}
		String[] arr = ids.split(",");
		sysUserService.resetPasswrodByIds(arr);
		return InvokeResult.success(config.getUser().getDefaultPassword());
	}
	
	/**
	 * 修改用户密码
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(path="/updatePassword",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult updatePassword(UpdatePasswordInVo inVO) {
		sysUserService.updatePassword(inVO);
		return InvokeResult.success(true);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysUserById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysUserById(String id) {
		SysUser su = sysUserService.getSysUserById(id);
		return InvokeResult.success(su);
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysUserDepById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysUserDepById(String id) {
		SysUser su = sysUserService.getSysUserById(id);
		SysUserOutVO outVo= Copy.simpleCopy(su, SysUserOutVO.class);
		List<Department> list=departmentSerive.getUserDepartmentName(id);
		for (Department department : list) {
			if(StringUtils.isNoneBlank(outVo.getDepName())){
				outVo.setDepName(outVo.getDepName()+","+department.getName());
			}else{
				outVo.setDepName(department.getName());
			}
		
		}
		return InvokeResult.success(outVo);
	}

	/**
	 * 编辑用户
	 * 
	 * @param InVO
	 * @return
	 */
	@RequestMapping(path="/editSysUser",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult editSysUser(SysUserEditInVO InVO) {
		SysUser sysUser = Copy.simpleCopy(InVO, SysUser.class);
		sysUserService.editSysUser(sysUser);
		return InvokeResult.success(sysUser.getId());
	}

	/**
	 * 查询已有权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysUserRoles_grant",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysUserRoles_grant(String id) {
		List<SysUserRoleOutVO> page = sysUserService.getSysUserGrantRoles(id);
		return InvokeResult.success(page);
	}

	/**
	 * 查询未有权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysUserRoles_notGrant",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysUserRoles_notGrant(String id) {
		List<SysUserRoleOutVO> page = sysUserService.getSysUserNotGrantRoles(id);
		return InvokeResult.success(page);
	}

	/**
	 * 删除用户角色
	 * 
	 * @param userRoleIds
	 * @return
	 */
	@RequestMapping(path="/deleteUserRole",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult deleteUserRole(String userRoleIds) {
		if (StringUtils.isBlank(userRoleIds)) {
			return InvokeResult.bys("未获取用户角色信息");
		}
		String[] arr = userRoleIds.split(",");
		sysUserService.deleteUserRoleByIds(arr);
		return InvokeResult.success(userRoleIds);
	}

	/**
	 * 添加用户角色
	 * 
	 * @param inVO
	 * @return
	 */
	@RequestMapping(path="/addUserRole",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult addUserRole(SysUserRoleInVO inVO) throws Exception {
		String[] arr = inVO.getRoleId().split(",");
		sysUserService.addUserRoles(inVO.getUserId(), arr);
		return InvokeResult.success(true);
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/session",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSessionUser() {
		SysUser su = sysUserService.getSysUserById(ServerSessionHolder.getSessionUser().getId());
		su.setPassword("");
		return InvokeResult.success(su);
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/getSysUserByAccount",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getSysUserByAccount(String account) {
		SysUser su = sysUserService.getSysUserByAccount(account);
		return InvokeResult.success(su);
	}
	
	/**
	 *  getRemoteUser:获取oa用户. 
	 *  @return_type:InvokeResult
	 *  @author fxl  
	 *  @return
	 */
	@RequestMapping(path="/getRemoteUser",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getRemoteUser(RemoteUserVo inVo,SimplePageRequestVO pageVo) {
		JPage<RemoteUserVo> page = sysUserService.getRemoteUser(inVo,new SimplePageRequest(pageVo.getPage(), pageVo.getSize()));
		return InvokeResult.success(page);
	}

	@RequestMapping(path="/getRemoteUserById",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getRemoteUserById(String id) {
		RemoteUserVo user = sysUserService.getRemoteUserById(id);
		Map<String, Object> result = new HashMap<String, Object>() ;
		result.put("user", user) ;
		return InvokeResult.success(result);
	}


}
