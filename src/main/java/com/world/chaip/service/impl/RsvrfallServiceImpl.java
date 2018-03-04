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
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class RsvrfallServiceImpl implements RsvrfallService {

    @Autowired
    RsvrfallMapper rsvrfallMapper;

    //水库 (实时)
    @Override
    public List<Rsvr> getRsvrByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        List<Rsvr> rainfalls=rsvrfallMapper.getRsvrByTerm(dateS,dateE,adcd,systemTypes,stcdOrStnm);
        return rainfalls;
    }

    //水库 (专业)
    @Override
    public DayRsvr getRsvrByZhuanYe(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        int fstp =  getRsverXunQi(dateS);
        int fstp1 =  getRsverXunQi(dateE);
        if(fstp >= fstp1){
            fstp = fstp1;
        }
        double level = 0;
        List<String> levelList = null;
        String levelS = null;
        int jilu = 0;
        List<RsvrZhuanYe> rainfalls=rsvrfallMapper.getRsvrByZhaunYe(dateE, fstp, adcd,systemTypes,stcdOrStnm);
        for(int i=0; i<rainfalls.size(); i++){
            if(rainfalls.get(i).getRz() >= rainfalls.get(i).getFsltdz()){
                jilu++;
                level = rainfalls.get(i).getRz() - rainfalls.get(i).getFsltdz();
                levelS = rainfalls.get(i).getStnm()+"水库，超汛限水位"+level+"米";
                levelList.add(levelS);
            }
        }
        String head = "目前有"+jilu+"处水库水位超过汛限水位";
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
        dayRsvr.setLevelList(levelList);
        return dayRsvr;
    }

    public int getRsverXunQi(Date time){
        String tmString = "";
        int tmInt = 0;
        Calendar now = Calendar.getInstance();
        now.setTime(time);
        int month = now.get(Calendar.MONTH)+1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        System.out.println(month);
        System.out.println(day);
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
            System.out.println(rsvrXunQi);
            if(rsvrXunQi != null){
                xqKS = Integer.parseInt(rsvrXunQi.getBGMD());
                xqJS = Integer.parseInt(rsvrXunQi.getEDMD());
                System.out.println("============");
                System.out.println(xqKS);
                System.out.println(xqJS);
                System.out.println(tmInt);
                System.out.println("============");
                if(tmInt >= xqKS && tmInt <= xqJS){
                    System.out.println("进入"+i);
                    return i;
                }
            }
        }
        System.out.println("最后");
        return 4;
    }
}
