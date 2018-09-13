
package com.fykj.pds.project.vo;

import com.fykj.kernel._c.model.JOutputModel;
import com.fykj.pds.project.model.ProjectDepartment;

import java.util.List;

public class ProjectFlowApproveInfo extends ProjectDepartment implements JOutputModel {

    /**
     *部门名称
     */
    private String imageText;

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }

}
