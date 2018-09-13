package com.fykj.pds.work.personWork.vo;

import java.util.Date;

/**
 * Author: songzhonglin
 * Date: 2017/12/1
 * Time: 13:54
 * Description:
 **/
public class PersonWorkPageOutVO {

    // 主键ID
    private String id;
    // 个人任务
    private String taskContent;
    //任务时间
    private String taskDateFormat;

    private Date taskDate;
    // 状态
    private String state;

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTaskDateFormat() {
        return taskDateFormat;
    }

    public void setTaskDateFormat(String taskDateFormat) {
        this.taskDateFormat = taskDateFormat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
