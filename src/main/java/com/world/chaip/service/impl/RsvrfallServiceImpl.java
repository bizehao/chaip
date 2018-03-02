package com.world.chaip.service.impl;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.report.River;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.RsvrXunQi;
import com.world.chaip.mapper.RsvrfallMapper;
import com.world.chaip.service.RsvrfallService;
import com.world.chaip.util.DateUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class RsvrfallServiceImpl implements RsvrfallService {

    @Autowired
    RsvrfallMapper rsvrfallMapper;

    //水库 (实时)
    @Override
    public List<Rsvr> getRsvrByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();

        List<Rsvr> rainfalls=rsvrfallMapper.getRsvrByTerm(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
        return rainfalls;
    }

    //水库 (专业)
    @Override
    public List<Rsvr> getRsvrByZhuanYe(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Date beginTime=null;
        Date endTime=null;
        DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime=now.getTime();
        now.setTime(dateE);
        endTime= now.getTime();
        List<RsvrXunQi> xunQiList = rsvrfallMapper.getRsvrFS(1);

        /*List<Rsvr> rainfalls=rsvrfallMapper.getRsvrByZhaunYe(time, fstp, adcd,systemTypes,stcdOrStnm);*/
        return null;
    }
}
