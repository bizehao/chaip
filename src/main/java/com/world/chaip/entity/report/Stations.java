package com.world.chaip.entity.report;

/**
 * 站点信息
 */
public class Stations {
    //测站编号
    private String stcd;
    //测站名称
    private String stnm;

    public Stations() {
    }

    public Stations(String stcd, String stnm) {
        this.stcd = stcd;
        this.stnm = stnm;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }
}
