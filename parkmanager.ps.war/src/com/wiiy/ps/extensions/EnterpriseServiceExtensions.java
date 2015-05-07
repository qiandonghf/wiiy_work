package com.wiiy.ps.extensions;

import java.util.ArrayList;
import java.util.Collections;
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

import com.wiiy.ps.dto.ServiceDto;

public class EnterpriseServiceExtensions implements IRegistryChangeListener{
	
	private static final Log logger = LogFactory.getLog(EnterpriseServiceExtensions.class);
	private IExtensionRegistry registry;
	private List<IExtension> serviceExtensions = new ArrayList<IExtension>();
	private static final String NAMESPACE="parkmanager.ps.war";
	private static final String EXTENSION_POINT="EnterpriseServiceExtension";
	
	public IExtensionRegistry getRegistry() {
		return registry;
	}
	public void setRegistry(IExtensionRegistry registry) {
		this.registry = registry;
        try {
			registry.addRegistryChangeListener(this, NAMESPACE);
			IExtension[] extensions = registry.getExtensionPoint(NAMESPACE+"."+EXTENSION_POINT).getExtensions();
			//logger.info(NAMESPACE+"."+EXTENSION_POINT+"  init extensions");
			for (int i = 0; i < extensions.length; i++) {
				//logger.info(NAMESPACE+"."+EXTENSION_POINT+"  ADDED extensions:"+extensions[i].getLabel());
				serviceExtensions.add(extensions[i]);
			}
		} catch (InvalidRegistryObjectException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void registryChanged(IRegistryChangeEvent event) {
		IExtensionDelta[] deltas = event.getExtensionDeltas(NAMESPACE,EXTENSION_POINT);
	    if (deltas.length == 0)
	    	return;
	    for (int i = 0; i < deltas.length; i++) {
	    	switch (deltas[i].getKind()) {
	    		case IExtensionDelta.ADDED:
	    			logger.info(NAMESPACE+"."+EXTENSION_POINT+"  ADDED extension:  "+deltas[i].getExtension().getLabel());
	    			serviceExtensions.add(deltas[i].getExtension());
	    			break;
	    		case IExtensionDelta.REMOVED:
	    			logger.info(NAMESPACE+"."+EXTENSION_POINT+"  REMOVED extension:  "+deltas[i].getExtension().getLabel());
	    			serviceExtensions.remove(deltas[i].getExtension());
	    			break;
	    		default:
	    			break;
	    	}
	    }
	}
	
	public List<ServiceDto> getService() {
		List<ServiceDto> serviceList = new ArrayList<ServiceDto>();
		try {
			for (Iterator<IExtension> iter = serviceExtensions.iterator(); iter.hasNext();) {
				IExtension extension = (IExtension) iter.next();
				IConfigurationElement[] elements = extension.getConfigurationElements();
				for (int j = 0; j < elements.length; j++) {
					IConfigurationElement element = elements[j];
					ServiceDto service = new ServiceDto();
					service.setUrl(element.getAttribute("url"));
					service.setName(element.getAttribute("name"));
					service.setIcon(element.getAttribute("icon"));
					service.setDataUrl(element.getAttribute("dataUrl"));
					service.setDescription(element.getAttribute("description"));
					service.setRunAs(element.getAttribute("runAs"));
					service.setFloatboxWidth(element.getAttribute("floatboxWidth"));
					service.setFloatboxHeight(element.getAttribute("floatboxHeight"));
					if(element.getAttribute("order")!=null){
						service.setOrder(Integer.parseInt(element.getAttribute("order")));
					}
					serviceList.add(service);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(NAMESPACE+"."+EXTENSION_POINT+"  addService error ");
		}
		Collections.sort(serviceList);
		return serviceList;
	}
}
