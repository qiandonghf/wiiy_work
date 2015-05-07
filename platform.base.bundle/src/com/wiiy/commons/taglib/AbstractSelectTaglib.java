package com.wiiy.commons.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public abstract class AbstractSelectTaglib extends AbstractChooseTaglib {
	
	private String id;
	
	protected abstract Map<String,String> getValueMap(); 

	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("<select ");
			if(disabled!=null) sb.append(" disabled=\"disabled\"");
			if(styleClass!=null)sb.append("class=\""+styleClass+"\"");
			if(id!=null)sb.append(" id=\""+id+"\" ");
			if(name!=null)sb.append(" name=\""+name+"\"");
			sb.append(">");
			try {
				sb.append("<option value=\"\">----请选择----</option>");
				Map<String,String> map = getValueMap();
				for (String key : map.keySet()) {
					sb.append("<option value=\""+key+"\"");
					if(checked!=null && checked.equals(key)) sb.append(" selected=\"selected\"");
					else if(defaultValue!=null && defaultValue.equals(key)) sb.append(" selected=\"selected\"");
					sb.append(" >");
					sb.append(map.get(key));
					sb.append("</option>");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sb.append("</select>");
			out.print(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
