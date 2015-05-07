package com.wiiy.park.action;

import java.util.List;

import com.wiiy.common.preferences.enums.BillRemindStatusEnum;
import com.wiiy.common.preferences.enums.DepartmentEnum;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.BillRemind;
import com.wiiy.park.service.BillRemindService;

/**
 * @author my
 */
public class BillRemindAction extends JqGridBaseAction<BillRemind>{
	
	private BillRemindService billRemindService;
	private Result result;
	private BillRemind billRemind;
	private Long id;
	private String ids;
	
	private String feeType;//费用类型
	
	private String department;//部门
	
	/**
	 * 设置催缴
	 */
	public String remind(){
		result = billRemindService.remind(id);
		return JSON;
	}
	
	/**
	 * 催缴提醒数量
	 * @return
	 */
	public String initRemindCount(){
		result = billRemindService.getRowCountByFeeType(department);
		return JSON;
	}
	
	
	public String save(){
		result = billRemindService.save(billRemind);
		return JSON;
	}
	
	public String view(){
		result = billRemindService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = billRemindService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		BillRemind dbBean = billRemindService.getBeanById(billRemind.getId()).getValue();
		BeanUtil.copyProperties(billRemind, dbBean);
		result = billRemindService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = billRemindService.deleteById(id);
		} else if(ids!=null){
			result = billRemindService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(BillRemind.class);
		filter.createAlias("billType", "billType");
		if(department!=null && !department.equals("")){
			filter.like("typeId", department);
		}
		if(feeType!=null && !"".equals(feeType)){
			filter.eq("typeId", feeType);
		}
		filter.eq("remindStatus", BillRemindStatusEnum.UNCALLEDBILL);
		return refresh(filter);
	}
	
	@Override
	protected List<BillRemind> getListByFilter(Filter fitler) {
		return billRemindService.getListByFilter(fitler).getValue();
	}
	
	
	public BillRemind getBillRemind() {
		return billRemind;
	}

	public void setBillRemind(BillRemind billRemind) {
		this.billRemind = billRemind;
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

	public void setBillRemindService(BillRemindService billRemindService) {
		this.billRemindService = billRemindService;
	}
	
	public Result getResult() {
		return result;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
