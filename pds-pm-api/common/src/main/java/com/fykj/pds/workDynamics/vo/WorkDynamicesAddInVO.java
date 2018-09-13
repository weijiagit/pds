package com.fykj.pds.workDynamics.vo;

import com.fykj.kernel._c.model.JInputModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Author: songzhonglin
 * Date: 2017/11/10
 * Time: 11:13
 * Description:
 **/
public class WorkDynamicesAddInVO implements JInputModel {

    // 工作动态部门名称
    @NotEmpty(message = "部门名称不允许为空")
    @NotNull(message = "部门名称不允许为空")
    @Length(max = 255, message = "部门名称最大长度支持255个字符")
    private String departmentName;

    private String departmentId;

    // 工作动态内容
    @NotEmpty(message = "工作动态内容不允许为空")
    @NotNull(message = "工作动态内容不允许为空")
    @Length(max = 255, message = "工作动态内容最大长度支持255个字符")
    private String content;

    // 图片ID
    private String imageId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
