package com.world.chaip.entity.excelFormat;

import com.world.chaip.entity.report.RsvrZhuanYe;

import java.util.List;

//河道（专业）
public class DayRsvr {

    //汛期等级
    private String fstp;
    //汛期数据
    private List<RsvrZhuanYe> rsvrZhuanYeList;
    //汛期报告
    private List<String> levelList;

    public DayRsvr() {
    }

    public DayRsvr(String fstp, List<RsvrZhuanYe> rsvrZhuanYeList, List<String> levelList) {
        this.fstp = fstp;
        this.rsvrZhuanYeList = rsvrZhuanYeList;
        this.levelList = levelList;
    }

    public String getFstp() {
        return fstp;
    }

    public void setFstp(String fstp) {
        this.fstp = fstp;
    }

    public List<RsvrZhuanYe> getRsvrZhuanYeList() {
        return rsvrZhuanYeList;
    }

    public void setRsvrZhuanYeList(List<RsvrZhuanYe> rsvrZhuanYeList) {
        this.rsvrZhuanYeList = rsvrZhuanYeList;
    }

    public List<String> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<String> levelList) {
        this.levelList = levelList;
    }
}
