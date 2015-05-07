package com.wiiy.business.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.wiiy.business.entity.InvestmentContectInfo;
import com.wiiy.business.preferences.enums.ContectInfoEnum;
import com.wiiy.business.preferences.enums.LevelEnum;
import com.wiiy.commons.service.IService;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface InvestmentContectInfoService extends IService<InvestmentContectInfo> {
	@SuppressWarnings("rawtypes")
	public Result changeState(Long id);
	
	Calendar calendar = Calendar.getInstance();
	
	Map<String, Integer> contectInfos(Boolean type);

//	List<Integer> contects();
	
	public Integer getRowCount(Filter filter);
	

}
