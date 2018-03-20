package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.entity.report.River;
import com.world.chaip.mapper.RiverfallMapper;
import com.world.chaip.service.RiverfallService;
import com.world.chaip.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
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
        River river = null;
        for(int i=0; i<rainfalls.size(); i++){
            river = rainfalls.get(i);
            river.setQ(river.getQ()==null?"":new DecimalFormat("#0.000").format(Double.parseDouble(river.getQ())));
            river.setZ(river.getZ()==null?"":new DecimalFormat("#0.00").format(Double.parseDouble(river.getZ())));
        }
        return rainfalls;
    }

    //外区河道
    @Override
    public List<River> getRiverByWai(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {

        List<River> rainfalls=riverfallMapper.getRiverByWai(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        River river = null;
        for(int i=0; i<rainfalls.size(); i++){
            river = rainfalls.get(i);
            river.setQ(river.getQ()==null?"":new DecimalFormat("#0.000").format(Double.parseDouble(river.getQ())));
            river.setZ(river.getZ()==null?"":new DecimalFormat("#0.00").format(Double.parseDouble(river.getZ())));
        }
        return rainfalls;
    }
}
