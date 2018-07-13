package com.world.chaip.controller.Excel;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.world.chaip.business.ExportExcel;
import com.world.chaip.business.StaticConfig;
import com.world.chaip.entity.Exchange.*;
import com.world.chaip.service.RsvrAnalysisService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
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
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception {

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

        System.out.println("开始时间"+dateStart);
        System.out.println("结束时间"+dateEnd);
        System.out.println("县域"+adcd);
        System.out.println("站类型"+systemTypes);
        System.out.println("站号"+stcdOrStnm);

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
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RsvrWaterExcel a = rsvrAnalysisService.getRsvrWaterAnalysis(dateS, dateE, adcdlist, typelist, stcdlist);
        List<Object[]> dataList = new ArrayList<>();
        Object[] objects = null;
        RsvrWaterExchange waterExchange = null;
        int k = 0;
        for (int i = 0; i < a.getRsvrWCList().size(); i++) {
            RsvrWaterExcel.RsvrWC rsvrWC = a.getRsvrWCList().get(i);;
            for(int j=0; j<rsvrWC.getrList().size(); j++){
                objects = new Object[11];
                objects[0] = ++k;
                if(j==0){
                    objects[1] = rsvrWC.getName();
                }else{
                    objects[1] = "";
                }
                waterExchange =  rsvrWC.getrList().get(j);
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
        System.out.println(dateS+""+dateE);
        String time = formatter.format(dateS)+ "年" + beginMonth + "-" + endMonth + "月";
        String title = formatter.format(dateS) + "年" + beginMonth + "-" + endMonth + "月" + "中小型水库水量计算表";
        Calendar updateTm = Calendar.getInstance();
        updateTm.setTime(dateS);
        String beginTm = (updateTm.get(Calendar.MONTH)+1)+"月"+updateTm.get(Calendar.DATE)+"日";
        updateTm.setTime(dateE);
        String endTm = (updateTm.get(Calendar.MONTH)+1)+"月"+updateTm.get(Calendar.DATE)+"日";
        /*String endtm = "";
        if(endMonth==12){
            endtm = endMonth+"月31日";
        }else{
            endtm = (endMonth+1)+"月1日";
        }*/
        String[] rowsName = new String[]{"序号", "系统", "库名", beginTm,"", endTm, "", "蓄水量差(m³)", "出库平均流量(m³/s)", "出库总量(百万m³)", "入库总量(百万m³)"};
        String[] shuangName = new String[]{"", "", "", "水位(m)", "蓄水量(m³)", "水位(m)", "蓄水量(m³)", "", "", "", ""};
        //列头单元格合并
        //序号
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(3, 4, 0, 0);//起始行,结束行,起始列,结束列
        //系统
        CellRangeAddress callRangeAddress2 = new CellRangeAddress(3, 4, 1, 1);//起始行,结束行,起始列,结束列
        //库名
        CellRangeAddress callRangeAddress3 = new CellRangeAddress(3, 4, 2, 2);//起始行,结束行,起始列,结束列
        //？年？月
        CellRangeAddress callRangeAddress4 = new CellRangeAddress(3, 3, 3, 4);//起始行,结束行,起始列,结束列
        //？年？月
        CellRangeAddress callRangeAddress5 = new CellRangeAddress(3, 3, 5, 6);//起始行,结束行,起始列,结束列
        //序号
        CellRangeAddress callRangeAddress6 = new CellRangeAddress(3, 4, 7, 7);//起始行,结束行,起始列,结束列
        //系统
        CellRangeAddress callRangeAddress7 = new CellRangeAddress(3, 4, 8, 8);//起始行,结束行,起始列,结束列
        //库名
        CellRangeAddress callRangeAddress8 = new CellRangeAddress(3, 4, 9, 9);//起始行,结束行,起始列,结束列
        //？年？月
        CellRangeAddress callRangeAddress9 = new CellRangeAddress(3, 4, 10, 10);//起始行,结束行,起始列,结束列
        CellRangeAddress[] titleCell = {callRangeAddress1, callRangeAddress2, callRangeAddress3, callRangeAddress4,
        callRangeAddress5, callRangeAddress6, callRangeAddress7, callRangeAddress8, callRangeAddress9};
        ExportExcel ex = new ExportExcel(title, rowsName, shuangName, titleCell, dataList, response, "");
        ex.export();

    }

    @GetMapping(value="getrsvrexchangewaterexcelfw")
    public JsonResult GetRsvrByAnalysiswaterExcel(){
        return new JsonResult("http://"+ip+"/services/realtime/rsvrfallexcel/getrsvrexchangewaterexcel");
    }

    //水库蓄水量分析对比表
    @GetMapping("getrsvrexchangestorageexcel")
    public void GetRsvrByAnalysisStorageExcel(
            HttpServletResponse response,
            @RequestParam("date")String date,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception {

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

        System.out.println("时间"+date);
        System.out.println("县域"+adcd);
        System.out.println("站类型"+systemTypes);
        System.out.println("站号"+stcdOrStnm);

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
        Date dateTime = null;
        try {
            dateTime = DateUtils.parse(date, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<RsvrW> a = (List<RsvrW>) rsvrAnalysisService.getRsvrStorageAnalysis(dateTime, adcdlist, typelist, stcdlist,1);
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

        for(int i=0; i<a.size(); i++){
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
        String title = time+ "年水库蓄水量分析表";     //"县域", "河流",
        String[] rowsName = new String[]{"库名", "县域", "河流", "蓄水量(百万m³)", "去年同期(百万m³)", "较去年(百万m³)", "常年同期(百万m³)", "较常年(百万m³)"};
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, "");
        ex.export();
    }

    @GetMapping(value="getrsvrexchangestorageexcelfw")
    public JsonResult GetRsvrByAnalysisStorageExcelfw(){
        return new JsonResult("http://"+ip+"/services/realtime/rsvrfallexcel/getrsvrexchangestorageexcel");
    }
    //水库特征值统计表
    @GetMapping("getrsvrexchangetongjiexcel")
    public void GetRsvrByAnalysistongjiExcel(
            HttpServletResponse response,
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception {

        /*String dateStart = "2017-02-11";
        String dateEnd = "2017-06-12";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";*/

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

        System.out.println("开始时间"+dateStart);
        System.out.println("结束时间"+dateEnd);
        System.out.println("县域"+adcd);
        System.out.println("站类型"+systemTypes);
        System.out.println("站号"+stcdOrStnm);

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
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RsvrExchangeExcel a = rsvrAnalysisService.getRsvrFeaturesAnalysis(dateS, dateE, adcdlist, typelist, stcdlist);
        List<Object[]> dataList = new ArrayList<>();
        Object[] objects = null;
        RsvrTZCount rsvrStrongeChild = null;
        RsvrExchangeExcel rsvrExchangeExcel = null;
        for (int i = 0; i < a.getRsvrPro().size(); i++) {
            RsvrExchangeExcel.RsvrExchangeItem rsvrExchangeItem = a.getRsvrPro().get(i);;
            for(int j=0; j<rsvrExchangeItem.getData().size(); j++){
                objects = new Object[10];
                if(j==0){
                    objects[0] = rsvrExchangeItem.getRvnm();
                }else{
                    objects[0] = "";
                }
                rsvrStrongeChild =  rsvrExchangeItem.getData().get(j);
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
        String time=time1+"~~"+time2;
        String title = year+ "年水库(洼淀)最高水位，最大蓄水量统计表";
        String[] rowsName = new String[]{"河名", "站名", year+"年","","","","","","",""};
        String[] shuangName = new String[]{"", "", "最高水位(m)", "出现日期(日)", "最大蓄水量(百万m³)", "出现日期(日)", "最大入库流量(m³/s)", "出现日期(日)", "最大出库流量(m³/s)", "出现日期(日)"};
        //列头单元格合并
        //河名
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(3, 4, 0, 0);//起始行,结束行,起始列,结束列
        //站名
        CellRangeAddress callRangeAddress2 = new CellRangeAddress(3, 4, 1, 1);//起始行,结束行,起始列,结束列
        //时间
        CellRangeAddress callRangeAddress3 = new CellRangeAddress(3, 3, 2, 9);//起始行,结束行,起始列,结束列
        CellRangeAddress[] titleCell = {callRangeAddress1, callRangeAddress2, callRangeAddress3};
        ExportExcel ex = new ExportExcel(title, rowsName, shuangName, titleCell, dataList, response, "");
        ex.export();
    }
    @GetMapping(value="getrsvrexchangetongjiexcelfw")
    public JsonResult GetRsvrByAnalysistongjiExcelfw(){
        return new JsonResult("http://"+ip+"/services/realtime/rsvrfallexcel/getrsvrexchangetongjiexcel");
    }
}
