package com.fykj.pds.workDynamics.vo;

import com.fykj._b._core.attachment.vo.AttachmentInfo;

import java.util.Date;
import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/10
 * Time: 11:25
 * Description:
 **/
public class WorkDynamicesPageOutVO {

    // 主键ID
    private String id;

    // 工作动态内容
    private String content;

    // 工作动态创建时间
    private Date createDate;

    private String createDateFormat;

    // 工作动态部门名称
    private String departmentName;

    private String departmentId;

    // 图片名称
    private String imgName;

    // 图片地址
    private String imgUrl;

    private List<AttachmentInfo> attachmentInfoList;

    public List<AttachmentInfo> getAttachmentInfoList() {
        return attachmentInfoList;
    }

    public void setAttachmentInfoList(List<AttachmentInfo> attachmentInfoList) {
        this.attachmentInfoList = attachmentInfoList;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
