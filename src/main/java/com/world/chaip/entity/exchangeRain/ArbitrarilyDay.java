package com.world.chaip.entity.exchangeRain;

/**
 * 任意日降雨量
 */
public class ArbitrarilyDay {

    //市名
    private String adnm;

    //某日到某日
    private double oDay_oDay;

    //去年同期
    private double samePeriodQu;

    //常年同期
    private double samePeriodChang;

    //去年同期
    private String samePeriodCompareQu;

    //常年同期
    private String samePeriodCompareChang;

    public ArbitrarilyDay() {
    }

    public ArbitrarilyDay(String adnm, double oDay_oDay, double samePeriodQu, double samePeriodChang, String samePeriodCompareQu, String samePeriodCompareChang) {
        this.adnm = adnm;
        this.oDay_oDay = oDay_oDay;
        this.samePeriodQu = samePeriodQu;
        this.samePeriodChang = samePeriodChang;
        this.samePeriodCompareQu = samePeriodCompareQu;
        this.samePeriodCompareChang = samePeriodCompareChang;
    }

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public double getoDay_oDay() {
        return oDay_oDay;
    }

    public void setoDay_oDay(double oDay_oDay) {
        this.oDay_oDay = oDay_oDay;
    }

    public double getSamePeriodQu() {
        return samePeriodQu;
    }

    public void setSamePeriodQu(double samePeriodQu) {
        this.samePeriodQu = samePeriodQu;
    }

    public double getSamePeriodChang() {
        return samePeriodChang;
    }

    public void setSamePeriodChang(double samePeriodChang) {
        this.samePeriodChang = samePeriodChang;
    }

    public String getSamePeriodCompareQu() {
        return samePeriodCompareQu;
    }

    public void setSamePeriodCompareQu(String samePeriodCompareQu) {
        this.samePeriodCompareQu = samePeriodCompareQu;
    }

    public String getSamePeriodCompareChang() {
        return samePeriodCompareChang;
    }

    public void setSamePeriodCompareChang(String samePeriodCompareChang) {
        this.samePeriodCompareChang = samePeriodCompareChang;
    }
}
