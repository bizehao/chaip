package com.world.chaip.service;

import com.world.chaip.entity.Exchange.*;
import com.world.chaip.entity.report.Rsvr;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


public interface RsvrAnalysisService {

    //水库水量分析表
    RsvrWaterExcel getRsvrWaterAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, List<String> ly);

    //水库蓄水量量分析表
    Object getRsvrStorageAnalysis(Date date, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, int sign, List<String> ly);

    //水库特征值统计表
    RsvrExchangeExcel getRsvrFeaturesAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm, List<String> ly) throws ParseException;
}
