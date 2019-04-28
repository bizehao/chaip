package com.world.chaip.entity.excelFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//日雨量
public class DayRainExcelX {

    List<DayRainX> dayRainXList;

    public DayRainExcelX() {
        dayRainXList = new ArrayList<DayRainX>();
    }

    public List<DayRainX> getDayRainXList() {
        return dayRainXList;
    }

    public void setDayRainXList(List<DayRainX> dayRainXList) {
        this.dayRainXList = dayRainXList;
    }

    public class DayRainX{
        //县名
        private String adnm;

        private List<Object[]> rainList;

        public DayRainX() {
            rainList = new ArrayList<>();
        }

        public String getAdnm() {
            return adnm;
        }

        public void setAdnm(String adnm) {
            this.adnm = adnm;
        }

        public List<Object[]> getRainList() {
            return rainList;
        }

        public void setRainList(List<Object[]> rainList) {
            this.rainList = rainList;
        }
    }
}
