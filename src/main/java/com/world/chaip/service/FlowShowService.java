package com.world.chaip.service;

import com.world.chaip.entity.FlowSpeed;
import com.world.chaip.mapper.FlowShowMapper;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 新增模块 流速
 * @time 2019/1/25 9:55
 */
public interface FlowShowService {

	//流速查询
	List<FlowSpeed> getFlowSpeed(Date beginTime, Date endTime, List<String> ly, List<String> adcd, List<String> systemTypes,
	                       List<String> stcdOrStnm);
}
