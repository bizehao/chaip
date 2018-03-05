package com.world.chaip.entity.Exchange;

public class RainExchange {
    //测站编号
    private String stcd;
    //测站名称
    private String stnm;
    //汛期降雨量6月
    private double numSix;
    //汛期降雨量7月
    private double numSeven;
    //汛期降雨量8月
    private double numEight;
    //汛期降雨量9月
    private double numNine;
    //汛期降雨量6-9月
    private double zong;

    public RainExchange() {
    }

    public RainExchange(String stcd, String stnm, double numSix, double numSeven, double numEight, double numNine, double zong) {
        this.stcd = stcd;
        this.stnm = stnm;
        this.numSix = numSix;
        this.numSeven = numSeven;
        this.numEight = numEight;
        this.numNine = numNine;
        this.zong = zong;
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

    public double getNumSix() {
        return numSix;
    }

    public void setNumSix(double numSix) {
        this.numSix = numSix;
    }

    public double getNumSeven() {
        return numSeven;
    }

    public void setNumSeven(double numSeven) {
        this.numSeven = numSeven;
    }

    public double getNumEight() {
        return numEight;
    }

    public void setNumEight(double numEight) {
        this.numEight = numEight;
    }

    public double getNumNine() {
        return numNine;
    }

    public void setNumNine(double numNine) {
        this.numNine = numNine;
    }

    public double getZong() {
        return zong;
    }

    public void setZong(double zong) {
        this.zong = zong;
    }
}
