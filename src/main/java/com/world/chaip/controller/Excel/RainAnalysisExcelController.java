package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExcel;
import com.world.chaip.business.ExportExecls;
import com.world.chaip.business.StaticConfig;
import com.world.chaip.entity.exchangeRain.ArbitrarilyDay;
import com.world.chaip.entity.exchangeRain.XunQi;
import com.world.chaip.entity.exchangeRain.YearAndMonthRain;
import com.world.chaip.entity.report.River;
import com.world.chaip.service.RainAnalysisService;
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
@RequestMapping("services/realtime/rainanalysisfallexcel")
public class RainAnalysisExcelController {

    private String ip = StaticConfig.ipAddress;

    @Autowired
    private RainAnalysisService service;

    //汛期降雨量
    @GetMapping(value="rainxqanalysisexcel")
    public void getRainAnalysisXQExcel(
            HttpServletResponse response/*,
            @RequestParam("date")String dateStr,
            @RequestParam(name="ly",required=false)String ly,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm*/) throws Exception {


        String dateStr = "2019-01-10";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";

        System.out.println("时间"+dateStr);
        System.out.println("县域"+adcd);
        System.out.println("站类型"+systemTypes);
        System.out.println("站号"+stcdOrStnm);
        List<String> lylist = new ArrayList<String>();
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

        if(ly.equals("X")){
            lylist=null;
        }else {
            ly = ly.substring(0, ly.length() - 1);
            String[] temp = ly.split(",");
            for(int i = 0; i<temp.length; i++){
                lylist.add(temp[i]);
            }
        }

        if(adcd.equals("X")){
            adcdlist=null;
        }else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for(int i = 0; i<temp.length; i++){
                adcdlist.add(temp[i]);
            }
        }

