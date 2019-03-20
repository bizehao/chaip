package com.world.chaip.service.impl;


import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.DaybyHourRainfall.DayByHourRainfallItem;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.excelFormat.DayRainExcel;
import com.world.chaip.entity.excelFormat.DayRainExcel.DayRain;
import com.world.chaip.entity.excelFormat.DayRainExcelX;
import com.world.chaip.entity.excelFormat.DayRainExcelX.DayRainX;
import com.world.chaip.entity.report.gson.PptnGson;
import com.world.chaip.mapper.RainfallMapper;
import com.world.chaip.service.RainfallService;
import com.world.chaip.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class RainfallServiceImpl implements RainfallService {

    @Autowired
    private RainfallMapper rainfallMapper;

    //逐时降雨量
    @Override
    public List<PptnGson> getDaybyHour(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int column, int sign, String db, List<String> ly) {
        Date beginTime = null;
        Date endTime = null;
        DaybyHourRainfall daybyHourRainfall = new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        int requestDay = now.get(Calendar.DATE);//用户请求的日
        int requesMonth = now.get(Calendar.MONTH) + 1;//用户请求的月
        int requesYear = now.get(Calendar.YEAR);//用户请求年份
        System.out.println("用户请求--年：" + requesYear + "月：" + requesMonth + "日：" + requestDay);
        beginTime = now.getTime();
        endTime = DateUtils.getDateAfter(beginTime, 1);
        System.out.println("beginTime:" + beginTime.toString());
        System.out.println("endTime:" + endTime.toString());
        List<Rainfall> rainfalls = rainfallMapper.selectByTm(beginTime, endTime, adcd, systemTypes, stcdOrStnm, db, ly);
        System.out.println("从数据库里查询的数据：rainfalls：" + rainfalls.size());
        Date currentDate = new Date();
        now.setTime(currentDate);
        int currentHour = now.get(Calendar.HOUR_OF_DAY);//当前时
        int currentDay = now.get(Calendar.DATE);//当前日
        int currentMonth = now.get(Calendar.MONTH) + 1;//用户请求的月
        int currentYear = now.get(Calendar.YEAR);//用户请求年份
        System.out.println("当前时间--年：" + currentYear + "月：" + currentMonth + "日：" + currentDay);

        if (rainfalls.size() > 0) {
            for (int i = 0; i < rainfalls.size(); i++) {
                System.out.println("rainfalls.get(" + i + "):" + rainfalls.get(i));
                DayByHourRainfallItem tempItem = null;
                for (int j = 0; j < daybyHourRainfall.getData().size(); j++) {
                    DayByHourRainfallItem tempItemX = daybyHourRainfall.getData().get(j);

                    if (tempItemX.getStcd().equals(rainfalls.get(i).getStcd())) {
                        tempItem = tempItemX;
                        if (rainfalls.get(i).getDrp() == null || rainfalls.get(i).getDrp() == 0.0) {
                            tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), "0");
                        } else {
                            tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), String.valueOf(rainfalls.get(i).getDrp()));
                        }
                        /*System.out.println((double) rainfalls.get(i).getTm().getHours()+","+rainfalls.get(i).getDrp());*/
                        /*System.out.println(rainfalls.get(i).getDrp());
                        dyp+=rainfalls.get(i).getDrp();*/
                        if (i == rainfalls.size() - 1) {
                            double count = tempItem.testValues();
                            tempItem.setCountDrp(count);
                            /*System.out.println("sads"+count);*/
                        }
                        break;
                    } else {
                        tempItem = tempItemX;
                        double count = tempItem.testValues();
                        tempItem.setCountDrp(count);
                        tempItem = null;
                        /*System.out.println("sads"+count);*/
                    }
                }
                /*System.out.println(dyp);*/
                if (tempItem == null) {
                    tempItem = new DaybyHourRainfall.DayByHourRainfallItem();
                    tempItem.setStcd(rainfalls.get(i).getStcd());
                    tempItem.setStnm(rainfalls.get(i).getStnm());
                    tempItem.setLgtd(rainfalls.get(i).getLgtd());
                    tempItem.setLttd(rainfalls.get(i).getLttd());
                    tempItem.setName(rainfalls.get(i).getName());
                    tempItem.setAdnm(rainfalls.get(i).getAdnm());
                    if (rainfalls.get(i).getDrp() == null || rainfalls.get(i).getDrp() == 0.0) {
                        tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), "0");
                    } else {
                        tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), String.valueOf(rainfalls.get(i).getDrp()));
                    }
                    if (i == rainfalls.size() - 1) {
                        double count = tempItem.testValues();
                        tempItem.setCountDrp(count);
                        /*System.out.println("sads"+count);*/
                    }
                    daybyHourRainfall.getData().add(tempItem);
                    /*tempItem.setDyp(dyp);*/
                }
            }
        }
        List<PptnGson> list = new ArrayList<>();
        PptnGson pptnGson = null;
        PptnGson avgRain = new PptnGson(); //平均降雨量
        avgRain.setAdnm("平均");

        for (int avi = 0; avi <= 23; avi++) {
            avgRain.getDrpMap().put(avi, 0);
        }

        for (int i = 0; i < daybyHourRainfall.getData().size(); i++) {
            pptnGson = new PptnGson();
            pptnGson.setAdnm(daybyHourRainfall.getData().get(i).getAdnm());
            pptnGson.setStnm(daybyHourRainfall.getData().get(i).getStnm());
            pptnGson.setName(daybyHourRainfall.getData().get(i).getName());
            pptnGson.setCountDrp(daybyHourRainfall.getData().get(i).getCountDrp());
            avgRain.setCountDrp(avgRain.getCountDrp() + pptnGson.getCountDrp()); //平均值
            pptnGson.setNineDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(9.0));
            pptnGson.setTenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(10.0));
            pptnGson.setElevenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(11.0));
            pptnGson.setTwelveDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(12.0));
            pptnGson.setThirteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(13.0));
            pptnGson.setFourteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(14.0));
            pptnGson.setFifteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(15.0));
            pptnGson.setSixteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(16.0));
            pptnGson.setSeventeenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(17.0));
            pptnGson.setEighteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(18.0));
            pptnGson.setNineteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(19.0));
            pptnGson.setTwentyDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(20.0));
            pptnGson.setTwenty_oneDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(21.0));
            pptnGson.setTwenty_twoDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(22.0));
            pptnGson.setTwenty_threeDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(23.0));
            pptnGson.setZeroDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(0.0));
            pptnGson.setOneDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(1.0));
            pptnGson.setTwoDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(2.0));
            pptnGson.setThreeDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(3.0));
            pptnGson.setFourDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(4.0));
            pptnGson.setFiveDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(5.0));
            pptnGson.setSixDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(6.0));
            pptnGson.setSevenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(7.0));
            pptnGson.setEightDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(8.0));

            Map<Object, Object> map = new LinkedHashMap<>();
            //
            if (requestDay == currentDay - 1 && requesMonth == currentMonth && requesYear == currentYear) { //当前日期未发生,将未发生的数据更改  requestDay == currentDay
                Double cishi = (double) currentHour;
                for (Double na = 9.0; na <= 23; na++) {
                    if (daybyHourRainfall.getData().get(i).getHourRainfalls().containsKey(na)) {
                        map.put(na, daybyHourRainfall.getData().get(i).getHourRainfalls().get(na));
                    } else {
                        map.put(na, 0);
                    }
                }
                for (Double na = 0.0; na <= cishi + 1; na++) {
                    if (daybyHourRainfall.getData().get(i).getHourRainfalls().containsKey(na)) {
                        map.put(na, daybyHourRainfall.getData().get(i).getHourRainfalls().get(na));
                    } else {
                        map.put(na, 0);
                    }
                }
                for (Double nb = cishi + 2; nb < 9; nb++) {
                    map.put(nb, " ");
                }
                //&& requesMonth==currentMonth && requesYear==currentYear
            } else if (requestDay == currentDay && requesMonth == currentMonth && requesYear == currentYear) {
                Double cishi = (double) currentHour;
                if (cishi == 23) {
                    for (Double na = 9.0; na <= 23; na++) {
                        if (daybyHourRainfall.getData().get(i).getHourRainfalls().containsKey(na)) {
                            map.put(na, daybyHourRainfall.getData().get(i).getHourRainfalls().get(na));
                        } else {
                            map.put(na, 0);
                        }
                    }
                    if (daybyHourRainfall.getData().get(i).getHourRainfalls().containsKey(0.0)) {
                        map.put(0.0, daybyHourRainfall.getData().get(i).getHourRainfalls().get(0.0));
                    } else {
                        map.put(0.0, 0);
                    }
                    for (Double na = 1.0; na < 9; na++) {
                        map.put(na, " ");
                    }

                } else {
                    for (Double na = 9.0; na <= cishi + 1; na++) {
                        if (daybyHourRainfall.getData().get(i).getHourRainfalls().containsKey(na)) {
                            map.put(na, daybyHourRainfall.getData().get(i).getHourRainfalls().get(na));
                        } else {
                            map.put(na, 0);
                        }
                    }
                    for (Double na = cishi + 2; na <= 23; na++) {
                        map.put(na, " ");
                    }
                    for (Double na = 0.0; na < 9; na++) {
                        map.put(na, " ");
                    }
                }
            } else {
                for (Double nd = 0.0; nd <= 23; nd++) {
                    if (daybyHourRainfall.getData().get(i).getHourRainfalls().containsKey(nd)) {
                        map.put(nd, daybyHourRainfall.getData().get(i).getHourRainfalls().get(nd));
                    } else {
                        map.put(nd, " ");
                    }
                }
            }


            pptnGson.setDrpMap(map);

            list.add(pptnGson);

            //avg map
            for (int avi = 0; avi <= 23; avi++) {
                double yuanshi = (double) avgRain.getDrpMap().get(avi);
                double xintian = pptnGson.getDrpMap().get(avi) == " " ? 0 : (double) pptnGson.getDrpMap().get(avi);
                avgRain.getDrpMap().replace(avi, yuanshi + xintian);
            }

        }
        if (column != -1) {
            list = getListPX(list, column, sign);
        }

        avgRain.setCountDrp(avgRain.getCountDrp()/list.size());

        for (int avi = 0; avi <= 23; avi++) {
            avgRain.getDrpMap().replace(avi, (double)avgRain.getDrpMap().get(avi)/list.size());
        }
        list.add(avgRain);
        return list;
    }

    //日降雨量
    @Override
    public Object getDaybyDate(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn, String benqu, String db, List<String> ly) {
        Date beginTime = null;
        Date endTime = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime = now.getTime();
        endTime = DateUtils.getDateAfter(beginTime, 1);
        List<Rainfall> list = rainfallMapper.selectByDate(endTime, adcd, systemTypes, stcdOrStnm, pptn, benqu, db, ly);
        if (cid == 0) {
            return list;
        }
		/*for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i).getStcd()+""+list.get(i).getStnm());
		}*/
        System.out.println("日降水量的记录数" + list.size());
        Object[] objects = null;
        DayRainExcel dayRainExcel = new DayRainExcel();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                DayRain dayRain = null;
                for (int j = 0; j < dayRainExcel.getDayRainList().size(); j++) {
                    DayRain dayRainX = dayRainExcel.getDayRainList().get(j);
                    if (dayRainX.getAdnm().equals(list.get(i).getAdnm())) {
                        dayRain = dayRainX;
                        objects = new Object[2];
                        objects[0] = list.get(i).getStnm();
                        objects[1] = list.get(i).getDyp();
                        dayRain.getDayRainArray().add(objects);
                        /*System.out.println(list.get(i).getStnm());*/
                        break;
                    }
                }
                if (dayRain == null) {
                    dayRain = dayRainExcel.new DayRain();
                    dayRain.setAdnm(list.get(i).getAdnm());
                    objects = new Object[2];
                    objects[0] = list.get(i).getStnm();
                    objects[1] = list.get(i).getDyp();
                    dayRain.getDayRainArray().add(objects);
                    /*System.out.println(list.get(i).getStnm());*/
                    dayRainExcel.getDayRainList().add(dayRain);
                }
            }
        }
        /*System.out.println("=============================");*/
		/*for(int i=0; i<dayRainExcel.getDayRainList().size(); i++){
			System.out.println(dayRainExcel.getDayRainList().get(i).getAdnm());
			Map<String, Double> map =  dayRainExcel.getDayRainList().get(i).getDayRainList();
			System.out.println(map.size());
			for (Map.Entry<String, Double> entry : map.entrySet()) {
				System.out.println("Key = 1" + entry.getKey() + ", Value = 1" + entry.getValue());
			}
		}*/
        return dayRainExcel;
    }

    //旬降雨量
    @Override
    public Object getDaybyXun(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn, String patat, String benqu, String db, List<String> ly) {
        Date Time = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time = now.getTime();
        Date beginTime = null;
        System.out.println(Calendar.DATE);
        if (now.get(Calendar.DATE) == 11) {
            now.set(Calendar.DATE, 2);
        } else if (now.get(Calendar.DATE) == 21) {
            now.set(Calendar.DATE, 12);
        } else {
            now.add(Calendar.MONTH, -1);
            now.set(Calendar.DATE, 22);
        }

        beginTime = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByXun(Time, beginTime, adcd, systemTypes, stcdOrStnm, pptn, patat, benqu, db, ly);
        if (cid == 0) {
            return list;
        }
        return getXYN(list);
    }

    //月降雨量
    @Override
    public Object getDaybyMonth(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn, String patat, String benqu, String db, List<String> ly) {
        Date Time = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time = now.getTime();
        Date beginTime = null;
        now.add(Calendar.MONTH, -1);
        now.add(Calendar.DATE, 1);
        beginTime = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByMonth(Time, beginTime, adcd, systemTypes, stcdOrStnm, pptn, patat, benqu, db, ly);
        if (cid == 0) {
            return list;
        }
        return getXYN(list);
    }

    //年降雨量
    @Override
    public Object getDaybyYear(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn, String patat, String benqu, String db, List<String> ly) {
        Date Time = null;
        DaybyHourRainfall daybyHourRainfall = new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time = now.getTime();
        Date beginTime = null;
        now.add(Calendar.YEAR, -1);
        now.add(Calendar.DATE, 1);
        beginTime = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByYear(Time, beginTime, adcd, systemTypes, stcdOrStnm, pptn, patat, benqu, db, ly);
        if (cid == 0) {
            return list;
        }
        return getXYN(list);
    }

    //时段降雨量
    @Override
    public Object getDaybyTime(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn, String benqu, String db, List<String> ly) {
        Calendar now = Calendar.getInstance();
        now.setTime(dateE);
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DATE);
        int hour = now.get(Calendar.HOUR_OF_DAY) - 1;
        int mounte = now.get(Calendar.MINUTE);
        now.set(year, month, day, hour, mounte, 0);
        now.set(Calendar.MILLISECOND, 0);
        Date NowTime = now.getTime();
        NowTime.setMinutes(0);
        System.out.println(NowTime);
        //开始时间
        now.setTime(dateS);
        //now.set(Calendar.MINUTE,0);
        dateS = now.getTime();
        //结束时间
        now.setTime(dateE);
        //now.set(Calendar.MINUTE,0);
        dateE = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByTime(dateS, dateE, NowTime, adcd, systemTypes, stcdOrStnm, pptn, benqu, db, ly);
        if (cid == 0) {
            return list;
        }
        DayRainExcelX dayRainExcelX = new DayRainExcelX();
        Object[] obRainX = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                DayRainX dayRainM = null;
                for (int j = 0; j < dayRainExcelX.getDayRainXList().size(); j++) {
                    DayRainX dayRainX = dayRainExcelX.getDayRainXList().get(j);
                    if (dayRainX.getAdnm().equals(list.get(i).getAdnm())) {
                        dayRainM = dayRainX;
                        obRainX = new Object[3];
                        obRainX[0] = list.get(i).getStnm();
                        obRainX[1] = list.get(i).getNum();
                        obRainX[2] = list.get(i).getDrp();
                        dayRainM.getRainList().add(obRainX);
                        break;
                    }
                }
                if (dayRainM == null) {
                    dayRainM = dayRainExcelX.new DayRainX();
                    dayRainM.setAdnm(list.get(i).getAdnm());
                    obRainX = new Object[3];
                    obRainX[0] = list.get(i).getStnm();
                    obRainX[1] = list.get(i).getNum();
                    obRainX[2] = list.get(i).getDrp();
                    dayRainM.getRainList().add(obRainX);
                    dayRainExcelX.getDayRainXList().add(dayRainM);
                }
            }
        }
		/*for(int i=0;i<dayRainExcelX.getDayRainXList().size();i++){
		    for(int j=0;j<dayRainExcelX.getDayRainXList().get(i).getRainList().size();j++){
                System.out.println(dayRainExcelX.getDayRainXList().get(i).getRainList().get(j)[0]);
                System.out.println(dayRainExcelX.getDayRainXList().get(i).getRainList().get(j)[1]);
                System.out.println(dayRainExcelX.getDayRainXList().get(i).getRainList().get(j)[2]);
                System.out.println("=======================================");
            }
        }*/
        return dayRainExcelX;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //逐时降雨量计算
    @Override
    public String getDaybyHourJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String db, List<String> ly) {
        Date beginTime = null;
        Date endTime = null;
        DaybyHourRainfall daybyHourRainfall = new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime = now.getTime();
        endTime = DateUtils.getDateAfter(beginTime, 1);
        System.out.println(beginTime + "," + endTime);
        List<Rainfall> rainfalls = rainfallMapper.selectByTm(beginTime, endTime, adcd, systemTypes, stcdOrStnm, db, ly);
        if (rainfalls != null && rainfalls.size() > 0) {
            for (int i = 0; i < rainfalls.size(); i++) {
                DayByHourRainfallItem tempItem = null;
                for (int j = 0; j < daybyHourRainfall.getData().size(); j++) {
                    DayByHourRainfallItem tempItemX = daybyHourRainfall.getData().get(j);
                    if (tempItemX.getStcd().equals(rainfalls.get(i).getStcd())) {
                        tempItem = tempItemX;
                        if (rainfalls.get(i).getDrp() == null) {
                            tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), "0.0");
                        } else {
                            tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), String.valueOf(rainfalls.get(i).getDrp()));
                        }
                        if (i == rainfalls.size() - 1) {
                            tempItem = tempItemX;
                            double count = tempItem.testValues();
                            tempItem.setCountDrp(count);
                        }
                        break;
                    } else {
                        tempItem = tempItemX;
                        double count = tempItem.testValues();
                        tempItem.setCountDrp(count);
                        tempItem = null;
                    }
                }
                if (tempItem == null) {
                    tempItem = new DaybyHourRainfall.DayByHourRainfallItem();
                    tempItem.setStcd(rainfalls.get(i).getStcd());
                    tempItem.setStnm(rainfalls.get(i).getStnm());
                    tempItem.setLgtd(rainfalls.get(i).getLgtd());
                    tempItem.setLttd(rainfalls.get(i).getLttd());
                    tempItem.setName(rainfalls.get(i).getName());
                    tempItem.setAdnm(rainfalls.get(i).getAdnm());
                    if (rainfalls.get(i).getDrp() == null) {
                        tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), "0.0");
                    } else {
                        tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), String.valueOf(rainfalls.get(i).getDrp()));
                    }
                    daybyHourRainfall.getData().add(tempItem);
                }
            }
        }
        int hundred = 0;
        int Fifty = 0;
        int Thirty = 0;

        bgm:
        for (int i = 0; i < daybyHourRainfall.getData().size(); i++) {
            Map<Double, String> map = daybyHourRainfall.getData().get(i).getHourRainfalls();
            int hundredmap = 0;
            int Fiftymap = 0;
            int Thirtymap = 0;
            for (String values : map.values()) {
                Double value = Double.parseDouble(values);
                if (Thirtymap != 1) {
                    if (value >= 30) {
                        Thirty++;
                        Thirtymap++;
                    }
                }
                if (Fiftymap != 1) {
                    if (value >= 50) {
                        Fifty++;
                        Fiftymap++;
                    }
                }
                if (hundredmap != 1) {
                    if (value >= 100) {
                        hundred++;
                        hundredmap++;
                    }
                }
                if (hundredmap == 1 && Fiftymap == 1 && Thirtymap == 1) {
                    continue bgm;
                }
            }
        }
        String xianshi = "一小时 超过100毫米的有：" + hundred + "个站;" + "超过50毫米的有：" + Fifty + "个站;" + "超过30毫米的有：" + Thirty + "个站.";
        return xianshi;
    }

    //日降雨量计算
    @Override
    public String getDaybyDateJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn, String benqu, String db, List<String> ly) {
        Date beginTime = null;
        Date endTime = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime = now.getTime();
        endTime = DateUtils.getDateAfter(beginTime, 1);
        List<Rainfall> list = rainfallMapper.selectByDate(endTime, adcd, systemTypes, stcdOrStnm, pptn, benqu, db, ly);
        String xianshi = coonPC(list, 1);
        return xianshi;
    }

    //旬降雨量计算
    @Override
    public String getDaybyXunJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn, String patat, String benqu, String db, List<String> ly) {
        Date Time = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time = now.getTime();
        Date beginTime = null;
        now.add(Calendar.DATE, -9);
        beginTime = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByXun(Time, beginTime, adcd, systemTypes, stcdOrStnm, pptn, patat, benqu, db, ly);
        String xianshi = coonPC(list, 2);
        return xianshi;
    }

    //月降雨量计算
    @Override
    public String getDaybyMonthJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn, String patat, String benqu, String db, List<String> ly) {
        Date Time = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time = now.getTime();
        Date beginTime = null;
        now.add(Calendar.MONTH, -1);
        now.add(Calendar.DATE, 1);
        beginTime = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByMonth(Time, beginTime, adcd, systemTypes, stcdOrStnm, pptn, patat, benqu, db, ly);
        String xianshi = coonPC(list, 2);
        return xianshi;
    }

    //年降雨量计算
    @Override
    public String getDaybyYearJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn, String patat, String benqu, String db, List<String> ly) {
        Date Time = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time = now.getTime();
        Date beginTime = null;
        now.add(Calendar.YEAR, -1);
        now.add(Calendar.DATE, 1);
        beginTime = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByYear(Time, beginTime, adcd, systemTypes, stcdOrStnm, pptn, patat, benqu, db, ly);
        String xianshi = coonPC(list, 2);
        return xianshi;
    }

    //时段降雨量计算
    @Override
    public String getDaybyTimeJS(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn, String benqu, String db, List<String> ly) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH + 1);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        now.set(year, month, day, hour, 0, 0);
        now.set(Calendar.MILLISECOND, 0);
        Date NowTime = now.getTime();
        //开始时间
        now.setTime(dateS);
        now.set(Calendar.MINUTE, 0);
        dateS = now.getTime();
        //结束时间
        now.setTime(dateE);
        now.set(Calendar.MINUTE, 0);
        dateE = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByTime(dateS, dateE, NowTime, adcd, systemTypes, stcdOrStnm, pptn, benqu, db, ly);
        String xianshi = coonPC(list, 3);
        return xianshi;
    }

    //list集合排序    1=日  2= 旬月年  3 = 时段
    public String coonPC(List<Rainfall> list, int sign) {
        int hundred = 0;
        int Fifty = 0;
        int Thirty = 0;
        double dyp = 0;
        double x = 0;
        if (sign == 1) {
            for (int i = 0; i < list.size(); i++) {
                x = list.get(i).getDyp();
                if (list.get(i).getDyp() != null) {
                    dyp = x;
                }
                if (dyp >= 100) {
                    hundred++;
                }
                if (dyp >= 50) {
                    Fifty++;
                }
                if (dyp >= 30) {
                    Thirty++;
                }
            }
            Collections.sort(list, new Comparator<Rainfall>() {
                @Override
                public int compare(Rainfall o1, Rainfall o2) {
                    if (o1.getDyp() > o2.getDyp()) {
                        return -1;
                    }
                    if (o1.getDyp().equals(o2.getDyp())) {
                        return 0;
                    }
                    return 1;
                }
            });
        } else if (sign == 2) {
            for (int i = 0; i < list.size(); i++) {
                x = list.get(i).getAccp();
                if (x != 0) {
                    dyp = x;
                }
                if (dyp >= 100) {
                    hundred++;
                }
                if (dyp >= 50) {
                    Fifty++;
                }
                if (dyp >= 30) {
                    Thirty++;
                }
            }
            Collections.sort(list, new Comparator<Rainfall>() {
                @Override
                public int compare(Rainfall o1, Rainfall o2) {
                    if (o1.getAccp() > o2.getAccp()) {
                        return -1;
                    }
                    if (o1.getAccp().equals(o2.getAccp())) {
                        return 0;
                    }
                    return 1;
                }
            });
        } else {
            for (int i = 0; i < list.size(); i++) {
                x = list.get(i).getNum();
                if (x != 0) {
                    dyp = x;
                }
                if (dyp >= 100) {
                    hundred++;
                }
                if (dyp >= 50) {
                    Fifty++;
                }
                if (dyp >= 30) {
                    Thirty++;
                }
            }
            Collections.sort(list, new Comparator<Rainfall>() {
                @Override
                public int compare(Rainfall o1, Rainfall o2) {
                    if (o1.getNum() > o2.getNum()) {
                        return -1;
                    }
                    if (o1.getNum().equals(o2.getNum())) {
                        return 0;
                    }
                    return 1;
                }
            });
        }
        String xianshi = "";
        double y1 = 0;
        double y2 = 1;
        double y3 = 2;
        if (list.size() != 0) {
            if (sign == 0) {
                if (list.size() == 1) {
                    y1 = list.get(0).getDrp();
                } else if (list.size() == 2) {
                    y1 = list.get(0).getDrp();
                    y2 = list.get(1).getDrp();
                } else {
                    y1 = list.get(0).getDrp();
                    y2 = list.get(1).getDrp();
                    y3 = list.get(2).getDrp();
                }
            } else if (sign == 1) {
                if (list.size() == 1) {
                    y1 = list.get(0).getDyp();
                } else if (list.size() == 2) {
                    y1 = list.get(0).getDyp();
                    y2 = list.get(1).getDyp();
                } else {
                    y1 = list.get(0).getDyp();
                    y2 = list.get(1).getDyp();
                    y3 = list.get(2).getDyp();
                }
            } else if (sign == 2) {
                if (list.size() == 1) {
                    y1 = list.get(0).getAccp();
                } else if (list.size() == 2) {
                    y1 = list.get(0).getAccp();
                    y2 = list.get(1).getAccp();
                } else {
                    y1 = list.get(0).getAccp();
                    y2 = list.get(1).getAccp();
                    y3 = list.get(2).getAccp();
                }
            } else {
                if (list.size() == 1) {
                    y1 = list.get(0).getNum();
                } else if (list.size() == 2) {
                    y1 = list.get(0).getNum();
                    y2 = list.get(1).getNum();
                } else {
                    y1 = list.get(0).getNum();
                    y2 = list.get(1).getNum();
                    y3 = list.get(2).getNum();
                }
            }
            String one = "";
            String two = "";
            String three = "";
            if (list.size() == 1) {
                one = "最大点是" + list.get(0).getAdnm() + "的" + list.get(0).getStnm() + "站、降雨量是" + y1 + "mm";
            } else if (list.size() == 2) {
                one = "最大点是" + list.get(0).getAdnm() + "的" + list.get(0).getStnm() + "站、降雨量是" + y1 + "mm";
                two = "次大点是" + list.get(1).getAdnm() + "的" + list.get(1).getStnm() + "站、降雨量是" + y2 + "mm";
            } else {
                one = "最大点是" + list.get(0).getAdnm() + "的" + list.get(0).getStnm() + "站、降雨量是" + y1 + "mm";
                two = "次大点是" + list.get(1).getAdnm() + "的" + list.get(1).getStnm() + "站、降雨量是" + y2 + "mm";
                three = "再次大点是" + list.get(2).getAdnm() + "的" + list.get(2).getStnm() + "站、降雨量是" + y3 + "mm";
            }
            if (sign == 2) {
                xianshi = one + "," + two + "," + three + ".";
            } else {
                xianshi = "降雨量超过100mm的有" + hundred + "个站," + "降雨量超过50mm的有" + Fifty + "个站," + "降雨量超过30mm的有" + Thirty + "个站," + one + "," + two + "," + three + ".";

            }
        } else {
            if (sign == 2) {
                xianshi = "最大点是_县的_站、降雨量是0.0mm," + "次大点是_县的_站、降雨量是0.0mm," + "再次大点是_县的_站、降雨量是0.0mm";
            } else {
                xianshi = "降雨量超过100mm的有0个站," + "降雨量超过50mm的有0个站," + "降雨量超过30mm的有0个站," + "最大点是_县的_站、降雨量是0.0mm," + "次大点是_县的_站、降雨量是0.0mm," + "再次大点是_县的_站、降雨量是0.0mm";
            }
        }
        return xianshi;
    }

    //处理旬月年
    public DayRainExcelX getXYN(List<Rainfall> list) {
        DayRainExcelX dayRainExcelX = new DayRainExcelX();
        Object[] obRainX = null;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                DayRainX dayRainM = null;
                for (int j = 0; j < dayRainExcelX.getDayRainXList().size(); j++) {
                    DayRainX dayRainX = dayRainExcelX.getDayRainXList().get(j);
                    if (dayRainX.getAdnm().equals(list.get(i).getAdnm())) {
                        dayRainM = dayRainX;
                        obRainX = new Object[3];
                        obRainX[0] = list.get(i).getStnm();
                        obRainX[1] = list.get(i).getAccp();
                        obRainX[2] = new DecimalFormat("#").format(list.get(i).getNum());
                        dayRainM.getRainList().add(obRainX);
                        break;
                    }
                }
                if (dayRainM == null) {
                    dayRainM = dayRainExcelX.new DayRainX();
                    dayRainM.setAdnm(list.get(i).getAdnm());
                    obRainX = new Object[3];
                    obRainX[0] = list.get(i).getStnm();
                    obRainX[1] = list.get(i).getAccp();
                    obRainX[2] = new DecimalFormat("#").format(list.get(i).getNum());
                    dayRainM.getRainList().add(obRainX);
                    dayRainExcelX.getDayRainXList().add(dayRainM);
                }
            }
        }
		/*for(int i=0; i<dayRainExcelX.getDayRainXList().size();i++){
		    for (int j=0; j<dayRainExcelX.getDayRainXList().get(i).getRainList().size();j++){
                System.out.println("导出的"+dayRainExcelX.getDayRainXList().get(i).getRainList().get(j)[0]);
            }

        }*/
        return dayRainExcelX;
    }

    public List<PptnGson> getListPX(List<PptnGson> list, int columns, int sign) {
        double column = columns;
        if (column == 24.0) {
            Collections.sort(list, new Comparator<PptnGson>() {
                @Override
                public int compare(PptnGson o1, PptnGson o2) {
                    if (sign == 0) {  //正序
                        if (o1.getCountDrp() > o2.getCountDrp()) {
                            return 1;
                        }
                        if (o1.getCountDrp() == o2.getCountDrp()) {
                            return 0;
                        }
                        return -1;
                    } else {     //倒序
                        if (o1.getCountDrp() > o2.getCountDrp()) {
                            return -1;
                        }
                        if (o1.getCountDrp() == o2.getCountDrp()) {
                            return 0;
                        }
                        return 1;
                    }
                }
            });
        } else {
            Collections.sort(list, new Comparator<PptnGson>() {
                @Override
                public int compare(PptnGson o1, PptnGson o2) {
                    if (o1.getDrpMap().get(column) == null || o1.getDrpMap().get(column) == "") {
                        o1.getDrpMap().put(column, 0);
                    }
                    if (o2.getDrpMap().get(column) == null || o2.getDrpMap().get(column) == "") {
                        o2.getDrpMap().put(column, 0);
                    }
                    double ce1 = Double.parseDouble(o1.getDrpMap().get(column).toString().equals(" ") ? "0" : o1.getDrpMap().get(column).toString());
                    double ce2 = Double.parseDouble(o2.getDrpMap().get(column).toString().equals(" ") ? "0" : o2.getDrpMap().get(column).toString());
                    if (sign == 0) { //正序
                        if (ce1 > ce2) {
                            return 1;
                        }
                        if (ce1 == ce2) {
                            return 0;
                        }
                        return -1;
                    } else { //倒序
                        if (ce1 > ce2) {
                            return -1;
                        }
                        if (ce1 == ce2) {
                            return 0;
                        }
                        return 1;
                    }
                }
            });
        }

        /*switch (column) {
            case 9:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getNineDrp()==null || o1.getNineDrp().equals("")){
                            o1.setNineDrp("0");
                        }
                        if(o2.getNineDrp()==null || o2.getNineDrp().equals("")){
                            o2.setNineDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getNineDrp()) > Double.parseDouble(o2.getNineDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getNineDrp()) == Double.parseDouble(o2.getNineDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getNineDrp()) > Double.parseDouble(o2.getNineDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getNineDrp()) == Double.parseDouble(o2.getNineDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 10:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getTenDrp()==null || o1.getTenDrp().equals("")){
                            o1.setTenDrp("0");
                        }
                        if(o2.getTenDrp()==null || o2.getTenDrp().equals("")){
                            o2.setTenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getTenDrp()) > Double.parseDouble(o2.getTenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getTenDrp()) == Double.parseDouble(o2.getTenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getTenDrp()) > Double.parseDouble(o2.getTenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getTenDrp()) == Double.parseDouble(o2.getTenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 11:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getElevenDrp()==null || o1.getElevenDrp().equals("")){
                            o1.setElevenDrp("0");
                        }
                        if(o2.getElevenDrp()==null || o2.getElevenDrp().equals("")){
                            o2.setElevenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getElevenDrp()) > Double.parseDouble(o2.getElevenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getElevenDrp()) == Double.parseDouble(o2.getElevenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getElevenDrp()) > Double.parseDouble(o2.getElevenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getElevenDrp()) == Double.parseDouble(o2.getElevenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 12:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getTwelveDrp()==null || o1.getTwelveDrp().equals("")){
                            o1.setTwelveDrp("0");
                        }
                        if(o2.getTwelveDrp()==null || o2.getTwelveDrp().equals("")){
                            o2.setTwelveDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getTwelveDrp()) > Double.parseDouble(o2.getTwelveDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getTwelveDrp()) == Double.parseDouble(o2.getTwelveDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getTwelveDrp()) > Double.parseDouble(o2.getTwelveDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getTwelveDrp()) == Double.parseDouble(o2.getTwelveDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 13:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getThirteenDrp()==null || o1.getThirteenDrp().equals("")){
                            o1.setThirteenDrp("0");
                        }
                        if(o2.getThirteenDrp()==null || o2.getThirteenDrp().equals("")){
                            o2.setThirteenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getThirteenDrp()) > Double.parseDouble(o2.getThirteenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getThirteenDrp()) == Double.parseDouble(o2.getThirteenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getThirteenDrp()) > Double.parseDouble(o2.getThirteenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getThirteenDrp()) == Double.parseDouble(o2.getThirteenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 14:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getFourteenDrp()==null || o1.getFourteenDrp().equals("")){
                            o1.setFourteenDrp("0");
                        }
                        if(o2.getFourteenDrp()==null || o2.getFourteenDrp().equals("")){
                            o2.setFourteenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getFourteenDrp()) > Double.parseDouble(o2.getFourteenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getFourteenDrp()) == Double.parseDouble(o2.getFourteenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getFourteenDrp()) > Double.parseDouble(o2.getFourteenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getFourteenDrp()) == Double.parseDouble(o2.getFourteenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 15:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getFifteenDrp()==null || o1.getFifteenDrp().equals("")){
                            o1.setFifteenDrp("0");
                        }
                        if(o2.getFifteenDrp()==null || o2.getFifteenDrp().equals("")){
                            o2.setFifteenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getFifteenDrp()) > Double.parseDouble(o2.getFifteenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getFifteenDrp()) == Double.parseDouble(o2.getFifteenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getFifteenDrp()) > Double.parseDouble(o2.getFifteenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getFifteenDrp()) == Double.parseDouble(o2.getFifteenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 16:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getSixteenDrp()==null || o1.getSixteenDrp().equals("")){
                            o1.setSixteenDrp("0");
                        }
                        if(o2.getSixteenDrp()==null || o2.getSixteenDrp().equals("")){
                            o2.setSixteenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getSixteenDrp()) > Double.parseDouble(o2.getSixteenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getSixteenDrp()) == Double.parseDouble(o2.getSixteenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getSixteenDrp()) > Double.parseDouble(o2.getSixteenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getSixteenDrp()) == Double.parseDouble(o2.getSixteenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 17:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getSeventeenDrp()==null || o1.getSeventeenDrp().equals("")){
                            o1.setSeventeenDrp("0");
                        }
                        if(o2.getSeventeenDrp()==null || o2.getSeventeenDrp().equals("")){
                            o2.setSeventeenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getSeventeenDrp()) > Double.parseDouble(o2.getSeventeenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getSeventeenDrp()) == Double.parseDouble(o2.getSeventeenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getSeventeenDrp()) > Double.parseDouble(o2.getSeventeenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getSeventeenDrp()) == Double.parseDouble(o2.getSeventeenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 18:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getEighteenDrp()==null || o1.getEighteenDrp().equals("")){
                            o1.setEighteenDrp("0");
                        }
                        if(o2.getEighteenDrp()==null || o2.getEighteenDrp().equals("")){
                            o2.setEighteenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getEighteenDrp()) > Double.parseDouble(o2.getEighteenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getEighteenDrp()) == Double.parseDouble(o2.getEighteenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getEighteenDrp()) > Double.parseDouble(o2.getEighteenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getEighteenDrp()) == Double.parseDouble(o2.getEighteenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 19:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getNineteenDrp()==null || o1.getNineteenDrp().equals("")){
                            o1.setNineteenDrp("0");
                        }
                        if(o2.getNineteenDrp()==null || o2.getNineteenDrp().equals("")){
                            o2.setNineteenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getNineteenDrp()) > Double.parseDouble(o2.getNineteenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getNineteenDrp()) == Double.parseDouble(o2.getNineteenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getNineteenDrp()) > Double.parseDouble(o2.getNineteenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getNineteenDrp()) == Double.parseDouble(o2.getNineteenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 20:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getTwentyDrp()==null || o1.getTwentyDrp().equals("")){
                            o1.setTwentyDrp("0");
                        }
                        if(o2.getTwentyDrp()==null || o2.getTwentyDrp().equals("")){
                            o2.setTwentyDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getTwentyDrp()) > Double.parseDouble(o2.getTwentyDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getTwentyDrp()) == Double.parseDouble(o2.getTwentyDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getTwentyDrp()) > Double.parseDouble(o2.getTwentyDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getTwentyDrp()) == Double.parseDouble(o2.getTwentyDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 21:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getTwenty_oneDrp()==null || o1.getTwenty_oneDrp().equals("")){
                            o1.setTwenty_oneDrp("0");
                        }
                        if(o2.getTwenty_oneDrp()==null || o2.getTwenty_oneDrp().equals("")){
                            o2.setTwenty_oneDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getTwenty_oneDrp()) > Double.parseDouble(o2.getTwenty_oneDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getTwenty_oneDrp()) == Double.parseDouble(o2.getTwenty_oneDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getTwenty_oneDrp()) > Double.parseDouble(o2.getTwenty_oneDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getTwenty_oneDrp()) == Double.parseDouble(o2.getTwenty_oneDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 22:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getTwenty_twoDrp()==null || o1.getTwenty_twoDrp().equals("")){
                            o1.setTwenty_twoDrp("0");
                        }
                        if(o2.getTwenty_twoDrp()==null || o2.getTwenty_twoDrp().equals("")){
                            o2.setTwenty_twoDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getTwenty_twoDrp()) > Double.parseDouble(o2.getTwenty_twoDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getTwenty_twoDrp()) == Double.parseDouble(o2.getTwenty_twoDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getTwenty_twoDrp()) > Double.parseDouble(o2.getTwenty_twoDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getTwenty_twoDrp()) == Double.parseDouble(o2.getTwenty_twoDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 23:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getTwenty_threeDrp()==null || o1.getTwenty_threeDrp().equals("")){
                            o1.setTwenty_threeDrp("0");
                        }
                        if(o2.getTwenty_threeDrp()==null || o2.getTwenty_threeDrp().equals("")){
                            o2.setTwenty_threeDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getTwenty_threeDrp()) > Double.parseDouble(o2.getTwenty_threeDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getTwenty_threeDrp()) == Double.parseDouble(o2.getTwenty_threeDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getTwenty_threeDrp()) > Double.parseDouble(o2.getTwenty_threeDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getTwenty_threeDrp()) == Double.parseDouble(o2.getTwenty_threeDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 0:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getZeroDrp()==null || o1.getZeroDrp().equals("")){
                            o1.setZeroDrp("0");
                        }
                        if(o2.getZeroDrp()==null || o2.getZeroDrp().equals("")){
                            o2.setZeroDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getZeroDrp()) > Double.parseDouble(o2.getZeroDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getZeroDrp()) == Double.parseDouble(o2.getZeroDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getZeroDrp()) > Double.parseDouble(o2.getZeroDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getZeroDrp()) == Double.parseDouble(o2.getZeroDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 1:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getOneDrp()==null || o1.getOneDrp().equals("")){
                            o1.setOneDrp("0");
                        }
                        if(o2.getOneDrp()==null || o2.getOneDrp().equals("")){
                            o2.setOneDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getOneDrp()) > Double.parseDouble(o2.getOneDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getOneDrp()) == Double.parseDouble(o2.getOneDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getOneDrp()) > Double.parseDouble(o2.getOneDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getOneDrp()) == Double.parseDouble(o2.getOneDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 2:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getTwoDrp()==null || o1.getTwoDrp().equals("")){
                            o1.setTwoDrp("0");
                        }
                        if(o2.getTwoDrp()==null || o2.getTwoDrp().equals("")){
                            o2.setTwoDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getTwoDrp()) > Double.parseDouble(o2.getTwoDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getTwoDrp()) == Double.parseDouble(o2.getTwoDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getTwoDrp()) > Double.parseDouble(o2.getTwoDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getTwoDrp()) == Double.parseDouble(o2.getTwoDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 3:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getThreeDrp()==null || o1.getThreeDrp().equals("")){
                            o1.setThreeDrp("0");
                        }
                        if(o2.getThreeDrp()==null || o2.getThreeDrp().equals("")){
                            o2.setThreeDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getThreeDrp()) > Double.parseDouble(o2.getThreeDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getThreeDrp()) == Double.parseDouble(o2.getThreeDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getThreeDrp()) > Double.parseDouble(o2.getThreeDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getThreeDrp()) == Double.parseDouble(o2.getThreeDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 4:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getFourDrp()==null || o1.getFourDrp().equals("")){
                            o1.setFourDrp("0");
                        }
                        if(o2.getFourDrp()==null || o2.getFourDrp().equals("")){
                            o2.setFourDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getFourDrp()) > Double.parseDouble(o2.getFourDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getFourDrp()) == Double.parseDouble(o2.getFourDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getFourDrp()) > Double.parseDouble(o2.getFourDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getFourDrp()) == Double.parseDouble(o2.getFourDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 5:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getFiveDrp()==null || o1.getFiveDrp().equals("")){
                            o1.setFiveDrp("0");
                        }
                        if(o2.getFiveDrp()==null || o2.getFiveDrp().equals("")){
                            o2.setFiveDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getFiveDrp()) > Double.parseDouble(o2.getFiveDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getFiveDrp()) == Double.parseDouble(o2.getFiveDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getFiveDrp()) > Double.parseDouble(o2.getFiveDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getFiveDrp()) == Double.parseDouble(o2.getFiveDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 6:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getSixDrp()==null || o1.getSixDrp().equals("")){
                            o1.setSixDrp("0");
                        }
                        if(o2.getSixDrp()==null || o2.getSixDrp().equals("")){
                            o2.setSixDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getSixDrp()) > Double.parseDouble(o2.getSixDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getSixDrp()) == Double.parseDouble(o2.getSixDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getSixDrp()) > Double.parseDouble(o2.getSixDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getSixDrp()) == Double.parseDouble(o2.getSixDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 7:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getSevenDrp()==null || o1.getSevenDrp().equals("")){
                            o1.setSevenDrp("0");
                        }
                        if(o2.getSevenDrp()==null || o2.getSevenDrp().equals("")){
                            o2.setSevenDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getSevenDrp()) > Double.parseDouble(o2.getSevenDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getSevenDrp()) == Double.parseDouble(o2.getSevenDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getSevenDrp()) > Double.parseDouble(o2.getSevenDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getSevenDrp()) == Double.parseDouble(o2.getSevenDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 8:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if(o1.getEightDrp()==null || o1.getEightDrp().equals("")){
                            o1.setEightDrp("0");
                        }
                        if(o2.getEightDrp()==null || o2.getEightDrp().equals("")){
                            o2.setEightDrp("0");
                        }
                        if (sign == 0) {  //正序
                            if (Double.parseDouble(o1.getEightDrp()) > Double.parseDouble(o2.getEightDrp())) {
                                return 1;
                            }
                            if (Double.parseDouble(o1.getEightDrp()) == Double.parseDouble(o2.getEightDrp())) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (Double.parseDouble(o1.getEightDrp()) > Double.parseDouble(o2.getEightDrp())) {
                                return -1;
                            }
                            if (Double.parseDouble(o1.getEightDrp()) == Double.parseDouble(o2.getEightDrp())) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
            case 24:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if (sign == 0) {  //正序
                            if (o1.getCountDrp() > o2.getCountDrp()) {
                                return 1;
                            }
                            if (o1.getCountDrp() == o2.getCountDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getCountDrp() > o2.getCountDrp()) {
                                return -1;
                            }
                            if (o1.getCountDrp() == o2.getCountDrp()) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
        }*/
        return list;
    }
}
