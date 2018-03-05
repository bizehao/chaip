package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.mapper.RainAnalysisMapper;
import com.world.chaip.service.RainAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RainAnalysisServiceImpl implements RainAnalysisService {

    @Autowired
    RainAnalysisMapper mapper;

    @Override
    public List<Object[]> getRainXQCompared(Date time, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        List<RainExchange> list1 = getRainXQ(now,adcd,systemTypes,stcdOrStnm);
        now.set(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainXQ(now,adcd,systemTypes,stcdOrStnm);
        List<RainExchange> list3 = mapper.getRainXQCLCompared(adcd,systemTypes,stcdOrStnm);
        int length = list1.size();
        Object[] xq = null;
        List<Object[]> xqList = new ArrayList<>();
        for(int i=0; i<length; i++){
            xq = new Object[16];
            xq[16] = list1.get(i).getStnm();
            xq[1] = list1.get(i).getNumSix();
            xq[2] = list1.get(i).getNumSeven();
            xq[3] = list1.get(i).getNumEight();
            xq[4] = list1.get(i).getNumNine();
            xq[5] = list1.get(i).getZong();
            xq[6] = new DecimalFormat("#0.000").format((list1.get(i).getNumSix()-list2.get(i).getNumSix())/list2.get(i).getNumSix()*100)+"%";
            xq[7] = new DecimalFormat("#0.000").format((list1.get(i).getNumSeven()-list2.get(i).getNumSeven())/list2.get(i).getNumSeven()*100)+"%";
            xq[8] = new DecimalFormat("#0.000").format((list1.get(i).getNumEight()-list2.get(i).getNumEight())/list2.get(i).getNumEight()*100)+"%";
            xq[9] = new DecimalFormat("#0.000").format((list1.get(i).getNumNine()-list2.get(i).getNumNine())/list2.get(i).getNumNine()*100)+"%";
            xq[10] = new DecimalFormat("#0.000").format((list1.get(i).getZong()-list2.get(i).getZong())/list2.get(i).getZong()*100)+"%";
            xq[11] = new DecimalFormat("#0.000").format((list1.get(i).getNumSix()-list3.get(i).getNumSix())/list3.get(i).getNumSix()*100)+"%";
            xq[12] = new DecimalFormat("#0.000").format((list1.get(i).getNumSeven()-list3.get(i).getNumSeven())/list3.get(i).getNumSeven()*100)+"%";
            xq[13] = new DecimalFormat("#0.000").format((list1.get(i).getNumEight()-list3.get(i).getNumEight())/list3.get(i).getNumEight()*100)+"%";
            xq[14] = new DecimalFormat("#0.000").format((list1.get(i).getNumNine()-list3.get(i).getNumNine())/list3.get(i).getNumNine()*100)+"%";
            xq[15] =  new DecimalFormat("#0.000").format((list1.get(i).getZong()-list3.get(i).getZong())/list3.get(i).getZong()*100)+"%";
            xqList.add(xq);
        }
        return xqList;
    }

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
