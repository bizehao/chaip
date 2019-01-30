package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExecls;
import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.FlowSpeed;
import com.world.chaip.entity.report.River;
import com.world.chaip.service.FlowShowService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 邢台新增模块
 * @time 2019/1/25 9:09
 */
@RestController
@RequestMapping("services/realtime/flowshowexcel")
public class FlowShowExcelController {

	@Resource
	FlowShowService mFlowShowService;

	@RequestMapping("flowspeed")
	public JsonResult getFlowSpeed(
			HttpServletResponse response,
			@RequestParam("dateS") String dateStr,
			@RequestParam("dateE") String dateEnd,
			@RequestParam(name = "ly", required = false) String ly,
			@RequestParam(name = "adcd", required = false) String adcd,
			@RequestParam(name = "systemTypes", required = false) String systemTypes,
			@RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm) {

		/*String dateStr = "2019-01-28 17:00";
		String dateEnd = "2019-01-28 17:00";
		String adcd = "X";
		String systemTypes = "11,12,";
		String stcdOrStnm = "X";
		String ly = "X";*/

		System.out.println("时间" + dateStr);
		System.out.println("时间" + dateEnd);
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
		List<FlowSpeed> a = mFlowShowService.getFlowSpeed(dateS, dateE, lylist, adcdlist, typelist, stcdlist);
		String dateSs = null;
		String dateEs = null;
		try {
			dateSs = DateUtils.format(dateS, "yyyy-MM-dd HH:mm");
			dateEs = DateUtils.format(dateE, "yyyy-MM-dd HH:mm");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String time = "时间：" + dateSs + "-" + dateEs;
		String title = "流速查询表";
		String[] rowsName = new String[]{"序号", "县域", "站名", "站号", "时间", "流速1", "流速2", "流速3", "流速4", "流速5", "水位"};
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objects = null;

		for (int i = 0; i < a.size(); i++) {
			objects = new Object[11];
			objects[0] = a.get(i).getSerial();
			objects[1] = a.get(i).getAdnm();
			objects[2] = a.get(i).getStnm();
			objects[3] = a.get(i).getStcd();
			objects[4] = a.get(i).getTm();
			objects[5] = a.get(i).getLs1();
			objects[6] = a.get(i).getLs2();
			objects[7] = a.get(i).getLs3();
			objects[8] = a.get(i).getLs4();
			objects[9] = a.get(i).getLs5();
			objects[10] = a.get(i).getZ();
			dataList.add(objects);
		}
		//导出Excel公共方法调用
		ExportExecls exportExecls = new ExportExecls(response, title, dataList, time, 63, 4, 11, ExportExecls.Direction.TRANSVERSE);
		exportExecls.export(new ExportExecls.ColumnAndHead() {
			@Override
			public void colHeadHandler(Sheet sheet) {
				CellStyle style = exportExecls.getContentStyle(sheet.getWorkbook());
				Row colTitleRow = sheet.createRow(3);
				Cell cell;
				for (int i = 0; i < rowsName.length; i++) {
					cell = colTitleRow.createCell(i);
					cell.setCellValue(rowsName[i]);
					cell.setCellStyle(style);
				}
				int weight = ExportExecls.HEIGHT / 11;
				for (int i = 0; i < 11; i++) {
					if (i == 0) sheet.setColumnWidth(i, weight - 1000);
					else if (i == 4) sheet.setColumnWidth(i, weight + 1000 + 9 * 500);
					else sheet.setColumnWidth(i, weight - 500);
				}
			}
		});
		return new JsonResult(a);
	}
}
