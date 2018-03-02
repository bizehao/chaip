package com.world.chaip.entity.excelFormat;

import com.world.chaip.entity.report.RsvrZhuanYe;

import java.util.List;

//河道（专业）
public class DayRsvr {

    //汛期等级
    private int fstp;

    private List<RsvrZhuanYe> rsvrZhuanYeList;

    public DayRsvr() {
    }

    public DayRsvr(int fstp, List<RsvrZhuanYe> rsvrZhuanYeList) {
        this.fstp = fstp;
        this.rsvrZhuanYeList = rsvrZhuanYeList;
    }

    public int getFstp() {
        return fstp;
    }

    public void setFstp(int fstp) {
        this.fstp = fstp;
    }

    public List<RsvrZhuanYe> getRsvrZhuanYeList() {
        return rsvrZhuanYeList;
    }

    public void setRsvrZhuanYeList(List<RsvrZhuanYe> rsvrZhuanYeList) {
        this.rsvrZhuanYeList = rsvrZhuanYeList;
    }
}
