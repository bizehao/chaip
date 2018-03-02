package com.world.chaip.entity.report;

import java.util.Date;

/**
 * 降雨量信息表
 * @author Administrator
 *
 */
public class Pptn {
	
	//测站编号
	private String stcd;
	//县名
	private String adnm;
	//测站名称
	private String stnm;
	//站类
	private String name;
	/*//时间
	private Date tm;*/
	//时段降雨量
	private double drp;
	//日降雨量
    private double dyp;
		
	public Pptn() {
		super();
		// TODO Auto-generated constructor stub
	}

    public Pptn(String stcd, String adnm, String stnm, String name, /*Date tm,*/ double drp, double dyp) {
        this.stcd = stcd;
        this.adnm = adnm;
        this.stnm = stnm;
        this.name = name;
        /*this.tm = tm;*/
        this.drp = drp;
        this.dyp = dyp;
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

	/*public Date getTm() {
		return tm;
	}
	public void setTm(Date tm) {
		this.tm = tm;
	}*/
	public double getDrp() {
		return drp;
	}
	public void setDrp(double drp) {
		this.drp = drp;
	}

	public String getAdnm() {
		return adnm;
	}

	public void setAdnm(String adnm) {
		this.adnm = adnm;
	}

    public double getDyp() {
        return dyp;
    }

    public void setDyp(double dyp) {
        this.dyp = dyp;
    }
}
