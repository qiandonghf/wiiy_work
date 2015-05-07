package com.wiiy.commons.activator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class CommonsActivator implements BundleActivator {
	private static final Log logger = LogFactory.getLog(CommonsActivator.class);
	public static BundleContext bundleContext;
	
	public void start(BundleContext context) throws Exception {
		bundleContext = context;
		logger.debug("bundle 【platform.base.bundle】 start");
	}

	public void stop(BundleContext context) throws Exception {
		logger.debug("bundle 【platform.base.bundle】 stop");
	}
}
