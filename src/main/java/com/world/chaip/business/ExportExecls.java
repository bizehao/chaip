package com.world.chaip.business;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author 毕泽浩
 * @Description: 导出excel
 * @time 2019/1/10 16:28
 */
public class ExportExecls {

    public static final int WEIGHT = 25000; //A4纸宽度
    public static final int HEIGHT = 37000; //A4纸高度

    private HttpServletResponse response;
    private String title; //表名
    private List<Object[]> datas; //表格数据
    private Direction direction; //方向
    private String time; //时间
    private int rows; //每页多少行行数
    private int pages; //页数
    private Sheet sheet; //当前sheet
    private ColumnAndHead columnAndHead; //列头和列
    private int topRows;
    private HSSFWorkbook sxssfWorkbook;
    private int cols; //列的数目
    private String lastInfo; //最后的信息

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }


    private int fontSize;//字体大小


    public enum Direction {
        TRANSVERSE(true), //横
        VERTICAL(false); //竖

        private boolean state;

        Direction(boolean state) {
            this.state = state;
        }
    }

    public ExportExecls(HttpServletResponse response, String title, List<Object[]> datas, String time, int rows, int topRows, int cols, Direction direction) {
        this.response = response;
        this.title = title;
        this.datas = datas;
        this.direction = direction;
        this.time = time;
        this.rows = rows - topRows-1;
        this.topRows = topRows;
        this.cols = cols;
        this.sxssfWorkbook = new HSSFWorkbook();
        if (datas.size() % rows == 0) {
            this.pages = datas.size() / rows;
        } else {
            this.pages = datas.size() / rows + 1;
        }
    }

    /**
     * 分页处理数据
     */
    public void export(ColumnAndHead columnAndHead) {
        System.out.println(pages);
        this.columnAndHead = columnAndHead;
        List<Object[]> pageDatas;
        try {
            OutputStream out = response.getOutputStream();
            if (pages == 0) {
                this.sheet = sxssfWorkbook.createSheet(title);
                //标题
                CellRangeAddress titleAddress = new CellRangeAddress(0, 1, 0, cols - 1);
                sheet.addMergedRegion(titleAddress);
                Row titleRow = sheet.createRow(0);
                Cell cell = titleRow.createCell(0);
                cell.setCellValue(title);
                CellStyle titleStyle = this.getTitleStyle(sxssfWorkbook);
                cell.setCellStyle(titleStyle);
                //时间
                CellRangeAddress timeAddress = new CellRangeAddress(2, 2, 0, cols - 1);//起始行,结束行,起始列,结束列
                sheet.addMergedRegion(timeAddress);
                Row rowRowAutograph = sheet.createRow(2);
                Cell cellAutograph = rowRowAutograph.createCell(0);
                cellAutograph.setCellValue(time);
                CellStyle timeStyle = this.getTimeStyle(sxssfWorkbook);
                cellAutograph.setCellStyle(timeStyle);

                //页数
                Row colTitleRow = sheet.createRow(rows + topRows);
                Cell cellLast = colTitleRow.createCell(0);
                cellLast.setCellValue("共1页/第1页");
                cellLast.setCellStyle(timeStyle);
                cellLast.setCellStyle(getTitleStyle(sheet.getWorkbook()));
                CellRangeAddress timeAddress1 = new CellRangeAddress(rows + topRows, rows + topRows, 0, cols - 1);
                sheet.addMergedRegion(timeAddress1);
                //表头
                this.columnAndHead.colHeadHandler(sheet);
                sheet.getPrintSetup().setLandscape(direction.state); //设置打印横向
            } else {
                for (int i = 0; i < pages; i++) {
                    this.sheet = sxssfWorkbook.createSheet(title + i);
                    sheet.setHorizontallyCenter(true); //水平居中
                    sheet.getPrintSetup().setPaperSize(PrintSetup.A4_PAPERSIZE);
                    sheet.setMargin(HSSFSheet.TopMargin, 0.05);
                    sheet.setMargin(HSSFSheet.BottomMargin, 0.05);
                    sheet.setMargin(HSSFSheet.LeftMargin, 0.05);
                    sheet.setMargin(HSSFSheet.RightMargin, 0.05);
                    //sheet.setVerticallyCenter(true); //垂直居中
                    //标题
                    CellRangeAddress titleAddress = new CellRangeAddress(0, 1, 0, cols - 1);
                    sheet.addMergedRegion(titleAddress);
                    Row titleRow = sheet.createRow(0);
                    Cell cell = titleRow.createCell(0);
                    cell.setCellValue(title);
                    CellStyle titleStyle = this.getTitleStyle(sxssfWorkbook);
                    cell.setCellStyle(titleStyle);
                    //时间
                    CellRangeAddress timeAddress = new CellRangeAddress(2, 2, 0, cols - 1);//起始行,结束行,起始列,结束列
                    sheet.addMergedRegion(timeAddress);
                    Row rowRowAutograph = sheet.createRow(2);
                    Cell cellAutograph = rowRowAutograph.createCell(0);
                    cellAutograph.setCellValue(time);
                    CellStyle timeStyle = this.getTimeStyle(sxssfWorkbook);
                    cellAutograph.setCellStyle(timeStyle);
                    //表头
                   // this.columnAndHead.colHeadHandler(sheet);

                    this.columnAndHead.colHeadHandler(sheet);
                    CellStyle style = getContentStyle(sxssfWorkbook); //内容样式
                    if (i == pages - 1) {
                        pageDatas = datas.subList(i * rows, datas.size());
                        excelHandler(pageDatas, style);
                    } else {
                        pageDatas = datas.subList(i * rows, (i + 1) * rows);
                        excelHandler(pageDatas, style);
                    }
                    if (i == pages - 1 && lastInfo != null) {
                        int lastno = lastInfo.length() / 40 + (lastInfo.length() % 40 == 0 ? 0 : 1);
                        Row lastRow = sheet.createRow(pageDatas.size() + topRows);
                        Cell lastCell1 = lastRow.createCell(0);
                        CellRangeAddress lastAddress = new CellRangeAddress(pageDatas.size() + topRows, pageDatas.size() + topRows + lastno, 0, cols - 1);
                        sheet.addMergedRegionUnsafe(lastAddress);
                        lastCell1.setCellValue(lastInfo);
                        CellStyle lastRowStyle = getLastRowStyle(sheet.getWorkbook());
                        lastCell1.setCellStyle(lastRowStyle);
                    }
                    //页数
                    Row colTitleRow = sheet.createRow(rows + topRows);
                    Cell cellLast = colTitleRow.createCell(0);
                    cellLast.setCellValue("共" + pages + "页/第" + (i+1) + "页");
                    cellLast.setCellStyle(timeStyle);
                    cellLast.setCellStyle(getTitleStyle(sheet.getWorkbook()));
                    CellRangeAddress timeAddress1 = new CellRangeAddress(rows + topRows, rows + topRows, 0, cols - 1);
                    sheet.addMergedRegion(timeAddress1);

                    setCellRangeAddressStyle(sheet, titleAddress); //标题栏(合并单元格)样式
                    setCellRangeAddressStyle(sheet, timeAddress); //时间(合并单元格)样式
                    sheet.getPrintSetup().setLandscape(direction.state); //设置打印横向
                }


            }
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(title + ".xls", "UTF-8"));
            sxssfWorkbook.write(out);
            // 关闭输出流
            out.close();
            System.out.println("导表成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理每页数据
     *
     * @param datas
     */
    private void excelHandler(List<Object[]> datas, CellStyle style) {
        Row row;
        Cell cell;
        for (int i = 0; i < datas.size(); i++) {
            row = sheet.createRow(i + topRows);
            Object[] objects = datas.get(i);
            for (int j = 0; j < objects.length; j++) {
                cell = row.createCell(j);
                if (objects[j] instanceof String) {
                    cell.setCellValue((String) objects[j]);
                } else if (objects[j] instanceof Integer) {
                    cell.setCellValue((Integer) objects[j]);
                } else if (objects[j] instanceof Double) {
                    cell.setCellValue((Double) objects[j]);
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(style);
            }
        }
		/*for (int i=0; i<datas.get(0).length;i++){
			sheet.autoSizeColumn(i, true);
		}*/
        /*setSizeColumn(sheet,datas.get(0).length);*/
    }

    public interface ColumnAndHead {
        void colHeadHandler(Sheet sheet); //处理表头
    }

    public void setLastInfo(String lastInfo) {
        this.lastInfo = lastInfo;
    }

    /**
     * 标题头单元格样式
     */
    private CellStyle getTitleStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(true);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 时间信息行单元格样式
     */
    private CellStyle getTimeStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        //设置字体名字
        font.setFontName("Courier New");
        //设置字体大小
        if (fontSize != 0) {
            font.setFontHeightInPoints((short) fontSize);
        } else {
            font.setFontHeightInPoints((short) 10);
        }
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(true);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.LEFT);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 列数据信息单元格样式
     */
    public CellStyle getContentStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        //设置字体名字
        font.setFontName("Courier New");
        //设置字体大小
        if (fontSize != 0) {
            font.setFontHeightInPoints((short) 8);
        } else {
            font.setFontHeightInPoints((short) 8);
        }
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //自动换行
        style.setWrapText(true);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    /**
     * 最后一行的样式
     */
    private CellStyle getLastRowStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        //设置字体大小
        if (fontSize != 0) {
            font.setFontHeightInPoints((short) 8);
        } else {
            font.setFontHeightInPoints((short) 8);
        }
        //字体加粗
        // font.setBold(true);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(true);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);


        return style;
    }

    /**
     * 合并单元格边框处理
     *
     * @param sheet
     * @param addresses
     */
    private void setCellRangeAddressStyle(Sheet sheet, CellRangeAddress addresses) {
        //合并单元格的样式
        RegionUtil.setBorderBottom(BorderStyle.THIN, addresses, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, addresses, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, addresses, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, addresses, sheet);
        RegionUtil.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex(), addresses, sheet);
        RegionUtil.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex(), addresses, sheet);
        RegionUtil.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex(), addresses, sheet);
        RegionUtil.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex(), addresses, sheet);
    }

    // 自适应宽度(中文支持)
    private void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }
}
