package com.wiiy.hibernate;

import org.hibernate.SessionFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SessionServiceImpl implements SessionService {
	
	private SessionFactory sessionFactory;
	private ComboPooledDataSource ds;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public ComboPooledDataSource getDs() {
		return ds;
	}

	public void setDs(ComboPooledDataSource ds) {
		this.ds = ds;
	}


}
