package com.world.chaip.entity;

import com.world.chaip.entity.baseinfo.Station;

/**
 * 雨情统计描太空信息
 * @author LIUWY
 *
 */
public class RainfallMaxDesc {
	public Station getFirstMax() {
		return firstMax;
	}
	public void setFirstMax(Station firstMax) {
		this.firstMax = firstMax;
	}
	public Station getSecondMax() {
		return secondMax;
	}
	public void setSecondMax(Station secondMax) {
		this.secondMax = secondMax;
	}
	public Station getThirdMax() {
		return thirdMax;
	}
	public void setThirdMax(Station thirdMax) {
		this.thirdMax = thirdMax;
	}
	private Station firstMax;
	private Station secondMax;
	private Station thirdMax;
}
