package com.wiiy.commons.util.dto;

import java.util.List;

public class ExcelSheetDto {
	
	private String sheetName;
	private String[] columns;
	private List<Object[]> dataList;
	
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String[] getColumns() {
		return columns;
	}
	public void setColumns(String[] columns) {
		this.columns = columns;
	}
	public List<Object[]> getDataList() {
		return dataList;
	}
	public void setDataList(List<Object[]> dataList) {
		this.dataList = dataList;
	}
}
