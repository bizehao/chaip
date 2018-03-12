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
     * @param Time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<RiverExchange> getRiverByAnalysis(
            @Param("Time") Date Time,
            @Param("adcd") List<String> adcd,
            @Param("systemTypes") List<String> systemTypes,
            @Param("stcdOrStnm") List<String> stcdOrStnm);

    /**
     * 获取最大水位和日期
     * @return
     */
    List<RiverExchange> getRiverByMaxQ(@Param("beginTime") Date beginTime,
                                       @Param("endTime") Date endTime,
                                       @Param("adcd") List<String> adcd,
                                       @Param("systemTypes") List<String> systemTypes,
                                       @Param("stcdOrStnm") List<String> stcdOrStnm);

    /**
     * 获取最大流量和日期
     * @return
     */
    List<RiverExchange> getRiverByMaxZ(@Param("beginTime") Date beginTime,
                                       @Param("endTime") Date endTime,
                                       @Param("adcd") List<String> adcd,
                                       @Param("systemTypes") List<String> systemTypes,
                                       @Param("stcdOrStnm") List<String> stcdOrStnm);
}
