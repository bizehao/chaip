package com.world.chaip.mapper;

import java.util.List;

import com.world.chaip.entity.report.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMapper {
	/**
	 * 水库水情统计
	 * @param city
	 * @param tm1
	 * @param tm2
	 * @param type
	 * @param stcd
	 * @return
	 */
	List<Rsvr> getRsvrByTerm(
            @Param("city") String city,
            @Param("tm1") String tm1,
            @Param("tm2") String tm2,
            @Param("type") int type,
            @Param("stcd") String stcd);
	/**
	 * 河道统计
	 * @param city
	 * @param tm1
	 * @param tm2
	 * @param type
	 * @param stcd
	 * @return
	 */
	List<River> getRiverByTerm(
            @Param("city") String city,
            @Param("tm1") String tm1,
            @Param("tm2") String tm2,
            @Param("type") int type,
            @Param("stcd") String stcd);

	/**
	 * 降雨量统计(按逐时)
	 * @param city
	 * @param tm1
	 * @param tm2
	 * @param type
	 * @param stcd
	 * @return
	 */
	List<Pptn> getPptnByTerm(@Param("city") String city,
							 @Param("tm1") String tm1,
							 @Param("tm2") String tm2,
							 @Param("type") int type,
							 @Param("stcd") String stcd);
    /**
     * 降雨量统计(按日)
     * @param city
     * @param tm1
     * @param tm2
     * @param type
     * @param stcd
     * @return
     */
    List<Pptn> getPptnByDate(@Param("city") String city,
                             @Param("tm1") String tm1,
                             @Param("tm2") String tm2,
                             @Param("type") int type,
                             @Param("stcd") String stcd,
                             @Param("stnm") String stnm);
    /**
     * 降雨量统计(按旬)
     * @param city
     * @param tm1
     * @param tm2
     * @param type
     * @param stcd
     * @return
     */
    List<Pptn> getPptnByTen(@Param("city") String city,
                             @Param("tm1") String tm1,
                             @Param("tm2") String tm2,
                             @Param("type") int type,
                             @Param("stcd") String stcd,
                             @Param("stnm") String stnm);
    /**
     * 降雨量统计(按月)
     * @param city
     * @param tm1
     * @param tm2
     * @param type
     * @param stcd
     * @return
     */
    List<Pptn> getPptnByMonth(@Param("city") String city,
                            @Param("tm1") String tm1,
                            @Param("tm2") String tm2,
                            @Param("type") int type,
                            @Param("stcd") String stcd,
                            @Param("stnm") String stnm);
    /**
     * 降雨量统计(按年)
     * @param city
     * @param tm1
     * @param tm2
     * @param type
     * @param stcd
     * @return
     */
    List<Pptn> getPptnByYear(@Param("city") String city,
                            @Param("tm1") String tm1,
                            @Param("tm2") String tm2,
                            @Param("type") int type,
                            @Param("stcd") String stcd,
                            @Param("stnm") String stnm);
    /**
     * 降雨量统计(按时段雨量)
     * @param city
     * @param tm1
     * @param tm2
     * @param type
     * @param stcd
     * @return
     */
    List<Pptn> getPptnByHour(@Param("city") String city,
                             @Param("tm1") String tm1,
                             @Param("tm2") String tm2,
                             @Param("type") int type,
                             @Param("stcd") String stcd,
                             @Param("stnm") String stnm);
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
	List<Stations> getStations(@Param("adcd") String adcd);

	/**
	 * 获取站类信息
	 * @return
	 */
	List<Tyid> getTyid();

}
