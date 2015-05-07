package com.wiiy.commons.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
	
	public static <T> List<T> subList(List<T> list, int offset, int length){
		List<T> subList = new ArrayList<T>();
		for (int i = 0; i < length; i++) {
			if(i+offset<list.size()){
				subList.add(list.get(i+offset));
			}
		}
		return subList;
	}
	
}
