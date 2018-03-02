package com.world.chaip.service;

import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.RsvrXunQi;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author LIUWY
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
	List<Rsvr> getRsvrByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);

	/**
	 * 水库（专业报表）
	 * @param dateS
	 * @param dateE
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rsvr> getRsvrByZhuanYe(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);

}
