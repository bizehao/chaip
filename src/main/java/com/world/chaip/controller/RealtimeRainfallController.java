package com.world.chaip.controller;

import java.text.ParseException;
import java.util.*;

import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.report.gson.PptnGson;
import com.world.chaip.entity.report.gson.RainEc;
import com.world.chaip.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.world.chaip.service.RainfallService;
import com.world.chaip.util.DateUtils;

@RestController
@RequestMapping("services/realtime/rainfall")
public class RealtimeRainfallController {

    @Autowired
    private RainfallService rainfallService;

    //逐时降雨量
    @GetMapping("getrainbyhour")
    public JsonResult getRainByHour(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "column", required = false) String column,
            @RequestParam(name = "sign", required = false) String sign,
            @RequestParam(name = "ly", required = false) String ly) {

        String db = "and c.db in (1,3)";
        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);
        System.out.println("列名" + column);
        System.out.println("顺序" + sign);
        System.out.println("流域" + ly);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }

        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int col = -1;
        int sig = -1;
        if (!column.equals("X")) {
            col = Integer.parseInt(column);
        }

        if (!sign.equals("X")) {
            sig = Integer.parseInt(sign);
        }
        System.out.println(lylist);
        List<PptnGson> a = rainfallService.getDaybyHour(date, adcdlist, typelist, stcdlist, col, sig, db, lylist);
        String b = rainfallService.getDaybyHourJS(date, adcdlist, typelist, stcdlist, db, lylist);
        RainEc arrList = new RainEc();
        arrList.setPptnGsonList(a);
        arrList.setMessage(b);
        return new JsonResult(arrList);
    }

    //日雨量
    @GetMapping("getrainbydate")
    public JsonResult getRainByDate(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {

        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyDate(date, adcdlist, typelist, stcdlist, 0, "ST_PPTN_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getDyp());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            sum += (double) avgList.get(i);
        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //旬雨量
    @GetMapping("getrainbyxun")
    public JsonResult getRainByXun(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {

        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyXun(date, adcdlist, typelist, stcdlist, 0, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            System.out.println("a.get(i).getStnm():"+a.get(i).getStnm());
            avgList.add(a.get(i).getAccp());
            System.out.println("a.get(i).getAccp():"+a.get(i).getAccp());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }

        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //月雨量
    @GetMapping("getrainbymonth")
    public JsonResult getRainByMonth(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {

        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyMonth(date, adcdlist, typelist, stcdlist, 0, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getAccp());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //年雨量
    @GetMapping("getrainbyyear")
    public JsonResult getRainByYear(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {

        /*dateStr="2017-06-04";
        stcdOrStnm="X";
        systemTypes="X";
        adcd="X";*/

        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyYear(date, adcdlist, typelist, stcdlist, 0, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getAccp());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //时段雨量
    @GetMapping("getrainbytime")
    public JsonResult getRainByTime(
            @RequestParam("dateS") String dateStart,
            @RequestParam("dateE") String dateEnd,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {

        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        System.out.println("开始时间" + dateStart);
        System.out.println("结束时间" + dateEnd);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
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
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyTime(dateS, dateE, adcdlist, typelist, stcdlist, 0, "ST_PPTN_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getNum());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //日雨量（专业）
    @GetMapping("getrainbydatezy")
    public JsonResult getRainByDateZY(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {

        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyDate(date, adcdlist, typelist, stcdlist, 0, "RP_PPTN_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getDyp());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //旬雨量（专业）
    @GetMapping("getrainbyxunzy")
    public JsonResult getRainByXunZY(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {
        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyXun(date, adcdlist, typelist, stcdlist, 0, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getAccp());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //月雨量(专业)
    @GetMapping("getrainbymonthzy")
    public JsonResult getRainByMonthZY(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {
        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        /*dateStr="2017-06-04";
        stcdOrStnm="X";
        systemTypes="X";
        adcd="X";*/

        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyMonth(date, adcdlist, typelist, stcdlist, 0, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getAccp());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //年雨量(专业)
    @GetMapping("getrainbyyearzy")
    public JsonResult getRainByYearZY(
            @RequestParam("date") String dateStr,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {
        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        /*dateStr="2017-06-04";
        stcdOrStnm="X";
        systemTypes="X";
        adcd="X";*/

        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                lylist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyYear(date, adcdlist, typelist, stcdlist, 0, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getAccp());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg = sum / countList.size();
        System.out.println("avg:"+avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }

    //时段雨量(专业)
    @GetMapping("getrainbytimezy")
    public JsonResult getRainByTimeZY(
            @RequestParam("dateS") String dateStart,
            @RequestParam("dateE") String dateEnd,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name = "ly", required = false) String ly) {

        System.out.println("开始时间" + dateStart);
        System.out.println("结束时间" + dateEnd);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);
        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        if (adcd.equals("X")) {
            adcdlist = null;
        } else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for (int i = 0; i < temp.length; i++) {
                adcdlist.add(temp[i]);
            }
        }
        if (systemTypes.equals("X")) {
            typelist = null;
        } else {
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                typelist.add(sytemp[i]);
            }
        }
        if (stcdOrStnm.equals("X")) {
            stcdlist = null;
        } else {
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for (int i = 0; i < sytemp.length; i++) {
                stcdlist.add(sytemp[i]);
            }
        }
        if (ly.equals("X")) {
            lylist = null;
        } else {
            ly = ly.substring(0, ly.length() - 1);
            String[] sytemp = ly.split(",");
            for (int i = 0; i < sytemp.length; i++) {
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
        List<Rainfall> a = (List<Rainfall>) rainfallService.getDaybyTime(dateS, dateE, adcdlist, typelist, stcdlist, 0, "RP_PPTN_R", benqu, db, lylist);
        //添加平均值
        List<Object> countList = new ArrayList<>();
        List<Object> avgList = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            countList.add(a.get(i).getStnm());
            avgList.add(a.get(i).getNum());
        }
        double sum = 0;
        for (int i = 0; i < avgList.size(); i++) {
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg = sum / countList.size();
        System.out.println(avg);
        return new JsonResult(a).setMessage("平均值为："+avg);
    }
}
