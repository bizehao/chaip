package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExcel;
import com.world.chaip.business.ExportExecls;
import com.world.chaip.business.StaticConfig;
import com.world.chaip.entity.Exchange.*;
import com.world.chaip.service.RsvrAnalysisService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/rsvrfallexcel")
public class RsvrAnalysisExcelController {

    private String ip = StaticConfig.ipAddress;

    @Autowired
    private RsvrAnalysisService rsvrAnalysisService;

    //水库水量分析表
    @GetMapping("getrsvrexchangewaterexcel")
    public void GetRsvrByAnalysiswaterExcel(
            HttpServletResponse response,
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
            @RequestParam(name="ly",required = false)String ly) {


        /*String dateStart = "2018-07-28";
        String dateEnd = "2018-08-01";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";
        String ly = "X";*/

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

        System.out.println("开始时间" + dateStart);
        System.out.println("结束时间" + dateEnd);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }

        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RsvrWaterExcel a = rsvrAnalysisService.getRsvrWaterAnalysis(dateS, dateE, adcdlist, typelist, stcdlist, lylist);
        List<Object[]> dataList = new ArrayList<>();
        Object[] objects = null;
        RsvrWaterExchange waterExchange = null;
        int k = 0;
        for (int i = 0; i < a.getRsvrWCList().size(); i++) {
            RsvrWaterExcel.RsvrWC rsvrWC = a.getRsvrWCList().get(i);
            ;
            for (int j = 0; j < rsvrWC.getrList().size(); j++) {
                objects = new Object[11];
                objects[0] = ++k;
                if (j == 0) {
                    objects[1] = rsvrWC.getName();
                } else {
                    objects[1] = "";
                }
                waterExchange = rsvrWC.getrList().get(j);
                objects[2] = waterExchange.getStnm();
                objects[3] = waterExchange.getqRZs();
                objects[4] = waterExchange.getqWs();
                objects[5] = waterExchange.gethRZs();
                objects[6] = waterExchange.gethWs();
                objects[7] = waterExchange.getChaW();
                objects[8] = waterExchange.getAvotqs();
                objects[9] = waterExchange.getSumotqs();
                objects[10] = waterExchange.getSuminqs();
                dataList.add(objects);
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        int beginMonth = tm.get(Calendar.MONTH) + 1;
        tm.setTime(dateE);
        int endMonth = tm.get(Calendar.MONTH) + 1;
        System.out.println(dateS + "" + dateE);
        String time = formatter.format(dateS) + "年" + beginMonth + "-" + endMonth + "月";
        String title = formatter.format(dateS) + "年" + beginMonth + "-" + endMonth + "月" + "中小型水库水量计算表";
        Calendar updateTm = Calendar.getInstance();
        updateTm.setTime(dateS);
        String beginTm = (updateTm.get(Calendar.MONTH) + 1) + "月" + updateTm.get(Calendar.DATE) + "日";
        updateTm.setTime(dateE);
        String endTm = (updateTm.get(Calendar.MONTH) + 1) + "月" + updateTm.get(Calendar.DATE) + "日";
        /*String endtm = "";
        if(endMonth==12){
            endtm = endMonth+"月31日";
        }else{
            endtm = (endMonth+1)+"月1日";
        }*/


        //列头单元格合并
        //序号

        ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 41, 5,11, ExportExecls.Direction.TRANSVERSE);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {

                String[] rowsName = new String[]{"序号", "系统", "库名", beginTm, "", endTm, "", "蓄水量差(m³)", "出库平均流量(m³/s)", "出库总量(百万m³)", "入库总量(百万m³)"};

                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                for (int i = 0; i < rowsName.length; i++) {
                    Cell colTitle0 = colTitleRow.createCell(i);
                    colTitle0.setCellValue(rowsName[i]);
                    colTitle0.setCellStyle(style);
                }
                String[] shuangName = new String[]{"", "", "", "水位(m)", "蓄水量(m³)", "水位(m)", "蓄水量(m³)", "", "", "", ""};
                Row colTitleRow1 = sheet.createRow(4);
                for (int i = 0; i < shuangName.length; i++) {
                    Cell colTitle = colTitleRow1.createCell(i);
                    colTitle.setCellValue(shuangName[i]);
                    colTitle.setCellStyle(style);

                }
                CellRangeAddress titleAddress;

                titleAddress = new CellRangeAddress(3, 4, 0, 0);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 4, 1, 1);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 4, 2, 2);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 3, 3, 4);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 3, 5, 6);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 4, 7, 7);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 4, 8, 8);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 4, 9, 9);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 4, 10, 10);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(titleAddress);

                int x = ExportExecls.HEIGHT / 10;
                for (int i = 0; i < 8; i++) {
                    sheet.setColumnWidth(i, x);
                }
            }
        });

    }

    @GetMapping(value = "getrsvrexchangewaterexcelfw")
    public JsonResult GetRsvrByAnalysiswaterExcel() {
        return new JsonResult("http://" + ip + "/services/realtime/rsvrfallexcel/getrsvrexchangewaterexcel");
    }

    //水库蓄水量分析对比表
    @GetMapping("getrsvrexchangestorageexcel")
    public void GetRsvrByAnalysisStorageExcel(
            HttpServletResponse response,
            @RequestParam("date")String date,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
            @RequestParam(name="ly",required = false)String ly) {

        /*String date = "2018-07-31";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";
        String ly = "X";*/

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

        System.out.println("时间" + date);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }

        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date dateTime = null;
        try {
            dateTime = DateUtils.parse(date, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<RsvrW> a = (List<RsvrW>) rsvrAnalysisService.getRsvrStorageAnalysis(dateTime, adcdlist, typelist, stcdlist, 1, lylist);
        List<Object[]> dataList = new ArrayList<>();
        Object[] objects = null;
        RsvrStrongeChild rsvrStrongeChild = null;

        RsvrW mm = null;

        /*for (int i = 0; i < a.getStrongeItemList().size(); i++) {
            RsvrStrongeExcel.RsvrStrongeItem rsvrStrongeItem= a.getStrongeItemList().get(i);;
            for(int j=0; j<rsvrStrongeItem.getChildList().size(); j++){
                objects = new Object[7];
                if(j==0){
                    if(rsvrStrongeItem.getHnnm().equals("未知")){
                        objects[0] = "";
                    }else{
                        objects[0] = rsvrStrongeItem.getHnnm();
                    }
                }else{
                    objects[0] = "";
                }
                rsvrStrongeChild =  rsvrStrongeItem.getChildList().get(j);
                objects[1] = rsvrStrongeChild.getStnm();
                objects[2] = rsvrStrongeChild.getW();
                objects[3] = rsvrStrongeChild.getQw();
                objects[4] = rsvrStrongeChild.getQwCompare();
                objects[5] = rsvrStrongeChild.getCw();
                objects[6] = rsvrStrongeChild.getCwCompare();
                dataList.add(objects);
            }
        }*/

        for (int i = 0; i < a.size(); i++) {
            mm = a.get(i);
            objects = new Object[8];
            objects[0] = mm.getStnm();
            objects[1] = mm.getAdnm();
            objects[2] = mm.getRvnm();
            objects[3] = mm.getW();
            objects[4] = mm.getQw();
            objects[5] = mm.getQwCompare();
            objects[6] = mm.getCw();
            objects[7] = mm.getCwCompare();
            dataList.add(objects);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateTime);
        String time = formatter.format(dateTime);
        String title = time + "年水库蓄水量分析表";     //"县域", "河流",
//        String[] rowsName = new String[]{"库名", "县域", "河流", "蓄水量(百万m³)", "去年同期(百万m³)", "较去年(百万m³)", "常年同期(百万m³)", "较常年(百万m³)"};
//        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, "");
//        ex.export();
        ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 43, 4, 8,ExportExecls.Direction.TRANSVERSE);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                Cell colTitle0 = colTitleRow.createCell(0);
                colTitle0.setCellValue("库名");
                colTitle0.setCellStyle(style);

                Cell colTitle1 = colTitleRow.createCell(1);
                colTitle1.setCellValue("县域");
                colTitle1.setCellStyle(style);

                Cell colTitle2 = colTitleRow.createCell(2);
                colTitle2.setCellValue("河流");
                colTitle2.setCellStyle(style);

                Cell colTitle3 = colTitleRow.createCell(3);
                colTitle3.setCellValue("蓄水量(百万m³)");
                colTitle3.setCellStyle(style);

                Cell colTitle4 = colTitleRow.createCell(4);
                colTitle4.setCellValue("去年同期(百万m³)");
                colTitle4.setCellStyle(style);

                Cell colTitle5 = colTitleRow.createCell(5);
                colTitle5.setCellValue("较去年(百万m³)");
                colTitle5.setCellStyle(style);

                Cell colTitle6 = colTitleRow.createCell(6);
                colTitle6.setCellValue("常年同期(百万m³)");
                colTitle6.setCellStyle(style);

                Cell colTitle7 = colTitleRow.createCell(7);
                colTitle7.setCellValue("较常年(百万m³)");
                colTitle7.setCellStyle(style);
                int x = ExportExecls.HEIGHT / 8;
                for (int i = 0; i < 8; i++) {
                    if (i == 1 || i == 2) {
                        sheet.setColumnWidth(i, x - 150);
                    } else if (i == 4 || i == 6) {
                        sheet.setColumnWidth(i, x + 150);
                    } else {
                        sheet.setColumnWidth(i, x);
                    }

                }
            }
        });

    }


    @GetMapping(value = "getrsvrexchangestorageexcelfw")
    public JsonResult GetRsvrByAnalysisStorageExcelfw() {
        return new JsonResult("http://" + ip + "/services/realtime/rsvrfallexcel/getrsvrexchangestorageexcel");
    }

    //水库特征值统计表
    @GetMapping("getrsvrexchangetongjiexcel")
    public void GetRsvrByAnalysistongjiExcel(
            HttpServletResponse response,
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
            @RequestParam(name="ly",required = false)String ly) throws Exception {


        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

        System.out.println("开始时间" + dateStart);
        System.out.println("结束时间" + dateEnd);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }

        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RsvrExchangeExcel a = rsvrAnalysisService.getRsvrFeaturesAnalysis(dateS, dateE, adcdlist, typelist, stcdlist, lylist);
        List<Object[]> dataList = new ArrayList<>();
        Object[] objects = null;
        RsvrTZCount rsvrStrongeChild = null;
        RsvrExchangeExcel rsvrExchangeExcel = null;
        for (int i = 0; i < a.getRsvrPro().size(); i++) {
            RsvrExchangeExcel.RsvrExchangeItem rsvrExchangeItem = a.getRsvrPro().get(i);
            ;
            for (int j = 0; j < rsvrExchangeItem.getData().size(); j++) {
                objects = new Object[10];
                if (j == 0) {
                    objects[0] = rsvrExchangeItem.getRvnm();
                } else {
                    objects[0] = "";
                }
                rsvrStrongeChild = rsvrExchangeItem.getData().get(j);
                objects[1] = rsvrStrongeChild.getStnm();
                objects[2] = rsvrStrongeChild.getMrz();
                objects[3] = rsvrStrongeChild.getMrztm();
                objects[4] = rsvrStrongeChild.getMw();
                objects[5] = rsvrStrongeChild.getMwtm();
                objects[6] = rsvrStrongeChild.getMinq();
                objects[7] = rsvrStrongeChild.getMinqtm();
                objects[8] = rsvrStrongeChild.getMotq();
                objects[9] = rsvrStrongeChild.getMotqtm();
                dataList.add(objects);
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String time1 = formatter.format(dateS);
        String time2 = formatter.format(dateE);
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        int year = tm.get(Calendar.YEAR);
        String time = time1 + "~~" + time2;
        String title = year + "年水库(洼淀)最高水位，最大蓄水量统计表";
//        String[] rowsName = new String[]{"河名", "站名", year + "年", "", "", "", "", "", "", ""};
//        String[] shuangName = new String[]{"", "", "最高水位(m)", "出现日期(日)", "最大蓄水量(百万m³)", "出现日期(日)", "最大入库流量(m³/s)", "出现日期(日)", "最大出库流量(m³/s)", "出现日期(日)"};

        ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 42, 4,10, ExportExecls.Direction.TRANSVERSE);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                Cell colTitle0 = colTitleRow.createCell(0);
                colTitle0.setCellValue("河名");
                colTitle0.setCellStyle(style);

                Cell colTitle1 = colTitleRow.createCell(1);
                colTitle1.setCellValue("站名");
                colTitle1.setCellStyle(style);

                Cell colTitle2 = colTitleRow.createCell(2);
                colTitle2.setCellValue("最高水位(m)");
                colTitle2.setCellStyle(style);

                Cell colTitle3 = colTitleRow.createCell(3);
                colTitle3.setCellValue("出现日期(日)");
                colTitle3.setCellStyle(style);

                Cell colTitle4 = colTitleRow.createCell(4);
                colTitle4.setCellValue("最大蓄水量(百万m³))");
                colTitle4.setCellStyle(style);

                Cell colTitle5 = colTitleRow.createCell(5);
                colTitle5.setCellValue("出现日期(日)");
                colTitle5.setCellStyle(style);

                Cell colTitle6 = colTitleRow.createCell(6);
                colTitle6.setCellValue("最大入库流量(m³/s)");
                colTitle6.setCellStyle(style);

                Cell colTitle7 = colTitleRow.createCell(7);
                colTitle7.setCellValue("出现日期(日)");
                colTitle7.setCellStyle(style);

                Cell colTitle8 = colTitleRow.createCell(8);
                colTitle8.setCellValue("最大出库流量(m³/s)");
                colTitle8.setCellStyle(style);

                Cell colTitle9 = colTitleRow.createCell(9);
                colTitle9.setCellValue("出现日期(日)");
                colTitle9.setCellStyle(style);

                int x = ExportExecls.HEIGHT / 10;
                for (int i = 0; i < 10; i++) {
                    sheet.setColumnWidth(i, x);
                }


            }
        });

    }

    @GetMapping(value = "getrsvrexchangetongjiexcelfw")
    public JsonResult GetRsvrByAnalysistongjiExcelfw() {
        return new JsonResult("http://" + ip + "/services/realtime/rsvrfallexcel/getrsvrexchangetongjiexcel");
    }
}
