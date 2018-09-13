package com.fykj.pds.jersey.util;

/**
 * Author: songzhonglin
 * Date: 2018/4/11
 * Time: 9:15
 * Description: 返回结果处理
 **/
public class PlanResult {

    //响应业务状态
    private String code;
    //相应消息
    private String msg;
    //相应中的数据
    private Object data;
    // 总记录数
    private Long totalRecordNumber;

    // 项目推进权限标志（0：没有 1：有）
    private String taskFlag;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotalRecordNumber() {
        return totalRecordNumber;
    }

    public void setTotalRecordNumber(Long totalRecordNumber) {
        this.totalRecordNumber = totalRecordNumber;
    }

    public String getTaskFlag() {
        return taskFlag;
    }

    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag;
    }
}
