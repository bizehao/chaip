package com.world.chaip.service;

import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.report.River;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author LIUWY
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
	List<River> getRiverByTerm(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);
	/**
	 * 河道统计 本区
     * @param dateS
     * @param dateE
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
	List<River> getRiverByBen(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);
    /**
     * 河道统计 外区
     * @param dateS
     * @param dateE
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<River> getRiverByWai(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);

}
