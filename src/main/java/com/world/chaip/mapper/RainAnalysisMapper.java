package com.world.chaip.mapper;

import com.world.chaip.entity.Rainfall;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RainAnalysisMapper {

    List<Rainfall> getRainXQCompared(@Param("beginTime") Date beginTime,
                                     @Param("endTime") Date endTime,
                                     @Param("adcd") List<String> adcd,
                                     @Param("systemTypes") List<String> systemTypes,
                                     @Param("stcdOrStnm") List<String> stcdOrStnm);
}
