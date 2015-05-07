package com.wiiy.commons.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

public class BaseAction implements ActionSchema {

	public static  String rootLocation=System.getProperty("rootLocation");

	private String contextLocation;
	
	public String getRootLocation() {
		
		return rootLocation+"/";
	}

	public String getContextLocation() {
		if (contextLocation == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			contextLocation = rootLocation+request.getContextPath() + "/";
		}
		return contextLocation;
	}
}
