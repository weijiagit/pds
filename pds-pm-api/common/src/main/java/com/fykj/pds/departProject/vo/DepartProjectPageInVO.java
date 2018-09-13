package com.fykj.pds.departProject.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: weijia
 * Date: 2018/01/09
 **/
public class DepartProjectPageInVO implements JInputModel {

    // 填报部门名称
    private String departmentName;
    //填报部门ID
    private String departmentId;
    // 项目名称
    private String projectName;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
