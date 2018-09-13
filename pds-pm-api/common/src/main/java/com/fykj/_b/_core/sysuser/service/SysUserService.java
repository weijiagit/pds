/**
 * 
 */
package com.fykj._b._core.sysuser.service;

import java.util.List;

import com.fykj._b._core.sysrole.vo.SysUserRoleOutVO;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.vo.RemoteUserVo;
import com.fykj._b._core.sysuser.vo.RetrievePassowrd;
import com.fykj._b._core.sysuser.vo.SysUserAddInVO;
import com.fykj._b._core.sysuser.vo.SysUserPageInVO;
import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj._b._core.sysuser.vo.UpdatePasswordInVo;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.web.model.SimplePageRequestVO;

/**
 * @author zhengzw
 *
 */
public interface SysUserService {

	/**
	 * 新增系统用户
	 * 
	 * @param serviceContext
	 * @param sysUser
	 */
	public SysUser saveSysUser(SysUserAddInVO sysUser);
	
	
	/**
	 *  saveListUser:(批量新增用户). 
	 *  @return_type:String
	 *  @author fxl  
	 *  @param userAccounts
	 *  @return
	 */
	public String saveListUser(String userAccounts);
	

	/**
	 * 根据用户账号判断账号唯一性
	 * 
	 * @param serviceContext
	 * @param user_account
	 * @return
	 */
	public boolean exists(String user_account);

	/**
	 * 根据用户id禁用用户
	 * 
	 * @author Lx
	 * @param context
	 * @param ids
	 */
	public void disableSysUser(String[] userIds);

	/**
	 * 得到用户管理页面分页数据
	 * 
	 * @author Lx
	 * @param context
	 * @param user_account
	 *            用户账号（精确查找）
	 * @param name
	 *            用户名称（左右模糊匹配）
	 * @param description
	 *            用户简介（左右模糊匹配）
	 * @param disabled
	 *            是否启用（精确查找）
	 * @param page
	 *            分页页数
	 * @param size
	 *            每页数量
	 * @return
	 */
	public JPage<SysUserPageOutVO> getSysUserPage(SysUserPageInVO inVO, SimplePageRequest page);

	/**
	 * 根据用户账号获取用户信息
	 * 
	 * @param serviceContext
	 * @param user_account
	 * @return
	 */
	public SysUser getSysUserByAccount(String user_account);

	/**
	 * 根据用户的openId获取用户信息
	 * @param openId
	 * @return
	 */
	public SysUser getSysUserByOpenId(String openId);

	/**
	 * 根据用户Id启用用户
	 * 
	 * @author Lx
	 * @param context
	 * @param userIds
	 */
	public void enableSysUser(String[] userIds);

	/**
	 * 根据ID 逻辑删除用户 ， is_avaliable = 0
	 * 
	 * @author Lx
	 * @param context
	 * @param userIds
	 */
	public void removeSysUser(String[] userIds);

	/**
	 * 根据用户主键获取用户信息
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public SysUser getSysUserById(String id);

	/**
	 * 修改用户信息
	 * 
	 * @param context
	 * @param su
	 */
	public void editSysUser(SysUser su);

	/**
	 * 查询当前用户已有角色信息
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public List<SysUserRoleOutVO> getSysUserGrantRoles(String id);

	/**
	 * 查询当前用户未有角色信息
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public List<SysUserRoleOutVO> getSysUserNotGrantRoles(String id);

	public void deleteUserRoleById(String userRoleId);

	public void deleteUserRoleByIds(String[] userRoleIds);

	public void addUserRole(String userId, String roleId);

	public void addUserRoles(String userId, String[] roleIds);

	public void resetPasswordById(String userId);

	public void resetPasswrodByIds(String[] userId);
	
	public void updatePassword(UpdatePasswordInVo invo);
	
	/**
	 * 找回密码
	 * @param userAccount
	 */
	public void retrievePassword(String userAccount) throws Exception ;
	
	/**
	 * 根据账号修改密码
	 * @param vo
	 */
	public void  updatePasswordByAccount(RetrievePassowrd vo) throws Exception ;
	
	public int getSysUserNum();
	
	public JPage<RemoteUserVo> getRemoteUser(RemoteUserVo inVO,SimplePageRequest page);
	
	public RemoteUserVo getRemoteUserById(String id);
	
	public RemoteUserVo getRemoteUserAccount(String account);

	public List<SysUserPageOutVO> getResponsibleLeader(String deptId);
	
	public void insertDepartment(String userId, String ids);
}
