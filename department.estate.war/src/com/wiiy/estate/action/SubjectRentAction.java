package com.wiiy.estate.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.entity.EstateSubjectRent;
import com.wiiy.estate.service.SubjectRentService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.Room;

/**
 * @author my
 */
public class SubjectRentAction extends JqGridBaseAction<EstateSubjectRent>{
	
	private SubjectRentService subjectRentService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private EstateSubjectRent subjectRent;
	private Long id;
	private String ids;
	private String saveFlag;
	
	private List<EstateSubjectRent> subjectRentList;
	
	public String generatePlan(){
		view();
		return "generatePlan";
	}
	
	public String save(){
		if(subjectRent.getRebateRuleId()!=null && subjectRent.getRebateRuleId().length()==0) subjectRent.setRebateRuleId(null);
		Room r = subjectRent.getRoom();
		r.setId(subjectRent.getRoomId());
		subjectRent.setRoom(r);
		result = subjectRentService.save(subjectRent);
		return JSON;
	}
	
	public String view(){
		result = subjectRentService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = subjectRentService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		EstateSubjectRent dbBean = subjectRentService.getBeanById(subjectRent.getId()).getValue();
		if(subjectRent.getRebateRuleId()!=null && subjectRent.getRebateRuleId().length()==0) subjectRent.setRebateRuleId(null);
		BeanUtil.copyProperties(subjectRent, dbBean, "rebateRuleId");
		result = subjectRentService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = subjectRentService.deleteById(id);
		} else if(ids!=null){
			result = subjectRentService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(EstateSubjectRent.class);
		filter.createAlias("room", "room");
		return refresh(filter);
	}
	
	
	@Override
	protected List<EstateSubjectRent> getListByFilter(Filter fitler) {
		return subjectRentService.getListByFilter(fitler).getValue();
	}
	
	
	public EstateSubjectRent getSubjectRent() {
		return subjectRent;
	}

	public void setSubjectRent(EstateSubjectRent subjectRent) {
		this.subjectRent = subjectRent;
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

	public void setSubjectRentService(SubjectRentService subjectRentService) {
		this.subjectRentService = subjectRentService;
	}
	
	public Result getResult() {
		return result;
	}
	
	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public List<EstateSubjectRent> getSubjectRentList() {
		return subjectRentList;
	}

	public void setSubjectRentList(List<EstateSubjectRent> subjectRentList) {
		this.subjectRentList = subjectRentList;
	}
}
