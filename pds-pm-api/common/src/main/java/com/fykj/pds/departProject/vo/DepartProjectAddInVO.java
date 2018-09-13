package com.fykj.pds.departProject.vo;

import com.fykj.kernel._c.model.JInputModel;
import com.fykj.pds.departProject.model.DepartProject;

/**
 * Author: weijia
 * Date: 2018/01/09
 **/
public class DepartProjectAddInVO extends DepartProject implements JInputModel {
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
}
