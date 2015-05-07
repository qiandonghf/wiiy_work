package com.wiiy.ps.action;

import java.util.List;

import com.wiiy.commons.action.BaseAction;
import com.wiiy.ps.dto.ServiceDto;
import com.wiiy.ps.extensions.EnterpriseServiceExtensions;

public class Action extends BaseAction{
	
	private EnterpriseServiceExtensions enterpriseServiceExtensions;
	
	private List<ServiceDto> serviceDtoList;
	
	public String service(){
		serviceDtoList = enterpriseServiceExtensions.getService();
		return "service";
	}

	public void setEnterpriseServiceExtensions(
			EnterpriseServiceExtensions enterpriseServiceExtensions) {
		this.enterpriseServiceExtensions = enterpriseServiceExtensions;
	}

	public List<ServiceDto> getServiceDtoList() {
		return serviceDtoList;
	}

}
