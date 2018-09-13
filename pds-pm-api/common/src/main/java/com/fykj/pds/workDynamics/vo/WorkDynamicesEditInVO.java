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
public class WorkDynamicesEditInVO implements JInputModel {

    @NotEmpty(message = "工作动态主键不允许为空")
    @NotNull(message = "工作动态主键不允许为空")
    @Length(max = 36, message = "工作动态主键最大长度支持36个字符")
    private String id;

    // 工作动态内容
    @NotEmpty(message = "工作动态内容不允许为空")
    @NotNull(message = "工作动态内容不允许为空")
    @Length(max = 255, message = "工作动态内容最大长度支持255个字符")
    private String content;

    // 部门ID
    private String departmentId;

    // 图片ID
    private String imageId;


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
}
