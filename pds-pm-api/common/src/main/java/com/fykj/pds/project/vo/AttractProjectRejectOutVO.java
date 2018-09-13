package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;


/**
 * weijia
 */
public class AttractProjectRejectOutVO implements JOutputModel {
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回信息
     */
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
