package com.world.chaip.entity.report;
/**
 * 水库表
 *
 * @author Administrator
 */

import java.util.Date;

public class Rsvr {
    //县域
    private String adnm;
    //河名
    private String rvnm;
    //站号
    private String stcd;
    //库名
    private String stnm;
    //水系
    private String hnnm;
    //站类型
    private String name;
    //时间
    private String tm;
    //水位
    private String rz;
    //蓄量
    private double w;
    //出库流量
    private double otq;
    //入库流量
    private double inq;
    //库水特征码
    private int RWCHRCD;

    public Rsvr() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Rsvr(String adnm, String rvnm, String stcd, String stnm, String hnnm, String name, String tm, String rz, double w, double otq, double inq, int RWCHRCD) {
        this.adnm = adnm;
        this.rvnm = rvnm;
        this.stcd = stcd;
        this.stnm = stnm;
        this.hnnm = hnnm;
        this.name = name;
        this.tm = tm;
        this.rz = rz;
        this.w = w;
        this.otq = otq;
        this.inq = inq;
        this.RWCHRCD = RWCHRCD;
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

    public String getTm() {
        return tm == null ? "" : tm.substring(0, tm.length() - 2);
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getOtq() {
        return otq;
    }

    public void setOtq(double otq) {
        this.otq = otq;
    }

    public double getInq() {
        return inq;
    }

    public void setInq(double inq) {
        this.inq = inq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRWCHRCD() {
        return RWCHRCD;
    }

    public void setRWCHRCD(int RWCHRCD) {
        this.RWCHRCD = RWCHRCD;
    }
}
