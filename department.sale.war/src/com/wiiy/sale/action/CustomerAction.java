package com.wiiy.sale.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dto.TreeDto;
import com.wiiy.sale.entity.SaleCustomer;
import com.wiiy.sale.entity.SaleCustomerCategory;
import com.wiiy.sale.entity.SaleCustomerLabel;
import com.wiiy.sale.preferences.enums.CustomerLevelEnum;
import com.wiiy.sale.preferences.enums.OwnerEnum;
import com.wiiy.sale.service.SaleCustomerCategoryService;
import com.wiiy.sale.service.SaleCustomerLabelService;
import com.wiiy.sale.service.SaleCustomerService;

/**
 * @author my
 */
public class CustomerAction extends JqGridBaseAction<SaleCustomer>{
	
	private SaleCustomerService customerService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private SaleCustomer customer;
	private Long id;
	private String ids;
	private SaleCustomerCategoryService customerCategoryService;
	private SaleCustomerLabelService customerLabelService;
	private List<SaleCustomerCategory> customerCategoryList;
	private List<SaleCustomerLabel> customerLabelList;
	private List<SaleCustomerLabel> myLabelList;
	private List<SaleCustomer> customerList;
	private List<DataDict> incubationRouteList;
	private List<DataDict> customerQualificationList;
	
	private CustomerLevelEnum type;
	private boolean who;
	private String own;
	
	
	public String levels() {
		result = Result.value(customerService.levels(who));
		return JSON;
	}
	
	public String add(){
		return "add";
	}
	
	public String save(){
		result = customerService.save(customer,ids);
		return JSON;
	}
	
	public String view(){
		result = customerService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = customerService.getBeanByFilter(//
				new Filter(SaleCustomer.class).eq("id", id).fetchMode("labelRefs", Filter.JOIN));
		return EDIT;
	}
	
	public String update(){
		result = customerService.update(customer,ids);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerService.deleteById(id);
		} else if(ids!=null){
			result = customerService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		User user = SaleActivator.getSessionUser();
		Filter filter = new Filter(SaleCustomer.class);
		if (type != null) {
			filter.eq("level", type);
		}
		if (own != null) {
			filter.eq("userId",user.getId());
		}
		return refresh(filter);
	}
	
