package com.fykj.pds.work.backlogWork.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2017/12/7
 * Time: 16:02
 * Description:
 **/
public class BacklogWorkPageInVO implements JInputModel {

    // 状态
    private String state;

    private String id;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
