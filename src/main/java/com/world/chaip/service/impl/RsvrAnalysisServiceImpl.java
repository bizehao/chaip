package com.world.chaip.service.impl;

import com.world.chaip.entity.report.Rsvr;
import com.world.chaip.mapper.RsvrAnalysisMapper;
import com.world.chaip.service.RsvrAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RsvrAnalysisServiceImpl implements RsvrAnalysisService{

    @Autowired
    private RsvrAnalysisMapper rsvrAnalysisMapper;

    @Override
    public List<Rsvr> getRsvrWaterAnalysis() {
        return null;
    }

    @Override
    public List<Rsvr> getRsvrStorageAnalysis() {
        return null;
    }

    @Override
    public List<Rsvr> getRsvrFeaturesAnalysis() {
        return null;
    }
}
