package com.wiiy.commons.generate;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 基础代码生成
 * @author my
 *
 */
public class GenerateCode {
	
	private Class<?> clazz;
	private String packageName;
	
	public static void main(String[] args) throws Exception {
//		GenerateCode.run(Floor.class);
		GenerateCode.run("com.wiiy.commons.entity");
	}
	
	/**
	 * @param root 同工程下类包名 (如 com.wiiy.entity)
	 */
	public static void run(String root){
		double start = System.nanoTime();
		root = root.replace(".", "/");
		File folder = new File("src/"+root);
		root = root.replace("/", ".");
		File[] files = folder.listFiles();
		if(files!=null)
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String fileName = file.getName();
			if(fileName.endsWith(".java")){
				String className = fileName.substring(0,fileName.indexOf(".java"));
				File hbm = new File(file.getPath().substring(0,file.getPath().lastIndexOf("\\")+1)+className+".hbm.xml");
				if(!hbm.exists())continue;
				try {
					Class<?> c = Class.forName(root+"."+className);
					GenerateCode.run(c);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		double time = (System.nanoTime()-start)/10000000;
		time = Math.rint(time*100)/100;
		System.out.println("generate complate (use time : "+time+" ms)");
	}
	
	/**
	 * @param bean 类
	 */
	public static void run(Class<?> bean){
		GenerateCode generate = new GenerateCode(bean);
		generate.createAllFile();
		generate.printSpringXML();
//		generate.printStrutsXML();
	}
	
	public static void printSpringXMLCode(Class<?> bean){
		new GenerateCode(bean).printSpringXML();
	}
	public static void printSpringXMLCode(String root){
		root = root.replace(".", "/");
		File folder = new File("src/"+root);
		root = root.replace("/", ".");
		File[] files = folder.listFiles();
		if(files!=null)
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String fileName = file.getName();
			if(fileName.endsWith(".java")){
				String className = fileName.substring(0,fileName.indexOf(".java"));
				File hbm = new File(file.getPath().substring(0,file.getPath().lastIndexOf("\\")+1)+className+".hbm.xml");
				if(!hbm.exists())continue;
				try {
					Class<?> c = Class.forName(root+"."+className);
					printSpringXMLCode(c);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void createAllFile(){
		createDao();
		createService();
		createServiceImpl();
		createAction();
	}
	private void printSpringXML(){
		println(getSpringXML());
	}
	private void printStrutsXML(){
		println(getStrutsXML());
	}
	private void println(String code){
		System.out.println(code);
	}
	private GenerateCode(Class<?> clazz) {
		this.clazz = clazz;
		packageName = clazz.getName().replace("."+clazz.getSimpleName(), "");
		packageName = packageName.substring(0,packageName.lastIndexOf("."));
	}
	private String getStrutsXML() {
		StringBuffer sb = new StringBuffer();
		String beanName = clazz.getSimpleName();
		String bean = beanName.substring(0,1).toLowerCase()+beanName.substring(1);
		sb.append("\t\t<action name=\"").append(bean).append("\" class=\"").append(bean).append("Action\">");
		sb.append("\n\t\t\t").append("<result name=\"view\">").append(bean).append("View.jsp</result>");
		sb.append("\n\t\t\t").append("<result name=\"edit\">").append(bean).append("Edit.jsp</result>");
		sb.append("\n\t\t\t").append("<result name=\"save\">").append(bean).append("Add.jsp</result>");
		sb.append("\n\t\t</action>");
		return sb.toString();
	}
	private String getSpringXML() {
		StringBuffer sb = new StringBuffer();
		String beanName = clazz.getSimpleName();
		String bean = beanName.substring(0,1).toLowerCase()+beanName.substring(1);
		sb.append("\n\t<bean id=\"").append(bean).append("Dao\" class=\"").append(packageName).append(".dao.").append(beanName).append("Dao\">");
		sb.append("\n\t\t").append("<property name=\"sessionService\" ref=\"sessionService\"></property>");
		sb.append("\n\t</bean>");
		sb.append("\n\t<bean id=\"").append(bean).append("Service\" class=\"").append(packageName).append(".service.impl.").append(beanName).append("ServiceImpl\">");
		sb.append("\n\t\t").append("<property name=\"").append(bean).append("Dao\" ref=\"").append(bean).append("Dao\"></property>");
		sb.append("\n\t</bean>");
		sb.append("\n\t<bean id=\"").append(bean).append("Action\" class=\"").append(packageName).append(".action.").append(beanName).append("Action\" scope=\"prototype\">");
		sb.append("\n\t\t").append("<property name=\"").append(bean).append("Service\" ref=\"").append(bean).append("Service\"></property>");
		sb.append("\n\t</bean>");
		return sb.toString();
	}
	private boolean createAction(){
		Entity entity = new Entity(clazz);
		entity.importPackage(packageName+".service."+clazz.getSimpleName()+"Service");
		String code = process(entity,"Action");
		String location = "src/"+code.substring(code.indexOf(' ')+1,code.indexOf(';')).replace('.', '/');
		String fileName = clazz.getSimpleName()+"Action";
		return createFile(code,location,fileName);
	}
	private boolean createService(){
		Entity entity = new Entity(clazz);
		String code = process(entity,"Service");
		String location = "src/"+code.substring(code.indexOf(' ')+1,code.indexOf(';')).replace('.', '/');
		String fileName = clazz.getSimpleName()+"Service";
		return createFile(code,location,fileName);
	}
	private boolean createServiceImpl(){
		Entity entity = new Entity(clazz);
		entity.importPackage(packageName+".dao."+clazz.getSimpleName()+"Dao");
		entity.importPackage(packageName+".service."+clazz.getSimpleName()+"Service");
		entity.importPackage(packageName+".preferences.R");
		entity.importPackage(packageName+".activator."+entity.getModuleName()+"Activator");
		String code = process(entity,"ServiceImpl");
		String location = "src/"+code.substring(code.indexOf(' ')+1,code.indexOf(';')).replace('.', '/');
		String fileName = clazz.getSimpleName()+"ServiceImpl";
		return createFile(code,location,fileName);
	}
	private boolean createDao(){
		Entity entity = new Entity(clazz);
		String code = process(entity,"Dao");
		String location = "src/"+code.substring(code.indexOf(' ')+1,code.indexOf(';')).replace('.', '/');
		String fileName = clazz.getSimpleName()+"Dao";
		return createFile(code,location,fileName);
	}
	private String process(Entity entity,String template){
		Configuration cfg = null;
		try {
			cfg = new Configuration();
		} catch (Exception e) {
		}
		try {
			String path = GenerateCode.class.getName().substring(0,getClass().getName().lastIndexOf(".")).replace(".", "/");
			String dir = GenerateCode.class.getClassLoader().getResource("").getFile();
			dir = dir.substring(0,dir.lastIndexOf("/"));
			dir = dir.substring(0,dir.lastIndexOf("/"));
			dir += "/src/"+path;
			File directory = new File(dir);
			cfg.setDirectoryForTemplateLoading(directory);
			Template t = cfg.getTemplate(template+".jt");
			Writer out = new StringWriter();
			t.process(entity, out);
			return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	private boolean createFile(String code,String location,String fileName){
		return Generate.createFile(code, location, fileName);
	}
}