package com.world.chaip.service.impl;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.excelFormat.DayRsvr;
import com.world.chaip.entity.report.River;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.RsvrXunQi;
import com.world.chaip.entity.report.RsvrZhuanYe;
import com.world.chaip.mapper.RsvrfallMapper;
import com.world.chaip.service.RsvrfallService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.ExcepTimeUtil;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class RsvrfallServiceImpl implements RsvrfallService {

    @Autowired
    RsvrfallMapper rsvrfallMapper;

    //水库 (实时)
    @Override
    public List<Rsvr> getRsvrByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) throws ParseException {
        List<Rsvr> rainfalls;
        if(dateS.equals(dateE)){
            rainfalls=rsvrfallMapper.getRsvrByTermNew(dateS,adcd,systemTypes,stcdOrStnm);
        }else{
            rainfalls=rsvrfallMapper.getRsvrByTerm(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        }
        for(Rsvr rsvr : rainfalls){
            rsvr.setTm(ExcepTimeUtil.getExcepTime(rsvr.getTm()));
            if(rsvr.getRWCHRCD() == 1){
                rsvr.setRz("干涸");
            }
        }
        return rainfalls;
    }

    //水库 (专业)
    @Override
    public DayRsvr getRsvrByZhuanYe(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) throws ParseException {
        int fstp =  getRsverXunQi(dateS);
        int fstp1 =  getRsverXunQi(dateE);
        if(fstp >= fstp1){
            fstp = fstp1;
        }
        double level = 0;
        List<String> levelList = new ArrayList<>();
        String levelS = "";
        int jilu = 0;
        List<RsvrZhuanYe> rainfalls;
        if(dateS.equals(dateE)){
            rainfalls=rsvrfallMapper.getRsvrByZhaunYeNew(dateE, fstp, adcd,systemTypes,stcdOrStnm);
        }else{
            rainfalls=rsvrfallMapper.getRsvrByZhaunYe(dateS,dateE, fstp, adcd,systemTypes,stcdOrStnm);
        }

        for(int i=0; i<rainfalls.size(); i++){
            rainfalls.get(i).setTm(ExcepTimeUtil.getExcepTime(rainfalls.get(i).getTm()));
            /*rainfalls.get(i).setTtcp(Double.parseDouble(new DecimalFormat("#0.00").format(rainfalls.get(i).getTtcp())));*/
            rainfalls.get(i).setFsltdz(rainfalls.get(i).getFsltdz()==null?"":new DecimalFormat("#0.00").format(Double.parseDouble(rainfalls.get(i).getFsltdz())));
            /*rainfalls.get(i).setFsltdw(Double.parseDouble(new DecimalFormat("#0.00").format(rainfalls.get(i).getFsltdw())));*/
            rainfalls.get(i).setRz(rainfalls.get(i).getRz()==null?"":new DecimalFormat("#0.00").format(Double.parseDouble(rainfalls.get(i).getRz())));
            rainfalls.get(i).setW(rainfalls.get(i).getW()==null?"":new DecimalFormat("#0.000").format(Double.parseDouble(rainfalls.get(i).getW())));
            rainfalls.get(i).setInq(rainfalls.get(i).getInq()==null?"":new DecimalFormat("#0.000").format(Double.parseDouble(rainfalls.get(i).getInq())));
            rainfalls.get(i).setOtq(rainfalls.get(i).getOtq()==null?"":new DecimalFormat("#0.000").format(Double.parseDouble(rainfalls.get(i).getOtq())));
            double a = rainfalls.get(i).getRz().length()==0?0:Double.parseDouble(rainfalls.get(i).getRz());
            double b = rainfalls.get(i).getFsltdz().length()==0?0:Double.parseDouble(rainfalls.get(i).getFsltdz());
            if(a >= b){
                jilu++;
                level = a - b;
                levelS = rainfalls.get(i).getStnm()+"水库，超汛限水位"+new DecimalFormat("#0.00").format(level)+"米";
                levelList.add(levelS);
            }
            if(rainfalls.get(i).getRWCHRCD() == 1){
                rainfalls.get(i).setRz("干涸");
            }
        }
        String head = "目前有"+jilu+"处水库水位超过汛限水位";
        if(levelList.size()>0){
            for(int i=0; i<levelList.size(); i++){
                head+="其中"+levelList.get(i)+",";
            }
            head.substring(head.length());
            head+="。";
        }
        levelList.add(0,head);

        DayRsvr dayRsvr = new DayRsvr();
        if(fstp==1){
            dayRsvr.setFstp("主汛期");
        }else if(fstp==2){
            dayRsvr.setFstp("后汛期");
        }else if(fstp==3){
            dayRsvr.setFstp("过渡期");
        }else{
            dayRsvr.setFstp("其它");
        }
        dayRsvr.setRsvrZhuanYeList(rainfalls);
        dayRsvr.setLevels(head);
        return dayRsvr;
    }

    public int getRsverXunQi(Date time){
        String tmString = "";
        int tmInt = 0;
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        int month = now.get(Calendar.MONTH)+1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        if(day<=9){
            tmString = month+"0"+day;
        }else{
            tmString = String.valueOf(month)+String.valueOf(day);
        }
        tmInt = Integer.parseInt(tmString);
        RsvrXunQi rsvrXunQi = null;
        int xqKS = 0;
        int xqJS = 0;
        for(int i=1; i<=4; i++){
            rsvrXunQi = rsvrfallMapper.getRsvrFS(i);
            if(rsvrXunQi != null){
                xqKS = Integer.parseInt(rsvrXunQi.getBGMD());
                xqJS = Integer.parseInt(rsvrXunQi.getEDMD());
                if(tmInt >= xqKS && tmInt <= xqJS){
                    return i;
                }
            }
        }
        return 4;
    }
}
