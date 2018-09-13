package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;

public class ProjectReportOutVO implements JOutputModel {

	/**  
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String projectNumber;
	 
	private String companyName;
	   
	private Double totalInvestment;

	private String totalInvestmentStr;

	private String projectName;
	 
	private String isZs;
	 
	private String staticYear;
	 
	private String constructionContentScale;
	 
	private String implementSchedule;
	 
	private String projectAttribute;
	 
	private String industryClassification;
	 
	private String industryClassificationStr;
	 
	private String implementScheduleStr;
	 
	private String projectAttributeStr;

	public String getTotalInvestmentStr() {
		return totalInvestmentStr;
	}

	public void setTotalInvestmentStr(String totalInvestmentStr) {
		this.totalInvestmentStr = totalInvestmentStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(Double totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getIsZs() {
		return isZs;
	}

	public void setIsZs(String isZs) {
		this.isZs = isZs;
	}

	public String getStaticYear() {
		return staticYear;
	}

	public void setStaticYear(String staticYear) {
		this.staticYear = staticYear;
	}

	public String getConstructionContentScale() {
		return constructionContentScale;
	}

	public void setConstructionContentScale(String constructionContentScale) {
		this.constructionContentScale = constructionContentScale;
	}

	public String getImplementSchedule() {
		return implementSchedule;
	}

	public void setImplementSchedule(String implementSchedule) {
		this.implementSchedule = implementSchedule;
	}

	public String getProjectAttribute() {
		return projectAttribute;
	}

	public void setProjectAttribute(String projectAttribute) {
		this.projectAttribute = projectAttribute;
	}

	public String getIndustryClassification() {
		return industryClassification;
	}

	public void setIndustryClassification(String industryClassification) {
		this.industryClassification = industryClassification;
	}

	public String getIndustryClassificationStr() {
		return industryClassificationStr;
	}

	public void setIndustryClassificationStr(String industryClassificationStr) {
		this.industryClassificationStr = industryClassificationStr;
	}

	public String getImplementScheduleStr() {
		return implementScheduleStr;
	}

	public void setImplementScheduleStr(String implementScheduleStr) {
		this.implementScheduleStr = implementScheduleStr;
	}

	public String getProjectAttributeStr() {
		return projectAttributeStr;
	}

	public void setProjectAttributeStr(String projectAttributeStr) {
		this.projectAttributeStr = projectAttributeStr;
	}
}
