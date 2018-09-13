package com.fykj.pds.departProject.model;

import com.fykj.kernel._c.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;


/**
 * Author: weijia
 * Date: 2018/01/09
 **/
@Entity
@Table(name = "t_depart_project")
public class DepartProject extends AbstractEntity {

    /**
     *项目名称
     */
    @Column(name = "project_name")
    private String projectName;

    /**
     *建设单位
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     *建设内容和规模
     */
    @Column(name = "construction_content_scale")
    private String constructionContentScale;

    /**
     *总投资
     */
    @Column(name = "total_investment")
    private double totalInvestment;

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
     *填报部门ID
     */
    @Column(name = "depart_id")
    private String departId;

    /**
     *部门联系人id
     */
    @Column(name = "user_id")
    private String userId;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getConstructionContentScale() {
        return constructionContentScale;
    }

    public void setConstructionContentScale(String constructionContentScale) {
        this.constructionContentScale = constructionContentScale;
    }

    public double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
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

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
