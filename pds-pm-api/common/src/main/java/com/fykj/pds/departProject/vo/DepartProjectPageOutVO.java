
package com.fykj.pds.departProject.vo;

import com.fykj.kernel._c.model.JOutputModel;
import com.fykj.pds.departProject.model.DepartProject;

/**
 * Author: weijia
 * Date: 2018/01/09
 **/
public class DepartProjectPageOutVO extends DepartProject implements JOutputModel {

    /**
     * 计划开工日期
     */
    private String planBeginDateStr;

    /**
     * 计划竣工日期
     */
    private String planEndDateStr;

    /**
     *填报部门
     */
    private String departName;

    /**
     *部门联系人
     */
    private String userName;

    /**
     *修改删除标志
     */
    private String delOrUpdateFlag;

    public String getDelOrUpdateFlag() {
        return delOrUpdateFlag;
    }

    public void setDelOrUpdateFlag(String delOrUpdateFlag) {
        this.delOrUpdateFlag = delOrUpdateFlag;
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

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
