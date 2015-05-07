package com.wiiy.commons.taglib;


public class SearchInputTaglib extends AbstractSearchTaglib{
	
	private String inputClass;
	private String value;
	
	@Override
	protected String getDataHTML() {
		String html = "<input id=\""+id+"\" class=\"data"; 
		if(inputClass!=null){
			html += " "+inputClass;
		}
		html += "\"";
		if(value!=null){
			html += " value=\""+value+"\"";
		}
		html += " />";
		return html;
	}

	public void setInputClass(String inputClass) {
		this.inputClass = inputClass;
	}

	public void setValue(String value) {
		Object attribute = TaglibHelper.getPageContextAttribute(pageContext, value);
		if(attribute!=null){
			this.value = attribute.toString();
		}
	}

}
