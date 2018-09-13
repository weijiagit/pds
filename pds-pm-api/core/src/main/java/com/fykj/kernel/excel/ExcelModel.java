package com.fykj.kernel.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:06
 * Description:
 **/
public class ExcelModel {

    private String sheetName = "sheet1";
    private String title = "";
    private List<String> columns = new ArrayList<>();
    private List<? extends ColumnWalker> recordColumns = new ArrayList<>();
    private String detail;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
    @SuppressWarnings("unchecked")
    public List<? extends ColumnWalker> getRecordColumns() {
        return (List<ColumnWalker>)recordColumns;
    }

    public void setRecordColumns(List<? extends ColumnWalker> recordColumns) {
        this.recordColumns = recordColumns;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
