package com.world.chaip.entity;

import java.util.Date;

import com.world.chaip.entity.baseinfo.Station;

public class Rainfall extends Station {
	public Date getTm() {
		return tm;
	}
	public void setTm(Date tm) {
		this.tm = tm;
	}
	public Double getDrp() {
		return drp;
	}
	public void setDrp(Double drp) {
		this.drp = drp;
	}
	Date tm;
	Double drp;
	Double accp;
	Double num;
    public Double getAccp() {
        return accp;
    }

    public void setAccp(Double accp) {
        this.accp = accp;
    }

	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
}
