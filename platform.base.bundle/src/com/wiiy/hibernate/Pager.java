package com.wiiy.hibernate;

import java.io.Serializable;

/**
 * 页面信息封装类
 * </br>记录 总页数 总记录数 单页记录数 页码 信息
 * @author my
 *
 */
public class Pager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int rows;
	private int page;
	private int records;
	
	/**
	 * 
	 * @param page 页码
	 * @param rows 单页记录数
	 */
	public Pager(int page, int rows) {
		this.page = page;
		this.rows = rows;
	}
	public Pager(int page) {
		this.page=page;
		this.rows=15;
	}
	/**
	 * 总页数(根据总记录数与单页记录数计算出的结果)
	 * </br>计算式子为 (总记录数 - 1) / 单页记录数 + 1
	 * @return 总页数
	 */
	public int getTotal() {
		if(records==0)return 0;
		if(rows==0)return 0;
		return (records - 1) / rows + 1;
	}
	/**
	 * 单页记录数(页面大小)
	 * 
	 * @return 单页记录数
	 */
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 * 页码 即 第几页 (从1开始)
	 * 
	 * @return 页码
	 */
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}

}
