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
    //蓄水量差
    private double chaW;
    //平均出库总量
    private double avotq;
    //平均入库总量
    private double avinq;
    //出库总量
    private double sumotq;
    //入库总量
    private double suminq;

    public RsvrWaterExchange() {
    }

    public RsvrWaterExchange(String stcd, String stnm, String hnnm, double qRZ, double qW, double hRZ, double hW,
                             double chaW, double avotq, double avinq, double sumotq, double suminq) {
        this.stcd = stcd;
        this.stnm = stnm;
        this.hnnm = hnnm;
        this.qRZ = qRZ;
        this.qW = qW;
        this.hRZ = hRZ;
        this.hW = hW;
        this.chaW = chaW;
        this.avotq = avotq;
        this.avinq = avinq;
        this.sumotq = sumotq;
        this.suminq = suminq;
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

    public double getqRZ() {
        return qRZ;
    }

    public void setqRZ(double qRZ) {
        this.qRZ = qRZ;
    }

    public double getqW() {
        return qW;
    }

    public void setqW(double qW) {
        this.qW = qW;
    }

    public double gethRZ() {
        return hRZ;
    }

    public void sethRZ(double hRZ) {
        this.hRZ = hRZ;
    }

    public double gethW() {
        return hW;
    }

    public void sethW(double hW) {
        this.hW = hW;
    }

    public double getAvotq() {
        return avotq;
    }

    public void setAvotq(double avotq) {
        this.avotq = avotq;
    }

    public double getAvinq() {
        return avinq;
    }

    public void setAvinq(double avinq) {
        this.avinq = avinq;
    }

    public double getSumotq() {
        return sumotq;
    }

    public void setSumotq(double sumotq) {
        this.sumotq = sumotq;
    }

    public double getSuminq() {
        return suminq;
    }

    public void setSuminq(double suminq) {
        this.suminq = suminq;
    }

    public double getChaW() {
        return chaW;
    }

    public void setChaW(double chaW) {
        this.chaW = chaW;
    }
}
