package com.world.chaip.entity.newRsvr;

/**
 * @author: bzh
 * @description: 汛期值列表
 * @create: 2019-06-05 11:14
 **/
public class XunQITime {
	private int bgmd = 0; //开始时间
	private int edmd = 0; //结束时间
	private int fstp = 0; //汛期标志
    private String FSLTDZ;//汛期水位
    private String FSLTDW; //汛期

	public String getFSLTDZ() {
		return FSLTDZ;
	}

	public void setFSLTDZ(String FSLTDZ) {
		this.FSLTDZ = FSLTDZ;
	}

	public String getFSLTDW() {
		return FSLTDW;
	}

	public void setFSLTDW(String FSLTDW) {
		this.FSLTDW = FSLTDW;
	}

	public int getBgmd() {
		return bgmd;
	}

	public void setBgmd(int bgmd) {
		this.bgmd = bgmd;
	}

	public int getEdmd() {
		return edmd;
	}

	public void setEdmd(int edmd) {
		this.edmd = edmd;
	}

	public int getFstp() {
		return fstp;
	}

	public void setFstp(int fstp) {
		this.fstp = fstp;
	}

	@Override
	public String toString() {
		return "XunQITime{" +
				"bgmd=" + bgmd +
				", edmd=" + edmd +
				", fstp=" + fstp +
				'}';
	}
}
