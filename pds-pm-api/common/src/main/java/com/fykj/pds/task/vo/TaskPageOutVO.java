package com.fykj.pds.task.vo;

import java.util.Date;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 15:41
 * Description:
 **/
public class TaskPageOutVO {

    private String id;

    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 分包领导
     */

    private String subcontractLeader;
    /**
     * 工作任务
     */
    private String workContent;
    /**
     * 责任单位
     */
    private String responsibleDepartment;
    /**
     *
     */
    private String responsibleLeader;
    /**
     * 责任人
     */
    private String responsiblePeople;
    /**
     *时间节点
     */
    private Date endDate;

    private String endDateFormat;

    /**
     * 创建时间
     */

    private Date createDate;

    private String createDateFormat;
    /**
     *
     */
    private String state;
    /**
     * 完成情况
     */
    private String isDepartFinish;
    /**
     * 核查情况
     */
    private String isFinish;


    /**
     * 工作进度
     */
    private String progressWork;

    /**
     * 批次
     */
    private String batch;

    public String getProgressWork() {
        return progressWork;
    }

    public void setProgressWork(String progressWork) {
        this.progressWork = progressWork;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateFormat() {
        return createDateFormat;
    }

    public void setCreateDateFormat(String createDateFormat) {
        this.createDateFormat = createDateFormat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndDateFormat() {
        return endDateFormat;
    }

    public void setEndDateFormat(String endDateFormat) {
        this.endDateFormat = endDateFormat;
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

    public String getSubcontractLeader() {
        return subcontractLeader;
    }

    public void setSubcontractLeader(String subcontractLeader) {
        this.subcontractLeader = subcontractLeader;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getResponsibleDepartment() {
        return responsibleDepartment;
    }

    public void setResponsibleDepartment(String responsibleDepartment) {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getResponsibleLeader() {
        return responsibleLeader;
    }

    public void setResponsibleLeader(String responsibleLeader) {
        this.responsibleLeader = responsibleLeader;
    }

    public String getResponsiblePeople() {
        return responsiblePeople;
    }

    public void setResponsiblePeople(String responsiblePeople) {
        this.responsiblePeople = responsiblePeople;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsDepartFinish() {
        return isDepartFinish;
    }

    public void setIsDepartFinish(String isDepartFinish) {
        this.isDepartFinish = isDepartFinish;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }
}
