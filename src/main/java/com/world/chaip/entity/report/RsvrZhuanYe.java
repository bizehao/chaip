package com.world.chaip.entity.report;

/**
 * 专业水库
 */
public class RsvrZhuanYe {

    //水库名称
    private String stnm;

    //水库编号
    private String stcd;

    //数据时间
    private String tm;

    //总库容
    private double ttcp;

    //汛期水位
    private double fsltdz;

    //库容
    private double fsltdw;

    //水位
    private double rz;

    //蓄水量
    private double w;

    //入库流量
    private double inq;

    //下泄流量
    private double otq;

    public RsvrZhuanYe() {
    }

    public RsvrZhuanYe(String stnm, String stcd, String tm, double ttcp, double fsltdz, double fsltdw, double rz, double w, double inq, double otq) {
        this.stnm = stnm;
        this.stcd = stcd;
        this.tm = tm;
        this.ttcp = ttcp;
        this.fsltdz = fsltdz;
        this.fsltdw = fsltdw;
        this.rz = rz;
        this.w = w;
        this.inq = inq;
        this.otq = otq;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public double getTtcp() {
        return ttcp;
    }

    public void setTtcp(double ttcp) {
        this.ttcp = ttcp;
    }

    public double getFsltdz() {
        return fsltdz;
    }

    public void setFsltdz(double fsltdz) {
        this.fsltdz = fsltdz;
    }

    public double getFsltdw() {
        return fsltdw;
    }

    public void setFsltdw(double fsltdw) {
        this.fsltdw = fsltdw;
    }

    public double getRz() {
        return rz;
    }

    public void setRz(double rz) {
        this.rz = rz;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getInq() {
        return inq;
    }

    public void setInq(double inq) {
        this.inq = inq;
    }

    public double getOtq() {
        return otq;
    }

    public void setOtq(double otq) {
        this.otq = otq;
    }
}
