package com.wiiy.hibernate;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UKConstraintException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6119410888985395824L;
	private String uk;
	
	public UKConstraintException(Throwable e) {
		super(e);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(baos));
		String message = baos.toString();
		uk = getUkFromExceptionMessage(message);
	}

	public UKConstraintException(String message) {
		super(message);
		uk = getUkFromExceptionMessage(message);
	}

	public String getUK() {
		return uk;
	}
	
	public static String getUkFromExceptionMessage(String message) {
		Pattern p = Pattern.compile("for key '[\\w_]*'");
		Matcher m = p.matcher(message);
		String group = null;
		while(m.find()){
			group = m.group();
			group = group.substring(group.indexOf("'",group.indexOf("key"))+1,group.lastIndexOf("'"));
		}
		return group;
		
	}
	
}
