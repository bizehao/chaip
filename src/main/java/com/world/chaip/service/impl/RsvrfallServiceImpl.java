package com.world.chaip.service.impl;

import com.google.gson.Gson;
import com.world.chaip.entity.excelFormat.DayRsvr;
import com.world.chaip.entity.newRsvr.RevrXunQi;
import com.world.chaip.entity.newRsvr.XunQITime;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.RsvrZhuanYe;
import com.world.chaip.mapper.RsvrfallMapper;
import com.world.chaip.mapper.StationMapper;
import com.world.chaip.service.RsvrfallService;
import com.world.chaip.util.ExcepTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RsvrfallServiceImpl implements RsvrfallService {

	@Autowired
	RsvrfallMapper rsvrfallMapper;

	@Autowired
	StationMapper stationMapper;

	//水库 (实时)
	@Override
	public List<Rsvr> getRsvrByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, List<String> ly) throws ParseException {
		List<Rsvr> rainfalls;
		if (dateS.equals(dateE)) {
			rainfalls = rsvrfallMapper.getRsvrByTermNew(dateS, adcd, systemTypes, stcdOrStnm, ly);
		} else {
			rainfalls = rsvrfallMapper.getRsvrByTerm(dateS, dateE, adcd, systemTypes, stcdOrStnm, ly);
		}
        /*for(Rsvr rsvr : rainfalls){
            rsvr.setTm(ExcepTimeUtil.getExcepTime(rsvr.getTm()));
            if(rsvr.getRWCHRCD() == 1){
                rsvr.setRz("干涸");
            }
        }*/
		return rainfalls;
	}

	//水库 (专业)
	@Override
	public DayRsvr getRsvrByZhuanYe(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, List<String> ly) throws ParseException {
		DayRsvr dayRsvr = new DayRsvr();
        Map<String,List<XunQITime>> XunQiMap = new HashMap<>();
		List<RevrXunQi> revrXunQis = rsvrfallMapper.getRsvrFS();
        revrXunQis.forEach(a ->XunQiMap.put(a.getStcd(),a.getXunQITimeList()));
        if(dateS.equals(dateE)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateS);
			 dateE = calendar.getTime();
			 if(calendar.get(Calendar.HOUR)<=8){
			 	calendar.add(Calendar.DATE,-1);
			 }
			calendar.set(Calendar.HOUR, 8);
			 dateS = calendar.getTime();
		}
		List<RsvrZhuanYe> rainfalls = rsvrfallMapper.getRsvrByZhaunYe(dateS, dateE,adcd, systemTypes, stcdOrStnm, ly);
		List<String> levelList = new ArrayList<>();
		Map<String,String> Stcd = new HashMap<>();
		rainfalls.forEach(item ->{
					String TM = item.getTm();
					if(TM.indexOf(".")>0){
					TM = TM.substring(0,TM.indexOf("."));
					}
					item.setTm(TM);
					int XunQiRest =	getRsverXunQi(TM);
					List<XunQITime> xunQITimes = XunQiMap.get(item.getStcd());
					for(XunQITime a : xunQITimes) {
						if (XunQiRest >= a.getBgmd() && XunQiRest <= a.getEdmd()) {
							item.setFsltdz(a.getFSLTDZ());
							item.setFsltdw(a.getFSLTDW());
						    break;
						}
					}
					Double XunQiZ =item.getFsltdz()==null? 0.0:Double.parseDouble(item.getFsltdz());
					if(XunQiZ!=0.0){
					Double Z = item.getRz() == null?0.0:Double.parseDouble(item.getRz());
					if(Z >= XunQiZ){
						if(Stcd.get(item.getStcd())==null){
						Double CXK = (double) Math.round((Z-XunQiZ) * 100) / 100;
						levelList.add(item.getStnm() + "水库，超汛限水位" + CXK + "米");
                         Stcd.put(item.getStcd(),"此站已记录");
						}
					}
					}
				});
		Stcd.clear();
		StringBuilder sb = new StringBuilder("目前有" + levelList.size() + "处水库水位超过汛限水位");
		levelList.forEach(a->sb.append("其中").append(a).append(","));
		rainfalls.sort((o1, o2) -> {if (o1.getTtcp() >= o2.getTtcp()) {return -1;} else {return 0;}});
        dayRsvr.setFstp(getXunQi(dateS));
        dayRsvr.setLevels(sb.append("。").toString());
		dayRsvr.setRsvrZhuanYeList(rainfalls);
		return dayRsvr;
	}

	/**
	 * 时间转为int
	 *
	 * @param times
	 * @return
	 */
	private int getRsverXunQi(String times) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = null;
		try {
			time = sdf.parse(times);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String tmString = "";
		int tmInt = 0;
		Calendar now = Calendar.getInstance();
		now.setTime(time);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		if (day <= 9) {
			tmString = month + "0" + day;
		} else {
			tmString = month + String.valueOf(day);
		}
		tmInt = Integer.parseInt(tmString);
		return tmInt;
	}
    private String getXunQi(Date time){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int rsverXunQi = getRsverXunQi(sdf.format(time));
		if(rsverXunQi>=710&&rsverXunQi<=810){
          return "主汛期";
		}else if(rsverXunQi >= 821 && rsverXunQi <= 831){
			return "后汛期";
		}else if(rsverXunQi >= 811 && rsverXunQi <= 820){
			return "过渡期";
		} else {
			return "其他";
		}
	}



}
