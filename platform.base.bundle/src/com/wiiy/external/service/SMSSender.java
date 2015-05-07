package com.wiiy.external.service;

public interface SMSSender {
	
	int sendSMS(String[] mobiles, String content);

	int sendSMS(String[] mobiles, String content, String addSerial);
}
