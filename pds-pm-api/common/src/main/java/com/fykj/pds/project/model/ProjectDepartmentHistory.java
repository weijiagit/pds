package com.fykj.pds.project.model;

import com.fykj.kernel._c.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_project_deprtment_history")
public class ProjectDepartmentHistory extends AbstractEntity {

	@Column(name = "approve_status")
	private String approveStatus;


	@Column(name = "project_depart_id")
	private String projectDepartId;

	@Column(name = "problem_describe")
	private String problemDescribe;

	@Column(name = "remark")
	private String remark;

	@Column(name = "leader_id")
	private String leaderId;

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getProjectDepartId() {
		return projectDepartId;
	}

	public void setProjectDepartId(String projectDepartId) {
		this.projectDepartId = projectDepartId;
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

	public String getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
}
