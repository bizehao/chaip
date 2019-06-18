package com.world.chaip.entity.report;

/**
 * 专业水库
 */
public class RsvrZhuanYe {

    //水库名称
    private String stnm;

    //水库编号
    private String stcd;

    //数据时间
    private String tm;

    //总库容
    private String ttcp;

    //汛期水位
    private String fsltdz;

    //汛限库容
    private String fsltdw;

    //水位
    private String rz;

    //蓄水量
    private String w;

    //入库流量
    private String inq;

    //下泄流量
    private String otq;

    //库水特征码
    private int RWCHRCD;

    //入流时段长
    private String INQDR;

    //日入库流量
    private String inqOfDay;

    public RsvrZhuanYe() {
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getTtcp() {
        return ttcp;
    }

    public void setTtcp(String ttcp) {
        this.ttcp = ttcp;
    }

    public String getFsltdz() {
        return fsltdz;
    }

    public void setFsltdz(String fsltdz) {
        this.fsltdz = fsltdz;
    }

    public String getFsltdw() {
        return fsltdw;
    }

    public void setFsltdw(String fsltdw) {
        this.fsltdw = fsltdw;
    }

    public String getRz() {
        return rz;
    }

    public void setRz(String rz) {
        this.rz = rz;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getInq() {
        return inq;
    }

    public void setInq(String inq) {
        this.inq = inq;
    }

    public String getOtq() {
        return otq;
    }

    public void setOtq(String otq) {
        this.otq = otq;
    }

    public int getRWCHRCD() {
        return RWCHRCD;
    }

    public void setRWCHRCD(int RWCHRCD) {
        this.RWCHRCD = RWCHRCD;
    }

    public String getINQDR() {
        return INQDR;
    }

    public void setINQDR(String INQDR) {
        this.INQDR = INQDR;
    }

    public String getInqOfDay() {
        return inqOfDay;
    }

    public void setInqOfDay(String inqOfDay) {
        this.inqOfDay = inqOfDay;
    }
}
