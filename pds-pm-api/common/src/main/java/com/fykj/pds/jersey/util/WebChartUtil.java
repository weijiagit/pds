package com.fykj.pds.jersey.util;

import com.fykj.kernel._c.model.JModel;

/**
 * Author: songzhonglin
 * Date: 2018/4/10
 * Time: 14:33
 * Description:
 **/
public class WebChartUtil implements JModel {

    private String id;
    private String userName;
    private String natureName;
    private String code;
    private String msg;
    private String password;

    private String openId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
