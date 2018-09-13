package com.fykj.pds.task.vo;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 15:44
 * Description:
 **/
public class TableFieldPageOutVO {

    private String id;

    private String tableName;

    private String fieldName;

    private String isChk;

    public String getIsChk() {
        return isChk;
    }

    public void setIsChk(String isChk) {
        this.isChk = isChk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
