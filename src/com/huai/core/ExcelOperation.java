package com.huai.core;

import com.huai.utils.MyDateFormat;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liangyihuai on 15-11-26.
 */
public class ExcelOperation {

    /**
     * 导入excel表中的数据，并将这些数据存放到一个二位数组中
     *
     * @param filePath
     *            包含文件名
     * @param sheetIndex
     *            excel 表中sheet的位置 默认从0开始
     * @return 带有excel表中的数据的二维数组
     * @author LiangYiHuai
     */
    public List<ArrayList<String>> importForm(String filePath, int sheetIndex) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

        Workbook wb = null;
        try {
            InputStream inp = new FileInputStream(filePath);
            wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(sheetIndex);

            int firstRowNum = sheet.getFirstRowNum();// 表格中开始有数据的行的索引
            Row biginRow = sheet.getRow(firstRowNum);// 表哥中开始有数据的行
            int lastRowNum = sheet.getLastRowNum();// 表格中最后一行的索引
            int firstColNum = biginRow.getFirstCellNum();// 表格中开始有数据的第一列的索引
            int colNum = biginRow.getLastCellNum() - firstColNum;// 表格中数据的最后一列减去第一列

            if (colNum > 1) {
                for (int i = sheet.getFirstRowNum(); i < lastRowNum + 1; i++) {
                    ArrayList<String> tempList = new ArrayList<String>();
                    Row tempRow = sheet.getRow(i);

                    for (int k = firstColNum; k < colNum; k++) {
                        Cell tempCell = tempRow.getCell(k,
                                Row.CREATE_NULL_AS_BLANK);

                        /**
                         * switch，用来判断excel单元格中的数据是什么格式的 然后采用相应的方法来读取，否则会抛出异常
                         */
                        switch (tempCell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                tempList.add(tempCell.getRichStringCellValue()
                                        .getString().trim());
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (DateUtil.isCellDateFormatted(tempCell)) {
                                    // 这里为日期格式化成字符串
                                    Date date = tempCell.getDateCellValue();
                                    String dateString = MyDateFormat.changeDateToString(date);
                                    tempList.add(dateString);
                                } else {
                                    tempCell.setCellType(Cell.CELL_TYPE_STRING);
                                    String tempString = tempCell
                                            .getStringCellValue().trim();
                                    if (tempString.indexOf(".") > -1) {
                                        tempList.add(String.valueOf(
                                                new Double(tempString)).trim());
                                    } else {
                                        tempList.add(tempString);
                                    }
                                }
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                tempList.add(String.valueOf(tempCell
                                        .getBooleanCellValue()));
                                break;
                            case Cell.CELL_TYPE_FORMULA:
                                tempCell.setCellType(Cell.CELL_TYPE_STRING);
                                String tempString = tempCell.getStringCellValue()
                                        .trim();
                                if (tempString != null) {
                                    tempString.replaceAll("#N/A", "").trim();
                                    tempList.add(tempString);
                                }
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                tempList.add("");
                                break;
                            default:
                                tempList.add("");
                        }

                    }
                    list.add(tempList);
                }
            }

        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if(wb != null)wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
