package com.fykj.pds.leaderMessage.vo;

import com.fykj.kernel._c.model.JInputModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Author: songzhonglin
 * Date: 2017/11/13
 * Time: 14:02
 * Description:
 **/
public class LeaderMessageEditInVO implements JInputModel {

    private  String id;
    // 领导留言内容
    @NotEmpty(message = "领导留言内容不允许为空")
    @NotNull(message = "领导留言内容不允许为空")
    @Length(max = 255, message = "领导留言内容最大长度支持255个字符")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // 领导留言 留言人
    private String userName;

    // 部门Id
    private String deptId;

    // 领导Id
    private String leaderId;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
