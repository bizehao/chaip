package com.world.chaip.mapper;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.entity.Rainfall;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RainAnalysisMapper {

    List<RainExchange> getRainXQCompared(@Param("beginTime1") Date beginTime1,
                                         @Param("endTime1") Date endTime1,
                                         @Param("beginTime2") Date beginTime2,
                                         @Param("endTime2") Date endTime2,
                                         @Param("beginTime3") Date beginTime3,
                                         @Param("endTime3") Date endTime3,
                                         @Param("beginTime4") Date beginTime4,
                                         @Param("endTime4") Date endTime4,
                                         @Param("adcd") List<String> adcd,
                                         @Param("systemTypes") List<String> systemTypes,
                                         @Param("stcdOrStnm") List<String> stcdOrStnm);

    List<RainExchange> getRainXQCLCompared(@Param("adcd") List<String> adcd,
                                         @Param("systemTypes") List<String> systemTypes,
                                         @Param("stcdOrStnm") List<String> stcdOrStnm);
}
