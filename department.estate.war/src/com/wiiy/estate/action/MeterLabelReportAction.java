package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.estate.entity.MeterLabelReport;
import com.wiiy.estate.service.MeterLabelReportService;

/**
 * @author my
 */
public class MeterLabelReportAction extends JqGridBaseAction<MeterLabelReport>{
	
	private MeterLabelReportService meterLabelReportService;
	private Result result;
	private MeterLabelReport meterLabelReport;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = meterLabelReportService.save(meterLabelReport);
		return JSON;
	}
	
	public String view(){
		result = meterLabelReportService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = meterLabelReportService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		MeterLabelReport dbBean = meterLabelReportService.getBeanById(meterLabelReport.getId()).getValue();
		BeanUtil.copyProperties(meterLabelReport, dbBean);
		result = meterLabelReportService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = meterLabelReportService.deleteById(id);
		} else if(ids!=null){
			result = meterLabelReportService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(MeterLabelReport.class));
	}
	
	@Override
	protected List<MeterLabelReport> getListByFilter(Filter fitler) {
		return meterLabelReportService.getListByFilter(fitler).getValue();
	}
	
	
	public MeterLabelReport getMeterLabelReport() {
		return meterLabelReport;
	}

	public void setMeterLabelReport(MeterLabelReport meterLabelReport) {
		this.meterLabelReport = meterLabelReport;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setMeterLabelReportService(MeterLabelReportService meterLabelReportService) {
		this.meterLabelReportService = meterLabelReportService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
