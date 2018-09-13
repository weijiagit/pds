package com.fykj.pds.project.vo;

import com.fykj._b._core.attachment.vo.AttachmentInfo;
import com.fykj.kernel._c.model.JModel;
import com.fykj.pds.project.model.Project;
import com.fykj.pds.project.model.ProjectTableField;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


public class ProjectInfo extends Project implements JModel {

    // 公告附件
    private String attachmentIds;
    private String delImageIds;

    //附件列表
    private List<AttachmentInfo> attachmentInfoList;

    public String getDelImageIds() {
        return delImageIds;
    }

    public void setDelImageIds(String delImageIds) {
        this.delImageIds = delImageIds;
    }

    public List<AttachmentInfo> getAttachmentInfoList() {
        return attachmentInfoList;
    }

    public void setAttachmentInfoList(List<AttachmentInfo> attachmentInfoList) {
        this.attachmentInfoList = attachmentInfoList;
    }

    public String getAttachmentIds() {
        return attachmentIds;
    }

    public void setAttachmentIds(String attachmentIds) {
        this.attachmentIds = attachmentIds;
    }

}
