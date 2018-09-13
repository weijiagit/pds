package com.fykj.pds.task.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fykj.kernel._c.model.AbstractEntity;

@Entity
@Table(name = "t_task")
public class Task extends AbstractEntity {

	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "project_code")
	private String projectCode;
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "subcontract_leader")
	private String subcontractLeader;
	
	@Column(name = "work_content")
	private String workContent;
	
	@Column(name = "responsible_department")
	private String responsibleDepartment;
	
	@Column(name = "responsible_leader")
	private String responsibleLeader;
	
	@Column(name = "responsible_people")
	private String responsiblePeople;
	
	@Column(name = "end_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date endDate;

	@Column(name = "state")
	private String state;

	@Column(name = "is_depart_finish")
	private String isDepartFinish;
	
	@Column(name = "is_finish")
	private String isFinish;

	@Column(name = "progress_work")
	private String progressWork;

	@Column(name = "batch")
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
