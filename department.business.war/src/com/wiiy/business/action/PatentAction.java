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
import com.wiiy.business.dto.PatentDto;
import com.wiiy.business.entity.Patent;
import com.wiiy.business.service.CustomerService;
import com.wiiy.business.service.PatentService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class PatentAction extends JqGridBaseAction<Patent>{
	
	private PatentService patentService;
	private CustomerService customerService;
	private Result result;
	private Patent patent;
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
		List<PatentDto> dtoList = new ArrayList<PatentDto>();
		Filter filter = new Filter (Patent.class);
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
			filter.between("buyTime", startTime, endTime);
		}
		List<Patent> list = patentService.getListByFilter(filter).getValue();
		for (Patent p : list) {
			PatentDto dto = new PatentDto();
			dto.setCustomer(p.getCustomer().getName());
			dto.setName(p.getName());
			dto.setSerialNo(p.getSerialNo());
			dto.setType(p.getType());
			dto.setBuyTime(p.getBuyTime());
			dto.setState(p.getState());
			dto.setAppler(p.getAppler());
			dto.setApplyTime(p.getApplyTime());
			dto.setSource(p.getSource());
			dto.setExpireTime(p.getExpireTime());
			dto.setPub(p.getPub());
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "patentList";
	}
	public String addByCustomerId(){
		result = customerService.getBeanById(id);
		return "addByCustomerId";
	}
	
	public String save(){
		if(("").equals(patent.getTypeId())){
			patent.setTypeId(null);
		}
		if(("").equals(patent.getSourceId())){
			patent.setSourceId(null);
		}
		result = patentService.save(patent);
		return JSON;
	}
	
	public String view(){
		result = patentService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = patentService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Patent dbBean = patentService.getBeanById(patent.getId()).getValue();
		BeanUtil.copyProperties(patent, dbBean);
		if(("").equals(dbBean.getTypeId())){
			dbBean.setTypeId(null);
		}
		if(("").equals(dbBean.getSourceId())){
			dbBean.setSourceId(null);
		}
		result = patentService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = patentService.deleteById(id);
		} else if(ids!=null){
			result = patentService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Patent.class);
		filter.createAlias("customer", "customer");
		return refresh(filter);
	}
	
	@Override
	protected List<Patent> getListByFilter(Filter fitler) {
		return patentService.getListByFilter(fitler).getValue();
	}
	
	
	public Patent getPatent() {
		return patent;
	}

	public void setPatent(Patent patent) {
		this.patent = patent;
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

	public void setPatentService(PatentService patentService) {
		this.patentService = patentService;
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
