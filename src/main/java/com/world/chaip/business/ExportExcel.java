package com.world.chaip.business;

import com.world.chaip.mapper.RainfallMapper;
import com.world.chaip.util.JsonResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Inet4Address;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ExportExcel {

    @Autowired
    RainfallMapper rainfallMapper;

    //显示的导出表的标题
    private String title;
    //导出表的列名
    private String[] rowName ;
    //双层表头的列名
    private String[] shuangName ;
    //双层表头的合并单元格
    private CellRangeAddress[] shuangCell;
    //导出的集合数据
    private List<Object[]> dataList = new ArrayList<Object[]>();
    private String xianshi;

    private String time;
    private HttpServletResponse response;

    public ExportExcel() {
    }

    //构造方法，传入要导出的数据 单行表头
    public ExportExcel(String title,String[] rowName,List<Object[]>  dataList, HttpServletResponse response, String time){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.time = time;
        this.response = response;
    }

    //构造方法，传入要导出的数据 双行表头
    public ExportExcel(String title,String[] rowName,String[] shuangName,CellRangeAddress[] shuangCell,List<Object[]>  dataList,
                       HttpServletResponse response, String time){
        this.dataList = dataList;
        this.rowName = rowName;
        this.shuangName = shuangName;
        this.shuangCell = shuangCell;
        this.title = title;
        this.time = time;
        this.response = response;
    }

    //构造方法，传入要导出的数据 双行表头 有最后一行
    public ExportExcel(String title,String[] rowName,String[] shuangName,CellRangeAddress[] shuangCell,List<Object[]>  dataList,
                       HttpServletResponse response, String time,String xianshi){
        this.dataList = dataList;
        this.rowName = rowName;
        this.shuangName = shuangName;
        this.shuangCell = shuangCell;
        this.title = title;
        this.time = time;
        this.response = response;
        this.xianshi = xianshi;
    }

    //构造方法，传入要导出的数据 最后一行
    public ExportExcel(String title,String[] rowName,List<Object[]>  dataList, HttpServletResponse response, String time,String xianshi){
        this.dataList = dataList;
        this.rowName = rowName;
        this.title = title;
        this.time = time;
        this.response = response;
        this.xianshi = xianshi;
    }

    /**
     * 导出数据
     * @throws Exception
     */
    public void export() throws Exception{
        InputStream is = null;
        try{
            HSSFWorkbook workbook = new HSSFWorkbook();                     // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title);                  // 创建工作表

            // 定义所需列数
            int columnNum = rowName.length;

            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);

            //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook);                  //单元格样式对象

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (columnNum-1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);
            //定义时间行
            CellRangeAddress callRangeAddress = new CellRangeAddress(2,2,0,(columnNum-1));//起始行,结束行,起始列,结束列
            sheet.addMergedRegion(callRangeAddress);
            HSSFRow rowRowTime = sheet.createRow(2);
            HSSFCell cellTime = rowRowTime.createCell(0);
            cellTime.setCellValue(time);
            HSSFRow rowRowName = sheet.createRow(3);                // 在索引3的位置创建行(最顶端的行开始的第四行)

            int wei = 0;    //从哪一行开始写起
            //合并双列表头的单元格
            if(shuangCell != null){
                wei = 5;
                int shuangtitle = shuangCell.length;
                for (int i=0; i<shuangtitle; i++){
                    sheet.addMergedRegion(shuangCell[i]);
                }
                int shangNum = rowName.length;
                for(int n=0;n<shangNum;n++){
                    HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格
                    cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型
                    HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                    cellRowName.setCellValue(text);                                 //设置列头单元格的值
                    cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
                }
                HSSFRow rowRowName1 = sheet.createRow(4);                // 在索引3的位置创建行(最顶端的行开始的第四行)
                int xiaNum = shuangName.length;
                for(int n=0;n<xiaNum;n++){
                    HSSFCell  cellRowName = rowRowName1.createCell(n);               //创建列头对应个数的单元格
                    cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型
                    HSSFRichTextString text = new HSSFRichTextString(shuangName[n]);
                    cellRowName.setCellValue(text);                                 //设置列头单元格的值
                    cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
                }
            }else{
                wei=4;
                // 将列头设置到sheet的单元格中
                for(int n=0;n<columnNum;n++){
                    HSSFCell  cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格
                    cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型
                    HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                    cellRowName.setCellValue(text);                                 //设置列头单元格的值
                    cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
                }
            }
            //将查询出的数据设置到sheet对应的单元格中
            for(int i=0;i<dataList.size();i++){

                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i+wei);//创建所需的行数

                for(int j=0; j<obj.length; j++){
                    HSSFCell  cell = null;   //设置单元格的数据类型
                    /*if(j == 0){
                        cell = row.createCell(j,HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i+1);
                    }else{
                        cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                        if(!"".equals(obj[j]) && obj[j] != null){
                            cell.setCellValue(obj[j].toString());                       //设置单元格的值
                        }
                    }*/
                    cell = row.createCell(j,HSSFCell.CELL_TYPE_STRING);
                    if(!"".equals(obj[j]) && obj[j] != null){
                        cell.setCellValue(obj[j].toString());                       //设置单元格的值
                    }else{
                        cell.setCellValue("");
                    }
                    cell.setCellStyle(style);                                   //设置单元格样式
                }
            }
            int chang = 0;
            if(xianshi != null){
                CellRangeAddress lastCallRangeAddress = null;
                chang = (int) Math.ceil((double) xianshi.length()/80);
                int hou =0;
                for(int i = 0; i<chang; i++){
                    lastCallRangeAddress = new CellRangeAddress(dataList.size()+6+i,dataList.size()+6+i,0,columnNum-1);//起始行,结束行,起始列,结束列
                    sheet.addMergedRegion(lastCallRangeAddress);
                    HSSFRow lastRow = sheet.createRow(dataList.size()+6+i);                // 创建最后一行
                    HSSFCell cell = lastRow.createCell(0);
                    if((xianshi.length()-80*i)>80){
                        cell.setCellValue(xianshi.substring(i*80,80*(i+1)));
                    }else{
                        cell.setCellValue(xianshi.substring(i*80,xianshi.length()));
                    }
                    HSSFCellStyle alignStyle = workbook.createCellStyle();
                    alignStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    cell.setCellStyle(alignStyle);
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum()-chang; rowNum++) {
                    if(rowNum !=2){
                        HSSFRow currentRow;
                        //当前行未被使用过
                        if (sheet.getRow(rowNum) == null) {
                            currentRow = sheet.createRow(rowNum);
                        } else {
                            currentRow = sheet.getRow(rowNum);
                        }
                        if (currentRow.getCell(colNum) != null) {
                            HSSFCell currentCell = currentRow.getCell(colNum);
                            if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                int length = currentCell.getStringCellValue().getBytes().length;
                                if (columnWidth < length) {
                                    columnWidth = length;
                                }
                            }
                        }
                    }
                }
                if(colNum == 0){
                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);
                }else{
                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);
                }
            }
/*            String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
            String headStr = "attachment; filename=\"" + fileName + "\"";*/
//				        response = getResponse();
//				        response.setContentType("APPLICATION/OCTET-STREAM");
//				        response.setHeader("Content-Disposition", headStr);
//				        OutputStream out = response.getOutputStream();
//				        FileOutputStream out=new FileOutputStream("C:\\test.xls");
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            //本地文件输出流
            /*File fil = new File("E://hello.xls");
            FileOutputStream file = new FileOutputStream(fil);
            workbook.write(file);*/
            // 1.创建一个File类的对象。
            workbook.write(os);
            byte[] content=os.toByteArray();
            is=new ByteArrayInputStream(content);
            String fileName = "enroll.xls";// 文件名
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, "UTF-8"));
            ServletOutputStream output = response.getOutputStream();
            IOUtils.copy(is,output);
            System.out.println("导出成功");
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short)11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }
    /**
     * 列数据信息单元格样式
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

}
