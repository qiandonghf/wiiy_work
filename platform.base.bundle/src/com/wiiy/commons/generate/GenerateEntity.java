package com.wiiy.commons.generate;

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * 实体类生成
 * @author my
 * 
 */
public class GenerateEntity {
	
	public static void main(String[] args) throws Exception {
//		GenerateEntity.run(new File("src/Park.hbm.xml"));
		GenerateEntity.run("com.wiiy.core.entity");
	}
	
	/**
	 * @param root 同工程下包名 (如 com.wiiy.entity)
	 */
	public static void run(String root){
		double start = System.nanoTime();
		root = root.replace(".", "/");
		File file = new File("src/"+root);
		root = root.replace("/", ".");
		File[] files = file.listFiles();
		if(files!=null)
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			if(fileName.endsWith(".hbm.xml")){
				run(files[i]);
			}
		}
		double time = (System.nanoTime()-start)/1000/1000/1000;
		time = Math.rint(time*100)/100;
		System.out.println("generate complate (use time : "+time+" s)");
	}
	
	/**
	 * @param file xxx.hbm.xml 文件
	 */
	public static void run(File file){
		try {
			generateEntity(parseHBM(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void generateEntity(ClassHBM hbm){
		
		hbm.implement("java.io.Serializable");
		hbm.building();
		
		String className = hbm.getClassName();
		String packageName = className.substring(0,className.lastIndexOf("."));
		String entityName = className.substring(className.lastIndexOf(".")+1);
		StringBuilder sb = new StringBuilder();
		sb.append("package ").append(packageName).append(";");
		sb.append("\n");
		sb.append(generateImportPackage(hbm.getImportPackageList()));
		sb.append("\n");
		sb.append(generateClassComment(hbm.getClassComment()));
		sb.append("\npublic class ").append(entityName);
		if(hbm.getExtendsName()!=null){
			sb.append(" extends ").append(hbm.getExtendsName().substring(hbm.getExtendsName().lastIndexOf(".")+1));
		}
		if(hbm.getImplementsList()!=null && hbm.getImplementsList().size()>0){
			sb.append(" implements ");
			for (String string : hbm.getImplementsList()) {
				sb.append(string).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(" {");
		sb.append("\n\t/** \n\t * \n\t */ \n\tprivate static final long serialVersionUID = 1L;");
		sb.append(generateField(hbm.getFieldList()));
		sb.append("\n");
		sb.append(generateFieldGetSet(hbm.getFieldList()));
		sb.append("\n}");
		String location = "src/"+packageName.replace('.', '/');
		Generate.createFile(sb.toString(), location, entityName);
	}
	private static String generateClassComment(Map<String,String> map){
		StringBuilder sb = new StringBuilder();
		sb.append("\n/**");
		for (String key : map.keySet()) {
			sb.append("\n * <br/>").append(key).append(" ").append(map.get(key));
		}
		sb.append("\n */");
		return sb.toString();
	}
	private static String generateImportPackage(List<String> packages){
		StringBuilder sb = new StringBuilder();
		for (String key : packages) {
			if(key.startsWith("java"))
				sb.append("\nimport ").append(key).append(";");
		}
		sb.append("\n");
		for (String key : packages) {
			if(key.startsWith("com"))
				sb.append("\nimport ").append(key).append(";");
		}
		for (String key : packages) {
			if(!key.startsWith("java") && !key.startsWith("com"))
				sb.append("\nimport ").append(key).append(";");
		}
		return sb.toString();
	}
	private static String generateField(List<FieldHBM> fields){
		StringBuilder sb = new StringBuilder();
		for (FieldHBM field : fields) {
			if(field.getComment()!=null && field.getComment().length()>0){
				sb.append("\n\t/**");
				sb.append("\n\t").append(" * ").append(field.getComment());
				/*Map<String, String> map = field.getFieldComment(); 
				for (String key : map.keySet()) {
					sb.append("\n\t").append(" * <br/>").append(key).append(" ").append(map.get(key));
				}*/
				sb.append("\n\t */");
				sb.append("\n\t@FieldDescription(\""+field.getComment()+"\")");
			}
			String name = field.getName();
			String type = null;
			if(field.getCollection()!=null){
				String collection = field.getCollection().substring(field.getCollection().lastIndexOf(".")+1);
				type = collection+"<"+field.getType().substring(field.getType().lastIndexOf(".")+1)+">";
			} else {
				type = field.getType().substring(field.getType().lastIndexOf(".")+1);
			}
			sb.append("\n\tprivate ").append(type).append(" ").append(name).append(";");
		}
		return sb.toString();
	}
	private static String generateFieldGetSet(List<FieldHBM> fields){
		StringBuilder sb = new StringBuilder();
		for (FieldHBM field : fields) {
			String name = field.getName();
			String Name = null;
			if(Character.isLowerCase(name.charAt(0)) && Character.isUpperCase(name.charAt(1))){
				Name = name;
			} else {
				Name = name.substring(0,1).toUpperCase()+name.substring(1);
			}
			String type = null;
			if(field.getCollection()!=null){
				String collection = field.getCollection().substring(field.getCollection().lastIndexOf(".")+1);
				type = collection+"<"+field.getType().substring(field.getType().lastIndexOf(".")+1)+">";
			} else {
				type = field.getType().substring(field.getType().lastIndexOf(".")+1);
			}
			if(field.getComment()!=null && field.getComment().length()>0){
				sb.append("\n\t/**");
				sb.append("\n\t * ").append(field.getComment());
				sb.append("\n\t */");
			}
			sb.append("\n\t").append("public ").append(type).append(" get").append(Name).append("(").append("){");
			sb.append("\n\t\treturn ").append(name).append(";");
			sb.append("\n\t}");
			sb.append("\n\t").append("public void set").append(Name).append("(").append(type).append(" ").append(name).append("){");
			sb.append("\n\t\tthis.").append(name).append(" = ").append(name).append(";");
			sb.append("\n\t}");
		}
		return sb.toString();
	}
	
	private static Document getDocument(File file){
		Document doc = null;
		try {
			SAXReader saxReader = new SAXReader();
			saxReader.setValidation(false);
			saxReader.setEntityResolver(new EntityResolver(){
				@Override
				public InputSource resolveEntity(String publicId, String systemId) {
					return new InputSource(new StringReader(""));
				}
			});
			doc = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	@SuppressWarnings("unchecked")
	private static ClassHBM parseHBM(File file) throws DocumentException{
		Document doc = getDocument(file);
		Element root = doc.getRootElement();
		
		Element clazz = root.element("class");
		String className = clazz.attributeValue("name");
		
		ClassHBM chbm = new ClassHBM();
		chbm.setClassName(className);
		
		Map<String,String> classComment = new HashMap<String,String>();
		List<Element> metas = clazz.elements("meta");
		for (Element meta : metas) {
			String name = meta.attributeValue("attribute");
			String value = meta.getText();
			classComment.put(name, value);
		}
		chbm.setClassComment(classComment);
		
		List<FieldHBM> fields = new ArrayList<FieldHBM>();
		
		Element idElement = clazz.element("id");
		String propertyIdName = idElement.attributeValue("name");
		String propertyIdType = idElement.attributeValue("type");
		FieldHBM id = new FieldHBM(propertyIdName, propertyIdType);
		Map<String,String> idComment = new HashMap<String, String>();
		idComment.put("generator", idElement.element("generator").attributeValue("class"));
		id.setFieldComment(idComment);
		fields.add(id);
		
		fields.addAll(parseManyToOne(clazz));
		fields.addAll(parseOneToOne(clazz));
		fields.addAll(parseProperty(clazz));
		fields.addAll(parseSet(clazz));
		
		chbm.setFieldList(fields);
		return chbm;
	}
	private static List<FieldHBM> parseSet(Element clazz){
		List<FieldHBM> fields = new ArrayList<FieldHBM>();
		List<Element> properties = clazz.elements("set");		
		for (Element property : properties) {
			String propertyName = property.attributeValue("name");
			String propertyType = property.element("one-to-many").attributeValue("class");
			FieldHBM field = new FieldHBM(propertyName,propertyType);
			field.setCollection("java.util.Set");
			Map<String,String> fieldComment = new HashMap<String, String>();
			for (Object obj : property.attributes()) {
				Attribute attribute = (Attribute)obj;
				String name = attribute.getName();
				String value = attribute.getValue();
				fieldComment.put(name, value);
			}			
			field.setFieldComment(fieldComment);
			Element column = property.element("key").element("column");
			if(column!=null){
				String columnName = column.attributeValue("name");
				fieldComment.put("column", columnName);
				Element comment = column.element("comment");
				if(comment!=null){
					String commentInfo = comment.getText();
					field.setComment(commentInfo);
				}
			}			
			fields.add(field);
		}
		return fields;
	}
	private static List<FieldHBM> parseOneToOne(Element clazz){
		List<FieldHBM> fields = new ArrayList<FieldHBM>();
		List<Element> properties = clazz.elements("one-to-one");		
		for (Element property : properties) {
			String propertyName = property.attributeValue("name");
			String propertyType = property.attributeValue("class");
			FieldHBM field = new FieldHBM(propertyName,propertyType);
			Map<String,String> fieldComment = new HashMap<String, String>();
			for (Object obj : property.attributes()) {
				Attribute attribute = (Attribute)obj;
				String name = attribute.getName();
				String value = attribute.getValue();
				fieldComment.put(name, value);
			}
			field.setFieldComment(fieldComment);
			field.setComment(property.elementText("meta"));
			fields.add(field);
		}
		return fields;
	}
	private static List<FieldHBM> parseManyToOne(Element clazz){
		List<FieldHBM> fields = new ArrayList<FieldHBM>();
		List<Element> properties = clazz.elements("many-to-one");		
		for (Element property : properties) {
			String propertyName = property.attributeValue("name");
			String propertyType = property.attributeValue("class");
			FieldHBM field = new FieldHBM(propertyName,propertyType);
			Map<String,String> fieldComment = new HashMap<String, String>();
			for (Object obj : property.attributes()) {
				Attribute attribute = (Attribute)obj;
				String name = attribute.getName();
				String value = attribute.getValue();
				fieldComment.put(name, value);
			}
			field.setFieldComment(fieldComment);
			Element column = property.element("column");
			if(column!=null){
				String columnName = column.attributeValue("name");
				fieldComment.put("column", columnName);
				Element comment = column.element("comment");
				if(comment!=null){
					String commentInfo = comment.getText();
					field.setComment(commentInfo);
				}
			}
			fields.add(field);
		}
		return fields;
	}
	private static List<FieldHBM> parseProperty(Element clazz){
		List<FieldHBM> fields = new ArrayList<FieldHBM>();
		List<Element> properties = clazz.elements("property");
		for (Element property : properties) {
			String propertyName = property.attributeValue("name");
			String propertyType = property.attributeValue("type");
			FieldHBM field = new FieldHBM(propertyName,propertyType);
			Map<String,String> fieldComment = new HashMap<String, String>();
			for (Object obj : property.attributes()) {
				Attribute attribute = (Attribute)obj;
				String name = attribute.getName();
				String value = attribute.getValue();
				fieldComment.put(name, value);
			}
			field.setFieldComment(fieldComment);
			Element column = property.element("column");
			if(column!=null){
				String columnName = column.attributeValue("name");
				fieldComment.put("column", columnName);
				Element comment = column.element("comment");
				if(comment!=null){
					String commentInfo = comment.getText();
					field.setComment(commentInfo);
				}
			}
			Element type = property.element("type");
			if(type!=null){
				List<Element> params = type.elements("param");
				for (Element param : params) {
					if(param.attributeValue("name").equals("enumClass")){
						propertyType = param.getText();
						field.setType(propertyType);
					}
				}
			}
			fields.add(field);
		}
		return fields;
	}

}
class ClassHBM{
	private String className;
	private String comment;
	private String extendsName;
	private List<String> implementsList;
	private List<String> importPackageList;
	private Map<String,String> classComment;
	private List<FieldHBM> fieldList;
	public String getClassName() {
		return className;
	}
	public void building() {
		for (int j = 0; j < fieldList.size(); j++) {
			if(fieldList.get(j).getFieldComment().get("gen-property")!=null && fieldList.get(j).getFieldComment().get("gen-property").equals("false")){
				fieldList.remove(j);
			}
		}
		if(classComment.get("extends")!=null){
			extend(classComment.get("extends"));
		}
		if(extendsName!=null){
			try {
				Class<?> parentClass = Class.forName(extendsName);
				while(parentClass!=Object.class){
					Field[] parentFields = parentClass.getDeclaredFields();
					for (int i = 0; i < parentFields.length; i++) {
						for (int j = 0; j < fieldList.size(); j++) {
							if(fieldList.get(j).getName().equals(parentFields[i].getName()) && !fieldList.get(j).getName().equals("id")){
								fieldList.remove(j);
							}
						}
					}
					parentClass = parentClass.getSuperclass();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		generateFieldImportPackage();
		importPackage("com.wiiy.commons.annotation.FieldDescription");
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Map<String, String> getClassComment() {
		return classComment;
	}
	public void setClassComment(Map<String, String> classComment) {
		this.classComment = classComment;
	}
	public List<FieldHBM> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<FieldHBM> fieldList) {
		this.fieldList = fieldList;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getExtendsName() {
		return extendsName;
	}
	public void setExtendsName(String extendsName) {
		this.extendsName = extendsName;
	}
	public List<String> getImplementsList() {
		return implementsList;
	}
	public void setImplementsList(List<String> implementsList) {
		this.implementsList = implementsList;
	}
	public List<String> getImportPackageList() {
		if(importPackageList!=null){
			Collections.sort(importPackageList);
		}
		return importPackageList;
	}
	public void setImportPackageList(List<String> importPackageList) {
		this.importPackageList = importPackageList;
	}
	public void importPackage(String packageName){
		if(importPackageList==null){
			importPackageList = new ArrayList<String>();
		}
		importPackageList.add(packageName);
	}
	public void extend(String extendsName){
		if(extendsName.indexOf(".")>-1){
			importPackage(extendsName);
		}
		this.extendsName = extendsName;
	}
	public void implement(String implement){
		if(implementsList==null){
			implementsList = new ArrayList<String>();
		}
		if(implement.indexOf(".")>-1){
			importPackage(implement);
			implementsList.add(implement.substring(implement.lastIndexOf(".")+1));
		} else {
			implementsList.add(implement);
		}
	}
	public void generateFieldImportPackage(){
		List<String> packageList = new ArrayList<String>();
		oupLoop : for (FieldHBM field : fieldList) {
			String type = field.getType();
			if(type.startsWith("java.lang")){
				continue;
			} else {
				for (String string : packageList) {
					if(string.equals(type)){
						continue oupLoop;
					}
				}
			}
			packageList.add(type);
			if(field.getCollection()!=null){
				String type2 = field.getCollection();
				if(type2.startsWith("java.lang")){
					continue;
				} else {
					for (String string : packageList) {
						if(string.equals(type2)){
							continue oupLoop;
						}
					}
				}
				packageList.add(type2);
			}
		}
		for (String string : packageList) {
			importPackage(string);
		}
	}
}
class FieldHBM{
	private String name;
	private String type;
	private String comment;
	private String collection;
	private Map<String,String> fieldComment;
	public FieldHBM(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, String> getFieldComment() {
		return fieldComment;
	}
	public void setFieldComment(Map<String, String> fieldComment) {
		this.fieldComment = fieldComment;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	
}