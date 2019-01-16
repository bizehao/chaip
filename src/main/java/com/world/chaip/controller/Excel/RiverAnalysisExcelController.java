package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExcel;
import com.world.chaip.business.ExportExecls;
import com.world.chaip.business.StaticConfig;
import com.world.chaip.entity.Exchange.RiverExchange;
import com.world.chaip.entity.exchangeRain.XunQi;
import com.world.chaip.service.RiverAnalysisService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/riveranalysisfall")
public class RiverAnalysisExcelController {

	private String ip = StaticConfig.ipAddress;

	@Autowired
	private RiverAnalysisService riverAnalysisService;

	//河道水情分析
	@GetMapping("getriverbyanalysisexcel")
	public void GetRiverByAnalysisExcel(
			HttpServletResponse response/*,
            @RequestParam("dateS") String dateStart,
            @RequestParam("dateE") String dateEnd,
            @RequestParam(name = "adcd", required = false) String adcd,
            @RequestParam(name = "systemTypes", required = false) String systemTypes,
            @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
            @RequestParam(name="ly",required = false)String ly*/) throws Exception {

		String dateStart = "2017-02-11";
		String dateEnd = "2017-06-12";
		String adcd = "X";
		String systemTypes = "11,12,";
		String stcdOrStnm = "X";
		String ly = "X";

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
		List<RiverExchange> riverByAnalysis = riverAnalysisService.getRiverByAnalysis(dateS, dateE, adcdlist, typelist, stcdlist, lylist);
		List<Object[]> dataList = new ArrayList<>();
		Object[] objects = null;
		for (int i = 0; i < riverByAnalysis.size(); i++) {
			objects = new Object[9];
			objects[0] = i + 1;
			objects[1] = riverByAnalysis.get(i).getRvnm();
			objects[2] = riverByAnalysis.get(i).getStnm();
			objects[3] = riverByAnalysis.get(i).getAvgQ();
			objects[4] = riverByAnalysis.get(i).getSumQ();
			objects[5] = riverByAnalysis.get(i).getMaxZ();
			objects[6] = riverByAnalysis.get(i).getMaxZTime();
			objects[7] = riverByAnalysis.get(i).getMaxQ();
			objects[8] = riverByAnalysis.get(i).getMaxQTime();
			dataList.add(objects);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Calendar tm = Calendar.getInstance();
		tm.setTime(dateS);
		int beginMonth = tm.get(Calendar.MONTH) + 1;
		tm.setTime(dateE);
		int endMonth = tm.get(Calendar.MONTH) + 1;
		String time = formatter.format(dateS) + "年" + beginMonth + "-" + endMonth + "月";
		String title = formatter.format(dateS) + "年" + beginMonth + "-" + endMonth + "月" + "各主要河道(闸坝)水量统计表";
		String[] rowsName = new String[]{"序号", "河名", "站名", formatter.format(dateS) + "年" + beginMonth + "-" + endMonth + "月", "", "", "", "", ""};
		String[] shuangName = new String[]{"", "", "", "平均流量(m³/s)", "径流总量(百万m³)", "最高水位(m)", "出现日期", "最大流量(m³/s)", "出现日期"};
		//列头单元格合并
		//序号
		CellRangeAddress callRangeAddress1 = new CellRangeAddress(3, 4, 0, 0);//起始行,结束行,起始列,结束列
		//河名
		CellRangeAddress callRangeAddress2 = new CellRangeAddress(3, 4, 1, 1);//起始行,结束行,起始列,结束列
		//站名
		CellRangeAddress callRangeAddress3 = new CellRangeAddress(3, 4, 2, 2);//起始行,结束行,起始列,结束列
		//？年？月
		CellRangeAddress callRangeAddress4 = new CellRangeAddress(3, 3, 3, 8);//起始行,结束行,起始列,结束列
		CellRangeAddress[] titleCell = {callRangeAddress1, callRangeAddress2, callRangeAddress3, callRangeAddress4};
//        ExportExcel ex = new ExportExcel(title, rowsName, shuangName, titleCell, dataList, response, "");
//        ex.export();


		ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 40, 5, 9, ExportExecls.Direction.TRANSVERSE);
		exportExecls.export(new ExportExecls.ColumnAndHead() {
			@Override
			public void colHeadHandler(Sheet sheet) {
				CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
				Row colTitleRow = sheet.createRow(3);
				for (int i = 0; i < rowsName.length; i++) {
					System.out.print(rowsName[i] + "\t");
					Cell colTitle = colTitleRow.createCell(i);
					colTitle.setCellValue(rowsName[i]);
					colTitle.setCellStyle(style);
				}
				colTitleRow = sheet.createRow(4);
				for (int i = 0; i < shuangName.length; i++) {
					Cell colTitle = colTitleRow.createCell(i);
					System.out.print(shuangName[i] + "\t");
					colTitle.setCellValue(shuangName[i]);
					colTitle.setCellStyle(style);
				}

				for (int i = 0; i < titleCell.length; i++) {
					sheet.addMergedRegion(titleCell[i]);
				}
				int x = 29700 / 9;
				for (int i = 0; i < 9; i++) {
					sheet.setColumnWidth(i, x);
				}
			}
		});
	}


	@GetMapping(value = "riversqXbyfx")
	public JsonResult rainXbyTimeXQ() {
		return new JsonResult("http://" + ip + "/services/realtime/riveranalysisfall/getriverbyanalysisexcel");
	}


}
