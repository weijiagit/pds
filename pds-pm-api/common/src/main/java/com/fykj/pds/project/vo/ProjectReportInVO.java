package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JInputModel;

public class ProjectReportInVO implements JInputModel {

	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;

	private String importentProject;
	 
	private String startTime;
	
	private String endTime;
	 
	private String implementSchedule;
	 
	private String industryClassification;
	
	private String isZs;
	 
	public String getImplementSchedule() {
		return implementSchedule;
	}

	public void setImplementSchedule(String implementSchedule) {
		this.implementSchedule = implementSchedule;
	}

	public String getIndustryClassification() {
		return industryClassification;
	}

	public void setIndustryClassification(String industryClassification) {
		this.industryClassification = industryClassification;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsZs() {
		return isZs;
	}

	public void setIsZs(String isZs) {
		this.isZs = isZs;
	}

	public String getImportentProject() {
		return importentProject;
	}

	public void setImportentProject(String importentProject) {
		this.importentProject = importentProject;
	}
}
