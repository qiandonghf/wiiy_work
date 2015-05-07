package com.wiiy.commons.taglib;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class FileSizeTaglib extends TagSupport {
	
	private String size;
	private String unit;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6005323920969299343L;
	
	public int doStartTag(){
		JspWriter out = pageContext.getOut();
		try {
			String[] units = new String[]{"B","KB","MB","GB"};
			Double value = Double.parseDouble(size);
			int i = 0;
			while(value/1024>1){
				value = value/1024;
				i++;
			}
			if(unit!=null){
				for (int j = 0; j < units.length; j++) {
					if(unit.equals(units[i])){
						i = i+j;
					}
				}
			}
			out.print(new DecimalFormat("#.##").format(value)+units[i]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setSize(String size) {
		try {
			this.size = ""+Double.parseDouble(size);
		} catch (Exception e) {
			this.size = TaglibHelper.getPageContextAttribute(pageContext,size).toString();
		}
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
