package com.wiiy.synthesis.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.dto.WorkReportDto;
import com.wiiy.synthesis.entity.WorkReport;

public interface WorkReportService extends IService<WorkReport> {
	public Result<List<WorkReport>> weekMonthReportList();
	public Result<WorkReportDto> getWorkReportlList(Integer week,Integer month,Integer year);
}
