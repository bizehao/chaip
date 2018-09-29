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
    public List<XunQi> getRainXQCompared(Date time,List<String> ly, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        List<RainExchange> list1 = getRainXQ(now,ly,adcd,systemTypes,stcdOrStnm);
        now.add(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainXQ(now,ly,adcd,systemTypes,stcdOrStnm);
        List<RainExchange> list3 = mapper.getRainXQCLCompared(ly,adcd,systemTypes,stcdOrStnm);
        int length = list1.size();
        XunQi xq = null;
        int adnmCount = 0; //县域下的站数
        List<XunQi> xqList = new ArrayList<>();
        for(int i=0; i<length; i++){
            adnmCount = list1.get(i).getAdnmCount();
            xq = new XunQi();
            xq.setStnm(list1.get(i).getAdnm());
            xq.setJxqSix(Double.parseDouble(new DecimalFormat("#0.0").format(list1.get(i).getNumSix()/adnmCount)));
            xq.setJxqSeven(Double.parseDouble(new DecimalFormat("#0.0").format(list1.get(i).getNumSeven()/adnmCount)));
            xq.setJxqEight(Double.parseDouble(new DecimalFormat("#0.0").format(list1.get(i).getNumEight()/adnmCount)));
            xq.setJxqNine(Double.parseDouble(new DecimalFormat("#0.0").format(list1.get(i).getNumNine()/adnmCount)));
            xq.setJxqSix_Nine(Double.parseDouble(new DecimalFormat("#0.0").format(list1.get(i).getZong()/adnmCount)));
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
            return "--";
        }else if(num2 == 0){
            return "--";
        }else{
            return new DecimalFormat("#0.0").format((num2-num1)/num1*100)+"%";
        }
    }
    //年逐月降雨量分析对比
    @Override
    public List<YearAndMonthRain> getRainNZYCompared(Date time,List<String> ly, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        List<RainExchange> list1 = getRainNZY(now,ly,adcd,systemTypes,stcdOrStnm);
        now.add(Calendar.YEAR, -2);
        List<RainExchange> list2 = getRainNZY(now,ly,adcd,systemTypes,stcdOrStnm);
        List<RainExchange> list3 = mapper.getRainNZYCLCompared(ly,adcd,systemTypes,stcdOrStnm);
        int length = list1.size();
        YearAndMonthRain yearAndMonthRain = null;
        List<YearAndMonthRain> nzylist = new ArrayList<>();
        int count = 0;
        for(int i=0; i<length; i++){
            count = list1.get(i).getAdnmCount();
            yearAndMonthRain = new YearAndMonthRain();
            yearAndMonthRain.setAdnm(list1.get(i).getAdnm());
            yearAndMonthRain.setNumOne(setXiaoShu(list1.get(i).getNumOne()/count));
            yearAndMonthRain.setNumTwo(setXiaoShu(list1.get(i).getNumTwo()/count));
            yearAndMonthRain.setNumThree(setXiaoShu(list1.get(i).getNumThree()/count));
            yearAndMonthRain.setNumFour(setXiaoShu(list1.get(i).getNumFour()/count));
            yearAndMonthRain.setNumFive(setXiaoShu(list1.get(i).getNumFive()/count));
            yearAndMonthRain.setNumSix(setXiaoShu(list1.get(i).getNumSix()/count));
            yearAndMonthRain.setNumSeven(setXiaoShu(list1.get(i).getNumSeven()/count));
            yearAndMonthRain.setNumEight(setXiaoShu(list1.get(i).getNumEight()/count));
            yearAndMonthRain.setNumNine(setXiaoShu(list1.get(i).getNumNine()/count));
            yearAndMonthRain.setNumTen(setXiaoShu(list1.get(i).getNumTen()/count));
            yearAndMonthRain.setNumEleven(setXiaoShu(list1.get(i).getNumEleven()/count));
            yearAndMonthRain.setNumTwelve(setXiaoShu(list1.get(i).getNumTwelve()/count));
            yearAndMonthRain.setJinYearZong(setXiaoShu(list1.get(i).getZong()/count));
            yearAndMonthRain.setQuYearZong(setXiaoShu(list2.get(i).getZong()/count));
            yearAndMonthRain.setChangYearZong(setXiaoShu(list3.get(i).getZong()/count));
            yearAndMonthRain.setCompareQu(setXiaoShu((list1.get(i).getZong()-list2.get(i).getZong())/list1.get(i).getAdnmCount()));
            System.out.println(setXiaoShu(list1.get(i).getZong()-list2.get(i).getZong())/list1.get(i).getAdnmCount());
            yearAndMonthRain.setCompareChang(setXiaoShu(list1.get(i).getZong()/list1.get(i).getAdnmCount()-list3.get(i).getZong()/count));
            yearAndMonthRain.setRelativeQu(suan(list2.get(i).getZong()/count, list1.get(i).getZong()/count));
            yearAndMonthRain.setRelativeChang(suan(list3.get(i).getZong()/count, list1.get(i).getZong()/count));
            nzylist.add(yearAndMonthRain);
        }
        return nzylist;
    }

    public Double setXiaoShu(double math){
        String a = new DecimalFormat("#0.0").format(math);
        return Double.parseDouble(a);
    }

    //任意日降雨量分析对比
    @Override
    public List<ArbitrarilyDay> getRainRYCompared(Date beginTime, Date endTime,List<String> ly, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar begin1 = Calendar.getInstance();
        begin1.setTime(beginTime);
        begin1.add(Calendar.DATE,1);
        Calendar end1 = Calendar.getInstance();
        end1.setTime(endTime);
        end1.add(Calendar.DATE,1);
        List<RainExchange> list1 = getRainRY(begin1, end1,ly, adcd, systemTypes, stcdOrStnm);//今年降雨量
        Calendar begin2 = Calendar.getInstance();
        begin2.setTime(beginTime);
        begin2.add(Calendar.DATE,1);
        begin2.add(Calendar.YEAR, -1);
        Calendar end2 = Calendar.getInstance();
        end2.setTime(endTime);
        end2.add(Calendar.DATE,1);
        end2.add(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainRY(begin2, end2,ly, adcd, systemTypes, stcdOrStnm);//去年降雨量
        List<Double> dList = getRainPYCL(beginTime, endTime,ly, adcd, systemTypes, stcdOrStnm);//常年降雨量
        List<ArbitrarilyDay> obList = new ArrayList<>();
        ArbitrarilyDay arbitrarilyDay = null;
        int length = list1.size();
        double chaqu = 0;
        double chachang = 0;
        int count = 0;
        for(int i=0; i<length; i++){
            count = list1.get(i).getAdnmCount();
            arbitrarilyDay = new ArbitrarilyDay();
            arbitrarilyDay.setAdnm(list1.get(i).getAdnm());
            arbitrarilyDay.setoDay_oDay(setXiaoShu(list1.get(i).getZong()/count));
            arbitrarilyDay.setSamePeriodQu(setXiaoShu(list2.get(i).getZong()/count));
            arbitrarilyDay.setSamePeriodChang(new DecimalFormat("#0.0").format(dList.get(i)/count));
            if(list1.get(i).getZong()==0){
                arbitrarilyDay.setSamePeriodCompareQu("--");
                arbitrarilyDay.setSamePeriodCompareChang("--");
            }else{
                chaqu = list1.get(i).getZong()/count-list2.get(i).getZong()/count;
                if(list2.get(i).getZong()>0){
                    if(chaqu==0){
                        arbitrarilyDay.setSamePeriodCompareQu(0+"%");
                    }
                    if(chaqu>0){
                        arbitrarilyDay.setSamePeriodCompareQu("多"+new DecimalFormat("#0.0").format(chaqu/(list2.get(i).getZong()/count)*100)+"%");
                    }else{
                        arbitrarilyDay.setSamePeriodCompareQu("少"+new DecimalFormat("#0.0").format(-chaqu/(list2.get(i).getZong()/count)*100)+"%");
                    }
                }else{
                    arbitrarilyDay.setSamePeriodCompareQu("--");
                }
                chachang = list1.get(i).getZong()/count-dList.get(i)/count;
                if(dList.get(i)>0){
                    if(chachang==0){
                        arbitrarilyDay.setSamePeriodCompareChang(0+"%");
                    }
                    if(chachang>0){
                        arbitrarilyDay.setSamePeriodCompareChang("多"+new DecimalFormat("#0.0").format(chachang/(dList.get(i)/count)*100)+"%");
                    }else{
                        arbitrarilyDay.setSamePeriodCompareChang("少"+new DecimalFormat("#0.0").format(-chachang/(dList.get(i)/count)*100)+"%");
                    }
                }else{
                    arbitrarilyDay.setSamePeriodCompareChang("--");
                }
            }
            obList.add(arbitrarilyDay);
        }
        return obList;
    }

    //处理汛期降雨量的时间
    public List<RainExchange> getRainXQ(Calendar now,List<String> ly, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
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

        return mapper.getRainXQCompared(timeSixEnd,timeSevenEnd,timeEightEnd,timeNineEnd,ly,adcd,systemTypes,stcdOrStnm);
    }

    //处理年逐月降雨量的时间
    public List<RainExchange> getRainNZY(Calendar now,List<String> ly,List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        //一月
        now.set(Calendar.MONTH,1);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeOneEnd = now.getTime();
        //二月
        now.set(Calendar.MONTH,2);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwoEnd = now.getTime();
        //三月
        now.set(Calendar.MONTH,3);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeThreeEnd = now.getTime();
        //四月
        now.set(Calendar.MONTH,4);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFourEnd = now.getTime();
        //五月
        now.set(Calendar.MONTH,5);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFiveEnd = now.getTime();
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
        //十月
        now.set(Calendar.MONTH,10);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTenEnd = now.getTime();
        //十一月
        now.set(Calendar.MONTH,11);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeElevenEnd = now.getTime();
        //十二月
        now.add(Calendar.YEAR, 1);
        now.set(Calendar.MONTH,0);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwelveEnd = now.getTime();

        return mapper.getRainNZYCompared(timeOneEnd,timeTwoEnd,timeThreeEnd,
                timeFourEnd,timeFiveEnd,timeSixEnd,timeSevenEnd,
                timeEightEnd,timeNineEnd,timeTenEnd,timeElevenEnd,
                timeTwelveEnd,ly,adcd,systemTypes,stcdOrStnm);
    }

    //处理任意日降雨量的时间及结果
    public List<RainExchange> getRainRY(Calendar beginTime, Calendar endTime,List<String> ly, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        beginTime.set(Calendar.HOUR_OF_DAY, 8);
        Date beginDate = beginTime.getTime();
        endTime.set(Calendar.HOUR_OF_DAY, 8);
        Date endDate = endTime.getTime();
        List<RainExchange> list = mapper.getRainRYCompared(beginDate, endDate,ly, adcd, systemTypes, stcdOrStnm);
        return list;
    }

    //处理任意日降雨量的常量数据
    public List<Double> getRainPYCL(Date beginTime, Date endTime,List<String> ly, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm){
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
            List<RainExchange> list1 = mapper.getRainRYCLCompared(beginMonth, beginDate,ly, adcd, systemTypes, stcdOrStnm,1,0);
            List<RainExchange> list2 = mapper.getRainRYCLCompared(endMonth, endDate,ly, adcd, systemTypes, stcdOrStnm,2,0);
            length = list1.size();
            dList = new ArrayList<>();
            for(int i=0; i<length; i++){
                dList.add(list1.get(i).getZong()+list2.get(i).getZong());
            }
            return dList;
        }else if(endMonth-beginMonth==0){
            List<RainExchange> list3 = mapper.getRainRYCLCompared(beginMonth, beginDate,ly, adcd, systemTypes, stcdOrStnm,3,endDate);
            length = list3.size();
            dList = new ArrayList<>();
            for(int i=0; i<length; i++){
                dList.add(list3.get(i).getZong());
            }
            return dList;
        }else{
            List<RainExchange> list1 = mapper.getRainRYCLCompared(beginMonth, beginDate,ly, adcd, systemTypes, stcdOrStnm,1,0);
            List<RainExchange> list2 = mapper.getRainRYCLCompared(endMonth, endDate,ly, adcd, systemTypes, stcdOrStnm,2,0);
            List<RainExchange> list0 = mapper.getRainRYCLCompared(beginMonth, endMonth,ly, adcd, systemTypes, stcdOrStnm,0,0);
            length = list1.size();
            dList = new ArrayList<>();
            for(int i=0; i<length; i++){
                dList.add(list1.get(i).getZong()+list2.get(i).getZong()+list0.get(i).getZong());
            }
            return dList;
        }
    }

}
