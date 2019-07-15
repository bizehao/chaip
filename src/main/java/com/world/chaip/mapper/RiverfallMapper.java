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
     * 河道查询(实时 时间范围里的数据)
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
            @Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("benqu") String benqu,
            @Param("ly") List<String> ly);
    /**
     * 河道查询(实时 最新一条数据)
     * @param time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<River> getRiverByTermNew(
            @Param("time") Date time,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("benqu") String benqu,
            @Param("ly") List<String> ly);
	/**
	 * 河道查询(本区 时间不同)
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
			@Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("ly") List<String> ly);
    /**
     * 河道查询(本区 时间相同)
     * @param time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<River> getRiverByBenNew(
            @Param("time") Date time,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("ly") List<String> ly);
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
			@Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("ly") List<String> ly);

    /**
     * 河道查询(外区)
     * @param time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<River> getRiverByWaiNew(
            @Param("time") Date time,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("ly") List<String> ly);

}
