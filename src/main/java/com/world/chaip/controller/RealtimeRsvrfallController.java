package com.world.chaip.controller;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.excelFormat.DayRsvr;
import com.world.chaip.entity.report.River;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.service.RainfallService;
import com.world.chaip.service.RiverfallService;
import com.world.chaip.service.RsvrfallService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/rsvrfall")
public class RealtimeRsvrfallController {

    @Autowired
    private RsvrfallService rsvrfallService;

    //水库 (实时)
    @GetMapping("getrsvrbyitem")
    public JsonResult GetRsvrByItem(
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws ParseException {

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
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH:mm");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rsvr> a = rsvrfallService.getRsvrByTerm(dateS, dateE, adcdlist, typelist, stcdlist);
        return new JsonResult(a);
    }

    //水库 (专业)
    @GetMapping("getrsvrbyzhuanye")
    public JsonResult GetRsvrByZhuanYe(
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws ParseException {

        /*String dateStart = "2018-07-10 8:00";
        String dateEnd = "2018-07-26 8:00";
        String adcd = "130501,130521,130522,130523,130524,130525,130526,130527,130528,130529,130530,130531,130532,130533,130534,130535,130581,130582,";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";*/

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
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH:mm");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRsvr a = rsvrfallService.getRsvrByZhuanYe(dateS, dateE, adcdlist, typelist, stcdlist);
        return new JsonResult(a);
    }
}
