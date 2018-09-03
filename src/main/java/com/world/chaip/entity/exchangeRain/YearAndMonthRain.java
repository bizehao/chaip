package com.world.chaip.entity.exchangeRain;

import com.world.chaip.entity.Exchange.RainExchange;

/**
 * 年逐月降雨量
 */
public class YearAndMonthRain extends RainExchange{

    //今年总降雨量
    private double jinYearZong;

    //去年总降雨量
    private double quYearZong;

    //常年总降雨量
    private double changYearZong;

    //去年比较
    private double compareQu;

    //常年比较
    private double compareChang;

    //相对值(去年)
    private String relativeQu;

    //相对值(常年)
    private String relativeChang;

    public YearAndMonthRain() {

    }

    public YearAndMonthRain(double jinYearZong, double quYearZong, double changYearZong, double compareQu, double compareChang, String relativeQu, String relativeChang) {
        this.jinYearZong = jinYearZong;
        this.quYearZong = quYearZong;
        this.changYearZong = changYearZong;
        this.compareQu = compareQu;
        this.compareChang = compareChang;
        this.relativeQu = relativeQu;
        this.relativeChang = relativeChang;
    }

    public YearAndMonthRain(String adnm, int adnmCount, double numOne, double numTwo, double numThree, double numFour, double numFive, double numSix, double numSeven, double numEight, double numNine, double numTen, double numEleven, double numTwelve, double zong, double jinYearZong, double quYearZong, double changYearZong, double compareQu, double compareChang, String relativeQu, String relativeChang) {
        super(adnm, adnmCount, numOne, numTwo, numThree, numFour, numFive, numSix, numSeven, numEight, numNine, numTen, numEleven, numTwelve, zong);
        this.jinYearZong = jinYearZong;
        this.quYearZong = quYearZong;
        this.changYearZong = changYearZong;
        this.compareQu = compareQu;
        this.compareChang = compareChang;
        this.relativeQu = relativeQu;
        this.relativeChang = relativeChang;
    }

    public double getJinYearZong() {
        return jinYearZong;
    }

    public void setJinYearZong(double jinYearZong) {
        this.jinYearZong = jinYearZong;
    }

    public double getQuYearZong() {
        return quYearZong;
    }

    public void setQuYearZong(double quYearZong) {
        this.quYearZong = quYearZong;
    }

    public double getChangYearZong() {
        return changYearZong;
    }

    public void setChangYearZong(double changYearZong) {
        this.changYearZong = changYearZong;
    }

    public double getCompareQu() {
        return compareQu;
    }

    public void setCompareQu(double compareQu) {
        this.compareQu = compareQu;
    }

    public double getCompareChang() {
        return compareChang;
    }

    public void setCompareChang(double compareChang) {
        this.compareChang = compareChang;
    }

    public String getRelativeQu() {
        return relativeQu;
    }

    public void setRelativeQu(String relativeQu) {
        this.relativeQu = relativeQu;
    }

    public String getRelativeChang() {
        return relativeChang;
    }

    public void setRelativeChang(String relativeChang) {
        this.relativeChang = relativeChang;
    }
}
