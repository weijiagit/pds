package com.fykj.pds.notice.vo;

import com.fykj.kernel._c.model.JInputModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Author: songzhonglin
 * Date: 2017/11/7
 * Time: 14:57
 * Description:
 **/
public class NoticeAddInVO implements JInputModel {

    // 通知公告内容
    @NotEmpty(message = "通知公告内容不允许为空")
    @NotNull(message = "通知公告内容不允许为空")
    @Length(max = 255, message = "通知公告内容最大长度支持255个字符")
    private String content;

    // 用户ID
    private String userId;

    // 通知公告附件
    private String attachmentIds;

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
