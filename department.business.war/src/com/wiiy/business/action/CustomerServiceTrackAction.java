package com.wiiy.business.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Pager;
import com.wiiy.hibernate.Result;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.entity.CustomerService;
import com.wiiy.business.entity.CustomerServiceTrack;
import com.wiiy.business.service.CustomerServiceService;
import com.wiiy.business.service.CustomerServiceTrackService;

/**
 * @author my
 */
public class CustomerServiceTrackAction extends JqGridBaseAction<CustomerServiceTrack>{
	
	private CustomerServiceTrackService customerServiceTrackService;
	private Result result;
	private CustomerServiceTrack customerServiceTrack;
	private Long id;
	private String ids;
	private Pager pager;
	private Long userId;
	private List<CustomerServiceTrack> customerServiceTrackList;
	private List<CustomerService> customerServiceList;
	private CustomerServiceService customerServiceService;
	
	public String save(){
		customerServiceList=customerServiceService.getList().getValue();
		result = customerServiceTrackService.save(customerServiceTrack,customerServiceList);
		return JSON;
	}
	
	public String view(){
		Filter filter = new Filter(CustomerServiceTrack.class);
		filter.eq("serviceId", id);
		return refresh(filter);
	}
	
	public String view2(){
		Filter filter = new Filter(CustomerServiceTrack.class);
		return refresh(filter);
	}
	
	public String edit(){
		result = customerServiceTrackService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		CustomerServiceTrack dbBean = customerServiceTrackService.getBeanById(customerServiceTrack.getId()).getValue();
		BeanUtil.copyProperties(customerServiceTrack, dbBean);
		result = customerServiceTrackService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = customerServiceTrackService.deleteById(id);
		} else if(ids!=null){
			result = customerServiceTrackService.deleteByIds(ids);
		}
		return JSON;
	}
	public String listAll(){
		return "listAll";
	}
	public String list(){
		//过滤显示当前联系单
		//当前联系单下的所有数据
		Filter filter = new Filter(CustomerServiceTrack.class);
		customerServiceTrackList=customerServiceTrackService.getList().getValue();
 
		userId = BusinessActivator.getSessionUser().getId();
		if(page!=0){
			pager = new Pager(page,15);
		} else {
			pager = new Pager(1,15);
		}
		filter.pager(pager);
		filter.eq("userId", userId);
		customerServiceTrackList = customerServiceTrackService.getListByFilter(filter).getValue();
		result = Result.value(customerServiceTrackList);
		return LIST;
	}

	@Override
	protected List<CustomerServiceTrack> getListByFilter(Filter fitler) {
		return customerServiceTrackService.getListByFilter(fitler).getValue();
	}
	
	
	public CustomerServiceTrack getCustomerServiceTrack() {
		return customerServiceTrack;
	}

	public void setCustomerServiceTrack(CustomerServiceTrack customerServiceTrack) {
		this.customerServiceTrack = customerServiceTrack;
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

	public void setCustomerServiceTrackService(CustomerServiceTrackService customerServiceTrackService) {
		this.customerServiceTrackService = customerServiceTrackService;
	}
	
	public Result getResult() {
		return result;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Pager getPager() {
		return pager;
	}

	public List<CustomerServiceTrack> getCustomerServiceTrackList() {
		return customerServiceTrackList;
	}

	public void setCustomerServiceTrackList(
			List<CustomerServiceTrack> customerServiceTrackList) {
		this.customerServiceTrackList = customerServiceTrackList;
	}

	public void setCustomerServiceService(
			CustomerServiceService customerServiceService) {
		this.customerServiceService = customerServiceService;
	}

	public List<CustomerService> getCustomerServiceList() {
		return customerServiceList;
	}

	public void setCustomerServiceList(List<CustomerService> customerServiceList) {
		this.customerServiceList = customerServiceList;
	}
	
}
