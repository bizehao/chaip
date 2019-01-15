package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExecls;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2019/1/10 21:22
 */
@RestController
@RequestMapping("test")
public class Test {

    @GetMapping("excel")
    public void excel(HttpServletResponse response) {
        System.out.println("测试成功");
        List<Object[]> datas = new ArrayList<>();
        Object[] objects;
        for (int i = 0; i < 80; i++) {
            objects = new Object[4];
            objects[0] = i;
            objects[1] = "测试" + i;
            objects[2] = i;
            if (i % 2 == 0) {
                objects[3] = "男";
            } else {
                objects[3] = "女";
            }
            datas.add(objects);
        }

        ExportExecls exportExecls = new ExportExecls(response, "学生表", datas, "2018年", 40, 4, 4,ExportExecls.Direction.TRANSVERSE);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                Cell colTitle0 = colTitleRow.createCell(0);
                colTitle0.setCellValue("编号");
                colTitle0.setCellStyle(style);

                Cell colTitle1 = colTitleRow.createCell(1);
                colTitle1.setCellValue("姓名");
                colTitle1.setCellStyle(style);

                Cell colTitle2 = colTitleRow.createCell(2);
                colTitle2.setCellValue("年龄");
                colTitle2.setCellStyle(style);

                Cell colTitle3 = colTitleRow.createCell(3);
                colTitle3.setCellValue("性别");
                colTitle3.setCellStyle(style);
            }
        });
    }

    @GetMapping("excellll")
    public void show(HttpServletResponse response) {
        List<Object[]> datas = new ArrayList<>();
        Object[] objects;
        for (int i = 0; i < 80; i++) {
            objects = new Object[4];
            objects[0] = i;
            objects[1] = "测试" + i;
            objects[2] = i;
            if (i % 2 == 0) {
                objects[3] = "男";
            } else {
                objects[3] = "女";
            }
            datas.add(objects);
        }
        ExportExecls exportExecls = new ExportExecls(response, "test", datas, "2018年", 40, 5,4, ExportExecls.Direction.TRANSVERSE);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {

                String[] rowsName = new String[]{"水库名称", "总库容(百万m³)", "汛期(1)", "", "目前实际", "", "", "", ""};

                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);

                for (int i=0; i<rowsName.length;i++){
                    Cell colTitle0 = colTitleRow.createCell(i);
                    colTitle0.setCellValue(rowsName[i]);
                    colTitle0.setCellStyle(style);
                }
//                Cell colTitle0 = colTitleRow.createCell(0);
//                colTitle0.setCellValue("水库名称");
//                colTitle0.setCellStyle(style);
//
//                Cell colTitle1 = colTitleRow.createCell(1);
//                colTitle1.setCellValue("总库容(百万m³)");
//                colTitle1.setCellStyle(style);
//
//                Cell colTitle2 = colTitleRow.createCell(2);
//                colTitle2.setCellValue("汛期(1)");
//                colTitle2.setCellStyle(style);
//
//                Cell colTitle3 = colTitleRow.createCell(3);
//                colTitle3.setCellValue("");
//                colTitle3.setCellStyle(style);
//
//                Cell colTitle4 = colTitleRow.createCell(4);
//                colTitle4.setCellValue("目前实际");
//                colTitle4.setCellStyle(style);
//
//                Cell colTitle5 = colTitleRow.createCell(5);
//                colTitle5.setCellValue("");
//                colTitle5.setCellStyle(style);
//
//                Cell colTitle6 = colTitleRow.createCell(6);
//                colTitle6.setCellValue("");
//                colTitle6.setCellStyle(style);
//
//                Cell colTitle7 = colTitleRow.createCell(7);
//                colTitle7.setCellValue("");
//                colTitle7.setCellStyle(style);
//
//                Cell colTitle8 = colTitleRow.createCell(8);
//                colTitle8.setCellValue("");
//                colTitle8.setCellStyle(style);


                String[] shuangName = new String[]{"", "", "水位(m)", "库容(百万m³)", "水位(m)", "蓄水量(百万m³)", "入库流量(m³/s)", "下泄流量(m³/s)", "数据时间"};
                Row colTitleRow1 = sheet.createRow(4);

                for (int i=0; i<shuangName.length;i++){
                    Cell colTitle = colTitleRow1.createCell(i);
                    colTitle.setCellValue(shuangName[i]);
                    colTitle.setCellStyle(style);

                }
//                Cell colTitle00 = colTitleRow1.createCell(0);
//                colTitle00.setCellValue("");
//                colTitle00.setCellStyle(style);
//
//                Cell colTitle01 = colTitleRow1.createCell(1);
//                colTitle01.setCellValue("");
//                colTitle01.setCellStyle(style);
//
//                Cell colTitle02 = colTitleRow1.createCell(2);
//                colTitle02.setCellValue("水位(m)");
//                colTitle02.setCellStyle(style);
//
//                Cell colTitle03 = colTitleRow1.createCell(3);
//                colTitle03.setCellValue("库容(百万m³)");
//                colTitle03.setCellStyle(style);
//
//                Cell colTitle04 = colTitleRow1.createCell(4);
//                colTitle04.setCellValue("水位(m)");
//                colTitle04.setCellStyle(style);
//
//                Cell colTitle05 = colTitleRow1.createCell(5);
//                colTitle05.setCellValue("蓄水量(百万m³)");
//                colTitle05.setCellStyle(style);
//
//                Cell colTitle06 = colTitleRow1.createCell(6);
//                colTitle06.setCellValue("入库流量(m³/s)");
//                colTitle06.setCellStyle(style);
//
//                Cell colTitle07 = colTitleRow1.createCell(7);
//                colTitle07.setCellValue("下泄流量(m³/s)");
//                colTitle07.setCellStyle(style);
//
//                Cell colTitle08 = colTitleRow1.createCell(8);
//                colTitle08.setCellValue("数据时间");
//                colTitle08.setCellStyle(style);
                CellRangeAddress titleAddress;
                titleAddress= new CellRangeAddress(3, 4, 0, 0);
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 4, 1, 1);
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 3, 2, 3);
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 3, 4, 8);
                sheet.addMergedRegion(titleAddress);

            }
        });

    }

    class Student {
        private int id;
        private String name;
        private int age;
        private String descript;

        public Student(int id, String name, int age, String descript) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.descript = descript;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDescript() {
            return descript;
        }

        public void setDescript(String descript) {
            this.descript = descript;
        }
    }
}
