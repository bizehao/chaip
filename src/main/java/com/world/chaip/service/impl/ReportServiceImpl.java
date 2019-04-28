package com.world.chaip.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.world.chaip.entity.report.*;
import com.world.chaip.entity.report.gson.PptnGson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.world.chaip.mapper.ReportMapper;
import com.world.chaip.service.ReportService;
@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ReportMapper report;

	//水库水情统计
	@Override
	public List<Rsvr> getRsvrByTerm(String city, String tm1, String tm2, int type, String stcd) {
		List<Rsvr> list = report.getRsvrByTerm(city, tm1, tm2, type, stcd);
		return list;
	}
    //河道统计
	@Override
	public List<River> getRiverByTerm(String city, String tm1, String tm2, int type, String stcd) {
		List<River> list = report.getRiverByTerm(city, tm1, tm2, type, stcd);
		return list;
	}
    //降雨量统计(按时段)
	/*@Override
	public List<PptnGson> getPptnByTerm(String city, String tm, int type, String stcd) {
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<PptnGson> pgList = new ArrayList<>();
        try {
            Date date = sdf.parse(tm);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date tomorrow = calendar.getTime();
            String tm2 = sdf.format(tomorrow);
            String tm1 = tm + " " + "08:00:000";
            tm2 = tm2 + " " + "08:00:000";
            System.out.println(tm1);
            System.out.println(tm2);
            List<Pptn> list = report.getPptnByTerm(city, tm1, tm2, type, stcd);
            PptnGson pg = new PptnGson();
            //当天的集合
            Map<Date, Double> drpMap = new HashMap<>();
            double countDrp = 0;
            if(list.size()>1){
                for(int i = 0; i < list.size(); i++){
                    if(i != list.size()-1){
                        if(list.get(i).getStcd().equals(list.get(i+1).getStcd())){
                            pg.setStcd(list.get(i).getStcd());
                            pg.setStnm(list.get(i).getStnm());
                            pg.setType(list.get(i).getName());
                            drpMap.put(list.get(i).getTm(),list.get(i).getDrp());
                        }else{
                            pg = mapManager( drpMap,countDrp, pg );
                            pgList.add(pg);
                        }
                    }else{
                        if(list.get(i).getStcd().equals(list.get(i-1).getStcd())){
                            pg.setStcd(list.get(i-1).getStcd());
                            pg.setStnm(list.get(i-1).getStnm());
                            pg.setType(list.get(i-1).getName());
                            drpMap.put(list.get(i).getTm(),list.get(i).getDrp());
                            pg = mapManager( drpMap,countDrp, pg );
                            pgList.add(pg);
                        }else{
                            pg.setStcd(list.get(i).getStcd());
                            pg.setStnm(list.get(i).getStnm());
                            pg.setType(list.get(i).getName());
                            drpMap.put(list.get(i).getTm(),list.get(i).getDrp());
                            pg = mapManager( drpMap,countDrp, pg );
                            pgList.add(pg);
                        }
                    }
                }
            }else if(list.size() == 1){
                System.out.println(list.get(0).getStcd());
                pg.setStcd(list.get(0).getStcd());
                pg.setStnm(list.get(0).getStnm());
                pg.setType(list.get(0).getName());
                drpMap.put(list.get(0).getTm(),list.get(0).getDrp());
                pg = mapManager( drpMap,countDrp, pg );
                pgList.add(pg);
                System.out.println(pgList);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pgList;
	}*/
    //降雨量统计(按日)
    @Override
    public List<Pptn> getPptnByDate(String city, String tm, int type, String stcd) {
	    String stnm = null;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        boolean ismath =  pattern.matcher(stcd).matches();
        if(ismath == false){
            stnm = stcd;
            stcd = null;
        }
        //设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Pptn> list = null;
        try {
            Date date = sdf.parse(tm);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date tomorrow = calendar.getTime();
            String tm2 = sdf.format(tomorrow);
            String tm1 = tm + " " + "08:00:000";
            tm2 = tm2 + " " + "08:00:000";
            System.out.println(tm1);
            System.out.println(tm2);
            System.out.println(city+","+tm1+","+tm2+","+type+","+stcd+","+stnm);
            list = report.getPptnByDate(city, tm1, tm2, type, stcd, stnm);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
    //按日时的前多少个
    @Override
    public List getPptnByDateGetTerm(String city, String tm, int type, String stcd) {
        List<Pptn> list = getPptnByDate(city, tm, type, stcd);
        int hundred = 0;
        int Fifty = 0;
        int Thirty = 0;
        for(int i = 0; i<list.size(); i++){
            if(list.get(i).getDyp() > 100){
                hundred++;
            }
            if(list.get(i).getDyp() > 50){
                Fifty++;
            }
            if(list.get(i).getDyp() > 30){
                Fifty++;
            }
        }
        Collections.sort(list, new Comparator<Pptn>() {
            @Override
            public int compare(Pptn o1, Pptn o2) {
                return new Double(o1.getDyp()).compareTo(new Double(o2.getDyp()));
            }
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hundred",hundred);
        jsonObject.put("Fifty",Fifty);
        jsonObject.put("Thirty",Thirty);
        jsonObject.put("hundred",hundred);
        jsonObject.put("Fifty",Fifty);
        jsonObject.put("Thirty",Thirty);
        return null;
    }

    //加载市县
	@Override
	public List<City> getCity() {
		return report.getCity();
	}
    //根据市县获取相应的站点信息
	@Override
	public List<Stations> getStations(String adcd) {
		return report.getStations(adcd);
	}
    //获取站类信息
	@Override
	public List<Tyid> getTyid() {
		return report.getTyid();
	}
	//根据时段分割
	public PptnGson mapManager(Map<Date,String> drpMap, Double countDrp,PptnGson pg ){
        for(Map.Entry<Date, String> entry : drpMap.entrySet()){
            countDrp+=Double.parseDouble(entry.getValue());
            int hour = entry.getKey().getHours();
            switch (hour){
                case 9:
                    pg.setNineDrp(entry.getValue());
                    break;
                case 10:
                    pg.setTenDrp(entry.getValue());
                    break;
                case 11:
                    pg.setElevenDrp(entry.getValue());
                    break;
                case 12:
                    pg.setTwelveDrp(entry.getValue());
                    break;
                case 13:
                    pg.setThirteenDrp(entry.getValue());
                    break;
                case 14:
                    pg.setFourteenDrp(entry.getValue());
                    break;
                case 15:
                    pg.setFifteenDrp(entry.getValue());
                    break;
                case 16:
                    pg.setSixteenDrp(entry.getValue());
                    break;
                case 17:
                    pg.setSeventeenDrp(entry.getValue());
                    break;
                case 18:
                    pg.setEighteenDrp(entry.getValue());
                    break;
                case 19:
                    pg.setNineteenDrp(entry.getValue());
                    break;
                case 20:
                    pg.setTwentyDrp(entry.getValue());
                    break;
                case 21:
                    pg.setTwenty_oneDrp(entry.getValue());
                    break;
                case 22:
                    pg.setTwenty_twoDrp(entry.getValue());
                    break;
                case 23:
                    pg.setTwenty_threeDrp(entry.getValue());
                    break;
                case 0:
                    pg.setZeroDrp(entry.getValue());
                    break;
                case 1:
                    pg.setOneDrp(entry.getValue());
                    break;
                case 2:
                    pg.setTwoDrp(entry.getValue());
                    break;
                case 3:
                    pg.setThreeDrp(entry.getValue());
                    break;
                case 4:
                    pg.setFourDrp(entry.getValue());
                    break;
                case 5:
                    pg.setFiveDrp(entry.getValue());
                    break;
                case 6:
                    pg.setSixDrp(entry.getValue());
                    break;
                case 7:
                    pg.setSevenDrp(entry.getValue());
                    break;
                case 8:
                    pg.setEightDrp(entry.getValue());
                    break;
            }
        }
        pg.setCountDrp(countDrp);
        return pg;
    }
}
