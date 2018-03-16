package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExcel;
import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.excelFormat.DayRsvr;
import com.world.chaip.entity.report.River;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.RsvrZhuanYe;
import com.world.chaip.service.RsvrfallService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/rsvrfallexcel")
public class RsvrExcelController {

    @Autowired
    private RsvrfallService rsvrfallService;

    //水库 (实时)
    @GetMapping("getrsvrbyitembyexcel")
    public void exportRsvrByItem(
            HttpServletResponse response,
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception{

        System.out.println("开始时间"+dateStart);
        System.out.println("结束时间"+dateEnd);
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
        List<Rsvr> a = rsvrfallService.getRsvrByTerm(dateS, dateE, adcdlist, typelist, stcdlist);
        String title = "水库水情统计表";
        String[] rowsName = new String[]{"序号","水系","库名","站号","时间","水位(m)","蓄水量(亿m³)","出库流量(m³/s)"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        for (int i=0; i<a.size(); i++){
            Rsvr item = a.get(i);
            objects = new Object[rowsName.length];
            objects[0] = i+1;
            objects[1] = item.getHnnm();
            objects[2] = item.getStnm();
            objects[3] = item.getStcd();
            objects[4] = item.getTm();
            objects[5] = item.getRz();
            objects[6] = item.getW();
            objects[7] = item.getOtq();
            dataList.add(objects);
        }
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time ="时间："+ begin+"-"+end;
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
        rsvrXbyItem();
    }

    //水库 (实时)
    @GetMapping("rsvrXbyitem")
    public JsonResult rsvrXbyItem(){
        return new JsonResult("http://192.168.1.63:8080/services/realtime/rsvrfallexcel/getrsvrbyitembyexcel");
    }

    //水库 (专业)
    @GetMapping("getrsvrbyzhuanyebyexcel")
    public void exportRsvrByZhuanYe(
            HttpServletResponse response
            /*@RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm*/) throws Exception{

        String dateStart = "2018-03-15 08";
        String dateEnd = "2018-03-16 08";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";

        System.out.println("开始时间"+dateStart);
        System.out.println("结束时间"+dateEnd);
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
        DayRsvr dayRsvr = rsvrfallService.getRsvrByZhuanYe(dateS, dateE, adcdlist, typelist, stcdlist);
        String title = "水库水情统计表";
        String[] rowsName = new String[]{"水库名称","总库容(亿m³)","汛期("+dayRsvr.getFstp()+")","","目前实际"};
        String[] shuangName = new String[]{"水位(m)","库容(亿m³)","水位(m)","蓄水量(亿m³)","入库流量(m³/s)","下泄流量(m³/s)","数据时间"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        List<RsvrZhuanYe> a = dayRsvr.getRsvrZhuanYeList();
        for (int i=0; i<a.size(); i++){
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
        Date beginTime=null;
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime= now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh");
        String begin = formatter.format(beginTime);
        String time ="时间："+ begin;

        //列头单元格合并
        //水库名称
        CellRangeAddress callRangeAddress1 = new CellRangeAddress(3,4,0,0);//起始行,结束行,起始列,结束列
        //总库容
        CellRangeAddress callRangeAddress2 = new CellRangeAddress(3,4,1,1);//起始行,结束行,起始列,结束列
        //汛期
        CellRangeAddress callRangeAddress3 = new CellRangeAddress(3,3,2,3);//起始行,结束行,起始列,结束列
        //目前实际
        CellRangeAddress callRangeAddress4 = new CellRangeAddress(3,3,4,8);//起始行,结束行,起始列,结束列

        CellRangeAddress[] titleCell = {callRangeAddress1,callRangeAddress2,callRangeAddress3,callRangeAddress4};

        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName,shuangName,titleCell, dataList, response, time,9);
        ex.export();
        rsvrXbyItem();
    }

    //水库 (专业)
    @GetMapping("rsvrXbyzhuanye")
    public JsonResult rsvrXbyZhuanYe(){
        return new JsonResult("http://192.168.1.63:8080/services/realtime/rsvrfallexcel/getrsvrbyzhuanyebyexcel");
    }
}