	public String loadCategory(){
		Filter filter = new Filter(SaleCustomerCategory.class);
		filter.or(Filter.Eq("ownerId", SaleActivator.getSessionUser().getId()),Filter.Eq("ownerEnum", OwnerEnum.PUBLIC));
		customerCategoryList = customerCategoryService.getListByFilter(filter).getValue();
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		TreeDto allCustomerDto = new TreeDto();
		allCustomerDto.setId(-1l);
		allCustomerDto.setText("所有客户");
		dtoList.add(allCustomerDto);
		
		for (SaleCustomerCategory category : customerCategoryList) {
			TreeDto dto = new TreeDto();
			dto.setId(category.getId());
			dto.setText(category.getName()+"<input type='hidden' value='"+category.getId()+"'>");
			dto.setState(TreeDto.CLOSED);
			dtoList.add(dto);
		}
		
		TreeDto myLabelDto = new TreeDto();
		myLabelDto.setId(0l);
		myLabelDto.setText("我的分组");
		myLabelDto.setState(TreeDto.CLOSED);
		dtoList.add(myLabelDto);
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	public String loadIncubatorRote(){
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		List<DataDict> list = SaleActivator.getDataDictInitService().getDataDictByParentId("crm.0025");
		for (DataDict dataDict : list) {
			TreeDto dto = new TreeDto();
			dto.setId(-2L);
			dto.setText(dataDict.getDataValue()+"<input type='hidden' value='"+dataDict.getId()+"'>");
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	public String loadLabelByCategoryId(){
		Filter filter = new Filter(SaleCustomerLabel.class);
		System.out.println(id);
		if(id!=0){
			filter.eq("categoryId", id);
		} else {
			filter.eq("ownerEnum",OwnerEnum.PRIVATE).eq("ownerId", SaleActivator.getSessionUser().getId());
		}
		customerLabelList = customerLabelService.getListByFilter(filter).getValue();
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		for (SaleCustomerLabel category : customerLabelList) {
			TreeDto dto = new TreeDto();
			dto.setId(category.getId());
			dto.setText(category.getName()+"<input type='hidden' value='"+category.getId()+"'>");
			dto.setState(TreeDto.OPEN);
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	public String loadMyLabel(){
		Filter filter = new Filter(SaleCustomerLabel.class);
		filter.eq("ownerEnum",OwnerEnum.PRIVATE).eq("ownerId", SaleActivator.getSessionUser().getId());
		customerLabelList = customerLabelService.getListByFilter(filter).getValue();
		List<TreeDto> dtoList = new ArrayList<TreeDto>();
		for (SaleCustomerLabel category : customerLabelList) {
			TreeDto dto = new TreeDto();
			dto.setId(category.getId());
			dto.setText(category.getName());
			dto.setState(TreeDto.OPEN);
			dtoList.add(dto);
		}
		result = Result.value(dtoList);
		return "rvalue";
	}
	
	@SuppressWarnings("unused")
	private LinkedHashMap<String, String> generateExportColumns(String columns){
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		columns = columns.replace("{", "");
		columns = columns.replace("}", "");
		String[] properties = columns.split(",");
		for (String string : properties) {
			String[] ss = string.split(":");
			String field = ss[0].replace("\"", "");
			String description = ss[1].replace("\"", "");
			map.put(field, description);
		}
		return map;
	}
	
	@Override
	protected List<SaleCustomer> getListByFilter(Filter fitler) {
		return customerService.getListByFilter(fitler).getValue();
	}
	
	
	public SaleCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(SaleCustomer customer) {
		this.customer = customer;
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

	public void setCustomerService(SaleCustomerService customerService) {
		this.customerService = customerService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}

	public void setCustomerCategoryService(
			SaleCustomerCategoryService customerCategoryService) {
		this.customerCategoryService = customerCategoryService;
	}

	public void setCustomerLabelService(
			SaleCustomerLabelService customerLabelService) {
		this.customerLabelService = customerLabelService;
	}

	public List<SaleCustomerCategory> getCustomerCategoryList() {
		return customerCategoryList;
	}

	public void setCustomerCategoryList(
			List<SaleCustomerCategory> customerCategoryList) {
		this.customerCategoryList = customerCategoryList;
	}

	public List<SaleCustomerLabel> getCustomerLabelList() {
		return customerLabelList;
	}

	public void setCustomerLabelList(List<SaleCustomerLabel> customerLabelList) {
		this.customerLabelList = customerLabelList;
	}

	public List<SaleCustomerLabel> getMyLabelList() {
		return myLabelList;
	}

	public void setMyLabelList(List<SaleCustomerLabel> myLabelList) {
		this.myLabelList = myLabelList;
	}

	public List<SaleCustomer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<SaleCustomer> customerList) {
		this.customerList = customerList;
	}

	public List<DataDict> getIncubationRouteList() {
		return incubationRouteList;
	}

	public void setIncubationRouteList(List<DataDict> incubationRouteList) {
		this.incubationRouteList = incubationRouteList;
	}

	public List<DataDict> getCustomerQualificationList() {
		return customerQualificationList;
	}

	public void setCustomerQualificationList(
			List<DataDict> customerQualificationList) {
		this.customerQualificationList = customerQualificationList;
	}

	public CustomerLevelEnum getType() {
		return type;
	}

	public void setType(CustomerLevelEnum type) {
		this.type = type;
	}

	public String getOwn() {
		return own;
	}

	public void setOwn(String own) {
		this.own = own;
	}

	public boolean isWho() {
		return who;
	}

	public void setWho(boolean who) {
		this.who = who;
	}
	
}
