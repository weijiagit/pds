/**
 * 
 */
package com.fykj._b._core.login.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fykj._b._core.APP;
import com.fykj._b._core.auth.impl.JWTClaims;
import com.fykj._b._core.cache.UserCache;
import com.fykj._b._core.element.service.PageElementService;
import com.fykj._b._core.element.vo.SysUserElementOutVO;
import com.fykj._b._core.kaptcha.KaptchaOn;
import com.fykj._b._core.login.vo.LoginInVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj._b._core.login.service.LoginService;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.vo.UserCacheVo;
import com.fykj.kernel.JWTService;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel.aop.NoAuthentication;
import com.fykj.kernel.aop.NoAuthorization;
import com.fykj.util.Copy;

/**
 * @author zhengzw
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private JWTClaims jwtClaims;
	
	@Autowired
	private UserCache userCache;

	@Autowired
	private PageElementService pageElementService;

	@RequestMapping(path="/userLogin",method=RequestMethod.POST)
	@ResponseBody
	@NoAuthentication
	@NoAuthorization
	@KaptchaOn
	public InvokeResult userLogin(LoginInVO inVO, HttpServletRequest request) throws Exception{
		SysUser sysUser = Copy.simpleCopy(inVO, SysUser.class);
//		String captcha = inVO.getCaptcha();
//		
//		if(!StringUtils.equals(request.getSession().getAttribute("validate_captcha_code"), captcha)){
//			throw new BusinessException("验证码错误!");
//		}
		SessionUser sessionUser=loginService.userLogin(sysUser);
		if(sessionUser!=null){
			Map<String, Object> claims=jwtClaims.claims(sessionUser, null);
			String token=jwtService.generateToken(claims);
			UserCacheVo cacheVo=new UserCacheVo();
			cacheVo.setUserId(sessionUser.getId());
			cacheVo.setUserName(sessionUser.getUserName());
			cacheVo.setName(sessionUser.getNatureName());

			userCache.load(token, cacheVo);
			return InvokeResult.success(token);
		}else{
			return InvokeResult.bys("Invalid User");
		}
	}

	@RequestMapping(path="/appLogin",method=RequestMethod.POST)
	@APP
	@ResponseBody
	public InvokeResult appLogin(LoginInVO inVO, HttpServletRequest request) throws Exception{
		SysUser sysUser = Copy.simpleCopy(inVO, SysUser.class);
		
		SessionUser sessionUser=loginService.userLogin(sysUser);
		if(sessionUser!=null){
			Map<String, Object> claims=jwtClaims.claims(sessionUser, null);
			String token=jwtService.generateToken(claims);
			
			return InvokeResult.success(token);
		}else{
			return InvokeResult.bys("Invalid User");
		}
	}
	
	@NoAuthentication
	@NoAuthorization
	@RequestMapping(path="/loginout",method=RequestMethod.POST)
	@ResponseBody
	public InvokeResult loginout() throws Exception{
		return InvokeResult.success(true);
	}
	
	/**
	 * 获取登录信息
	 * 用户名密码等
	 * getParty:
	 * <pre>
	 *
	 * </pre>
	 * @return
	 * @author 张军
	 */
	@RequestMapping(path="/getsessionUser",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getsessionUser(){
		SessionUser sessionUser = ServerSessionHolder.getSessionUser();
		return 	InvokeResult.success(sessionUser);
	}

	/**
	 * 获取当前登录用户的操作权限
	 * @return
	 */
	@RequestMapping(path="/getRoleList",method=RequestMethod.GET)
	@ResponseBody
	public InvokeResult getRoleList(){
		List<String> roleList =new ArrayList<>();
		SessionUser sessionUser = ServerSessionHolder.getSessionUser();
		List<SysUserElementOutVO> elementList = pageElementService.getElementByUser(sessionUser.getId());
		for( SysUserElementOutVO out : elementList){
			roleList.add(out.getFuncId());
		}
		return 	InvokeResult.success(roleList);
	}

}
