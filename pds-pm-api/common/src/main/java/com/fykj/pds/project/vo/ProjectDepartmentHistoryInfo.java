package com.fykj.pds.project.vo;

import com.fykj._b._core.attachment.vo.AttachmentInfo;
import com.fykj.kernel._c.model.JModel;
import com.fykj.pds.project.model.ProjectDepartmentHistory;

import java.util.List;

/**
 * weijia
 */
public class ProjectDepartmentHistoryInfo extends ProjectDepartmentHistory implements JModel {

    //姓名
    private String name;

    //附件列表
    private List<AttachmentInfo> attachmentInfoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttachmentInfo> getAttachmentInfoList() {
        return attachmentInfoList;
    }

    public void setAttachmentInfoList(List<AttachmentInfo> attachmentInfoList) {
        this.attachmentInfoList = attachmentInfoList;
    }
}
