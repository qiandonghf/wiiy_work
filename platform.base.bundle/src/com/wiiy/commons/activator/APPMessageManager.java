package com.wiiy.commons.activator;

import java.util.HashMap;
import java.util.Map;

public class APPMessageManager {

	private static final Map<Long, String> appMsgs = new HashMap<Long, String>();
	
	public static String getMessage(Long bundleId) {
		return appMsgs.get(bundleId);
	}

	public static void setMessage(Long bundleId, String msg) {
		appMsgs.put(bundleId, msg);
	}
}
