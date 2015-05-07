package com.wiiy.sms.boyong.service;

import java.io.UnsupportedEncodingException;

import com.wiiy.external.service.SMSSender;

public class BoYongSMSSender implements SMSSender {

	private String sn;
	private String pwd;
	private Client client;
	
	private void initClient() {
		if(client==null) {
			try {
				client = new Client(sn, pwd);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public int sendSMS(String[] mobiles, String content) {
		initClient();
		//获取余额 参 数
		String balance = client.getBalance();
		if(Integer.valueOf(balance)>0){
			StringBuilder sb = new StringBuilder();
			for (String string : mobiles) {
				sb.append(string).append(",");
			}
			if(sb.length()>0){
				sb.deleteCharAt(sb.length()-1);
			}
			System.out.println("BoYongSMSSender.sendSMS()"+"  "+content);
			String result_mt = client.mt(sb.toString(), content, "", "", "");
			// 以负号判断是否发送成功
			if (result_mt.startsWith("-")) {
				System.out.print("发送失败！返回值为：" + result_mt + "请查看webservice返回值对照表");
				return Integer.parseInt(result_mt);
			} else {
				return 1;
			}
		}else{
			return 0;
		}
	}

	@Override
	public int sendSMS(String[] mobiles, String content, String addSerial) {
		return sendSMS(mobiles, content);
	}

}
