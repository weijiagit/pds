package com.fykj.pds.interfaceManage.vo;

import com.fykj.kernel._c.model.JOutputModel;


/**
 * weijia
 */
public class OAMessageOutVO implements JOutputModel {
    /**
     * 返回码
     */
    private String success;
    /**
     * 返回信息
     */
    private String msg;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
