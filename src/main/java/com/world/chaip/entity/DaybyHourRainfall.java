package com.world.chaip.entity;

import java.text.DecimalFormat;
import java.util.*;

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
			hourRainfalls=new HashMap<Double, String>();
		}
		
		public Map<Double, String> getHourRainfalls() {
			return hourRainfalls;
		}

		public void setHourRainfalls(Map<Double, String> hourRainfalls) {
			this.hourRainfalls = hourRainfalls;
		}

		Map<Double,String> hourRainfalls;
        //日降雨量
		double countDrp;

        public double testValues() {
            double count = 0;
            for (String value : hourRainfalls.values()) {
                count += Double.parseDouble(value);
            }
            String cou = new DecimalFormat("#0.0").format(count);
            count = Double.parseDouble(cou);
            return count;
        }

        public double getCountDrp() {
            return countDrp;
        }

        public void setCountDrp(double countDrp) {
            this.countDrp = countDrp;
        }
        /*public void changeDrp(Double currentHour){
            for(Map.Entry<Double, String > entry : hourRainfalls.entrySet()){
                if(entry.getKey() > currentHour || (currentHour < 9 && entry.getKey() > currentHour)){
                    System.out.println("======");
                    entry.setValue("");
                }
            }
        }*/
    }
}
