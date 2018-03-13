package com.world.chaip.service;

import com.world.chaip.entity.Exchange.RsvrExchange;
import com.world.chaip.entity.report.Rsvr;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface RsvrAnalysisService {

    //水库水量分析表
    List<Rsvr> getRsvrWaterAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);

    //水库蓄水量量分析表
    List<Rsvr> getRsvrStorageAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);

    //水库特征值统计表
    List<RsvrExchange> getRsvrFeaturesAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm);
}
