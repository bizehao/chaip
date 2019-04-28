package com.world.chaip.controller;

import com.world.chaip.entity.FlowSpeed;
import com.world.chaip.entity.exchangeRain.ArbitrarilyDay;
import com.world.chaip.service.FlowShowService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 邢台新增模块
 * @time 2019/1/25 9:09
 */
@RestController
@RequestMapping("services/realtime/flowshow")
public class FlowShowController {

	@Resource
	FlowShowService mFlowShowService;

	@RequestMapping("flowspeed")
	public JsonResult getFlowSpeed(
			@RequestParam("dateS") String dateStr,
			@RequestParam("dateE") String dateEnd,
			@RequestParam(name = "ly", required = false) String ly,
			@RequestParam(name = "adcd", required = false) String adcd,
			@RequestParam(name = "systemTypes", required = false) String systemTypes,
			@RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm) {

		/*String dateStr = "2019-01-22 17:00";
        String dateEnd = "2019-01-24 17:00";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";
        String ly = "X";*/

		System.out.println("开始时间" + dateStr);
		System.out.println("结束时间" + dateEnd);
		System.out.println("县域" + adcd);
		System.out.println("站类型" + systemTypes);
		System.out.println("站号" + stcdOrStnm);

		List<String> lylist = new ArrayList<String>();
		List<String> adcdlist = new ArrayList<String>();
		List<String> typelist = new ArrayList<String>();
		List<String> stcdlist = new ArrayList<String>();

		if (ly.equals("X")) {
			lylist = null;
		} else {
			ly = ly.substring(0, ly.length() - 1);
			String[] temp = ly.split(",");
			for (int i = 0; i < temp.length; i++) {
				lylist.add(temp[i]);
			}
		}

		if (adcd.equals("X")) {
			adcdlist = null;
		} else {
			adcd = adcd.substring(0, adcd.length() - 1);
			String[] temp = adcd.split(",");
			for (int i = 0; i < temp.length; i++) {
				adcdlist.add(temp[i]);
			}
		}

		if (systemTypes.equals("X")) {
			typelist = null;
		} else {
			systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
			String[] sytemp = systemTypes.split(",");
			for (int i = 0; i < sytemp.length; i++) {
				typelist.add(sytemp[i]);
			}
		}
		if (stcdOrStnm.equals("X")) {
			stcdlist = null;
		} else {
			stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
			String[] sytemp = stcdOrStnm.split(",");
			for (int i = 0; i < sytemp.length; i++) {
				stcdlist.add(sytemp[i]);
			}
		}
		Date dateS = null;
		Date dateE = null;
		try {
			dateS = DateUtils.parse(dateStr, "yyyy-MM-dd HH:mm");
			dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("==========================");
		List<FlowSpeed> a = mFlowShowService.getFlowSpeed(dateS, dateE, lylist, adcdlist, typelist, stcdlist);
		return new JsonResult(a);
	}
}
