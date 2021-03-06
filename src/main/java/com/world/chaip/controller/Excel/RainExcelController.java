package com.world.chaip.controller.Excel;

import com.world.chaip.business.ExportExecls;
import com.world.chaip.business.StaticConfig;
import com.world.chaip.entity.DaybyHourRainfall;
import com.world.chaip.entity.excelFormat.DayRainExcel;
import com.world.chaip.entity.excelFormat.DayRainExcelX;
import com.world.chaip.entity.report.gson.PptnGson;
import com.world.chaip.service.RainfallService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("services/realtime/rainfallexcel")
public class RainExcelController extends HttpServlet {

    @Autowired
    private RainfallService rainfallService;

    private String ip = StaticConfig.ipAddress;

    //导出逐时表
    @GetMapping("getrainbyhourbyexcel")
    public void exportRainByHour(HttpServletResponse response,
	                             @RequestParam("date") String dateStr,
	                             @RequestParam(name = "adcd", required = false) String adcd,
	                             @RequestParam(name = "systemTypes", required = false) String systemTypes,
	                             @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
	                             @RequestParam(name = "column", required = false) String column,
	                             @RequestParam(name = "sign", required = false) String sign,
	                             @RequestParam(name = "ly", required = false) String ly) {

      /*  String dateStr = "2018-7-24";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String column = "X";
        String sign = "X";
        String ly = "X";*/


        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();
        String db = "and c.db in (1,3)";
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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int col = -1;
        int sig = -1;
        if (!column.equals("X")) {
            col = Integer.parseInt(column);
        }

        if (!sign.equals("X")) {
            sig = Integer.parseInt(sign);
        }
        List<PptnGson> a = rainfallService.getDaybyHour(date, adcdlist, typelist, stcdlist, col, sig, db, lylist);
        String b = rainfallService.getDaybyHourJS(date, adcdlist, typelist, stcdlist, db, lylist);
        String title = "逐时雨量统计报表";
        String[] rowsName = new String[]{"序号", "县名", "站名", "站类", "累计值(mm)", "9", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "0", "1", "2", "3", "4", "5", "6", "7", "8"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        Map<Object, Object> map = null;
        for (int i = 0; i < a.size(); i++) {
            objects = new Object[rowsName.length];
            objects[0] = i + 1;
            objects[1] = a.get(i).getAdnm();
            objects[2] = a.get(i).getStnm();
            objects[3] = a.get(i).getName();
            objects[4] = a.get(i).getCountDrp();
            map = a.get(i).getDrpMap();
            double xx = 8.0;
            for (int j = 5; j <= 28; j++) {
                xx++;
                if (xx > 23) {
                    xx = 0.0;
                }
                objects[j] = map.get(xx);
            }
            dataList.add(objects);
        }
        //处理时间
        Date beginTime = null;
        Date endTime = null;
        DaybyHourRainfall daybyHourRainfall = new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 8);
        beginTime = now.getTime();
        endTime = DateUtils.getDateAfter(beginTime, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time = "时间：" + begin + "~~" + end;
        System.out.println(time);
        //导出Excel公共方法调用
        //System.out.println(b);
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, dataList, time, 35, 4, 29, ExportExecls.Direction.TRANSVERSE);
        execls.setLastInfo(b);
        execls.setFontSize(8);
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                for (int i = 0; i < rowsName.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(rowsName[i]);
                    cell.setCellStyle(style);

                }
                int x = 35800/ 29;
                int y=x/5;
                for (int i = 0; i < 29; i++) {
                    if (i==0)sheet.setColumnWidth(i, x);
                    else if (i==2)sheet.setColumnWidth(i, x+2*y);
                    else if (i>4)sheet.setColumnWidth(i, x-10);
                    else sheet.setColumnWidth(i, x+60+y);
                }
            }
        });
    }

    @GetMapping(value = "rainXbyhour")
    public JsonResult rainXbyHour() throws IOException {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbyhourbyexcel");
    }

    //导出逐日表
    @GetMapping("getrainbydatebyexcel")
    public void exportRainByDate(HttpServletResponse response,
	                             @RequestParam("date") String dateStr,
	                             @RequestParam(name = "adcd", required = false) String adcd,
	                             @RequestParam(name = "systemTypes", required = false) String systemTypes,
	                             @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
	                             @RequestParam(name = "ly", required = false) String ly) throws Exception {

       /* String dateStr = "2018-7-24";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";*/


        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        System.out.println("时间" + dateStr);
        System.out.println("县域" + adcd);
        System.out.println("站类型" + systemTypes);
        System.out.println("站号" + stcdOrStnm);

        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DayRainExcel a = (DayRainExcel) rainfallService.getDaybyDate(date, adcdlist, typelist, stcdlist, 1, "ST_PPTN_R", benqu, db, lylist);
        String b = rainfallService.getDaybyDateJS(date, adcdlist, typelist, stcdlist, "ST_PPTN_R", benqu, db, lylist);
        String title = "日雨量统计报表";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        int cols1 = 11; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 2; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < a.getDayRainList().size(); i++) { //5
            DayRainExcel.DayRain item = a.getDayRainList().get(i);
            for (int j = 0; j < item.getDayRainArray().size(); j++) {
                if (j == 0) {
                    if (objects != null) dataList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    dataList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getDayRainArray().get(j)[k];
                }
            }
            if (i == a.getDayRainList().size() - 1) {
                dataList.add(objects);
            }
        }

        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainList().size();i++){
            DayRainExcel.DayRain item = a.getDayRainList().get(i);
            for (int j=0;j<item.getDayRainArray().size();j++){
                countList.add(item.getDayRainArray().get(j)[0]);
                avgList.add(item.getDayRainArray().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);


        //处理时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = formatter.format(date);
        String time = "时间：" + begin;
        ExportExecls execlse = new ExportExecls(response, title, dataList, time, 58, 4, 11, ExportExecls.Direction.VERTICAL);
        execlse.setLastInfo("平均值："+val+"(mm)\n"+b);//
        execlse.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execlse.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                Cell colTitle0 = colTitleRow.createCell(0);
                colTitle0.setCellValue("县名");
                colTitle0.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell colTitle1 = colTitleRow.createCell(2 * i + 1);
                    colTitle1.setCellValue("站名");
                    colTitle1.setCellStyle(style);
                    Cell colTitle2 = colTitleRow.createCell(2 * i + 2);
                    colTitle2.setCellValue("雨量(mm)");
                    colTitle2.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 12;
                for (int i = 0; i < 12; i++) {
                    if (i==0)sheet.setColumnWidth(i, x-50);
                    else if(i%2==0) sheet.setColumnWidth(i, x-600);
                    else if (i%2!=0) sheet.setColumnWidth(i, x+600);
                }
            }
        });
    }

    @GetMapping(value = "rainXbydate")
    public JsonResult rainXbyDate() {
        System.out.println("----------------");
        System.out.println("http://" + ip + "/services/realtime/rainfallexcel/getrainbydatebyexcel");
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbydatebyexcel");
    }

    //导出逐旬表
    @GetMapping("getrainbyxunbyexcel")
    public void exportRainByXun(HttpServletResponse response,
                                @RequestParam("date") String dateStr,
                                @RequestParam(name = "adcd", required = false) String adcd,
                                @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                @RequestParam(name = "ly", required = false) String ly) throws Exception {

       /* String dateStr = "2018-7-24";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";*/

        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX) rainfallService.getDaybyXun(date, adcdlist, typelist, stcdlist, 1, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        String b = rainfallService.getDaybyXunJS(date, adcdlist, typelist, stcdlist, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        List<DayRainExcelX.DayRainX> dayRainXES = a.getDayRainXList();

        List<Object[]> objectList = new ArrayList<>();
        Object[] objects = null;
        int cols1 = 16; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 3; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < dayRainXES.size(); i++) { //5
            DayRainExcelX.DayRainX item = dayRainXES.get(i);
            for (int j = 0; j < item.getRainList().size(); j++) {
                if (j == 0) {
                    if (objects != null) objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getRainList().get(j)[k];
                }
            }
            if (i == dayRainXES.size() - 1) {
                objectList.add(objects);
            }
        }
//        List<String> stringList = new ArrayList<>();
//        for (int i = 0; i < dayRainXES.size(); i++) {
//            DayRainExcelX.DayRainX item = dayRainXES.get(i);
//            String manage = "";
//            item.getAdnm();
//            manage += item.getAdnm() + ",";
//            List<Object[]> objectList2 = item.getRainList();
//            for (int j = 0; j < objectList2.size(); j++) {
//                Object[] manage1 = objectList2.get(j);
//                Object manage2 = manage1[0];
//                Object manage3 = manage1[1];
//                Object manage4 = manage1[2];
//                System.out.println(manage4.toString());
//                manage += manage2.toString() + ",";
//                manage += manage3.toString() + ",";
//                manage += manage4.toString() + ",";
//            }
//            stringList.add(manage);
//        }
//
////        for (int i = 0; i < stringList.size(); i++) {
////            System.out.println(stringList.get(i));
////        }
//
//        for (int i = 0; i < stringList.size(); i++) {
//            String[] count = stringList.get(i).split(",");
//            if (count.length == 16) {
//                objects = new Object[16];
//                for(int j=0;j<objects.length;j++){
//                    objects[j] = count[j];
//
//                }
//
//                objectList.add(objects);
//            } else if (count.length < 16) {
//                objects = new Object[16];
//                for (int j = 0; j < count.length; j++) {
//                    if (count[j] != null) {
//                        objects[j] = count[j];
//                    }
//                }
//                objectList.add(objects);
//            } else if (count.length > 16) {
//                objects = new Object[16];
//                objects[0] = count[0];
//                int num=1;
//                for (int j=1;j<count.length-1;j++){
//
//                    if (count[j]!=null){
//                        objects[num]=count[j];
//                        num+=1;
//                    }
//                    if (num>15){
//                        num=1;
//                        objectList.add(objects);
//                        objects = new Object[16];
//                    }
//                    if (j==(count.length-2)){
//                        if (count[j+1]!=null)
//                            objects[num]=count[j+1];
//                        // System.out.println(bb[j+1]);
//                        objectList.add(objects);
//                    }
//                }
//
//            }
//        }


//        for (int i = 0; i < objectList.size(); i++) {
//            Object[] bb = objectList.get(i);
//            for (int j = 0; j < bb.length; j++) {
//                if (bb[j] != null) {
//                    System.out.print(bb[j] + "\t");
//                }
//            }
//            System.out.println("");
//        }


        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainXList().size();i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            for (int j=0;j<item.getRainList().size();j++){
                countList.add(item.getRainList().get(j)[0]);
                avgList.add(item.getRainList().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                if (avgList.get(i)!=null){
                    sum += (double) avgList.get(i);
                }
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);

        String title = "旬雨量统计报表";
        //处理时间
        Date Time;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int ri = now.get(Calendar.DATE);
        String xun;
        if (ri == 11) {
            xun = "上旬";
        } else if (ri == 21) {
            xun = "中旬";
        } else {
            xun = "下旬";
        }
        Time = now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String begin = formatter.format(Time);
        String time = "时间：" + begin + "" + xun;
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, objectList, time, 50, 4, 16, ExportExecls.Direction.VERTICAL);
        execls.setLastInfo("平均值："+val+"(mm)\n"+b);
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("县名");
                cell.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell cell1 = row.createCell(3 * i + 1);
                    cell1.setCellValue("站名");
                    cell1.setCellStyle(style);
                    Cell cell2 = row.createCell(3 * i + 2);
                    cell2.setCellValue("雨量(mm)");
                    cell2.setCellStyle(style);
                    Cell cell3 = row.createCell(3 * i + 3);
                    cell3.setCellValue("降雨天数");
                    cell3.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 17;
                for (int i = 0; i < 16; i++) {
                    sheet.setColumnWidth(i, x+150);
                }
            }
        });
    }

    @GetMapping(value = "rainXbyxun")
    public JsonResult rainXbyXun() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbyxunbyexcel");
    }

    //导出逐月表
    @GetMapping("getrainbymonthbyexcel")
    public void exportRainByMonth(HttpServletResponse response,
                                  @RequestParam("date") String dateStr,
                                  @RequestParam(name = "adcd", required = false) String adcd,
                                  @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                  @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                  @RequestParam(name = "ly", required = false) String ly) throws Exception {

//        String dateStr = "2018-7-24";
//        String adcd = "X";
//        String systemTypes = "X";
//        String stcdOrStnm = "X";
//        String ly = "X";

        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();


        System.out.println("时间" + dateStr);
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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(date);
        DayRainExcelX a = (DayRainExcelX) rainfallService.getDaybyMonth(date, adcdlist, typelist, stcdlist, 1, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        String b = rainfallService.getDaybyMonthJS(date, adcdlist, typelist, stcdlist, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        String title = "月雨量统计报表";
        //处理List<Object[]>;
        List<Object[]> objectList = new ArrayList<>();
        List<DayRainExcelX.DayRainX> dayRainXES = a.getDayRainXList();
        Object[] objects = null;
        int cols1 = 16; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 3; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < dayRainXES.size(); i++) { //5
            DayRainExcelX.DayRainX item = dayRainXES.get(i);
            for (int j = 0; j < item.getRainList().size(); j++) {
                if (j == 0) {
                    if (objects != null) objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getRainList().get(j)[k];
                }
            }
            if (i == dayRainXES.size() - 1) {
                objectList.add(objects);
            }
        }


        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainXList().size();i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            for (int j=0;j<item.getRainList().size();j++){
                countList.add(item.getRainList().get(j)[0]);
                avgList.add(item.getRainList().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);

        //处理时间
        Date beginTime = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, -1);
        beginTime = now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String begin = formatter.format(date);
        String time = "时间：" + begin;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, objectList, time, 50, 4, 16, ExportExecls.Direction.VERTICAL);
        execls.setLastInfo("平均值："+val+"(mm)\n"+b);;
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("县名");
                cell.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell cell1 = row.createCell(3 * i + 1);
                    cell1.setCellValue("站名");
                    cell1.setCellStyle(style);
                    Cell cell2 = row.createCell(3 * i + 2);
                    cell2.setCellValue("雨量(mm)");
                    cell2.setCellStyle(style);
                    Cell cell3 = row.createCell(3 * i + 3);
                    cell3.setCellValue("降雨天数");
                    cell3.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 17;
                for (int i = 0; i < 16; i++) {
                    sheet.setColumnWidth(i, x+150);
                }
            }
        });
    }

    @GetMapping(value = "rainXbymonth")
    public JsonResult rainXbyMonth() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbymonthbyexcel");
    }

    //导出逐年表
    @GetMapping("getrainbyyearbyexcel")
    public void exportRainByYear(HttpServletResponse response,
                                 @RequestParam("date") String dateStr,
                                 @RequestParam(name = "adcd", required = false) String adcd,
                                 @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                 @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                 @RequestParam(name = "ly", required = false) String ly) throws Exception {

       /* String dateStr = "2018-7-24";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";*/

        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX) rainfallService.getDaybyYear(date, adcdlist, typelist, stcdlist, 1, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        String b = rainfallService.getDaybyYearJS(date, adcdlist, typelist, stcdlist, "ST_PPTN_R", "ST_PSTAT_R", benqu, db, lylist);
        String title = "年雨量统计报表";
        //处理List<Object[]>;
        List<Object[]> objectList = new ArrayList<>();
        List<DayRainExcelX.DayRainX> dayRainXES = a.getDayRainXList();
        Object[] objects = null;
        int cols1 = 16; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 3; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < dayRainXES.size(); i++) { //5
            DayRainExcelX.DayRainX item = dayRainXES.get(i);
            for (int j = 0; j < item.getRainList().size(); j++) {
                if (j == 0) {
                    if (objects != null) objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getRainList().get(j)[k];
                }
            }
            if (i == dayRainXES.size() - 1) {
                objectList.add(objects);
            }
        }

        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainXList().size();i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            for (int j=0;j<item.getRainList().size();j++){
                countList.add(item.getRainList().get(j)[0]);
                avgList.add(item.getRainList().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);

        //处理时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Calendar tm = Calendar.getInstance();
        tm.setTime(date);
        tm.add(Calendar.YEAR, -1);
        Date btm = tm.getTime();
        String begin = formatter.format(date);
        String time = "时间：" + begin;
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, objectList, time, 50, 4, 16, ExportExecls.Direction.VERTICAL);
        execls.setLastInfo("平均值："+val+"(mm)\n"+b);
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("县名");
                cell.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell cell1 = row.createCell(3 * i + 1);
                    cell1.setCellValue("站名");
                    cell1.setCellStyle(style);
                    Cell cell2 = row.createCell(3 * i + 2);
                    cell2.setCellValue("雨量(mm)");
                    cell2.setCellStyle(style);
                    Cell cell3 = row.createCell(3 * i + 3);
                    cell3.setCellValue("降雨天数");
                    cell3.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 17;
                for (int i = 0; i < 16; i++) {
                    sheet.setColumnWidth(i, x+150);
                }
            }
        });
    }

    @GetMapping(value = "rainXbyyear")
    public JsonResult rainXbyYear() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbyyearbyexcel");
    }

    //导出时段表
    @GetMapping("getrainbytimebyexcel")
    public void exportRainByTime(HttpServletResponse response,
                                 @RequestParam("dateS") String dateStart,
                                 @RequestParam("dateE") String dateEnd,
                                 @RequestParam(name = "adcd", required = false) String adcd,
                                 @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                 @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                 @RequestParam(name = "ly", required = false) String ly) throws Exception {


      /*  String dateStart = "2019-01-28 17:00";
        String dateEnd = "2019-01-28 17:00";
        String adcd = "X";
        String systemTypes = "11,12,";
        String stcdOrStnm = "X";
        String ly = "X";*/


        String benqu = "and c.dq=31";
        String db = "and c.db in (1,3)";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

        System.out.println(dateStart);
        System.out.println(dateEnd);
        System.out.println(adcd);
        System.out.println(systemTypes);
        System.out.println(stcdOrStnm);

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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH:mm");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX) rainfallService.getDaybyTime(dateS, dateE, adcdlist, typelist, stcdlist, 1, "ST_PPTN_R", benqu, db, lylist);
        String b = rainfallService.getDaybyTimeJS(dateS, dateE, adcdlist, typelist, stcdlist, "ST_PPTN_R", benqu, db, lylist);

        String title = "时段雨量统计报表";
        //处理List<Object[]>;
        //处理List<Object[]>;
        List<Object[]> objectList = new ArrayList<>();
        List<DayRainExcelX.DayRainX> dayRainXES = a.getDayRainXList();
        Object[] objects = null;
        int cols1 = 16; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 3; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < dayRainXES.size(); i++) { //5
            DayRainExcelX.DayRainX item = dayRainXES.get(i);
            for (int j = 0; j < item.getRainList().size(); j++) {
                if (j == 0) {
                    if (objects != null) objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getRainList().get(j)[k];
                }
            }
            if (i == dayRainXES.size() - 1) {
                objectList.add(objects);
            }
        }

        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainXList().size();i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            for (int j=0;j<item.getRainList().size();j++){
                countList.add(item.getRainList().get(j)[0]);
                avgList.add(item.getRainList().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);

        //处理时间
        Date beginTime = null;
        Date endTime = null;
        DaybyHourRainfall daybyHourRainfall = new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime = now.getTime();
        now.setTime(dateE);
        endTime = now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time = "时间：" + begin + " ~~" + end + "";
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, objectList, time, 50, 4, 16, ExportExecls.Direction.VERTICAL);
        execls.setLastInfo("平均值："+val+"(mm)\n"+b);
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("县名");
                cell.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell cell1 = row.createCell(3 * i + 1);
                    cell1.setCellValue("站名");
                    cell1.setCellStyle(style);
                    Cell cell2 = row.createCell(3 * i + 2);
                    cell2.setCellValue("雨量(mm)");
                    cell2.setCellStyle(style);
                    Cell cell3 = row.createCell(3 * i + 3);
                    cell3.setCellValue("最近1小时雨量");
                    cell3.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 17;
                for (int i = 0; i < 16; i++) {
                    sheet.setColumnWidth(i, x+150);
                }
            }
        });
    }

    @GetMapping(value = "rainXbytime")
    public JsonResult rainXbyTime() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbytimebyexcel");
    }

    //导表
    public List<Object[]> conExcel(DayRainExcelX a, String[] rowsName) {
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] rainOb = null;
        Object[] objects = null;
        for (int i = 0; i < a.getDayRainXList().size(); i++) {
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            objects = new Object[rowsName.length];
            objects[0] = item.getAdnm();
            List<Object[]> map = a.getDayRainXList().get(i).getRainList();
            int j = -2;
            int m = 0;
            int n = 0;
            for (int u = 0; u < map.size(); u++) {  /*20*/  /*5*/
                rainOb = map.get(u);
                j = j + 3;                                                                     /* 2 5 8 11 14 */   /*17*/
                m = j + 1;                                                                     /* 3 6 9 12 15 */
                n = m + 1;
                if (j <= 13) {
                    objects[j] = rainOb[0];
                    objects[m] = rainOb[1];
                    objects[n] = rainOb[2];
                }
                if (j == 13 || (u == map.size() - 1 && j != 16)) {                              /*6*/
                    dataList.add(objects);
                }
                if (j > 13 && map.size() >= 5) {
                    objects = new Object[rowsName.length];
                    objects[0] = null;
                    j = -2;
                    j = j + 3;
                    m = j + 1;
                    n = m + 1;
                    objects[j] = rainOb[0];
                    objects[m] = rainOb[1];
                    objects[n] = rainOb[2];
                    if (u == map.size() - 1) {
                        dataList.add(objects);
                    }
                }
            }
        }
        return dataList;
    }

    //导出逐日表(专业)
    @GetMapping("getrainbydatebyexcelzy")
    public void exportRainByDateZY(HttpServletResponse response,
                                   @RequestParam("date") String dateStr,
                                   @RequestParam(name = "adcd", required = false) String adcd,
                                   @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                   @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                   @RequestParam(name = "ly", required = false) String ly) throws Exception {

       /* String dateStr = "2018-7-24";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";*/

        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";


        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DayRainExcel a = (DayRainExcel) rainfallService.getDaybyDate(date, adcdlist, typelist, stcdlist, 1, "RP_PPTN_R", benqu, db, lylist);
        String b = rainfallService.getDaybyDateJS(date, adcdlist, typelist, stcdlist, "RP_PPTN_R", benqu, db, lylist);
        /*for(int i=0; i<a.getDayRainList().size(); i++){
            System.out.println(a.getDayRainList().get(i).getAdnm());
            Map<String, Double> map =  a.getDayRainList().get(i).getDayRainList();
            System.out.println(map.size());
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        }*/
//		String title = "日雨量统计报表";
//		String[] rowsName = new String[]{"县名", "站名", "雨量(mm)", "站名", "雨量(mm)", "站名", "雨量(mm)", "站名", "雨量(mm)", "站名", "雨量(mm)"};
//		List<Object[]> dataList = new ArrayList<Object[]>();
//		Object[] objects = null;
//		Object[] rx = null;
//		for (int i = 0; i < a.getDayRainList().size(); i++) {
//			DayRainExcel.DayRain item = a.getDayRainList().get(i);
//			objects = new Object[rowsName.length];
//			objects[0] = item.getAdnm();
//			int j = -1;
//			int m = 0;
//			for (int u = 0; u < item.getDayRainArray().size(); u++) {
//				rx = item.getDayRainArray().get(u);
//				j = j + 2;
//				m = j + 1;
//				if (j <= 9) {
//					objects[j] = rx[0];
//					objects[m] = rx[1];
//				}
//				if (j == 9 || (u == item.getDayRainArray().size() - 1 && j != 11)) {                              /*6*/
//					dataList.add(objects);
//				}
//				if (j > 9 && item.getDayRainArray().size() >= 5) {
//					objects = new Object[rowsName.length];
//					objects[0] = null;
//					j = -1;
//					j = j + 2;
//					m = j + 1;
//					objects[j] = rx[0];
//					objects[m] = rx[1];
//					if (u == item.getDayRainArray().size() - 1) {
//						dataList.add(objects);
//					}
//				}
//			}
//		}
//		//处理时间
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		String begin = formatter.format(date);
//		String time = "时间：" + begin;
//		System.out.println(time);
//		//导出Excel公共方法调用
//		ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time, b, 1);
//		ex.export();
        String title = "日雨量统计报表";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects = null;
        int cols1 = 11; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 2; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < a.getDayRainList().size(); i++) { //5
            DayRainExcel.DayRain item = a.getDayRainList().get(i);
            for (int j = 0; j < item.getDayRainArray().size(); j++) {
                if (j == 0) {
                    if (objects != null) dataList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    dataList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getDayRainArray().get(j)[k];
                }
            }
            if (i == a.getDayRainList().size() - 1) {
                dataList.add(objects);
            }
        }

        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainList().size();i++){
            DayRainExcel.DayRain item = a.getDayRainList().get(i);
            for (int j=0;j<item.getDayRainArray().size();j++){
                countList.add(item.getDayRainArray().get(j)[0]);
                avgList.add(item.getDayRainArray().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);
        //处理时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String begin = formatter.format(date);
        String time = "时间：" + begin;
        ExportExecls execlse = new ExportExecls(response, title, dataList, time, 59, 4, 11, ExportExecls.Direction.VERTICAL);
        execlse.setLastInfo("平均值："+val+"(mm)\n"+b);
        execlse.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execlse.getContentStyle(sheet.getWorkbook());
                Row colTitleRow = sheet.createRow(3);
                Cell colTitle0 = colTitleRow.createCell(0);
                colTitle0.setCellValue("县名");
                colTitle0.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell colTitle1 = colTitleRow.createCell(2 * i + 1);
                    colTitle1.setCellValue("站名");
                    colTitle1.setCellStyle(style);
                    Cell colTitle2 = colTitleRow.createCell(2 * i + 2);
                    colTitle2.setCellValue("雨量(mm)");
                    colTitle2.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 12;
                for (int i = 0; i < 12; i++) {
                    if (i==0)sheet.setColumnWidth(i, x-50);
                    else if(i%2==0) sheet.setColumnWidth(i, x-600);
                    else if (i%2!=0) sheet.setColumnWidth(i, x+600);
                }
            }
        });
    }

    @GetMapping(value = "rainXbydatezy")
    public JsonResult rainXbyDateZY() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbydatebyexcelzy");
    }

    //导出逐旬表(专业)
    @GetMapping("getrainbyxunbyexcelzy")
    public void exportRainByXunZY(HttpServletResponse response,
                                  @RequestParam("date") String dateStr,
                                  @RequestParam(name = "adcd", required = false) String adcd,
                                  @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                  @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                  @RequestParam(name = "ly", required = false) String ly) throws Exception {

      /*  String dateStr = "2018-7-24";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";*/


        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX) rainfallService.getDaybyXun(date, adcdlist, typelist, stcdlist, 1, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
        String b = rainfallService.getDaybyXunJS(date, adcdlist, typelist, stcdlist, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
		/*String title = "旬雨量统计报表";
		String[] rowsName = new String[]{"县名", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数"};
		//处理List<Object[]>;
		List<Object[]> dataList = conExcel(a, rowsName);
		//处理时间
		Date Time = null;
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int ri = now.get(Calendar.DATE);
		String xun = null;
		if (ri == 11) {
			xun = "上旬";
		} else if (ri == 21) {
			xun = "中旬";
		} else {
			xun = "下旬";
		}
		Time = now.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		String begin = formatter.format(Time);
		String time = "时间：" + begin + "" + xun;
		//导出Excel公共方法调用
		ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time, b, 1);
		ex.export();*/
        List<DayRainExcelX.DayRainX> dayRainXES = a.getDayRainXList();

        List<Object[]> objectList = new ArrayList<>();
        Object[] objects = null;
        int cols1 = 16; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 3; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < dayRainXES.size(); i++) { //5
            DayRainExcelX.DayRainX item = dayRainXES.get(i);
            for (int j = 0; j < item.getRainList().size(); j++) {
                if (j == 0) {
                    if (objects != null) objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getRainList().get(j)[k];
                }
            }
            if (i == dayRainXES.size() - 1) {
                objectList.add(objects);
            }
        }


        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainXList().size();i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            for (int j=0;j<item.getRainList().size();j++){
                countList.add(item.getRainList().get(j)[0]);
                avgList.add(item.getRainList().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);

        String title = "旬雨量统计报表";
        //处理时间
        Date Time;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int ri = now.get(Calendar.DATE);
        String xun;
        if (ri == 11) {
            xun = "上旬";
        } else if (ri == 21) {
            xun = "中旬";
        } else {
            xun = "下旬";
        }
        Time = now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String begin = formatter.format(Time);
        String time = "时间：" + begin + "" + xun;
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, objectList, time, 51, 4, 16, ExportExecls.Direction.VERTICAL);
        execls.setLastInfo("平均值："+val+"(mm)\n"+b);
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("县名");
                cell.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell cell1 = row.createCell(3 * i + 1);
                    cell1.setCellValue("站名");
                    cell1.setCellStyle(style);
                    Cell cell2 = row.createCell(3 * i + 2);
                    cell2.setCellValue("雨量(mm)");
                    cell2.setCellStyle(style);
                    Cell cell3 = row.createCell(3 * i + 3);
                    cell3.setCellValue("降水天数");
                    cell3.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 17;
                for (int i = 0; i < 16; i++) {
                    sheet.setColumnWidth(i, x+150);
                }
            }
        });
    }

    @GetMapping(value = "rainXbyxunzy")
    public JsonResult rainXbyXunZY() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbyxunbyexcelzy");
    }

    //导出逐月表(专业)
    @GetMapping("getrainbymonthbyexcelzy")
    public void exportRainByMonthZY(HttpServletResponse response,
                                    @RequestParam("date") String dateStr,
                                    @RequestParam(name = "adcd", required = false) String adcd,
                                    @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                    @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                    @RequestParam(name = "ly", required = false) String ly) throws Exception {

      /*  String dateStr = "2018-7-24";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";*/

        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX) rainfallService.getDaybyMonth(date, adcdlist, typelist, stcdlist, 1, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
        String b = rainfallService.getDaybyMonthJS(date, adcdlist, typelist, stcdlist, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
//		String title = "月雨量统计报表";
//		String[] rowsName = new String[]{"县名", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数"};
//		//处理List<Object[]>;
//		List<Object[]> dataList = conExcel(a, rowsName);
//		//处理时间
//		Date beginTime = null;
//		Calendar now = Calendar.getInstance();
//		now.setTime(date);
//		now.add(Calendar.MONTH, -1);
//		beginTime = now.getTime();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
//		String begin = formatter.format(beginTime);
//		String time = "时间：" + begin;
//		System.out.println(time);
//		//导出Excel公共方法调用
//		ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time, b, 1);
//		ex.export();

        String title = "月雨量统计报表";
        //处理List<Object[]>;
        List<Object[]> objectList = new ArrayList<>();
        List<DayRainExcelX.DayRainX> dayRainXES = a.getDayRainXList();
        Object[] objects = null;
        int cols1 = 16; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 3; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < dayRainXES.size(); i++) { //5
            DayRainExcelX.DayRainX item = dayRainXES.get(i);
            for (int j = 0; j < item.getRainList().size(); j++) {
                if (j == 0) {
                    if (objects != null) objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getRainList().get(j)[k];
                }
            }
            if (i == dayRainXES.size() - 1) {
                objectList.add(objects);
            }
        }

        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainXList().size();i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            for (int j=0;j<item.getRainList().size();j++){
                countList.add(item.getRainList().get(j)[0]);
                avgList.add(item.getRainList().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);

        //处理时间
        Date beginTime = null;
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, -1);
        beginTime = now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String begin = formatter.format(date);
        String time = "时间：" + begin;
        System.out.println(time);
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, objectList, time, 51, 4, 16, ExportExecls.Direction.VERTICAL);
        execls.setLastInfo("平均值："+val+"(mm)\n"+b);
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("县名");
                cell.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell cell1 = row.createCell(3 * i + 1);
                    cell1.setCellValue("站名");
                    cell1.setCellStyle(style);
                    Cell cell2 = row.createCell(3 * i + 2);
                    cell2.setCellValue("雨量(mm)");
                    cell2.setCellStyle(style);
                    Cell cell3 = row.createCell(3 * i + 3);
                    cell3.setCellValue("降水天数");
                    cell3.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 17;
                for (int i = 0; i < 16; i++) {
                    sheet.setColumnWidth(i, x+150);
                }
            }
        });
    }

    @GetMapping(value = "rainXbymonthzy")
    public JsonResult rainXbyMonthZY() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbymonthbyexcelzy");
    }

    //导出逐年表(专业)
    @GetMapping("getrainbyyearbyexcelzy")
    public void exportRainByYearZY(HttpServletResponse response,
                                   @RequestParam("date") String dateStr,
                                   @RequestParam(name = "adcd", required = false) String adcd,
                                   @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                   @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                   @RequestParam(name = "ly", required = false) String ly) throws Exception {

/*
        String dateStr = "2018-7-24";
        String adcd = "X";
        String systemTypes = "X";
        String stcdOrStnm = "X";
        String ly = "X";*/

        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX) rainfallService.getDaybyYear(date, adcdlist, typelist, stcdlist, 1, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
        String b = rainfallService.getDaybyYearJS(date, adcdlist, typelist, stcdlist, "RP_PPTN_R", "RP_PSTAT_R", benqu, db, lylist);
        /*for(int i=0; i<a.getDayRainXList().size(); i++){
            System.out.println(a.getDayRainXList().get(i).getAdnm());
            List<Object[]> map =  a.getDayRainXList().get(i).getRainList();
            System.out.println(map.size());
            for (int p=0; p<map.size();p++){
                Object[] om = map.get(p);
                System.out.println(Arrays.toString(om));
            }
        }*/
        //String title = "年雨量统计报表";
        //String[] rowsName = new String[]{"县名", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数", "站名", "雨量(mm)", "降水天数"};
        //处理List<Object[]>;
        //List<Object[]> dataList = conExcel(a, rowsName);
        //处理时间
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        //Calendar tm = Calendar.getInstance();
        //tm.setTime(date);
        //tm.add(Calendar.YEAR, -1);
        //Date ytm = tm.getTime();
        //String begin = formatter.format(ytm);
        //String time = "时间：" + begin;
        //System.out.println(time);
        //导出Excel公共方法调用
        //ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time, b, 1);
        //ex.export();

        String title = "年雨量统计报表";
        //处理List<Object[]>;
        List<Object[]> objectList = new ArrayList<>();
        List<DayRainExcelX.DayRainX> dayRainXES = a.getDayRainXList();
        Object[] objects = null;
        int cols1 = 16; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 3; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < dayRainXES.size(); i++) { //5
            DayRainExcelX.DayRainX item = dayRainXES.get(i);
            for (int j = 0; j < item.getRainList().size(); j++) {
                if (j == 0) {
                    if (objects != null) objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getRainList().get(j)[k];
                }
            }
            if (i == dayRainXES.size() - 1) {
                objectList.add(objects);
            }
        }

        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainXList().size();i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            for (int j=0;j<item.getRainList().size();j++){
                countList.add(item.getRainList().get(j)[0]);
                avgList.add(item.getRainList().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);

        //处理时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Calendar tm = Calendar.getInstance();
        tm.setTime(date);
        tm.add(Calendar.YEAR, -1);
        Date btm = tm.getTime();
        String begin = formatter.format(date);
        String time = "时间：" + begin;
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, objectList, time, 51, 4, 16, ExportExecls.Direction.VERTICAL);
        execls.setLastInfo("平均值："+val+"(mm)\n"+b);
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("县名");
                cell.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell cell1 = row.createCell(3 * i + 1);
                    cell1.setCellValue("站名");
                    cell1.setCellStyle(style);
                    Cell cell2 = row.createCell(3 * i + 2);
                    cell2.setCellValue("雨量(mm)");
                    cell2.setCellStyle(style);
                    Cell cell3 = row.createCell(3 * i + 3);
                    cell3.setCellValue("降水天数");
                    cell3.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 17;
                for (int i = 0; i < 16; i++) {
                    sheet.setColumnWidth(i, x+150);
                }
            }
        });


    }

    @GetMapping(value = "rainXbyyearzy")
    public JsonResult rainXbyYearZY() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbyyearbyexcelzy");
    }

    //导出时段表(专业)
    @GetMapping("getrainbytimebyexcelzy")
    public void exportRainByTimeZT(HttpServletResponse response,
                                   @RequestParam("dateS") String dateStart,
                                   @RequestParam("dateE") String dateEnd,
                                   @RequestParam(name = "adcd", required = false) String adcd,
                                   @RequestParam(name = "systemTypes", required = false) String systemTypes,
                                   @RequestParam(name = "stcdOrStnm", required = false) String stcdOrStnm,
                                   @RequestParam(name = "ly", required = false) String ly) throws Exception {


//        String dateStart = "2019-01-28 17:00";
//        String dateEnd = "2019-01-28 17:00";
//        String adcd = "X";
//        String systemTypes = "11,12,";
//        String stcdOrStnm = "X";
//        String ly = "X";
        System.out.println("开始" + dateStart);
        System.out.println("结束" + dateEnd);


        String benqu = "";
        String db = "and c.jdb in (1,3) and c.dq = 31";
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        List<String> lylist = new ArrayList<>();

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
            System.out.println("stcdOrStnm" + stcdOrStnm);
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
            dateS = DateUtils.parse(dateStart, "yyyy-MM-dd HH:mm");
            dateE = DateUtils.parse(dateEnd, "yyyy-MM-dd HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DayRainExcelX a = (DayRainExcelX) rainfallService.getDaybyTime(dateS, dateE, adcdlist, typelist, stcdlist, 1, "RP_PPTN_R", benqu, db, lylist);
        String b = rainfallService.getDaybyTimeJS(dateS, dateE, adcdlist, typelist, stcdlist, "RP_PPTN_R", benqu, db, lylist);
        /*for(int i=0; i<a.getDayRainXList().size(); i++){
            System.out.println(a.getDayRainXList().get(i).getAdnm());
            List<Object[]> map =  a.getDayRainXList().get(i).getRainList();
            System.out.println(map.size());
            for (int p=0; p<map.size();p++){
                Object[] om = map.get(p);
                System.out.println(Arrays.toString(om));
            }
        }*/
//		String title = "时段雨量统计报表";
//		String[] rowsName = new String[]{"县名", "站名", "雨量(mm)", "最近1小时雨量", "站名", "雨量(mm)", "最近1小时雨量", "站名", "雨量(mm)", "最近1小时雨量", "站名", "雨量(mm)", "最近1小时雨量", "站名", "雨量(mm)", "最近1小时雨量"};
//		//处理List<Object[]>;
//		List<Object[]> dataList = conExcel(a, rowsName);
//		//处理时间
//		Date beginTime = null;
//		Date endTime = null;
//		DaybyHourRainfall daybyHourRainfall = new DaybyHourRainfall();
//		Calendar now = Calendar.getInstance();
//		now.setTime(dateS);
//		beginTime = now.getTime();
//		now.setTime(dateE);
//		endTime = now.getTime();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		String begin = formatter.format(beginTime);
//		String end = formatter.format(endTime);
//		String time = "时间：" + begin + "~~" + end;
//		//导出Excel公共方法调用
//		ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time, b, 1);
//		ex.export();


        String title = "时段雨量统计报表";
        //处理List<Object[]>;
        //处理List<Object[]>;
        List<Object[]> objectList = new ArrayList<>();
        List<DayRainExcelX.DayRainX> dayRainXES = a.getDayRainXList();
        Object[] objects = null;
        int cols1 = 16; //总格子数
        int cols3 = 5; //重复的单个的格子树
        int cols4 = 3; //一行多少个重复的

        int index = 0;
        for (int i = 0; i < dayRainXES.size(); i++) { //5
            DayRainExcelX.DayRainX item = dayRainXES.get(i);
            for (int j = 0; j < item.getRainList().size(); j++) {
                if (j == 0) {
                    if (objects != null) objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = item.getAdnm();

                } else if (j % cols3 == 0) {
                    objectList.add(objects);
                    index = 0;
                    objects = new Object[cols1];
                    objects[index] = "";
                }
                for (int k = 0; k < cols4; k++) {
                    objects[++index] = item.getRainList().get(j)[k];
                }
            }
            if (i == dayRainXES.size() - 1) {
                objectList.add(objects);
            }
        }


        //添加平均值
        List<Object> countList=new ArrayList<>();
        List<Object> avgList=new ArrayList<>();
        for(int i=0;i<a.getDayRainXList().size();i++){
            DayRainExcelX.DayRainX item = a.getDayRainXList().get(i);
            for (int j=0;j<item.getRainList().size();j++){
                countList.add(item.getRainList().get(j)[0]);
                avgList.add(item.getRainList().get(j)[1]);
            }
        }
        double sum=0;
        for (int i=0;i<avgList.size();i++){
            if (avgList.get(i)!=null){
                sum += (double) avgList.get(i);
            }
        }
        double avg=sum/countList.size();
        String val = new DecimalFormat("#0.00").format(avg);
        //处理时间
        Date beginTime = null;
        Date endTime = null;
        DaybyHourRainfall daybyHourRainfall = new DaybyHourRainfall();
        Calendar now = Calendar.getInstance();
        now.setTime(dateS);
        beginTime = now.getTime();
        now.setTime(dateE);
        endTime = now.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        String time = "时间：" + begin + " ~~" + end + "";
        //导出Excel公共方法调用
        ExportExecls execls = new ExportExecls(response, title, objectList, time, 51, 4, 16, ExportExecls.Direction.VERTICAL);
        execls.setLastInfo("平均值："+val+"(mm)\n"+b);
        execls.export(new ExportExecls.ColumnAndHead() {
            @Override
            public void colHeadHandler(Sheet sheet) {
                CellStyle style = execls.getContentStyle(sheet.getWorkbook());
                Row row = sheet.createRow(3);
                Cell cell = row.createCell(0);
                cell.setCellValue("县名");
                cell.setCellStyle(style);
                for (int i = 0; i < 5; i++) {
                    Cell cell1 = row.createCell(3 * i + 1);
                    cell1.setCellValue("站名");
                    cell1.setCellStyle(style);
                    Cell cell2 = row.createCell(3 * i + 2);
                    cell2.setCellValue("雨量(mm)");
                    cell2.setCellStyle(style);
                    Cell cell3 = row.createCell(3 * i + 3);
                    cell3.setCellValue("最近1小时雨量");
                    cell3.setCellStyle(style);
                }
                int x = ExportExecls.WEIGHT / 17;
                for (int i = 0; i < 16; i++) {
                    sheet.setColumnWidth(i, x+150);
                }
            }
        });
    }

    @GetMapping(value = "rainXbytimezy")
    public JsonResult rainXbyTimeZY() {
        return new JsonResult("http://" + ip + "/services/realtime/rainfallexcel/getrainbytimebyexcelzy");
    }

}
