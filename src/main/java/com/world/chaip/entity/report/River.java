package com.world.chaip.entity.report;

import java.util.Date;

/**
 * 河道表
 * @author Administrator
 *
 */
public class River {
	//测站编号
	private String stcd;
	//县名
	private String adnm;
	//测站名称
	private String stnm;
	//河名
	private String rvnm;
	//时间
	private String tm;
	//水位
	private String z;
	//水势
	private String wptn;
	//流量
	private String q;
		
	public River() {
		super();
		// TODO Auto-generated constructor stub
	}

    public River(String stcd, String adnm, String stnm, String rvnm, String tm, String z, String wptn, String q) {
        this.stcd = stcd;
        this.adnm = adnm;
        this.stnm = stnm;
        this.rvnm = rvnm;
        this.tm = tm;
        this.z = z;
        this.wptn = wptn;
        this.q = q;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public String getStnm() {
        return stnm;
    }

    public void setStnm(String stnm) {
        this.stnm = stnm;
    }

    public String getRvnm() {
        return rvnm;
    }

    public void setRvnm(String rvnm) {
        this.rvnm = rvnm;
    }

    public String getTm() {
        return tm;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getWptn() {
        return wptn;
    }

    public void setWptn(String wptn) {
        this.wptn = wptn;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
