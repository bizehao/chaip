package com.world.chaip.entity.excelFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        private Map<String, Double> dayRainList;

        public DayRain() {
            dayRainList = new HashMap<String, Double>();
        }

        public DayRain(String adnm, Map<String, Double> dayRainList) {

            this.adnm = adnm;
            this.dayRainList = dayRainList;
        }

        public String getAdnm() {
            return adnm;
        }

        public void setAdnm(String adnm) {
            this.adnm = adnm;
        }

        public Map<String, Double> getDayRainList() {
            return dayRainList;
        }

        public void setDayRainList(Map<String, Double> dayRainList) {
            this.dayRainList = dayRainList;
        }
    }
}
