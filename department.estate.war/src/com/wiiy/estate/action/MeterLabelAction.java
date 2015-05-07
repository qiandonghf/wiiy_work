package com.wiiy.estate.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.wiiy.common.preferences.enums.MeterTypeEnum;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.entity.MeterLabel;
import com.wiiy.estate.preferences.enums.MeterRecordStatusEnum;
import com.wiiy.estate.service.MeterLabelService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Pager;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.Building;
import com.wiiy.park.service.BuildingService;

public class MeterLabelAction extends JqGridBaseAction<MeterLabel>{
	private MeterLabelService meterLabelService;
	private BuildingService buildingService;
	
	private MeterLabel meterLabel;
	private Long id;
	private String ids;
	@SuppressWarnings("rawtypes")
	private Result result;
	private Pager pager;
	private List<MeterLabel> meterLabelList;
	private MeterTypeEnum type;

	public String initList(){
		Filter filter = new Filter(MeterLabel.class);
		filter.orderBy("modifyTime", Filter.DESC);
		filter.eq("id", id);
		result =  meterLabelService.getListByFilter(filter);
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(MeterLabel.class);
		if(page!=0){
			pager = new Pager(page,10);
		} else {
			pager = new Pager(1,10);
		}
		filter.pager(pager);
		if(type!=null){
			filter.eq("type", type);
		}
		if(id!=null&&!"".equals(id)){
			filter.eq("id", id);
		}
		filter.orderBy("modifyTime", Filter.DESC);
		meterLabelList = meterLabelService.getListByFilter(filter).getValue();
		return LIST;
	}

	public String save(){
		meterLabel.setCheckOut(MeterRecordStatusEnum.INITIAL);
		result = meterLabelService.save(meterLabel);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = meterLabelService.deleteById(id);
		}
		return JSON;
	}
	
	public String edit(){
		result = meterLabelService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		MeterLabel dbean = meterLabelService.getBeanById(meterLabel.getId()).getValue();
		String oldIds = dbean.getBuildingIds();
		BeanUtil.copyProperties(meterLabel, dbean);
		String newIds = meterLabel.getBuildingIds();
		result = meterLabelService.update(dbean);
		if(result.isSuccess()){
			if(!oldIds.equals(newIds)){
				meterLabelService.updateRecord(meterLabel.getId(),oldIds,newIds);
			}
		}
		return JSON;
	}
	
	public String view(){
		meterLabel = meterLabelService.getBeanById(id).getValue();
		result = meterLabelService.getBeanById(id);
		String buildingNames = "";
		meterLabel = (MeterLabel)result.getValue();
		String buildingIds = meterLabel.getBuildingIds();
		for(String buildingId : buildingIds.split(",")){
			Building building = buildingService.getBeanById(Long.parseLong(buildingId)).getValue();
			String name = building.getPark().getName()+"--"+building.getName(); 
			buildingNames += name + ",";
		}
		buildingNames = buildingNames.substring(0,buildingNames.length()-1);
		ServletActionContext.getRequest().setAttribute("buildingNames", buildingNames);
		if(meterLabel.getType().equals(MeterTypeEnum.WATER)){
			return "waterView";
		}else{
			return "eleView";
		}
		
	}
	
	@Override
	protected List<MeterLabel> getListByFilter(Filter filter) {
		return meterLabelService.getListByFilter(filter).getValue();
	}
	public MeterLabel getMeterLabel() {
		return meterLabel;
	}
	public void setMeterLabel(MeterLabel meterLabel) {
		this.meterLabel = meterLabel;
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
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public void setMeterLabelService(MeterLabelService meterLabelService) {
		this.meterLabelService = meterLabelService;
	}

	public void setBuildingService(BuildingService buildingService) {
		this.buildingService = buildingService;
	}

	public List<MeterLabel> getMeterLabelList() {
		return meterLabelList;
	}

	public void setMeterLabelList(List<MeterLabel> meterLabelList) {
		this.meterLabelList = meterLabelList;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public MeterTypeEnum getType() {
		return type;
	}

	public void setType(MeterTypeEnum type) {
		this.type = type;
	}

}