        if(systemTypes.equals("X")){
            typelist=null;
        }else{
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for(int i = 0; i<sytemp.length; i++){
                typelist.add(sytemp[i]);
            }
        }
        if(stcdOrStnm.equals("X")){
            stcdlist=null;
        }else{
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for(int i = 0; i<sytemp.length; i++){
                stcdlist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<XunQi> xqList = service.getRainXQCompared(date,lylist, adcdlist, typelist,stcdlist);
        List<Object[]> dataList = new ArrayList<>();
        Object[] objects = null;
        for (int i=0; i<xqList.size(); i++){
            objects = new Object[17];
            objects[0] = xqList.get(i).getStnm();
            objects[1] = xqList.get(i).getJxqSix();
            objects[2] = xqList.get(i).getJxqSeven();
            objects[3] = xqList.get(i).getJxqEight();
            objects[4] = xqList.get(i).getJxqNine();
            objects[5] = xqList.get(i).getJxqSix_Nine();
            objects[6] = xqList.get(i).getJxqSix_Nine_Compare();
            objects[7] = xqList.get(i).getQxqSix();
            objects[8] = xqList.get(i).getQxqSeven();
            objects[9] = xqList.get(i).getQxqEight();
            objects[10] = xqList.get(i).getQxqNine();
            objects[11] = xqList.get(i).getQxqSix_Nine();
            objects[12] = xqList.get(i).getCxqSix();
            objects[13] = xqList.get(i).getCxqSeven();
            objects[14] = xqList.get(i).getCxqEight();
            objects[15] = xqList.get(i).getCxqNine();
            objects[16] = xqList.get(i).getCxqSix_Nine();
            dataList.add(objects);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String time = formatter.format(date);
        String title = time+"年邢台市年降雨量分析比较表";
        String[] rowsName = new String[]{"县名","汛期降雨量","","","","","","降雨量与去年同期比较","","","","","降雨量与常年同期比较","","","",""};
        String[] shuangName = new String[]{"","6月","7月","8月","9月","6-9月","6月、9月对比","6月","7月","8月","9月","6-9月","6月","7月","8月","9月","6-9月"};
        //列头单元格合并
        //市名
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(3,4,0,0);//起始行,结束行,起始列,结束列
        //总库容
        CellRangeAddress callRangeAddress2 = new CellRangeAddress(3,3,1,6);//起始行,结束行,起始列,结束列
        //汛期
        CellRangeAddress callRangeAddress3 = new CellRangeAddress(3,3,7,11);//起始行,结束行,起始列,结束列
        //目前实际
        CellRangeAddress callRangeAddress4 = new CellRangeAddress(3,3,12,16);//起始行,结束行,起始列,结束列

        CellRangeAddress[] titleCell = {callRangeAddress1,callRangeAddress2,callRangeAddress3,callRangeAddress4};
//        ExportExcel ex = new ExportExcel(title, rowsName,shuangName,titleCell, dataList, response, "");
//        ex.export();

        ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 40, 5, 17,ExportExecls.Direction.TRANSVERSE);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                for (int i=0; i<rowsName.length;i++){
                    Cell colTitle= colTitleRow.createCell(i);
                    colTitle.setCellValue(rowsName[i]);
                    colTitle.setCellStyle(style);
                }
                Row colTitleRow1 = sheet.createRow(4);
                for (int i=0; i<shuangName.length;i++){
                    Cell colTitle= colTitleRow1.createCell(i);
                    colTitle.setCellValue(shuangName[i]);
                    colTitle.setCellStyle(style);
                }

                for (int i=0; i<titleCell.length;i++){
                    sheet.addMergedRegion(titleCell[i]);
                }
                int x=ExportExecls.HEIGHT/17;
                for (int i=0;i<17;i++){
                    sheet.setColumnWidth(i,x);
                }


            }
        });
    }

    @GetMapping(value="rainxqXbytime")
    public JsonResult rainXbyTimeXQ(){
        return new JsonResult("http://"+ip+"/services/realtime/rainanalysisfallexcel/rainxqanalysisexcel");
    }

    //年逐月降雨量
    @GetMapping(value="rainnzyanalysisexcel")
    public void getRainAnalysisNZYExcel(
            HttpServletResponse response/*,
            @RequestParam("date")String dateStr,
            @RequestParam(name="ly",required=false)String ly,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm*/) throws Exception {

        String dateStr = "2019-01-10";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";

        System.out.println("时间"+dateStr);
        System.out.println("县域"+adcd);
        System.out.println("站类型"+systemTypes);
        System.out.println("站号"+stcdOrStnm);

        List<String> lylist = new ArrayList<String>();
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

        if(ly.equals("X")){
            lylist=null;
        }else {
            ly = ly.substring(0, ly.length() - 1);
            String[] temp = ly.split(",");
            for(int i = 0; i<temp.length; i++){
                lylist.add(temp[i]);
            }
        }

        if(adcd.equals("X")){
            adcdlist=null;
        }else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for(int i = 0; i<temp.length; i++){
                adcdlist.add(temp[i]);
            }
        }

        if(systemTypes.equals("X")){
            typelist=null;
        }else{
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for(int i = 0; i<sytemp.length; i++){
                typelist.add(sytemp[i]);
            }
        }
        if(stcdOrStnm.equals("X")){
            stcdlist=null;
        }else{
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for(int i = 0; i<sytemp.length; i++){
                stcdlist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<YearAndMonthRain> yearAndMonthRains = service.getRainNZYCompared(date,lylist, adcdlist, typelist,stcdlist);
        List<Object[]> dataList = new ArrayList<>();
        Object[] object = null;
        for(int i=0; i<yearAndMonthRains.size(); i++){
            object = new Object[20];
            object[0] = yearAndMonthRains.get(i).getAdnm();
            object[1] = yearAndMonthRains.get(i).getNumOne();
            object[2] = yearAndMonthRains.get(i).getNumTwo();
            object[3] = yearAndMonthRains.get(i).getNumThree();
            object[4] = yearAndMonthRains.get(i).getNumFour();
            object[5] = yearAndMonthRains.get(i).getNumFive();
            object[6] = yearAndMonthRains.get(i).getNumSix();
            object[7] = yearAndMonthRains.get(i).getNumSeven();
            object[8] = yearAndMonthRains.get(i).getNumEight();
            object[9] = yearAndMonthRains.get(i).getNumNine();
            object[10] = yearAndMonthRains.get(i).getNumTen();
            object[11] = yearAndMonthRains.get(i).getNumEleven();
            object[12] = yearAndMonthRains.get(i).getNumTwelve();
            object[13] = yearAndMonthRains.get(i).getJinYearZong();
            object[14] = yearAndMonthRains.get(i).getQuYearZong();
            object[15] = yearAndMonthRains.get(i).getChangYearZong();
            object[16] = yearAndMonthRains.get(i).getCompareQu();
            object[17] = yearAndMonthRains.get(i).getCompareChang();
            object[18] = yearAndMonthRains.get(i).getRelativeQu();
            object[19] = yearAndMonthRains.get(i).getRelativeChang();
            dataList.add(object);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String time = formatter.format(date);
        String title = time+"年邢台市逐月及年降雨量分析比较表";
        String[] rowsName = new String[]{"县名","降雨量(mm)","","","","","","","","","","","","年降雨量","","","比较","","相对值比较(%)",""};
        String[] shuangName = new String[]{"","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月",
                "降雨量","去年","常年","去年","常年","去年","常年"};
        //列头单元格合并
        //市名
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(3,4,0,0);//起始行,结束行,起始列,结束列
        //降水量
        CellRangeAddress callRangeAddress2 = new CellRangeAddress(3,3,1,12);//起始行,结束行,起始列,结束列
        //年降雨量
        CellRangeAddress callRangeAddress3 = new CellRangeAddress(3,3,13,15);//起始行,结束行,起始列,结束列
        //比较
        CellRangeAddress callRangeAddress4 = new CellRangeAddress(3,3,16,17);//起始行,结束行,起始列,结束列
        //相对值
        CellRangeAddress callRangeAddress5 = new CellRangeAddress(3,3,18,19);//起始行,结束行,起始列,结束列

        CellRangeAddress[] titleCell = {callRangeAddress1,callRangeAddress2,callRangeAddress3,callRangeAddress4,callRangeAddress5};
//        ExportExcel ex = new ExportExcel(title, rowsName,shuangName,titleCell, dataList, response, "");
//        ex.export();

        ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 40, 5, 20,ExportExecls.Direction.TRANSVERSE);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                for (int i=0; i<rowsName.length;i++){
                    Cell colTitle= colTitleRow.createCell(i);
                    colTitle.setCellValue(rowsName[i]);
                    colTitle.setCellStyle(style);
                }
                Row colTitleRow1 = sheet.createRow(4);
                for (int i=0; i<shuangName.length;i++){
                    Cell colTitle= colTitleRow1.createCell(i);
                    colTitle.setCellValue(shuangName[i]);
                    colTitle.setCellStyle(style);
                }

                for (int i=0; i<titleCell.length;i++){
                    sheet.addMergedRegion(titleCell[i]);
                }
                int x=ExportExecls.HEIGHT/20;
                for (int i=0;i<20;i++){
                    sheet.setColumnWidth(i,x);
                }


            }
        });

    }

    @GetMapping(value="rainnzyXbytime")
    public JsonResult rainXbyTimeNZY(){
        return new JsonResult("http://"+ip+"/services/realtime/rainanalysisfallexcel/rainnzyanalysisexcel");
    }

    //任意日降雨量
    @GetMapping(value="rainryanalysisexcel")
    public void getRainAnalysisRYExcel(
            HttpServletResponse response/*,
            @RequestParam("dateS") String dateStart,
            @RequestParam("dateE") String dateEnd,
            @RequestParam(name="ly",required=false)String ly,
            @RequestParam(name = "adcd",required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm*/) throws Exception {

        String dateStart = "2017-02-11";
        String dateEnd = "2017-06-12";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";
        String ly = "X";

        List<String> lylist = new ArrayList<String>();
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

        if(ly.equals("X")){
            lylist=null;
        }else {
            ly = ly.substring(0, ly.length() - 1);
            String[] temp = ly.split(",");
            for(int i = 0; i<temp.length; i++){
                lylist.add(temp[i]);
            }
        }

        if(adcd.equals("X")){
            adcdlist=null;
        }else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for(int i = 0; i<temp.length; i++){
                adcdlist.add(temp[i]);
            }
        }

        if(systemTypes.equals("X")){
            typelist=null;
        }else{
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for(int i = 0; i<sytemp.length; i++){
                typelist.add(sytemp[i]);
            }
        }
        if(stcdOrStnm.equals("X")){
            stcdlist=null;
        }else{
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            System.out.println("stcdOrStnm"+stcdOrStnm);
            String[] sytemp = stcdOrStnm.split(",");
            for(int i = 0; i<sytemp.length; i++){
                stcdlist.add(sytemp[i]);
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
        List<ArbitrarilyDay> arbitrarilyDays = service.getRainRYCompared(dateS,dateE,lylist,adcdlist, typelist,stcdlist);
        List<Object[]> dataList = new ArrayList<>();
        Object[] objects = null;
        for(int i=0; i<arbitrarilyDays.size(); i++){
            objects = new Object[6];
            objects[0] = arbitrarilyDays.get(i).getAdnm();
            objects[1] = arbitrarilyDays.get(i).getoDay_oDay();
            objects[2] = arbitrarilyDays.get(i).getSamePeriodQu();
            objects[3] = arbitrarilyDays.get(i).getSamePeriodChang();
            objects[4] = arbitrarilyDays.get(i).getSamePeriodCompareQu();
            objects[5] = arbitrarilyDays.get(i).getSamePeriodCompareChang();
            dataList.add(objects);
        }
        SimpleDateFormat formYear = new SimpleDateFormat("yyyy");
        String year = formYear.format(dateS);
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        int monthS = tm.get(Calendar.MONTH)+1;
        int dayS = tm.get(Calendar.DATE);
        tm.setTime(dateE);
        int monthE = tm.get(Calendar.MONTH)+1;
        int dayE = tm.get(Calendar.DATE);
        String time = monthS+"月"+dayS+"日"+""+"至"+""+monthE+"月"+dayE+"日";
        String title = year+"年邢台市降雨量分析比较表";
        String[] rowsName = new String[]{"县名","降雨量(mm)","","","去年同期比较","常年同期比较"};
        String[] shuangName = new String[]{"",time,"去年同期","常年同期","",""};
        //列头单元格合并
        //市名
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(3,4,0,0);//起始行,结束行,起始列,结束列
        //降水量
        CellRangeAddress callRangeAddress2 = new CellRangeAddress(3,3,1,3);//起始行,结束行,起始列,结束列
        //去年同期
        CellRangeAddress callRangeAddress3 = new CellRangeAddress(3,4,4,4);//起始行,结束行,起始列,结束列
        //常年同期
        CellRangeAddress callRangeAddress4 = new CellRangeAddress(3,4,5,5);//起始行,结束行,起始列,结束列

        CellRangeAddress[] titleCell = {callRangeAddress1,callRangeAddress2,callRangeAddress3,callRangeAddress4};
        ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 40, 5, 6, ExportExecls.Direction.VERTICAL);
        exportExecls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                for (int i = 0; i < rowsName.length; i++) {
                    System.out.print(rowsName[i] + "\t");
                    Cell colTitle = colTitleRow.createCell(i);
                    colTitle.setCellValue(rowsName[i]);
                    colTitle.setCellStyle(style);
                }
                colTitleRow = sheet.createRow(4);
                for (int i = 0; i < shuangName.length; i++) {
                    Cell colTitle = colTitleRow.createCell(i);
                    System.out.print(shuangName[i] + "\t");
                    colTitle.setCellValue(shuangName[i]);
                    colTitle.setCellStyle(style);
                }

                for (int i = 0; i < titleCell.length; i++) {
                    sheet.addMergedRegion(titleCell[i]);
                }
                int x = ExportExecls.WEIGHT / 6;
                for (int i = 0; i < 6; i++) {
                    sheet.setColumnWidth(i, x);
                }
            }
        });
    }

    @GetMapping(value="rainryXbytime")
    public JsonResult rainXbyTimeRY(){
        return new JsonResult("http://"+ip+"/services/realtime/rainanalysisfallexcel/rainryanalysisexcel");
    }
}
