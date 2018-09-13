package com.fykj.pds.notice.vo;

import com.fykj._b._core.Constants;
import com.fykj._b._core.attachment.vo.AttachmentInfo;

import java.util.Date;
import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/7
 * Time: 14:58
 * Description:
 **/
public class NoticePageOutVO {

    // 主键ID
    private String id;

    // 公告内容
    private String content;

    // 公告创建时间
    private Date createDate;

    private String createDateFormat;

    // 公告提交人
    private String userName;

    // 置顶
    private String isTop;

    // 发布状态
    private String state;


    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

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

    private List<AttachmentInfo> attachmentInfoList;

    public List<AttachmentInfo> getAttachmentInfoList() {
        return attachmentInfoList;
    }

    public void setAttachmentInfoList(List<AttachmentInfo> attachmentInfoList) {
        this.attachmentInfoList = attachmentInfoList;
    }
}
