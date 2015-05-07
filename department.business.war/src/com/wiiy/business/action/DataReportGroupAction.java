package com.wiiy.business.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

import com.wiiy.business.entity.DataReport;
import com.wiiy.business.entity.DataReportGroup;
import com.wiiy.business.service.DataReportGroupService;

/**
 * @author my
 */
public class DataReportGroupAction extends JqGridBaseAction<DataReportGroup>{
	
	private DataReportGroupService dataReportGroupService;
	private Result result;
	private DataReportGroup dataReportGroup;
	private Long id;
	private String ids;
	private Long groupId;
	
	
	public String save(){
		result = dataReportGroupService.save(dataReportGroup);
		return JSON;
	}
	
	public String view(){
		result = dataReportGroupService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = dataReportGroupService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		DataReportGroup dbBean = dataReportGroupService.getBeanById(dataReportGroup.getId()).getValue();
		BeanUtil.copyProperties(dataReportGroup, dbBean);
		result = dataReportGroupService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = dataReportGroupService.deleteById(id);
		} else if(ids!=null){
			result = dataReportGroupService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		result = dataReportGroupService.getList();
		return LIST;
	}
	
	@Override
	protected List<DataReportGroup> getListByFilter(Filter fitler) {
		return dataReportGroupService.getListByFilter(fitler).getValue();
	}
	
	
	public DataReportGroup getDataReportGroup() {
		return dataReportGroup;
	}

	public void setDataReportGroup(DataReportGroup dataReportGroup) {
		this.dataReportGroup = dataReportGroup;
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

	public void setDataReportGroupService(DataReportGroupService dataReportGroupService) {
		this.dataReportGroupService = dataReportGroupService;
	}
	
	public Result getResult() {
		return result;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
}
