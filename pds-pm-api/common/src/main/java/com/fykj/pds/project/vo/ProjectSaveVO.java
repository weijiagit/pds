
package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JInputModel;

public class ProjectSaveVO extends ProjectInfo implements JInputModel {

	/**
	 * 总投资
	 */
	private String totalInvestmentStr;

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
	 * 草稿正常标志
	 */
	private String projectFillStatusStr;


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

	public String getProjectFillStatusStr() {
		return projectFillStatusStr;
	}

	public void setProjectFillStatusStr(String projectFillStatusStr) {
		this.projectFillStatusStr = projectFillStatusStr;
	}
}
