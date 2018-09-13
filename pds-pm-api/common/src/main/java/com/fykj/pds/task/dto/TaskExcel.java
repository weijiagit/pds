package com.fykj.pds.task.dto;

import com.fykj.kernel._c.model.JModel;
import com.fykj.kernel.excel.ColumnWalker;
import com.fykj.kernel.excel.ColumnWalkerImpl;
import com.fykj.kernel.excel.ExcelColumn;
import com.fykj.kernel.excel.ExcelHead;
import com.fykj.pds.task.constant.TaskConstant;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:24
 * Description:
 **/
@ExcelHead("项目推进")
public class TaskExcel extends ColumnWalkerImpl implements JModel, ColumnWalker {

    /**
     * 项目名称
     */
    @ExcelColumn("项目名称")
    private String projectName;

    /**
     * 分包领导
     */
    @ExcelColumn("分包领导")
    private String subcontractLeader;
    /**
     * 工作任务
     */
    @ExcelColumn("工作任务")
    private String workContent;
    /**
     * 责任单位
     */
    @ExcelColumn("责任单位")
    private String responsibleDepartment;
    /**
     * 责任人
     */
    @ExcelColumn("责任人")
    private String responsiblePeople;
    /**
     * 时间节点
     */
    @ExcelColumn("时间节点")
    private String endDateStr;

    /**
     * 创建时间
     */
    @ExcelColumn("创建时间")
    private String createDateStr;


    @ExcelColumn("工程进度")
    private String progressWork;

    @ExcelColumn("批次")
    private String batch;

    /**
     * 完成情况
     */
    @ExcelColumn("完成情况")
    private String isDepartFinish;
    /**
     * 核查情况
     */
    @ExcelColumn("核查情况")
    private String isFinish;




    public TaskExcel(String projectName, String subcontractLeader, String workContent, String responsibleDepartment,
                     String responsiblePeople, String endDateStr, String createDateStr,String progressWork,String batch,
                     String isDepartFinish,
                     String isFinish) {
        this.projectName = projectName;
        this.subcontractLeader = subcontractLeader;
        this.workContent = workContent;
        this.responsibleDepartment = responsibleDepartment;
        this.responsiblePeople = responsiblePeople;
        this.endDateStr = endDateStr;
        this.createDateStr = createDateStr;
        this.progressWork = progressWork;
        this.batch = batch;
        this.isDepartFinish = isDepartFinish.equals(TaskConstant.TaskStatus.IS_DEPART_FINISH_OFF)
                ? TaskConstant.TaskName.IS_DEPART_FINISH_NAME_NO : TaskConstant.TaskName.IS_DEPART_FINISH_NAME_YES;
        this.isFinish = isFinish.equals(TaskConstant.TaskStatus.IS_FINISH_OFF) ? TaskConstant.TaskName.IS_FINISH_NAME_NO
                : TaskConstant.TaskName.IS_FINISH_NAME_YES;

    }

    public String getProgressWork() {
        return progressWork;
    }

    public void setProgressWork(String progressWork) {
        this.progressWork = progressWork;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSubcontractLeader() {
        return subcontractLeader;
    }

    public void setSubcontractLeader(String subcontractLeader) {
        this.subcontractLeader = subcontractLeader;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getResponsibleDepartment() {
        return responsibleDepartment;
    }

    public void setResponsibleDepartment(String responsibleDepartment) {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getResponsiblePeople() {
        return responsiblePeople;
    }

    public void setResponsiblePeople(String responsiblePeople) {
        this.responsiblePeople = responsiblePeople;
    }

    public String getIsDepartFinish() {
        return isDepartFinish;
    }

    public void setIsDepartFinish(String isDepartFinish) {
        this.isDepartFinish = isDepartFinish;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }


    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }


    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }
}
