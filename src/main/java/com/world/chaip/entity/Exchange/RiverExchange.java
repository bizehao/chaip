package com.world.chaip.entity.Exchange;

/**
 * 河道水情分析
 */
public class RiverExchange {

    //站号
    private String stcd;

    //河名
    private String rvnm;

    //站名
    private String stnm;

    //平均流量
    private String avgQ;

    //径流总量
    private String sumQ;

    //最高水位
    private String maxZ;

    //水位的最高日期
    private String maxZTime;

    //最大流量
    private String maxQ;

    //流量的最高日期
    private String maxQTime;

    public RiverExchange() {
    }

    public RiverExchange(String stcd, String rvnm, String stnm, String avgQ, String sumQ, String maxZ, String maxZTime, String maxQ, String maxQTime) {
        this.stcd = stcd;
        this.rvnm = rvnm;
        this.stnm = stnm;
        this.avgQ = avgQ;
        this.sumQ = sumQ;
        this.maxZ = maxZ;
        this.maxZTime = maxZTime;
        this.maxQ = maxQ;
        this.maxQTime = maxQTime;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getRvnm() {
        return rvnm;
    }

    public void setRvnm(String rvnm) {
        this.rvnm = rvnm;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getAvgQ() {
        return avgQ;
    }

    public void setAvgQ(String avgQ) {
        this.avgQ = avgQ;
    }

    public String getSumQ() {
        return sumQ;
    }

    public void setSumQ(String sumQ) {
        this.sumQ = sumQ;
    }

    public String getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(String maxZ) {
        this.maxZ = maxZ;
    }

    public String getMaxZTime() {
        return maxZTime;
    }

    public void setMaxZTime(String maxZTime) {
        this.maxZTime = maxZTime;
    }

    public String getMaxQ() {
        return maxQ;
    }

    public void setMaxQ(String maxQ) {
        this.maxQ = maxQ;
    }

    public String getMaxQTime() {
        return maxQTime;
    }

    public void setMaxQTime(String maxQTime) {
        this.maxQTime = maxQTime;
    }
}
