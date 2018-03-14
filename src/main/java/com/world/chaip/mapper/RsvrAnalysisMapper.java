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
     * @param monthTime
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<RsvrWaterExchange> getRsvrWaterAnalysis(@Param("monthTime") Date monthTime,
                                                 @Param("adcd") List<String> adcd,
                                                 @Param("systemTypes") List<String> systemTypes,
                                                 @Param("stcdOrStnm") List<String> stcdOrStnm);

    List<Rsvr> getRsvrStorageAnalysis(@Param("Time") Date Time,
                                      @Param("adcd") List<String> adcd,
                                      @Param("systemTypes") List<String> systemTypes,
                                      @Param("stcdOrStnm") List<String> stcdOrStnm);

    List<RsvrExchange> getRsvrFeaturesAnalysis(@Param("beginTime") Date beginTime,
                                               @Param("endTime") Date endTime,
                                               @Param("adcd") List<String> adcd,
                                               @Param("systemTypes") List<String> systemTypes,
                                               @Param("stcdOrStnm") List<String> stcdOrStnm);


}
