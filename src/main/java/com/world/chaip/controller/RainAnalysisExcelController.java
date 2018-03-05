package com.world.chaip.controller;

import com.world.chaip.business.ExportExcel;
import com.world.chaip.entity.report.River;
import com.world.chaip.service.RainAnalysisService;
import com.world.chaip.util.DateUtils;
import com.world.chaip.util.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("services/realtime/rainanalysisfallexcel")
public class RainAnalysisExcelController {

    private RainAnalysisService service;

    @GetMapping(value="rainxqanalysisexcel")
    public void getRainAnalysisXQExcel(
            HttpServletResponse response,
            @RequestParam("date")String dateStr,
            @RequestParam(name="adcd",required=false)String adcd,
            @RequestParam(name="systemTypes",required=false)String systemTypes,
            @RequestParam(name="stcdOrStnm",required=false)String stcdOrStnm) throws Exception {

        System.out.println("时间"+dateStr);
        System.out.println("县域"+adcd);
        System.out.println("站类型"+systemTypes);
        System.out.println("站号"+stcdOrStnm);
        List<String> adcdlist = new ArrayList<String>();
        List<String> typelist = new ArrayList<String>();
        List<String> stcdlist = new ArrayList<String>();
        if(adcd.equals("X")){
            adcdlist=null;
        }else {
            adcd = adcd.substring(0, adcd.length() - 1);
            String[] temp = adcd.split(",");
            for(int i = 0; i<temp.length; i++){
                adcdlist.add(temp[i]);
            }
        }

        if(systemTypes.equals("X")){
            typelist=null;
        }else{
            systemTypes = systemTypes.substring(0, systemTypes.length() - 1);
            String[] sytemp = systemTypes.split(",");
            for(int i = 0; i<sytemp.length; i++){
                typelist.add(sytemp[i]);
            }
        }
        if(stcdOrStnm.equals("X")){
            stcdlist=null;
        }else{
            stcdOrStnm = stcdOrStnm.substring(0, stcdOrStnm.length() - 1);
            String[] sytemp = stcdOrStnm.split(",");
            for(int i = 0; i<sytemp.length; i++){
                stcdlist.add(sytemp[i]);
            }
        }
        Date date = null;
        try {
            date = DateUtils.parse(dateStr, "yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Object[]> dataList = service.getRainXQCompared(date, adcdlist, typelist,stcdlist);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        String time = formatter.format(date);
        String title = time+"年全省及各市年降雨量分析比较表";
        String[] rowsName = new String[]{"站名","6月","7月","8月","9月","6-9月","6月","7月","8月","9月","6-9月","6月","7月","8月","9月","6-9月"};
        ExportExcel ex = new ExportExcel(title, rowsName, dataList, response, time);
        ex.export();
    }

    @GetMapping(value="rainxqXbytime")
    public JsonResult rainXbyTime(){
        return new JsonResult("http://192.168.1.63:8080/services/realtime/rainanalysisfallexcel/rainxqanalysisexcel");
    }
}
