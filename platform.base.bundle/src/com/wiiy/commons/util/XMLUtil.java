package com.wiiy.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLUtil {
	
	public static Document loadDocument(File file) {

		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

		Document doc = null;
		try {
			doc = new SAXReader().read(is);
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		} finally {
			if (is != null) {
				try { is.close();} catch(Exception e) {}
			}
		}
		return doc;
	}
	
	public static void writeDocument(File docFile, Document document) {

		if (docFile.exists()) {
			docFile.delete();
			try {
				docFile.createNewFile();
			} catch (IOException e) {
				throw new IllegalStateException("Can't update file : " + docFile);
			}
		}

		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(docFile));
	        writer.write(document);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			if (writer != null) {
				try { writer.close(); } catch (IOException e) {}
			}
		}
	}
}
