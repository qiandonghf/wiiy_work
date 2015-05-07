package com.wiiy.hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

public class MySessionFactory extends LocalSessionFactoryBean implements IRegistryChangeListener {
	private static final Log logger = LogFactory.getLog(MySessionFactory.class);
	
	private IExtensionRegistry registry;
	private List<IExtension> poExtensions = new ArrayList<IExtension>();
	private static final String NAMESPACE="platform.base.bundle";
	private static final String EXTENSION_POINT_PO="HibernateExtension";
	private Configuration configuration;
	
	public IExtensionRegistry getRegistry() {
		return registry;
	}
	public void setRegistry(IExtensionRegistry registry) {
		this.registry = registry;
		registry.addRegistryChangeListener(this, NAMESPACE);
		hibernatePoRegistry();
	}
	
	public void hibernatePoRegistry(){
		try {
			IExtension[] extensions = registry.getExtensionPoint(NAMESPACE+"."+EXTENSION_POINT_PO).getExtensions();
			for (int i = 0; i < extensions.length; i++) {
			    poExtensions.add(extensions[i]);
			}
		} catch (InvalidRegistryObjectException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void registryChanged(IRegistryChangeEvent event) {
		hibernatePoRegistryChanged(event);
	}
	
	public void hibernatePoRegistryChanged(IRegistryChangeEvent event){
		IExtensionDelta[] deltas = event.getExtensionDeltas(NAMESPACE,EXTENSION_POINT_PO);
	    if (deltas.length == 0)
	    	return;
	    for (int i = 0; i < deltas.length; i++) {
	    	switch (deltas[i].getKind()) {
	    		case IExtensionDelta.ADDED:
	    			logger.debug(NAMESPACE+"."+EXTENSION_POINT_PO+"  ADDED extension:  "+deltas[i].getExtension().getLabel());
	    			poExtensions.add(deltas[i].getExtension());
	    			break;
	    		case IExtensionDelta.REMOVED:
	    			logger.debug(NAMESPACE+"."+EXTENSION_POINT_PO+"  REMOVED extension:  "+deltas[i].getExtension().getLabel());
	    			poExtensions.remove(deltas[i].getExtension());
	    			break;
	    		default:
	    			break;
	    	}
	    }
	}
	
	
	@Override
	protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
		// TODO Auto-generated method stub
		System.out.println("MySessionFactory.buildSessionFactory()***************************************************************************");
		this.configuration = this.getConfiguration();
		loadHibernatePoConfig();
		
		return super.buildSessionFactory(sfb);
	}
	@Override
	public void afterPropertiesSet() throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println("MySessionFactory.afterPropertiesSet()***************************************************************************");
		super.afterPropertiesSet();
	}
//	protected void postProcessConfiguration(Configuration config) throws HibernateException {
//		this.configuration = config;
//		loadHibernatePoConfig();
//	}

	private void loadHibernatePoConfig() {
		Class<? extends Object> poClass = null;
		try {
			for (Iterator<IExtension> iter = poExtensions.iterator(); iter.hasNext();) {
				IExtension extension = (IExtension) iter.next();
				IConfigurationElement[] elements = extension.getConfigurationElements();
				for (int j = 0; j < elements.length; j++) {
					poClass = elements[j].createExecutableExtension("class").getClass();
					configuration.addClass(poClass);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
