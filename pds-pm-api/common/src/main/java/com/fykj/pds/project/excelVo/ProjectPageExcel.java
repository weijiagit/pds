package com.fykj.pds.project.excelVo;

import com.fykj.kernel._c.model.JModel;
import com.fykj.kernel.excel.ColumnWalker;
import com.fykj.kernel.excel.ColumnWalkerImpl;
import com.fykj.kernel.excel.ExcelColumn;
import com.fykj.kernel.excel.ExcelHead;

@ExcelHead("项目详细信息")
public class ProjectPageExcel extends ColumnWalkerImpl implements JModel, ColumnWalker {

	@ExcelColumn("项目名称")
	private String projectName;

	@ExcelColumn("项目代码")
	private String projectNumber;

	@ExcelColumn("建设单位")
	private String companyName;

	@ExcelColumn("组织机构代码")
	private String organizationCode;

	@ExcelColumn("项目属地")
	private String projectAttribute;

	@ExcelColumn("建设性质")
	private String constructionNature;

	@ExcelColumn("行业分类")
	private String industryClassification;

	@ExcelColumn("实施进度分类")
	private String implementSchedule;

	@ExcelColumn("计划开工日期")
	private String planBeginDateFormat;

	@ExcelColumn("计划竣工日期")
	private String planEndDateFormat;

	@ExcelColumn("实际开工日期")
	private String actualBeginDateFormat;

	@ExcelColumn("实际竣工日期")
	private String actualEndDateFormat;

	@ExcelColumn("总投资")
	private String totalInvestment;

	@ExcelColumn("企业法人")
	private String enterpriseLegalPerson;

	@ExcelColumn("企业联系人")
	private String enterpriseContactPerson;

	@ExcelColumn("企业联系人联系方式")
	private String enterpriseContactPhone;

	@ExcelColumn("企业负责人")
	private String enterpriseChargePerson;

	@ExcelColumn("企业负责人联系方式")
	private String enterpriseChargePhone;

	@ExcelColumn("建设内容和规模")
	private String constructionContentScale;

	@ExcelColumn("行业分类是否ppp项目")
	private String ispppProject;

	@ExcelColumn("是否计划新开工")
	private String isplanNewWork;

	@ExcelColumn("是否计划竣工")
	private String isplanComplete;

	@ExcelColumn("是否省重点项目")
	private String isprovinceimp;

	@ExcelColumn("是否市重点项目")
	private String iscityimp;

	@ExcelColumn("是否区重点项目")
	private String isdistrictimp;

	@ExcelColumn("分包领导")
	private String subcontractLeader;

	@ExcelColumn("分包单位1")
	private String subcontractCompany1;

	@ExcelColumn("分包单位1责任人")
	private String subcontractCompany1Person;

	@ExcelColumn("分包单位1责任人联系方式")
	private String subcontractCompany1PersonPhone;

	@ExcelColumn("分包单位1联系人")
	private String subcontractCompany1Contact;

	@ExcelColumn("分包单位1联系人联系方式")
	private String subcontractCompany1ContactPhone;

	@ExcelColumn("分包单位2")
	private String subcontractCompany2;

	@ExcelColumn("分包单位2责任人")
	private String subcontractCompany2Person;

	@ExcelColumn("分包单位2责任人联系方式")
	private String subcontractCompany2PersonPhone;

	@ExcelColumn("分包单位2联系人")
	private String subcontractCompany2Contact;

	@ExcelColumn("分包单位2联系人联系方式")
	private String subcontractCompany2ContactPhone;

	@ExcelColumn("分包单位3")
	private String subcontractCompany3;

	@ExcelColumn("分包单位3责任人")
	private String subcontractCompany3Person;

	@ExcelColumn("分包单位3责任人联系方式")
	private String subcontractCompany3PersonPhone;

	@ExcelColumn("分包单位3联系人")
	private String subcontractCompany3Contact;

	@ExcelColumn("分包单位3联系人联系方式")
	private String subcontractCompany3ContactPhone;

	@ExcelColumn("选址意见书是否完成")
	private String issiteselect;

	@ExcelColumn("是否存在问题")
	private String isproblem;

	@ExcelColumn("国有土地使用证是否完成")
	private String isstateland;

	@ExcelColumn("人防批文是否完成")
	private String ispeopledefence;

	@ExcelColumn("立项批文是否完成")
	private String isprojectapprove;

