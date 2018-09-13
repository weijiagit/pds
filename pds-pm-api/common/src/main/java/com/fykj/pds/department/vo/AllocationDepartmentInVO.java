package com.fykj.pds.department.vo;

import com.fykj.kernel._c.model.JInputModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Author: songzhonglin
 * Date: 2017/11/8
 * Time: 14:30
 * Description:
 **/
public class AllocationDepartmentInVO implements JInputModel {
    @NotEmpty(message = "用户ID不允许为空")
    @NotNull(message = "用户ID不允许为空")
    @Length(max = 36, message = "用户ID最大长度支持255个字符")
    private String userId;


    private String selected;

    private String undetermined;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getUndetermined() {
        return undetermined;
    }

    public void setUndetermined(String undetermined) {
        this.undetermined = undetermined;
    }
}
