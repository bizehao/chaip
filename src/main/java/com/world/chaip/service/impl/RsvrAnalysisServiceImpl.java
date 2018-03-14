package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RsvrExchange;
import com.world.chaip.entity.Exchange.RsvrWaterExcel;
import com.world.chaip.entity.Exchange.RsvrWaterExcel.RsvrWC;
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
            day = tm.getActualMaximum(Calendar.DAY_OF_MONTH);
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
                    rsvrWaterExchange.setAvotq(otqlistArray[i]/countDay);
                    rsvrWaterExchange.setSumotq(otqlistArray[i]/countDay*3600*24*countDay);
                    rsvrWaterExchange.setSuminq(inqlistArray[i]/countDay*3600*24*countDay);
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
            rsvrWaterExchange.setqRZ(beginRsvrList.get(i).getRz());
            rsvrWaterExchange.setqW(beginRsvrList.get(i).getW());
            rsvrWaterExchange.sethRZ(endiRsvrList.get(i).getRz());
            rsvrWaterExchange.sethW(endiRsvrList.get(i).getW());
            rsvrWaterExchange.setChaW(endiRsvrList.get(i).getW()-beginRsvrList.get(i).getW());
            rsvrsList.add(rsvrWaterExchange);
        }
        RsvrWaterExcel rsvrWaterExcel = new RsvrWaterExcel();
        for(int i=0; i<rsvrsList.size(); i++){
            RsvrWC rsvrWC = null;
            for (int j=0; j<rsvrWaterExcel.getRsvrWCList().size(); j++){
                RsvrWC rsvrWCX = rsvrWaterExcel.getRsvrWCList().get(i);
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
