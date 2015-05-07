package com.wiiy.estate.preferences.enums;

public enum PatrolIntervalEnum {
	 Month("月"),Week("周");
	
	 private String title;
	 PatrolIntervalEnum(String title) {
		this.title = title;
	 }

	 public String getTitle() {
		return title;
	 }
}
