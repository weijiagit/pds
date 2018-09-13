package com.fykj.pds.project.model;

import com.fykj.kernel._c.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by weijia on 2017/11/13.
 */
@Entity
@Table(name = "t_project_attract")
public class AttractProject extends AbstractEntity {

    /**
     *拟项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     *项目简介
     */
    @Column(name = "project_describe")
    private String projectDescribe;

    /**
     *投资概况
     */
    @Column(name = "invest_survey")
    private String investSurvey;

    /**
     *征地
     */
    @Column(name = "land_acquisition")
    private String landAcquisition;

    /**
     *占地面积
     */
    @Column(name = "floor_space")
    private double floorSpace;

    /**
     *总投资
     */
    @Column(name = "total_investment")
    private double totalInvestment;

    /**
     *投资单位
     */
    @Column(name = "investment_company")
    private String investmentCompany;

    /**
     *公司全称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     *选址位置
     */
    @Column(name = "site_location")
    private String siteLocation;

    /**
     *项目负责人姓名
     */
    @Column(name = "charge_name")
    private String chargeName;

    /**
     *项目负责人电话
     */
    @Column(name = "charge_phone")
    private String chargePhone;

    /**
     *项目联系人姓名
     */
    @Column(name = "contact_name")
    private String contactName;

    /**
     *项目联系人电话
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     *招行管理系统ID
     */
    @Column(name = "special_number")
    private String specialNumber;

    /**
     *退回标志
     */
    @Column(name = "back_flag")
    private String backFlag;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescribe() {
        return projectDescribe;
    }

    public void setProjectDescribe(String projectDescribe) {
        this.projectDescribe = projectDescribe;
    }

    public String getInvestSurvey() {
        return investSurvey;
    }

    public void setInvestSurvey(String investSurvey) {
        this.investSurvey = investSurvey;
    }

    public String getLandAcquisition() {
        return landAcquisition;
    }

    public void setLandAcquisition(String landAcquisition) {
        this.landAcquisition = landAcquisition;
    }

    public double getFloorSpace() {
        return floorSpace;
    }

    public void setFloorSpace(double floorSpace) {
        this.floorSpace = floorSpace;
    }

    public double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getInvestmentCompany() {
        return investmentCompany;
    }

    public void setInvestmentCompany(String investmentCompany) {
        this.investmentCompany = investmentCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSiteLocation() {
        return siteLocation;
    }

    public void setSiteLocation(String siteLocation) {
        this.siteLocation = siteLocation;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getChargePhone() {
        return chargePhone;
    }

    public void setChargePhone(String chargePhone) {
        this.chargePhone = chargePhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSpecialNumber() {
        return specialNumber;
    }

    public void setSpecialNumber(String specialNumber) {
        this.specialNumber = specialNumber;
    }

    public String getBackFlag() {
        return backFlag;
    }

    public void setBackFlag(String backFlag) {
        this.backFlag = backFlag;
    }
}
