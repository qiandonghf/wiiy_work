package com.wiiy.sale.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.entity.ContectInfo;

/**
 * @author my
 */
public interface ContectInfoService extends IService<ContectInfo> {
	public List<Integer> contects();

	public Result changeState(Long id);
	
}
