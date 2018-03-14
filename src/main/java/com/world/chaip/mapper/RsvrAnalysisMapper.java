package com.world.chaip.mapper;

import com.world.chaip.entity.Exchange.RsvrExchange;
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
                                                 @Param("stcdOrStnm") List<String> stcdOrStnm);

    /**
     * 水库水量分析表
     * @param time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<Rsvr> getRsvrWaterAnalysisRi(@Param("time") Date time,
                                                 @Param("adcd") List<String> adcd,
                                                 @Param("systemTypes") List<String> systemTypes,
                                                 @Param("stcdOrStnm") List<String> stcdOrStnm);

    List<Rsvr> getRsvrStorageAnalysis(@Param("Time") Date Time,
                                      @Param("adcd") List<String> adcd,
                                      @Param("systemTypes") List<String> systemTypes,
                                      @Param("stcdOrStnm") List<String> stcdOrStnm);

    /**
     * 水库特征值统计表
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
                                               @Param("stcdOrStnm") List<String> stcdOrStnm);


}
