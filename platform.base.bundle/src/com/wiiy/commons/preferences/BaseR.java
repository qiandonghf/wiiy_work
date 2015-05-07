package com.wiiy.commons.preferences;

public class BaseR {
	
	public static final String DEFAULT_FK_MESSAGE = "有相关联数据未删除";
	
	protected static final String SAVE = "保存";
	protected static final String LOAD = "加载";
	protected static final String UPDATE = "更新";
	protected static final String DELETE = "删除";
	protected static final String SELECT = "查询";
	
	protected static final String CONFIG = "配置";
	
	protected static final String SUCCESS = "成功";
	protected static final String FAILURE = "失败";

	protected static final String ERROR = "错误";
	protected static final String WARNING = "警告";
	
	public static String getUKMessage(String fieldDescription){
		return fieldDescription+"已经存在!";
	}
	
	public static String getFKMessage(String classDescription){
		return classDescription+"中有相关联数据 请先删除!";
	}
	
}
