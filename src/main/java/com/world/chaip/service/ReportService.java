package com.world.chaip.service;

import java.util.List;

import com.world.chaip.entity.report.*;
import com.world.chaip.entity.report.gson.PptnGson;
import org.springframework.stereotype.Service;


public interface ReportService {
	/**
	 * 水库水情统计
	 * @param city
	 * @param tm1
	 * @param tm2
	 * @param type
	 * @param stcd
	 * @return
	 */
	List<Rsvr> getRsvrByTerm(String city, String tm1, String tm2, int type, String stcd);
	/**
	 * 河道统计
	 * @param city
	 * @param tm1
	 * @param tm2
	 * @param type
	 * @param stcd
	 * @return
	 */
	List<River> getRiverByTerm(String city, String tm1, String tm2, int type, String stcd);

	/**
	 * 降雨量统计(按时段)
	 * @param city
	 * @param tm
	 * @param type
	 * @param stcd
	 * @return
	 */
	/*List<PptnGson> getPptnByTerm(String city, String tm, int type, String stcd);*/
    /**
     * 降雨量统计(按日)
     * @param city
     * @param tm
     * @param type
     * @param stcd
     * @return
     */
    List<Pptn> getPptnByDate(String city, String tm, int type, String stcd);
    /**
     * 降雨量统计(按日获取条件的)
     * @param city
     * @param tm
     * @param type
     * @param stcd
     * @return
     */
    List<Pptn> getPptnByDateGetTerm(String city, String tm, int type, String stcd);
	/**
	 * 加载市县
	 * @return
	 */
	List<City> getCity();

	/**
	 * 根据市县获取相应的站点信息
	 * @param adcd
	 * @return
	 */
	List<Stations> getStations(String adcd);

	/**
	 * 获取站类信息
	 * @return
	 */
	List<Tyid> getTyid();
}
