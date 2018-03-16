package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RsvrStrongeExcel.RsvrStrongeItem;
import com.world.chaip.entity.Exchange.*;
import com.world.chaip.entity.Exchange.RsvrWaterExcel.RsvrWC;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.mapper.RsvrAnalysisMapper;
import com.world.chaip.service.RsvrAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class RsvrAnalysisServiceImpl implements RsvrAnalysisService{

    @Autowired
    private RsvrAnalysisMapper rsvrAnalysisMapper;

    @Override
    public RsvrWaterExcel getRsvrWaterAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar tm = Calendar.getInstance();
        tm.setTime(dateS);
        int beginMonth = tm.get(Calendar.MONTH);
        tm.setTime(dateE);
        int endMonth = tm.get(Calendar.MONTH);
        int day = 0;
        int countDay = 0;
        Date beginTime = null;
        Date endTime = null;
        List<RsvrWaterExchange>  rsvrsList= new ArrayList<>();
        List<RsvrWaterExchange> list = new ArrayList<>();
        RsvrWaterExchange rsvrWaterExchange = null;
        double[] otqlistArray = null;   //出库平均流量
        double[] inqlistArray = null;   //入库平均流量
        for(int month = beginMonth; month<=endMonth; month++){
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
            System.out.println("天数"+day);
            List<RsvrWaterExchange> rsvrWaterAnalysis = rsvrAnalysisMapper.getRsvrWaterAnalysis(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
            if(month == beginMonth){
                otqlistArray = new double[rsvrWaterAnalysis.size()];
                inqlistArray = new double[rsvrWaterAnalysis.size()];
            }
            for(int i=0; i<rsvrWaterAnalysis.size(); i++){
                otqlistArray[i] = otqlistArray[i]+rsvrWaterAnalysis.get(i).getAvotq()*day;
                inqlistArray[i] = inqlistArray[i]+rsvrWaterAnalysis.get(i).getAvinq()*day;
            }
            countDay+=day;
            if(month == endMonth){
                for (int i=0; i<rsvrWaterAnalysis.size(); i++){
                    rsvrWaterExchange = new RsvrWaterExchange();
                    rsvrWaterExchange.setStcd(rsvrWaterAnalysis.get(i).getStcd());
                    rsvrWaterExchange.setName(rsvrWaterAnalysis.get(i).getName());
                    rsvrWaterExchange.setStnm(rsvrWaterAnalysis.get(i).getStnm());
                    rsvrWaterExchange.setAvotqs(new DecimalFormat("#0.000").format(otqlistArray[i]/countDay));
                    rsvrWaterExchange.setSumotqs(new DecimalFormat("#0.000").format(otqlistArray[i]/countDay*3600*24*countDay));
                    rsvrWaterExchange.setSuminqs(new DecimalFormat("#0.000").format(inqlistArray[i]/countDay*3600*24*countDay));
                    list.add(rsvrWaterExchange);
                }
            }
        }
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        now.set(Calendar.DATE, 1);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Date time = now.getTime();
        List<Rsvr> beginRsvrList =  rsvrAnalysisMapper.getRsvrWaterAnalysisRi(time,adcd,systemTypes,stcdOrStnm);
        now.setTime(dateE);
        now.add(Calendar.MONTH, 1);
        now.set(Calendar.DATE, 1);
        now.set(Calendar.HOUR_OF_DAY, 8);
        time = now.getTime();
        List<Rsvr> endiRsvrList = rsvrAnalysisMapper.getRsvrWaterAnalysisRi(time,adcd,systemTypes,stcdOrStnm);
        for (int i=0; i<list.size(); i++){
            rsvrWaterExchange = list.get(i);
            rsvrWaterExchange.setqRZs(new DecimalFormat("#0.00").format(beginRsvrList.get(i).getRz()));
            rsvrWaterExchange.setqWs(new DecimalFormat("#0.000").format(beginRsvrList.get(i).getW()));
            rsvrWaterExchange.sethRZs(new DecimalFormat("#0.00").format(endiRsvrList.get(i).getRz()));
            rsvrWaterExchange.sethWs(new DecimalFormat("#0.000").format(endiRsvrList.get(i).getW()));
            rsvrWaterExchange.setChaW(new DecimalFormat("#0.000").format(endiRsvrList.get(i).getW()-beginRsvrList.get(i).getW()));
            rsvrsList.add(rsvrWaterExchange);
        }
        RsvrWaterExcel rsvrWaterExcel = new RsvrWaterExcel();
        for(int i=0; i<rsvrsList.size(); i++){
            RsvrWC rsvrWC = null;
            for (int j=0; j<rsvrWaterExcel.getRsvrWCList().size(); j++){
                RsvrWC rsvrWCX = rsvrWaterExcel.getRsvrWCList().get(j);
                if(rsvrWCX.getName() .equals(rsvrsList.get(i).getName())){
                    rsvrWC = rsvrWCX;
                    rsvrWC.getrList().add(rsvrsList.get(i));
                }
            }
            if(rsvrWC == null){
                rsvrWC = rsvrWaterExcel.new RsvrWC();
                rsvrWC.setName(rsvrsList.get(i).getName());
                rsvrWC.getrList().add(rsvrsList.get(i));
                rsvrWaterExcel.getRsvrWCList().add(rsvrWC);
            }
        }
        return rsvrWaterExcel;
    }

    @Override
    public RsvrStrongeExcel getRsvrStorageAnalysis(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Date time1 = now.getTime();
        now.add(Calendar.DATE,1);
        now.set(Calendar.HOUR_OF_DAY, 8);
        Date time2 = now.getTime();

        List<RsvrW> rsvrWList = new ArrayList<>();
        RsvrStronge rsvrStronge = null;
        double countrsvrStrongeListj3 = 0;
        double countrsvrStrongeListj2 = 0;
        double countrsvrStrongeListj1 = 0;
        double countrsvrStrongeListq3 = 0;
        double countrsvrStrongeListq2 = 0;
        double countrsvrStrongeListq1 = 0;
        double countrsvrStrongeListc3 = 0;
        double countrsvrStrongeListc2 = 0;
        double countrsvrStrongeListc1 = 0;
        double jinZong = 0;
        double quZong = 0;
        double changZong = 0;

        List<RsvrStronge> jinList = new ArrayList<>();
        //今年大型
        List<RsvrStronge> rsvrStrongeListj3 =  rsvrAnalysisMapper.getRsvrStorageAnalysis(time1,time2,adcd,systemTypes,stcdOrStnm,3);
        if(rsvrStrongeListj3.size()>0){
            for (int i=0; i<rsvrStrongeListj3.size(); i++){
                countrsvrStrongeListj3+=rsvrStrongeListj3.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("大型", countrsvrStrongeListj3);
            rsvrStrongeListj3.add(rsvrStronge);
            jinList.addAll(rsvrStrongeListj3);
        }

        //今年中型
        List<RsvrStronge> rsvrStrongeListj2 =  rsvrAnalysisMapper.getRsvrStorageAnalysis(time1,time2,adcd,systemTypes,stcdOrStnm,2);
        if(rsvrStrongeListj2.size()>0){
            for (int i=0; i<rsvrStrongeListj2.size(); i++){
                countrsvrStrongeListj2+=rsvrStrongeListj2.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("中型", countrsvrStrongeListj2);
            rsvrStrongeListj2.add(rsvrStronge);
            jinList.addAll(rsvrStrongeListj2);
        }

        //今年小型
        List<RsvrStronge> rsvrStrongeListj1 =  rsvrAnalysisMapper.getRsvrStorageAnalysis(time1,time2,adcd,systemTypes,stcdOrStnm,1);
        if(rsvrStrongeListj1.size()>0){
            for (int i=0; i<rsvrStrongeListj1.size(); i++){
                countrsvrStrongeListj1+=rsvrStrongeListj1.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("小型", countrsvrStrongeListj1);
            rsvrStrongeListj1.add(rsvrStronge);
            jinList.addAll(rsvrStrongeListj1);
        }

        jinZong=countrsvrStrongeListj3+countrsvrStrongeListj2+countrsvrStrongeListj1;
        //去年
        now.add(Calendar.YEAR,-1);
        time2 = now.getTime();
        now.add(Calendar.DATE,-1);
        time1 = now.getTime();

        List<RsvrStronge> quList = new ArrayList<>();
        //去年大型
        List<RsvrStronge> rsvrStrongeListq3 =  rsvrAnalysisMapper.getRsvrStorageAnalysis(time1,time2,adcd,systemTypes,stcdOrStnm,3);
        if(rsvrStrongeListq3.size()>0){
            for (int i=0; i<rsvrStrongeListq3.size(); i++){
                countrsvrStrongeListq3+=rsvrStrongeListq3.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("大型", countrsvrStrongeListq3);
            rsvrStrongeListq3.add(rsvrStronge);
            quList.addAll(rsvrStrongeListq3);
        }

        //去年中型
        List<RsvrStronge> rsvrStrongeListq2 =  rsvrAnalysisMapper.getRsvrStorageAnalysis(time1,time2,adcd,systemTypes,stcdOrStnm,2);
        if(rsvrStrongeListq2.size()>0){
            for (int i=0; i<rsvrStrongeListq2.size(); i++){
                countrsvrStrongeListq2+=rsvrStrongeListq2.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("中型", countrsvrStrongeListq2);
            rsvrStrongeListq2.add(rsvrStronge);
            quList.addAll(rsvrStrongeListq2);
        }

        //去年小型
        List<RsvrStronge> rsvrStrongeListq1 =  rsvrAnalysisMapper.getRsvrStorageAnalysis(time1,time2,adcd,systemTypes,stcdOrStnm,1);
        if(rsvrStrongeListq1.size()>0){
            for (int i=0; i<rsvrStrongeListq1.size(); i++){
                countrsvrStrongeListq1+=rsvrStrongeListq1.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("小型", countrsvrStrongeListq1);
            rsvrStrongeListq1.add(rsvrStronge);
            quList.addAll(rsvrStrongeListq1);
        }

        quZong=countrsvrStrongeListq3+countrsvrStrongeListq2+countrsvrStrongeListq1;

        List<RsvrStronge> changList = new ArrayList<>();
        //常年
        Calendar chang = Calendar.getInstance();
        chang.setTime(date);
        int month = chang.get(Calendar.MONTH)+1;
        int day = chang.get(Calendar.DATE);

        //常年大型
        List<RsvrStronge> rsvrStrongeListc3 =  rsvrAnalysisMapper.getRsvrStorageCLAnalysis(month,day,adcd,systemTypes,stcdOrStnm,3);
        if(rsvrStrongeListc3.size()>0){
            for (int i=0; i<rsvrStrongeListc3.size(); i++){
                countrsvrStrongeListc3+=rsvrStrongeListc3.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("大型", countrsvrStrongeListc3);
            rsvrStrongeListc3.add(rsvrStronge);
            changList.addAll(rsvrStrongeListc3);
        }

        //常年中型
        List<RsvrStronge> rsvrStrongeListc2 =  rsvrAnalysisMapper.getRsvrStorageCLAnalysis(month,day,adcd,systemTypes,stcdOrStnm,2);
        if(rsvrStrongeListc2.size()>0){
            for (int i=0; i<rsvrStrongeListc2.size(); i++){
                countrsvrStrongeListc2+=rsvrStrongeListc2.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("中型", countrsvrStrongeListc2);
            rsvrStrongeListc2.add(rsvrStronge);
            changList.addAll(rsvrStrongeListc2);
        }

        //常年小型
        List<RsvrStronge> rsvrStrongeListc1 =  rsvrAnalysisMapper.getRsvrStorageCLAnalysis(month,day,adcd,systemTypes,stcdOrStnm,1);
        if(rsvrStrongeListc1.size()>0){
            for (int i=0; i<rsvrStrongeListc1.size(); i++){
                countrsvrStrongeListc1+=rsvrStrongeListc1.get(i).getW();
            }
            rsvrStronge = getRsvrStronge("小型", countrsvrStrongeListc1);
            rsvrStrongeListc1.add(rsvrStronge);
            changList.addAll(rsvrStrongeListc1);
        }

        changZong=countrsvrStrongeListc3+countrsvrStrongeListc2+countrsvrStrongeListc1;

        int listLength =  jinList.size();
        System.out.println("长度"+listLength);
        RsvrW rsvrW = null;
        for(int i=0; i<listLength; i++){
            rsvrW = new RsvrW();
            rsvrW.setHnnm(jinList.get(i).getHnnm());
            rsvrW.setStnm(jinList.get(i).getStnm());
            rsvrW.setW(new DecimalFormat("#0.000").format(jinList.get(i).getW()));
            rsvrW.setQw(new DecimalFormat("#0.000").format(quList.get(i).getW()));
            rsvrW.setQwCompare(new DecimalFormat("#0.000").format(jinList.get(i).getW()-quList.get(i).getW()));
            rsvrW.setCw(new DecimalFormat("#0.000").format(changList.get(i).getW()));
            rsvrW.setCwCompare(new DecimalFormat("#0.000").format(jinList.get(i).getW()-changList.get(i).getW()));
            rsvrWList.add(rsvrW);
        }
        //综合
        RsvrW rwZong = new RsvrW();
        rwZong.setHnnm("未知");
        rwZong.setStnm("综合");
        rwZong.setW(new DecimalFormat("#0.000").format(jinZong));
        rwZong.setQw(new DecimalFormat("#0.000").format(quZong));
        rwZong.setQwCompare(new DecimalFormat("#0.000").format(jinZong-quZong));
        rwZong.setCw(new DecimalFormat("#0.000").format(changZong));
        rwZong.setCwCompare(new DecimalFormat("#0.000").format(jinZong-changZong));
        rsvrWList.add(rwZong);

        RsvrStrongeExcel rsvrStrongeExcel = new RsvrStrongeExcel();
        for (int i=0; i<rsvrWList.size(); i++){
            RsvrStrongeItem rsvrStrongeItem = null;
            /*for(int j=0; j<rsvrStrongeExcel.getStrongeItemList().size(); j++){*/
            if(rsvrStrongeExcel.getStrongeItemList().size()>0){
                RsvrStrongeItem rsvrStrongeItemX = rsvrStrongeExcel.getStrongeItemList().get(rsvrStrongeExcel.getStrongeItemList().size()-1);
                if (rsvrStrongeItemX.getHnnm().equals(rsvrWList.get(i).getHnnm())){
                    rsvrStrongeItem = rsvrStrongeItemX;
                    rsvrStrongeItem.getChildList().add(rsvrWList.get(i));
                }
            }
            /*}*/
            if(rsvrStrongeItem == null){
                rsvrStrongeItem = rsvrStrongeExcel.new RsvrStrongeItem();
                rsvrStrongeItem.setHnnm(rsvrWList.get(i).getHnnm());
                rsvrStrongeItem.getChildList().add(rsvrWList.get(i));
                rsvrStrongeExcel.getStrongeItemList().add(rsvrStrongeItem);
            }
        }
        return rsvrStrongeExcel;
    }
    public RsvrStronge getRsvrStronge(String type, double w){
        RsvrStronge rs = new RsvrStronge();
        rs.setStcd("");
        rs.setHnnm("未知");
        rs.setStnm(type);
        rs.setW(w);
        return rs;
    }

    //水库特征值他统计表
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
