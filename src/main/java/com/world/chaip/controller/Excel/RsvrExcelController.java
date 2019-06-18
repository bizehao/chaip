package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExecls;
import com.world.chaip.business.StaticConfig;
import com.world.chaip.entity.excelFormat.DayRsvr;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.RsvrZhuanYe;
import com.world.chaip.service.RsvrfallService;
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
public class RsvrExcelController {

    private String autograph = StaticConfig.autograph;

    @Autowired
    private RsvrfallService rsvrfallService;

    private String ip = StaticConfig.ipAddress;

    //水库 (实时)
    @GetMapping("getrsvrbyitembyexcel")
    public void exportRsvrByItem(
            HttpServletResponse response,
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
            @RequestParam(name="ly",required = false)String ly) throws Exception {

     /*   String dateStart = "2018-7-28 17:00";
        String dateEnd = "2019-01-28 17:00";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";
        String ly = "X";*/

        System.out.println("开始时间" + dateStart);
        System.out.println("结束时间" + dateEnd);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rsvr> a = rsvrfallService.getRsvrByTerm(dateS, dateE, adcdlist, typelist, stcdlist, lylist);
        String title = "水库水情统计表";
        // String[] rowsName = new String[]{"序号","库名","站号","县域","河流","时间","水位(m)","蓄水量(百万m³)"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        // System.out.println(rowsName.length+"--");
        Object[] objects = null;
        for (int i = 0; i < a.size(); i++) {
            Rsvr item = a.get(i);
            objects = new Object[8];
            objects[0] = i + 1;
            objects[1] = item.getStnm();
            objects[2] = item.getStcd();
            objects[3] = item.getAdnm();
            objects[4] = item.getRvnm();
            objects[5] = item.getTm();
            objects[6] = item.getRz();
            objects[7] = item.getW();
            dataList.add(objects);
        }
        /*Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();*/
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String begin = formatter.format(formatter.parse(dateStart));
        String end = formatter.format(formatter.parse(dateEnd));
        String time = "时间：" + begin + "-" + end;
//        System.out.println("===========" + time);
        //导出Excel公共方法调用
//        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
//        ex.export();
        ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 60, 4, 8, ExportExecls.Direction.VERTICAL);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {

                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                Cell colTitle0 = colTitleRow.createCell(0);
                colTitle0.setCellValue("序号");
                colTitle0.setCellStyle(style);

                Cell colTitle1 = colTitleRow.createCell(1);
                colTitle1.setCellValue("库名");
                colTitle1.setCellStyle(style);

                Cell colTitle2 = colTitleRow.createCell(2);
                colTitle2.setCellValue("站号");
                colTitle2.setCellStyle(style);

                Cell colTitle3 = colTitleRow.createCell(3);
                colTitle3.setCellValue("县域");
                colTitle3.setCellStyle(style);

                Cell colTitle4 = colTitleRow.createCell(4);
                colTitle4.setCellValue("河流");
                colTitle4.setCellStyle(style);

                Cell colTitle5 = colTitleRow.createCell(5);
                colTitle5.setCellValue("时间");
                colTitle5.setCellStyle(style);

                Cell colTitle6 = colTitleRow.createCell(6);
                colTitle6.setCellValue("水位(m)");
                colTitle6.setCellStyle(style);

                Cell colTitle7 = colTitleRow.createCell(7);
                colTitle7.setCellValue("蓄水量(百万m³)");
                colTitle7.setCellStyle(style);
                // sheet.setColumnWidth(0, 20000);
                int x = ExportExecls.WEIGHT / 9;
                for (int i = 0; i < 9; i++) {
                    if (i == 0) sheet.setColumnWidth(i, x - 1300);
                    else if (i == 1) sheet.setColumnWidth(i, x + 2000);
                    else if (i == 5) sheet.setColumnWidth(i, x + 250 * 4+1000+300);
                    else if (i==7)sheet.setColumnWidth(i, x-500);
                    else sheet.setColumnWidth(i, x - 250);
                }
            }
        });


        rsvrXbyItem();
    }

    //水库 (实时)
    @GetMapping("rsvrXbyitem")
    public JsonResult rsvrXbyItem() {
        return new JsonResult("http://" + ip + "/services/realtime/rsvrfallexcel/getrsvrbyitembyexcel");
    }

    //水库 (专业)
    @GetMapping("getrsvrbyzhuanyebyexcel")
    public void exportRsvrByZhuanYe(
            HttpServletResponse response,
            @RequestParam("dateS") String dateStart,
            @RequestParam("dateE") String dateEnd,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) throws Exception {

        /*String dateStart = "2018-07-10 8:00";
        String dateEnd = "2018-07-26 8:00";
        String adcd = "130501,130521,130522,130523,130524,130525,130526,130527,130528,130529,130530,130531,130532,130533,130534,130535,130581,130582,";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";
        String ly = "X";*/

        System.out.println("开始时间" + dateStart);
        System.out.println("结束时间" + dateEnd);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRsvr dayRsvr = rsvrfallService.getRsvrByZhuanYe(dateS, dateE, adcdlist, typelist, stcdlist, lylist);
        String title = "今日水情(水库)";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        List<RsvrZhuanYe> a = dayRsvr.getRsvrZhuanYeList();
        for (int i = 0; i < a.size(); i++) {
            RsvrZhuanYe item = a.get(i);
            objects = new Object[9];
            objects[0] = item.getStnm();
            objects[1] = item.getTtcp();
            objects[2] = item.getFsltdz();
            objects[3] = item.getFsltdw();
            objects[4] = item.getRz();
            objects[5] = item.getW();
            objects[6] = item.getInq();
            objects[7] = item.getOtq();
            objects[8] = item.getTm();
            dataList.add(objects);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
        String begin = formatter.format(dateS);
        String end = formatter.format(dateE);
        String time = "时间：" + begin + " - " + end;

        ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 42, 5, 9, ExportExecls.Direction.TRANSVERSE);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {

                String[] rowsName = new String[]{"水库名称", "总库容(百万m³)", "汛期(主汛期)", "", "目前实际", "", "", "", ""};

                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                for (int i = 0; i < rowsName.length; i++) {
                    Cell colTitle0 = colTitleRow.createCell(i);
                    colTitle0.setCellValue(rowsName[i]);
                    colTitle0.setCellStyle(style);
                }
                String[] shuangName = new String[]{"", "", "水位(m)", "库容(百万m³)", "水位(m)", "蓄水量(百万m³)", "入库流量(m³/s)", "下泄流量(m³/s)", "数据时间"};
                Row colTitleRow1 = sheet.createRow(4);

                for (int i = 0; i < shuangName.length; i++) {
                    Cell colTitle = colTitleRow1.createCell(i);
                    colTitle.setCellValue(shuangName[i]);
                    colTitle.setCellStyle(style);

                }
                CellRangeAddress titleAddress;
                titleAddress = new CellRangeAddress(3, 4, 0, 0);
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 4, 1, 1);
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 3, 2, 3);
                sheet.addMergedRegion(titleAddress);

                titleAddress = new CellRangeAddress(3, 3, 4, 8);
                sheet.addMergedRegion(titleAddress);
                int x = ExportExecls.HEIGHT / 10;
                for (int i = 0; i < 10; i++) {
                    if (i ==0) {
                        sheet.setColumnWidth(i, x +200+50*8);
                    }else if (i!= 8){
                        sheet.setColumnWidth(i, x - 250);
                    }else {
                        sheet.setColumnWidth(i, x + 200 * 7);
                    }
                }
            }
        });

        rsvrXbyItem();
    }

    //水库 (专业)
    @GetMapping("rsvrXbyzhuanye")
    public JsonResult rsvrXbyZhuanYe() {
        return new JsonResult("http://" + ip + "/services/realtime/rsvrfallexcel/getrsvrbyzhuanyebyexcel");
    }
}
