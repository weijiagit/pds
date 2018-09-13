package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;

/**
 * weijia
 */
public class ProjectDepartmentHistoryRecordVO extends ProjectDepartmentHistoryInfo implements JOutputModel {

    //处理人名称
    private String leaderName;

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }
}
