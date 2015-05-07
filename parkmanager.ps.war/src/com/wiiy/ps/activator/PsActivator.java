package com.wiiy.ps.activator;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;

import com.wiiy.commons.activator.AbstractActivator;
import com.wiiy.core.entity.User;
import com.wiiy.core.service.export.AppConfig;
import com.wiiy.core.service.export.AppConfigManager;
import com.wiiy.core.service.export.HttpSessionService;
import com.wiiy.core.service.export.ModuleRegister;
import com.wiiy.external.service.OperationLogPubService;

public class PsActivator extends AbstractActivator {
		
	public static BundleContext bundleContext;
	private static AppConfigManager appConfigManager;
	public static final String APP_CONFIG_NAME = "ParkManagement-AppName";	
	
	@Override
	public void startBundle(BundleContext context) throws Exception {
		bundleContext = context;
		addInitService(new ModuleRegister());
		appConfigManager = new AppConfigManager();
		addInitService(appConfigManager);
		
		logger.debug("bundle 【parkmanager.ps.war】 start");
	}

	public static AppConfig getAppConfig() {
		return appConfigManager.getAppConfig();
	}
	
	@Override
	public void stopBundle(BundleContext context) throws Exception {
		logger.debug("bundle 【parkmanager.ps.war】 stop");
	}
	
	@Override
	protected void initDataDict() {
	}
	
	public static HttpSessionService getHttpSessionService(){
		return getService(bundleContext, HttpSessionService.class);
	}
	
	public static User getSessionUser(HttpServletRequest request){
		return getHttpSessionService().getSessionUser(new HttpServletRequest[]{request});
	}

	@Override
	protected void registryFK() {
	}
	public static OperationLogPubService getOperationLogService() {
		OperationLogPubService logService=getService(OperationLogPubService.class);
		String bundleName=bundleContext.getBundle().getHeaders().get(APP_CONFIG_NAME);
		if(bundleName==null)bundleName="默认模块";
		logService.initBundleName(bundleName);
		return logService;
	}
}
