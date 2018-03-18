package com.world.chaip.service.impl;

import java.sql.Array;
import java.text.DecimalFormat;
import java.util.*;

import com.world.chaip.entity.excelFormat.DayRainExcel;
import com.world.chaip.entity.excelFormat.DayRainExcel.DayRain;
import com.world.chaip.entity.excelFormat.DayRainExcelX.DayRainX;
import com.world.chaip.entity.excelFormat.DayRainExcelX;
import com.world.chaip.entity.report.Pptn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.DaybyHourRainfall.DayByHourRainfallItem;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.mapper.RainfallMapper;
import com.world.chaip.service.RainfallService;
import com.world.chaip.util.DateUtils;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
@Service
public class RainfallServiceImpl implements RainfallService {

	@Autowired
	private RainfallMapper rainfallMapper;

	//时段降雨量
	@Override
	public DaybyHourRainfall getDaybyHour(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
		Date beginTime=null;
		Date endTime=null;
		DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		beginTime=now.getTime();
		endTime=DateUtils.getDateAfter(beginTime, 1);
		System.out.println(beginTime+","+endTime);
		List<Rainfall> rainfalls=rainfallMapper.selectByTm(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
		System.out.println("数量"+rainfalls.size());
		if(rainfalls!=null && rainfalls.size()>0) {
			for(int i=0;i<rainfalls.size();i++) {
				DayByHourRainfallItem tempItem =null;
				/*System.out.println(i+"这个"+daybyHourRainfall.getData().size());*/
				for(int j=0;j<daybyHourRainfall.getData().size();j++) {
					DayByHourRainfallItem tempItemX = daybyHourRainfall.getData().get(j);
					if(tempItemX.getStcd().equals(rainfalls.get(i).getStcd())) {
						tempItem=tempItemX;
						tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), rainfalls.get(i).getDrp());
						/*System.out.println((double) rainfalls.get(i).getTm().getHours()+","+rainfalls.get(i).getDrp());*/
                        /*System.out.println(rainfalls.get(i).getDrp());
                        dyp+=rainfalls.get(i).getDrp();*/
						if(i == rainfalls.size()-1){
							tempItem=tempItemX;
							double count = tempItem.testValues();
							tempItem.setCountDrp(count);
							/*System.out.println("sads"+count);*/
						}
						break;
					}else{
						tempItem=tempItemX;
						double count = tempItem.testValues();
						tempItem.setCountDrp(count);
						tempItem = null;
						/*System.out.println("sads"+count);*/
					}
				}
				/*System.out.println(dyp);*/
				if(tempItem==null) {
					tempItem=daybyHourRainfall.new DayByHourRainfallItem();
					tempItem.setStcd(rainfalls.get(i).getStcd());
					tempItem.setStnm(rainfalls.get(i).getStnm());
					tempItem.setLgtd(rainfalls.get(i).getLgtd());
					tempItem.setLttd(rainfalls.get(i).getLttd());
					tempItem.setName(rainfalls.get(i).getName());
					tempItem.setAdnm(rainfalls.get(i).getAdnm());
                    tempItem.getHourRainfalls().put((double) rainfalls.get(i).getTm().getHours(), rainfalls.get(i).getDrp());
					daybyHourRainfall.getData().add(tempItem);
					/*tempItem.setDyp(dyp);*/
				}
			}
		}
		return daybyHourRainfall;
	}

	//日降雨量
	@Override
	public Object getDaybyDate(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
        Date beginTime=null;
		Date endTime=null;
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		beginTime=now.getTime();
		endTime=DateUtils.getDateAfter(beginTime, 1);
		List<Rainfall> list = rainfallMapper.selectByDate(endTime,adcd,systemTypes,stcdOrStnm,pptn);
		if(cid == 0){
			return list;
		}
		/*for(int i=0; i<list.size(); i++){
			System.out.println(list.get(i).getStcd()+""+list.get(i).getStnm());
		}*/
		System.out.println("日降水量的记录数"+list.size());
		Object[] objects = null;
		DayRainExcel dayRainExcel = new DayRainExcel();
		if(list != null && list.size()>0){
			for(int i=0; i<list.size(); i++){
				DayRain dayRain = null;
				for(int j=0; j<dayRainExcel.getDayRainList().size(); j++){
					DayRain dayRainX = dayRainExcel.getDayRainList().get(j);
					if(dayRainX.getAdnm().equals(list.get(i).getAdnm())){
						dayRain=dayRainX;
						objects = new Object[2];
						objects[0] = list.get(i).getStnm();
						objects[1] = list.get(i).getDyp();
						dayRain.getDayRainList().add(objects);
						/*System.out.println(list.get(i).getStnm());*/
						break;
					}
				}
				if(dayRain == null){
					dayRain = dayRainExcel.new DayRain();
					dayRain.setAdnm(list.get(i).getAdnm());
					objects = new Object[2];
					objects[0] = list.get(i).getStnm();
					objects[1] = list.get(i).getDyp();
					dayRain.getDayRainList().add(objects);
					/*System.out.println(list.get(i).getStnm());*/
					dayRainExcel.getDayRainList().add(dayRain);
				}
			}
		}
		/*System.out.println("=============================");*/
		/*for(int i=0; i<dayRainExcel.getDayRainList().size(); i++){
			System.out.println(dayRainExcel.getDayRainList().get(i).getAdnm());
			Map<String, Double> map =  dayRainExcel.getDayRainList().get(i).getDayRainList();
			System.out.println(map.size());
			for (Map.Entry<String, Double> entry : map.entrySet()) {
				System.out.println("Key = 1" + entry.getKey() + ", Value = 1" + entry.getValue());
			}
		}*/
		return dayRainExcel;
	}

	//旬降雨量
	@Override
	public Object getDaybyXun(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
		Date Time=null;
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		Time=now.getTime();
		Date beginTime = null;
		now.add(Calendar.DATE, -9);
		beginTime = now.getTime();
		List<Rainfall> list =  rainfallMapper.selectByXun(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
		if(cid == 0){
			return list;
		}
		return getXYN(list);
	}

	//月降雨量
	@Override
	public Object getDaybyMonth(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
		Date Time=null;
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		Time=now.getTime();
		Date beginTime = null;
		now.add(Calendar.MONTH, -1);
		now.add(Calendar.DATE, 1);
		beginTime = now.getTime();
		List<Rainfall> list =  rainfallMapper.selectByMonth(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
		if(cid == 0){
			return list;
		}
		return getXYN(list);
	}

	//年降雨量
	@Override
	public Object getDaybyYear(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
		Date Time=null;
		DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		Time=now.getTime();
		Date beginTime = null;
		now.add(Calendar.YEAR, -1);
		now.add(Calendar.DATE, 1);
		beginTime = now.getTime();
		List<Rainfall> list = rainfallMapper.selectByYear(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
		if(cid == 0){
			return list;
		}
		return getXYN(list);
	}

	//时段降雨量
	@Override
	public Object getDaybyTime(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn) {
		Calendar now =  Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH);
		int day = now.get(Calendar.DATE);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		now.set(year,month,day,hour,0,0);
		now.set(Calendar.MILLISECOND,0);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(hour);
        Date NowTime = now.getTime();
        System.out.println(NowTime);
        //开始时间
		now.setTime(dateS);
		now.set(Calendar.MINUTE,0);
		dateS = now.getTime();
		//结束时间
		now.setTime(dateE);
		now.set(Calendar.MINUTE,0);
		dateE = now.getTime();
		List<Rainfall> list = rainfallMapper.selectByTime(dateS, dateE, NowTime, adcd, systemTypes, stcdOrStnm,pptn);
		if(cid == 0){
			return list;
		}
		DayRainExcelX dayRainExcelX = new DayRainExcelX();
		Object[] obRainX = null;
		if(list != null && list.size()>0){
			for(int i=0; i<list.size(); i++){
				DayRainX dayRainM = null;
				for(int j=0; j<dayRainExcelX.getDayRainXList().size(); j++){
					DayRainX dayRainX = dayRainExcelX.getDayRainXList().get(j);
					if(dayRainX.getAdnm().equals(list.get(i).getAdnm())){
						dayRainM=dayRainX;
						obRainX = new Object[3];
						obRainX[0] = list.get(i).getStnm();
						obRainX[1] = list.get(i).getNum();
						obRainX[2] = list.get(i).getDrp();
						dayRainM.getRainList().add(obRainX);
						break;
					}
				}
				if(dayRainM == null){
					dayRainM = dayRainExcelX.new DayRainX();
					dayRainM.setAdnm(list.get(i).getAdnm());
					obRainX = new Object[3];
					obRainX[0] = list.get(i).getStnm();
					obRainX[1] = list.get(i).getAccp();
					obRainX[2] = list.get(i).getNum();
					dayRainM.getRainList().add(obRainX);
					dayRainExcelX.getDayRainXList().add(dayRainM);
				}
			}
		}
		return dayRainExcelX;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//时段降雨量计算
	@Override
	public String getDaybyHourJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
		Date beginTime=null;
		Date endTime=null;
		DaybyHourRainfall daybyHourRainfall=new DaybyHourRainfall();
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		beginTime=now.getTime();
		endTime=DateUtils.getDateAfter(beginTime, 1);
		System.out.println(beginTime+","+endTime);
		List<Rainfall> rainfalls=rainfallMapper.selectByTm(beginTime,endTime,adcd,systemTypes,stcdOrStnm);
		String xianshi = coonPC(rainfalls);
		return xianshi;
	}

	//日降雨量计算
	@Override
	public String getDaybyDateJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
		Date beginTime=null;
		Date endTime=null;
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		beginTime=now.getTime();
		endTime=DateUtils.getDateAfter(beginTime, 1);
		List<Rainfall> list = rainfallMapper.selectByDate(endTime,adcd,systemTypes,stcdOrStnm,pptn);
		String xianshi = coonPC(list);
		return xianshi;
	}

	//旬降雨量计算
	@Override
	public String getDaybyXunJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
		Date Time=null;
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		Time=now.getTime();
		Date beginTime = null;
		now.add(Calendar.DATE, -9);
		beginTime = now.getTime();
		List<Rainfall> list =  rainfallMapper.selectByXun(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
		String xianshi = coonPC(list);
		return xianshi;
	}

	//月降雨量计算
	@Override
	public String getDaybyMonthJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
		Date Time=null;
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		Time=now.getTime();
		Date beginTime = null;
		now.add(Calendar.MONTH, -1);
		now.add(Calendar.DATE, 1);
		beginTime = now.getTime();
		List<Rainfall> list =  rainfallMapper.selectByMonth(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
		String xianshi = coonPC(list);
		return xianshi;
	}

	//年降雨量计算
	@Override
	public String getDaybyYearJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
		Date Time=null;
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR_OF_DAY, 8);
		Time=now.getTime();
		Date beginTime = null;
		now.add(Calendar.YEAR, -1);
		now.add(Calendar.DATE, 1);
		beginTime = now.getTime();
		List<Rainfall> list = rainfallMapper.selectByYear(Time,beginTime,adcd,systemTypes,stcdOrStnm,pptn);
		String xianshi = coonPC(list);
		return xianshi;
	}

    //时段降雨量计算
    @Override
    public String getDaybyTimeJS(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn) {
        Calendar now =  Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH + 1);
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        now.set(year,month,day,hour,0,0);
        Date NowTime = now.getTime();
        //开始时间
        now.setTime(dateS);
        now.set(Calendar.MINUTE,0);
        dateS = now.getTime();
        //结束时间
        now.setTime(dateE);
        now.set(Calendar.MINUTE,0);
        dateE = now.getTime();
        List<Rainfall> list = rainfallMapper.selectByTime(dateS, dateE, NowTime, adcd, systemTypes, stcdOrStnm,pptn);
        String xianshi = coonPC(list);
        return xianshi;
    }

    //list集合排序
	public String coonPC(List<Rainfall> list) {
		int hundred = 0;
		int Fifty = 0;
		int Thirty = 0;
		double dyp = 0;
		for(int i = 0; i<list.size(); i++){
			if(list.get(i).getDyp() != null){
				dyp = list.get(i).getDyp();
			}
			if(dyp >= 100){
				hundred++;
			}
			if(dyp >= 50){
				Fifty++;
			}
			if(dyp >= 30){
                Thirty++;
			}
		}
		Collections.sort(list, new Comparator<Rainfall>() {
			@Override
			public int compare(Rainfall o1, Rainfall o2) {
				double a = 0;
				double b = 0;
				if(o1.getDyp() != null){
					a = o1.getDyp();
				}
				if(o2.getDyp() != null){
					b = o2.getDyp();
				}
				return new Double(b).compareTo(new Double(a));
			}
		});
        String one = "";
        String two = "";
        String three = "";
		System.out.println("最大"+list.get(0).getDyp());
		if(list.size()==1){
            one ="最大的是"+list.get(0).getAdnm()+"的"+list.get(0).getStnm()+"站，降雨量是"+(list.get(0).getDyp()==null?0:list.get(0).getDyp())+"毫米";
        }else if(list.size()==2){
            one ="最大的是"+list.get(0).getAdnm()+"的"+list.get(0).getStnm()+"站，降雨量是"+(list.get(0).getDyp()==null?0:list.get(0).getDyp())+"毫米";
            two = "次大点的是"+list.get(1).getAdnm()+"的"+list.get(1).getStnm()+"站，降雨量是"+(list.get(1).getDyp()==null?0:list.get(1).getDyp())+"毫米";
        }else{
            one ="最大的是"+list.get(0).getAdnm()+"的"+list.get(0).getStnm()+"站，降雨量是"+(list.get(0).getDyp()==null?0:list.get(0).getDyp())+"毫米";
            two = "次大点的是"+list.get(1).getAdnm()+"的"+list.get(1).getStnm()+"站，降雨量是"+(list.get(1).getDyp()==null?0:list.get(1).getDyp())+"毫米";
            three = "再次大点的是"+list.get(2).getAdnm()+"的"+list.get(2).getStnm()+"站，降雨量是"+(list.get(2).getDyp()==null?0:list.get(2).getDyp())+"毫米";
        }
		System.out.println("超过100的:"+hundred);
		System.out.println("超过50的:"+Fifty);
		System.out.println("超过30的:"+Thirty);
		System.out.println("前三个:第一个"+one+", 第二个"+two+","+", 第三个"+three);
		String xianshi ="超过100毫米的有："+
				hundred+"站"+
				"超过50毫米的有："+
				Fifty+"站"+
				"超过30毫米的有："+
				Thirty+"站"+
				"最大的是"+
				one+"站"+
				"次大点的是"+
				two+"站"+
				 "再次大点的是"+
				 three+"站";
		return xianshi;
	}
	//处理旬月年
	public DayRainExcelX getXYN(List<Rainfall>  list) {
		DayRainExcelX dayRainExcelX = new DayRainExcelX();
		Object[] obRainX = null;
		if(list != null && list.size()>0){
			for(int i=0; i<list.size(); i++){
				DayRainX dayRainM = null;
				for(int j=0; j<dayRainExcelX.getDayRainXList().size(); j++){
					DayRainX dayRainX = dayRainExcelX.getDayRainXList().get(j);
					if(dayRainX.getAdnm().equals(list.get(i).getAdnm())){
						dayRainM=dayRainX;
						obRainX = new Object[3];
						obRainX[0] = list.get(i).getStnm();
						obRainX[1] = list.get(i).getAccp();
						obRainX[2] = new DecimalFormat("#").format(list.get(i).getNum());
						dayRainM.getRainList().add(obRainX);
						break;
					}
				}
				if(dayRainM == null){
					dayRainM = dayRainExcelX.new DayRainX();
					dayRainM.setAdnm(list.get(i).getAdnm());
					obRainX = new Object[3];
					obRainX[0] = list.get(i).getStnm();
					obRainX[1] = list.get(i).getAccp();
					obRainX[2] = new DecimalFormat("#").format(list.get(i).getNum());
					dayRainM.getRainList().add(obRainX);
					dayRainExcelX.getDayRainXList().add(dayRainM);
				}
			}
		}
		return dayRainExcelX;
	}

}
