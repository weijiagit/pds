package com.fykj.pds.leaderMessage.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2017/11/13
 * Time: 14:02
 * Description:
 **/
public class LeaderMessagePageInVO implements JInputModel {

    private String content ;

    private String userName ;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
