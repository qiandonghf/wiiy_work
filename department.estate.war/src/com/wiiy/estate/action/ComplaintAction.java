package com.wiiy.estate.action;

import java.util.ArrayList;
import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.estate.dto.PropertyDto;
import com.wiiy.estate.entity.Complaint;
import com.wiiy.estate.entity.PropertyFix;
import com.wiiy.estate.preferences.enums.ComplaintAcceptStatusEnum;
import com.wiiy.estate.preferences.enums.PropertyFixStatusEnum;
import com.wiiy.estate.service.ComplaintService;

/**
 * @author my
 */
public class ComplaintAction extends JqGridBaseAction<Complaint>{
	
	private ComplaintService complaintService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private Complaint complaint;
	private Long id;
	private String ids;
	private String status;
	
	public String workBenchComplaint(){
		result = complaintService.getListByFilter(//
				new Filter(Complaint.class).orderBy("complaintTime", Filter.ASC).maxResults(4));
		return "workBenchComplaint";
	}
	
	public String handIn(){
		result = Result.success();
		return JSON;
	}
	
	public String handle(){
		result = complaintService.getBeanById(id);
		return "handle";
	}
	
	public String save(){
		complaint.setStatus(ComplaintAcceptStatusEnum.UNSTART);
		result = complaintService.save(complaint);
		return JSON;
	}
	
	public String view(){
		result = complaintService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = complaintService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		Complaint dbBean = complaintService.getBeanById(complaint.getId()).getValue();
		BeanUtil.copyProperties(complaint, dbBean);
		result = complaintService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = complaintService.deleteById(id);
		} else if(ids!=null){
			result = complaintService.deleteByIds(ids);
		}
		return JSON;
	}
	public String amounts(){
		List<PropertyDto> dtoList=new ArrayList<PropertyDto>();
		Filter filter = new Filter(Complaint.class);
		filter.or(Filter.Eq("status", ComplaintAcceptStatusEnum.UNSTART),Filter.Eq("status", ComplaintAcceptStatusEnum.HANGUP));
		List<Complaint> list = complaintService.getListByFilter(filter).getValue();
		if(list.size()!=0){
			PropertyDto dto = new PropertyDto();
			for (Complaint li : list) {
				dto.setId(li.getId());
				dto.setTypeName(li.getStatus().toString());
				dtoList.add(dto);
			}
		}
		result=Result.value(dtoList);
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(Complaint.class));
	}
	
	public String listAll(){
		return "listAll";
	}
	public String listByType(){
		Filter filter = new Filter(Complaint.class);
		if(!("").equals(status)&&status!=null){
			filter.or(Filter.Eq("status", ComplaintAcceptStatusEnum.UNSTART),Filter.Eq("status", ComplaintAcceptStatusEnum.HANGUP));
		}
		return refresh(filter);
}
	@Override
	protected List<Complaint> getListByFilter(Filter fitler) {
		return complaintService.getListByFilter(fitler).getValue();
	}
	
	
	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
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

	public void setComplaintService(ComplaintService complaintService) {
		this.complaintService = complaintService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
