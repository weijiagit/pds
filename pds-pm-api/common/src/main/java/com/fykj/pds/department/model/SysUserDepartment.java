package com.fykj.pds.department.model;

import com.fykj.kernel._c.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Author: songzhonglin
 * Date: 2017/11/8
 * Time: 13:54
 * Description:
 **/
@Entity
@Table(name = "t_sys_user_department")
public class SysUserDepartment extends AbstractEntity {

    @Column(name = "user_id")
    private  String userId;

    @Column(name = "department_id")
    private  String departmentId;

    @Column(name = "check_state")
    private  String checkState;

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
