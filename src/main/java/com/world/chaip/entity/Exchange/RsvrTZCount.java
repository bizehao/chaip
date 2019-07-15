package com.world.chaip.entity.Exchange;

/**
 * 水库特征值统计表
 */
public class RsvrTZCount {

    //站名
    private String stnm;
    //最大水位
    private String mrz;
    //最大水位日期
    private String mrztm;
    //最大蓄水量
    private String mw;
    //最大蓄水量出现的日期
    private String mwtm;
    //最大入库流量
    private String minq;
    //最大入库的日期
    private String minqtm;
    //最大出库流量
    private String motq;
    //最大出库的日期
    private String motqtm;

    public RsvrTZCount() {
    }

    public RsvrTZCount(String stnm, String mrz, String mrztm, String mw, String mwtm, String minq, String minqtm, String motq, String motqtm) {
        this.stnm = stnm;
        this.mrz = mrz;
        this.mrztm = mrztm;
        this.mw = mw;
        this.mwtm = mwtm;
        this.minq = minq;
        this.minqtm = minqtm;
        this.motq = motq;
        this.motqtm = motqtm;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getMrz() {
        return mrz;
    }

    public void setMrz(String mrz) {
        this.mrz = mrz;
    }

    public String getMrztm() {
        return mrztm;
    }

    public void setMrztm(String mrztm) {
        this.mrztm = mrztm;
    }

    public String getMw() {
        return mw;
    }

    public void setMw(String mw) {
        this.mw = mw;
    }

    public String getMwtm() {
        return mwtm;
    }

    public void setMwtm(String mwtm) {
        this.mwtm = mwtm;
    }

    public String getMinq() {
        return minq;
    }

    public void setMinq(String minq) {
        this.minq = minq;
    }

    public String getMinqtm() {
        return minqtm;
    }

    public void setMinqtm(String minqtm) {
        this.minqtm = minqtm;
    }

    public String getMotq() {
        return motq;
    }

    public void setMotq(String motq) {
        this.motq = motq;
    }

    public String getMotqtm() {
        return motqtm;
    }

    public void setMotqtm(String motqtm) {
        this.motqtm = motqtm;
    }
}
