package com.world.chaip.controller;

import com.world.chaip.entity.exchangeRain.XunQi;
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
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm){

        /*dateStr="2018";
        adcd="130501,130503,130521,130522,130523,130524,130525,130526,130527,130528,130529,130530,130531,130532,130533,130534,130535,130581,130582,";
        systemTypes="11,12,";
        stcdOrStnm="12,";*/
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
        List<XunQi> a = service.getRainXQCompared(date, adcdlist, typelist,stcdlist);
        /*res.setCharacterEncoding(set);*/
        return new JsonResult(a);
    }

    //年逐月降雨量
    @GetMapping("rainnqyanalysis")
    public JsonResult getRainAnalysisNZY(
            @RequestParam("date")String dateStr,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm){

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
        List<Object[]> a = service.getRainNZYCompared(date, adcdlist, typelist,stcdlist);
        return new JsonResult(a);
    }
}
