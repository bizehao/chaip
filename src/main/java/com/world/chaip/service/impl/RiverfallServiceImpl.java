package com.world.chaip.service.impl;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.report.River;
import com.world.chaip.mapper.RainfallMapper;
import com.world.chaip.mapper.RiverfallMapper;
import com.world.chaip.service.RiverfallService;
import com.world.chaip.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class RiverfallServiceImpl implements RiverfallService {

    @Autowired
    private RiverfallMapper riverfallMapper;


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

    @Override
    public List<River> getRiverByWai(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {

        List<River> rainfalls=riverfallMapper.getRiverByWai(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        return rainfalls;
    }

    @Override
    public List<River> getRiverByAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        List<River> rainfalls=riverfallMapper.getRiverByAnalysis(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        List<Object[]> list = new ArrayList<>();
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
        Object[] objects = null;
        for(int i=0; i<rainfalls.size(); i++){
            River river = rainfalls.get(i);
            objects = new Object[9];
            objects[0] = i+1;
            objects[1] = river.getRvnm();
            objects[2] = river.getStnm();
            objects[3] = new DecimalFormat("#0.00").format(river.getQ()/day/24/3600);
            objects[4] = river.getZ();
            objects[5] = river.getTm();
        }
        return null;
    }
}
