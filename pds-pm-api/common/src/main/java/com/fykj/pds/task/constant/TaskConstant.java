package com.fykj.pds.task.constant;

/**
 * Author: songzhonglin
 * Date: 2017/11/17
 * Time: 9:34
 * Description:
 **/
public interface TaskConstant {

    interface TaskStatus {

        String CODE_TYPE = "TASK_STATUS";

        // 核查情况
        String IS_FINISH_OFF = "0";
        String IS_FINISH_ON = "1";
        // 完成情况
        String IS_DEPART_FINISH_OFF = "0";
        String IS_DEPART_FINISH_ON = "1";
        //
        String STATE_OFF = "0";

        String STATE_ON = "1";

        // 是否被选中
        String IS_CHK_OFF = "0";

        String IS_CHK_ON = "1";

        String SPIRT = "/" ;
    }

    interface TaskName {

        String CODE_TYPE = "TASK_NAME";

        String DEPARTMENT_NAME = "部门领导";

        String IS_DEPART_FINISH_NAME_NO = "否";
        String IS_DEPART_FINISH_NAME_YES = "是";

        String IS_FINISH_NAME_NO = "否";

        String IS_FINISH_NAME_YES = "是";

        String SHEET_NAME = "工作进展";

        String FILE_NAME = "工作进展";

        String SUFFIX = ".xls";

        String FILE_EXPORT_SIZE = "Excel文件导出失败，原因：查询列表无数据！";

        String PROJECT_NAME = "project_name";

        String RESPONSIBLE_DEPARTMENT = "responsible_department";

        String RESPONSIBLE_LEADER = "responsible_leader";

        String RESPONSIBLE_PEOPLE = "responsible_people";

        String STATE = "state";

        String SUBCONTRACT_LEADER = "subcontract_leader";

        String WORK_CONTENT = "work_content";

        String IS_FINISH = "is_finish";

        String IS_DEPART_FINISH = "is_depart_finish";

        String END_DATE = "end_date";

        String CREATE_DATE = "create_date";

        String PROJECT_CODE = "project_code";

        String BATCH = "batch";

    }
}
