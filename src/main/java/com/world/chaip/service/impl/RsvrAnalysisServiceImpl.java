package com.world.chaip.service.impl;

import com.world.chaip.entity.Exchange.RsvrExchange;
import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.mapper.RsvrAnalysisMapper;
import com.world.chaip.service.RsvrAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public class RsvrAnalysisServiceImpl implements RsvrAnalysisService{

    @Autowired
    private RsvrAnalysisMapper rsvrAnalysisMapper;

    @Override
    public List<Rsvr> getRsvrWaterAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        return null;
    }

    @Override
    public List<Rsvr> getRsvrStorageAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        return null;
    }

    @Override
    public List<RsvrExchange> getRsvrFeaturesAnalysis(Date dateS, Date dateE, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
        return null;
    }
}
