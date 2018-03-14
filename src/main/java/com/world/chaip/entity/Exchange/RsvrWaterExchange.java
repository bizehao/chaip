package com.world.chaip.entity.Exchange;

/**
 * 水库水情分析表
 */
public class RsvrWaterExchange {

    //站编号
    private String stcd;
    //站名
    private String stnm;
    //水系名
    private String hnnm;
    //前水位
    private double qRZ;
    //前蓄水量
    private double qW;
    //后水位
    private double hRZ;
    //后蓄水量
    private double hW;
    //平均出库总量
    private double avotq;
    //平均入库总量
    private double avinq;



    public RsvrWaterExchange() {
    }

}
