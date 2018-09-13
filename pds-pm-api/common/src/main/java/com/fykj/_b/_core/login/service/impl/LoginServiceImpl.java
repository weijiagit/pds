/**
 *
 */
package com.fykj._b._core.login.service.impl;

import com.fykj._b._core.login.service.LoginService;
import com.fykj._b._core.sysuser.UserCodesTable;
import com.fykj._b._core.sysuser.model.SysUser;
import com.fykj._b._core.sysuser.service.SysUserService;
import com.fykj.kernel.BusinessException;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel._c.security.SecurityService;
import com.fykj.kernel._c.service.ServiceSupport;
import com.fykj.pds.jersey.util.PageUtil;
import com.fykj.pds.jersey.util.WebChartConstants;
import com.fykj.pds.jersey.util.WebChartUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhengzw
 */
@Service
@Transactional
public class LoginServiceImpl extends ServiceSupport implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SecurityService securityService;

    @Override
    public SessionUser userLogin(SysUser sysUser) {
        String user_account = sysUser.getUserAccount();
        String password = sysUser.getPassword();

        SysUser dbUser = sysUserService.getSysUserByAccount(user_account);

        if (null == dbUser) {
            throw new BusinessException("用户账号或者密码错误!");
        }

        // 密码正确
        if (securityService.encriptyByMD5(password).equals(dbUser.getPassword())) {
            if (UserCodesTable.UserState.DISABLE.equals(dbUser.getDisabled())) {
                throw new BusinessException("用户账号已禁用,请联系系统管理员!");
            }
            String userId = dbUser.getId();
            // 用户信息
            SessionUser user = new SessionUser();
            user.setId(userId);
            user.setUserName(dbUser.getUserAccount());
            user.setNatureName(dbUser.getName());
            return user;

        } else {
            throw new BusinessException("用户账号或者密码错误!");
        }
    }

    @Override
    public SessionUser userLogin(String userName) {
        SessionUser user = new SessionUser();
        SysUser dbUser = sysUserService.getSysUserByAccount(userName);
        if (null == dbUser) {
            user.setErrorCode("DL003");
            user.setErrorMessage("用户账号未同步，请联系管理员!");
            return user;
        }
        // 密码正确
        if (UserCodesTable.UserState.DISABLE.equals(dbUser.getDisabled())) {
            user.setErrorCode("DL004");
            user.setErrorMessage("用户账号已禁用,请联系系统管理员!");
            return user;
        }
        String userId = dbUser.getId();
        // 用户信息
        user.setId(userId);
        user.setUserName(dbUser.getUserAccount());
        user.setNatureName(dbUser.getName());
        return user;
    }

    @Override
    public WebChartUtil publicUserLogin(PageUtil pageUtil) {

        WebChartUtil webChartUser = new WebChartUtil();

        if (StringUtils.isNotBlank(pageUtil.getOpenId())) {
            SysUser sysUserOpenId = sysUserService.getSysUserByOpenId(pageUtil.getOpenId());
            if (null == sysUserOpenId) {
                webChartUser.setCode(WebChartConstants.ERROR_CODE);
                webChartUser.setMsg(WebChartConstants.USER_ACCOUNT_BIND_WEBCHART);
                return webChartUser;
            }
        }

        String user_account = pageUtil.getUserAccount();
        String password = pageUtil.getPassword();

        SysUser dbUser = sysUserService.getSysUserByAccount(user_account);

        if (null == dbUser) {
            webChartUser.setCode(WebChartConstants.ERROR_CODE);
            webChartUser.setMsg(WebChartConstants.USER_PWD_ERROR_MSG);
            return webChartUser;
        }
        if (checkPassword(webChartUser, password, dbUser)) return webChartUser;


        String userId = dbUser.getId();
        // 用户信息
        webChartUser.setId(userId);
        webChartUser.setUserName(dbUser.getUserAccount());
        webChartUser.setNatureName(dbUser.getName());
        webChartUser.setOpenId(dbUser.getOpenId());
        webChartUser.setCode(WebChartConstants.SUCCESS_CODE);
        return webChartUser;
    }

    private boolean checkPassword(WebChartUtil webChartUser, String password, SysUser dbUser) {
        // 密码正确
        if (securityService.encriptyByMD5(password).equals(dbUser.getPassword())) {
            if (UserCodesTable.UserState.DISABLE.equals(dbUser.getDisabled())) {
                webChartUser.setCode(WebChartConstants.ERROR_CODE);
                webChartUser.setMsg(WebChartConstants.USER_ACCOUNT_DISABLED);
                return true;
            }
        } else {
            webChartUser.setCode(WebChartConstants.ERROR_CODE);
            webChartUser.setMsg(WebChartConstants.USER_PWD_ERROR_MSG);
            return true;
        }
        return false;
    }

    @Override
    public WebChartUtil checkUserByCode(String userAccount, String password) {

        WebChartUtil webChartUser = new WebChartUtil();

        SysUser dbUser = sysUserService.getSysUserByAccount(userAccount);

        if (null == dbUser) {
            webChartUser.setCode(WebChartConstants.ERROR_CODE);
            webChartUser.setMsg(WebChartConstants.USER_PWD_ERROR_MSG);
            return webChartUser;
        }

        // 密码正确
        if (checkPassword(webChartUser, password, dbUser)) return webChartUser;
        webChartUser.setCode(WebChartConstants.SUCCESS_CODE);
        return webChartUser;
    }

}
