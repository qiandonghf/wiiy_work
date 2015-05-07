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
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.business.dto.ProjectDto;
import com.wiiy.business.entity.Product;
import com.wiiy.business.service.CustomerService;
import com.wiiy.business.service.ProductService;

/**
 * @author my
 */
public class ProductAction extends JqGridBaseAction<Product>{
	
	private ProductService productService;
	private CustomerService customerService;
	private Result result;
	private Product product;
	private Long id;
	private String ids;
	private List<Integer> years;
	private String yearNo;
	private String monthNo;
	public String listByProductInfoApply(){
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
		List<ProjectDto> dtoList = new ArrayList<ProjectDto>();
		Filter filter = new Filter (Product.class);
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
		List<Product> list= productService.getListByFilter(filter).getValue();
		for (Product p : list) {
			ProjectDto dto = new ProjectDto();
			dto.setCustomer(p.getCustomer().getName());
			dto.setStage(p.getStage());
			dto.setTechnic(p.getTechnic());
			dto.setName(p.getName());
			dto.setIntroduction(p.getIntroduction());
			dto.setPub(p.getPub());
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "listByProductInfo";
	}
	
	public String addByCustomerId(){
		result = customerService.getBeanById(id);
		return "addByCustomerId";
	}
	
	public String loadProductsByCustomerId(){
		result = productService.getListByFilter(new Filter(Product.class).include("id").include("name").eq("customerId", id));
		return JSON;
	}
	
	public String save(){
		result = productService.save(product);
		return JSON;
	}
	
	public String view(){
		result = productService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = productService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Product dbBean = productService.getBeanById(product.getId()).getValue();
		BeanUtil.copyProperties(product, dbBean);
		result = productService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = productService.deleteById(id);
		} else if(ids!=null){
			result = productService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Product.class);
		filter.createAlias("customer", "customer");
		return refresh(filter);
	}
	
	
	@Override
	protected List<Product> getListByFilter(Filter fitler) {
		return productService.getListByFilter(fitler).getValue();
	}
	
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public void setProductService(ProductService productService) {
		this.productService = productService;
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
