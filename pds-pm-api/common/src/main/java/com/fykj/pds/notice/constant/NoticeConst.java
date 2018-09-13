package com.fykj.pds.notice.constant;

/**
 * Author: songzhonglin
 * Date: 2017/11/7
 * Time: 16:04
 * Description:
 **/
public enum NoticeConst {
    UNPUBLISHED("0"),// 未发布
    PUBLISHED("1"),// 已发布
    TOP_OFF("0"), // 未置顶
    TOP_ON("1"); // 已置顶

    private String val;

    private NoticeConst(String str){
        this.val=str;
    }

    public String getVal(){
        return val;
    }
}
