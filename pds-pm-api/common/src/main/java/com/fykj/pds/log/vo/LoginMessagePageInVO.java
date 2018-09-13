package com.fykj.pds.log.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2018/1/4
 * Time: 9:14
 * Description:
 **/
public class LoginMessagePageInVO implements JInputModel {

    // 登录账号
    private String loginAccount;

    // 项目主题
    private String projectObject;

    // 日志类型
    private String logType;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getProjectObject() {
        return projectObject;
    }

    public void setProjectObject(String projectObject) {
        this.projectObject = projectObject;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
