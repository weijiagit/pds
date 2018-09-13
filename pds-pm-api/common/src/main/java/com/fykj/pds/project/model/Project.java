package com.fykj.pds.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fykj.kernel._c.model.AbstractEntity;


/**
 * Created by weijia on 2017/11/8.
 */
@Entity
@Table(name = "t_project")
public class Project extends AbstractEntity {

    /**
     *项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     *项目代码
     */
    @Column(name = "project_number")
    private String projectNumber;

    /**
     *建设单位
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     *组织机构代码
     */
    @Column(name = "organization_code")
    private String organizationCode;

    /**
     *总投资
     */
    @Column(name = "total_investment")
    private double totalInvestment;

    /**
     *建设内容和规模
     */
    @Column(name = "construction_content_scale")
    private String constructionContentScale;

    /**
     *项目属地(遵化店镇、皇台街道办事处、高新区、其他)
     */
    @Column(name = "project_attribute")
    private String projectAttribute;

    /**
     *建设性质(新建、扩建、改建、迁建、技术改造)
     */
    @Column(name = "construction_nature")
    private String constructionNature;

    /**
     * 计划开工日期
     */
    @Column(name = "plan_begin_date")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date planBeginDate;

    /**
     * 计划竣工日期
     */
    @Column(name = "plan_end_date")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date planEndDate;

    /**
     *企业法人
     */
    @Column(name = "enterprise_legal_person")
    private String enterpriseLegalPerson;

    /**
     *企业联系人
     */
    @Column(name = "enterprise_contact_person")
    private String enterpriseContactPerson;

    /**
     *企业联系人联系方式
     */
    @Column(name = "enterprise_contact_phone")
    private String enterpriseContactPhone;

    /**
     *企业负责人
     */
    @Column(name = "enterprise_charge_person")
    private String enterpriseChargePerson;


    /**
     *企业负责人联系方式
     */
    @Column(name = "enterprise_charge_phone")
    private String enterpriseChargePhone;

    /**
     *行业分类(工业、基础设施、服务业、PPP项目、房地产、医疗卫生、教育、农林水利、其他、预留1、预留2、预留3)
     */
    @Column(name = "industry_classification")
    private String industryClassification;

    /**
     *实际开工日期
     */
    @Column(name = "actual_begin_date")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date actualBeginDate;

    /**
     *实际竣工日期
     */
    @Column(name = "actual_end_date")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date actualEndDate;

    /**
     *行业分类是否ppp项目
     */
    @Column(name = "isppp_project")
    private String ispppProject;

    /**
     *实施进度分类(前期、新开工、续建、竣工)
     */
    @Column(name = "implement_schedule")
    private String implementSchedule;

    /**
     *是否计划新开工
     */
    @Column(name = "isplan_new_work")
    private String isplanNewWork;

    /**
     *是否计划竣工
     */
    @Column(name = "isplan_complete")
    private String isplanComplete;

    /**
     *分包领导
     */
    @Column(name = "subcontract_leader")
    private String subcontractLeader;

    /**
     *分包单位1
     */
    @Column(name = "subcontract_company1")
    private String subcontractCompany1;

    /**
     *分包单位1责任人
     */
    @Column(name = "subcontract_company1_person")
    private String subcontractCompany1Person;

    /**
     *分包单位1责任人联系方式
     */
    @Column(name = "subcontract_company1_person_phone")
    private String subcontractCompany1PersonPhone;

    /**
     *分包单位1联系人
     */
    @Column(name = "subcontract_company1_contact")
    private String subcontractCompany1Contact;

    /**
     *分包单位1联系人联系方式
     */
    @Column(name = "subcontract_company1_contact_phone")
    private String subcontractCompany1ContactPhone;

    /**
     *分包单位2
     */
    @Column(name = "subcontract_company2")
    private String subcontractCompany2;

    /**
     *分包单位2责任人
     */
    @Column(name = "subcontract_company2_person")
    private String subcontractCompany2Person;

    /**
     *分包单位2责任人联系方式
     */
    @Column(name = "subcontract_company2_person_phone")
    private String subcontractCompany2PersonPhone;

    /**
     *分包单位2联系人
     */
    @Column(name = "subcontract_company2_contact")
    private String subcontractCompany2Contact;

    /**
     *分包单位2联系人联系方式
     */
    @Column(name = "subcontract_company2_contact_phone")
    private String subcontractCompany2ContactPhone;

    /**
     *分包单位3
     */
    @Column(name = "subcontract_company3")
    private String subcontractCompany3;

    /**
     *分包单位3责任人
     */
    @Column(name = "subcontract_company3_person")
    private String subcontractCompany3Person;

    /**
     *分包单位3责任人联系方式
     */
    @Column(name = "subcontract_company3_person_phone")
    private String subcontractCompany3PersonPhone;

    /**
     *分包单位3联系人
     */
    @Column(name = "subcontract_company3_contact")
    private String subcontractCompany3Contact;

    /**
     *分包单位3联系人联系方式
     */
    @Column(name = "subcontract_company3_contact_phone")
    private String subcontractCompany3ContactPhone;

