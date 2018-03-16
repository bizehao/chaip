package com.world.chaip.controller.Excel;

import com.sun.jndi.cosnaming.IiopUrl;
import com.world.chaip.business.ExportExcel;
import com.world.chaip.business.StaticConfig;
import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.excelFormat.DayRainExcel;
import com.world.chaip.entity.excelFormat.DayRainExcelX;
import com.world.chaip.service.RainfallService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("services/realtime/rainfallexcel")
public class RainExcelController extends HttpServlet{

    @Autowired
    private RainfallService rainfallService;

    String ip = StaticConfig.ipAddress;;

    //导出逐时表
    @GetMapping("getrainbyhourbyexcel")
    public void exportRainByHour(HttpServletResponse response,
                                 @RequestParam("date")String dateStr,
                                 @RequestParam(name="adcd",required=false)String adcd,
                                 @RequestParam(name="systemTypes",required=false)String systemTypes,
                                 @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DaybyHourRainfall a = rainfallService.getDaybyHour(date, adcdlist, typelist,stcdlist);
        String title = "逐时雨量统计报表";
        String[] rowsName = new String[]{"序号","县名","站名","站类","累计值(mm)","9","10","11","12","13","14","15","16",
                "17","18","19","20","21","22","23","0","1","2","3","4","5","6","7","8"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        for (int i=0; i<a.getData().size(); i++){
            DaybyHourRainfall.DayByHourRainfallItem item = a.getData().get(i);
            objects = new Object[rowsName.length];
            objects[0] = i+1;
            objects[1] = item.getAdnm();
            objects[2] = item.getStnm();
            objects[3] = item.getName();
            objects[4] = (double) Math.round(item.getCountDrp() * 100) / 100;
            for(int j=9; j<=23; j++){
                objects[j-4] = item.getHourRainfalls().get((double)j);
            }
            for(int k=0; k<=8; k++){
                objects[k+20] = item.getHourRainfalls().get((double)k);
            }

            dataList.add(objects);
        }
        //处理时间
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime=now.getTime();
        endTime=DateUtils.getDateAfter(beginTime, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time ="时间："+ begin+"~~"+end;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
        rainXbyHour();
    }

    @GetMapping(value="rainXbyhour")
    public JsonResult rainXbyHour() throws IOException {
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbyhourbyexcel");
    }

    //导出逐日表
    @GetMapping("getrainbydatebyexcel")
    public void exportRainByDate(HttpServletResponse response,
                                 @RequestParam("date")String dateStr,
                                 @RequestParam(name="adcd",required=false)String adcd,
                                 @RequestParam(name="systemTypes",required=false)String systemTypes,
                                 @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        /*String dateStr="2017-07-21";
        String adcd="130501,130503,130521,130522,130523,130524,130525,130526,130527,130528,130529,130530,130531,130532,130533,130534,130535,130581,130582,";
        String systemTypes="11,12,";
        String stcdOrStnm="X";*/

        System.out.println("时间"+dateStr);
        System.out.println("县域"+adcd);
        System.out.println("站类型"+systemTypes);
        System.out.println("站号"+stcdOrStnm);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DayRainExcel a = (DayRainExcel)rainfallService.getDaybyDate(date, adcdlist, typelist,stcdlist,1,"ST_PPTN_R");
        List<Object> b = rainfallService.getDaybyDateJS(date, adcdlist, typelist,stcdlist,"ST_PPTN_R");
        String title = "日雨量统计报表";
        String[] rowsName = new String[]{"县名","站名","雨量(mm)","站名","雨量(mm)","站名","雨量(mm)","站名","雨量(mm)","站名","雨量(mm)"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        Object[] rx = null;
        for (int i=0; i<a.getDayRainList().size(); i++){
            DayRainExcel.DayRain item = a.getDayRainList().get(i);
            objects = new Object[rowsName.length];
            objects[0] = item.getAdnm();
            int j = -1;
            int m = 0;
            for (int u=0; u<item.getDayRainList().size(); u++) {
                rx = item.getDayRainList().get(u);
                j=j+2;
                m=j+1;
                if(j<=9){
                    objects[j] = rx[0];
                    objects[m] = rx[1];
                }
                if(j==9 || (u==item.getDayRainList().size()-1 && j!=11)){                              /*6*/
                    dataList.add(objects);
                }
                if(j>9 && item.getDayRainList().size()>=5){
                    objects = new Object[rowsName.length];
                    objects[0] = null;
                    j = -1;
                    j=j+2;
                    m=j+1;
                    objects[j] = rx[0];
                    objects[m] = rx[1];
                    if(u==item.getDayRainList().size()-1){
                        dataList.add(objects);
                    }
                }
            }
        }
        objects = new Object[12];
        objects[0] = "超过100毫米的有：";
        objects[1] = b.get(0)+"站";
        objects[2] = "超过50毫米的有：";
        objects[3] = b.get(1)+"站";
        objects[4] = "超过30毫米的有：";
        objects[5] = b.get(2)+"站";
        objects[6] = "最大的是";
        objects[7] = b.get(3)+"站";
        objects[8] = "次大点的是";
        objects[9] = b.get(4)+"站";
        objects[10] = "再次大点的是";
        objects[11] = b.get(5)+"站";
        dataList.add(objects);
        //处理时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = formatter.format(date);
        String time ="时间："+ begin;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbydate")
    public JsonResult rainXbyDate(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbydatebyexcel");
    }

    //导出逐旬表
    @GetMapping("getrainbyxunbyexcel")
    public void exportRainByXun(HttpServletResponse response,
                                @RequestParam("date")String dateStr,
                                @RequestParam(name="adcd",required=false)String adcd,
                                @RequestParam(name="systemTypes",required=false)String systemTypes,
                                @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        /*String dateStr="2017-07-11";
        String adcd="130501,130503,130521,130522,130523,130524,130525,130526,130527,130528,130529,130530,130531,130532,130533,130534,130535,130581,130582,";
        String systemTypes="11,12,";
        String stcdOrStnm="X";*/

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX)rainfallService.getDaybyXun(date, adcdlist, typelist,stcdlist,1,"ST_PPTN_R");
        String title = "旬雨量统计报表";
        String[] rowsName = new String[]{"县名","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数"};
        //处理List<Object[]>;
        List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        Date Time=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int ri = now.get(Calendar.DATE);
        String xun = null;
        if(ri==11){
            xun="上旬";
        }else if(ri==21){
            xun="中旬";
        }else{
            xun="下旬";
        }
        Time=now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String begin = formatter.format(Time);
        String time ="时间："+ begin+""+xun;
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbyxun")
    public JsonResult rainXbyXun(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbyxunbyexcel");
    }
    //导出逐月表
    @GetMapping("getrainbymonthbyexcel")
    public void exportRainByMonth(HttpServletResponse response,
                                  @RequestParam("date")String dateStr,
                                  @RequestParam(name="adcd",required=false)String adcd,
                                  @RequestParam(name="systemTypes",required=false)String systemTypes,
                                  @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

        System.out.println("时间"+dateStr);
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
            System.out.println("stcdOrStnm"+stcdOrStnm);
            String[] sytemp = stcdOrStnm.split(",");
            for(int i = 0; i<sytemp.length; i++){
                stcdlist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DayRainExcelX a = (DayRainExcelX)rainfallService.getDaybyMonth(date, adcdlist, typelist,stcdlist,1,"ST_PPTN_R");
        String title = "月雨量统计报表";
        String[] rowsName = new String[]{"县名","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数"};
        //处理List<Object[]>;
        List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        Date beginTime=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, -1);
        beginTime=now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String begin = formatter.format(beginTime);
        String time ="时间："+ begin;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbymonth")
    public JsonResult rainXbyMonth(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbymonthbyexcel");
    }

    //导出逐年表
    @GetMapping("getrainbyyearbyexcel")
    public void exportRainByYear(HttpServletResponse response,
                                 @RequestParam("date")String dateStr,
                                 @RequestParam(name="adcd",required=false)String adcd,
                                 @RequestParam(name="systemTypes",required=false)String systemTypes,
                                 @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX)rainfallService.getDaybyYear(date, adcdlist, typelist,stcdlist,1,"ST_PPTN_R");
        /*for(int i=0; i<a.getDayRainXList().size(); i++){
            System.out.println(a.getDayRainXList().get(i).getAdnm());
            List<Object[]> map =  a.getDayRainXList().get(i).getRainList();
            System.out.println(map.size());
            for (int p=0; p<map.size();p++){
                Object[] om = map.get(p);
                System.out.println(Arrays.toString(om));
            }
        }*/
        String title = "年雨量统计报表";
        String[] rowsName = new String[]{"县名","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数","站名","雨量(mm)","降水天数"};
        //处理List<Object[]>;
        List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Calendar tm = Calendar.getInstance();
        tm.setTime(date);
        tm.add(Calendar.YEAR,-1);
        Date btm = tm.getTime();
        String begin = formatter.format(btm);
        String time ="时间："+ begin;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbyyear")
    public JsonResult rainXbyYear(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbyyearbyexcel");
    }

    //导出时段表
    @GetMapping("getrainbytimebyexcel")
    public void exportRainByTime(HttpServletResponse response,
                                 @RequestParam("dateS")String dateStart,
                                 @RequestParam("dateE")String dateEnd,
                                 @RequestParam(name="adcd",required=false)String adcd,
                                 @RequestParam(name="systemTypes",required=false)String systemTypes,
                                 @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

        System.out.println("这儿00"+dateStart);
        System.out.println("这儿00"+dateEnd);
        System.out.println(adcd);
        System.out.println(systemTypes);
        System.out.println(stcdOrStnm);

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
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd hh");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd hh");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX)rainfallService.getDaybyTime(dateS, dateE, adcdlist, typelist,stcdlist,1,"ST_PPTN_R");
        /*for(int i=0; i<a.getDayRainXList().size(); i++){
            System.out.println(a.getDayRainXList().get(i).getAdnm());
            List<Object[]> map =  a.getDayRainXList().get(i).getRainList();
            System.out.println(map.size());
            for (int p=0; p<map.size();p++){
                Object[] om = map.get(p);
                System.out.println(Arrays.toString(om));
            }
        }*/
        String title = "时段雨量统计报表";
        String[] rowsName = new String[]{"县名","站名","雨量(mm)","最近一小时雨量","站名","雨量(mm)","最近一小时雨量","站名","雨量(mm)","最近一小时雨量","站名","雨量(mm)","最近一小时雨量","站名","雨量(mm)","最近一小时雨量"};
        //处理List<Object[]>;
        List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        System.out.println("这个的颠三倒四"+dateS);
        System.out.println(dateE);
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time ="时间："+ begin+" :00 ~~"+end + " :00";
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbytime")
    public JsonResult rainXbyTime(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbytimebyexcel");
    }

    //导表
    public List<Object[]> conExcel(DayRainExcelX  a, String[] rowsName){
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] rainOb = null;
        Object[] objects = null;
        for (int i=0; i<a.getDayRainXList().size(); i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            objects = new Object[rowsName.length];
            objects[0] = item.getAdnm();
            List<Object[]> map =  a.getDayRainXList().get(i).getRainList();
            int j = -2;
            int m = 0;
            int n = 0;
            for (int u=0; u<map.size(); u++) {  /*20*/  /*5*/
                rainOb = map.get(u);
                j=j+3;                                                                     /* 2 5 8 11 14 */   /*17*/
                m=j+1;                                                                     /* 3 6 9 12 15 */
                n=m+1;
                if(j<=13){
                    objects[j] = rainOb[0];
                    objects[m] = rainOb[1];
                    objects[n] = rainOb[2];
                }
                if(j==13 || (u==map.size()-1 && j!=16)){                              /*6*/
                    dataList.add(objects);
                }
                if(j>13 && map.size()>=5){
                    objects = new Object[rowsName.length];
                    objects[0] = null;
                    j = -2;
                    j=j+3;
                    m=j+1;
                    n=m+1;
                    objects[j] = rainOb[0];
                    objects[m] = rainOb[1];
                    objects[n] = rainOb[2];
                    if(u==map.size()-1){
                        dataList.add(objects);
                    }
                }
            }
        }
        return dataList;
    }

    //导出逐日表(专业)
    @GetMapping("getrainbydatebyexcelzy")
    public void exportRainByDateZY(HttpServletResponse response,
                                   @RequestParam("date")String dateStr,
                                   @RequestParam(name="adcd",required=false)String adcd,
                                   @RequestParam(name="systemTypes",required=false)String systemTypes,
                                   @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DayRainExcel a = (DayRainExcel)rainfallService.getDaybyDate(date, adcdlist, typelist,stcdlist,1,"RP_PPTN_R");
        List<Object> b = rainfallService.getDaybyDateJS(date, adcdlist, typelist,stcdlist,"RP_PPTN_R");
        /*for(int i=0; i<a.getDayRainList().size(); i++){
            System.out.println(a.getDayRainList().get(i).getAdnm());
            Map<String, Double> map =  a.getDayRainList().get(i).getDayRainList();
            System.out.println(map.size());
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        }*/
        String title = "日雨量统计报表";
        String[] rowsName = new String[]{"序号","县名","站名","雨量","站名","雨量","站名","雨量","站名","雨量","站名","雨量"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        Object[] rx = null;
        int xuhao = 0;
        for (int i=0; i<a.getDayRainList().size(); i++){
            DayRainExcel.DayRain item = a.getDayRainList().get(i);
            objects = new Object[rowsName.length];
            objects[0] = item.getAdnm();
            int j = -1;
            int m = 0;
            for (int u=0; u<item.getDayRainList().size(); u++) {
                rx = item.getDayRainList().get(u);
                j=j+2;
                m=j+1;
                if(j<=9){
                    objects[j] = rx[0];
                    objects[m] = rx[1];
                }
                if(j==9 || (u==item.getDayRainList().size()-1 && j!=11)){                              /*6*/
                    dataList.add(objects);
                }
                if(j>9 && item.getDayRainList().size()>=5){
                    objects = new Object[rowsName.length];
                    objects[0] = null;
                    j = -1;
                    j=j+2;
                    m=j+1;
                    objects[j] = rx[0];
                    objects[m] = rx[1];
                    if(u==item.getDayRainList().size()-1){
                        dataList.add(objects);
                    }
                }
            }
        }
        objects = new Object[12];
        objects[0] = "超过100毫米的有：";
        objects[1] = b.get(0)+"站";
        objects[2] = "超过50毫米的有：";
        objects[3] = b.get(1)+"站";
        objects[4] = "超过30毫米的有：";
        objects[5] = b.get(2)+"站";
        objects[6] = "最大的是";
        objects[7] = b.get(3)+"站";
        objects[8] = "次大点的是";
        objects[9] = b.get(4)+"站";
        objects[10] = "再次大点的是";
        objects[11] = b.get(5)+"站";
        dataList.add(objects);
        //处理时间
        //处理时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = formatter.format(date);
        String time ="时间："+ begin;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbydatezy")
    public JsonResult rainXbyDateZY(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbydatebyexcelzy");
    }

    //导出逐旬表(专业)
    @GetMapping("getrainbyxunbyexcelzy")
    public void exportRainByXunZY(HttpServletResponse response,
                                  @RequestParam("date")String dateStr,
                                  @RequestParam(name="adcd",required=false)String adcd,
                                  @RequestParam(name="systemTypes",required=false)String systemTypes,
                                  @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX)rainfallService.getDaybyXun(date, adcdlist, typelist,stcdlist,1,"RP_PPTN_R");
        String title = "旬雨量统计报表";
        String[] rowsName = new String[]{"序号","县名","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数"};
        //处理List<Object[]>;
        List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        Date Time=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int ri = now.get(Calendar.DATE);
        String xun = null;
        if(ri==11){
            xun="上旬";
        }else if(ri==21){
            xun="中旬";
        }else{
            xun="下旬";
        }
        Time=now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String begin = formatter.format(Time);
        String time ="时间："+ begin+""+xun;
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbyxunzy")
    public JsonResult rainXbyXunZY(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbyxunbyexcelzy");
    }
    //导出逐月表(专业)
    @GetMapping("getrainbymonthbyexcelzy")
    public void exportRainByMonthZY(HttpServletResponse response,
                                    @RequestParam("date")String dateStr,
                                    @RequestParam(name="adcd",required=false)String adcd,
                                    @RequestParam(name="systemTypes",required=false)String systemTypes,
                                    @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DayRainExcelX a = (DayRainExcelX)rainfallService.getDaybyMonth(date, adcdlist, typelist,stcdlist,1,"RP_PPTN_R");
        String title = "月雨量统计报表";
        String[] rowsName = new String[]{"序号","县名","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数"};
        //处理List<Object[]>;
        List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        Date beginTime=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.MONTH, -1);
        beginTime=now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String begin = formatter.format(beginTime);
        String time ="时间："+ begin;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbymonthzy")
    public JsonResult rainXbyMonthZY(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbymonthbyexcelzy");
    }

    //导出逐年表(专业)
    @GetMapping("getrainbyyearbyexcelzy")
    public void exportRainByYearZY(HttpServletResponse response,
                                   @RequestParam("date")String dateStr,
                                   @RequestParam(name="adcd",required=false)String adcd,
                                   @RequestParam(name="systemTypes",required=false)String systemTypes,
                                   @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX)rainfallService.getDaybyYear(date, adcdlist, typelist,stcdlist,1,"RP_PPTN_R");
        /*for(int i=0; i<a.getDayRainXList().size(); i++){
            System.out.println(a.getDayRainXList().get(i).getAdnm());
            List<Object[]> map =  a.getDayRainXList().get(i).getRainList();
            System.out.println(map.size());
            for (int p=0; p<map.size();p++){
                Object[] om = map.get(p);
                System.out.println(Arrays.toString(om));
            }
        }*/
        String title = "年雨量统计报表";
        String[] rowsName = new String[]{"序号","县名","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数","站名","雨量","降水天数"};
        //处理List<Object[]>;
        List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String begin = formatter.format(date);
        String time ="时间："+ begin;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbyyearzy")
    public JsonResult rainXbyYearZY(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbyyearbyexcelzy");
    }

    //导出时段表(专业)
    @GetMapping("getrainbytimebyexcelzy")
    public void exportRainByTimeZT(HttpServletResponse response,
                                   @RequestParam("dateS")String dateStart,
                                   @RequestParam("dateE")String dateEnd,
                                   @RequestParam(name="adcd",required=false)String adcd,
                                   @RequestParam(name="systemTypes",required=false)String systemTypes,
                                   @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        System.out.println("开始"+dateStart);
        System.out.println("结束"+dateEnd);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();

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
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd hh");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd hh");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX)rainfallService.getDaybyTime(dateS, dateE, adcdlist, typelist,stcdlist,1,"RP_PPTN_R");
        /*for(int i=0; i<a.getDayRainXList().size(); i++){
            System.out.println(a.getDayRainXList().get(i).getAdnm());
            List<Object[]> map =  a.getDayRainXList().get(i).getRainList();
            System.out.println(map.size());
            for (int p=0; p<map.size();p++){
                Object[] om = map.get(p);
                System.out.println(Arrays.toString(om));
            }
        }*/
        String title = "时段雨量统计报表";
        String[] rowsName = new String[]{"序号","县名","站名","雨量","最近一小时雨量","站名","雨量","最近一小时雨量","站名","雨量","最近一小时雨量","站名","雨量","最近一小时雨量","站名","雨量","最近一小时雨量"};
        //处理List<Object[]>;
        List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time ="时间："+ begin+"-"+end;
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }
    @GetMapping(value="rainXbytimezy")
    public JsonResult rainXbyTimeZY(){
        return new JsonResult("http://"+ip+"/services/realtime/rainfallexcel/getrainbytimebyexcelzy");
    }

}
