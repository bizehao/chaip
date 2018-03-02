package com.world.chaip.entity;

/**
 * 过程雨量统计（时间雨量统计）
 * @author LIUWY
 *
 */
public class ProcessRaifall extends Rainfall {
	public Double getAccp() {
		return accp;
	}

	public void setAccp(Double accp) {
		this.accp = accp;
	}

	private Double accp;
	
}
