package com.wiiy.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {
	
	public static void copyInputStreamToFile(InputStream in,File file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			byte[] bytes = new byte[1024 * 64];
			int len;
			while ((len = in.read(bytes)) != -1) {
				out.write(bytes, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void copyFileToOutputStream(File file,OutputStream out) {
		try {
			FileInputStream in = new FileInputStream(file);
			byte[] bytes = new byte[1024 * 64];
			int len;
			while ((len = in.read(bytes)) != -1) {
				out.write(bytes, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
