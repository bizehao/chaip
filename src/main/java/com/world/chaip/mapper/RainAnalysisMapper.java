package com.world.chaip.mapper;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.entity.Rainfall;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface RainAnalysisMapper {

    //汛期今年 往年查询
    List<RainExchange> getRainXQCompared(@Param("endTime1") Date endTime1,
                                         @Param("endTime2") Date endTime2,
                                         @Param("endTime3") Date endTime3,
                                         @Param("endTime4") Date endTime4,
                                         @Param("adcd") List<String> adcd,
                                         @Param("systemTypes") List<String> systemTypes,
                                         @Param("stcdOrStnm") List<String> stcdOrStnm);
    //汛期常年查询
    List<RainExchange> getRainXQCLCompared(@Param("adcd") List<String> adcd,
                                           @Param("systemTypes") List<String> systemTypes,
                                           @Param("stcdOrStnm") List<String> stcdOrStnm);

    //年逐月降雨量
    List<RainExchange> getRainNZYCompared(@Param("endTime1") Date endTime1,
                                          @Param("endTime2") Date endTime2,
                                          @Param("endTime3") Date endTime3,
                                          @Param("endTime4") Date endTime4,
                                          @Param("endTime5") Date endTime5,
                                          @Param("endTime6") Date endTime6,
                                          @Param("endTime7") Date endTime7,
                                          @Param("endTime8") Date endTime8,
                                          @Param("endTime9") Date endTime9,
                                          @Param("endTime10") Date endTime10,
                                          @Param("endTime11") Date endTime11,
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
    List<RainExchange> getRainRYCLCompared(@Param("beginTime") int beginTime,
                                           @Param("endTime") int endTime,
                                           @Param("adcd") List<String> adcd,
                                           @Param("systemTypes") List<String> systemTypes,
                                           @Param("stcdOrStnm") List<String> stcdOrStnm,
                                           @Param("sign") int sign,
                                           @Param("isendTime") int isendTime);

}
