package com.wiiy.business.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cipher {
	
    public static String md5(String str) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (NoSuchAlgorithmException e) {  
            System.exit(-1);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
    }  
    public static void main(String[] args) {
		System.out.println(md5("123456"));
		
		/*System.out.println(System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"/"+bundle.getSymbolicName()+"/web/checkHtml/Test.html");*/
		/*File htmlFile = new File(); 
		if(!htmlFile.exists()){
			try {
				htmlFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
*/
		//System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"/"+bundle.getSymbolicName()+"/"+pathBasedBundle
	}
}
