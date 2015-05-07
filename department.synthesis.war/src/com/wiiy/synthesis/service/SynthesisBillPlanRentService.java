package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SynthesisBillPlanRent;

/**
 * @author my
 */
public interface SynthesisBillPlanRentService extends IService<SynthesisBillPlanRent> {
	Result<String> generateCode();
}
