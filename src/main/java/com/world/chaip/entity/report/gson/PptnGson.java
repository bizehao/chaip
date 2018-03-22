package com.world.chaip.entity.report.gson;

import java.util.Date;

public class PptnGson {
    //县名
    private String adnm;
    //测站名称
    private String stnm;
    //站类
    private String name;
    //日总和降雨量
    private double countDrp;
    //9点
    private double nineDrp = 0;
    //10点
    private double tenDrp = 0;
    //11点
    private double elevenDrp = 0;
    //12点
    private double twelveDrp = 0;
    //13点
    private double thirteenDrp = 0;
    //14点
    private double fourteenDrp = 0;
    //15点
    private double fifteenDrp = 0;
    //16点
    private double sixteenDrp = 0;
    //17点
    private double seventeenDrp = 0;
    //18点
    private double eighteenDrp = 0;
    //19点
    private double nineteenDrp = 0;
    //20点
    private double twentyDrp = 0;
    //21点
    private double twenty_oneDrp = 0;
    //22点
    private double twenty_twoDrp = 0;
    //23点
    private double twenty_threeDrp = 0;
    //0点
    private double zeroDrp = 0;
    //1点
    private double oneDrp = 0;
    //2点
    private double twoDrp = 0;
    //3点
    private double threeDrp = 0;
    //4点
    private double fourDrp = 0;
    //5点
    private double fiveDrp = 0;
    //6点
    private double sixDrp = 0;
    //7点
    private double sevenDrp = 0;
    //8点
    private double eightDrp = 0;

    public PptnGson() {
        super();
        // TODO Auto-generated constructor stub
    }

