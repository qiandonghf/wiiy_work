package com.wiiy.synthesis.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.WorkDayReport;

public interface WorkDayReportService extends IService<WorkDayReport>{


	List<WorkDayReport> getListByDepIds(String year, String month, String week,
			String depIds);

	Result getWorkReportlList(Integer myWeek, Integer myMonth, Integer year);

}
