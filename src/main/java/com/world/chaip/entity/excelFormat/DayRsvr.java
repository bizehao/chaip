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
    private String levels;

    public DayRsvr() {
    }

    public DayRsvr(String fstp, List<RsvrZhuanYe> rsvrZhuanYeList, String levels) {
        this.fstp = fstp;
        this.rsvrZhuanYeList = rsvrZhuanYeList;
        this.levels = levels;
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

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }
}
