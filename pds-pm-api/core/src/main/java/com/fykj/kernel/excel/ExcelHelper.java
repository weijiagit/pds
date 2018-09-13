package com.fykj.kernel.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: songzhonglin
 * Date: 2017/11/20
 * Time: 11:11
 * Description:
 **/
public class ExcelHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHelper.class);

    private static final int BUFFER_INITIALLY = 4000;

    public static ExcelModel readRecord(List<? extends ColumnWalker> list) {
        ExcelModel excelModel = new ExcelModel();
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("arguments list can not be null or empty");
        }
        Class<?> clazz = list.get(0).getClass();
        ExcelHead excelHead = clazz.getAnnotation(ExcelHead.class);

        Field[] fields = clazz.getDeclaredFields();
        ExcelColumn excelColumn = null;
        List<String> columns = new ArrayList<>();
        for (Field field : fields) {
            excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn != null) {
                columns.add(excelColumn.value());
            }
        }

        if (excelHead != null) {
            excelModel.setTitle(excelHead.value());
        }
        excelModel.setColumns(columns);
        excelModel.setRecordColumns(list);
        return excelModel;
    }

    /**
     * 写excel文件
     *
     * @param excelModel
     * @param sheetName
     * @return
     */
    public static byte[] excel2003(ExcelModel excelModel, String sheetName) {
        byte[] bytes = null;
        Workbook wb = null;
        ByteArrayOutputStream fileOut = null;


        int currentRowNumber = 0;
        try {
            wb = new HSSFWorkbook();
            //设置单元格居中
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            //粗体
            Font font = wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setFontHeightInPoints((short) 16);
            cellStyle.setFont(font);
            CreationHelper createHelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet(StringUtils.isNotEmpty(sheetName) ? sheetName : excelModel.getSheetName());

            CellStyle hederCellStyle = wb.createCellStyle();

            hederCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            hederCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            hederCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            hederCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            hederCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            hederCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            hederCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            // header font
            Font headerFont = wb.createFont();
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerFont.setFontHeightInPoints((short) 10);
            hederCellStyle.setFont(headerFont);

            CellStyle contentCellStyle = wb.createCellStyle();

            contentCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            contentCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            contentCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            contentCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            contentCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            contentCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            contentCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);


            String title = excelModel.getTitle();
            String detail = excelModel.getDetail();
            List<String> columns = excelModel.getColumns();
            int columnSize = columns.size();
            // 填充表头标题
            if (StringUtils.isNotBlank(title)) {
                Row rowHead = sheet.createRow(currentRowNumber);
                Cell cell = rowHead.createCell(0);
                cell.setCellValue(createHelper.createRichTextString(excelModel.getTitle()));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnSize - 1));
                cell.setCellStyle(cellStyle);
                currentRowNumber++;
            }
            // 填充表头header
//            if (StringUtils.isNoneBlank(detail)) {
//                Row rowHead = sheet.createRow(currentRowNumber);
//                Cell cell = rowHead.createCell(0);
//                cell.setCellValue(createHelper.createRichTextString(excelModel.getDetail()));
//                sheet.addMergedRegion(new CellRangeAddress(currentRowNumber, currentRowNumber, 0, columnSize - 1));
//                cell.setCellStyle(hederCellStyle);
//                currentRowNumber++;
//            }

            if (!columns.isEmpty()) {
                Row rowColumns = sheet.createRow(currentRowNumber);
                Cell cell = null;
                for (int i = 0; i < columnSize; i++) {
                    cell = rowColumns.createCell(i);
                    cell.setCellValue(createHelper.createRichTextString(columns.get(i)));
                    cell.setCellStyle(hederCellStyle);
                    sheet.setColumnWidth(i, 100 * 60);
                }
                currentRowNumber++;
            }
            // 填充表格内容
            List<ColumnWalker> records = (List<ColumnWalker>) excelModel.getRecordColumns();
            if (!columns.isEmpty()) {
                for (ColumnWalker columnWalker : records) {
                    Row rowRecords = sheet.createRow(currentRowNumber);
                    Cell cell = null;
                    for (int i = 0; i < columnSize; i++) {
                        cell = rowRecords.createCell(i);
                        cell.setCellValue(columnWalker.next());
                        cell.setCellStyle(contentCellStyle);
                    }
                    currentRowNumber++;
                }
            }

            fileOut = new ByteArrayOutputStream(BUFFER_INITIALLY);
            wb.write(fileOut);
            bytes = fileOut.toByteArray();
            fileOut.flush();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fileOut);
            IOUtils.closeQuietly(wb);
        }
        return bytes;
    }

    /**
     * excel文件弹框
     *
     * @param response
     * @param bytes
     * @param fileName
     */
    public void excelHelper(HttpServletResponse response, byte[] bytes, String fileName) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new ByteArrayInputStream(bytes));
            byte[] buffer = new byte[4000];//一次读取4000个字节
            response.reset();//清除首部的空白行
            response.addHeader("Content-Length", Integer.toString(bytes.length));
            os = new BufferedOutputStream(response.getOutputStream());
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/vnd.ms-excel");
            while (is.read(buffer) > 0) {
                os.write(buffer);
                os.flush();
            }
        } catch (Exception e) {
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }
}
