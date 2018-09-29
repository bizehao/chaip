package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExcel;
import com.world.chaip.business.StaticConfig;
import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.report.River;
import com.world.chaip.service.RiverfallService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("services/realtime/riverfallexcel")
public class RiverExcelController {

    private String autograph = StaticConfig.autograph;

    @Autowired
    private RiverfallService riverfallService;

    private String ip = StaticConfig.ipAddress;

    //导出河道表(实时库)
    @GetMapping("getriverbyitembyexcel")
    public void exportRiverByItem(HttpServletResponse response,
                       @RequestParam("dateS")String dateStart,
                       @RequestParam("dateE")String dateEnd,
                       @RequestParam(name="adcd",required=false)String adcd,
                       @RequestParam(name="systemTypes",required=false)String systemTypes,
                       @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
                                  @RequestParam(name="ly",required = false)String ly) throws Exception{
        String benqu="and c.dq=31 and c.db in (2,3)";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
        if(ly.equals("X")){
            lylist = null;
        }else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for(int i = 0; i<sytemp.length; i++){
                lylist.add(sytemp[i]);
            }
        }
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH:mm");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<River> a = riverfallService.getRiverByTerm(dateS, dateE, adcdlist, typelist, stcdlist,benqu,lylist);
        String title = "河道水情统计表";
        String[] rowsName = new String[]{"序号","县名","河名","站名","站号","时间","水位(m)","流量(m³/s)","水势"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        for (int i=0; i<a.size(); i++){
            River item = a.get(i);
            objects = new Object[rowsName.length];
            objects[0] = i+1;
            objects[1] = item.getAdnm();
            objects[2] = item.getRvnm();
            objects[3] = item.getStnm();
            objects[4] = item.getStcd();
            objects[5] = item.getTm();
            objects[6] = item.getZ();
            objects[7] = item.getQ();
            objects[8] = item.getWptn();
            dataList.add(objects);
        }
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        System.out.println(dateE);
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time ="时间："+ begin+"-"+end;
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
        riverXbyItem();
    }

    //河道
    @GetMapping("riverXbyitem")
    public JsonResult riverXbyItem(){
        return new JsonResult("http://"+ip+"/services/realtime/riverfallexcel/getriverbyitembyexcel");
    }

    //导出河道表(本区)
    @GetMapping("getriverbybenbyexcel")
    public void exportRiverByBen(HttpServletResponse response,
                                  @RequestParam("dateS")String dateStart,
                                  @RequestParam("dateE")String dateEnd,
                                  @RequestParam(name="adcd",required=false)String adcd,
                                  @RequestParam(name="systemTypes",required=false)String systemTypes,
                                  @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
                                  @RequestParam(name="ly",required = false)String ly) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

