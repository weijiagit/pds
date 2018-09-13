package com.fykj.kernel.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:09
 * Description:
 **/
public class ExcelModelColumnWalkerImpl implements ExcelModelColumnWalker {

    private static final Logger LOGGER = LoggerFactory.getLogger(ColumnWalkerImpl.class);

    protected int i = 0;
    protected int columns = 0;
    protected List<Field> actualFields = new ArrayList<>();

    public ExcelModelColumnWalkerImpl(){
        Field[] fields = getClass().getDeclaredFields();
        columns = fields.length;
        for (int i = 0; i < columns; i++) {
            actualFields.add(fields[i]);
        }
    }

    @Override
    public void next(HSSFRow row) {
        LOGGER.debug("rowNum:[{}],cellNum:[{}]",row.getRowNum(),i);
        try {
            Field field = actualFields.get(i);
            HSSFCell cell = row.getCell(i);
            field.setAccessible(true);
            if(cell != null)
                field.set(this, cell.getStringCellValue().trim());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
        i++;
    }

    @Override
    public boolean hasNext(HSSFRow row) {
        LOGGER.debug("lastColumnNum:[{}]",columns);
        if (i >= columns) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validate(HSSFRow row) {
        HSSFCell cell = row.getCell(0);
        if(cell == null || cell.getStringCellValue() == null || "".equals(cell.getStringCellValue())){
            return false;
        }
        return true;
    }
}
