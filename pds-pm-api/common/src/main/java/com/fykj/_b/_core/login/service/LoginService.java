/**
 * 
 */
package com.fykj._b._core.login.service;

import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.pds.jersey.util.PageUtil;
import com.fykj.pds.jersey.util.WebChartUtil;

/**
 * @author zhengzw
 *
 */
public interface LoginService {

	/**
	 * 用户登录
	 * 
	 * @param context
	 * @param sysUser
	 * @return
	 */
	public SessionUser userLogin(SysUser sysUser);
	
	/**
	 * Sso / Cas 等第三方用户登录
	 *
	 * @param userName 登录名
	 * @return
	 * @author zhangj
	 */
	public SessionUser userLogin(String userName);

	/**
	 *  公众号用户登录
	 * @param pageUtil
	 * @return
	 */
	public WebChartUtil publicUserLogin(PageUtil pageUtil);

	/**
	 * 验证用户名和密码
	 * @param userAccount
	 * @param password
	 * @return
	 */
	public WebChartUtil checkUserByCode(String userAccount, String password);

}