    public PptnGson(String adnm, String stnm, String name, double countDrp, double nineDrp, double tenDrp, double elevenDrp,
                    double twelveDrp, double thirteenDrp, double fourteenDrp, double fifteenDrp, double sixteenDrp,
                    double seventeenDrp, double eighteenDrp, double nineteenDrp, double twentyDrp, double twenty_oneDrp,
                    double twenty_twoDrp, double twenty_threeDrp, double zeroDrp, double oneDrp, double twoDrp,
                    double threeDrp, double fourDrp, double fiveDrp, double sixDrp, double sevenDrp, double eightDrp) {
        this.adnm = adnm;
        this.stnm = stnm;
        this.name = name;
        this.countDrp = countDrp;
        this.nineDrp = nineDrp;
        this.tenDrp = tenDrp;
        this.elevenDrp = elevenDrp;
        this.twelveDrp = twelveDrp;
        this.thirteenDrp = thirteenDrp;
        this.fourteenDrp = fourteenDrp;
        this.fifteenDrp = fifteenDrp;
        this.sixteenDrp = sixteenDrp;
        this.seventeenDrp = seventeenDrp;
        this.eighteenDrp = eighteenDrp;
        this.nineteenDrp = nineteenDrp;
        this.twentyDrp = twentyDrp;
        this.twenty_oneDrp = twenty_oneDrp;
        this.twenty_twoDrp = twenty_twoDrp;
        this.twenty_threeDrp = twenty_threeDrp;
        this.zeroDrp = zeroDrp;
        this.oneDrp = oneDrp;
        this.twoDrp = twoDrp;
        this.threeDrp = threeDrp;
        this.fourDrp = fourDrp;
        this.fiveDrp = fiveDrp;
        this.sixDrp = sixDrp;
        this.sevenDrp = sevenDrp;
        this.eightDrp = eightDrp;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public double getCountDrp() {
        return countDrp;
    }

    public void setCountDrp(double countDrp) {
        this.countDrp = countDrp;
    }

    public double getNineDrp() {
        return nineDrp;
    }

    public void setNineDrp(double nineDrp) {
        this.nineDrp = nineDrp;
    }

    public double getTenDrp() {
        return tenDrp;
    }

    public void setTenDrp(double tenDrp) {
        this.tenDrp = tenDrp;
    }

    public double getElevenDrp() {
        return elevenDrp;
    }

    public void setElevenDrp(double elevenDrp) {
        this.elevenDrp = elevenDrp;
    }

    public double getTwelveDrp() {
        return twelveDrp;
    }

    public void setTwelveDrp(double twelveDrp) {
        this.twelveDrp = twelveDrp;
    }

    public double getThirteenDrp() {
        return thirteenDrp;
    }

    public void setThirteenDrp(double thirteenDrp) {
        this.thirteenDrp = thirteenDrp;
    }

    public double getFourteenDrp() {
        return fourteenDrp;
    }

    public void setFourteenDrp(double fourteenDrp) {
        this.fourteenDrp = fourteenDrp;
    }

    public double getFifteenDrp() {
        return fifteenDrp;
    }

    public void setFifteenDrp(double fifteenDrp) {
        this.fifteenDrp = fifteenDrp;
    }

    public double getSixteenDrp() {
        return sixteenDrp;
    }

    public void setSixteenDrp(double sixteenDrp) {
        this.sixteenDrp = sixteenDrp;
    }

    public double getSeventeenDrp() {
        return seventeenDrp;
    }

    public void setSeventeenDrp(double seventeenDrp) {
        this.seventeenDrp = seventeenDrp;
    }

    public double getEighteenDrp() {
        return eighteenDrp;
    }

    public void setEighteenDrp(double eighteenDrp) {
        this.eighteenDrp = eighteenDrp;
    }

    public double getNineteenDrp() {
        return nineteenDrp;
    }

    public void setNineteenDrp(double nineteenDrp) {
        this.nineteenDrp = nineteenDrp;
    }

    public double getTwentyDrp() {
        return twentyDrp;
    }

    public void setTwentyDrp(double twentyDrp) {
        this.twentyDrp = twentyDrp;
    }

    public double getTwenty_oneDrp() {
        return twenty_oneDrp;
    }

    public void setTwenty_oneDrp(double twenty_oneDrp) {
        this.twenty_oneDrp = twenty_oneDrp;
    }

    public double getTwenty_twoDrp() {
        return twenty_twoDrp;
    }

    public void setTwenty_twoDrp(double twenty_twoDrp) {
        this.twenty_twoDrp = twenty_twoDrp;
    }

    public double getTwenty_threeDrp() {
        return twenty_threeDrp;
    }

    public void setTwenty_threeDrp(double twenty_threeDrp) {
        this.twenty_threeDrp = twenty_threeDrp;
    }

    public double getZeroDrp() {
        return zeroDrp;
    }

    public void setZeroDrp(double zeroDrp) {
        this.zeroDrp = zeroDrp;
    }

    public double getOneDrp() {
        return oneDrp;
    }

    public void setOneDrp(double oneDrp) {
        this.oneDrp = oneDrp;
    }

    public double getTwoDrp() {
        return twoDrp;
    }

    public void setTwoDrp(double twoDrp) {
        this.twoDrp = twoDrp;
    }

    public double getThreeDrp() {
        return threeDrp;
    }

    public void setThreeDrp(double threeDrp) {
        this.threeDrp = threeDrp;
    }

    public double getFourDrp() {
        return fourDrp;
    }

    public void setFourDrp(double fourDrp) {
        this.fourDrp = fourDrp;
    }

    public double getFiveDrp() {
        return fiveDrp;
    }

    public void setFiveDrp(double fiveDrp) {
        this.fiveDrp = fiveDrp;
    }

    public double getSixDrp() {
        return sixDrp;
    }

    public void setSixDrp(double sixDrp) {
        this.sixDrp = sixDrp;
    }

    public double getSevenDrp() {
        return sevenDrp;
    }

    public void setSevenDrp(double sevenDrp) {
        this.sevenDrp = sevenDrp;
    }

    public double getEightDrp() {
        return eightDrp;
    }

    public void setEightDrp(double eightDrp) {
        this.eightDrp = eightDrp;
    }

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
