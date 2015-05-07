package com.wiiy.commons.service;

import java.lang.reflect.ParameterizedType;

public abstract class InitService<T> {
	
	private Class<T> dependOnServiceClazz;
	
	protected T dependOnService;
	
	@SuppressWarnings("unchecked")
	protected InitService() {
		dependOnServiceClazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public abstract void init();
	
	@SuppressWarnings("unchecked")
	public void setDependOnService(Object dependOnService) {
		this.dependOnService = (T)dependOnService;
	}
	
	protected T getDependOnService() {
		return dependOnService;
	}
	
	public Class<T> getDependOnServiceClazz() {
		return dependOnServiceClazz;
	}
}
