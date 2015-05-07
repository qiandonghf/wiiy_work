package com.wiiy.synthesis.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SynthesisBillRent;

/**
 * @author my
 */
public interface SynthesisBillRentService extends IService<SynthesisBillRent> {
	Result<List> getResultBySql(String sql);
}
