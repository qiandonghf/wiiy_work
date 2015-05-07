package com.wiiy.engineering.service;

import com.wiiy.commons.service.IService;
import com.wiiy.engineering.entity.EngineeringProject;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface EngineeringProjectService extends IService<EngineeringProject> {
	Result<String> generateCode();
}
