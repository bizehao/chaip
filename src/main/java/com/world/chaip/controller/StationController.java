package com.world.chaip.controller;

import java.util.ArrayList;
import java.util.List;

import com.world.chaip.entity.report.City;
import com.world.chaip.entity.report.Stations;
import com.world.chaip.entity.report.Tyid;
import com.world.chaip.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.world.chaip.entity.baseinfo.Station;
import com.world.chaip.entity.baseinfo.StationDetail;
import com.world.chaip.service.StationService;

@RestController
@RequestMapping("services/station")
public class StationController {
	
	@Autowired
	private StationService stationService;
	
	
	@GetMapping("getall")
	public List<StationDetail> getAll() {
		return stationService.getAll();
	}
    //获取县域
    @GetMapping("getcity")
    public List<City> getCity() {
        return stationService.getCity();
    }
    //获取站点
    @GetMapping("getstations")
    public JsonResult getStations(
            @RequestParam(name="adcd",required=false)String adcd) {
        System.out.println("县域编号"+adcd);

        List<String> adcdlist = new ArrayList<String>();
        if(adcd.equals("X")){
            adcdlist=null;
        }else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for(int i = 0; i<temp.length; i++){
                adcdlist.add(temp[i]);
            }
        }

        List<Stations> a = stationService.getStations(adcdlist);
        return new JsonResult(a);
    }

    @GetMapping("gettyid")
    public List<Tyid> getTyid() {
        return stationService.getTyid();
    }

}
