package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;

public class ProjectTotalOutVO implements JOutputModel {

	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;
	
	private String staticYear;
	private Long totalProject;
	private Double totalInvestment;
	private String type;
	
	public String getStaticYear() {
		return staticYear;
	}
	public void setStaticYear(String staticYear) {
		this.staticYear = staticYear;
	}
	public Long getTotalProject() {
		return totalProject;
	}
	public void setTotalProject(Long totalProject) {
		this.totalProject = totalProject;
	}
	public Double getTotalInvestment() {
		return totalInvestment;
	}
	public void setTotalInvestment(Double totalInvestment) {
		this.totalInvestment = totalInvestment;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
