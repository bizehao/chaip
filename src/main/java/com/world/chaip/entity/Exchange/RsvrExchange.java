package com.world.chaip.entity.Exchange;

/**
 * 水库特征值统计表
 */
public class RsvrExchange {

    //站编号
    private String stcd;
    //站名
    private String stnm;
    //河道名
    private String rvnm;
    //最大水位
    private double mrz;
    //最大水位日期
    private String mrztm;
    //最大蓄水量
    private double mw;
    //最大蓄水量出现的日期
    private String mwtm;
    //最大入库流量
    private double minq;
    //最大入库的日期
    private String minqtm;
    //最大出库流量
    private double motq;
    //最大出库的日期
    private String motqtm;

    public RsvrExchange() {
    }

    public RsvrExchange(String stcd, String stnm, String rvnm, double mrz, String mrztm, double mw, String mwtm,
                        double minq, String minqtm, double motq, String motqtm) {
        this.stcd = stcd;
        this.stnm = stnm;
        this.rvnm = rvnm;
        this.mrz = mrz;
        this.mrztm = mrztm;
        this.mw = mw;
        this.mwtm = mwtm;
        this.minq = minq;
        this.minqtm = minqtm;
        this.motq = motq;
        this.motqtm = motqtm;
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

    public String getRvnm() {
        return rvnm;
    }

    public void setRvnm(String rvnm) {
        this.rvnm = rvnm;
    }

    public double getMrz() {
        return mrz;
    }

    public void setMrz(double mrz) {
        this.mrz = mrz;
    }

    public String getMrztm() {
        return mrztm;
    }

    public void setMrztm(String mrztm) {
        this.mrztm = mrztm;
    }

    public double getMw() {
        return mw;
    }

    public void setMw(double mw) {
        this.mw = mw;
    }

    public String getMwtm() {
        return mwtm;
    }

    public void setMwtm(String mwtm) {
        this.mwtm = mwtm;
    }

    public double getMinq() {
        return minq;
    }

    public void setMinq(double minq) {
        this.minq = minq;
    }

    public String getMinqtm() {
        return minqtm;
    }

    public void setMinqtm(String minqtm) {
        this.minqtm = minqtm;
    }

    public double getMotq() {
        return motq;
    }

    public void setMotq(double motq) {
        this.motq = motq;
    }

    public String getMotqtm() {
        return motqtm;
    }

    public void setMotqtm(String motqtm) {
        this.motqtm = motqtm;
    }
}
