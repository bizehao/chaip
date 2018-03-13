package com.world.chaip.service;

import java.util.List;

import com.world.chaip.entity.baseinfo.Station;
import com.world.chaip.entity.baseinfo.StationDetail;
import com.world.chaip.entity.report.City;
import com.world.chaip.entity.report.Stations;
import com.world.chaip.entity.report.Tyid;
import org.springframework.stereotype.Service;


public interface StationService {
	public List<StationDetail> getAll();

    /**
     * 加载市县
     * @return
     */
    List<City> getCity();

    /**
     * 根据市县获取相应的站点信息
     * @param adcd
     * @return
     */
    List<Stations> getStations(List<String> adcd);
    /**
     * 获取站类信息
     * @return
     */
    List<Tyid> getTyid();
}

