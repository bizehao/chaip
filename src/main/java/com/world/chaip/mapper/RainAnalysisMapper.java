package com.world.chaip.mapper;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.entity.Rainfall;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RainAnalysisMapper {

    //汛期今年 往年查询
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
    //汛期常年查询
    List<RainExchange> getRainXQCLCompared(@Param("adcd") List<String> adcd,
                                           @Param("systemTypes") List<String> systemTypes,
                                           @Param("stcdOrStnm") List<String> stcdOrStnm);

    //年逐月降雨量
    List<RainExchange> getRainNZYCompared(@Param("beginTime1") Date beginTime1,
                                          @Param("endTime1") Date endTime1,
                                          @Param("beginTime2") Date beginTime2,
                                          @Param("endTime2") Date endTime2,
                                          @Param("beginTime3") Date beginTime3,
                                          @Param("endTime3") Date endTime3,
                                          @Param("beginTime4") Date beginTime4,
                                          @Param("endTime4") Date endTime4,
                                          @Param("beginTime5") Date beginTime5,
                                          @Param("endTime5") Date endTime5,
                                          @Param("beginTime6") Date beginTime6,
                                          @Param("endTime6") Date endTime6,
                                          @Param("beginTime7") Date beginTime7,
                                          @Param("endTime7") Date endTime7,
                                          @Param("beginTime8") Date beginTime8,
                                          @Param("endTime8") Date endTime8,
                                          @Param("beginTime9") Date beginTime9,
                                          @Param("endTime9") Date endTime9,
                                          @Param("beginTime10") Date beginTime10,
                                          @Param("endTime10") Date endTime10,
                                          @Param("beginTime11") Date beginTime11,
                                          @Param("endTime11") Date endTime11,
                                          @Param("beginTime12") Date beginTime12,
                                          @Param("endTime12") Date endTime12,
                                          @Param("adcd") List<String> adcd,
                                          @Param("systemTypes") List<String> systemTypes,
                                          @Param("stcdOrStnm") List<String> stcdOrStnm);

    //年逐月常量查询
    List<RainExchange> getRainNZYCLCompared(@Param("adcd") List<String> adcd,
                                           @Param("systemTypes") List<String> systemTypes,
                                           @Param("stcdOrStnm") List<String> stcdOrStnm);

    //任意日降雨量
    List<RainExchange> getRainRYCompared(@Param("beginTime") Date beginTime,
                                         @Param("endTime") Date endTime,
                                         @Param("adcd") List<String> adcd,
                                         @Param("systemTypes") List<String> systemTypes,
                                         @Param("stcdOrStnm") List<String> stcdOrStnm);

    //任意常量日降雨量
    List<RainExchange> getRainRYCLCompared(@Param("beginTime") Date beginTime,
                                           @Param("endTime") Date endTime,
                                           @Param("adcd") List<String> adcd,
                                           @Param("systemTypes") List<String> systemTypes,
                                           @Param("stcdOrStnm") List<String> stcdOrStnm);

}
