package com.fykj.pds.work.personWork.model;

import com.fykj.kernel._c.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: songzhonglin
 * Date: 2017/12/1
 * Time: 13:58
 * Description:
 **/
@Entity
@Table(name = "t_person_work")
public class PersonWork extends AbstractEntity {

    // 个人任务
    @Column(name = "task_content")
    private String taskContent;
    // 任务时间
    @Column(name = "task_date")
    @Temporal(value= TemporalType.TIMESTAMP)
    private Date taskDate;
    // 状态
    @Column(name = "state")
    private String state;
    // 用户ID
    @Column(name = "user_id")
    private String userId;

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
