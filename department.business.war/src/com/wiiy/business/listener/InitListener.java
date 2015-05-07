package com.wiiy.business.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		new Timer().schedule(new JobTask(e), 1000*60);
	}
	
	class JobTask extends TimerTask{
		
		private ServletContextEvent servletContextEvent;
		public JobTask(ServletContextEvent servletContextEvent) {
			this.servletContextEvent = servletContextEvent;
		}
		@Override
		public void run() {
			System.out.println("-----------------------------------------");
			System.out.println("InitListener.contextInitialized()");
			System.out.println("-----------------------------------------");
		}
		
	}

}
