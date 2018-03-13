package com.world.chaip.controller;

import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.service.RiverAnalysisService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/riverfall")
public class RiverAnalysisController {

    @Autowired
    private RiverAnalysisService riverAnalysisService;

    //河道水情分析
    @GetMapping("getriverbyanalysis")
    public JsonResult GetRiverByAnalysis(
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm){

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
            dateS = DateUtils.parse(dateStart, "yyyy-MM");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<RiverExchange> a = riverAnalysisService.getRiverByAnalysis(dateS, dateE, adcdlist, typelist, stcdlist);
        return new JsonResult(a);
    }
}
