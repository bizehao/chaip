package com.world.chaip.service.impl;

import java.util.List;

import com.world.chaip.entity.report.City;
import com.world.chaip.entity.report.Stations;
import com.world.chaip.entity.report.Tyid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.world.chaip.entity.baseinfo.Station;
import com.world.chaip.entity.baseinfo.StationDetail;
import com.world.chaip.mapper.StationMapper;
import com.world.chaip.service.StationService;

@Service
public class StationServiceImpl implements StationService {

	@Autowired
	private StationMapper stationMapper;
	
	@Override
	public List<StationDetail> getAll() {
		return stationMapper.selectAll();
	}

    @Override
    public List<City> getCity() {
        return stationMapper.getCity();
    }

    @Override
    public List<Stations> getStations(List<String> adcd, String sttp) {
        return stationMapper.getStations(adcd, sttp);
    }

    @Override
    public List<Tyid> getTyid() {
        return stationMapper.getTyid();
    }

}
