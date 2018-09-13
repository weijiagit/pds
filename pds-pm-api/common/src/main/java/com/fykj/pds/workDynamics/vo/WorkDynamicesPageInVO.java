package com.fykj.pds.workDynamics.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2017/11/10
 * Time: 11:23
 * Description:
 **/
public class WorkDynamicesPageInVO implements JInputModel {

    // 工作动态部门名称
    private String departmentName;

    private String departmentId;
    // 工作动态内容
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
