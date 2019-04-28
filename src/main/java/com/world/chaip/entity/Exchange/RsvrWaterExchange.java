package com.world.chaip.entity.Exchange;

/**
 * 水库水情分析表
 */
public class RsvrWaterExchange {

    //站编号
    private String stcd;
    //站名
    private String stnm;
    //系统
    private String name;
    //前水位
    private double qRZ;
    private String qRZs;
    //前蓄水量
    private double qW;
    private String qWs;
    //后水位
    private double hRZ;
    private String hRZs;
    //后蓄水量
    private double hW;
    private String hWs;
    //蓄水量差
    private String chaW;
    //平均出库总量
    private double avotq;
    private String avotqs;
    //平均入库总量
    private double avinq;
    //出库总量
    private double sumotq;
    private String sumotqs;
    //入库总量
    private double suminq;
    private String suminqs;

    public RsvrWaterExchange() {
    }

    public RsvrWaterExchange(String stcd, String stnm, String name, double qRZ, String qRZs, double qW, String qWs,
                             double hRZ, String hRZs, double hW, String hWs, String chaW, double avotq, String avotqs,
                             double avinq, double sumotq, String sumotqs, double suminq, String suminqs) {
        this.stcd = stcd;
        this.stnm = stnm;
        this.name = name;
        this.qRZ = qRZ;
        this.qRZs = qRZs;
        this.qW = qW;
        this.qWs = qWs;
        this.hRZ = hRZ;
        this.hRZs = hRZs;
        this.hW = hW;
        this.hWs = hWs;
        this.chaW = chaW;
        this.avotq = avotq;
        this.avotqs = avotqs;
        this.avinq = avinq;
        this.sumotq = sumotq;
        this.sumotqs = sumotqs;
        this.suminq = suminq;
        this.suminqs = suminqs;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getqRZ() {
        return qRZ;
    }

    public void setqRZ(double qRZ) {
        this.qRZ = qRZ;
    }

    public String getqRZs() {
        return qRZs;
    }

    public void setqRZs(String qRZs) {
        this.qRZs = qRZs;
    }

    public double getqW() {
        return qW;
    }

    public void setqW(double qW) {
        this.qW = qW;
    }

    public String getqWs() {
        return qWs;
    }

    public void setqWs(String qWs) {
        this.qWs = qWs;
    }

    public double gethRZ() {
        return hRZ;
    }

    public void sethRZ(double hRZ) {
        this.hRZ = hRZ;
    }

    public String gethRZs() {
        return hRZs;
    }

    public void sethRZs(String hRZs) {
        this.hRZs = hRZs;
    }

    public double gethW() {
        return hW;
    }

    public void sethW(double hW) {
        this.hW = hW;
    }

    public String gethWs() {
        return hWs;
    }

    public void sethWs(String hWs) {
        this.hWs = hWs;
    }

    public String getChaW() {
        return chaW;
    }

    public void setChaW(String chaW) {
        this.chaW = chaW;
    }

    public double getAvotq() {
        return avotq;
    }

    public void setAvotq(double avotq) {
        this.avotq = avotq;
    }

    public String getAvotqs() {
        return avotqs;
    }

    public void setAvotqs(String avotqs) {
        this.avotqs = avotqs;
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

    public String getSumotqs() {
        return sumotqs;
    }

    public void setSumotqs(String sumotqs) {
        this.sumotqs = sumotqs;
    }

    public double getSuminq() {
        return suminq;
    }

    public void setSuminq(double suminq) {
        this.suminq = suminq;
    }

    public String getSuminqs() {
        return suminqs;
    }

    public void setSuminqs(String suminqs) {
        this.suminqs = suminqs;
    }
}
