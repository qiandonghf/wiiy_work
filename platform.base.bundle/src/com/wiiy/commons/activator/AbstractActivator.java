package com.wiiy.commons.activator;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.context.BundleContextAware;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import com.wiiy.commons.service.InitService;

public abstract class AbstractActivator implements BundleActivator{
	
	protected CachedLog logger = CachedLog.getLog(getClass());
	
	protected static BundleContext bundleContext;
	
	protected abstract void registryFK();
	protected abstract void initDataDict();

	private List<InitService<?>> initServices = new ArrayList<InitService<?>>();
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		AbstractActivator.bundleContext = bundleContext;
		Bundle bundle = bundleContext.getBundle();
		logger.cleanCache();
		logger.info(bundle.getSymbolicName() + " 开始加载 ...");
		startBundle(bundleContext);
		try {
			logger.info(" 开始加载初始化服务...");
			executeInitService(bundleContext);
			logger.info(" 初始化服务加载完成");
		} catch (Exception e) {
			logger.error(" 初始化服务加载失败", e);
		}
		try {
			logger.info(" 开始注册外键...");
			registryFK();
			logger.info(" 注册外键完成");
		} catch (Exception e) {
			logger.error(" 注册外键失败", e);
		}
		try {
			logger.info(" 开始数据初始化...");
			initDataDict();
			logger.info(" 数据初始化完成");
		} catch (Exception e) {
			logger.error(" 数据初始化失败", e);
		}	
		logger.info(bundle.getSymbolicName() + " 加载成功");
		APPMessageManager.setMessage(bundle.getBundleId(), logger.getCachedMsg());
	}
	
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Bundle bundle = bundleContext.getBundle();
		logger.cleanCache();
		logger.info(bundle.getSymbolicName() + " 开始卸载 ...");
		stopBundle(bundleContext);
		logger.info(bundle.getSymbolicName() + " 卸载成功 ");
		APPMessageManager.setMessage(bundle.getBundleId(), logger.getCachedMsg());
	}
	
	public abstract void startBundle(BundleContext bundleContext) throws Exception;

	public abstract void stopBundle(BundleContext bundleContext) throws Exception;

	private void executeInitService(final BundleContext bundleContext) {
		
		for (InitService<?> initService : initServices) {
			
			if (initService instanceof BundleContextAware) {
				((BundleContextAware)initService).setBundleContext(bundleContext);
			}
			
			Class<?> dependOnServiceClazz = initService.getDependOnServiceClazz();
			
			ServiceReference<?> serviceRef = 
					bundleContext.getServiceReference(dependOnServiceClazz);
			
			if (serviceRef != null) {
				initService.setDependOnService(bundleContext.getService(serviceRef));
				initService.init();
			} else {
				listenDependOnService(bundleContext, initService, dependOnServiceClazz);
			}
		}		
	}
	private void listenDependOnService(
			final BundleContext bundleContext,
			final InitService<?> initService,
			final Class<?> dependOnServiceClazz) {
		
		bundleContext.addServiceListener(new ServiceListener() {
			@Override
			public void serviceChanged(ServiceEvent serviceEvent) {
				if (serviceEvent.getType() == ServiceEvent.REGISTERED){
					ServiceReference<?> serviceReference = serviceEvent.getServiceReference();
					Object registeredService = bundleContext.getService(serviceReference);
					if (dependOnServiceClazz.isInstance(registeredService)) {
						initService.setDependOnService(registeredService);
						initService.init();
					}
				}
			}
		});
	}

	protected void addInitService(InitService<?> initService) {
		initServices.add(initService);
	}
	public static <T> T getService(Class<T> clazz){
		return getService(bundleContext, clazz);
	}
	protected static <T> T getService(BundleContext bundleContext,Class<T> t){
		ServiceReference<T> ref = bundleContext.getServiceReference(t);
		if(ref==null) throw new NullPointerException("ServiceReference"+t.getName());
		return bundleContext.getService(ref);
	}
	
	protected Map<String,List<String>> getFKDescription(BundleContext bundleContext,String entryPaths) {
		logger.info("Start scan FK from bundle : " + bundleContext.getBundle().getSymbolicName());
		long startTime = System.currentTimeMillis();
		Enumeration<String> urls = bundleContext.getBundle().getEntryPaths(entryPaths);
		Map<String,List<String>> map = new HashMap<String, List<String>>();
		while (urls!=null && urls.hasMoreElements()) {
			String url = urls.nextElement();
			if(url.endsWith("hbm.xml")){
				InputStream is = null;
				try {
					is = bundleContext.getBundle().getEntry(url).openStream();
					Document doc = getDocument(is);
					map.put(getClassDescriptionFromHBMFile(doc), getForeignKeyFromHBMFile(doc));
				} catch (IOException e) {
					logger.error("Scan FX error from : " + url, e);
				} finally {
					if(is!=null){
						try {is.close();} catch (IOException e) {}
					}
				}
			}
		}
		logger.info("Scan over : " + (System.currentTimeMillis() - startTime));
		return map;
	}
		
	private String getClassDescriptionFromHBMFile(Document doc){
		Element root = doc.getRootElement();
		Element clazz = root.element("class");
		List<Element> metas = clazz.elements("meta");
		for (Element meta : metas) {
			if(meta.attributeValue("attribute").equals("class-description")){
				return meta.getText();
			}
		}
		return null;
	}
	
	private List<String> getForeignKeyFromHBMFile(Document doc){
		Element root = doc.getRootElement();
		Element clazz = root.element("class");
		List<String> fkList = new ArrayList<String>();
		List<Element> manyToOnes = clazz.elements("many-to-one");
		for (Element manyToOne : manyToOnes) {
			String fk = manyToOne.attributeValue("foreign-key");
			fkList.add(fk);
		}
		List<Element> oneToOnes = clazz.elements("one-to-one");
		for (Element oneToOne : oneToOnes) {
			String fk = oneToOne.attributeValue("foreign-key");
			fkList.add(fk);
		}
		return fkList;
	}
	
	private Document getDocument(InputStream is){
		Document doc = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setValidation(false);
			saxReader.setEntityResolver(new EntityResolver(){
				@Override
				public InputSource resolveEntity(String publicId, String systemId) {
					return new InputSource(new StringReader(""));
				}
			});
			doc = saxReader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public static class CachedLog implements Log {
		
		private Log logger;
		
		private StringWriter cachedMsg = new StringWriter(); 
		
		private PrintWriter printWriter = new PrintWriter(cachedMsg);
		
		private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		public CachedLog(Log logger) {
			this.logger = logger;
		}

		public static CachedLog getLog(Class<?> clazz) {
			Log logger = LogFactory.getLog(clazz);
			return new CachedLog(logger); 
		}

		public void debug(Object arg0, Throwable arg1) {
			logger.debug(arg0, arg1);
		}

		public void debug(Object arg0) {
			logger.debug(arg0);
		}

		public void error(Object arg0, Throwable arg1) {
			cachedMsg.write(render("ERROR",arg0.toString()));
			cachedMsg.write("\n");
			arg1.printStackTrace(printWriter);
			logger.error(arg0, arg1);
		}

		public void error(Object arg0) {
			cachedMsg.write(render("ERROR",arg0.toString()));
			cachedMsg.write("\n");
			logger.error(arg0);
		}

		public void fatal(Object arg0, Throwable arg1) {
			cachedMsg.write(render("FATAL",arg0.toString()));
			cachedMsg.write("\n");
			arg1.printStackTrace(printWriter);
			logger.fatal(arg0, arg1);
		}

		public void fatal(Object arg0) {
			cachedMsg.write(render("FATAL",arg0.toString()));
			cachedMsg.write("\n");
			logger.fatal(arg0);
		}

		public void info(Object arg0, Throwable arg1) {
			cachedMsg.write(render("INFO",arg0.toString()));
			cachedMsg.write("\n");
			arg1.printStackTrace(printWriter);
			logger.info(arg0, arg1);
		}

		public void info(Object arg0) {
			cachedMsg.write(render("INFO",arg0.toString()));
			cachedMsg.write("\n");
			logger.info(arg0);
		}

		public boolean isDebugEnabled() {
			return logger.isDebugEnabled();
		}

		public boolean isErrorEnabled() {
			return logger.isErrorEnabled();
		}

		public boolean isFatalEnabled() {
			return logger.isFatalEnabled();
		}

		public boolean isInfoEnabled() {
			return logger.isInfoEnabled();
		}

		public boolean isTraceEnabled() {
			return logger.isTraceEnabled();
		}

		public boolean isWarnEnabled() {
			return logger.isWarnEnabled();
		}

		public void trace(Object arg0, Throwable arg1) {
			logger.trace(arg0, arg1);
		}

		public void trace(Object arg0) {
			logger.trace(arg0);
		}

		public void warn(Object arg0, Throwable arg1) {
			cachedMsg.write(render("WARN",arg0.toString()));
			cachedMsg.write("\n");
			arg1.printStackTrace(printWriter);
			logger.warn(arg0, arg1);
		}

		public void warn(Object arg0) {
			cachedMsg.write(render("WARN",arg0.toString()));
			cachedMsg.write("\n");
			logger.warn(arg0);
		}
		
		private String render(String level, String msg) {
			return "&nbsp;"+dateFormat.format(new Date()) + "&nbsp;[" + level + "]&nbsp;" + msg;
		}

		public void cleanCache() {
			cachedMsg = new StringWriter();
			printWriter = new PrintWriter(cachedMsg);
		}
		
		public String getCachedMsg() {
			return  cachedMsg.toString();
		}
	}
}

