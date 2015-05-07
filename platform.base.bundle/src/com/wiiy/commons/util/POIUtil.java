package com.wiiy.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;

import com.wiiy.commons.util.dto.ExcelSheetDto;

public class POIUtil {
	
	private static final int CELL_WIDTH = (int)(35.7*120);
	private static final int TITLE_HEIGHT_POINT = 26;
	private static final int HEADER_HEIGHT_POINT = 22;
	
	public static <T> void export(String title,String[] columns, List<Object[]> dataList, OutputStream out,List<String> colNames){
		if(columns.length==0) throw new RuntimeException("导出数据列不能为空");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFCellStyle headerCellStyle = getHeaderCellStyle(wb);
		HSSFCellStyle rowCellStyle = getRowCellStyle(wb);
		int xIndex = 0;
		int yIndex = 0;
		if(title!=null){
			HSSFRow titleRow = sheet.createRow(yIndex++);
			titleRow.setHeightInPoints(TITLE_HEIGHT_POINT);
			HSSFCell cell = titleRow.createCell(0);
			cell.setCellValue(title);
			cell.setCellStyle(getTitleCellStyle(wb));
			mergeCell(sheet, 0, 0, 0, columns.length-1);
		}
		HSSFRow headerRow2 = sheet.createRow(yIndex++);
		headerRow2.setHeightInPoints(HEADER_HEIGHT_POINT);
		for (int i = 0; i < 2; i++) {
			HSSFCell cell = headerRow2.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
			mergeCell(sheet, 1, 2, i, i);
		}
		HSSFRow headerRow3 = sheet.createRow(yIndex);
		headerRow3.setHeightInPoints(HEADER_HEIGHT_POINT);
		int k = 2;
		int num = 0;
		for(int i=2;i<(colNames.size()*2+2);i+=2){
			HSSFCell cell = headerRow2.createCell(i);
			cell.setCellValue(colNames.get(num));
			cell.setCellStyle(headerCellStyle);
			mergeCell(sheet, 1, 1, k, k+1);
			k += 2;
			num++;
		}
		
		HSSFRow headerRow = sheet.createRow(yIndex++);
		headerRow.setHeightInPoints(HEADER_HEIGHT_POINT);
		for (int i = 0; i < columns.length; i++) {
			HSSFCell cell = headerRow.createCell(xIndex);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
			sheet.setColumnWidth(xIndex, CELL_WIDTH);
			xIndex++;
		}
		
		if(dataList!=null && dataList.size()>0){
			for (Object[] results : dataList) {
				HSSFRow row = sheet.createRow(yIndex++);
				for (xIndex = 0; xIndex < columns.length; xIndex++) {
					HSSFCell cell = row.createCell(xIndex);
					cell.setCellStyle(rowCellStyle);
					try {
						Object result = results[xIndex];
						if(result==null) continue;
						if(result instanceof String){
							cell.setCellValue(String.valueOf(result));
						} else if(result instanceof Date){
							cell.setCellValue(DateUtil.format((Date)result));
						} else if(result instanceof Number){
							cell.setCellValue(((Number)result).doubleValue());
						} else {
							cell.setCellValue(result.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void export(InputStream is, int rowOffset, List<Object[]> dataList, OutputStream out) throws IOException{
		export(is, 0, rowOffset, dataList, out);
	}
	public static void export(InputStream is,int sheetIndex, int rowOffset, List<Object[]> dataList, OutputStream out) throws IOException{
		POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(sheetIndex);
		int xIndex = 0;
		int yIndex = rowOffset;
		HSSFRow headerRow = sheet.createRow(yIndex++);
		headerRow.setHeightInPoints(HEADER_HEIGHT_POINT);
		if(dataList!=null && dataList.size()>0){
			for (Object[] results : dataList) {
				HSSFRow row = sheet.createRow(yIndex++);
				for (xIndex = 0; xIndex < results.length; xIndex++) {
					HSSFCell cell = row.createCell(xIndex);
					try {
						Object result = results[xIndex];
						if(result==null) continue;
						if(result instanceof String){
							cell.setCellValue(String.valueOf(result));
						} else if(result instanceof Date){
							cell.setCellValue(DateUtil.format((Date)result));
						} else if(result instanceof Number){
							cell.setCellValue(((Number)result).doubleValue());
						} else {
							cell.setCellValue(result.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void export(List<ExcelSheetDto> sheetDtoList, OutputStream out){
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int sheetIndex = 0; sheetIndex < sheetDtoList.size(); sheetIndex++) {
			HSSFSheet sheet = wb.createSheet();
			ExcelSheetDto sheetDto = sheetDtoList.get(sheetIndex);
			wb.setSheetName(sheetIndex, sheetDto.getSheetName());
			String[] columns = sheetDto.getColumns();
			List<Object[]> dataList = sheetDto.getDataList();
			HSSFCellStyle headerCellStyle = getHeaderCellStyle(wb);
			HSSFCellStyle rowCellStyle = getRowCellStyle(wb);
			int xIndex = 0;
			int yIndex = 0;
			HSSFRow headerRow = sheet.createRow(yIndex++);
			headerRow.setHeightInPoints(HEADER_HEIGHT_POINT);
			for (int i = 0; i < columns.length; i++) {
				HSSFCell cell = headerRow.createCell(xIndex);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(headerCellStyle);
				sheet.setColumnWidth(xIndex, CELL_WIDTH);
				xIndex++;
			}
			if(dataList!=null && dataList.size()>0){
				for (Object[] results : dataList) {
					HSSFRow row = sheet.createRow(yIndex++);
					for (xIndex = 0; xIndex < columns.length; xIndex++) {
						HSSFCell cell = row.createCell(xIndex);
						cell.setCellStyle(rowCellStyle);
						try {
							Object result = results[xIndex];
							if(result==null) continue;
							if(result instanceof String){
								cell.setCellValue(String.valueOf(result));
							} else if(result instanceof Date){
								cell.setCellValue(DateUtil.format((Date)result));
							} else if(result instanceof Number){
								cell.setCellValue(((Number)result).doubleValue());
							} else {
								cell.setCellValue(result.toString());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		try {
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将数据导出到输出流中
	 * @param <T>
	 * @param title 标题
	 * @param columns 列名
	 * @param list 数据源 导出在columns中定义过列名的字段
	 * @param out 输入流
	 * @throws IOException 
	 */
	public static <T> void export(String title,String[] columns, List<Object[]> dataList, OutputStream out){
		if(columns.length==0) throw new RuntimeException("导出数据列不能为空");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFCellStyle headerCellStyle = getHeaderCellStyle(wb);
		HSSFCellStyle rowCellStyle = getRowCellStyle(wb);
		int xIndex = 0;
		int yIndex = 0;
		if(title!=null){
			HSSFRow titleRow = sheet.createRow(yIndex++);
			titleRow.setHeightInPoints(TITLE_HEIGHT_POINT);
			HSSFCell cell = titleRow.createCell(0);
			cell.setCellValue(title);
			cell.setCellStyle(getTitleCellStyle(wb));
			mergeCell(sheet, 0, 0, 0, columns.length-1);
		}
		HSSFRow headerRow = sheet.createRow(yIndex++);
		headerRow.setHeightInPoints(HEADER_HEIGHT_POINT);
		for (int i = 0; i < columns.length; i++) {
			HSSFCell cell = headerRow.createCell(xIndex);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
			sheet.setColumnWidth(xIndex, CELL_WIDTH);
			xIndex++;
		}
		if(dataList!=null && dataList.size()>0){
			for (Object[] results : dataList) {
				HSSFRow row = sheet.createRow(yIndex++);
				for (xIndex = 0; xIndex < columns.length; xIndex++) {
					HSSFCell cell = row.createCell(xIndex);
					cell.setCellStyle(rowCellStyle);
					try {
						Object result = results[xIndex];
						if(result==null) continue;
						if(result instanceof String){
							cell.setCellValue(String.valueOf(result));
						} else if(result instanceof Date){
							cell.setCellValue(DateUtil.format((Date)result));
						} else if(result instanceof Number){
							cell.setCellValue(((Number)result).doubleValue());
						} else {
							cell.setCellValue(result.toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static <T> void export(String title,LinkedHashMap<String,String> columns, List<T> list, OutputStream out){
		if(columns.size()==0) throw new RuntimeException("导出数据列不能为空");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFCellStyle headerCellStyle = getHeaderCellStyle(wb);
		HSSFCellStyle rowCellStyle = getRowCellStyle(wb);
		int xIndex = 0;
		int yIndex = 0;
		if(title!=null){
			HSSFRow titleRow = sheet.createRow(yIndex++);
			titleRow.setHeightInPoints(TITLE_HEIGHT_POINT);
			HSSFCell cell = titleRow.createCell(0);
			cell.setCellValue(title);
			cell.setCellStyle(getTitleCellStyle(wb));
			mergeCell(sheet, 0, 0, 0, columns.size()-1);
		}
		HSSFRow headerRow = sheet.createRow(yIndex++);
		headerRow.setHeightInPoints(HEADER_HEIGHT_POINT);
		String[] fields = new String[columns.size()];
		for (String key : columns.keySet()) {
			fields[xIndex] = key;
			HSSFCell cell = headerRow.createCell(xIndex);
			cell.setCellValue(columns.get(key));
			cell.setCellStyle(headerCellStyle);
			sheet.setColumnWidth(xIndex, CELL_WIDTH);
			xIndex++;
		}
		if(list.size()>0){
			Class<?> clazz = list.get(0).getClass();
			Method[] getMethods = new Method[columns.size()];
			xIndex = 0;
			for (String key : columns.keySet()) {
				try {
					getMethods[xIndex++] = clazz.getMethod("get"+StringUtil.convertFirstCharToUpperCase(key.split("\\.")[0]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (T t : list) {
				HSSFRow row = sheet.createRow(yIndex++);
				for (xIndex = 0; xIndex < fields.length; xIndex++) {
					HSSFCell cell = row.createCell(xIndex);
					cell.setCellStyle(rowCellStyle);
					try {
						if(getMethods[xIndex]==null){
							continue;
						}
						Object result = getMethods[xIndex].invoke(t);
						if(result==null) continue;
						if(fields[xIndex].indexOf(".")>0){
							String[] properties = fields[xIndex].split("\\.");
							for (int i = 1; i < properties.length; i++) {
								String methodName = "get"+StringUtil.convertFirstCharToUpperCase(properties[i]);
								result = result.getClass().getMethod(methodName).invoke(result);
								if(result==null){result="";break;}
							}
						}
						if(result instanceof String){
							cell.setCellValue(String.valueOf(result));
						} else if(result instanceof Date){
							cell.setCellValue(DateUtil.format((Date)result));
						} else if(result instanceof Integer){
							cell.setCellValue(((Integer)result));
						} else if(result instanceof Double){
							cell.setCellValue((new DecimalFormat("#.##").format(result)));
						} else if(result instanceof Long){
							cell.setCellValue(((Long)result));
						}else if(result instanceof BigDecimal){
							cell.setCellValue((new DecimalFormat("#.##").format((BigDecimal)result)));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> void export(String title,LinkedHashMap<String,String> columns, List<T> list, OutputStream out,String dateFormat){
		if(columns.size()==0) throw new RuntimeException("导出数据列不能为空");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFCellStyle headerCellStyle = getHeaderCellStyle(wb);
		HSSFCellStyle rowCellStyle = getRowCellStyle(wb);
		int xIndex = 0;
		int yIndex = 0;
		if(title!=null){
			HSSFRow titleRow = sheet.createRow(yIndex++);
			titleRow.setHeightInPoints(TITLE_HEIGHT_POINT);
			HSSFCell cell = titleRow.createCell(0);
			cell.setCellValue(title);
			cell.setCellStyle(getTitleCellStyle(wb));
			mergeCell(sheet, 0, 0, 0, columns.size()-1);
		}
		HSSFRow headerRow = sheet.createRow(yIndex++);
		headerRow.setHeightInPoints(HEADER_HEIGHT_POINT);
		String[] fields = new String[columns.size()];
		for (String key : columns.keySet()) {
			fields[xIndex] = key;
			HSSFCell cell = headerRow.createCell(xIndex);
			cell.setCellValue(columns.get(key));
			cell.setCellStyle(headerCellStyle);
			sheet.setColumnWidth(xIndex, CELL_WIDTH);
			xIndex++;
		}
		if(list.size()>0){
			Class<?> clazz = list.get(0).getClass();
			Method[] getMethods = new Method[columns.size()];
			xIndex = 0;
			for (String key : columns.keySet()) {
				try {
					getMethods[xIndex++] = clazz.getMethod("get"+StringUtil.convertFirstCharToUpperCase(key.split("\\.")[0]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (T t : list) {
				HSSFRow row = sheet.createRow(yIndex++);
				for (xIndex = 0; xIndex < fields.length; xIndex++) {
					HSSFCell cell = row.createCell(xIndex);
					cell.setCellStyle(rowCellStyle);
					try {
						Object result = getMethods[xIndex].invoke(t);
						if(result==null) continue;
						if(fields[xIndex].indexOf(".")>0){
							String[] properties = fields[xIndex].split("\\.");
							for (int i = 1; i < properties.length; i++) {
								String methodName = "get"+StringUtil.convertFirstCharToUpperCase(properties[i]);
								result = result.getClass().getMethod(methodName).invoke(result);
								if(result==null){result="";break;}
							}
						}
						if(result instanceof String){
							cell.setCellValue(String.valueOf(result));
						} else if(result instanceof Date){
							SimpleDateFormat smt = new SimpleDateFormat(dateFormat);
							cell.setCellValue(smt.format((Date)result));
						} else if(result instanceof Integer){
							cell.setCellValue(((Integer)result));
						} else if(result instanceof Double){
							cell.setCellValue((new DecimalFormat("#.##").format(result)));
						} else if(result instanceof Long){
							cell.setCellValue(((Long)result));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		try {
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 标题样式
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle getTitleCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(getFont(wb,"宋体",(short) 16,HSSFFont.BOLDWEIGHT_BOLD));
		return style;
	}
	/**
	 * 表头样式
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle getHeaderCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(getFont(wb,"宋体",(short) 14,HSSFFont.BOLDWEIGHT_BOLD));
		return style;
	}
	/**
	 * 表头样式2
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle getHeaderCellStyle2(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(getFont(wb,"宋体",(short) 12,HSSFFont.BOLDWEIGHT_NORMAL));
		return style;
	}
	/**
	 * 数据行样式
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle getRowCellStyle(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(getFont(wb,"宋体",(short) 12,HSSFFont.BOLDWEIGHT_NORMAL));
		return style;
	}
	/**
	 * 数据行样式2
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle getRowCellStyle2(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(getFont(wb,"宋体",(short) 14,HSSFFont.BOLDWEIGHT_BOLD));
		return style;
	}
	/**
	 * 生成单元格字体
	 * @param wb
	 * @param fontName
	 * @param point
	 * @param weight
	 * @return
	 */
	private static HSSFFont getFont(HSSFWorkbook wb,String fontName,short point,short weight){
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints(point);
		font.setBoldweight(weight);
		font.setFontName(fontName);
		return font;
	}
	/**
	 * 合并单元格
	 * @param 	sheet		HSSFSheet
	 * @param 	firstRow	起始行
	 * @param 	lastRow		结束行
	 * @param 	firstColumn	起始列
	 * @param 	lastColumn	结束列
	 * @return				合并区域号码
	 */
	private static int mergeCell(HSSFSheet sheet,int firstRow,int lastRow,int firstColumn,int lastColumn){
		return sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,firstColumn,lastColumn));	
	}
}
