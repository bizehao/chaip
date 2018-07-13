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

        long difference =  (dateS.getTime()-dateE.getTime())/86400000;
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        tm.set(Calendar.HOUR_OF_DAY, 8);
        Date beginTime = tm.getTime();

        tm.setTime(dateE);

        tm.set(Calendar.HOUR_OF_DAY, 8);
        tm.add(Calendar.DATE,1);
        Date endTime = tm.getTime();

        List<RiverExchange> riverByAnalysis = riverAnalysisMapper.getRiverByAnalysis(beginTime,endTime,adcd,systemTypes,stcdOrStnm);

        int days = (int) (Math.abs(difference)+1);
        System.out.println("======"+days);
        List<RiverExchange> list = new ArrayList<>();
        RiverExchange riverExchange = null;

        for (int i=0; i<riverByAnalysis.size(); i++){
            riverExchange = new RiverExchange();
            riverExchange.setStcd(riverByAnalysis.get(i).getStcd());
            riverExchange.setRvnm(riverByAnalysis.get(i).getRvnm()==null?"":riverByAnalysis.get(i).getRvnm());
            riverExchange.setStnm(riverByAnalysis.get(i).getStnm());
            riverExchange.setAvgQ(new DecimalFormat("#0.000").format((Double.parseDouble(riverByAnalysis.get(i).getAvgQ()))/days));
            riverExchange.setSumQ(new DecimalFormat("#0.000").format((Double.parseDouble(riverByAnalysis.get(i).getAvgQ()))*3600*24/1000000));
            list.add(riverExchange);
        }

        System.out.println(list.size());

        /*Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        int beginMonth = tm.get(Calendar.MONTH);
        tm.setTime(dateE);
        int endMonth = tm.get(Calendar.MONTH);
        Date beginTime = null;
        Date endTime = null;
        int day = 0;
        int countDay = 0;
        List<RiverExchange> list = new ArrayList<>();
        RiverExchange riverExchange = null;
        double[] listArray = null;
        for(int month = beginMonth; month<=endMonth; month++){   //  4     6
            tm.set(Calendar.MONTH, month);
            tm.set(Calendar.DATE, 1);
            tm.set(Calendar.HOUR_OF_DAY, 8);
            beginTime = tm.getTime();
            tm.set(Calendar.MONTH, month+1);
            tm.set(Calendar.DATE, 1);
            tm.set(Calendar.HOUR_OF_DAY, 8);
            endTime = tm.getTime();
            tm.add(Calendar.MONTH,-1);
            day = tm.getActualMaximum(Calendar.DAY_OF_MONTH);
            List<RiverExchange> riverByAnalysis = riverAnalysisMapper.getRiverByAnalysis(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
            if(month == beginMonth){
                listArray = new double[riverByAnalysis.size()];
            }
            for(int i=0; i<riverByAnalysis.size(); i++){
                listArray[i] = listArray[i]+Double.parseDouble(riverByAnalysis.get(i).getAvgQ())*day;
            }
            countDay+=day;
            if(month == endMonth){
                for (int i=0; i<riverByAnalysis.size(); i++){
                    riverExchange = new RiverExchange();
                    riverExchange.setStcd(riverByAnalysis.get(i).getStcd());
                    riverExchange.setRvnm(riverByAnalysis.get(i).getRvnm()==null?"":riverByAnalysis.get(i).getRvnm());
                    riverExchange.setStnm(riverByAnalysis.get(i).getStnm());
                    riverExchange.setAvgQ(new DecimalFormat("#0.000").format(listArray[i]/countDay));
                    riverExchange.setSumQ(new DecimalFormat("#0.000").format(listArray[i]/countDay*3600*24*countDay));
                    list.add(riverExchange);
                }
            }
        }*/
        //最大水位 流量 及 时间
        /*Calendar maxtm = Calendar.getInstance();
        maxtm.setTime(dateS);
        maxtm.set(Calendar.DATE, 1);
        maxtm.set(Calendar.HOUR_OF_DAY,8);
        dateS = maxtm.getTime();
        maxtm.setTime(dateE);
        maxtm.add(Calendar.MONTH,1);
        maxtm.set(Calendar.DATE, 1);
        maxtm.set(Calendar.HOUR_OF_DAY,8);
        dateE = maxtm.getTime();*/
        List<RiverExchange> riverByMaxQZ = riverAnalysisMapper.getRiverByMaxQZ(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
        System.out.println(riverByMaxQZ.size());
        List<RiverExchange> rList = new ArrayList<>();
        for(int i=0; i<riverByMaxQZ.size(); i++){
            riverExchange = list.get(i);
            riverExchange.setMaxZ(new DecimalFormat("#0.00").format(Double.parseDouble(riverByMaxQZ.get(i).getMaxZ())));
            Calendar m = Calendar.getInstance();
            Date dateZ = null;
            Date dateQ = null;
            int month = 0;
            int date = 0;
            int hour = 0;
            int minute = 0;
            String MaxZTime = riverByMaxQZ.get(i).getMaxZTime();
            if( MaxZTime != null){
                try {
                    dateZ = DateUtils.parse(MaxZTime, "yyyy-MM-dd HH:mm");
                    m.setTime(dateZ);
                    month = m.get(Calendar.MONTH)+1;
                    date = m.get(Calendar.DATE);
                    hour = m.get(Calendar.HOUR_OF_DAY);
                    minute = m.get(Calendar.MINUTE);
                    riverExchange.setMaxZTime(month+"月"+date+"日"+hour+"时"+minute+"分");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                riverExchange.setMaxZTime("");
            }
            riverExchange.setMaxQ(new DecimalFormat("#0.000").format(Double.parseDouble(riverByMaxQZ.get(i).getMaxQ())));
            String MaxQTime = riverByMaxQZ.get(i).getMaxQTime();
            if( MaxQTime != null){
                try {
                    dateQ = DateUtils.parse(MaxQTime, "yyyy-MM-dd HH:mm");
                    m.setTime(dateQ);
                    month = m.get(Calendar.MONTH)+1;
                    date = m.get(Calendar.DATE);
                    hour = m.get(Calendar.HOUR_OF_DAY);
                    minute = m.get(Calendar.MINUTE);
                    riverExchange.setMaxQTime(month+"月"+date+"日"+hour+"时"+minute+"分");
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
    public String roundByScale(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        if(scale == 0){
            return new DecimalFormat("0").format(v);
        }
        String formatStr = "0.";
        for(int i=0;i<scale;i++){
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }
}
