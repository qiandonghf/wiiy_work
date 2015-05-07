package com.wiiy.synthesis.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.Appraisal;
import com.wiiy.synthesis.entity.AppraisalAtt;

/**
 * @author my
 */
public interface AppraisalService extends IService<Appraisal> {

	Result save(Appraisal appraisal, List<AppraisalAtt> list);

	Result update(Appraisal dbBean, List<AppraisalAtt> list);

	List<Object> getListBySql(String sql);
	
	
}
