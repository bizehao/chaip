package com.world.chaip.service.impl;

import com.world.chaip.entity.FlowSpeed;
import com.world.chaip.mapper.FlowShowMapper;
import com.world.chaip.service.FlowShowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 新增模块 流速
 * @time 2019/1/25 10:05
 */
@Service
public class FlowShowServiceImpl implements FlowShowService {

	@Resource
	FlowShowMapper mFlowShowMapper;

	//流速查询
	@Override
	public List<FlowSpeed> getFlowSpeed(Date beginTime, Date endTime, List<String> ly, List<String> adcd, List<String> systemTypes, List<String> stcdOrStnm) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<FlowSpeed> flowSpeeds;
		if (beginTime.getTime() == endTime.getTime()) {
			flowSpeeds = mFlowShowMapper.getFlowSpeed02(beginTime, ly, adcd, systemTypes, stcdOrStnm);
		} else {
			flowSpeeds = mFlowShowMapper.getFlowSpeed01(beginTime, endTime, ly, adcd, systemTypes, stcdOrStnm);
		}
		for (FlowSpeed vl : flowSpeeds) {
			try {
				vl.setTm(simpleDateFormat.format(simpleDateFormat.parse(vl.getTm())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return flowSpeeds;
	}
}
