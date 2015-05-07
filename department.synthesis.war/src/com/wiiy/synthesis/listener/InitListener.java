package com.wiiy.synthesis.listener;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wiiy.synthesis.job.CarAnnualJob;
import com.wiiy.synthesis.job.CarInsuranceJob;
import com.wiiy.synthesis.job.RepeatJob;

public class InitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		new Timer().schedule(new JobTask(e), 1000*60);//延迟启动
	}
	
	class JobTask extends TimerTask{
		
		private ServletContextEvent servletContextEvent;
		private ApplicationContext applicationContext;
		private Timer timer;
		private Queue<RepeatJob> taskQueue = new LinkedList<RepeatJob>();
		
		public JobTask(ServletContextEvent servletContextEvent) {
			this.servletContextEvent = servletContextEvent;
			applicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContextEvent
							.getServletContext());
			//保险到期提醒
			taskQueue.add(new CarInsuranceJob(applicationContext));
			//年检到期提醒
			taskQueue.add(new CarAnnualJob(applicationContext));
		}
		@Override
		public void run() {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					while (!taskQueue.isEmpty()) {
						RepeatJob job = taskQueue.peek();
						try {
							job.start();
							taskQueue.poll();
							System.out.println("--------------------");
							System.out.println("JOB启动成功");
							System.out.println("--------------------");
						} catch (Throwable e) {
							System.err.println("--------------------");
							System.out.println("JOB启动失败，稍后再次启动");
							System.err.println("--------------------");
							// 跳出等待
							break;
						}
					}
					timer.cancel();
				}
			}, 1000, 1000*60);
		}
	}
}
