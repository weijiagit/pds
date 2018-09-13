package com.fykj.pds.leaderMessage.vo;

import java.util.Date;

/**
 * Author: songzhonglin
 * Date: 2017/11/13
 * Time: 14:02
 * Description:
 **/
public class LeaderMessagePageOutVO {

    // 主键ID
    private String id;

    // 公告内容
    private String content;

    // 公告创建时间
    private Date createDate;

    private String createDateFormat;

    // 公告提交人
    private String userName;

    private String deptName;

    private String finishState;

    private String deptLeaderName;

    private String leaderId;

    private String deptId;

    private String userId;

    private String delOrUpdateFlag;

    public String getDelOrUpdateFlag() {
        return delOrUpdateFlag;
    }

    public void setDelOrUpdateFlag(String delOrUpdateFlag) {
        this.delOrUpdateFlag = delOrUpdateFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getFinishState() {
        return finishState;
    }

    public void setFinishState(String finishState) {
        this.finishState = finishState;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptLeaderName() {
        return deptLeaderName;
    }

    public void setDeptLeaderName(String deptLeaderName) {
        this.deptLeaderName = deptLeaderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateFormat() {
        return createDateFormat;
    }

    public void setCreateDateFormat(String createDateFormat) {
        this.createDateFormat = createDateFormat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
