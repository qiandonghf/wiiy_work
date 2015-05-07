package com.wiiy.commons.taglib;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class AbstractChooseTaglib extends TagSupport {
	
	protected String name;
	protected String checked;
	protected String defaultValue;
	protected String styleClass;
	protected String disabled;
	
	/**
	 * 显示数据键值对
	 * key为保存到DB的值
	 * value为显示在页面上的值
	 * @return
	 */
	protected abstract Map<String,String> getValueMap(); 

	public abstract int doStartTag() throws JspException;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setStyleClass(String styleClass){
		this.styleClass = styleClass;
	}
	public void setChecked(String checked) {
		Object attribute = TaglibHelper.getPageContextAttribute(pageContext, checked);
		if(attribute!=null){
			this.checked = attribute.toString();
		} else {
			this.checked = checked;
		}
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
}
