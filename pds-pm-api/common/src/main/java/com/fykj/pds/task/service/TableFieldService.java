package com.fykj.pds.task.service;

import com.fykj.pds.task.vo.TableFieldPageInVO;
import com.fykj.pds.task.vo.TableFieldPageOutVO;

import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/16
 * Time: 16:05
 * Description:
 **/
public interface TableFieldService {

    /**
     * 查询表字段
     *
     * @return
     */

    public List<TableFieldPageOutVO> queryTableFieldList();

    /**
     * 首页选择显示列
     *
     * @param pageTableField
     * @return
     */
    public void addTableField(TableFieldPageInVO pageTableField);

    /**
     * 显示表字段
     * @return
     */
    public List<TableFieldPageOutVO> showTableFieldList();

}
