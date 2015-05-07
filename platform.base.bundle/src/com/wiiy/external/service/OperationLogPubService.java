package com.wiiy.external.service;

import com.wiiy.hibernate.Result;
/**
 * 
 * @author Aswan
 *操作日志公共接口
 */
public interface OperationLogPubService {
	
	/**
	 * 操作日志
	 * @param msg 日志内容
	 * @return
	 */
	public Result logOP(String msg);
	/**
	 * 登录日志
	 * @param msg 日志内容
	 * @return
	 */
	public Result logLogin(String msg);
	/**
	 * 登出日志
	 * @param msg 日志内容
	 * @return
	 */
	public Result logLogout(String msg);
	
	public void initBundleName(String bundleName);
	
}
