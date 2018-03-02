package com.world.chaip.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.world.chaip.entity.Rainfall;

@Repository
public interface RainfallMapper {
	/**
	 * 逐时降雨量查询
	 * @param beginTime
	 * @param endTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rainfall> selectByTm(
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") List<String> stcdOrStnm);
	/**
	 * 逐日降雨量查询
	 * @param Time
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rainfall> selectByDate(
			@Param("Time") Date Time,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm,
			@Param("pptn") String pptn);
	/**
	 * 逐旬降雨量查询
	 * @param Time
	 * @param beginTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rainfall> selectByXun(
			@Param("Time") Date Time,
			@Param("beginTime") Date beginTime,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm,
			@Param("pptn") String pptn);
	/**
	 * 逐月降雨量查询
	 * @param Time
	 * @param beginTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rainfall> selectByMonth(
			@Param("Time") Date Time,
			@Param("beginTime") Date beginTime,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm,
			@Param("pptn") String pptn);
	/**
	 * 逐年降雨量查询
	 * @param Time
	 * @param beginTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rainfall> selectByYear(
			@Param("Time") Date Time,
			@Param("beginTime") Date beginTime,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm,
			@Param("pptn") String pptn);

	/**
	 * 降雨量查询(时段)
	 * @param beginTime
	 * @param endTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rainfall> selectByTime(
			@Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,
			@Param("NowTime") Date NowTime,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm,
			@Param("pptn") String pptn);
}
