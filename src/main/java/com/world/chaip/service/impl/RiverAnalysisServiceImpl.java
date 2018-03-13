package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.mapper.RiverAnalysisMapper;
import com.world.chaip.service.RiverAnalysisService;
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
public class RiverAnalysisServiceImpl implements RiverAnalysisService{

    @Autowired
    private RiverAnalysisMapper riverAnalysisMapper;

    //河道水情分析
    @Override
    public List<RiverExchange> getRiverByAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        int beginMonth = tm.get(Calendar.MONTH);
        tm.setTime(dateE);
        int endMonth = tm.get(Calendar.MONTH);
        Date beginTime = null;
        Date endTime = null;
        int day = 0;
        double avq = 0;
        int countDay = 0;
        List<RiverExchange> list = new ArrayList<>();
        List<Double> alist = new ArrayList<>();
        RiverExchange riverExchange = null;
        List<Double> liuliang = null;
        List<List<Double>> jiheList = new ArrayList<>();
        for(int month = beginMonth; month<=endMonth; month++){
            liuliang = new ArrayList<>();
            tm.set(Calendar.MONTH, month);
            tm.set(Calendar.DATE, 1);
            tm.set(Calendar.HOUR_OF_DAY, 8);
            beginTime = tm.getTime();
            tm.set(Calendar.MONTH, month+1);
            tm.set(Calendar.DATE, 1);
            tm.set(Calendar.HOUR_OF_DAY, 8);
            endTime = tm.getTime();
            day = tm.getActualMaximum(Calendar.DAY_OF_MONTH);
            List<RiverExchange> riverByAnalysis = riverAnalysisMapper.getRiverByAnalysis(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
            for(int i=0; i<riverByAnalysis.size(); i++){
                if(month == beginMonth){
                    RiverExchange river = riverByAnalysis.get(i);
                    riverExchange = new RiverExchange();
                    riverExchange.setStcd(river.getStcd());
                    riverExchange.setRvnm(river.getRvnm()==null?"":river.getRvnm());
                    riverExchange.setStnm(river.getStnm());
                    list.add(riverExchange);
                }
                liuliang.add(riverByAnalysis.get(i).getAvgQ()*day);
            }
            countDay+=day;
            jiheList.add(liuliang);
        }
        for(int i = 0; i<jiheList.size(); i++){
            liuliang = jiheList.get(i);

        }
        //最大水位 流量 及 时间
        Calendar maxtm = Calendar.getInstance();
        maxtm.setTime(dateS);
        maxtm.set(Calendar.DATE, 2);
        maxtm.set(Calendar.HOUR_OF_DAY,8);
        dateS = maxtm.getTime();
        maxtm.setTime(dateE);
        maxtm.add(Calendar.MONTH,1);
        maxtm.set(Calendar.DATE, 1);
        maxtm.set(Calendar.HOUR_OF_DAY,8);
        dateE = tm.getTime();
        List<RiverExchange> riverByMaxQZ = riverAnalysisMapper.getRiverByMaxQZ(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        double avgQ = 0;
        List<RiverExchange> rList = new ArrayList<>();
        for(int i=0; i<alist.size(); i++){
            riverExchange = list.get(i);
            avgQ = alist.get(i)/countDay;
            riverExchange.setAvgQ(avgQ);
            riverExchange.setSumQ(avgQ*3600*24*countDay);
            riverExchange.setMaxZ(Double.parseDouble(new DecimalFormat("#0.000").format(riverByMaxQZ.get(i).getMaxZ())));
            Calendar m = Calendar.getInstance();
            Date dateZ = null;
            Date dateQ = null;
            int month = 0;
            int date = 0;
            String MaxZTime = riverByMaxQZ.get(i).getMaxZTime();
            if( MaxZTime != null){
                try {
                    dateZ = DateUtils.parse(MaxZTime, "yyyy-MM-dd");
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
            riverExchange.setMaxQ(Double.parseDouble(new DecimalFormat("#0.000").format(riverByMaxQZ.get(i).getMaxQ())));
            String MaxQTime = riverByMaxQZ.get(i).getMaxQTime();
            if( MaxQTime != null){
                try {
                    dateQ = DateUtils.parse(MaxQTime, "yyyy-MM-dd");
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
            rList.add(riverExchange);
        }
        return rList;
    }
}
