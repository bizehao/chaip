package com.world.chaip.controller;

import com.world.chaip.entity.Exchange.*;
import com.world.chaip.service.RsvrAnalysisService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springsource.loaded.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/rsvrfall")
public class RsvrAnalysisController {

    @Autowired
    private RsvrAnalysisService rsvrAnalysisService;

    //水库水量分析表
    @GetMapping("getrsvrexchangewater")
    public RsvrWaterExcel GetRsvrByAnalysiswater(
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm){

        /*String dateStart = "2018-02-11";
        String dateEnd = "2018-07-20";
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
        RsvrWaterExcel a = rsvrAnalysisService.getRsvrWaterAnalysis(dateS, dateE, adcdlist, typelist, stcdlist);
        return a;
    }
    //水库蓄水量分析对比表
    @GetMapping("getrsvrexchangestorage")
    public RsvrStrongeExcel GetRsvrByAnalysisStorage(
            @RequestParam("date")String date,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm){

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
        RsvrStrongeExcel a = (RsvrStrongeExcel) rsvrAnalysisService.getRsvrStorageAnalysis(dateTime, adcdlist, typelist, stcdlist,0);
        return a;
    }
    //水库特征值统计表
    @GetMapping("getrsvrexchangetongji")
    public RsvrExchangeExcel GetRsvrByAnalysistongji(
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws ParseException {

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
        return a;
    }

}
