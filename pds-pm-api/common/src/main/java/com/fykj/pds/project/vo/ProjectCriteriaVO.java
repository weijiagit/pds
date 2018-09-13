package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JInputModel;

import io.swagger.annotations.ApiModelProperty;

public class ProjectCriteriaVO extends ProjectInfo implements JInputModel {

    /**
     * 项目名称
     */
    @ApiModelProperty(value="项目名称")
    private String projectName;


    /**
     * 是否重点项目
     */
    private String importentProjectIds;

    /**
     * 实施进度
     */
    private String implementScheduleIds;

    /**
     * 行业分类
     */
    private String industryClassificationIds;

    /**
     * 项目填写进度
     */
    private String projectFillStatusIds;

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getImportentProjectIds() {
        return importentProjectIds;
    }

    public void setImportentProjectIds(String importentProjectIds) {
        this.importentProjectIds = importentProjectIds;
    }

    public String getImplementScheduleIds() {
        return implementScheduleIds;
    }

    public void setImplementScheduleIds(String implementScheduleIds) {
        this.implementScheduleIds = implementScheduleIds;
    }

    public String getIndustryClassificationIds() {
        return industryClassificationIds;
    }

    public void setIndustryClassificationIds(String industryClassificationIds) {
        this.industryClassificationIds = industryClassificationIds;
    }

    public String getProjectFillStatusIds() {
        return projectFillStatusIds;
    }

    public void setProjectFillStatusIds(String projectFillStatusIds) {
        this.projectFillStatusIds = projectFillStatusIds;
    }
}
