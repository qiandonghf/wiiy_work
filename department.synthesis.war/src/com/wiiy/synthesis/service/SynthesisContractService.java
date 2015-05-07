package com.wiiy.synthesis.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SynthesisContract;

/**
 * @author my
 */
public interface SynthesisContractService extends IService<SynthesisContract> {
	Result<String> generateCode();

	List<Object> getListBySql(String sql);
}
