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
		List<RsvrZhuanYe> totalRainfalls = new ArrayList<>();
		List<RevrXunQi> revrXunQis = rsvrfallMapper.getRsvrFS();
		Gson gson = new Gson();
		String ss = gson.toJson(revrXunQis);
		System.out.println(ss);
		//String topOneStcd = null;
		int topFstp = 0;
		if (stcdOrStnm == null) {
			int beginTimeOfInt = getRsverXunQi(dateS);
			for (RevrXunQi rxq : revrXunQis) {
				if (rxq.getXunQITimeList() != null && rxq.getXunQITimeList().size() > 0) {
					for (XunQITime xunQITime : rxq.getXunQITimeList()) {
						if (beginTimeOfInt >= xunQITime.getBgmd() && beginTimeOfInt <= xunQITime.getEdmd()) {
							rxq.setChooseFstp(xunQITime.getFstp());
							break;
						}
					}
					/*if (topOneStcd == null) {
						topOneStcd = rxq.getStcd();
					}*/
				}
				List<RsvrZhuanYe> rainfalls;
				if(topFstp == 0 && rxq.getChooseFstp() != 0){
					topFstp = rxq.getChooseFstp();
				}
				if (dateS.equals(dateE)) {
					rainfalls = rsvrfallMapper.getRsvrByZhaunYeNew(dateE, rxq.getChooseFstp(), adcd, systemTypes, rxq.getStcd(), ly);
				} else {
					rainfalls = rsvrfallMapper.getRsvrByZhaunYe(dateS, dateE, rxq.getChooseFstp(), adcd, systemTypes, rxq.getStcd(), ly);
				}
				totalRainfalls.addAll(rainfalls);
			}
		} else {
			for (String stcd : stcdOrStnm) {
				int beginTimeOfInt = getRsverXunQi(dateS);
				for (RevrXunQi rxq : revrXunQis) {
					if (stcd.equals(rxq.getStcd()) || stcd.equals(rxq.getStnm())) {
						for (XunQITime xunQITime : rxq.getXunQITimeList()) {
							if (beginTimeOfInt > xunQITime.getBgmd() && beginTimeOfInt < xunQITime.getEdmd()) {
								rxq.setChooseFstp(xunQITime.getFstp());
								break;
							}
						}
						if (rxq.getChooseFstp() != 0) {
							List<RsvrZhuanYe> rainfalls;
							if (dateS.getTime() == dateE.getTime()) {
								rainfalls = rsvrfallMapper.getRsvrByZhaunYeNew(dateE, rxq.getChooseFstp(), adcd, systemTypes, rxq.getStcd(), ly);
							} else {
								rainfalls = rsvrfallMapper.getRsvrByZhaunYe(dateS, dateE, rxq.getChooseFstp(), adcd, systemTypes, rxq.getStcd(), ly);
							}
							totalRainfalls.addAll(rainfalls);
						}
						break;
					}
				}
			}
		}

		double level = 0;
		List<String> levelList = new ArrayList<>();
		String levelS = "";
		int jilu = 0;

		List<RsvrZhuanYe> rsvrItem = null; //新增 处理多条
		RsvrZhuanYe rsvrZhuanYe; //新增 处理多条   新增  一个站有多条数据  其中最小的一个超过汛限水位,就算是超汛限水位
		List<RsvrZhuanYe> rsvrChao = new ArrayList<>(); //在汛限水位里包含的
		for (int i = 0; i < totalRainfalls.size(); i++) {

			RsvrZhuanYe rzy = totalRainfalls.get(i);

			rzy.setTm(ExcepTimeUtil.getExcepTime(totalRainfalls.get(i).getTm()));
			//*rainfalls.get(i).setTtcp(Double.parseDouble(new DecimalFormat("#0.00").format(rainfalls.get(i).getTtcp())));*//*
			rzy.setFsltdz(rzy.getFsltdz() == null ? "" : new DecimalFormat("#0.00").format(Double.parseDouble(rzy.getFsltdz())));
			//*rainfalls.get(i).setFsltdw(Double.parseDouble(new DecimalFormat("#0.00").format(rainfalls.get(i).getFsltdw())));*//*
			rzy.setRz(rzy.getRz() == null ? "" : new DecimalFormat("#0.00").format(Double.parseDouble(rzy.getRz())));
			rzy.setW(rzy.getW() == null ? "" : new DecimalFormat("#0.000").format(Double.parseDouble(rzy.getW())));
			rzy.setInq(rzy.getInq() == null ? "" : new DecimalFormat("#0.000").format(Double.parseDouble(rzy.getInq())));
			rzy.setOtq(rzy.getOtq() == null ? "" : new DecimalFormat("#0.000").format(Double.parseDouble(rzy.getOtq())));
			double x1 = 0;
			if (rzy.getInq() != null && !rzy.getInq().trim().equals("")) {
				x1 = Double.parseDouble(rzy.getInq());
			}
			double x2 = 0;
			if (rzy.getINQDR() != null && !rzy.getINQDR().trim().equals("")) {
				x2 = Double.parseDouble(rzy.getINQDR());
			}
			rzy.setInqOfDay(new DecimalFormat("#0.000").format(x1 * x2));
			rsvrChao.add(rzy);

		}
		String firstStcd = null;
		for (int i = 0; i < rsvrChao.size(); i++) {
			if (rsvrChao.get(i).getRWCHRCD() == 1) {
				rsvrChao.get(i).setRz("干涸");
			}
			if (rsvrItem != null) {
				if (firstStcd.equals(rsvrChao.get(i).getStcd())) {  // 111   22222    33  5
					rsvrZhuanYe = rsvrChao.get(i);
					rsvrItem.add(rsvrZhuanYe);
				} else {
					rsvrItem.sort((o1, o2) -> {
						double oo1 = 0;
						try {
							oo1 = Double.valueOf(o1.getRz());
						}
						catch ( NumberFormatException e) {
							oo1 = 0;
						}
						double oo2 = 0;
						try {
							oo2 = Double.valueOf(o2.getRz());
						}
						catch ( NumberFormatException e) {
							oo2 = 0;
						}
						if( oo1 > oo2 ){
							return 0;
						}
						return 1;
					});
					rsvrZhuanYe = rsvrItem.get(0); //处理
					double a = rsvrZhuanYe.getRz().trim().length() == 0 ? 0 : Double.parseDouble(rsvrZhuanYe.getRz());//rainfalls.get(i).getRz()
					double b = rsvrZhuanYe.getFsltdz().trim().length() == 0 ? 0 : Double.parseDouble(rsvrZhuanYe.getFsltdz());//rainfalls.get(i).getFsltdz()
					if (a >= b) {
						jilu++;
						level = a - b;
						levelS = rsvrZhuanYe.getStnm() + "水库，超汛限水位" + new DecimalFormat("#0.00").format(level) + "米";
						levelList.add(levelS);
					}

					rsvrItem = new ArrayList<>();
					rsvrZhuanYe = rsvrChao.get(i);
					rsvrItem.add(rsvrZhuanYe);
					firstStcd = rsvrZhuanYe.getStcd();
					if (i == rsvrChao.size() - 1) {
						rsvrZhuanYe = rsvrChao.get(i); //处理
						double ah = rsvrZhuanYe.getRz().trim().length() == 0 ? 0 : Double.parseDouble(rsvrZhuanYe.getRz());//rainfalls.get(i).getRz()
						double bh = rsvrZhuanYe.getFsltdz().trim().length() == 0 ? 0 : Double.parseDouble(rsvrZhuanYe.getFsltdz());//rainfalls.get(i).getFsltdz()
						if (ah >= bh) {
							jilu++;
							level = ah - bh;
							levelS = rsvrZhuanYe.getStnm() + "水库，超汛限水位" + new DecimalFormat("#0.00").format(level) + "米";
							levelList.add(levelS);
						}
					}
				}
			} else {
				rsvrItem = new ArrayList<>();
				rsvrZhuanYe = rsvrChao.get(i);
				firstStcd = rsvrZhuanYe.getStcd();
				rsvrItem.add(rsvrZhuanYe);
			}
		}

		StringBuilder head = new StringBuilder("目前有" + jilu + "处水库水位超过汛限水位");
		if (levelList.size() > 0) {
			for (String s : levelList) {
				head.append("其中").append(s).append(",");
			}
			head.substring(head.length());
			head.append("。");
		}
		levelList.add(0, head.toString());

		DayRsvr dayRsvr = new DayRsvr();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateS);
		int beginMonth = calendar.get(Calendar.MONTH) + 1;
		int beginDay = calendar.get(Calendar.DATE);
		int beginTM = Integer.parseInt(beginMonth + String.valueOf(beginDay));
		//XunQITime xunQITime = rsvrfallMapper.getXQTime(topOneStcd);
		/*if (xunQITime == null) {
			dayRsvr.setFstp("其它");
		} else {
			if (beginTM >= xunQITime.getBgmd() && beginTM <= xunQITime.getEdmd()) { //6-9月属于汛期
				dayRsvr.setFstp("主汛期");
			} else {
				dayRsvr.setFstp("其它");
			}
		}*/
		switch (topFstp){
			case 1:dayRsvr.setFstp("主汛期"); break;
			case 2:dayRsvr.setFstp("后汛期");break;
			case 3:dayRsvr.setFstp("过渡期"); break;
			case 4:dayRsvr.setFstp("其他"); break;
		}
		//排序 从大到小
		rsvrChao.sort((o1, o2) -> {
			if (o1.getTtcp() >= o2.getTtcp()) {
				return -1;
			} else {
				return 0;
			}
		});
		dayRsvr.setRsvrZhuanYeList(rsvrChao);
		dayRsvr.setLevels(head.toString());
		return dayRsvr;
	}

	/**
	 * 时间转为int
	 *
	 * @param time
	 * @return
	 */
	private int getRsverXunQi(Date time) {
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

}
