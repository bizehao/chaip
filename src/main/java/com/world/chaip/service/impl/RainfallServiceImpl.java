package com.world.chaip.service.impl;

import java.sql.Array;
import java.text.DecimalFormat;
import java.util.*;

import com.world.chaip.entity.excelFormat.DayRainExcel;
import com.world.chaip.entity.excelFormat.DayRainExcel.DayRain;
import com.world.chaip.entity.excelFormat.DayRainExcelX.DayRainX;
import com.world.chaip.entity.excelFormat.DayRainExcelX;
import com.world.chaip.entity.report.gson.PptnGson;
import com.world.chaip.entity.report.gson.RainEc;
import org.apache.poi.hssf.record.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.DaybyHourRainfall.DayByHourRainfallItem;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.mapper.RainfallMapper;
import com.world.chaip.service.RainfallService;
import com.world.chaip.util.DateUtils;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
@Service
public class RainfallServiceImpl implements RainfallService {

    @Autowired
    private RainfallMapper rainfallMapper;

    //时段降雨量
    @Override
    public List<PptnGson> getDaybyHour(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm,int column,int sign) {
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime=now.getTime();
        endTime=DateUtils.getDateAfter(beginTime, 1);
        System.out.println(beginTime+","+endTime);
        List<Rainfall> rainfalls=rainfallMapper.selectByTm(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
        System.out.println("数量"+rainfalls.size());
        if(rainfalls!=null && rainfalls.size()>0) {
            for(int i=0;i<rainfalls.size();i++) {
                DayByHourRainfallItem tempItem =null;
				/*System.out.println(i+"这个"+daybyHourRainfall.getData().size());*/
                for(int j=0;j<daybyHourRainfall.getData().size();j++) {
                    DayByHourRainfallItem tempItemX = daybyHourRainfall.getData().get(j);
                    if(tempItemX.getStcd().equals(rainfalls.get(i).getStcd())) {
                        tempItem=tempItemX;
                        if(rainfalls.get(i).getDrp() == null){
                            tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), 0.0);
                        }else{
                            tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), rainfalls.get(i).getDrp());
                        }
						/*System.out.println((double) rainfalls.get(i).getTm().getHours()+","+rainfalls.get(i).getDrp());*/
                        /*System.out.println(rainfalls.get(i).getDrp());
                        dyp+=rainfalls.get(i).getDrp();*/
                        if(i == rainfalls.size()-1){
                            tempItem=tempItemX;
                            double count = tempItem.testValues();
                            tempItem.setCountDrp(count);
							/*System.out.println("sads"+count);*/
                        }
                        break;
                    }else{
                        tempItem=tempItemX;
                        double count = tempItem.testValues();
                        tempItem.setCountDrp(count);
                        tempItem = null;
						/*System.out.println("sads"+count);*/
                    }
                }
				/*System.out.println(dyp);*/
                if(tempItem==null) {
                    tempItem=daybyHourRainfall.new DayByHourRainfallItem();
                    tempItem.setStcd(rainfalls.get(i).getStcd());
                    tempItem.setStnm(rainfalls.get(i).getStnm());
                    tempItem.setLgtd(rainfalls.get(i).getLgtd());
                    tempItem.setLttd(rainfalls.get(i).getLttd());
                    tempItem.setName(rainfalls.get(i).getName());
                    tempItem.setAdnm(rainfalls.get(i).getAdnm());
                    if(rainfalls.get(i).getDrp() == null){
                        tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), 0.0);
                    }else{
                        tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), rainfalls.get(i).getDrp());
                    }
                    daybyHourRainfall.getData().add(tempItem);
					/*tempItem.setDyp(dyp);*/
                }
            }
        }
        List<PptnGson> list = new ArrayList<>();
        PptnGson pptnGson = null;
        for(int i=0; i<daybyHourRainfall.getData().size(); i++){
            pptnGson = new PptnGson();
            pptnGson.setAdnm(daybyHourRainfall.getData().get(i).getAdnm());
            pptnGson.setStnm(daybyHourRainfall.getData().get(i).getStnm());
            pptnGson.setName(daybyHourRainfall.getData().get(i).getName());
            pptnGson.setCountDrp(daybyHourRainfall.getData().get(i).getCountDrp());
            pptnGson.setNineDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(9.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(9.0));
            pptnGson.setTenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(10.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(10.0));
            pptnGson.setElevenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(11.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(11.0));
            pptnGson.setTwelveDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(12.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(12.0));
            pptnGson.setThirteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(13.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(13.0));
            pptnGson.setFourteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(14.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(14.0));
            pptnGson.setFifteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(15.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(15.0));
            pptnGson.setSixteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(16.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(16.0));
            pptnGson.setSeventeenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(17.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(17.0));
            pptnGson.setEighteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(18.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(18.0));
            pptnGson.setNineteenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(19.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(19.0));
            pptnGson.setTwentyDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(20.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(20.0));
            pptnGson.setTwenty_oneDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(21.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(21.0));
            pptnGson.setTwenty_twoDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(22.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(22.0));
            pptnGson.setTwenty_threeDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(23.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(23.0));
            pptnGson.setZeroDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(0.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(0.0));
            pptnGson.setOneDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(1.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(1.0));
            pptnGson.setTwoDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(2.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(2.0));
            pptnGson.setThreeDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(3.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(3.0));
            pptnGson.setFourDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(4.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(4.0));
            pptnGson.setFiveDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(5.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(5.0));
            pptnGson.setSixDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(6.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(6.0));
            pptnGson.setSevenDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(7.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(7.0));
            pptnGson.setEightDrp(daybyHourRainfall.getData().get(i).getHourRainfalls().get(8.0)==null?0:daybyHourRainfall.getData().get(i).getHourRainfalls().get(8.0));
            list.add(pptnGson);
        }
        if(column != -1){
            list =  getListPX(list,column,sign);
        }

        return list;
    }

    //日降雨量
    @Override
    public Object getDaybyDate(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
        Date beginTime=null;
        Date endTime=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime=now.getTime();
        endTime=DateUtils.getDateAfter(beginTime, 1);
        List<Rainfall> list = rainfallMapper.selectByDate(endTime,adcd,systemTypes,stcdOrStnm,pptn);
        if(cid == 0){
            return list;
        }
		/*for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i).getStcd()+""+list.get(i).getStnm());
		}*/
        System.out.println("日降水量的记录数"+list.size());
        Object[] objects = null;
        DayRainExcel dayRainExcel = new DayRainExcel();
        if(list != null && list.size()>0){
            for(int i=0; i<list.size(); i++){
                DayRain dayRain = null;
                for(int j=0; j<dayRainExcel.getDayRainList().size(); j++){
                    DayRain dayRainX = dayRainExcel.getDayRainList().get(j);
                    if(dayRainX.getAdnm().equals(list.get(i).getAdnm())){
                        dayRain=dayRainX;
                        objects = new Object[2];
                        objects[0] = list.get(i).getStnm();
                        objects[1] = list.get(i).getDyp();
                        dayRain.getDayRainList().add(objects);
						/*System.out.println(list.get(i).getStnm());*/
                        break;
                    }
                }
                if(dayRain == null){
                    dayRain = dayRainExcel.new DayRain();
                    dayRain.setAdnm(list.get(i).getAdnm());
                    objects = new Object[2];
                    objects[0] = list.get(i).getStnm();
                    objects[1] = list.get(i).getDyp();
                    dayRain.getDayRainList().add(objects);
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
    public Object getDaybyXun(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
        Date Time=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time=now.getTime();
        Date beginTime = null;
        now.add(Calendar.DATE, -9);
        beginTime = now.getTime();
        List<Rainfall> list =  rainfallMapper.selectByXun(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
        if(cid == 0){
            return list;
        }
        return getXYN(list);
    }

    //月降雨量
    @Override
    public Object getDaybyMonth(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
        Date Time=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time=now.getTime();
        Date beginTime = null;
        now.add(Calendar.MONTH, -1);
        now.add(Calendar.DATE, 1);
        beginTime = now.getTime();
        List<Rainfall> list =  rainfallMapper.selectByMonth(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
        if(cid == 0){
            return list;
        }
        return getXYN(list);
    }

    //年降雨量
    @Override
    public Object getDaybyYear(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
        Date Time=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time=now.getTime();
        Date beginTime = null;
        now.add(Calendar.YEAR, -1);
        now.add(Calendar.DATE, 1);
        beginTime = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByYear(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
        if(cid == 0){
            return list;
        }
        return getXYN(list);
    }

    //时段降雨量
    @Override
    public Object getDaybyTime(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
        Calendar now =  Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DATE);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        now.set(year,month,day,hour,0,0);
        now.set(Calendar.MILLISECOND,0);
        Date NowTime = now.getTime();
        System.out.println(NowTime);
        //开始时间
        now.setTime(dateS);
        now.set(Calendar.MINUTE,0);
        dateS = now.getTime();
        //结束时间
        now.setTime(dateE);
        now.set(Calendar.MINUTE,0);
        dateE = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByTime(dateS, dateE, NowTime, adcd, systemTypes, stcdOrStnm,pptn);
        if(cid == 0){
            return list;
        }
        DayRainExcelX dayRainExcelX = new DayRainExcelX();
        Object[] obRainX = null;
        if(list != null && list.size()>0){
            for(int i=0; i<list.size(); i++){
                DayRainX dayRainM = null;
                for(int j=0; j<dayRainExcelX.getDayRainXList().size(); j++){
                    DayRainX dayRainX = dayRainExcelX.getDayRainXList().get(j);
                    if(dayRainX.getAdnm().equals(list.get(i).getAdnm())){
                        dayRainM=dayRainX;
                        obRainX = new Object[3];
                        obRainX[0] = list.get(i).getStnm();
                        obRainX[1] = list.get(i).getNum();
                        obRainX[2] = list.get(i).getDrp();
                        dayRainM.getRainList().add(obRainX);
                        break;
                    }
                }
                if(dayRainM == null){
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
    public String getDaybyHourJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime=now.getTime();
        endTime=DateUtils.getDateAfter(beginTime, 1);
        System.out.println(beginTime+","+endTime);
        List<Rainfall> rainfalls=rainfallMapper.selectByTm(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
        if(rainfalls!=null && rainfalls.size()>0) {
            for(int i=0;i<rainfalls.size();i++) {
                DayByHourRainfallItem tempItem =null;
                for(int j=0;j<daybyHourRainfall.getData().size();j++) {
                    DayByHourRainfallItem tempItemX = daybyHourRainfall.getData().get(j);
                    if(tempItemX.getStcd().equals(rainfalls.get(i).getStcd())) {
                        tempItem=tempItemX;
                        if(rainfalls.get(i).getDrp() == null){
                            tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), 0.0);
                        }else{
                            tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), rainfalls.get(i).getDrp());
                        }
                        if(i == rainfalls.size()-1){
                            tempItem=tempItemX;
                            double count = tempItem.testValues();
                            tempItem.setCountDrp(count);
                        }
                        break;
                    }else{
                        tempItem=tempItemX;
                        double count = tempItem.testValues();
                        tempItem.setCountDrp(count);
                        tempItem = null;
                    }
                }
                if(tempItem==null) {
                    tempItem=daybyHourRainfall.new DayByHourRainfallItem();
                    tempItem.setStcd(rainfalls.get(i).getStcd());
                    tempItem.setStnm(rainfalls.get(i).getStnm());
                    tempItem.setLgtd(rainfalls.get(i).getLgtd());
                    tempItem.setLttd(rainfalls.get(i).getLttd());
                    tempItem.setName(rainfalls.get(i).getName());
                    tempItem.setAdnm(rainfalls.get(i).getAdnm());
                    if(rainfalls.get(i).getDrp() == null){
                        tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), 0.0);
                    }else{
                        tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), rainfalls.get(i).getDrp());
                    }
                    daybyHourRainfall.getData().add(tempItem);
                }
            }
        }
        int hundred = 0;
        int Fifty = 0;
        int Thirty = 0;

        bgm:for(int i=0; i<daybyHourRainfall.getData().size(); i++){
            Map<Double,Double> map = daybyHourRainfall.getData().get(i).getHourRainfalls();
            int hundredmap = 0;
            int Fiftymap = 0;
            int Thirtymap = 0;
            for (Double value : map.values()) {
                if(Thirtymap != 1){
                    if(value >= 30){
                        Thirty++;
                        Thirtymap++;
                    }
                }
                if(Fiftymap != 1){
                    if(value >= 50){
                        Fifty++;
                        Fiftymap++;
                    }
                }
                if(hundredmap != 1){
                    if(value >= 100){
                        hundred++;
                        hundredmap++;
                    }
                }
                if(hundredmap==1&&Fiftymap==1&&Thirtymap==1){
                    continue bgm;
                }
            }
        }
        String xianshi ="一小时 超过100毫米的有："+
                hundred+"站;"+
                "超过50毫米的有："+
                Fifty+"站;"+
                "超过30毫米的有："+
                Thirty+"站";
        return xianshi;
    }

    //日降雨量计算
    @Override
    public String getDaybyDateJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
        Date beginTime=null;
        Date endTime=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime=now.getTime();
        endTime=DateUtils.getDateAfter(beginTime, 1);
        List<Rainfall> list = rainfallMapper.selectByDate(endTime,adcd,systemTypes,stcdOrStnm,pptn);
        String xianshi = coonPC(list,1);
        return xianshi;
    }

    //旬降雨量计算
    @Override
    public String getDaybyXunJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
        Date Time=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time=now.getTime();
        Date beginTime = null;
        now.add(Calendar.DATE, -9);
        beginTime = now.getTime();
        List<Rainfall> list =  rainfallMapper.selectByXun(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
        String xianshi = coonPC(list,2);
        return xianshi;
    }

    //月降雨量计算
    @Override
    public String getDaybyMonthJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
        Date Time=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time=now.getTime();
        Date beginTime = null;
        now.add(Calendar.MONTH, -1);
        now.add(Calendar.DATE, 1);
        beginTime = now.getTime();
        List<Rainfall> list =  rainfallMapper.selectByMonth(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
        String xianshi = coonPC(list,2);
        return xianshi;
    }

    //年降雨量计算
    @Override
    public String getDaybyYearJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
        Date Time=null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Time=now.getTime();
        Date beginTime = null;
        now.add(Calendar.YEAR, -1);
        now.add(Calendar.DATE, 1);
        beginTime = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByYear(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
        String xianshi = coonPC(list,2);
        return xianshi;
    }

    //时段降雨量计算
    @Override
    public String getDaybyTimeJS(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
        Calendar now =  Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH + 1);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        now.set(year,month,day,hour,0,0);
        now.set(Calendar.MILLISECOND,0);
        Date NowTime = now.getTime();
        //开始时间
        now.setTime(dateS);
        now.set(Calendar.MINUTE,0);
        dateS = now.getTime();
        //结束时间
        now.setTime(dateE);
        now.set(Calendar.MINUTE,0);
        dateE = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByTime(dateS, dateE, NowTime, adcd, systemTypes, stcdOrStnm,pptn);
        String xianshi = coonPC(list,3);
        return xianshi;
    }

    //list集合排序    1=日  2= 旬月年  3 = 时段
    public String coonPC(List<Rainfall> list,int sign) {
        int hundred = 0;
        int Fifty = 0;
        int Thirty = 0;
        double dyp = 0;
        double x = 0;
        if(sign == 1){
            for(int i = 0; i<list.size(); i++) {
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
        }else if(sign == 2){
            for(int i = 0; i<list.size(); i++) {
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
        }else{
            for(int i = 0; i<list.size(); i++) {
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
        if(list.size()>3){
            if(sign==0){
                y1 = list.get(0).getDrp();
                y2 = list.get(1).getDrp();
                y3 = list.get(2).getDrp();
            }else if(sign==1){
                y1 = list.get(0).getDyp();
                y2 = list.get(1).getDyp();
                y3 = list.get(2).getDyp();
            }else if(sign==2){
                y1 = list.get(0).getAccp();
                y2 = list.get(1).getAccp();
                y3 = list.get(2).getAccp();
            }else{
                y1 = list.get(0).getNum();
                y2 = list.get(1).getNum();
                y3 = list.get(2).getNum();
            }
            String one = "";
            String two = "";
            String three = "";
            if(list.size()==1){
                one ="最大的是"+list.get(0).getAdnm()+"的"+list.get(0).getStnm()+"站，降雨量是"+y1+"毫米";
            }else if(list.size()==2){
                one ="最大的是"+list.get(0).getAdnm()+"的"+list.get(0).getStnm()+"站，降雨量是"+y1+"毫米";
                two = "次大点的是"+list.get(1).getAdnm()+"的"+list.get(1).getStnm()+"站，降雨量是"+y2+"毫米";
            }else{
                one ="最大的是"+list.get(0).getAdnm()+"的"+list.get(0).getStnm()+"站，降雨量是"+y1+"毫米";
                two = "次大点的是"+list.get(1).getAdnm()+"的"+list.get(1).getStnm()+"站，降雨量是"+y2+"毫米";
                three = "再次大点的是"+list.get(2).getAdnm()+"的"+list.get(2).getStnm()+"站，降雨量是"+y3+"毫米";
            }
            if(sign ==2 ){
                xianshi = one+";"+two+";"+three+".";
            }else{
                xianshi ="超过100毫米的有："+
                        hundred+"站"+
                        "超过50毫米的有："+
                        Fifty+"站"+
                        "超过30毫米的有："+
                        Thirty+"站;"+ one+";"+two+";"+three+".";

            }
            return xianshi;
        }
        return xianshi;
    }
    //处理旬月年
    public DayRainExcelX getXYN(List<Rainfall>  list) {
        DayRainExcelX dayRainExcelX = new DayRainExcelX();
        Object[] obRainX = null;
        if(list != null && list.size()>0){
            for(int i=0; i<list.size(); i++){
                DayRainX dayRainM = null;
                for(int j=0; j<dayRainExcelX.getDayRainXList().size(); j++){
                    DayRainX dayRainX = dayRainExcelX.getDayRainXList().get(j);
                    if(dayRainX.getAdnm().equals(list.get(i).getAdnm())){
                        dayRainM=dayRainX;
                        obRainX = new Object[3];
                        obRainX[0] = list.get(i).getStnm();
                        obRainX[1] = list.get(i).getAccp();
                        obRainX[2] = new DecimalFormat("#").format(list.get(i).getNum());
                        dayRainM.getRainList().add(obRainX);
                        break;
                    }
                }
                if(dayRainM == null){
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

    public List<PptnGson> getListPX(List<PptnGson> list, int column, int sign){
        switch (column) {
            case 9:
                Collections.sort(list, new Comparator<PptnGson>() {
                    @Override
                    public int compare(PptnGson o1, PptnGson o2) {
                        if (sign == 0) {  //正序
                            if (o1.getNineDrp() > o2.getNineDrp()) {
                                return 1;
                            }
                            if (o1.getNineDrp() == o2.getNineDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getNineDrp() > o2.getNineDrp()) {
                                return -1;
                            }
                            if (o1.getNineDrp() == o2.getNineDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getTenDrp() > o2.getTenDrp()) {
                                return 1;
                            }
                            if (o1.getTenDrp() == o2.getTenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getTenDrp() > o2.getTenDrp()) {
                                return -1;
                            }
                            if (o1.getTenDrp() == o2.getTenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getElevenDrp() > o2.getElevenDrp()) {
                                return 1;
                            }
                            if (o1.getElevenDrp() == o2.getElevenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getElevenDrp() > o2.getElevenDrp()) {
                                return -1;
                            }
                            if (o1.getElevenDrp() == o2.getElevenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getTwelveDrp() > o2.getTwelveDrp()) {
                                return 1;
                            }
                            if (o1.getTwelveDrp() == o2.getTwelveDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getTwelveDrp() > o2.getTwelveDrp()) {
                                return -1;
                            }
                            if (o1.getTwelveDrp() == o2.getTwelveDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getThirteenDrp() > o2.getThirteenDrp()) {
                                return 1;
                            }
                            if (o1.getThirteenDrp() == o2.getThirteenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getThirteenDrp() > o2.getThirteenDrp()) {
                                return -1;
                            }
                            if (o1.getThirteenDrp() == o2.getThirteenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getFourteenDrp() > o2.getFourteenDrp()) {
                                return 1;
                            }
                            if (o1.getFourteenDrp() == o2.getFourteenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getFourteenDrp() > o2.getFourteenDrp()) {
                                return -1;
                            }
                            if (o1.getFourteenDrp() == o2.getFourteenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getFifteenDrp() > o2.getFifteenDrp()) {
                                return 1;
                            }
                            if (o1.getFifteenDrp() == o2.getFifteenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getFifteenDrp() > o2.getFifteenDrp()) {
                                return -1;
                            }
                            if (o1.getFifteenDrp() == o2.getFifteenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getSixteenDrp() > o2.getSixteenDrp()) {
                                return 1;
                            }
                            if (o1.getSixteenDrp() == o2.getSixteenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getSixteenDrp() > o2.getSixteenDrp()) {
                                return -1;
                            }
                            if (o1.getSixteenDrp() == o2.getSixteenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getSeventeenDrp() > o2.getSeventeenDrp()) {
                                return 1;
                            }
                            if (o1.getSeventeenDrp() == o2.getSeventeenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getSeventeenDrp() > o2.getSeventeenDrp()) {
                                return -1;
                            }
                            if (o1.getSeventeenDrp() == o2.getSeventeenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getEighteenDrp() > o2.getEighteenDrp()) {
                                return 1;
                            }
                            if (o1.getEighteenDrp() == o2.getEighteenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getEighteenDrp() > o2.getEighteenDrp()) {
                                return -1;
                            }
                            if (o1.getEighteenDrp() == o2.getEighteenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getNineteenDrp() > o2.getNineteenDrp()) {
                                return 1;
                            }
                            if (o1.getNineteenDrp() == o2.getNineteenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getNineteenDrp() > o2.getNineteenDrp()) {
                                return -1;
                            }
                            if (o1.getNineteenDrp() == o2.getNineteenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getTwentyDrp() > o2.getTwentyDrp()) {
                                return 1;
                            }
                            if (o1.getTwentyDrp() == o2.getTwentyDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getTwentyDrp() > o2.getTwentyDrp()) {
                                return -1;
                            }
                            if (o1.getTwentyDrp() == o2.getTwentyDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getTwenty_oneDrp() > o2.getTwenty_oneDrp()) {
                                return 1;
                            }
                            if (o1.getTwenty_oneDrp() == o2.getTwenty_oneDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getTwenty_oneDrp() > o2.getTwenty_oneDrp()) {
                                return -1;
                            }
                            if (o1.getTwenty_oneDrp() == o2.getTwenty_oneDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getTwenty_twoDrp() > o2.getTwenty_twoDrp()) {
                                return 1;
                            }
                            if (o1.getTwenty_twoDrp() == o2.getTwenty_twoDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getTwenty_twoDrp() > o2.getTwenty_twoDrp()) {
                                return -1;
                            }
                            if (o1.getTwenty_twoDrp() == o2.getTwenty_twoDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getTwenty_threeDrp() > o2.getTwenty_threeDrp()) {
                                return 1;
                            }
                            if (o1.getTwenty_threeDrp() == o2.getTwenty_threeDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getTwenty_threeDrp() > o2.getTwenty_threeDrp()) {
                                return -1;
                            }
                            if (o1.getTwenty_threeDrp() == o2.getTwenty_threeDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getZeroDrp() > o2.getZeroDrp()) {
                                return 1;
                            }
                            if (o1.getZeroDrp() == o2.getZeroDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getZeroDrp() > o2.getZeroDrp()) {
                                return -1;
                            }
                            if (o1.getZeroDrp() == o2.getZeroDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getOneDrp() > o2.getOneDrp()) {
                                return 1;
                            }
                            if (o1.getOneDrp() == o2.getOneDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getOneDrp() > o2.getOneDrp()) {
                                return -1;
                            }
                            if (o1.getOneDrp() == o2.getOneDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getTwoDrp() > o2.getTwoDrp()) {
                                return 1;
                            }
                            if (o1.getTwoDrp() == o2.getTwoDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getTwoDrp() > o2.getTwoDrp()) {
                                return -1;
                            }
                            if (o1.getTwoDrp() == o2.getTwoDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getThreeDrp() > o2.getThreeDrp()) {
                                return 1;
                            }
                            if (o1.getThreeDrp() == o2.getThreeDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getThreeDrp() > o2.getThreeDrp()) {
                                return -1;
                            }
                            if (o1.getThreeDrp() == o2.getThreeDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getFourDrp() > o2.getFourDrp()) {
                                return 1;
                            }
                            if (o1.getFourDrp() == o2.getFourDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getFourDrp() > o2.getFourDrp()) {
                                return -1;
                            }
                            if (o1.getFourDrp() == o2.getFourDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getFiveDrp() > o2.getFiveDrp()) {
                                return 1;
                            }
                            if (o1.getFiveDrp() == o2.getFiveDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getFiveDrp() > o2.getFiveDrp()) {
                                return -1;
                            }
                            if (o1.getFiveDrp() == o2.getFiveDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getSixDrp() > o2.getSixDrp()) {
                                return 1;
                            }
                            if (o1.getSixDrp() == o2.getSixDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getSixDrp() > o2.getSixDrp()) {
                                return -1;
                            }
                            if (o1.getSixDrp() == o2.getSixDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getSevenDrp() > o2.getSevenDrp()) {
                                return 1;
                            }
                            if (o1.getSevenDrp() == o2.getSevenDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getSevenDrp() > o2.getSevenDrp()) {
                                return -1;
                            }
                            if (o1.getSevenDrp() == o2.getSevenDrp()) {
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
                        if (sign == 0) {  //正序
                            if (o1.getEightDrp() > o2.getEightDrp()) {
                                return 1;
                            }
                            if (o1.getEightDrp() == o2.getEightDrp()) {
                                return 0;
                            }
                            return -1;
                        } else {     //倒序
                            if (o1.getEightDrp() > o2.getEightDrp()) {
                                return -1;
                            }
                            if (o1.getEightDrp() == o2.getEightDrp()) {
                                return 0;
                            }
                            return 1;
                        }
                    }
                });
                break;
        }
        return list;
    }
}
