package com.fykj.pds.work.backlogWork.vo;

import java.util.Date;

/**
 * Author: songzhonglin
 * Date: 2017/12/7
 * Time: 15:59
 * Description:
 **/
public class BacklogWorkPageOutVO {

    private String id;

    // 项目代码
    private String projectCode;

    // 项目名称
    private String projectName;

    // 被催办任务
    private String hastenTask;

    // 被分配任务
    private String assignmentTask;

    // 项目创建时间
    private String projectCreateTimeFormat;

    private Date projectCreateTime;

    // 每个项目的主键ID
    private String projectId;

    // 操作类型
    private String operationType;

    // 状态
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHastenTask() {
        return hastenTask;
    }

    public void setHastenTask(String hastenTask) {
        this.hastenTask = hastenTask;
    }

    public String getAssignmentTask() {
        return assignmentTask;
    }

    public void setAssignmentTask(String assignmentTask) {
        this.assignmentTask = assignmentTask;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getProjectCreateTimeFormat() {
        return projectCreateTimeFormat;
    }

    public void setProjectCreateTimeFormat(String projectCreateTimeFormat) {
        this.projectCreateTimeFormat = projectCreateTimeFormat;
    }

    public Date getProjectCreateTime() {
        return projectCreateTime;
    }

    public void setProjectCreateTime(Date projectCreateTime) {
        this.projectCreateTime = projectCreateTime;
    }
}
