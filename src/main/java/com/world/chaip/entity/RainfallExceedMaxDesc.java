package com.world.chaip.entity;

public class RainfallExceedMaxDesc extends RainfallMaxDesc {

	public Integer getExceed100() {
		return exceed100;
	}
	public void setExceed100(Integer exceed100) {
		this.exceed100 = exceed100;
	}
	public Integer getExceed50() {
		return exceed50;
	}
	public void setExceed50(Integer exceed50) {
		this.exceed50 = exceed50;
	}
	public Integer getExceed30() {
		return exceed30;
	}
	public void setExceed30(Integer exceed30) {
		this.exceed30 = exceed30;
	}
	private Integer exceed100;
	private Integer exceed50;
	private Integer exceed30;
	
}
