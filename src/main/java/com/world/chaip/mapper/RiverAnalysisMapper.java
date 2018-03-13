package com.world.chaip.mapper;

import com.world.chaip.entity.Exchange.RiverExchange;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RiverAnalysisMapper {
    /**
     * 河道水情分析
     * @param beginTime
     * @param endTime
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<RiverExchange> getRiverByAnalysis(@Param("beginTime") Date beginTime,
                                           @Param("endTime") Date endTime,
                                           @Param("adcd") List<String> adcd,
                                           @Param("systemTypes") List<String> systemTypes,
                                           @Param("stcdOrStnm") List<String> stcdOrStnm);

    /**
     * 获取最大水位流量和日期
     * @return
     */
    List<RiverExchange> getRiverByMaxQZ(@Param("beginTime") Date beginTime,
                                        @Param("endTime") Date endTime,
                                        @Param("adcd") List<String> adcd,
                                        @Param("systemTypes") List<String> systemTypes,
                                        @Param("stcdOrStnm") List<String> stcdOrStnm);

}
