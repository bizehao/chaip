package com.world.chaip.mapper;

import com.world.chaip.entity.FlowSpeed;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 流速查询
 * @time 2019/1/25 9:11
 */
@Repository
public interface FlowShowMapper {
	/**
	 * 流量站流速查询 时间不同
	 * @return
	 */
	List<FlowSpeed> getFlowSpeed01(@Param("beginTime") Date beginTime,
	                         @Param("endTime") Date endTime,
	                         @Param("ly") List<String> ly,
	                         @Param("adcd") List<String> adcd,
	                         @Param("systemTypes") List<String> systemTypes,
	                         @Param("stcdOrStnm") List<String> stcdOrStnm);
	/**
	 * 流量站流速查询 时间相同
	 * @return
	 */
	List<FlowSpeed> getFlowSpeed02(@Param("time") Date time,
	                      @Param("ly") List<String> ly,
	                      @Param("adcd") List<String> adcd,
	                      @Param("systemTypes") List<String> systemTypes,
	                      @Param("stcdOrStnm") List<String> stcdOrStnm);
}
