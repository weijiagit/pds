/**
 * 
 */
package com.fykj._b._core.sysuser.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.fykj.AppConfig;
import com.fykj._b._core.Constants;
import com.fykj._b._core.sysrole.service.SysRoleService;
import com.fykj._b._core.sysrole.vo.SysUserRoleOutVO;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.model.SysUserRole;
import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj._b._core.sysuser.vo.RemoteUserVo;
import com.fykj._b._core.sysuser.vo.RetrievePassowrd;
import com.fykj._b._core.sysuser.vo.SysUserAddInVO;
import com.fykj._b._core.sysuser.vo.SysUserPageInVO;
import com.fykj._b._core.sysuser.vo.UpdatePasswordInVo;
import com.fykj.kernel._Cfg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj._b._core.sysuser.vo.SysUserPageOutVO;
import com.fykj.kernel.BusinessException;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.JPageUtil;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.kernel._c.security.SecurityService;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.department.model.Department;
import com.fykj.pds.department.service.DepartmentSerive;
import com.fykj.pds.oadb.DBHelper;
import com.fykj.util.Copy;

/**
 * @author zhengzw
 *
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceSupport implements SysUserService {

	private SingleEntityManager<SysUser> internalSysUserServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(SysUser.class);

	private SingleEntityManager<SysUserRole> internalSysUserRoleServiceImpl = SingleEntityManagerGetter.get()
			.getInstance(SysUserRole.class);

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private DepartmentSerive departmentSerive;
	
	@Autowired
	private AppConfig._Config config;

	@Autowired
	private _Cfg _cfg;

	@Override
	public SysUser saveSysUser(SysUserAddInVO addInVO) {
		SysUser sysUser = Copy.simpleCopy(addInVO, SysUser.class);
		String user_account = sysUser.getUserAccount();
		if (exists(user_account)) {
			throw new BusinessException("用户账号 [ " + user_account + " ] 已经存在!");
		}
		sysUser.setPassword(securityService.encriptyByMD5(config.getUser().getDefaultPassword()));
		internalSysUserServiceImpl.saveOnly(sysUser);
		String ids=addInVO.getDepIdOther()+addInVO.getDepId();
		insertDepartment(sysUser.getId(),ids);
		
		return sysUser;
	}
	
	@Override
	public String saveListUser(String userAccounts) {
		String[] ids=userAccounts.split(",");
		String message="";
		for (int i = 0; i < ids.length; i++) {
			String remoteId = ids[i];
			RemoteUserVo vo=getRemoteUserById(remoteId);
			if (exists(vo.getUserAccount())) {
				message=message+"用户账号 [ " + vo.getUserAccount() + " ] 已经存在!";
			}else{
				SysUser sysUser = new SysUser();
				sysUser.setUserAccount(vo.getUserAccount());
				sysUser.setPassword(securityService.encriptyByMD5(config.getUser().getDefaultPassword()));
				sysUser.setName(vo.getName());
				sysUser.setEmail(vo.getEmail());
				sysUser.setDisabled("1");
				internalSysUserServiceImpl.saveOnly(sysUser);
				String depIds=vo.getDepIdOther()+vo.getDepId();
				insertDepartment(sysUser.getId(),depIds);
			}
		}
		return message;
	}
	
	@Override
	public void insertDepartment(String userId, String ids){
		if(StringUtils.isNoneBlank(ids)){
			//获取用户自己部门
			List<Department> totalDepart=new ArrayList<Department>();
			
			List<Department> departs=getRemoteDeparment(ids,false);
			totalDepart.addAll(departs);
			
			List<Department> parentDepart=new ArrayList<Department>();
			getRemoteParentDepartment(departs,parentDepart);
			totalDepart.addAll(parentDepart);
	
			String depId="";
			departmentSerive.delDeptByUserId(userId);
			for (Department department : totalDepart) {
					Department dep=departmentSerive.getDepartmentByRemoteId(department.getRemoteId());
					//存在，更新
					if(dep!=null){
						depId=dep.getId();
						dep.setName(department.getName());
						dep.setRemoteParentId(department.getRemoteParentId());
					}else{
						//不存在新增
						departmentSerive.saveDepartment(department);
						depId=department.getId();
					}
					if(!department.getIsRemoteParent()){
						//重新添加用户，部门关系
						departmentSerive.assignUserDept(userId,depId);
					}
			}
	
		}
	}
	
	//递归获取部门
	private void getRemoteParentDepartment(List<Department> departs,List<Department> result){
		List<Department> parent=new ArrayList<Department>();
		for (Department department : departs) {
			//不是根部门放入list中
			if(!"0".equals(department.getPid())){
				parent.add(department);
			}
		}
		if(parent.size()>0){
			String parentIds="";
			for (int i = 0; i < parent.size(); i++) {
				if(i!=parent.size()-1){
					parentIds=parent.get(i).getRemoteParentId()+",";
				}else{
					parentIds=parent.get(i).getRemoteParentId();
				}
			}
			//获取用户所有部门父部门
			List<Department> list=getRemoteDeparment(parentIds,true);
			result.addAll(list);
			getRemoteParentDepartment(list,result);
		}
		
	}
	
	
	
	private List<Department> getRemoteDeparment(String ids,Boolean isParent){
		List<Department> departments=new ArrayList<Department>();
		String depSql="select d.DEPT_ID,d.DEPT_NAME,d.DEPT_PARENT from department d where d.DEPT_ID in(+"+ids+")";
		DBHelper db1=new DBHelper(depSql, null);
		try {  
        	ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) { 
            	Department department=new Department();
            	department.setRemoteId(ret.getString(1));
            	department.setName(ret.getString(2));  
            	department.setRemoteParentId(ret.getString(3));  
            	department.setIsRemoteParent(isParent);
            	departments.add(department);
            }//显示数据  
            ret.close();  
            db1.close();//关闭连接  
            return departments;
        } catch (SQLException e) {  
            e.printStackTrace();  
            return null;
        }  
	}

	@Override
	public boolean exists(String user_account) {
		SysUser sysUser = internalSysUserServiceImpl.singleEntityQuery2().conditionDefault()
				.equals("userAccount", user_account).ready().model();
		return sysUser != null;
	}

	@Override
	public void disableSysUser(String[] userIds) {
		SysUser sysUser = null;
		for (String userId : userIds) {
			if (StringUtils.isNotBlank(userId)) {
				sysUser = internalSysUserServiceImpl.getById(userId);
				sysUser.setDisabled(Constants.isEnable.disable);
				internalSysUserServiceImpl.updateOnly(sysUser);
			}
		}
	}

	@Override
	public void enableSysUser(String[] userIds) {
		SysUser sysUser = null;
		for (String userId : userIds) {
			if (StringUtils.isNotBlank(userId)) {
				sysUser = internalSysUserServiceImpl.getById(userId);
				sysUser.setDisabled(Constants.isEnable.enable);
				internalSysUserServiceImpl.updateOnly(sysUser);
			}
		}
	}

	@Override
	public JPage<SysUserPageOutVO> getSysUserPage(SysUserPageInVO inVO, SimplePageRequest page) {
		StringBuilder jpql = new StringBuilder("SELECT");
		jpql.append(
				" t.id as id,t.USER_ACCOUNT as userAccount,t.NAME as name,t.DESCRIPTION as description,t.DISABLED as disabled");
		jpql.append(" FROM T_SYS_USER t");
		jpql.append(" WHERE t.is_available = :isAvailable");
		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		if (StringUtils.isNotBlank(inVO.getUserAccount())) {
			jpql.append(" AND t.USER_ACCOUNT like :userAccount");
			params.put("userAccount", "%"+inVO.getUserAccount()+"%");
		}
		if (StringUtils.isNotBlank(inVO.getName())) {
			jpql.append(" AND t.name like :name");
			params.put("name", "%" + inVO.getName() + "%");
		}
		if (StringUtils.isNotBlank(inVO.getDescription())) {
			jpql.append(" AND t.description like :description");
			params.put("description", "%" + inVO.getDescription() + "%");
		}
		if (StringUtils.isNotBlank(inVO.getDisabled())) {
			jpql.append(" AND t.disabled = :disabled");
			params.put("disabled", inVO.getDisabled());
		}
		jpql.append(" order by t.USER_ACCOUNT");
		return nativeQuery().setSql(jpql.toString()).setParams(params).modelPage(page, SysUserPageOutVO.class);
	}

	@Override
	public SysUser getSysUserByAccount(String user_account) {
		return internalSysUserServiceImpl.singleEntityQuery2().conditionDefault().equals("userAccount", user_account)
				.ready().model();
	}

	@Override
	public SysUser getSysUserByOpenId(String openId) {
		return internalSysUserServiceImpl.singleEntityQuery2().conditionDefault().equals("openId", openId)
				.ready().model();
	}

	@Override
	public void removeSysUser(String[] userIds) {
		SysUser sysUser = null;
		for (String userId : userIds) {
			if (StringUtils.isNotBlank(userId)) {
				sysUser = internalSysUserServiceImpl.getById(userId);
				internalSysUserServiceImpl.delete(sysUser);
			}
		}
	}

	@Override
	public SysUser getSysUserById(String id) {
		return internalSysUserServiceImpl.getById(id, SysUser.class);
	}

	public void editSysUser(SysUser su) {
		SysUser sysUser = getSysUserById(su.getId());
		sysUser.setName(su.getName());
		sysUser.setEmail(su.getEmail());
		sysUser.setDisabled(su.getDisabled());
		sysUser.setDescription(su.getDescription());
		internalSysUserServiceImpl.updateOnly(sysUser);
	}

	@Override
	public List<SysUserRoleOutVO> getSysUserGrantRoles(String id) {
		List<SysUserRoleOutVO> list = sysRoleService.getUserRoles(id);
		return list;
	}

	@Override
	public List<SysUserRoleOutVO> getSysUserNotGrantRoles(String id) {
		return sysRoleService.getUserNotRoles(id);

	}

	@Override
	public void deleteUserRoleById(String userRoleId) {
		if (StringUtils.isNoneBlank(userRoleId)) {
			SysUserRole model = internalSysUserRoleServiceImpl.getById(userRoleId);
			internalSysUserRoleServiceImpl.delete(model);
		}
	}

	public void deleteUserRoleByIds(String[] userRoleIds) {
		for (String userRoleId : userRoleIds) {
			deleteUserRoleById(userRoleId);
		}
	}

	@Override
	public void addUserRole(String userId, String roleId) {
		SysUserRole model = new SysUserRole();
		model.setUserId(userId);
		model.setRoleId(roleId);
		internalSysUserRoleServiceImpl.saveOnly(model);
	}

	public void addUserRoles(String userId, String[] roleIds) {
		for (String roleId : roleIds) {
			addUserRole(userId, roleId);
		}
	}

	public void resetPasswordById(String userId) {
		SysUser sysUser = internalSysUserServiceImpl.getById(userId);
		sysUser.setPassword(securityService.encriptyByMD5(config.getUser().getDefaultPassword()));
		internalSysUserServiceImpl.updateOnly(sysUser);
	}

	public void resetPasswrodByIds(String[] userIds) {
		for (String userId : userIds) {
			resetPasswordById(userId);
		}
	}

	@Override
	public void updatePassword(UpdatePasswordInVo invo) {
		String userId=ServerSessionHolder.getSessionUser().getId();
		SysUser sysUser = internalSysUserServiceImpl.getById(userId);
		if (securityService.encriptyByMD5(invo.getOldPassword()).equals(sysUser.getPassword())) {
			sysUser.setPassword(securityService.encriptyByMD5(invo.getNewPassword()));
		}else{
			throw new BusinessException("原密码错误!");
		}
	}

	@Override
	public void retrievePassword(String userAccount) throws Exception {
//		SysUser user=this.getSysUserByAccount(userAccount);
//		if(user!=null){
//			if(StringUtils.isNoneBlank(user.getEmail())){
//				String key=userAccount+";"+new Date().getTime();
//				String sendMessage="<a href='"+mail.getMessage()+"?account="+securityService.encrypt(key.getBytes())+"' >修改密码</a>";
//				mail.setReceiver(user.getEmail());
//				mail.setSendMessage(sendMessage);
//				MailUtil util=new MailUtil();
//				util.send(mail);
//			}else{
//				throw new BusinessException("该用户没有有效邮箱!");
//			}
//		}else{
//			throw new BusinessException("用户不存在!");
//		}
		throw new RuntimeException("uncompletely");
	}

	@Override
	public void updatePasswordByAccount(RetrievePassowrd vo) throws Exception {
//		String  account=vo.getUserAccount();
//		String dec=securityService.decrypt(account.getBytes());
//		String[] key=dec.split(";");
//		if(key.length!=2){
//			throw new BusinessException("密钥无效!");
//		}
//		long keyDate=Long.parseLong(key[1]);
//		if(DateTime.now().minusHours(mail.getTimeOut()).isBefore(new DateTime().withMillis(keyDate))){
//			SysUser user=this.getSysUserByAccount(key[0]);
//			if(user!=null){
//				user.setPassword(securityService.encriptyByMD5(vo.getNewPassword()));
//			}else{
//				throw new BusinessException("密钥无效!");
//			}
//		}else{
//			throw new BusinessException("密钥超时!");
//		}
		throw new RuntimeException("uncompletely");
	}

	@Override
	public int getSysUserNum() {
		
		String sql = "SELECT count(*) FROM t_sys_user WHERE  is_available = 1";
		Long num = nativeQuery().setSql(sql).model();
		return	Integer.parseInt(num.toString());
	
	}

	@Override
	public JPage<RemoteUserVo> getRemoteUser(RemoteUserVo inVo,SimplePageRequest page) {
		List<RemoteUserVo> list=new ArrayList<RemoteUserVo>();
		 List<Object> params =new ArrayList<Object>();
		 String natralSql=" from user u INNER JOIN department d on u.DEPT_ID=d.DEPT_ID";
		 if(StringUtils.isNoneBlank(inVo.getName())){
			 natralSql=natralSql+" and u.USER_NAME like ?";
			 params.add("%"+inVo.getName()+"%");
		 }
		 if(StringUtils.isNoneBlank(inVo.getUserAccount())){
			 natralSql=natralSql+" and u.BYNAME like ?";
			 params.add("%"+inVo.getUserAccount()+"%");
		 }
		 natralSql=natralSql+" order by u.BYNAME";
		 String sql = "select u.BYNAME,u.USER_NAME,u.EMAIL,u.DEPT_ID,d.DEPT_NAME,u.UID "+natralSql+" limit ?,? ";//SQL语句  

		 String totalSql="select count(*) "+natralSql;
		 DBHelper  db2 = new DBHelper(totalSql,params);//创建DBHelper对象  
	     Integer total=0;
	     try {  
        	ResultSet ret = db2.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) { 
            	total=ret.getInt(1);
            }//显示数据  
            ret.close();  
            db2.close();//关闭连接  
         } catch (SQLException e) {  
            e.printStackTrace();  
         }
		 
		 int start=page.getPageNumber()*page.getPageSize();
		 int end=page.getPageSize();
		 params.add(start);
		 params.add(end);
	     DBHelper  db1 = new DBHelper(sql,params);//创建DBHelper对象  
         try {  
        	ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) { 
            	RemoteUserVo vo=new RemoteUserVo();
            	vo.setUserAccount(ret.getString(1));  
            	vo.setName(ret.getString(2));  
            	vo.setEmail(ret.getString(3));  
            	vo.setDepId(ret.getString(4));  
            	vo.setDepName(ret.getString(5));  
            	vo.setId(ret.getString(6));
            	list.add(vo);
            }//显示数据  
            ret.close();  
            db1.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
	    JPage<RemoteUserVo> pageResult =JPageUtil.wrap(list,Long.valueOf(String.valueOf(total)),page);
		return pageResult;
	}

	@Override
	public RemoteUserVo getRemoteUserById(String id) {
		String sql = "select u.BYNAME,u.USER_NAME,u.EMAIL,u.DEPT_ID,d.DEPT_NAME,u.UID,u.DEPT_ID_OTHER from user u INNER JOIN department d on u.DEPT_ID=d.DEPT_ID where u.UID=?";//SQL语句  
		List<Object> params =new ArrayList<Object>();
		params.add(id);
		DBHelper  db1 = new DBHelper(sql,params);//创建DBHelper对象  
	    RemoteUserVo vo=new RemoteUserVo();
        try {  
        	ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) { 
            	vo.setUserAccount(ret.getString(1));  
            	vo.setName(ret.getString(2));  
            	vo.setEmail(ret.getString(3));  
            	vo.setDepId(ret.getString(4));  
            	vo.setDepName(ret.getString(5));  
            	vo.setId(ret.getString(6));
            	vo.setDepIdOther(ret.getString(7));
            }//显示数据  
            ret.close();  
            db1.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
		return vo;
	}
	
	@Override
	public RemoteUserVo getRemoteUserAccount(String account){
		String sql = "select u.BYNAME,u.USER_NAME,u.EMAIL,u.DEPT_ID,d.DEPT_NAME,u.UID,u.DEPT_ID_OTHER from user u INNER JOIN department d on u.DEPT_ID=d.DEPT_ID where u.BYNAME=?";//SQL语句  
		List<Object> params =new ArrayList<Object>();
		params.add(account);
		DBHelper  db1 = new DBHelper(sql,params);//创建DBHelper对象  
	    RemoteUserVo vo=new RemoteUserVo();
        try {  
        	ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集  
            while (ret.next()) { 
            	vo.setUserAccount(ret.getString(1));  
            	vo.setName(ret.getString(2));  
            	vo.setEmail(ret.getString(3));  
            	vo.setDepId(ret.getString(4));  
            	vo.setDepName(ret.getString(5));  
            	vo.setId(ret.getString(6));
            	vo.setDepIdOther(ret.getString(7));
            }//显示数据  
            ret.close();  
            db1.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
		return vo;
	}

	@Override
	public List<SysUserPageOutVO> getResponsibleLeader(String deptId) {
		StringBuilder sql = new StringBuilder("select ");
		sql.append(
				" u.id as id, u.name as name");
		sql.append("  from t_sys_user u ");
		sql.append("  LEFT JOIN t_sys_user_department ud ON u.id = ud.user_id ");
		sql.append("  LEFT JOIN t_sys_user_role ur ON ud.user_id = ur.user_id ");
		sql.append(" where ud.department_id =:departmentId and ud.is_available = :isAvailable  ");
		sql.append(" and ur.role_id =:roleId and u.is_available=:isAvailable and ur.is_available=:isAvailable");

		Map<String, Object> params = new WeakHashMap<String, Object>();
		params.put("isAvailable", Availability.available.ordinal());
		params.put("departmentId", deptId);
		String roleId=_cfg.getDepartment().getRoleId();
		params.put("roleId", roleId);
		return nativeQuery().setSql(sql.toString()).setParams(params).models(SysUserPageOutVO.class);
	}

}
