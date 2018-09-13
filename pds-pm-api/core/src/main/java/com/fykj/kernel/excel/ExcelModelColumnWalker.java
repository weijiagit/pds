package com.fykj.kernel.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:07
 * Description:
 * excel中说有单元格的值都为文本类型
 * 从第二行开始读
 **/
public interface ExcelModelColumnWalker {

    /**
     * 下一列的值
     * @return
     */
    void next(HSSFRow row);
    /**
     * 是否还需要读取下一列
     * @return
     */
    boolean hasNext(HSSFRow row);

    /**
     * 判断当前行是否合法
     * @param row
     * @return
     */
    boolean validate(HSSFRow row);
}
