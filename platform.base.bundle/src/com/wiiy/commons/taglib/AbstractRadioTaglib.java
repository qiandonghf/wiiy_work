package com.wiiy.commons.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public abstract class AbstractRadioTaglib extends AbstractChooseTaglib {
	
	protected abstract Map<String,String> getValueMap(); 

	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			StringBuilder sb = new StringBuilder();
			try {
				Map<String,String> map = getValueMap();
				int i = 0;
				for (String key : map.keySet()) {
					sb.append("<label>");
					sb.append("<input ");
					if(styleClass!=null)sb.append("class=\""+styleClass+"\"");
					if(name!=null)sb.append(" name=\""+name+"\"");
					if(checked!=null && checked.equals(key)) sb.append(" checked=\"checked\"");
					if(checked==null){
						if(defaultValue!=null && defaultValue.equals(key)) sb.append(" checked=\"checked\"");
						else if(checked==null && defaultValue==null && i==0)sb.append(" checked=\"checked\"");
					}
					sb.append(" type=\"radio\" value=\""+key+"\" />&nbsp;");
					sb.append(map.get(key));
					sb.append("</label>&nbsp;&nbsp;");
					i++;
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
	
}
