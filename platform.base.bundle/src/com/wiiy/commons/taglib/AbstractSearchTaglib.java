package com.wiiy.commons.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class AbstractSearchTaglib extends TagSupport {
	
	private final String[] ops = {"eq","ne","lt","le","gt","ge","bw","bn","ew","en","cn","nc","in","ni"};
	
	protected String id;
	private String field;
	private String op;
	private String dataType;
	
	protected abstract String getDataHTML();
	
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("<input ");
			if(id!=null){
				sb.append("id=\""+id+"\" ");
			}
			sb.append("class=\"field\" type=\"hidden\" value=\""+field+"\"/>");
			sb.append("<input ");
			if(id!=null){
				sb.append("id=\""+id+"\" ");
			}
			sb.append("class=\"op\" type=\"hidden\" value=\""+op+"\"/>");
			sb.append("<input ");
			if(id!=null){
				sb.append("id=\""+id+"\" ");
			}
			sb.append("class=\"dataType\" type=\"hidden\" value=\""+dataType+"\"/>");
			sb.append(getDataHTML());
			out.print(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	};

	public void setField(String field) {
		this.field = field;
	}

	public void setOp(String op) {
		boolean opexist = false;
		for (int i = 0; i < ops.length; i++) {
			if(ops[i].equals(op)){
				opexist = true;
				break;
			}
		}
		if(!opexist) throw new RuntimeException(op+"操作符非法");
		this.op = op;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