    /**
     *是否重点项目
     */
    @Column(name = "isimp")
    private String isimp;

    /**
     *是否省重点项目
     */
    @Column(name = "isprovinceimp")
    private String isprovinceimp;

    /**
     *是否市重点项目
     */
    @Column(name = "iscityimp")
    private String iscityimp;

    /**
     *是否区重点项目
     */
    @Column(name = "isdistrictimp")
    private String isdistrictimp;

    /**
     *是否存在问题
     */
    @Column(name = "isproblem")
    private String isproblem;

    /**
     *是否存在问题内容
     */
    @Column(name = "question_content")
    private String questionContent;

    /**
     *国有土地使用证是否完成
     */
    @Column(name = "isstateland")
    private String isstateland;

    /**
     *立项批文是否完成
     */
    @Column(name = "isprojectapprove")
    private String isprojectapprove;

    /**
     *建设工程规划许可证是否完成
     */
    @Column(name = "isbuildproject")
    private String isbuildproject;

    /**
     *建设用地规划许可证
     */
    @Column(name = "isbuildland")
    private String isbuildland;

    /**
     *选址意见书是否完成
     */
    @Column(name = "issiteselect")
    private String issiteselect;

    /**
     *人防批文是否完成
     */
    @Column(name = "ispeopledefence")
    private String ispeopledefence;

    /**
     *是否联审联批项目
     */
    @Column(name = "ischeckapprove")
    private String ischeckapprove;

    /**
     *是否模拟审批项目
     */
    @Column(name = "issimulateapprove")
    private String issimulateapprove;

    /**
     *环评批文是否完成
     */
    @Column(name = "isenvironmentapprove")
    private String isenvironmentapprove;

    /**
     *文物勘探是否完成
     */
    @Column(name = "isculturalrelics")
    private String isculturalrelics;

    /**
     *气象局手续是否完成
     */
    @Column(name = "isweather")
    private String isweather;

    /**
     *抗震设防是否完成
     */
    @Column(name = "isantiknock")
    private String isantiknock;

    /**
     *节能审批是否完成
     */
    @Column(name = "isenergyconservation")
    private String isenergyconservation;

    /**
     *园林绿化是否完成
     */
    @Column(name = "isparkafforest")
    private String isparkafforest;

    /**
     *消防是否完成
     */
    @Column(name = "isfirecontrol")
    private String isfirecontrol;

    /**
     *模拟审批是否完成
     */
    @Column(name = "issimulatefinish")
    private String issimulatefinish;

    /**
     *转结状态
     */
    @Column(name = "transfer_status")
    private String transferStatus;

    /**
     *项目填写状态
     */
    @Column(name = "project_fill_status")
    private String projectFillStatus;

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

    public double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getConstructionContentScale() {
        return constructionContentScale;
    }

    public void setConstructionContentScale(String constructionContentScale) {
        this.constructionContentScale = constructionContentScale;
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

    public Date getPlanBeginDate() {
        return planBeginDate;
    }

    public void setPlanBeginDate(Date planBeginDate) {
        this.planBeginDate = planBeginDate;
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
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

    public String getIndustryClassification() {
        return industryClassification;
    }

    public void setIndustryClassification(String industryClassification) {
        this.industryClassification = industryClassification;
    }

    public Date getActualBeginDate() {
        return actualBeginDate;
    }

    public void setActualBeginDate(Date actualBeginDate) {
        this.actualBeginDate = actualBeginDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getIspppProject() {
        return ispppProject;
    }

    public void setIspppProject(String ispppProject) {
        this.ispppProject = ispppProject;
    }

    public String getImplementSchedule() {
        return implementSchedule;
    }

    public void setImplementSchedule(String implementSchedule) {
        this.implementSchedule = implementSchedule;
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

    public String getIsimp() {
        return isimp;
    }

    public void setIsimp(String isimp) {
        this.isimp = isimp;
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

    public String getIsprojectapprove() {
        return isprojectapprove;
    }

    public void setIsprojectapprove(String isprojectapprove) {
        this.isprojectapprove = isprojectapprove;
    }

    public String getIsbuildproject() {
        return isbuildproject;
    }

    public void setIsbuildproject(String isbuildproject) {
        this.isbuildproject = isbuildproject;
    }

    public String getIsbuildland() {
        return isbuildland;
    }

    public void setIsbuildland(String isbuildland) {
        this.isbuildland = isbuildland;
    }

    public String getIssiteselect() {
        return issiteselect;
    }

    public void setIssiteselect(String issiteselect) {
        this.issiteselect = issiteselect;
    }

    public String getIspeopledefence() {
        return ispeopledefence;
    }

    public void setIspeopledefence(String ispeopledefence) {
        this.ispeopledefence = ispeopledefence;
    }

    public String getIscheckapprove() {
        return ischeckapprove;
    }

    public void setIscheckapprove(String ischeckapprove) {
        this.ischeckapprove = ischeckapprove;
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

    public String getIssimulatefinish() {
        return issimulatefinish;
    }

    public void setIssimulatefinish(String issimulatefinish) {
        this.issimulatefinish = issimulatefinish;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }
}
