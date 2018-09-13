package com.fykj.pds.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fykj.kernel._c.model.AbstractEntity;

@Entity
@Table(name = "t_project_deprtment")
public class ProjectDepartment extends AbstractEntity {

	@Column(name = "project_id")
    private String projectId;
    
    @Column(name = "department_id")
    private String departmentId;

	@Column(name = "approve_status")
	private String approveStatus;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
}
