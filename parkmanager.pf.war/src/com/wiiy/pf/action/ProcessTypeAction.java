package com.wiiy.pf.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.service.ProcessTypeService;

/**
 * @author my
 */
public class ProcessTypeAction extends JqGridBaseAction<ProcessType>{
	
	private ProcessTypeService processTypeService;
	private Result result;
	private ProcessType processType;
	private Long id;
	private String ids;
	
	
	public String save(){
		result = processTypeService.save(processType);
		return JSON;
	}
	
	public String view(){
		result = processTypeService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = processTypeService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		ProcessType dbBean = processTypeService.getBeanById(processType.getId()).getValue();
		BeanUtil.copyProperties(processType, dbBean);
		result = processTypeService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = processTypeService.deleteById(id);
		} else if(ids!=null){
			result = processTypeService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(ProcessType.class));
	}
	
	@Override
	protected List<ProcessType> getListByFilter(Filter fitler) {
		return processTypeService.getListByFilter(fitler).getValue();
	}
	
	
	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
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

	public void setProcessTypeService(ProcessTypeService processTypeService) {
		this.processTypeService = processTypeService;
	}
	
	public Result getResult() {
		return result;
	}
	
}
