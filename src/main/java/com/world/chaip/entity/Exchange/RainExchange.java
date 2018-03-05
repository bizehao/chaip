package com.world.chaip.entity.Exchange;

public class RainExchange {

    //测站编号
    private String stcd;
    //测站名称
    private String stnm;
    //汛期降雨量1月
    private double numOne;
    //汛期降雨量2月
    private double numTwo;
    //汛期降雨量3月
    private double numThree;
    //汛期降雨量4月
    private double numFour;
    //汛期降雨量5月
    private double numFive;
    //汛期降雨量6月
    private double numSix;
    //汛期降雨量7月
    private double numSeven;
    //汛期降雨量8月
    private double numEight;
    //汛期降雨量9月
    private double numNine;
    //汛期降雨量10月
    private double numTen;
    //汛期降雨量11月
    private double numEleven;
    //汛期降雨量12月
    private double numTwelve;
    //汛期降雨量总和
    private double zong;

    public RainExchange() {
    }

    public RainExchange(String stcd, String stnm, double numOne, double numTwo, double numThree, double numFour,
                        double numFive, double numSix, double numSeven, double numEight, double numNine, double numTen,
                        double numEleven, double numTwelve, double zong) {
        this.stcd = stcd;
        this.stnm = stnm;
        this.numOne = numOne;
        this.numTwo = numTwo;
        this.numThree = numThree;
        this.numFour = numFour;
        this.numFive = numFive;
        this.numSix = numSix;
        this.numSeven = numSeven;
        this.numEight = numEight;
        this.numNine = numNine;
        this.numTen = numTen;
        this.numEleven = numEleven;
        this.numTwelve = numTwelve;
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

    public double getNumOne() {
        return numOne;
    }

    public void setNumOne(double numOne) {
        this.numOne = numOne;
    }

    public double getNumTwo() {
        return numTwo;
    }

    public void setNumTwo(double numTwo) {
        this.numTwo = numTwo;
    }

    public double getNumThree() {
        return numThree;
    }

    public void setNumThree(double numThree) {
        this.numThree = numThree;
    }

    public double getNumFour() {
        return numFour;
    }

    public void setNumFour(double numFour) {
        this.numFour = numFour;
    }

    public double getNumFive() {
        return numFive;
    }

    public void setNumFive(double numFive) {
        this.numFive = numFive;
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

    public double getNumTen() {
        return numTen;
    }

    public void setNumTen(double numTen) {
        this.numTen = numTen;
    }

    public double getNumEleven() {
        return numEleven;
    }

    public void setNumEleven(double numEleven) {
        this.numEleven = numEleven;
    }

    public double getNumTwelve() {
        return numTwelve;
    }

    public void setNumTwelve(double numTwelve) {
        this.numTwelve = numTwelve;
    }

    public double getZong() {
        return zong;
    }

    public void setZong(double zong) {
        this.zong = zong;
    }
}
