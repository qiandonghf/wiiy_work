package com.wiiy.commons.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class FileNameTaglib extends TagSupport {

	private String value;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6005323920969299343L;

	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		try {
			if(value.lastIndexOf(".")>-1)
				value = value.substring(0,value.lastIndexOf("."));
			out.print(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setValue(String value) {
		Object attribute = TaglibHelper.getPageContextAttribute(pageContext, value);
		if(attribute!=null){
			this.value = attribute.toString();
		} else {
			this.value = value;
		}
	}

}
