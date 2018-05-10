package com.world.chaip.controller;

import com.world.chaip.entity.exchangeRain.ArbitrarilyDay;
import com.world.chaip.entity.exchangeRain.XunQi;
import com.world.chaip.entity.exchangeRain.YearAndMonthRain;
import com.world.chaip.service.RainAnalysisService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/rainanalysisfall")
public class RainAnalysisController {

    @Autowired
    private RainAnalysisService service;

    //汛期降雨量
    @GetMapping(value = "rainxqanalysis", produces = "application/json;charset=UTF-8")
    public JsonResult getRainAnalysisXQ (
            @RequestParam("date")String dateStr,
            @RequestParam(name="ly",required=false)String ly,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm){

        System.out.println("时间"+dateStr);
        System.out.println("流域"+ly);
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
        List<XunQi> a = service.getRainXQCompared(date,lylist, adcdlist, typelist,stcdlist);
        /*res.setCharacterEncoding(set);*/
        return new JsonResult(a);
    }

    //年逐月降雨量
    @GetMapping("rainnqyanalysis")
    public JsonResult getRainAnalysisNZY(
            @RequestParam("date")String dateStr,
            @RequestParam(name="ly",required=false)String ly,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm){

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
        List<YearAndMonthRain> a = service.getRainNZYCompared(date,lylist, adcdlist, typelist,stcdlist);
        return new JsonResult(a);
    }

    //任意日降雨量
    @GetMapping("rainryanalysis")
    public JsonResult getRainAnalysisRY(
            @RequestParam("dateS")String dateStr,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="ly",required=false)String ly,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm){

        System.out.println("时间"+dateStr);
        System.out.println("时间"+dateEnd);
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
        Date dateS = null;
        Date dateE = null;
        try {
            dateS = DateUtils.parse(dateStr, "yyyy-MM-dd");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ArbitrarilyDay> a = service.getRainRYCompared(dateS, dateE,lylist, adcdlist, typelist,stcdlist);
        return new JsonResult(a);
    }
}
