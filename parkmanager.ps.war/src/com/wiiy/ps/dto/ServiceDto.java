package com.wiiy.ps.dto;

public class ServiceDto implements Comparable<ServiceDto>{
	
	private String name;
	private String url;
	private String icon;
	private String dataUrl;
	private String runAs;
	private String floatboxWidth;
	private String floatboxHeight;
	private String description;
	private Integer order;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDataUrl() {
		return dataUrl;
	}
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	public String getRunAs() {
		return runAs;
	}
	public void setRunAs(String runAs) {
		this.runAs = runAs;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFloatboxWidth() {
		return floatboxWidth;
	}
	public void setFloatboxWidth(String floatboxWidth) {
		this.floatboxWidth = floatboxWidth;
	}
	public String getFloatboxHeight() {
		return floatboxHeight;
	}
	public void setFloatboxHeight(String floatboxHeight) {
		this.floatboxHeight = floatboxHeight;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	@Override
	public int compareTo(ServiceDto o) {
		return this.order-o.order;
	}
	
}
