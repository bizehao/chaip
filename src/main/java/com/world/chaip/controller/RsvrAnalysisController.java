package com.world.chaip.controller;

import com.world.chaip.service.RsvrAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("services/realtime/rsvrfall")
public class RsvrAnalysisController {

    @Autowired
    private RsvrAnalysisService rsvrAnalysisService;



}
