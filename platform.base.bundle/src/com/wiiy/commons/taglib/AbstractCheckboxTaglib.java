package com.wiiy.commons.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public abstract class AbstractCheckboxTaglib extends AbstractChooseTaglib {
	
	protected abstract Map<String,String> getValueMap(); 

	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			StringBuilder sb = new StringBuilder();
			try {
				Map<String,String> map = getValueMap();
				for (String key : map.keySet()) {
					sb.append("<label>");
					sb.append("<input ");
					if(styleClass!=null)sb.append(" class=\""+styleClass+"\"");
					if(name!=null)sb.append(" name=\""+name+"\"");
					if(checked(key)) sb.append(" checked=\"checked\"");
					else if(defaultValue!=null && defaultValue.equals(key)) sb.append(" checked=\"checked\"");
					sb.append(" type=\"checkbox\" value=\""+key+"\" />&nbsp;");
					sb.append(map.get(key));
					sb.append("</label>&nbsp;&nbsp;");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.print(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}

	private boolean checked(String key) {
		if(checked==null) return false;
		if(checked.indexOf(",")==-1){
			return checked.equals(key);
		} else {
			String[] values = checked.split(",");
			boolean exist = false;
			for (String value : values) {
				if(value.equals(key)){
					exist = true;
					break;
				}
			}
			return exist;
		}
	}
	
}
