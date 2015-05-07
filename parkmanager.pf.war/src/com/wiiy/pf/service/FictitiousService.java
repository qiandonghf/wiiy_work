package com.wiiy.pf.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.entity.Fictitious;

/**
 * @author my
 */
public interface FictitiousService extends IService<Fictitious> {

	Result getInvestment(Long id);

	Result save(Fictitious fictitious, Map<String, String[]> roomRent,
			Map<String, String[]> billPlanRent, Map<String, String[]> deposit);

	List<Object> getListBySql(String sql);

	void print(Long id, String taskId, ByteArrayOutputStream out);
}
