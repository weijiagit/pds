package com.fykj.pds.department.vo;

import com.fykj.kernel._c.model.JInputModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Author: songzhonglin
 * Date: 2017/11/8
 * Time: 13:47
 * Description:
 **/
public class DepartmentEditInVO implements JInputModel {
    @NotEmpty(message = "部门主键不允许为空")
    @NotNull(message = "部门主键不允许为空")
    private String id;

    @NotEmpty(message = "部门名称不允许为空")
    @NotNull(message = "部门名称不允许为空")
    @Length(max = 255, message = "部门名称最大长度支持255个字符")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
