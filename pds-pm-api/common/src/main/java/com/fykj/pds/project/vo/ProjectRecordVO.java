
package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;

public class ProjectRecordVO extends ProjectInfo implements JOutputModel {

	/**
	 * 计划开工日期
	 */
	private String planBeginDateStr;

	/**
	 * 计划竣工日期
	 */
	private String planEndDateStr;

	/**
	 * 实际开工日期
	 */
	private String actualBeginDateStr;

	/**
	 * 实际竣工日期
	 */
	private String actualEndDateStr;

	/**
	 * 总投资
	 */
	private String totalInvestmentStr;

	/**
	 * 总投资显示标志
	 */
	private String totalInvestmentFlag;

	/**
	 *项目属地(遵化店镇、皇台街道办事处、高新区、其他)
	 */
	private String projectAttributeStr;

	/**
	 *建设性质(新建、扩建、改建、迁建、技术改造)
	 */
	private String constructionNatureStr;

	/**
	 * 行业分类
	 */
	private String industryClassificationStr;

	/**
	 * 实施进度
	 */
	private String implementScheduleStr;

	public String getTotalInvestmentFlag() {
		return totalInvestmentFlag;
	}

	public void setTotalInvestmentFlag(String totalInvestmentFlag) {
		this.totalInvestmentFlag = totalInvestmentFlag;
	}

	public String getTotalInvestmentStr() {
		return totalInvestmentStr;
	}

	public void setTotalInvestmentStr(String totalInvestmentStr) {
		this.totalInvestmentStr = totalInvestmentStr;
	}

	public String getPlanBeginDateStr() {
		return planBeginDateStr;
	}

	public void setPlanBeginDateStr(String planBeginDateStr) {
		this.planBeginDateStr = planBeginDateStr;
	}

	public String getPlanEndDateStr() {
		return planEndDateStr;
	}

	public void setPlanEndDateStr(String planEndDateStr) {
		this.planEndDateStr = planEndDateStr;
	}

	public String getActualBeginDateStr() {
		return actualBeginDateStr;
	}

	public void setActualBeginDateStr(String actualBeginDateStr) {
		this.actualBeginDateStr = actualBeginDateStr;
	}

	public String getActualEndDateStr() {
		return actualEndDateStr;
	}

	public void setActualEndDateStr(String actualEndDateStr) {
		this.actualEndDateStr = actualEndDateStr;
	}

	public String getProjectAttributeStr() {
		return projectAttributeStr;
	}

	public void setProjectAttributeStr(String projectAttributeStr) {
		this.projectAttributeStr = projectAttributeStr;
	}

	public String getConstructionNatureStr() {
		return constructionNatureStr;
	}

	public void setConstructionNatureStr(String constructionNatureStr) {
		this.constructionNatureStr = constructionNatureStr;
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
}
