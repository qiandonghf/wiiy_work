package com.wiiy.business.action;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.business.dto.CopyrightDto;
import com.wiiy.business.entity.Copyright;
import com.wiiy.business.service.CopyrightService;
import com.wiiy.business.service.CustomerService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class CopyrightAction extends JqGridBaseAction<Copyright>{
	
	private CopyrightService copyrightService;
	private CustomerService customerService;
	private Result result;
	private Copyright copyright;
	private Long id;
	private String ids;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	private Integer flag;
	
	public String listByKnowledge(){
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
		List<CopyrightDto> dtoList = new ArrayList<CopyrightDto>();
		Filter filter = new Filter (Copyright.class);
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
			filter.between("effectivetime", startTime, endTime);
		}
		List<Copyright> list = copyrightService.getListByFilter(filter).getValue();
		for (Copyright p : list) {
			CopyrightDto dto = new CopyrightDto();
			dto.setCustomer(p.getCustomer().getName());
			dto.setName(p.getName());
			dto.setSerialNo(p.getSerialNo());
			dto.setApplyTime(p.getApplyTime());
			dto.setAppler(p.getAppler());
			dto.setType(p.getType());
			dto.setEffectivetime(p.getEffectivetime());
			dto.setExpireTime(p.getExpireTime());
			dto.setPub(p.getPub());
			dto.setSummery(p.getSummery());
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "copyrightList";
	}
	
	public String addByCustomerId(){
		result = customerService.getBeanById(id);
		return "addByCustomerId";
	}
	
	public String save(){
		result = copyrightService.save(copyright);
		return JSON;
	}
	
	public String view(){
		result = copyrightService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = copyrightService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Copyright dbBean = copyrightService.getBeanById(copyright.getId()).getValue();
		BeanUtil.copyProperties(copyright, dbBean);
		result = copyrightService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = copyrightService.deleteById(id);
		} else if(ids!=null){
			result = copyrightService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Copyright.class);
		filter.createAlias("customer", "customer");
		return refresh(filter);
	}
	
	@Override
	protected List<Copyright> getListByFilter(Filter fitler) {
		return copyrightService.getListByFilter(fitler).getValue();
	}
	
	
	public Copyright getCopyright() {
		return copyright;
	}

	public void setCopyright(Copyright copyright) {
		this.copyright = copyright;
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

	public void setCopyrightService(CopyrightService copyrightService) {
		this.copyrightService = copyrightService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	
}
