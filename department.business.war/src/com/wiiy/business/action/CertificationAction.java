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
import com.wiiy.business.dto.CertificationDto;
import com.wiiy.business.entity.Certification;
import com.wiiy.business.service.CertificationService;
import com.wiiy.business.service.CustomerService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class CertificationAction extends JqGridBaseAction<Certification>{
	
	private CertificationService certificationService;
	private CustomerService customerService;
	private Result result;
	private Certification certification;
	private Long id;
	private String ids;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	private Integer flag;
	
	public String listBycertification(){
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
		List<CertificationDto> dtoList = new ArrayList<CertificationDto>();
		Filter filter = new Filter (Certification.class);
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
			filter.between("certTime", startTime, endTime);
		}
		List<Certification> list = certificationService.getListByFilter(filter).getValue();
		for (Certification c : list) {
			CertificationDto dto = new CertificationDto();
			dto.setCustomer(c.getCustomer().getName());
			dto.setSerialNo(c.getSerialNo());
			dto.setType(c.getType());
			dto.setPub(c.getPub());
			dto.setCertTime(c.getCertTime());
			dto.setName(c.getName());
			dto.setAgency(c.getAgency());
			dto.setSummery(c.getSummery());
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "certification";
	}
	
	public String addByCustomerId(){
		result = customerService.getBeanById(id);
		return "addByCustomerId";
	}
	
	public String save(){
		result = certificationService.save(certification);
		return JSON;
	}
	
	public String view(){
		result = certificationService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = certificationService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Certification dbBean = certificationService.getBeanById(certification.getId()).getValue();
		BeanUtil.copyProperties(certification, dbBean);
		result = certificationService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = certificationService.deleteById(id);
		} else if(ids!=null){
			result = certificationService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Certification.class);
		filter.createAlias("customer", "customer");
		return refresh(filter);
	}
	
	@Override
	protected List<Certification> getListByFilter(Filter fitler) {
		return certificationService.getListByFilter(fitler).getValue();
	}
	
	
	public Certification getCertification() {
		return certification;
	}

	public void setCertification(Certification certification) {
		this.certification = certification;
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

	public void setCertificationService(CertificationService certificationService) {
		this.certificationService = certificationService;
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
