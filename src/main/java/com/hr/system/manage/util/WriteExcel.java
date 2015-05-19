package com.hr.system.manage.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteExcel {

    /**
     * 写2007
     * @throws IOException
     */
    public static void create2007Excel(Map<String,String> keyValue,List<String> titleList,List<Map<String,String>> dataList,ByteArrayOutputStream os) throws IOException{
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet sheet = workBook.createSheet();//创建一个工作薄对象
        sheet.setColumnWidth(1,10000);//设置第二列的宽度
        XSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(23);//设置行高23像素
        XSSFCellStyle style = workBook.createCellStyle();//创建样式对象
        //设置字体
        XSSFFont font = workBook.createFont();//创建字体对象
        font.setFontHeightInPoints((short)15);//设置字体大小
        //	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//设置粗体
        font.setFontName("黑体");
//        style.setFont(font); //将字体缴入到样式对象
        //设置对齐方式
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);//水平居中
//        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //设置边框
//		style.setBorderTop(HSSFCellStyle.BORDER_THICK);//顶部边框粗线
        //	style.setTopBorderColor(HSSFColor.RED.index);//设置为红色
        //	style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);//底部边框双线
        //	style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);//左边框
        //	style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);//右边框
        //格式化日期
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        int titleI = 0;
        for(String title:titleList){
            XSSFCell cell = row.createCell(titleI);//创建单元格
            cell.setCellValue(keyValue.get(title));
            titleI++;
        }
        int dataRow = 1;
        for(Map<String,String> data:dataList){
            XSSFRow rowTemp = sheet.createRow(dataRow);
            int columnIndex = 0;
            for(String key:titleList){
                XSSFCell cell = rowTemp.createCell(columnIndex);//创建单元格
                cell.setCellValue(data.get(key));
                columnIndex++;
            }
            dataRow++;
        }
//        cell.setCellValue(new Date());
//        cell.setCellStyle(style);
//        FileOutputStream os = new FileOutputStream("C://style_2007.xlsx");
        workBook.write(os);
    }

//    public static void main(String[] args) {
//        try {
//            List<String> title = new ArrayList<String>();
//            title.add("name");
//            title.add("age");
//            List<Map<String,String>> data = new ArrayList<Map<String, String>>();
//            Map<String,String> map1 = new HashMap<String, String>();
//            map1.put("name","csx");
//            map1.put("age", "12");
//            data.add(map1);
//
//            Map<String,String> map2 = new HashMap<String, String>();
//            map2.put("name","csx1");
//            map2.put("age","121");
//            data.add(map2);
//
////            create2007Excel(title, data);
////            create2003Excel();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
