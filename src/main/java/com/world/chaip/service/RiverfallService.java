package com.world.chaip.service;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.entity.report.River;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 实时和专业
 * @author BZH
 *
 */

public interface RiverfallService {
    /**
     * 河道统计 实时
     * @param dateS
     * @param dateE
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
	List<River> getRiverByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm,String benqu, List<String> ly) throws ParseException;
	/**
	 * 河道统计 本区
     * @param dateS
     * @param dateE
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
	List<River> getRiverByBen(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, List<String> ly) throws ParseException;
    /**
     * 河道统计 外区
     * @param dateS
     * @param dateE
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<River> getRiverByWai(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, List<String> ly) throws ParseException;

}
