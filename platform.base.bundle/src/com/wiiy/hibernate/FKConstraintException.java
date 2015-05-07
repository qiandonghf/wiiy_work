package com.wiiy.hibernate;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FKConstraintException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4173767723243782530L;
	private String fk;
	
	public FKConstraintException(Throwable e) {
		super(e);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(baos));
		String message = baos.toString();
		fk = getFkFromExceptionMessage(message);
	}

	public FKConstraintException(String message) {
		super(message);
		fk = getFkFromExceptionMessage(message);
	}

	public String getFK() {
		return fk;
	}
	
	public static String getFkFromExceptionMessage(String message) {
		try {
			Pattern p = Pattern.compile("CONSTRAINT `[\\w_]*` FOREIGN KEY");
			Matcher m = p.matcher(message);
			String group = null;
			while(m.find()){
				group = m.group();
				group = group.replace("CONSTRAINT `", "");
				group = group.replace("` FOREIGN KEY", "");
			}
			return group;
		} catch (Exception e) {
			return "";
		}
	}
	
}
