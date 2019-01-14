package com.world.chaip.entity.excelFormat;

import java.util.*;

//日雨量
public class DayRainExcel {

    List<DayRain> dayRainList;

    public DayRainExcel() {
        dayRainList = new ArrayList<DayRain>();
    }

    public List<DayRain> getDayRainList() {
        return dayRainList;
    }

    public void setDayRainList(List<DayRain> dayRainList) {
        this.dayRainList = dayRainList;
    }

    public class DayRain{
        //县名
        private String adnm;

        private List<Object[]> dayRainList;

        public DayRain() {
            dayRainList = new ArrayList<>();
        }

        public DayRain(String adnm, List<Object[]> dayRainList) {

            this.adnm = adnm;
            this.dayRainList = dayRainList;
        }

        public String getAdnm() {
            return adnm;
        }

        public void setAdnm(String adnm) {
            this.adnm = adnm;
        }

        public List<Object[]> getDayRainArray() {
            return dayRainList;
        }

        public void setDayRainList(List<Object[]> dayRainList) {
            this.dayRainList = dayRainList;
        }
    }
}
