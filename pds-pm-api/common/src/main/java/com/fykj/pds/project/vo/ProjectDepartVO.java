
package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;

import java.util.List;

public class ProjectDepartVO extends ProjectDepartInfo implements JOutputModel {

    /**
     *项目名称
     */
    private String projectName;


    private List<ProjectDepartInfo> projectDepartInfoList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectDepartInfo> getProjectDepartInfoList() {
        return projectDepartInfoList;
    }

    public void setProjectDepartInfoList(List<ProjectDepartInfo> projectDepartInfoList) {
        this.projectDepartInfoList = projectDepartInfoList;
    }
}
