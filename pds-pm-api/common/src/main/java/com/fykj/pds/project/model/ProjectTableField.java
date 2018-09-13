package com.fykj.pds.project.model;
import com.fykj.kernel._c.model.AbstractEntity;

import javax.persistence.*;

/**
 * Created by weijia on 2017/11/24.
 */
@Entity
@Table(name = "t_project_table_field")
public class ProjectTableField extends AbstractEntity{

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "count")
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
