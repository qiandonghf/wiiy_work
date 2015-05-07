package com.wiiy.engineering.service;

import com.wiiy.commons.service.IService;
import com.wiiy.engineering.entity.EngineeringPlan;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface EngineeringPlanService extends IService<EngineeringPlan> {
	Result<String> generateCode();
}
