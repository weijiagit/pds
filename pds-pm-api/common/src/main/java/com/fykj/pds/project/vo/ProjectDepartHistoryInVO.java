package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JInputModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * weijia
 */
public class ProjectDepartHistoryInVO implements JInputModel {

    //项目部门ID
    private String projectDepartId;
    //审批状态
    private String approveStatus;
    //问题描述
    private String problemDescribe;
    //备注
    private String remark;
    //处理人
    private String leaderId;
    //项目ID
    private String projectId;

    //相关附件
    private String attachmentIds;

    public String getProjectDepartId() {
        return projectDepartId;
    }

    public void setProjectDepartId(String projectDepartId) {
        this.projectDepartId = projectDepartId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getProblemDescribe() {
        return problemDescribe;
    }

    public void setProblemDescribe(String problemDescribe) {
        this.problemDescribe = problemDescribe;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
