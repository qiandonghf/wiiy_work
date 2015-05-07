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
import com.wiiy.business.dto.BrandDto;
import com.wiiy.business.entity.Brand;
import com.wiiy.business.service.BrandService;
import com.wiiy.business.service.CustomerService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

public class BrandAction extends JqGridBaseAction<Brand>{
	private BrandService brandService;
	private CustomerService customerService;
	private Brand brand;
	private Long id;
	private String ids;
	private Result result;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	private Integer flag;
	
	public String listByBrand(){
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
		List<BrandDto> dtoList = new ArrayList<BrandDto>();
		Filter filter = new Filter (Brand.class);
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
			filter.between("grantDate", startTime, endTime);
		}
		List<Brand> list = brandService.getListByFilter(filter).getValue();
		for (Brand b : list) {
			BrandDto dto = new BrandDto();
			dto.setCustomer(b.getCustomer().getName());
			dto.setName(b.getName());
			dto.setBrandNo(b.getBrandNo());
			dto.setRegister(b.getRegister());
			dto.setRegisterAddress(b.getRegisterAddress());
			dto.setStartDate(b.getStartDate());
			dto.setEndDate(b.getEndDate());
			dto.setGrantDate(b.getGrantDate());
			dto.setMemo(b.getMemo());
			dtoList.add(dto);
 		}
		result = Result.value(dtoList);
		return "brandList";
	}
	public String addByCustomerId(){
		result = customerService.getBeanById(id);
		return "addByCustomerId";
	}
	
	public String list(){
		Filter filter = new Filter(Brand.class);
		return refresh(filter);
	}
	
	public String save(){
		result = brandService.save(brand);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = brandService.deleteById(id); 
		}
		if(ids!=null){
			result = brandService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String edit(){
		result = brandService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Brand dbean = brandService.getBeanById(brand.getId()).getValue();
		BeanUtil.copyProperties(brand, dbean);
		result = brandService.update(dbean);
		return JSON;
	}
	
	public String view(){
		result = brandService.getBeanById(id);
		return VIEW;
	}
	
	@Override
	protected List<Brand> getListByFilter(Filter filter) {
		return brandService.getListByFilter(filter).getValue();
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
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
