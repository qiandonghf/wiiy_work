package com.wiiy.synthesis.action;

import java.util.List;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.entity.SealRegistration;
import com.wiiy.synthesis.entity.SealRegistrationAtt;
import com.wiiy.synthesis.preferences.enums.SealApplyEnum;
import com.wiiy.synthesis.service.SealRegistrationAttService;
import com.wiiy.synthesis.service.SealRegistrationService;

/**
 * @author my
 */
public class SealRegistrationAction extends JqGridBaseAction<SealRegistration>{
	
	private SealRegistrationService sealRegistrationService;
	private SealRegistrationAttService sealRegistrationAttService;
	private Result result;
	private SealRegistration sealRegistration;
	private Long id;
	private String ids;
	
	private String attAddress;
	private String attNames;
	private String attSizes;
	private String attPaths;
	private String type;
	
	public String agree(){
		result = sealRegistrationService.agree(id);
		return JSON;
	}
	
	public String disagree(){
		result = sealRegistrationService.disagree(id);
		return JSON;
	}

	public String allList(){
		type = "all";
		return LIST;
	}
	
	public String sealApply(){
		type = "Apply";
		return LIST;
	}
	
	public String sealAgree(){
		type="Agree";
		return LIST;
	}
	
	public String save(){
		result = sealRegistrationService.save(sealRegistration,attAddress);
		return JSON;
	}
	
	public String view(){
		attNames = "";
		attSizes = "";
		attPaths = "";
		List<SealRegistrationAtt> list = sealRegistrationAttService.getListByFilter(new Filter(SealRegistrationAtt.class).eq("sealRegistrationId", id)).getValue();
		for (SealRegistrationAtt meetingAtt : list) {
			attNames += meetingAtt.getName()+",";
			attSizes += meetingAtt.getSize()+",";
			attPaths += meetingAtt.getNewName()+",";
		}
		if(attNames.length()>0){
			attNames = attNames.substring(0, attNames.length()-1);
			attSizes = attSizes.substring(0, attSizes.length()-1);
			attPaths = attPaths.substring(0, attPaths.length()-1);
		}
		result = sealRegistrationService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		attNames = "";
		attSizes = "";
		attPaths = "";
		List<SealRegistrationAtt> list = sealRegistrationAttService.getListByFilter(new Filter(SealRegistrationAtt.class).eq("sealRegistrationId", id)).getValue();
		for (SealRegistrationAtt meetingAtt : list) {
			attNames += meetingAtt.getName()+",";
			attSizes += meetingAtt.getSize()+",";
			attPaths += meetingAtt.getNewName()+",";
		}
		if(attNames.length()>0){
			attNames = attNames.substring(0, attNames.length()-1);
			attSizes = attSizes.substring(0, attSizes.length()-1);
			attPaths = attPaths.substring(0, attPaths.length()-1);
		}
		result = sealRegistrationService.getBeanById(id);
		if("Apply".equals(type)){
			return "applyedit";
		}
		return EDIT;
	}
	
	public String update(){
		SealRegistration dbBean = sealRegistrationService.getBeanById(sealRegistration.getId()).getValue();
		BeanUtil.copyProperties(sealRegistration, dbBean);
		result = sealRegistrationService.update(dbBean,attAddress);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = sealRegistrationService.deleteById(id);
		} else if(ids!=null){
			result = sealRegistrationService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(SealRegistration.class);
		if(SynthesisActivator.getSessionUser().getAdmin()!=BooleanEnum.YES && "Apply".equals(type)){
			filter.eq("userId", SynthesisActivator.getSessionUser().getId());
		}
		filter.orderBy("status", Filter.DESC);
		filter.orderBy("time", Filter.DESC);
		return refresh(filter);
	}
	
	@Override
	protected List<SealRegistration> getListByFilter(Filter fitler) {
		return sealRegistrationService.getListByFilter(fitler).getValue();
	}
	
	
	public SealRegistration getSealRegistration() {
		return sealRegistration;
	}

	public void setSealRegistration(SealRegistration sealRegistration) {
		this.sealRegistration = sealRegistration;
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

	public void setSealRegistrationService(SealRegistrationService sealRegistrationService) {
		this.sealRegistrationService = sealRegistrationService;
	}
	
	public Result getResult() {
		return result;
	}

	public String getAttAddress() {
		return attAddress;
	}

	public void setAttAddress(String attAddress) {
		this.attAddress = attAddress;
	}

	public String getAttNames() {
		return attNames;
	}

	public void setAttNames(String attNames) {
		this.attNames = attNames;
	}

	public String getAttSizes() {
		return attSizes;
	}

	public void setAttSizes(String attSizes) {
		this.attSizes = attSizes;
	}

	public String getAttPaths() {
		return attPaths;
	}

	public void setAttPaths(String attPaths) {
		this.attPaths = attPaths;
	}

	public void setSealRegistrationAttService(
			SealRegistrationAttService sealRegistrationAttService) {
		this.sealRegistrationAttService = sealRegistrationAttService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
