package com.world.chaip.entity.Exchange;

public class RsvrStrongeChild {
    //县域
    private String adnm;
    //河名
    private String rvnm;
    //站编号
    private String stcd;
    //站名（库名）
    private String stnm;
    //蓄水量
    private String w;
    //去年同期蓄水量
    private String qw;
    //较去年
    private String qwCompare;
    //常年同期
    private String cw;
    //较常年
    private String cwCompare;

    public RsvrStrongeChild(String adnm, String rvnm, String stcd, String stnm, String w, String qw, String qwCompare, String cw, String cwCompare) {
        this.adnm = adnm;
        this.rvnm = rvnm;
        this.stcd = stcd;
        this.stnm = stnm;
        this.w = w;
        this.qw = qw;
        this.qwCompare = qwCompare;
        this.cw = cw;
        this.cwCompare = cwCompare;
    }

    public RsvrStrongeChild() {
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

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getQw() {
        return qw;
    }

    public void setQw(String qw) {
        this.qw = qw;
    }

    public String getQwCompare() {
        return qwCompare;
    }

    public void setQwCompare(String qwCompare) {
        this.qwCompare = qwCompare;
    }

    public String getCw() {
        return cw;
    }

    public void setCw(String cw) {
        this.cw = cw;
    }

    public String getCwCompare() {
        return cwCompare;
    }

    public void setCwCompare(String cwCompare) {
        this.cwCompare = cwCompare;
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
