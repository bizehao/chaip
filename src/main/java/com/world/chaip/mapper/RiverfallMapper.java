package com.world.chaip.mapper;

import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.entity.Rainfall;
import com.world.chaip.entity.report.River;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RiverfallMapper {
	/**
     * 河道查询(实时)
	 * @param beginTime
     * @param endTime
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
	List<River> getRiverByTerm(
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") List<String> stcdOrStnm);
	/**
	 * 河道查询(本区)
	 * @param beginTime
	 * @param endTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<River> getRiverByBen(
			@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm);
    /**
	 * 河道查询(外区)
	 * @param beginTime
	 * @param endTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<River> getRiverByWai(
			@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm);

	/**
	 * 河道水情分析
	 * @param beginTime
	 * @param endTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<RiverExchange> getRiverByAnalysis(
			@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm);

	/**
	 * 获取最大水位或者流量的日期
	 * @return
	 */
	String getRiverMaxTime(@Param("beginTime") Date beginTime,
						   @Param("endTime") Date endTime,
						   @Param("stcd") String stcd,
						   @Param("sign") int sign,
						   @Param("max") double max);
}
