package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.entity.Exchange.RsvrExchange;
import com.world.chaip.entity.Exchange.RsvrWaterExchange;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.mapper.RsvrAnalysisMapper;
import com.world.chaip.service.RsvrAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class RsvrAnalysisServiceImpl implements RsvrAnalysisService{

    @Autowired
    private RsvrAnalysisMapper rsvrAnalysisMapper;

    /*@Override
    public List<Rsvr> getRsvrWaterAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        int beginMonth = tm.get(Calendar.MONTH);
        tm.setTime(dateE);
        int endMonth = tm.get(Calendar.MONTH);
        Date time = null;
        int day = 0;
        double avq = 0;
        int countDay = 0;
        List<RiverExchange> list = new ArrayList<>();
        List<Double> alist = new ArrayList<>();
        RiverExchange riverExchange = null;
        for(int month = beginMonth; month<=endMonth; month++){
            tm.set(Calendar.MONTH, month);
            tm.set(Calendar.DATE, 1);
            tm.set(Calendar.HOUR_OF_DAY, 8);
            time = tm.getTime();
            day = tm.getActualMaximum(Calendar.DAY_OF_MONTH);
            List<RsvrWaterExchange> rsvrWaterAnalysis = rsvrAnalysisMapper.getRsvrWaterAnalysis(time,adcd,systemTypes,stcdOrStnm);
            for(int i=0; i<rsvrWaterAnalysis.size(); i++){
                if(month == beginMonth){
                    RsvrWaterExchange rsvrWaterExchange = rsvrWaterAnalysis.get(i);
                    riverExchange = new RiverExchange();
                    riverExchange.setStcd(rsvrWaterExchange.getStcd());
                    riverExchange.setRvnm(river.getRvnm()==null?"":river.getRvnm());
                    riverExchange.setStnm(river.getStnm());
                    list.add(riverExchange);
                }
                avq += riverByAnalysis.get(i).getAvgQ()*day;
                if(month == endMonth){
                    alist.add(avq);
                }
                countDay+=day;
            }
        }
        return null;
    }*/

    @Override
    public List<Rsvr> getRsvrWaterAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        return null;
    }

    @Override
    public List<Rsvr> getRsvrStorageAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        return null;
    }

    @Override
    public List<RsvrExchange> getRsvrFeaturesAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Date beginTime = now.getTime();
        now.setTime(dateE);
        now.add(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Date endTime = now.getTime();
        List<RsvrExchange> list = rsvrAnalysisMapper.getRsvrFeaturesAnalysis(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
        return list;
    }
}
