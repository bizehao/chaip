package com.world.chaip.entity;

/**
 * @author 毕泽浩
 * @Description: 流速查询表
 * @time 2019/1/25 9:56
 */
public class FlowSpeed {
	private int serial; //序号
	private String adnm; //县域名称
	private String stnm; //站名
	private String stcd; //站号
	private String tm; //时间
	private double ls1; //流速1-5
	private double ls2;
	private double ls3;
	private double ls4;
	private double ls5;
	private double z; //水位

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
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

	public double getLs1() {
		return ls1;
	}

	public void setLs1(double ls1) {
		this.ls1 = ls1;
	}

	public double getLs2() {
		return ls2;
	}

	public void setLs2(double ls2) {
		this.ls2 = ls2;
	}

	public double getLs3() {
		return ls3;
	}

	public void setLs3(double ls3) {
		this.ls3 = ls3;
	}

	public double getLs4() {
		return ls4;
	}

	public void setLs4(double ls4) {
		this.ls4 = ls4;
	}

	public double getLs5() {
		return ls5;
	}

	public void setLs5(double ls5) {
		this.ls5 = ls5;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
}
