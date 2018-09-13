package com.fykj.pds.task.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 15:41
 * Description:
 **/
public class TaskPageInVO  implements JInputModel {

    private String id;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 责任单位
     */
    private String responsibleDepartment;

    private String responsibleDepartmentId;

    /**
     * 完成情况
     */
    private String isDepartFinish;

    /**
     * 批次
     */
    private String batch;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getResponsibleDepartmentId() {
        return responsibleDepartmentId;
    }

    public void setResponsibleDepartmentId(String responsibleDepartmentId) {
        this.responsibleDepartmentId = responsibleDepartmentId;
    }

    public String getResponsibleDepartment() {
        return responsibleDepartment;
    }

    public void setResponsibleDepartment(String responsibleDepartment) {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getIsDepartFinish() {
        return isDepartFinish;
    }

    public void setIsDepartFinish(String isDepartFinish) {
        this.isDepartFinish = isDepartFinish;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
