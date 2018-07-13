package com.world.chaip.service;

import com.world.chaip.entity.excelFormat.DayRsvr;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.RsvrXunQi;
import com.world.chaip.entity.report.RsvrZhuanYe;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 实时和专业
 * @author BZH
 *
 */

public interface RsvrfallService {
	/**
	 * 水库(实时)
	 * @param dateS 日期
     * @param dateE 日期
	 * @param adcd 测站编码
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rsvr> getRsvrByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) throws ParseException;

	/**
	 * 水库（专业报表）
	 * @param dateS
	 * @param dateE
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	DayRsvr getRsvrByZhuanYe(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) throws ParseException;

}
