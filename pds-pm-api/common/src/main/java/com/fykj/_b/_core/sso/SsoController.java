package com.fykj._b._core.sso;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fykj._b._core.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fykj._b._core.cache.CacheVoRepository;
import com.fykj._b._core.login.service.LoginService;
import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj._b._core.sysuser.vo.RemoteUserVo;
import com.fykj._b._core.sysuser.vo.UserCacheVo;
import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel.redis.SSOService;
import com.fykj.pds.interfaceManage.InterfaceManager;
import com.fykj.pds.interfaceManage.vo.SsoReturnVo;
import com.fykj.pds.log.model.LoginMessage;
import com.fykj.pds.log.service.LoginMessageService;
import com.fykj.pds.oadb.DBHelper;

@RestController
@RequestMapping("/sso")
public class SsoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private LoginMessageService loginMessageService;
	
	@Autowired
	private SSOService sSOService;
	
	@Autowired
	private InterfaceManager interfaceManager;
	
	@Autowired
	private _Cfg cfg;
	
	@RequestMapping(path="/demo" ,method =RequestMethod.GET)
	public InvokeResult SaveDeptYwInf(String userName){
		return  InvokeResult.success(userName);
	}
	
	
	@RequestMapping(path="/ssoLogin",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult ssoLogin(String token) throws Exception{
		if(StringUtils.isBlank(token)){
			return InvokeResult.LYS("DL001", cfg.getSso().getHost()+cfg.getSso().getLoginUrl());
		}
		SsoReturnVo ssoUser=interfaceManager.validateToken(token);
		if(!ssoUser.getFlag()){
			return InvokeResult.LYS("DL001", cfg.getSso().getHost()+cfg.getSso().getLoginUrl());
		}
		SessionUser sessionUser = loginService.userLogin(ssoUser.getUserName());
		RemoteUserVo remoteVo=sysUserService.getRemoteUserAccount(ssoUser.getUserName());
		String ids=remoteVo.getDepIdOther()+remoteVo.getDepId();
		if(sessionUser!=null){
			//验证错误返回
			if(StringUtils.isNoneBlank(sessionUser.getErrorCode())){
				return InvokeResult.LYS(sessionUser.getErrorCode(), sessionUser.getErrorMessage());
			}
			//记录登陆日志
			LoginMessage loginMessage= loginMessageService.saveLoginMessage(Constants.logType.LOGIN_TYPE,sessionUser,"","");
			sessionUser.setLoginMessageId(loginMessage.getId());
			//生成token
			String _token=sSOService.generateToken(sessionUser);
			
			//登陆重新拉取部门
			sysUserService.insertDepartment(sessionUser.getId(),ids);
			
			return InvokeResult.success(_token);
		}else{
			return InvokeResult.LYS("DL002", "无效用户");
		}
	}
	
	
	@RequestMapping(path="/ssoLogOut",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult ssoLogOut(HttpServletRequest source,String token) throws Exception{
//		if(StringUtils.isBlank(userName)){
//			userName=ServerSessionHolder.getSessionUser().getUserName();
//		}
//		sSOService.redisLogOut(userName);
//		String loginMessageId=ServerSessionHolder.getSessionUser().getLoginMessageId();
//		if(StringUtils.isNoneBlank(userName)){
//			LoginMessage message=loginMessageService.getLoginMessage(loginMessageId);
//			message.setSignOutTime(new Date());
//			loginMessageService.updateLoginMessage(message);
//		}
//		return InvokeResult.LYS("DL005", cfg.getSso().getHost()+cfg.getSso().getLoginOutUrl());
		if(StringUtils.isBlank(token)){
			String tokenKey=cfg.getRedis().getHeader();
			token=source.getHeader(tokenKey);
		}
		if(StringUtils.isNoneBlank(token)){
			String loginMessageId=ServerSessionHolder.getSessionUser().getLoginMessageId();
			LoginMessage message=loginMessageService.getLoginMessage(loginMessageId);
			message.setSignOutTime(new Date());
			//登录日志类型
			message.setLogType(Constants.logType.LOGIN_TYPE);
			loginMessageService.updateLoginMessage(message);
		}
		sSOService.redisLogOut(token);
		return InvokeResult.LYS("DL005", cfg.getSso().getHost()+cfg.getSso().getLoginOutUrl());
	}


}

