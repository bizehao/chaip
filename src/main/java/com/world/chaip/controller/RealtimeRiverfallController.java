package com.world.chaip.controller;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.entity.report.River;
import com.world.chaip.service.RainfallService;
import com.world.chaip.service.RiverfallService;
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
@RequestMapping("services/realtime/riverfall")
public class RealtimeRiverfallController {
	
	@Autowired
	private RiverfallService riverfallService;

	//实时河道
	@GetMapping("getriverbyitem")
	public JsonResult GetRiverByItem(
			@RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
			@RequestParam(name="adcd",required=false)String adcd,
			@RequestParam(name="systemTypes",required=false)String systemTypes,
			@RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
            @RequestParam(name="ly",required = false)String ly) throws ParseException {

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
        List<River> a = riverfallService.getRiverByTerm(dateS, dateE, adcdlist, typelist, stcdlist, benqu,lylist);
        return new JsonResult(a);
	}

    //本区河道
    @GetMapping("getriverbyben")
    public JsonResult GetRiverByBen(
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
            @RequestParam(name="ly",required = false)String ly) throws ParseException {
        String benqu="and c.dq != 32 and c.jdb in (2,3)";
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
        System.out.println("======================");
        System.out.println(dateS);
        System.out.println(dateE);
        List<River> a = riverfallService.getRiverByBen(dateS, dateE, adcdlist, typelist, stcdlist,lylist);
        return new JsonResult(a);
    }

    //外区河道
    @GetMapping("getriverbywai")
    public JsonResult GetRiverByWai(
            @RequestParam("dateS")String dateStart,
            @RequestParam("dateE")String dateEnd,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm,
            @RequestParam(name="ly",required = false)String ly) throws ParseException {

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
        try {//因为曹哥那边要改
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH:mm");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<River> a = riverfallService.getRiverByWai(dateS, dateE, adcdlist, typelist, stcdlist,lylist);
        return new JsonResult(a);
    }

}
