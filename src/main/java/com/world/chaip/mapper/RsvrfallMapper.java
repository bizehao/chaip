package com.world.chaip.mapper;

import com.world.chaip.entity.newRsvr.RevrXunQi;
import com.world.chaip.entity.newRsvr.XunQITime;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.entity.report.RsvrXunQi;
import com.world.chaip.entity.report.RsvrZhuanYe;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RsvrfallMapper {
	/**
     * 水库查询(实时 时间不同)
	 * @param beginTime
     * @param endTime
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
	List<Rsvr> getRsvrByTerm(
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("ly") List<String> ly);

    /**
     * 水库查询(实时 时间相同)
     * @param time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<Rsvr> getRsvrByTermNew(
            @Param("time") Date time,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("ly") List<String> ly);

	/**
	 * 水库查询（专业报表 时间不同）
	 * @param beginTime
     * @param endTime
	 * @param
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<RsvrZhuanYe> getRsvrByZhaunYe(
            @Param("beginTime") Date beginTime,
			@Param("endTime") Date endTime,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm,
            @Param("ly") List<String> ly);
    /**
     * 水库查询（专业报表 时间相同）
     * @param time
     * @param fstp
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<RsvrZhuanYe> getRsvrByZhaunYeNew(
            @Param("time") Date time,
            @Param("fstp") int fstp,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") String stcdOrStnm,
            @Param("ly") List<String> ly);

	/**
	 * 查询汛期
	 * @return
	 */
	List<RevrXunQi> getRsvrFS();

	/**
	 * 查询主汛期的时间
	 * @return
	 */
	XunQITime getXQTime(String stcd);

	List<String> getFsltdzStations(@Param("fstp") int fstp);
}
