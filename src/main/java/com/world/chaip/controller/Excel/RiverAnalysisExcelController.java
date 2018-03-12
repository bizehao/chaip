package com.world.chaip.controller.Excel;

import com.world.chaip.service.RiverAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("services/realtime/riverfall")
public class RiverAnalysisExcelController {

    @Autowired
    private RiverAnalysisService riverAnalysisService;
}
