package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.mapper.RainAnalysisMapper;
import com.world.chaip.service.RainAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RainAnalysisServiceImpl implements RainAnalysisService {

    @Autowired
    RainAnalysisMapper mapper;

    @Override
    public List<RainExchange> getRainXQCompared(Date time, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        List<RainExchange> list1 = getRainXQ(now,adcd,systemTypes,stcdOrStnm);
        now.set(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainXQ(now,adcd,systemTypes,stcdOrStnm);

        return null;
    }

    @Override
    public List<RainExchange> getRainXQ(Calendar now, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        //六月
        now.set(Calendar.MONTH,6);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSixBegin = now.getTime();
        now.set(Calendar.MONTH,7);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSixEnd = now.getTime();
        //七月
        now.set(Calendar.MONTH,7);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSevenBegin = now.getTime();
        now.set(Calendar.MONTH,8);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSevenEnd = now.getTime();
        //八月
        now.set(Calendar.MONTH,8);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeEightBegin = now.getTime();
        now.set(Calendar.MONTH,9);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeEightEnd = now.getTime();
        //九月
        now.set(Calendar.MONTH,9);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeNineBegin = now.getTime();
        now.set(Calendar.MONTH,10);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeNineEnd = now.getTime();
        //去年的日期
        now.set(Calendar.YEAR, -1);
        return mapper.getRainXQCompared(timeSixBegin,timeSixEnd,timeSevenBegin,timeSevenEnd,timeEightBegin,timeEightEnd,timeNineBegin,
                timeNineEnd,adcd,systemTypes,stcdOrStnm);
    }


}
