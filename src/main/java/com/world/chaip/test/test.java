package com.world.chaip.test;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: libai
 * @Date: 2019/2/22 10:41
 * @Version 1.0
 * @Description:
 */
public class test {
    public static void main(String[] args) throws Exception {
        FileInputStream fs = new FileInputStream("F:\\ASS.xls");  //获取d://test.xls
        POIFSFileSystem ps = new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
        HSSFWorkbook wb = new HSSFWorkbook(ps);
        HSSFSheet sheet = wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表
        HSSFRow row = sheet.getRow(0);  //获取第一行（excel中的行默认从0开始，所以这就是为什么，一个excel必须有字段列头），即，字段列头，便于赋值
        System.out.println(sheet.getLastRowNum() + " " + row.getLastCellNum());  //分别得到最后一行的行号，和一条记录的最后一个单元格

        FileOutputStream out = new FileOutputStream("F:\\ASS.xls");  //向d://test.xls中写数据
//        row=sheet.createRow((short)(sheet.getLastRowNum()+1)); //在现有行号后追加数据
//        row.createCell(0).setCellValue("leilei"); //设置第一个（从0开始）单元格的数据
//        row.createCell(1).setCellValue(24); //设置第二个（从0开始）单元格的数据


        //设置表头
        row = sheet.createRow((short) (0));
        Cell cellLast=row.createCell(0);
        cellLast.setCellValue("2020年水库蓄水量分析表");
        cellLast.setCellStyle(getTitleStyle(sheet.getWorkbook()));

        row = sheet.createRow((short) (2));
        row.createCell(0).setCellValue("2020");


        Object[] a1 = {0, 1, 2, 3, 4, 5, 6, 7};
        Object[] a2 = {"a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7"};
        Object[] a3 = {0, 1, 2, 3, 4, 5, 6, 7};
        Object[] a4 = {"a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7"};
        Object[] a5 = {0, 1, 2, 3, 4, 5, 6, 7};
        Object[] a6 = {"a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7"};
        Object[] a7 = {0, 1, 2, 3, 4, 5, 6, 7};
        Object[] a8 = {"a0", "a1", "a22222222222222222222222222", "a3", "a4", "a5", "a6", "a7"};
        List<Object[]> objectList = new ArrayList<>();
        objectList.add(a1);
        objectList.add(a2);
        objectList.add(a3);
        objectList.add(a4);
        objectList.add(a5);
        objectList.add(a6);
        objectList.add(a7);
        objectList.add(a8);
        for (int i = 0; i < objectList.size(); i++) {
            Object[] a = objectList.get(i);
            row = sheet.createRow((short) (sheet.getLastRowNum() + 1));
            for (int j = 0; j < a.length; j++) {
                row.createCell(j).setCellValue(a[j].toString());
            }

        }


        out.flush();
        wb.write(out);
        out.close();
        System.out.println(row.getPhysicalNumberOfCells() + " " + row.getLastCellNum());
    }



    /**
     * 标题头单元格样式
     */
    private static CellStyle getTitleStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(true);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
}