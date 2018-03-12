package com.world.chaip.service;

import com.world.chaip.entity.report.Rsvr;

import java.util.List;

public interface RsvrAnalysisService {

    //水库水量分析表
    List<Rsvr> getRsvrWaterAnalysis();

    //水库蓄水量量分析表
    List<Rsvr> getRsvrStorageAnalysis();

    //水库特征值统计表
    List<Rsvr> getRsvrFeaturesAnalysis();
}
