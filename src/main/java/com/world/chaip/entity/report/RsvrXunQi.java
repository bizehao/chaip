package com.world.chaip.entity.report;

public class RsvrXunQi {

    //起 汛期时间
    private String BGMD;

    //止 汛期时间
    private String EDMD;

    public RsvrXunQi() {
    }

    public RsvrXunQi(String BGMD, String EDMD) {
        this.BGMD = BGMD;
        this.EDMD = EDMD;
    }

    public String getBGMD() {
        return BGMD;
    }

    public void setBGMD(String BGMD) {
        this.BGMD = BGMD;
    }

    public String getEDMD() {
        return EDMD;
    }

    public void setEDMD(String EDMD) {
        this.EDMD = EDMD;
    }
}
