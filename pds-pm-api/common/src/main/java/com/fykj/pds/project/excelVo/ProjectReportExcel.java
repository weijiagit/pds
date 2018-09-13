package com.fykj.pds.project.excelVo;

import com.fykj.kernel._c.model.JModel;
import com.fykj.kernel.excel.ColumnWalker;
import com.fykj.kernel.excel.ColumnWalkerImpl;
import com.fykj.kernel.excel.ExcelColumn;

public class ProjectReportExcel extends ColumnWalkerImpl implements JModel, ColumnWalker {

    /**  
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).   
	 */
	private static final long serialVersionUID = 1L;
	

	@ExcelColumn("行业分类")
  	private String industryClassificationStr;
    
	@ExcelColumn("建设单位")
	private String companyName;
    
    @ExcelColumn("项目名称")
    private String projectName;
	
	@ExcelColumn("建设内容和规模")
	private String constructionContentScale;
	
    @ExcelColumn("总投资(元)")
	private String totalInvestment;
	
	@ExcelColumn("实施进度分类")
	private String implementScheduleStr;
	
	@ExcelColumn("项目属地")
	private String projectAttributeStr;
	
    


	public ProjectReportExcel(String industryClassificationStr, String companyName, String projectName,
			String constructionContentScale, String totalInvestment, String implementScheduleStr,
			String projectAttributeStr) {
		super();
		this.industryClassificationStr = industryClassificationStr;
		this.companyName = companyName;
		this.projectName = projectName;
		this.constructionContentScale = constructionContentScale;
		this.totalInvestment = totalInvestment;
		this.implementScheduleStr = implementScheduleStr;
		this.projectAttributeStr = projectAttributeStr;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(String totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

	public String getIndustryClassificationStr() {
		return industryClassificationStr;
	}

	public void setIndustryClassificationStr(String industryClassificationStr) {
		this.industryClassificationStr = industryClassificationStr;
	}

	public String getConstructionContentScale() {
		return constructionContentScale;
	}

	public void setConstructionContentScale(String constructionContentScale) {
		this.constructionContentScale = constructionContentScale;
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
