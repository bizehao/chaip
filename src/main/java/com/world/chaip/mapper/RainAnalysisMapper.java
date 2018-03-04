package com.world.chaip.mapper;

import com.world.chaip.entity.Rainfall;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RainAnalysisMapper {

    List<Rainfall> getRainXQCompared(@Param("Time") Date Time,
                                     @Param("adcd") List<String> adcd,
                                     @Param("systemTypes") List<String> systemTypes,
                                     @Param("stcdOrStnm") List<String> stcdOrStnm,
                                     @Param("pptn") String pptn);
}
