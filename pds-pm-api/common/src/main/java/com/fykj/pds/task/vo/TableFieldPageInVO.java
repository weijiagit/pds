package com.fykj.pds.task.vo;

import com.fykj.kernel._c.model.JInputModel;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 15:44
 * Description:
 **/
public class TableFieldPageInVO implements JInputModel {

    // 选中
    private String selectedList;

    // 未选中
    private String unSelectedList;


    public String getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(String selectedList) {
        this.selectedList = selectedList;
    }

    public String getUnSelectedList() {
        return unSelectedList;
    }

    public void setUnSelectedList(String unSelectedList) {
        this.unSelectedList = unSelectedList;
    }
}
