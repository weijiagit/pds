package com.fykj.pds.work.personWork.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2017/12/1
 * Time: 13:53
 * Description:
 **/
public class PersonWorkAddInVO implements JInputModel {

    // 个人任务
    private String taskContent;
    //任务时间
    private String taskDateStr;
    // 状态
    private String state;

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskDateStr() {
        return taskDateStr;
    }

    public void setTaskDateStr(String taskDateStr) {
        this.taskDateStr = taskDateStr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
