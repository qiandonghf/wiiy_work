package com.wiiy.hibernate;

import java.io.Serializable;

/**
 * User: Lewis Wang Date: 8/15/11 Time: 10:29 AM
 */
public class Result<T> implements Serializable {

	private boolean success = true;

	private T value;

	private String msg;

	private Result() {
	}

	private Result(String msg) {
		this(msg, true);
	}

	private Result(String msg, Boolean success) {
		this(success,null,msg);
	}

	public Result(boolean success, T value, String msg) {
		super();
		this.success = success;
		this.value = value;
		this.msg = msg;
		
		
		
		
		
	}

	public T getValue() {
		return value;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static <T> Result<T> success() {
		return new Result<T>();
	}

	public static <T> Result<T> success(String msg) {
		return new Result<T>(msg);
	}
	
	public static <T> Result<T> success(String msg, T t) {
		return new Result<T>(true,t,msg);
	}


	public static <T> Result<T> failure(String msg) {
		return new Result<T>(msg, false);
	}

	public static <T> Result<T> failure(String msg, T t) {
		return new Result<T>(false, t, msg);
	}

	public static <T> Result<T> value(T t) {
		return new Result<T>(true,t,"");
	}
}
