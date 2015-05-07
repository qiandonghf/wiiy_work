package com.wiiy.commons.taglib;

import javax.servlet.jsp.PageContext;

public class TaglibHelper {
	
	/**
	 * 获取页面上下文中的属性 从session 或 request中
	 * @param pageContext
	 * @param scopeProperty
	 * @return
	 */
	public static Object getPageContextAttribute(PageContext pageContext,String property){
		if(property!=null){
			boolean sessionScope = false;
			if(property.startsWith("sessionScope")){
				property = property.substring(property.indexOf(".")+1);
				sessionScope = true;
			}
			String[] checkeds = property.split("\\.");
			Object object = null;
			if(sessionScope){
				object = pageContext.getSession().getAttribute(checkeds[0]);
			} else {
				object = pageContext.getRequest().getAttribute(checkeds[0]);
				if(object==null){
					object = pageContext.getAttribute(checkeds[0]);
					if(object==null) return null;
				}
			}
			for (int i = 1; i < checkeds.length; i++) {
				try {
					String methodName = "get"+checkeds[i].substring(0,1).toUpperCase()+checkeds[i].substring(1);
					object = object.getClass().getMethod(methodName).invoke(object);
					if(object==null) return null;
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			return object;
		}
		return null;
	}

}
