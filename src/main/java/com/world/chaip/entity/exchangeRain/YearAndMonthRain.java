package com.world.chaip.entity.exchangeRain;

import com.world.chaip.entity.Exchange.RainExchange;

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

}
