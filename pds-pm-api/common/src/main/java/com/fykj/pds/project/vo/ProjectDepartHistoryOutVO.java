package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;

import java.util.List;

/**
 * weijia
 */
public class ProjectDepartHistoryOutVO implements JOutputModel {

    /**
     *项目名称
     */
    private String projectName;

    private List<ProjectDepartInfo> projectDepartInfoOneList;
    private List<ProjectDepartInfo> projectDepartInfoTwoList;
    private List<ProjectDepartInfo> projectDepartInfoThreeList;
    private List<ProjectDepartInfo> projectDepartInfoFourList;
    private List<ProjectDepartInfo> projectDepartInfoFiveList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ProjectDepartInfo> getProjectDepartInfoOneList() {
        return projectDepartInfoOneList;
    }

    public void setProjectDepartInfoOneList(List<ProjectDepartInfo> projectDepartInfoOneList) {
        this.projectDepartInfoOneList = projectDepartInfoOneList;
    }

    public List<ProjectDepartInfo> getProjectDepartInfoTwoList() {
        return projectDepartInfoTwoList;
    }

    public void setProjectDepartInfoTwoList(List<ProjectDepartInfo> projectDepartInfoTwoList) {
        this.projectDepartInfoTwoList = projectDepartInfoTwoList;
    }

    public List<ProjectDepartInfo> getProjectDepartInfoThreeList() {
        return projectDepartInfoThreeList;
    }

    public void setProjectDepartInfoThreeList(List<ProjectDepartInfo> projectDepartInfoThreeList) {
        this.projectDepartInfoThreeList = projectDepartInfoThreeList;
    }

    public List<ProjectDepartInfo> getProjectDepartInfoFourList() {
        return projectDepartInfoFourList;
    }

    public void setProjectDepartInfoFourList(List<ProjectDepartInfo> projectDepartInfoFourList) {
        this.projectDepartInfoFourList = projectDepartInfoFourList;
    }

    public List<ProjectDepartInfo> getProjectDepartInfoFiveList() {
        return projectDepartInfoFiveList;
    }

    public void setProjectDepartInfoFiveList(List<ProjectDepartInfo> projectDepartInfoFiveList) {
        this.projectDepartInfoFiveList = projectDepartInfoFiveList;
    }

}
