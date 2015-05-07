package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SynthesisPlan;

/**
 * @author my
 */
public interface SynthesisPlanService extends IService<SynthesisPlan> {
	Result<String> generateCode();
}
