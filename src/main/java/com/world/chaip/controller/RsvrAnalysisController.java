package com.world.chaip.controller;

import com.world.chaip.entity.Exchange.*;
import com.world.chaip.service.RsvrAnalysisService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springsource.loaded.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/rsvrfall")
public class RsvrAnalysisController {

	@Autowired
	private RsvrAnalysisService rsvrAnalysisService;

	//水库水量分析表
	@GetMapping("getrsvrexchangewater")
	public RsvrWaterExcel GetRsvrByAnalysiswater(
			@RequestParam("dateS") String dateStart,
			@RequestParam("dateE") String dateEnd,
			@RequestParam(name = "adcd", required = false) String adcd,
			@RequestParam(name = "systemTypes", required = false) String systemTypes,
			@RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
			@RequestParam(name = "ly", required = false) String ly) {

		List<String> adcdlist = new ArrayList<String>();
		List<String> typelist = new ArrayList<String>();
		List<String> stcdlist = new ArrayList<String>();
		List<String> lylist = new ArrayList<>();

		System.out.println("开始时间" + dateStart);
		System.out.println("结束时间" + dateEnd);
		System.out.println("县域" + adcd);
		System.out.println("站类型" + systemTypes);
		System.out.println("站号" + stcdOrStnm);

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
		if (ly.equals("X")) {
			lylist = null;
		} else {
			ly = ly.substring(0, ly.length() - 1);
			String[] sytemp = ly.split(",");
			for (int i = 0; i < sytemp.length; i++) {
				lylist.add(sytemp[i]);
			}
		}
		Date dateS = null;
		Date dateE = null;
		try {
			dateS = DateUtils.parse(dateStart, "yyyy-MM-dd");
			dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		RsvrWaterExcel a = rsvrAnalysisService.getRsvrWaterAnalysis(dateS, dateE, adcdlist, typelist, stcdlist, lylist);
		return a;
	}

	//水库蓄水量分析对比表
	@GetMapping("getrsvrexchangestorage")
	public RsvrStrongeExcel GetRsvrByAnalysisStorage(
			@RequestParam("date") String date,
			@RequestParam(name = "adcd", required = false) String adcd,
			@RequestParam(name = "systemTypes", required = false) String systemTypes,
			@RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
			@RequestParam(name = "ly", required = false) String ly) {

		List<String> adcdlist = new ArrayList<String>();
		List<String> typelist = new ArrayList<String>();
		List<String> stcdlist = new ArrayList<String>();
		List<String> lylist = new ArrayList<>();

		System.out.println("时间" + date);
		System.out.println("县域" + adcd);
		System.out.println("站类型" + systemTypes);
		System.out.println("站号" + stcdOrStnm);

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
		if (ly.equals("X")) {
			lylist = null;
		} else {
			ly = ly.substring(0, ly.length() - 1);
			String[] sytemp = ly.split(",");
			for (int i = 0; i < sytemp.length; i++) {
				lylist.add(sytemp[i]);
			}
		}
		Date dateTime = null;
		try {
			dateTime = DateUtils.parse(date, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		RsvrStrongeExcel a = (RsvrStrongeExcel) rsvrAnalysisService.getRsvrStorageAnalysis(dateTime, adcdlist, typelist, stcdlist, 0, lylist);
		return a;
	}

	//水库特征值统计表
	@GetMapping("getrsvrexchangetongji")
	public RsvrExchangeExcel GetRsvrByAnalysistongji(
			/*@RequestParam("dateS") String dateStart,
			@RequestParam("dateE") String dateEnd,
			@RequestParam(name = "adcd", required = false) String adcd,
			@RequestParam(name = "systemTypes", required = false) String systemTypes,
			@RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
			@RequestParam(name = "ly", required = false) String ly*/) throws ParseException {

        String dateStart = "2018-07-11";
        String dateEnd = "2018-07-11";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";
        String ly = "X";

		List<String> adcdlist = new ArrayList<String>();
		List<String> typelist = new ArrayList<String>();
		List<String> stcdlist = new ArrayList<String>();
		List<String> lylist = new ArrayList<>();

		System.out.println("水库特征值统计表:查询");
		System.out.println("开始时间" + dateStart);
		System.out.println("结束时间" + dateEnd);
		System.out.println("县域" + adcd);
		System.out.println("站类型" + systemTypes);
		System.out.println("站号" + stcdOrStnm);

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
		if (ly.equals("X")) {
			lylist = null;
		} else {
			ly = ly.substring(0, ly.length() - 1);
			String[] sytemp = ly.split(",");
			for (int i = 0; i < sytemp.length; i++) {
				lylist.add(sytemp[i]);
			}
		}
		Date dateS = null;
		Date dateE = null;
		try {
			dateS = DateUtils.parse(dateStart, "yyyy-MM-dd");
			dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		RsvrExchangeExcel a = rsvrAnalysisService.getRsvrFeaturesAnalysis(dateS, dateE, adcdlist, typelist, stcdlist, lylist);
		return a;
	}

}
