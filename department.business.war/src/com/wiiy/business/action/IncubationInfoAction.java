package com.wiiy.business.action;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.business.dto.IncubateFormDto;
import com.wiiy.business.entity.IncubationInfo;
import com.wiiy.business.service.IncubationInfoService;

/**
 * @author my
 */
public class IncubationInfoAction extends JqGridBaseAction<IncubationInfo>{
	
	private IncubationInfoService incubationInfoService;
	private Result result;
	private IncubationInfo incubationInfo;
	private Long id;
	private String ids;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	
	public String listByCustomerIncubate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String time = null;
		String year = sdf.format(date);
		for (int i = 1; i > -4; i--) {
			if(years==null){
				years = new ArrayList<Integer>();
			}
			years.add(Integer.parseInt(year)+i);
		}
		List<IncubateFormDto> dtoList = new ArrayList<IncubateFormDto>();
		Filter filter =new Filter(IncubationInfo.class);
		boolean flag = false;
		if(yearNo!=null&&yearNo.length()>0){
			time = yearNo;
			if(monthNo!=null&&monthNo.length()>0){
				time = time+"-"+monthNo;
				flag = true;
			}else{
				monthNo = "0";
			}
		}
		if(time!=null){
			Date startTime = null;
			Date endTime = null;
			Date d = null;
			try {
				int calendarIndex = Calendar.YEAR;
				if(flag){
					d = new SimpleDateFormat("yyyy-MM").parse(time);
					calendarIndex = Calendar.MONTH;
				}else{
					d = new SimpleDateFormat("yyyy").parse(time);
				}
				startTime = CalendarUtil.getEarliest(d, calendarIndex);
				endTime = CalendarUtil.getLatest(d, calendarIndex);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			filter.between("incubationStartDate", startTime, endTime);
		}
		List<IncubationInfo> incubateList=incubationInfoService.getListByFilter(filter).getValue();
		for (IncubationInfo list : incubateList) {
			if(list.getStatusName() != null && !"".equals(list.getStatusName())){
				IncubateFormDto dto = new IncubateFormDto();
				dto.setName(list.getCustomer().getName());
				dto.setStatusName(list.getStatusName());
				dto.setIncubatorAreas(list.getIncubatorAreas());
				dto.setIncubationStartDate(list.getIncubationStartDate());
				dto.setIncubationEndDate(list.getIncubationEndDate());
				dto.setGraduationDate(list.getGraduationDate());
				dto.setHighTechEnterprise(list.getHighTechEnterprise());
				dto.setTutorSupport(list.getTutorSupport());
				dto.setUndergraduateEnterprise(list.getUndergraduateEnterprise());
				dto.setOverseaEnterprise(list.getOverseaEnterprise());
				dtoList.add(dto);
			}
		}
		result = Result.value(dtoList);
		return "customerIncubate";
	}
	public String save(){
		result = incubationInfoService.save(incubationInfo);
		return JSON;
	}
	
	public String view(){
		result = incubationInfoService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = incubationInfoService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		result = incubationInfoService.update(incubationInfo);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = incubationInfoService.deleteById(id);
		} else if(ids!=null){
			result = incubationInfoService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(IncubationInfo.class));
	}
	
	@Override
	protected List<IncubationInfo> getListByFilter(Filter fitler) {
		return incubationInfoService.getListByFilter(fitler).getValue();
	}
	
	
	public IncubationInfo getIncubationInfo() {
		return incubationInfo;
	}

	public void setIncubationInfo(IncubationInfo incubationInfo) {
		this.incubationInfo = incubationInfo;
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

	public void setIncubationInfoService(IncubationInfoService incubationInfoService) {
		this.incubationInfoService = incubationInfoService;
	}
	
	public Result getResult() {
		return result;
	}
	public List<Integer> getYears() {
		return years;
	}
	public void setYears(List<Integer> years) {
		this.years = years;
	}
	public String getYearNo() {
		return yearNo;
	}
	public void setYearNo(String yearNo) {
		this.yearNo = yearNo;
	}
	public String getMonthNo() {
		return monthNo;
	}
	public void setMonthNo(String monthNo) {
		this.monthNo = monthNo;
	}
	
}
