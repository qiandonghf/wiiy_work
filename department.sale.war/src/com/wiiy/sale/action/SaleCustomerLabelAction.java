package com.wiiy.sale.action;

import java.util.ArrayList;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dto.SaleCustomerCategoryDto;
import com.wiiy.sale.entity.SaleCustomerCategory;
import com.wiiy.sale.entity.SaleCustomerLabel;
import com.wiiy.sale.preferences.enums.OwnerEnum;
import com.wiiy.sale.service.SaleCustomerCategoryService;
import com.wiiy.sale.service.SaleCustomerLabelService;

/**
 * @author my
 */
public class SaleCustomerLabelAction extends JqGridBaseAction<SaleCustomerLabel>{
	
	private SaleCustomerLabelService customerLabelService;
	private SaleCustomerCategoryService customerCategoryService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private SaleCustomerLabel customerLabel;
	private List<SaleCustomerLabel> customerLabelList;
	private Long id;
	private String ids;
	
	public String loadSaleCustomerLabel(){
		List<SaleCustomerCategory> customerCategoryList = customerCategoryService.getListByFilter(new Filter(SaleCustomerCategory.class)).getValue();
		List<SaleCustomerLabel> labelList = customerLabelService.getListByFilter(new Filter(SaleCustomerLabel.class).or(Filter.Eq("ownerEnum", OwnerEnum.PUBLIC),Filter.Eq("ownerId", SaleActivator.getSessionUser().getId()))).getValue();
		List<SaleCustomerCategoryDto> dtoList = new ArrayList<SaleCustomerCategoryDto>();
		for (SaleCustomerCategory customerCategory : customerCategoryList) {
			SaleCustomerCategoryDto customerCategoryDto = new SaleCustomerCategoryDto();
			customerCategoryDto.setSaleCustomerCategory(customerCategory);
			List<SaleCustomerLabel> customerLabelList = new ArrayList<SaleCustomerLabel>();
			for (SaleCustomerLabel customerLabel : labelList) {
				if(customerLabel.getOwnerEnum().equals(OwnerEnum.PUBLIC) && customerLabel.getCategoryId().longValue()==customerCategory.getId()){
					customerLabelList.add(customerLabel);
				}
			}
			customerCategoryDto.setSaleCustomerLabelList(customerLabelList);
			dtoList.add(customerCategoryDto);
		}
		customerLabelList = new ArrayList<SaleCustomerLabel>();
		for (SaleCustomerLabel customerLabel : labelList) {
			if(customerLabel.getOwnerEnum().equals(OwnerEnum.PRIVATE)){
				customerLabelList.add(customerLabel);
			}
		}
		result = Result.value(dtoList);
		return JSON;
	}
	
	public String loadSaleCustomerLabelByCategoryId(){
		result = customerLabelService.getListByFilter(new Filter(SaleCustomerLabel.class).eq("categoryId", id));
		return JSON;
	}
	
	public String loadMyLabel(){
		result = customerLabelService.getListByFilter(new Filter(SaleCustomerLabel.class).eq("ownerEnum",OwnerEnum.PRIVATE).eq("ownerId", SaleActivator.getSessionUser().getId()));
		return JSON;
	}
	
	public String addByCategoryId(){
		if(id!=null){
			result = customerCategoryService.getBeanById(id);
		}
		return "addByCategoryId";
	}
	
	public String save(){
		if(customerLabel.getOwnerEnum()==null)customerLabel.setOwnerEnum(OwnerEnum.PRIVATE);
		customerLabel.setOwnerId(SaleActivator.getSessionUser().getId());
		result = customerLabelService.save(customerLabel);
		return JSON;
	}
	
	public String view(){
		result = customerLabelService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = customerLabelService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		SaleCustomerLabel dbBean = customerLabelService.getBeanById(customerLabel.getId()).getValue();
		BeanUtil.copyProperties(customerLabel, dbBean);
		result = customerLabelService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerLabelService.deleteById(id);
		} else if(ids!=null){
			result = customerLabelService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(SaleCustomerLabel.class));
	}
	
	@Override
	protected List<SaleCustomerLabel> getListByFilter(Filter fitler) {
		return customerLabelService.getListByFilter(fitler).getValue();
	}
	
	
	public List<SaleCustomerLabel> getCustomerLabelList() {
		return customerLabelList;
	}

	public SaleCustomerLabel getCustomerLabel() {
		return customerLabel;
	}

	public void setCustomerLabel(SaleCustomerLabel customerLabel) {
		this.customerLabel = customerLabel;
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

	public void setCustomerLabelService(SaleCustomerLabelService customerLabelService) {
		this.customerLabelService = customerLabelService;
	}
	
	public void setCustomerCategoryService(
			SaleCustomerCategoryService customerCategoryService) {
		this.customerCategoryService = customerCategoryService;
	}

	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}
	
}
