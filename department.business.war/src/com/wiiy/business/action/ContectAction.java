package com.wiiy.business.action;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.business.dto.CustomerContactDto;
import com.wiiy.business.entity.Contect;
import com.wiiy.business.service.ContectService;
import com.wiiy.business.service.CustomerService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class ContectAction extends JqGridBaseAction<Contect>{
	
	private ContectService contectService;
	private CustomerService customerService;
	private Result result;
	private Contect contect;
	private Long id;
	private String ids;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	
	public String listByCustomerContact() {
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
		Filter filter = new Filter(Contect.class);
		List<CustomerContactDto> dtoList=new ArrayList<CustomerContactDto>();
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
			filter.between("createTime", startTime, endTime);
		}
		List<Contect> contectList = contectService.getListByFilter(filter).getValue();
		for (Contect c : contectList) {
			CustomerContactDto dto=new CustomerContactDto();
			dto.setCustomerName(c.getCustomer().getName());
			dto.setName(c.getName());
			dto.setGender(c.getGender());
			dto.setCellPhone(c.getMobile());
			dto.setTelePhone(c.getPhone());
			dto.setJob(c.getPosition());
			dto.setEmail(c.getEmail());
			dto.setQq(c.getQq());
			dto.setZipCode(c.getZipcode());
			dto.setHomeAddress(c.getHomeAddr());
			dto.setMemo(c.getMemo());
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "customerContact";
	}
	public String importCard(){
		result = contectService.importCard(ids);
		return JSON;
	}
	
	
	public String select(){
		return SELECT;
	}
	
	public String loadContectsByCustomerId(){
		result = contectService.getListByFilter(new Filter(Contect.class).include("id").include("name").eq("customerId", id));
		return JSON;
	}
	
	public String addByCustomerId(){
		result = customerService.getBeanById(id);
		return "addByCustomerId";
	}
	
	public String save(){
		result = contectService.save(contect);
		return JSON;
	}
	
	public String view(){
		result = contectService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contectService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		result = contectService.update(contect);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = contectService.deleteById(id);
		} else if(ids!=null){
			result = contectService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Contect.class);
		filter.createAlias("customer", "customer");
		return refresh(filter);
	}
	
	@Override
	protected List<Contect> getListByFilter(Filter fitler) {
		return contectService.getListByFilter(fitler).getValue();
	}
	
	
	public Contect getContect() {
		return contect;
	}

	public void setContect(Contect contect) {
		this.contect = contect;
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

	public void setContectService(ContectService contectService) {
		this.contectService = contectService;
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

}
