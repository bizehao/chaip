package com.world.chaip.service.impl;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.report.River;
import com.world.chaip.mapper.RainfallMapper;
import com.world.chaip.mapper.RiverfallMapper;
import com.world.chaip.service.RiverfallService;
import com.world.chaip.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class RiverfallServiceImpl implements RiverfallService {

    @Autowired
    private RiverfallMapper riverfallMapper;

    //实时 河道
    @Override
    public List<River> getRiverByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        /*Date beginTime=null;
        Date endTime=null;
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();*/

        List<River> rainfalls=riverfallMapper.getRiverByTerm(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        return rainfalls;
    }

    //本区 河道
    @Override
    public List<River> getRiverByBen(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        /*Date beginTime=null;
        Date endTime=null;
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();*/

        List<River> rainfalls=riverfallMapper.getRiverByBen(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        return rainfalls;
    }

    //外区河道
    @Override
    public List<River> getRiverByWai(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {

        List<River> rainfalls=riverfallMapper.getRiverByWai(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        return rainfalls;
    }

    //河道水情分析
    @Override
    public List<RiverExchange> getRiverByAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        tm.set(Calendar.DATE, 2);
        tm.set(Calendar.HOUR_OF_DAY,8);
        dateS = tm.getTime();
        tm.setTime(dateE);
        tm.add(Calendar.MONTH,1);
        tm.set(Calendar.DATE, 1);
        tm.set(Calendar.HOUR_OF_DAY,8);
        dateE = tm.getTime();
        List<RiverExchange> rainfalls=riverfallMapper.getRiverByAnalysis(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        List<RiverExchange> list = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        int monthS = now.get(Calendar.MONTH);
        now.setTime(dateE);
        int monthE = now.get(Calendar.MONTH);
        int day = 0;
        for(; monthS<=monthE; monthS++){
            now.set(Calendar.MONTH,monthS);
            day+=now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        RiverExchange riverExchange = null;
        for(int i=0; i<rainfalls.size(); i++){
            RiverExchange river = rainfalls.get(i);
            riverExchange = new RiverExchange();
            riverExchange.setStcd(river.getStcd());
            riverExchange.setRvnm(river.getRvnm()==null?"":river.getRvnm());
            riverExchange.setStnm(river.getStnm());
            riverExchange.setAvgQ(Double.parseDouble(new DecimalFormat("#0.00").format(river.getSumQ()/day/24/3600)));
            riverExchange.setSumQ(river.getSumQ());
            riverExchange.setMaxQ(river.getMaxQ());
            String time1 = riverfallMapper.getRiverMaxTime(dateS,dateE,river.getStcd(),1,river.getMaxQ());
            Date dateQ = null;
            int month = 0;
            int date = 0;
            Calendar m = Calendar.getInstance();
            if(time1 != null){
                try {
                    dateQ = DateUtils.parse(time1, "yyyy-MM-dd");
                    m.setTime(dateQ);
                    month = m.get(Calendar.MONTH)+1;
                    date = m.get(Calendar.DATE);
                    riverExchange.setMaxQTime(month+"月"+date+"日");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                riverExchange.setMaxQTime("");
            }
            riverExchange.setMaxQ(river.getMaxZ());
            Date dateZ = null;
            String time2 = riverfallMapper.getRiverMaxTime(dateS,dateE,river.getStcd(),0,river.getMaxQ());
            if(time2 != null){
                try {
                    dateZ = DateUtils.parse(time2, "yyyy-MM-dd");
                    m.setTime(dateZ);
                    month = m.get(Calendar.MONTH)+1;
                    date = m.get(Calendar.DATE);
                    riverExchange.setMaxZTime(month+"月"+date+"日");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                riverExchange.setMaxZTime("");
            }
            list.add(riverExchange);
        }
        return list;
    }
}
