package com.fykj.pds.work.backlogWork.model;

import com.fykj.kernel._c.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: songzhonglin
 * Date: 2017/12/7
 * Time: 16:05
 * Description:
 **/
@Entity
@Table(name = "t_backlog_work")
public class BacklogWork extends AbstractEntity {

    // 项目代码
    @Column(name = "project_code")
    private String projectCode;

    // 项目名称
    @Column(name = "project_name")
    private String projectName;

    // 被催办任务
    @Column(name = "hasten_task")
    private String hastenTask;

    // 被分配任务
    @Column(name = "assignment_task")
    private String assignmentTask;

    // 项目创建时间
    @Column(name = "project_create_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date projectCreateTime;

    // 每个项目的主键ID
    @Column(name = "project_id")
    private String projectId;

    // 操作类型
    @Column(name = "operation_type")
    private String operationType;

    // 状态
    @Column(name = "state")
    private String state;

    // 状态
    @Column(name = "leader_id")
    private String leaderId;

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
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

    public Date getProjectCreateTime() {
        return projectCreateTime;
    }

    public void setProjectCreateTime(Date projectCreateTime) {
        this.projectCreateTime = projectCreateTime;
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
}
