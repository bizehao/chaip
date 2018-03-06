package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.mapper.RainAnalysisMapper;
import com.world.chaip.service.RainAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.Oneway;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 汛期降雨量  交换库
 */
public class RainAnalysisServiceImpl implements RainAnalysisService {

    @Autowired
    RainAnalysisMapper mapper;
    //汛期降雨量
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
            xq[0] = list1.get(i).getStnm();
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

    //年逐月降雨量分析对比
    @Override
    public List<Object[]> getRainNZYCompared(Date time, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        List<RainExchange> list1 = getRainNZY(now,adcd,systemTypes,stcdOrStnm);
        now.set(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainNZY(now,adcd,systemTypes,stcdOrStnm);
        List<RainExchange> list3 = mapper.getRainNZYCLCompared(adcd,systemTypes,stcdOrStnm);
        int length = list1.size();
        Object[] object = null;
        List<Object[]> nzylist = new ArrayList<>();
        for(int i=0; i<length; i++){
            object = new Object[19];
            object[0] = list1.get(i).getStnm();
            object[1] = list1.get(i).getNumOne();
            object[2] = list1.get(i).getNumThree();
            object[3] = list1.get(i).getNumFour();
            object[4] = list1.get(i).getNumFive();
            object[5] = list1.get(i).getNumSix();
            object[6] = list1.get(i).getNumSeven();
            object[7] = list1.get(i).getNumEight();
            object[8] = list1.get(i).getNumNine();
            object[9] = list1.get(i).getNumTen();
            object[10] = list1.get(i).getNumEleven();
            object[11] = list1.get(i).getNumTwelve();
            object[12] = list1.get(i).getZong();
            object[13] = list2.get(i).getZong();
            object[14] = list3.get(i).getZong();
            object[15] = new DecimalFormat("#0.000").format((list1.get(i).getZong()-list2.get(i).getZong())/list2.get(i).getZong());
            object[16] = new DecimalFormat("#0.000").format((list1.get(i).getZong()-list3.get(i).getZong())/list2.get(i).getZong());
            object[17] = new DecimalFormat("#0.000").format((list1.get(i).getZong()-list2.get(i).getZong())/list2.get(i).getZong()*100)+"%";
            object[18] = new DecimalFormat("#0.000").format((list1.get(i).getZong()-list3.get(i).getZong())/list2.get(i).getZong()*100)+"%";
            nzylist.add(object);
        }
        return nzylist;
    }

    //任意日降雨量分析对比
    @Override
    public List<Object[]> getRainRYCompared(Date beginTime, Date endTime, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar begin1 = Calendar.getInstance();
        begin1.setTime(beginTime);
        Calendar end1 = Calendar.getInstance();
        end1.setTime(endTime);
        List<RainExchange> list1 = getRainRY(begin1, end1, adcd, systemTypes, stcdOrStnm);
        Calendar begin2 = Calendar.getInstance();
        begin2.setTime(beginTime);
        begin2.set(Calendar.YEAR, -1);
        Calendar end2 = Calendar.getInstance();
        end2.setTime(endTime);
        end2.set(Calendar.YEAR, -1);
        List<RainExchange> list2 = getRainRY(begin2, end2, adcd, systemTypes, stcdOrStnm);
        List<Double> dList = getRainPYCL(beginTime, endTime, adcd, systemTypes, stcdOrStnm);
        List<Object[]> obList = new ArrayList<>();
        Object[] objects = null;
        int length = list1.size();
        double chaqu = 0;
        double chachang = 0;
        for(int i=0; i<length; i++){
            objects = new Object[6];
            objects[0] = list1.get(i).getStnm();
            objects[1] = list1.get(i).getZong();
            objects[2] = list2.get(i).getZong();
            objects[3] = dList.get(i);
            chaqu = list1.get(i).getZong()-list2.get(i).getZong();
            if(chaqu==0){
                objects[4] = 0+"%";
            }
            if(chaqu>0){
                objects[4] = "多"+new DecimalFormat("#0.00").format(chaqu/list2.get(i).getZong())+"%";
            }else{
                objects[4] = "少"+new DecimalFormat("#0.00").format(-chaqu/list2.get(i).getZong())+"%";
            }
            chachang = list1.get(i).getZong()-dList.get(i);
            if(chachang==0){
                objects[5] = 0+"%";
            }
            if(chaqu>0){
                objects[5] = "多"+new DecimalFormat("#0.00").format(chaqu/dList.get(i))+"%";
            }else{
                objects[5] = "少"+new DecimalFormat("#0.00").format(-chaqu/dList.get(i))+"%";
            }
            obList.add(objects);
        }
        return obList;
    }

    //处理汛期降雨量的时间
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

        return mapper.getRainXQCompared(timeSixBegin,timeSixEnd,timeSevenBegin,timeSevenEnd,timeEightBegin,timeEightEnd,timeNineBegin,
                timeNineEnd,adcd,systemTypes,stcdOrStnm);
    }

    //处理年逐月降雨量的时间
    public List<RainExchange> getRainNZY(Calendar now, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        //一月
        now.set(Calendar.MONTH,1);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeOneBegin = now.getTime();
        now.set(Calendar.MONTH,2);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeOneEnd = now.getTime();
        //二月
        now.set(Calendar.MONTH,2);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwoBegin = now.getTime();
        now.set(Calendar.MONTH,3);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwoEnd = now.getTime();
        //三月
        now.set(Calendar.MONTH,3);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeThreeBegin = now.getTime();
        now.set(Calendar.MONTH,4);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeThreeEnd = now.getTime();
        //四月
        now.set(Calendar.MONTH,4);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFourBegin = now.getTime();
        now.set(Calendar.MONTH,5);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFourEnd = now.getTime();
        //五月
        now.set(Calendar.MONTH,5);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFiveBegin = now.getTime();
        now.set(Calendar.MONTH,6);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeFiveEnd = now.getTime();
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
        //十月
        now.set(Calendar.MONTH,10);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTenBegin = now.getTime();
        now.set(Calendar.MONTH,11);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTenEnd = now.getTime();
        //十一月
        now.set(Calendar.MONTH,11);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeElevenBegin = now.getTime();
        now.set(Calendar.MONTH,12);
        now.set(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeElevenEnd = now.getTime();
        //十二月
        now.set(Calendar.MONTH,12);
        now.set(Calendar.DATE,2);
        now.set(Calendar.HOUR_OF_DAY,8);
        Date timeTwelveBegin = now.getTime();
        now.set(Calendar.YEAR, 1);
        now.set(Calendar.MONTH,1);
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
