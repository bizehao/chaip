package com.world.chaip.mapper;

import java.util.List;

import com.world.chaip.entity.report.City;
import com.world.chaip.entity.report.Stations;
import com.world.chaip.entity.report.Tyid;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.world.chaip.entity.baseinfo.Station;
import com.world.chaip.entity.baseinfo.StationDetail;

@Repository
public interface StationMapper {
	List<StationDetail> selectAll();

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
    List<Stations> getStations(@Param("adcd") List<String> adcd);

    /**
     * 获取站类信息
     * @return
     */
    List<Tyid> getTyid();

    String getStnmByStcd(String stcd);
}
