package com.world.chaip.service;

import com.world.chaip.entity.Exchange.RiverExchange;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 专业分析  （河道）
 */

public interface RiverAnalysisService {

    /**
     * 河道水情分析
     * @param dateS
     * @param dateE
     * @param adcd
     * @param systemTypes
     * @param stcdOrStnm
     * @return
     */
    List<RiverExchange> getRiverByAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);
}
