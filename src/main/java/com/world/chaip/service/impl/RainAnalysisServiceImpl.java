package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.entity.exchangeRain.ArbitrarilyDay;
import com.world.chaip.entity.exchangeRain.XunQi;
import com.world.chaip.entity.exchangeRain.YearAndMonthRain;
import com.world.chaip.mapper.RainAnalysisMapper;
import com.world.chaip.service.RainAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.CciOperationNotSupportedException;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 汛期降雨量  交换库
 */
@Service
public class RainAnalysisServiceImpl implements RainAnalysisService {

    @Autowired
    RainAnalysisMapper mapper;
    //汛期降雨量
    @Override
    public List<XunQi> getRainXQCompared(Date time, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        List<RainExchange> list1 = getRainXQ(now,adcd,systemTypes,stcdOrStnm);
        now.add(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainXQ(now,adcd,systemTypes,stcdOrStnm);
        List<RainExchange> list3 = mapper.getRainXQCLCompared(adcd,systemTypes,stcdOrStnm);
        int length = list1.size();
        XunQi xq = null;
        List<XunQi> xqList = new ArrayList<>();
        for(int i=0; i<length; i++){
            xq = new XunQi();
            xq.setStnm(list1.get(i).getAdnm());
            xq.setJxqSix(list1.get(i).getNumSix());
            xq.setJxqSeven(list1.get(i).getNumSeven());
            xq.setJxqEight(list1.get(i).getNumEight());
            xq.setJxqNine(list1.get(i).getNumNine());
            xq.setJxqSix_Nine(Double.parseDouble(new DecimalFormat("#0.0").format(list1.get(i).getZong())));
            xq.setJxqSix_Nine_Compare(suan(list1.get(i).getNumNine(), list1.get(i).getNumSix()));
            xq.setQxqSix(suan(list2.get(i).getNumSix(), list1.get(i).getNumSix()));
            xq.setQxqSeven(suan(list2.get(i).getNumSeven(), list1.get(i).getNumSeven()));
            xq.setQxqEight(suan(list2.get(i).getNumEight(), list1.get(i).getNumEight()));
            xq.setQxqNine(suan(list2.get(i).getNumNine(), list1.get(i).getNumNine()));
            xq.setQxqSix_Nine(suan(list2.get(i).getZong(), list1.get(i).getZong()));
            xq.setCxqSix(suan(list3.get(i).getNumSix(), list1.get(i).getNumSix()));
            xq.setCxqSeven(suan(list3.get(i).getNumSeven(), list1.get(i).getNumSeven()));
            xq.setCxqEight(suan(list3.get(i).getNumEight(), list1.get(i).getNumEight()));
            xq.setCxqNine(suan(list3.get(i).getNumNine(), list1.get(i).getNumNine()));
            xq.setCxqSix_Nine(suan(list3.get(i).getZong(), list1.get(i).getZong()));
            xqList.add(xq);
        }
        return xqList;
    }
    //计算值
    public String suan(double num1, double num2){
        if(num1 == 0){
            return "100%";
        }else{
            return new DecimalFormat("#0.0").format((num2-num1)/num1*100)+"%";
        }
    }
    //年逐月降雨量分析对比
    @Override
    public List<YearAndMonthRain> getRainNZYCompared(Date time, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        List<RainExchange> list1 = getRainNZY(now,adcd,systemTypes,stcdOrStnm);
        now.set(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainNZY(now,adcd,systemTypes,stcdOrStnm);
        List<RainExchange> list3 = mapper.getRainNZYCLCompared(adcd,systemTypes,stcdOrStnm);
        int length = list1.size();
        YearAndMonthRain yearAndMonthRain = null;
        List<YearAndMonthRain> nzylist = new ArrayList<>();
        for(int i=0; i<length; i++){
            yearAndMonthRain = new YearAndMonthRain();
            yearAndMonthRain.setAdnm(list1.get(i).getAdnm());
            yearAndMonthRain.setNumOne(list1.get(i).getNumOne());
            yearAndMonthRain.setNumThree(list1.get(i).getNumThree());
            yearAndMonthRain.setNumFour(list1.get(i).getNumFour());
            yearAndMonthRain.setNumFive(list1.get(i).getNumFive());
            yearAndMonthRain.setNumSix(list1.get(i).getNumSix());
            yearAndMonthRain.setNumSeven(list1.get(i).getNumSeven());
            yearAndMonthRain.setNumEight(list1.get(i).getNumEight());
            yearAndMonthRain.setNumNine(list1.get(i).getNumNine());
            yearAndMonthRain.setNumTen(list1.get(i).getNumTen());
            yearAndMonthRain.setNumEleven(list1.get(i).getNumEleven());
            yearAndMonthRain.setNumTwelve(list1.get(i).getNumTwelve());
            yearAndMonthRain.setJinYearZong(list1.get(i).getZong());
            yearAndMonthRain.setQuYearZong(list2.get(i).getZong());
            yearAndMonthRain.setChangYearZong(list3.get(i).getZong());
            yearAndMonthRain.setCompareQu(Double.parseDouble(new DecimalFormat("#0.000").format(list1.get(i).getZong()-list2.get(i).getZong())));
            yearAndMonthRain.setCompareChang(Double.parseDouble((new DecimalFormat("#0.000").format(list1.get(i).getZong()-list3.get(i).getZong()))));
            yearAndMonthRain.setRelativeQu(suan(list2.get(i).getZong(), list1.get(i).getZong()));
            yearAndMonthRain.setRelativeChang(suan(list3.get(i).getZong(), list1.get(i).getZong()));
            nzylist.add(yearAndMonthRain);
        }
        return nzylist;
    }

    //任意日降雨量分析对比
    @Override
    public List<ArbitrarilyDay> getRainRYCompared(Date beginTime, Date endTime, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar begin1 = Calendar.getInstance();
        begin1.setTime(beginTime);
        Calendar end1 = Calendar.getInstance();
        end1.setTime(endTime);
        List<RainExchange> list1 = getRainRY(begin1, end1, adcd, systemTypes, stcdOrStnm);
        Calendar begin2 = Calendar.getInstance();
        begin2.setTime(beginTime);
        begin2.add(Calendar.YEAR, -1);
        Calendar end2 = Calendar.getInstance();
        end2.setTime(endTime);
        end2.set(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainRY(begin2, end2, adcd, systemTypes, stcdOrStnm);
        List<Double> dList = getRainPYCL(beginTime, endTime, adcd, systemTypes, stcdOrStnm);
        List<ArbitrarilyDay> obList = new ArrayList<>();
        ArbitrarilyDay arbitrarilyDay = null;
        int length = list1.size();
        double chaqu = 0;
        double chachang = 0;
        for(int i=0; i<length; i++){
            arbitrarilyDay = new ArbitrarilyDay();
            arbitrarilyDay.setAdnm(list1.get(i).getAdnm());
            arbitrarilyDay.setoDay_oDay(list1.get(i).getZong());
            arbitrarilyDay.setSamePeriodQu(list2.get(i).getZong());
            arbitrarilyDay.setSamePeriodChang(dList.get(i));
            chaqu = list1.get(i).getZong()-list2.get(i).getZong();
            if(list2.get(i).getZong()>0){
                if(chaqu==0){
                    arbitrarilyDay.setSamePeriodCompareQu(0+"%");
                }
                if(chaqu>0){
                    arbitrarilyDay.setSamePeriodCompareQu("多"+new DecimalFormat("#0.00").format(chaqu/list2.get(i).getZong())+"%");
                }else{
                    arbitrarilyDay.setSamePeriodCompareQu("少"+new DecimalFormat("#0.00").format(-chaqu/list2.get(i).getZong())+"%");
                }
            }else{
                arbitrarilyDay.setSamePeriodCompareQu("100%");
            }
            chachang = list1.get(i).getZong()-dList.get(i);
            if(dList.get(i)>0){
                if(chachang==0){
                    arbitrarilyDay.setSamePeriodCompareChang(0+"%");
                }
                if(chaqu>0){
                    arbitrarilyDay.setSamePeriodCompareChang("多"+new DecimalFormat("#0.00").format(chaqu/dList.get(i))+"%");
                }else{
                    arbitrarilyDay.setSamePeriodCompareChang("少"+new DecimalFormat("#0.00").format(-chaqu/dList.get(i))+"%");
                }
            }else{
                arbitrarilyDay.setSamePeriodCompareChang("100%");
            }
            obList.add(arbitrarilyDay);
        }
        return obList;
    }

    //处理汛期降雨量的时间
    public List<RainExchange> getRainXQ(Calendar now, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        //六月
        now.set(Calendar.MONTH,6);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSixEnd = now.getTime();
        //七月
        now.set(Calendar.MONTH,7);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSevenEnd = now.getTime();
        //八月
        now.set(Calendar.MONTH,8);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeEightEnd = now.getTime();
        //九月
        now.set(Calendar.MONTH,9);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeNineEnd = now.getTime();

        return mapper.getRainXQCompared(timeSixEnd,timeSevenEnd,timeEightEnd,timeNineEnd,adcd,systemTypes,stcdOrStnm);
    }

    //处理年逐月降雨量的时间
    public List<RainExchange> getRainNZY(Calendar now, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        //一月
        now.set(Calendar.MONTH,0);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeOneBegin = now.getTime();
        now.set(Calendar.MONTH,1);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeOneEnd = now.getTime();
        //二月
        now.set(Calendar.MONTH,1);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwoBegin = now.getTime();
        now.set(Calendar.MONTH,2);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwoEnd = now.getTime();
        //三月
        now.set(Calendar.MONTH,2);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeThreeBegin = now.getTime();
        now.set(Calendar.MONTH,3);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeThreeEnd = now.getTime();
        //四月
        now.set(Calendar.MONTH,3);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFourBegin = now.getTime();
        now.set(Calendar.MONTH,4);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFourEnd = now.getTime();
        //五月
        now.set(Calendar.MONTH,4);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFiveBegin = now.getTime();
        now.set(Calendar.MONTH,5);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFiveEnd = now.getTime();
        //六月
        now.set(Calendar.MONTH,5);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSixBegin = now.getTime();
        now.set(Calendar.MONTH,6);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSixEnd = now.getTime();
        //七月
        now.set(Calendar.MONTH,6);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSevenBegin = now.getTime();
        now.set(Calendar.MONTH,7);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeSevenEnd = now.getTime();
        //八月
        now.set(Calendar.MONTH,7);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeEightBegin = now.getTime();
        now.set(Calendar.MONTH,8);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeEightEnd = now.getTime();
        //九月
        now.set(Calendar.MONTH,8);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeNineBegin = now.getTime();
        now.set(Calendar.MONTH,9);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeNineEnd = now.getTime();
        //十月
        now.set(Calendar.MONTH,9);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTenBegin = now.getTime();
        now.set(Calendar.MONTH,10);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTenEnd = now.getTime();
        //十一月
        now.set(Calendar.MONTH,10);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeElevenBegin = now.getTime();
        now.set(Calendar.MONTH,11);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeElevenEnd = now.getTime();
        //十二月
        now.set(Calendar.MONTH,11);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwelveBegin = now.getTime();
        now.set(Calendar.YEAR, 1);
        now.set(Calendar.MONTH,0);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwelveEnd = now.getTime();

        return mapper.getRainNZYCompared(timeOneBegin,timeOneEnd,timeTwoBegin,timeTwoEnd,timeThreeBegin,timeThreeEnd,
                timeFourBegin,timeFourEnd,timeFiveBegin,timeFiveEnd,timeSixBegin,timeSixEnd,timeSevenBegin,timeSevenEnd,
                timeEightBegin,timeEightEnd,timeNineBegin,timeTenBegin,timeTenEnd,timeElevenBegin,timeElevenEnd,
                timeTwelveBegin,timeTwelveEnd, timeNineEnd,adcd,systemTypes,stcdOrStnm);
    }

    //处理任意日降雨量的时间及结果
    public List<RainExchange> getRainRY(Calendar beginTime, Calendar endTime, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        beginTime.set(Calendar.HOUR_OF_DAY, 8);
        Date beginDate = beginTime.getTime();
        endTime.set(Calendar.HOUR_OF_DAY, 8);
        Date endDate = endTime.getTime();
        List<RainExchange> list = mapper.getRainRYCompared(beginDate, endDate, adcd, systemTypes, stcdOrStnm);
        return list;
    }

    //处理任意日降雨量的常量数据
    public List<Double> getRainPYCL(Date beginTime, Date endTime, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm){
        List<Double> dList = null;
        int length = 0;
        Calendar tm = Calendar.getInstance();
        tm.setTime(beginTime);
        int beginMonth = tm.get(Calendar.MONTH)+1;
        int beginDate = tm.get(Calendar.DATE);
        tm.setTime(endTime);
        int endMonth = tm.get(Calendar.MONTH)+1;
        int endDate = tm.get(Calendar.DATE);
        if(endMonth-beginMonth==1){
            List<RainExchange> list1 = mapper.getRainRYCLCompared(beginMonth, beginDate, adcd, systemTypes, stcdOrStnm,1,0);
            List<RainExchange> list2 = mapper.getRainRYCLCompared(endMonth, endDate, adcd, systemTypes, stcdOrStnm,2,0);
            length = list1.size();
            dList = new ArrayList<>();
            for(int i=0; i<length; i++){
                dList.add(list1.get(i).getZong()+list2.get(i).getZong());
            }
            return dList;
        }else if(endMonth-beginMonth==0){
            List<RainExchange> list3 = mapper.getRainRYCLCompared(beginMonth, beginDate, adcd, systemTypes, stcdOrStnm,3,endDate);
            length = list3.size();
            dList = new ArrayList<>();
            for(int i=0; i<length; i++){
                dList.add(list3.get(i).getZong());
            }
            return dList;
        }else{
            List<RainExchange> list1 = mapper.getRainRYCLCompared(beginMonth, beginDate, adcd, systemTypes, stcdOrStnm,1,0);
            List<RainExchange> list2 = mapper.getRainRYCLCompared(endMonth, endDate, adcd, systemTypes, stcdOrStnm,2,0);
            List<RainExchange> list0 = mapper.getRainRYCLCompared(beginMonth, endMonth, adcd, systemTypes, stcdOrStnm,0,0);
            length = list1.size();
            dList = new ArrayList<>();
            for(int i=0; i<length; i++){
                dList.add(list1.get(i).getZong()+list2.get(i).getZong()+list0.get(i).getZong());
            }
            return dList;
        }
    }

}
