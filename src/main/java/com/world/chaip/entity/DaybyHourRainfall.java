package com.world.chaip.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaybyHourRainfall {
	
	public DaybyHourRainfall() {
		data=new ArrayList<DayByHourRainfallItem>();
	}
	
	public List<DayByHourRainfallItem> getData() {
        return data;
	}
	public void setData(List<DayByHourRainfallItem> data) {
		this.data = data;
	}
	
	List<DayByHourRainfallItem> data;
	
	public class DayByHourRainfallItem extends Rainfall{
	
		public DayByHourRainfallItem() {
			hourRainfalls=new HashMap<Double, Double>();
		}
		
		public Map<Double, Double> getHourRainfalls() {
			return hourRainfalls;
		}

		public void setHourRainfalls(Map<Double, Double> hourRainfalls) {
			this.hourRainfalls = hourRainfalls;
		}

		Map<Double,Double> hourRainfalls;
        //日降雨量
		double countDrp;

        public double testValues() {
            double count = 0;
            for (Double value : hourRainfalls.values()) {
                /*System.out.println(value);*/
                count += value;
            }
            return count;
        }

        public double getCountDrp() {
            return countDrp;
        }

        public void setCountDrp(double countDrp) {
            this.countDrp = countDrp;
        }
    }
}
