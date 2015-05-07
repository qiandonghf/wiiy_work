package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.entity.CarApply;
import com.wiiy.synthesis.service.CarApplyService;

/**
 * @author my
 */
public class CarApplyAction extends JqGridBaseAction<CarApply>{
	
	private CarApplyService carApplyService;
	private Result result;
	private CarApply carApply;
	private Long id;
	private String ids;
	private Long approverId;//审批人id
	private String approverl;//审批人
	private String applyCheck;//是否同意  
	private String type;
	
	public String all(){
		type = "all";
		return "all";
	}
	
	public String registration(){
		type = "registration";
		return "registration";
	}
	
	public String apply(){
		type = "apply";
		return "apply";
	}
	
	//提交申请
	public String approve(){
		result = carApplyService.approve(id,approverId,approverl);
		return JSON;
	}
	//审批
	public String approveCarApply(){
		result = carApplyService.approveCarApply(id,applyCheck);
		return JSON;
	}
		
	
	public String save(){
		result = carApplyService.save(carApply);
		return JSON;
	}
	
	public String view(){
		result = carApplyService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = carApplyService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		CarApply dbBean = carApplyService.getBeanById(carApply.getId()).getValue();
		BeanUtil.copyProperties(carApply, dbBean);
		result = carApplyService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = carApplyService.deleteById(id);
		} else if(ids!=null){
			result = carApplyService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(CarApply.class);
		if(SynthesisActivator.getSessionUser().getAdmin()!=BooleanEnum.YES && "apply".equals(type)){
			filter.eq("creatorId", SynthesisActivator.getSessionUser().getId());
		}
		filter.orderBy("status", Filter.ASC);
		return refresh(filter);
	}
	
	@Override
	protected List<CarApply> getListByFilter(Filter fitler) {
		return carApplyService.getListByFilter(fitler).getValue();
	}
	
	
	public CarApply getCarApply() {
		return carApply;
	}

	public void setCarApply(CarApply carApply) {
		this.carApply = carApply;
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

	public void setCarApplyService(CarApplyService carApplyService) {
		this.carApplyService = carApplyService;
	}
	
	public Result getResult() {
		return result;
	}

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public String getApproverl() {
		return approverl;
	}

	public void setApproverl(String approverl) {
		this.approverl = approverl;
	}

	public String getApplyCheck() {
		return applyCheck;
	}

	public void setApplyCheck(String applyCheck) {
		this.applyCheck = applyCheck;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
