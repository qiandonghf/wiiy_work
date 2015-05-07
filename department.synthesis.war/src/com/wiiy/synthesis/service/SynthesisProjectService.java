package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.SynthesisProject;

/**
 * @author my
 */
public interface SynthesisProjectService extends IService<SynthesisProject> {
	Result<String> generateCode();
}
