package com.fykj.pds.department.vo;

import com.fykj.kernel._c.model.JInputModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Author: songzhonglin
 * Date: 2017/11/8
 * Time: 13:51
 * Description:
 **/
public class DepartmentSaveInVO implements JInputModel {

    @NotEmpty(message = "部门名称不允许为空")
    @NotNull(message = "部门名称不允许为空")
    @Length(max = 255, message = "部门名称最大长度支持255个字符")
    private String name;

    @NotNull(message = "部门父节点不允许为空")
    @NotEmpty(message = "部门父节点不允许为空")
    private String pid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
