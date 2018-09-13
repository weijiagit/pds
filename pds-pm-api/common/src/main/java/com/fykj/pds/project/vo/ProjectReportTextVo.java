package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;

public class ProjectReportTextVo  implements JOutputModel {

	/**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;

	private String yearText;// 年份
	private String type;//类型
	private String title;//标题
	private String totalInvestmentText;//投资
	public String getYearText() {
		return yearText;
	}
	public void setYearText(String yearText) {
		this.yearText = yearText;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTotalInvestmentText() {
		return totalInvestmentText;
	}
	public void setTotalInvestmentText(String totalInvestmentText) {
		this.totalInvestmentText = totalInvestmentText;
	}
	
	
	
	
}
