package com.world.chaip.entity.newRsvr;

import java.util.List;

/**
 * @author: bzh
 * @description: rsvr站的汛期值
 * @create: 2019-06-05 10:49
 **/
public class RevrXunQi {
	private String stcd; //站号
	private String stnm; //站名
	private int chooseFstp; //确定的汛期值
	private List<XunQITime> xunQITimeList; //汛期值列表

	public String getStcd() {
		return stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	public List<XunQITime> getXunQITimeList() {
		return xunQITimeList;
	}

	public void setXunQITimeList(List<XunQITime> xunQITimeList) {
		this.xunQITimeList = xunQITimeList;
	}

	public int getChooseFstp() {
		return chooseFstp;
	}

	public void setChooseFstp(int chooseFstp) {
		this.chooseFstp = chooseFstp;
	}

	public String getStnm() {
		return stnm;
	}

	public void setStnm(String stnm) {
		this.stnm = stnm;
	}
}
