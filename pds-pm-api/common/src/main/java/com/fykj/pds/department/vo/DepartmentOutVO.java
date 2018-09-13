package com.fykj.pds.department.vo;

import com.fykj.kernel._c.model.JOutputModel;

/**
 * Author: songzhonglin
 * Date: 2017/11/8
 * Time: 14:17
 * Description:
 **/
public class DepartmentOutVO implements JOutputModel {

    private String id;

    private String pid;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
