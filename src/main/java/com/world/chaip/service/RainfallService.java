package com.world.chaip.service;

import java.util.Date;
import java.util.List;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.excelFormat.DayRainExcel;
import com.world.chaip.entity.excelFormat.DayRainExcelX;
import org.springframework.stereotype.Service;

/**
 * 实时和专业
 * @author BZH
 *
 */
@Service
public interface RainfallService {
	/**
	 * 逐时雨量统计
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	DaybyHourRainfall getDaybyHour(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);
	/**
	 * 逐日雨量统计
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	Object getDaybyDate(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn);
	/**
	 * 逐旬雨量统计
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	Object getDaybyXun(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn);
	/**
	 * 逐月雨量统计
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	Object getDaybyMonth(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn);
	/**
	 * 逐年雨量统计
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	Object getDaybyYear(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn);

	/**
	 * 时段雨量统计
	 * @param dateS
	 * @param dateE
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	Object getDaybyTime(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn);
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 逐日雨量计算
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Object> getDaybyDateJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn);
	/**
	 * 逐旬雨量计算
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Object> getDaybyXunJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn);
	/**
	 * 逐月雨量计算
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Object> getDaybyMonthJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn);
	/**
	 * 逐年雨量计算
	 * @param date 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Object> getDaybyYearJS(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, String pptn);
	/**
	 * 时段雨量计算
	 * @param dateS
	 * @param dateE
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	Object getDaybyTimeJS(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int cid, String pptn);
}
