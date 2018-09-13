package com.fykj.pds.work.personWork.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2017/12/1
 * Time: 13:54
 * Description:
 **/
public class PersonWorkPageInVO implements JInputModel {

    // 个人任务
    private String taskContent;

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }
}
