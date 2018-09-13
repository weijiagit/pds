
package com.fykj._b._core.departGovernment.vo;

import com.fykj.kernel._c.model.JOutputModel;

public class DepartGovernmentPageOutVO extends DepartGovernmentInfo implements JOutputModel {

    //部门名称
    private String departName;

    //是否显示
    private String showPicStr;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getShowPicStr() {
        return showPicStr;
    }

    public void setShowPicStr(String showPicStr) {
        this.showPicStr = showPicStr;
    }
}
