package com.world.chaip.mapper;

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
     * 水库查询(实时)
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
            @Param("stcdOrStnm") List<String> stcdOrStnm);

	/**
	 * 水库查询（专业报表）
	 * @param time
	 * @param fstp
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<RsvrZhuanYe> getRsvrByZhaunYe(
			@Param("time") Date time,
			@Param("fstp") int fstp,
			@Param("adcd") List<String> adcd,
			@Param("systemTypes") List<String> systemTypes,
			@Param("stcdOrStnm") List<String> stcdOrStnm);

	/**
	 * 查询汛期
	 * @param fstp
	 * @return
	 */
	RsvrXunQi getRsvrFS(
			@Param("fstp") int fstp);
}
