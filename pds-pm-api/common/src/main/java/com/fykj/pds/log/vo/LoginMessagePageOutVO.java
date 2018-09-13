package com.fykj.pds.log.vo;

import java.util.Date;

/**
 * Author: songzhonglin
 * Date: 2018/1/4
 * Time: 9:12
 * Description:
 **/
public class LoginMessagePageOutVO {

    // 主键Id
    private String id ;

    // 登录账号
    private String loginAccount;

    // 登录名称
    private String loginName;

    // 登录时间
    private Date signInTime;

    private String signInTimeStr;

    // 登出时间
    private Date signOutTime;

    private String signOutTimeStr;

    // 操作时间
    private Date operateTime;

    private String operateTimeStr;

    // 日志类型
    private String logType;

    // 项目主题
    private String projectObject;

    // 模块名称
    private String projectModule;

    public String getProjectModule() {
        return projectModule;
    }

    public void setProjectModule(String projectModule) {
        this.projectModule = projectModule;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getProjectObject() {
        return projectObject;
    }

    public void setProjectObject(String projectObject) {
        this.projectObject = projectObject;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateTimeStr() {
        return operateTimeStr;
    }

    public void setOperateTimeStr(String operateTimeStr) {
        this.operateTimeStr = operateTimeStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public String getSignInTimeStr() {
        return signInTimeStr;
    }

    public void setSignInTimeStr(String signInTimeStr) {
        this.signInTimeStr = signInTimeStr;
    }

    public Date getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }

    public String getSignOutTimeStr() {
        return signOutTimeStr;
    }

    public void setSignOutTimeStr(String signOutTimeStr) {
        this.signOutTimeStr = signOutTimeStr;
    }
}
