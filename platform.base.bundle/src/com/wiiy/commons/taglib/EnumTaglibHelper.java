package com.wiiy.commons.taglib;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnumTaglibHelper {
	public static Map<String, String> getValueMapByType(String type){
		Map<String,String> map = new LinkedHashMap<String, String>();
		try {
			Class<?> c = Class.forName(type);
			Enum[] es = (Enum[]) c.getEnumConstants();
			for (int i = 0; i < es.length; i++) {
				if(es[i].getClass().getMethod("getTitle")!=null){
					map.put(es[i].name(),(String) es[i].getClass().getMethod("getTitle").invoke(es[i]));
				} else {
					map.put(es[i].name(),es[i].name());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
