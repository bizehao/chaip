package com.world.chaip.mapper;

import com.world.chaip.entity.Exchange.RsvrExchange;
import com.world.chaip.entity.Exchange.RsvrStronge;
import com.world.chaip.entity.Exchange.RsvrWaterExchange;
import com.world.chaip.entity.report.Rsvr;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 水库水情分析
 */
@Repository
public interface RsvrAnalysisMapper {

	/**
	 * 水库水量分析表
	 *
	 * @param beginTime
	 * @param endTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<RsvrWaterExchange> getRsvrWaterAnalysis(@Param("beginTime") Date beginTime,
	                                             @Param("endTime") Date endTime,
	                                             @Param("adcd") List<String> adcd,
	                                             @Param("systemTypes") List<String> systemTypes,
	                                             @Param("stcdOrStnm") List<String> stcdOrStnm,
	                                             @Param("ly") List<String> ly);

	/**
	 * 水库水量分析表 单日
	 *
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<Rsvr> getRsvrWaterAnalysisRi(@Param("beginTime") Date beginTime,
	                                  @Param("endTime") Date endTime,
	                                  @Param("adcd") List<String> adcd,
	                                  @Param("systemTypes") List<String> systemTypes,
	                                  @Param("stcdOrStnm") List<String> stcdOrStnm,
	                                  @Param("ly") List<String> ly);

	/**
	 * 水库蓄水量分析表
	 *
	 * @param time1
	 * @param time2
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<RsvrStronge> getRsvrStorageAnalysis(@Param("time1") Date time1,
	                                         @Param("time2") Date time2,
	                                         @Param("adcd") List<String> adcd,
	                                         @Param("systemTypes") List<String> systemTypes,
	                                         @Param("stcdOrStnm") List<String> stcdOrStnm,
	                                         @Param("sign") int sign,
	                                         @Param("ly") List<String> ly);

	/**
	 * 水库蓄水量分析常量值表
	 *
	 * @param month
	 * @param day
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<RsvrStronge> getRsvrStorageCLAnalysis(@Param("month") int month,
	                                           @Param("day") int day,
	                                           @Param("adcd") List<String> adcd,
	                                           @Param("systemTypes") List<String> systemTypes,
	                                           @Param("stcdOrStnm") List<String> stcdOrStnm,
	                                           @Param("sign") int sign,
	                                           @Param("ly") List<String> ly);

	/**
	 * 水库特征值统计表
	 *
	 * @param beginTime
	 * @param endTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<RsvrExchange> getRsvrFeaturesAnalysis(@Param("beginTime") Date beginTime,
	                                           @Param("endTime") Date endTime,
	                                           @Param("adcd") List<String> adcd,
	                                           @Param("systemTypes") List<String> systemTypes,
	                                           @Param("stcdOrStnm") List<String> stcdOrStnm,
	                                           @Param("ly") List<String> ly);

	/**
	 * 水库特征值统计表 优化版
	 *
	 * @param beginTime
	 * @param endTime
	 * @param adcd
	 * @param systemTypes
	 * @param stcdOrStnm
	 * @return
	 */
	List<RsvrExchange> getRsvrFeaturesAnalysisOptimization(@Param("beginTime") Date beginTime,
	                                                       @Param("endTime") Date endTime,
	                                                       @Param("adcd") List<String> adcd,
	                                                       @Param("systemTypes") List<String> systemTypes,
	                                                       @Param("stcdOrStnm") List<String> stcdOrStnm,
	                                                       @Param("ly") List<String> ly);

	//水库特征值统计表 优化版 获取最高水位的时间
	String getRsvrFeaturesAnalysisOptimizationType(@Param("stcd") String stcd,
	                                               @Param("beginTime") Date beginTime,
	                                               @Param("endTime") Date endTime,
	                                               @Param("type") String type,
	                                               @Param("typeValue") double typeValue);

}
