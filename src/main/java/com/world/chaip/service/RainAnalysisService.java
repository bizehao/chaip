package com.world.chaip.service;

import com.world.chaip.entity.Exchange.RainExchange;
import com.world.chaip.entity.Rainfall;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 专业分析
 * @author BZH
 */
public interface RainAnalysisService {

    /**
     * 汛期降雨量分析对比
     * @param time
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<Object[]> getRainXQCompared(Date time, List<String> adcd, List<String> systemTypes,
                                         List<String> stcdOrStnm);
}