	@ExcelColumn("是否联审联批项目")
	private String ischeckapprove;

	@ExcelColumn("建设工程规划许可证是否完成")
	private String isbuildproject;

	@ExcelColumn("是否模拟审批项目")
	private String issimulateapprove;

	@ExcelColumn("环评批文是否完成")
	private String isenvironmentapprove;

	@ExcelColumn("文物勘探是否完成")
	private String isculturalrelics;

	@ExcelColumn("气象局手续是否完成")
	private String isweather;

	@ExcelColumn("抗震设防是否完成")
	private String isantiknock;

	@ExcelColumn("节能审批是否完成")
	private String isenergyconservation;

	@ExcelColumn("园林绿化是否完成")
	private String isparkafforest;

	@ExcelColumn("消防是否完成")
	private String isfirecontrol;

	@ExcelColumn("建设用地规划许可证")
	private String isbuildland;

	@ExcelColumn("模拟审批是否完成")
	private String issimulatefinish;

	@ExcelColumn("是否转结")
	private String transferStatus;

	@ExcelColumn("是否草稿")
	private String projectFillStatus;

	public ProjectPageExcel(String projectName, String projectNumber, String companyName, String organizationCode, String projectAttribute, String constructionNature, String industryClassification, String implementSchedule, String planBeginDateFormat, String planEndDateFormat, String actualBeginDateFormat, String actualEndDateFormat, String totalInvestment, String enterpriseLegalPerson, String enterpriseContactPerson, String enterpriseContactPhone, String enterpriseChargePerson, String enterpriseChargePhone, String constructionContentScale, String ispppProject, String isplanNewWork, String isplanComplete, String isprovinceimp, String iscityimp, String isdistrictimp, String subcontractLeader, String subcontractCompany1, String subcontractCompany1Person, String subcontractCompany1PersonPhone, String subcontractCompany1Contact, String subcontractCompany1ContactPhone, String subcontractCompany2, String subcontractCompany2Person, String subcontractCompany2PersonPhone, String subcontractCompany2Contact, String subcontractCompany2ContactPhone, String subcontractCompany3, String subcontractCompany3Person, String subcontractCompany3PersonPhone, String subcontractCompany3Contact, String subcontractCompany3ContactPhone, String issiteselect, String isproblem, String isstateland, String ispeopledefence, String isprojectapprove, String ischeckapprove, String isbuildproject, String issimulateapprove, String isenvironmentapprove, String isculturalrelics, String isweather, String isantiknock, String isenergyconservation, String isparkafforest, String isfirecontrol, String isbuildland, String issimulatefinish,String transferStatus, String projectFillStatus) {
		this.projectName = projectName;
		this.projectNumber = projectNumber;
		this.companyName = companyName;
		this.organizationCode = organizationCode;
		this.projectAttribute = projectAttribute;
		this.constructionNature = constructionNature;
		this.industryClassification = industryClassification;
		this.implementSchedule = implementSchedule;
		this.planBeginDateFormat = planBeginDateFormat;
		this.planEndDateFormat = planEndDateFormat;
		this.actualBeginDateFormat = actualBeginDateFormat;
		this.actualEndDateFormat = actualEndDateFormat;
		this.totalInvestment = totalInvestment;
		this.enterpriseLegalPerson = enterpriseLegalPerson;
		this.enterpriseContactPerson = enterpriseContactPerson;
		this.enterpriseContactPhone = enterpriseContactPhone;
		this.enterpriseChargePerson = enterpriseChargePerson;
		this.enterpriseChargePhone = enterpriseChargePhone;
		this.constructionContentScale = constructionContentScale;
		this.ispppProject = ispppProject;
		this.isplanNewWork = isplanNewWork;
		this.isplanComplete = isplanComplete;
		this.isprovinceimp = isprovinceimp;
		this.iscityimp = iscityimp;
		this.isdistrictimp = isdistrictimp;
		this.subcontractLeader = subcontractLeader;
		this.subcontractCompany1 = subcontractCompany1;
		this.subcontractCompany1Person = subcontractCompany1Person;
		this.subcontractCompany1PersonPhone = subcontractCompany1PersonPhone;
		this.subcontractCompany1Contact = subcontractCompany1Contact;
		this.subcontractCompany1ContactPhone = subcontractCompany1ContactPhone;
		this.subcontractCompany2 = subcontractCompany2;
		this.subcontractCompany2Person = subcontractCompany2Person;
		this.subcontractCompany2PersonPhone = subcontractCompany2PersonPhone;
		this.subcontractCompany2Contact = subcontractCompany2Contact;
		this.subcontractCompany2ContactPhone = subcontractCompany2ContactPhone;
		this.subcontractCompany3 = subcontractCompany3;
		this.subcontractCompany3Person = subcontractCompany3Person;
		this.subcontractCompany3PersonPhone = subcontractCompany3PersonPhone;
		this.subcontractCompany3Contact = subcontractCompany3Contact;
		this.subcontractCompany3ContactPhone = subcontractCompany3ContactPhone;
		this.issiteselect = issiteselect;
		this.isproblem = isproblem;
		this.isstateland = isstateland;
		this.ispeopledefence = ispeopledefence;
		this.isprojectapprove = isprojectapprove;
		this.ischeckapprove = ischeckapprove;
		this.isbuildproject = isbuildproject;
		this.issimulateapprove = issimulateapprove;
		this.isenvironmentapprove = isenvironmentapprove;
		this.isculturalrelics = isculturalrelics;
		this.isweather = isweather;
		this.isantiknock = isantiknock;
		this.isenergyconservation = isenergyconservation;
		this.isparkafforest = isparkafforest;
		this.isfirecontrol = isfirecontrol;
		this.isbuildland = isbuildland;
		this.issimulatefinish = issimulatefinish;
		this.transferStatus = transferStatus;
		this.projectFillStatus = projectFillStatus;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getProjectAttribute() {
		return projectAttribute;
	}

	public void setProjectAttribute(String projectAttribute) {
		this.projectAttribute = projectAttribute;
	}

	public String getConstructionNature() {
		return constructionNature;
	}

	public void setConstructionNature(String constructionNature) {
		this.constructionNature = constructionNature;
	}

	public String getIndustryClassification() {
		return industryClassification;
	}

	public void setIndustryClassification(String industryClassification) {
		this.industryClassification = industryClassification;
	}

	public String getImplementSchedule() {
		return implementSchedule;
	}

	public void setImplementSchedule(String implementSchedule) {
		this.implementSchedule = implementSchedule;
	}

	public String getPlanBeginDateFormat() {
		return planBeginDateFormat;
	}

	public void setPlanBeginDateFormat(String planBeginDateFormat) {
		this.planBeginDateFormat = planBeginDateFormat;
	}

	public String getPlanEndDateFormat() {
		return planEndDateFormat;
	}

	public void setPlanEndDateFormat(String planEndDateFormat) {
		this.planEndDateFormat = planEndDateFormat;
	}

	public String getActualBeginDateFormat() {
		return actualBeginDateFormat;
	}

	public void setActualBeginDateFormat(String actualBeginDateFormat) {
		this.actualBeginDateFormat = actualBeginDateFormat;
	}

	public String getActualEndDateFormat() {
		return actualEndDateFormat;
	}

	public void setActualEndDateFormat(String actualEndDateFormat) {
		this.actualEndDateFormat = actualEndDateFormat;
	}

	public String getTotalInvestment() {
		return totalInvestment;
	}

	public void setTotalInvestment(String totalInvestment) {
		this.totalInvestment = totalInvestment;
	}

	public String getEnterpriseLegalPerson() {
		return enterpriseLegalPerson;
	}

	public void setEnterpriseLegalPerson(String enterpriseLegalPerson) {
		this.enterpriseLegalPerson = enterpriseLegalPerson;
	}

	public String getEnterpriseContactPerson() {
		return enterpriseContactPerson;
	}

	public void setEnterpriseContactPerson(String enterpriseContactPerson) {
		this.enterpriseContactPerson = enterpriseContactPerson;
	}

	public String getEnterpriseContactPhone() {
		return enterpriseContactPhone;
	}

	public void setEnterpriseContactPhone(String enterpriseContactPhone) {
		this.enterpriseContactPhone = enterpriseContactPhone;
	}

	public String getEnterpriseChargePerson() {
		return enterpriseChargePerson;
	}

	public void setEnterpriseChargePerson(String enterpriseChargePerson) {
		this.enterpriseChargePerson = enterpriseChargePerson;
	}

	public String getEnterpriseChargePhone() {
		return enterpriseChargePhone;
	}

	public void setEnterpriseChargePhone(String enterpriseChargePhone) {
		this.enterpriseChargePhone = enterpriseChargePhone;
	}

	public String getConstructionContentScale() {
		return constructionContentScale;
	}

	public void setConstructionContentScale(String constructionContentScale) {
		this.constructionContentScale = constructionContentScale;
	}

	public String getIspppProject() {
		return ispppProject;
	}

	public void setIspppProject(String ispppProject) {
		this.ispppProject = ispppProject;
	}

	public String getIsplanNewWork() {
		return isplanNewWork;
	}

	public void setIsplanNewWork(String isplanNewWork) {
		this.isplanNewWork = isplanNewWork;
	}

	public String getIsplanComplete() {
		return isplanComplete;
	}

	public void setIsplanComplete(String isplanComplete) {
		this.isplanComplete = isplanComplete;
	}

	public String getIsprovinceimp() {
		return isprovinceimp;
	}

	public void setIsprovinceimp(String isprovinceimp) {
		this.isprovinceimp = isprovinceimp;
	}

	public String getIscityimp() {
		return iscityimp;
	}

	public void setIscityimp(String iscityimp) {
		this.iscityimp = iscityimp;
	}

	public String getIsdistrictimp() {
		return isdistrictimp;
	}

	public void setIsdistrictimp(String isdistrictimp) {
		this.isdistrictimp = isdistrictimp;
	}

	public String getSubcontractLeader() {
		return subcontractLeader;
	}

	public void setSubcontractLeader(String subcontractLeader) {
		this.subcontractLeader = subcontractLeader;
	}

	public String getSubcontractCompany1() {
		return subcontractCompany1;
	}

	public void setSubcontractCompany1(String subcontractCompany1) {
		this.subcontractCompany1 = subcontractCompany1;
	}

	public String getSubcontractCompany1Person() {
		return subcontractCompany1Person;
	}

	public void setSubcontractCompany1Person(String subcontractCompany1Person) {
		this.subcontractCompany1Person = subcontractCompany1Person;
	}

	public String getSubcontractCompany1PersonPhone() {
		return subcontractCompany1PersonPhone;
	}

	public void setSubcontractCompany1PersonPhone(String subcontractCompany1PersonPhone) {
		this.subcontractCompany1PersonPhone = subcontractCompany1PersonPhone;
	}

	public String getSubcontractCompany1Contact() {
		return subcontractCompany1Contact;
	}

	public void setSubcontractCompany1Contact(String subcontractCompany1Contact) {
		this.subcontractCompany1Contact = subcontractCompany1Contact;
	}

	public String getSubcontractCompany1ContactPhone() {
		return subcontractCompany1ContactPhone;
	}

	public void setSubcontractCompany1ContactPhone(String subcontractCompany1ContactPhone) {
		this.subcontractCompany1ContactPhone = subcontractCompany1ContactPhone;
	}

	public String getSubcontractCompany2() {
		return subcontractCompany2;
	}

	public void setSubcontractCompany2(String subcontractCompany2) {
		this.subcontractCompany2 = subcontractCompany2;
	}

	public String getSubcontractCompany2Person() {
		return subcontractCompany2Person;
	}

	public void setSubcontractCompany2Person(String subcontractCompany2Person) {
		this.subcontractCompany2Person = subcontractCompany2Person;
	}

	public String getSubcontractCompany2PersonPhone() {
		return subcontractCompany2PersonPhone;
	}

	public void setSubcontractCompany2PersonPhone(String subcontractCompany2PersonPhone) {
		this.subcontractCompany2PersonPhone = subcontractCompany2PersonPhone;
	}

	public String getSubcontractCompany2Contact() {
		return subcontractCompany2Contact;
	}

	public void setSubcontractCompany2Contact(String subcontractCompany2Contact) {
		this.subcontractCompany2Contact = subcontractCompany2Contact;
	}

	public String getSubcontractCompany2ContactPhone() {
		return subcontractCompany2ContactPhone;
	}

	public void setSubcontractCompany2ContactPhone(String subcontractCompany2ContactPhone) {
		this.subcontractCompany2ContactPhone = subcontractCompany2ContactPhone;
	}

	public String getSubcontractCompany3() {
		return subcontractCompany3;
	}

	public void setSubcontractCompany3(String subcontractCompany3) {
		this.subcontractCompany3 = subcontractCompany3;
	}

	public String getSubcontractCompany3Person() {
		return subcontractCompany3Person;
	}

	public void setSubcontractCompany3Person(String subcontractCompany3Person) {
		this.subcontractCompany3Person = subcontractCompany3Person;
	}

	public String getSubcontractCompany3PersonPhone() {
		return subcontractCompany3PersonPhone;
	}

	public void setSubcontractCompany3PersonPhone(String subcontractCompany3PersonPhone) {
		this.subcontractCompany3PersonPhone = subcontractCompany3PersonPhone;
	}

	public String getSubcontractCompany3Contact() {
		return subcontractCompany3Contact;
	}

	public void setSubcontractCompany3Contact(String subcontractCompany3Contact) {
		this.subcontractCompany3Contact = subcontractCompany3Contact;
	}

	public String getSubcontractCompany3ContactPhone() {
		return subcontractCompany3ContactPhone;
	}

	public void setSubcontractCompany3ContactPhone(String subcontractCompany3ContactPhone) {
		this.subcontractCompany3ContactPhone = subcontractCompany3ContactPhone;
	}

	public String getIssiteselect() {
		return issiteselect;
	}

	public void setIssiteselect(String issiteselect) {
		this.issiteselect = issiteselect;
	}

	public String getIsproblem() {
		return isproblem;
	}

	public void setIsproblem(String isproblem) {
		this.isproblem = isproblem;
	}

	public String getIsstateland() {
		return isstateland;
	}

	public void setIsstateland(String isstateland) {
		this.isstateland = isstateland;
	}

	public String getIspeopledefence() {
		return ispeopledefence;
	}

	public void setIspeopledefence(String ispeopledefence) {
		this.ispeopledefence = ispeopledefence;
	}

	public String getIsprojectapprove() {
		return isprojectapprove;
	}

	public void setIsprojectapprove(String isprojectapprove) {
		this.isprojectapprove = isprojectapprove;
	}

	public String getIscheckapprove() {
		return ischeckapprove;
	}

	public void setIscheckapprove(String ischeckapprove) {
		this.ischeckapprove = ischeckapprove;
	}

	public String getIsbuildproject() {
		return isbuildproject;
	}

	public void setIsbuildproject(String isbuildproject) {
		this.isbuildproject = isbuildproject;
	}

	public String getIssimulateapprove() {
		return issimulateapprove;
	}

	public void setIssimulateapprove(String issimulateapprove) {
		this.issimulateapprove = issimulateapprove;
	}

	public String getIsenvironmentapprove() {
		return isenvironmentapprove;
	}

	public void setIsenvironmentapprove(String isenvironmentapprove) {
		this.isenvironmentapprove = isenvironmentapprove;
	}

	public String getIsculturalrelics() {
		return isculturalrelics;
	}

	public void setIsculturalrelics(String isculturalrelics) {
		this.isculturalrelics = isculturalrelics;
	}

	public String getIsweather() {
		return isweather;
	}

	public void setIsweather(String isweather) {
		this.isweather = isweather;
	}

	public String getIsantiknock() {
		return isantiknock;
	}

	public void setIsantiknock(String isantiknock) {
		this.isantiknock = isantiknock;
	}

	public String getIsenergyconservation() {
		return isenergyconservation;
	}

	public void setIsenergyconservation(String isenergyconservation) {
		this.isenergyconservation = isenergyconservation;
	}

	public String getIsparkafforest() {
		return isparkafforest;
	}

	public void setIsparkafforest(String isparkafforest) {
		this.isparkafforest = isparkafforest;
	}

	public String getIsfirecontrol() {
		return isfirecontrol;
	}

	public void setIsfirecontrol(String isfirecontrol) {
		this.isfirecontrol = isfirecontrol;
	}

	public String getIsbuildland() {
		return isbuildland;
	}

	public void setIsbuildland(String isbuildland) {
		this.isbuildland = isbuildland;
	}

	public String getIssimulatefinish() {
		return issimulatefinish;
	}

	public void setIssimulatefinish(String issimulatefinish) {
		this.issimulatefinish = issimulatefinish;
	}

	public String getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	public String getProjectFillStatus() {
		return projectFillStatus;
	}

	public void setProjectFillStatus(String projectFillStatus) {
		this.projectFillStatus = projectFillStatus;
	}
}
