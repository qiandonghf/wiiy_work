package com.wiiy.hibernate;

import org.hibernate.SessionFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public interface SessionService {
	
	public SessionFactory getSessionFactory();
	
	public ComboPooledDataSource getDs();
}
