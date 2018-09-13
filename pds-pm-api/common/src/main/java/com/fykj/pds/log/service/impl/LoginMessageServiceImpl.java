package com.fykj.pds.log.service.impl;



import com.fykj._b._core.Constants;
import com.fykj.kernel._c.model.Availability;
import com.fykj.kernel._c.model.JPage;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.model.SimplePageRequest;
import com.fykj.pds.log.vo.LoginMessagePageInVO;
import com.fykj.pds.log.vo.LoginMessagePageOutVO;
import com.fykj.util.Copy;
import com.fykj.util.JDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.kernel._c.service.SingleEntityManager;
import com.fykj.kernel._c.service.SingleEntityManagerGetter;
import com.fykj.pds.log.model.LoginMessage;
import com.fykj.pds.log.service.LoginMessageService;

import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

@Service
@Transactional
public class LoginMessageServiceImpl extends ServiceSupport implements LoginMessageService {

	 private SingleEntityManager<LoginMessage> internalLoginMessageServiceImpl = SingleEntityManagerGetter.get()
	            .getInstance(LoginMessage.class);
	
	@Override
	public LoginMessage saveLoginMessage(String logType,SessionUser sessionUser,String projectObject,String projectModule) {

		LoginMessage loginMessage=new LoginMessage();
		//登录日志
		if(logType.equals(Constants.logType.LOGIN_TYPE)){
			loginMessage.setLoginName(sessionUser.getNatureName());
			loginMessage.setLoginAccount(sessionUser.getUserName());
			loginMessage.setSignInTime(new Date());
			loginMessage.setLogType(logType);
		}else{
		//删除日志
			loginMessage.setLoginName(sessionUser.getNatureName());
			loginMessage.setLoginAccount(sessionUser.getUserName());
			loginMessage.setOperateTime(new Date());
			loginMessage.setProjectObject(projectObject);
			loginMessage.setProjectModule(projectModule);
			loginMessage.setLogType(logType);
		}
		internalLoginMessageServiceImpl.saveOnly(loginMessage);
		return loginMessage;
	}
	
	@Override
	public void updateLoginMessage(LoginMessage loginMessage) {
		internalLoginMessageServiceImpl.updateOnly(loginMessage);
	}

	@Override
	public LoginMessage getLoginMessage(String id) {
		return internalLoginMessageServiceImpl.getById(id, LoginMessage.class);
	}

	/**
	 * 登录log日志查询
	 * @param vo
	 * @param page
	 * @return
	 */
	@Override
	public JPage<LoginMessagePageOutVO> getLoginMessagePage(LoginMessagePageInVO vo, SimplePageRequest page) {
		StringBuilder sql = new StringBuilder("select ");
		sql.append(
				" t.id as id, t.login_account as loginAccount,t.login_name as loginName ," +
						"DATE_FORMAT(t.sign_in_time,'%Y-%m-%d %H:%i:%S') as signInTimeStr, " +
						"DATE_FORMAT(t.sign_out_time,'%Y-%m-%d %H:%i:%S') as signOutTimeStr, " +
						"DATE_FORMAT(t.operate_time,'%Y-%m-%d %H:%i:%S') as operateTimeStr, " +
						"t.log_type as logType,t.project_object as projectObject "
		);
		sql.append("  from t_login_message t ");
		sql.append(" where t.is_available = :isAvailable ");

		Map<String, Object> params = new WeakHashMap<String, Object>();

		if (StringUtils.isNotBlank(vo.getLoginAccount())) {
			sql.append(" and t.login_account like :loginAccount ");
			params.put("loginAccount", "%" + vo.getLoginAccount() + "%");
		}

		if (StringUtils.isNotBlank(vo.getProjectObject())) {
			sql.append(" and t.project_object like :projectObject ");
			params.put("projectObject", "%" + vo.getProjectObject() + "%");
		}

		if (StringUtils.isNotBlank(vo.getLogType())) {
			sql.append(" and t.log_type = :logType ");
			params.put("logType",vo.getLogType());
		}

		sql.append(" order by  t.create_date desc");


		params.put("isAvailable", Availability.available.ordinal());

		return nativeQuery().setSql(sql.toString()).setParams(params).modelPage(page, LoginMessagePageOutVO.class);
	}

	/**
	 * 部门日志详情
	 *
	 * @param id
	 * @return
	 */
	@Override
	public LoginMessagePageOutVO getLoginMessageById(String id) {
		LoginMessage loginMessage = internalLoginMessageServiceImpl.getById(id);
		LoginMessagePageOutVO vo = new LoginMessagePageOutVO();
		Copy.simpleCopyExcludeNull(loginMessage, vo);

		//时间转换
		if (null == loginMessage.getSignInTime()) {
			vo.setSignInTimeStr("");
		} else {
			vo.setSignInTimeStr(JDateUtils.dateTimeFormat(loginMessage.getSignInTime()));
		}

		if (null == loginMessage.getSignOutTime()) {
			vo.setSignOutTimeStr("");
		} else {
			vo.setSignOutTimeStr(JDateUtils.dateTimeFormat(loginMessage.getSignOutTime()));
		}

		if (null == loginMessage.getOperateTime()) {
			vo.setOperateTimeStr("");
		} else {
			vo.setOperateTimeStr(JDateUtils.dateTimeFormat(loginMessage.getOperateTime()));
		}
		return vo;
	}


}
