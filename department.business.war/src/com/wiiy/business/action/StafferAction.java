package com.wiiy.business.action;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.business.dto.TalentsDto;
import com.wiiy.business.entity.Staffer;
import com.wiiy.business.service.CustomerService;
import com.wiiy.business.service.StafferService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class StafferAction extends JqGridBaseAction<Staffer>{
	
	private StafferService stafferService;
	private CustomerService customerService;
	private Result result;
	private Staffer staffer;
	private Long id;
	private String ids;
	private String isManager;
	private String isLegal;
	private Long customerId;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	
	public String listByCustomerTalents(){
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
		List<TalentsDto> dtoList = new ArrayList<TalentsDto>();
		Filter filter = new Filter (Staffer.class);
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
		List<Staffer> staffer = stafferService.getListByFilter(filter).getValue();
		for (Staffer s : staffer) {
			TalentsDto dto = new TalentsDto();
			dto.setCustomerName(s.getCustomer().getName());
			dto.setName(s.getName());
			dto.setGender(s.getGender());
			dto.setPolitical(s.getPolitical());
			dto.setPosition(s.getPosition());
			dto.setDegree(s.getDegree());
			dto.setEducation(s.getEducation());
			dto.setStudySchool(s.getStudySchool());
			dto.setAbroadCountry(s.getAbroadCountry());
			dto.setCellPhone(s.getPhone());
			dto.setEmail(s.getEmail());
			dto.setStockHolder(s.getStockHolder());
			dto.setManager(s.getManager());
			dto.setLegal(s.getLegal());
			dto.setPub(s.getPub());
			dto.setMemo(s.getMemo());
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "customerStaffer";
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	
	public String check(){
		Filter filter = new Filter(Staffer.class);
		filter.eq("customerId",customerId).or(Filter.Eq("manager", BooleanEnum.YES),Filter.Eq("legal", BooleanEnum.YES));
		if(id!=null){
			filter.ne("id", id);
		}
		List<Staffer> staffers = stafferService.getListByFilter(filter).getValue();
		result = Result.success("");
		if(staffers != null && staffers.size()>0){
			for (Staffer staffer : staffers) {
				if(staffer.getManager() == BooleanEnum.YES && isManager.equals("YES")){
					result = Result.failure("该企业已存在总经理,是否替换");
				}else if(staffer.getLegal() == BooleanEnum.YES && isLegal.equals("YES")){
					result = Result.failure("该企业已存在法人,是否替换");
				}
			}
		}
		return JSON;
	}
	
	public String importCard(){
		result = stafferService.importCard(ids);
		return JSON;
	}
	
	public String addByCustomerId(){
		result = customerService.getBeanById(id);
		return "addByCustomerId";
	}
	
	public String save(){
		result = stafferService.save(staffer);
		return JSON;
	}
	
	public String view(){
		result = stafferService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = stafferService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Staffer dbBean = stafferService.getBeanById(staffer.getId()).getValue();
		BeanUtil.copyProperties(staffer, dbBean);
		result = stafferService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = stafferService.deleteById(id);
		} else if(ids!=null){
			result = stafferService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Staffer.class);
		filter.createAlias("customer", "customer");
		return refresh(filter.orderBy("createTime", Filter.ASC));
	}
	
	@Override
	protected List<Staffer> getListByFilter(Filter fitler) {
		return stafferService.getListByFilter(fitler).getValue();
	}
	
	
	public Staffer getStaffer() {
		return staffer;
	}

	public void setStaffer(Staffer staffer) {
		this.staffer = staffer;
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

	public void setStafferService(StafferService stafferService) {
		this.stafferService = stafferService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Result getResult() {
		return result;
	}
	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}

	public String getIsLegal() {
		return isLegal;
	}

	public void setIsLegal(String isLegal) {
		this.isLegal = isLegal;
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
