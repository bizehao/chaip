package com.world.chaip.entity.exchangeRain;

import java.io.Serializable;

/**
 * 汛期降雨量映射表
 */
public class XunQi implements Serializable {
    
    //测站名称
    private String stnm;

    //今6月
    private double jxqSix;
    //今7月
    private double jxqSeven;
    //今8月
    private double jxqEight;
    //今9月
    private double jxqNine;
    //今6-9月
    private double jxqSix_Nine;
    //今6-9月的对比
    private String jxqSix_Nine_Compare;

    //去6月
    private String qxqSix;
    //去7月
    private String qxqSeven;
    //去8月
    private String qxqEight;
    //去9月
    private String qxqNine;
    //去6-9月
    private String qxqSix_Nine;

    //常6月
    private String cxqSix;
    //常7月
    private String cxqSeven;
    //常8月
    private String cxqEight;
    //常9月
    private String cxqNine;
    //常6-9月
    private String cxqSix_Nine;

    public XunQi() {
    }

    public XunQi(String stnm, double jxqSix, double jxqSeven, double jxqEight, double jxqNine,
                 double jxqSix_Nine, String jxqSix_Nine_Compare, String qxqSix, String qxqSeven,
                 String qxqEight, String qxqNine, String qxqSix_Nine, String cxqSix, String cxqSeven,
                 String cxqEight, String cxqNine, String cxqSix_Nine) {
        this.stnm = stnm;
        this.jxqSix = jxqSix;
        this.jxqSeven = jxqSeven;
        this.jxqEight = jxqEight;
        this.jxqNine = jxqNine;
        this.jxqSix_Nine = jxqSix_Nine;
        this.jxqSix_Nine_Compare = jxqSix_Nine_Compare;
        this.qxqSix = qxqSix;
        this.qxqSeven = qxqSeven;
        this.qxqEight = qxqEight;
        this.qxqNine = qxqNine;
        this.qxqSix_Nine = qxqSix_Nine;
        this.cxqSix = cxqSix;
        this.cxqSeven = cxqSeven;
        this.cxqEight = cxqEight;
        this.cxqNine = cxqNine;
        this.cxqSix_Nine = cxqSix_Nine;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public double getJxqSix() {
        return jxqSix;
    }

    public void setJxqSix(double jxqSix) {
        this.jxqSix = jxqSix;
    }

    public double getJxqSeven() {
        return jxqSeven;
    }

    public void setJxqSeven(double jxqSeven) {
        this.jxqSeven = jxqSeven;
    }

    public double getJxqEight() {
        return jxqEight;
    }

    public void setJxqEight(double jxqEight) {
        this.jxqEight = jxqEight;
    }

    public double getJxqNine() {
        return jxqNine;
    }

    public void setJxqNine(double jxqNine) {
        this.jxqNine = jxqNine;
    }

    public double getJxqSix_Nine() {
        return jxqSix_Nine;
    }

    public void setJxqSix_Nine(double jxqSix_Nine) {
        this.jxqSix_Nine = jxqSix_Nine;
    }

    public String getQxqSix() {
        return qxqSix;
    }

    public void setQxqSix(String qxqSix) {
        this.qxqSix = qxqSix;
    }

    public String getQxqSeven() {
        return qxqSeven;
    }

    public void setQxqSeven(String qxqSeven) {
        this.qxqSeven = qxqSeven;
    }

    public String getQxqEight() {
        return qxqEight;
    }

    public void setQxqEight(String qxqEight) {
        this.qxqEight = qxqEight;
    }

    public String getQxqNine() {
        return qxqNine;
    }

    public void setQxqNine(String qxqNine) {
        this.qxqNine = qxqNine;
    }

    public String getQxqSix_Nine() {
        return qxqSix_Nine;
    }

    public void setQxqSix_Nine(String qxqSix_Nine) {
        this.qxqSix_Nine = qxqSix_Nine;
    }

    public String getCxqSix() {
        return cxqSix;
    }

    public void setCxqSix(String cxqSix) {
        this.cxqSix = cxqSix;
    }

    public String getCxqSeven() {
        return cxqSeven;
    }

    public void setCxqSeven(String cxqSeven) {
        this.cxqSeven = cxqSeven;
    }

    public String getCxqEight() {
        return cxqEight;
    }

    public void setCxqEight(String cxqEight) {
        this.cxqEight = cxqEight;
    }

    public String getCxqNine() {
        return cxqNine;
    }

    public void setCxqNine(String cxqNine) {
        this.cxqNine = cxqNine;
    }

    public String getCxqSix_Nine() {
        return cxqSix_Nine;
    }

    public void setCxqSix_Nine(String cxqSix_Nine) {
        this.cxqSix_Nine = cxqSix_Nine;
    }

    public String getJxqSix_Nine_Compare() {
        return jxqSix_Nine_Compare;
    }

    public void setJxqSix_Nine_Compare(String jxqSix_Nine_Compare) {
        this.jxqSix_Nine_Compare = jxqSix_Nine_Compare;
    }

    @Override
    public String toString() {
        return "XunQi{" + "stnm='" + stnm + '\'' + ", jxqSix=" + jxqSix + ", jxqSeven=" + jxqSeven + ", jxqEight=" + jxqEight + ", jxqNine=" + jxqNine + ", jxqSix_Nine=" + jxqSix_Nine + ", jxqSix_Nine_Compare='" + jxqSix_Nine_Compare + '\'' + ", qxqSix='" + qxqSix + '\'' + ", qxqSeven='" + qxqSeven + '\'' + ", qxqEight='" + qxqEight + '\'' + ", qxqNine='" + qxqNine + '\'' + ", qxqSix_Nine='" + qxqSix_Nine + '\'' + ", cxqSix='" + cxqSix + '\'' + ", cxqSeven='" + cxqSeven + '\'' + ", cxqEight='" + cxqEight + '\'' + ", cxqNine='" + cxqNine + '\'' + ", cxqSix_Nine='" + cxqSix_Nine + '\'' + '}';
    }
}
