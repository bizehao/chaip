package com.world.chaip.entity.Exchange;

public class RsvrStronge {
    //县域
    private String adnm;
    //河名
    private String rvnm;
    //站编号
    private String stcd;
    //站名（库名）
    private String stnm;
    //河系
    private String hnnm;
    //蓄水量
    private double w;

    public RsvrStronge() {
    }

    public RsvrStronge(String adnm, String rvnm, String stcd, String stnm, String hnnm, double w) {
        this.adnm = adnm;
        this.rvnm = rvnm;
        this.stcd = stcd;
        this.stnm = stnm;
        this.hnnm = hnnm;
        this.w = w;
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

    public String getHnnm() {
        return hnnm;
    }

    public void setHnnm(String hnnm) {
        this.hnnm = hnnm;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public String getRvnm() {
        return rvnm;
    }

    public void setRvnm(String rvnm) {
        this.rvnm = rvnm;
    }
}
