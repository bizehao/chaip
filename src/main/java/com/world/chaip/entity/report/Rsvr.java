package com.world.chaip.entity.report;
/**
 * 水库表
 * @author Administrator
 *
 */

import java.util.Date;

public class Rsvr {
	
	//站号
	private String stcd;
	//库名
	private String  stnm;
	//水系
	private String hnnm;
	//时间
	private String tm;
	//水位
	private double rz;
	//蓄量
	private double w;
	//出库流量
	private double otq;
	//入库流量
	private double inq;
	
	public Rsvr() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rsvr(String stcd, String stnm, String hnnm, String tm, double rz, double w, double otq, double inq) {
		this.stcd = stcd;
		this.stnm = stnm;
		this.hnnm = hnnm;
		this.tm = tm;
		this.rz = rz;
		this.w = w;
		this.otq = otq;
		this.inq = inq;
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

	public String getTm() {
		return tm;
	}

	public void setTm(String tm) {
		this.tm = tm;
	}

	public double getRz() {
		return rz;
	}

	public void setRz(double rz) {
		this.rz = rz;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getOtq() {
		return otq;
	}

	public void setOtq(double otq) {
		this.otq = otq;
	}

	public double getInq() {
		return inq;
	}

	public void setInq(double inq) {
		this.inq = inq;
	}
}
