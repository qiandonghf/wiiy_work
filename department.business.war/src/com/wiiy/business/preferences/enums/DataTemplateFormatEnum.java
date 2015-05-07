package com.wiiy.business.preferences.enums;

import java.util.List;
import java.util.Map;

import com.wiiy.business.entity.DataProperty;
import com.wiiy.business.entity.DataReportProperty;
import com.wiiy.business.entity.DataReportValue;
import com.wiiy.business.preferences.enums.format.BusinessDataReportFormat;
import com.wiiy.business.preferences.enums.format.DataReportFormat;
import com.wiiy.business.preferences.enums.format.DefaultReportFormat;

public enum DataTemplateFormatEnum {
	
	DEFAULT("默认", new DefaultReportFormat()),BUSINESSDATA("企业经营数据",new BusinessDataReportFormat());
	private String title;
	private DataReportFormat format;

	DataTemplateFormatEnum(String title, DataReportFormat format) {
		this.title = title;
		this.format = format;
	}

	public String getTitle() {
		return title;
	}
	
	public String format(List<DataProperty> propertyList){
		return format.format(propertyList);
	}
	public String format(List<DataReportProperty> propertyList,Map<Long, DataReportValue> valueMap){
		return format.format(propertyList,valueMap);
	}

}