        System.out.println("开始000时间"+dateStart);
        System.out.println("结束00时间"+dateEnd);
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
        if(ly.equals("X")){
            lylist = null;
        }else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for(int i = 0; i<sytemp.length; i++){
                lylist.add(sytemp[i]);
            }
        }
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH:mm");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<River> a = riverfallService.getRiverByBen(dateS, dateE, adcdlist, typelist, stcdlist,lylist);
        String title = "今日水情(河道)";
        String[] rowsName = new String[]{"河名","站名","水位(m)","流量(m³/s)","数据时间","河名","站名","水位(m)","流量(m³/s)","数据时间"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        for (int i=0; i<a.size(); i++){
            if(i%2==0 || i==0){
                objects = new Object[rowsName.length];
                if(i == a.size()-1){
                    River item = a.get(i);
                    objects[0] = item.getRvnm();
                    objects[1] = item.getStnm();
                    objects[2] = item.getZ();
                    objects[3] = item.getQ();
                    objects[4] = item.getTm();
                    dataList.add(objects);
                }
                River item = a.get(i);
                objects[0] = item.getRvnm();
                objects[1] = item.getStnm();
                objects[2] = item.getZ();
                objects[3] = item.getQ();
                objects[4] = item.getTm();
            }else{
                River itemX = a.get(i);
                objects[5] = itemX.getRvnm();
                objects[6] = itemX.getStnm();
                objects[7] = itemX.getZ();
                objects[8] = itemX.getQ();
                objects[9] = itemX.getTm();
                dataList.add(objects);
            }
        }

        /*if(a.size() %2 == 0){
            for (int i=0; i<a.size()/2; i++){
                River item = a.get(2*i);
                River itemX = a.get(2*i+1);
                objects = new Object[rowsName.length];
                objects[0] = item.getRvnm();
                objects[1] = item.getStnm();
                objects[2] = item.getZ();
                objects[3] = item.getQ();
                objects[4] = item.getTm();
                objects[5] = itemX.getRvnm();
                objects[6] = itemX.getStnm();
                objects[7] = itemX.getZ();
                objects[8] = itemX.getQ();
                objects[9] = itemX.getTm();
                dataList.add(objects);
            }
        }else{
            for (int i=0; i<(a.size()+1)/2; i++){
                if(2*i+1 != a.size()){
                    River item = a.get(2*i);
                    River itemX = a.get(2*i+1);
                    objects = new Object[rowsName.length];
                    objects[0] = item.getRvnm();
                    objects[1] = item.getStnm();
                    objects[2] = item.getZ();
                    objects[3] = item.getQ();
                    objects[4] = item.getTm();
                    objects[5] = itemX.getRvnm();
                    objects[6] = itemX.getStnm();
                    objects[7] = itemX.getZ();
                    objects[8] = itemX.getQ();
                    objects[9] = itemX.getTm();
                    dataList.add(objects);
                }else{
                    River item = a.get(a.size()-1);
                    objects = new Object[rowsName.length];
                    objects[0] = item.getRvnm();
                    objects[1] = item.getStnm();
                    objects[2] = item.getZ();
                    objects[3] = item.getQ();
                    objects[4] = item.getTm();
                    dataList.add(objects);
                }
            }
        }*/

        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time ="时间："+ begin+"-"+end;
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, autograph);
        ex.export();
        riverXbyBen();
    }

    //河道(本区)
    @GetMapping("riverXbyben")
    public JsonResult riverXbyBen(){
        return new JsonResult("http://"+ip+"/services/realtime/riverfallexcel/getriverbybenbyexcel");
    }

    //导出河道表(外区)
    @GetMapping("getriverbywaibyexcel")
    public void exportRiverByWai(HttpServletResponse response,
                                  @RequestParam("dateS")String dateStart,
                                  @RequestParam("dateE")String dateEnd,
                                  @RequestParam(name="adcd",required=false)String adcd,
                                  @RequestParam(name="systemTypes",required=false)String systemTypes,
                                  @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
                                  @RequestParam(name="ly",required = false)String ly) throws Exception{

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
        if(ly.equals("X")){
            lylist = null;
        }else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for(int i = 0; i<sytemp.length; i++){
                lylist.add(sytemp[i]);
            }
        }
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH:mm");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<River> a = riverfallService.getRiverByWai(dateS, dateE, adcdlist, typelist, stcdlist,lylist);
        String title = "今日外区水情(河道)";
        String[] rowsName = new String[]{"河名","站名","水位(m)","流量(m³/s)","数据时间","河名","站名","水位(m)","流量(m³/s)","数据时间"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        for (int i=0; i<a.size(); i++){
            if(i%2==0 || i==0){
                objects = new Object[rowsName.length];
                if(i == a.size()-1){
                    River item = a.get(i);
                    objects[0] = item.getRvnm();
                    objects[1] = item.getStnm();
                    objects[2] = item.getZ();
                    objects[3] = item.getQ();
                    objects[4] = item.getTm();
                    dataList.add(objects);
                }
                River item = a.get(i);
                objects[0] = item.getRvnm();
                objects[1] = item.getStnm();
                objects[2] = item.getZ();
                objects[3] = item.getQ();
                objects[4] = item.getTm();
            }else{
                River itemX = a.get(i);
                objects[5] = itemX.getRvnm();
                objects[6] = itemX.getStnm();
                objects[7] = itemX.getZ();
                objects[8] = itemX.getQ();
                objects[9] = itemX.getTm();
                dataList.add(objects);
            }
        }
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        System.out.println(dateE);
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time ="时间："+ begin+"-"+end;
        //导出Excel公共方法调用
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, autograph);
        ex.export();
        riverXbyBen();
    }

    //河道 (外区)
    @GetMapping("riverXbywai")
    public JsonResult riverXbyWai(){
        return new JsonResult("http://"+ip+"/services/realtime/riverfallexcel/getriverbywaibyexcel");
    }


}
