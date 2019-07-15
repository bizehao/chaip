package com.world.chaip.entity.baseinfo;

/**
 * 测站信息
 * @author LIUWY
 *
 */
public abstract class Station {


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
	public AdInfo getAdInfo() {
		return adInfo;
	}
	public void setAdInfo(AdInfo adInfo) {
		this.adInfo = adInfo;
	}
	public SystemType getSystemType() {
		return systemType;
	}
	public void setSystemType(SystemType systemType) {
		this.systemType = systemType;
	}
	
	public String getSttp() {
		return sttp;
	}
	public void setSttp(String sttp) {
		this.sttp = sttp;
	}
	public Double getLgtd() {
		return lgtd;
	}
	public void setLgtd(Double lgtd) {
		this.lgtd = lgtd;
	}
	public Double getLttd() {
		return lttd;
	}
	public void setLttd(Double lttd) {
		this.lttd = lttd;
	}
	private Double lgtd;
	private Double lttd;
	private String sttp;
	private String stcd;
	private String stnm;
	private AdInfo adInfo;
	private SystemType systemType;
    private String name;
    private Double dyp;
    private String adnm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public Double getDyp() {
        return dyp;
    }

    public void setDyp(Double dyp) {
        this.dyp = dyp;
    }
}
