package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.WorkReportConfig;

public interface WorkReportConfigService extends IService<WorkReportConfig>{
	Result  configReporter(String ids);
}
