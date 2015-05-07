package com.wiiy.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.wiiy.commons.annotation.FieldDescription;

public class BeanUtil {
	
	/**
	 * 返回一个空的对象 在反射执行set方法参数为null时使用(调用invoke传入null会与动态参数充突 引发异常 必须显式声明一个对象为null再做参数传入)
	 * @return
	 */
	public static Object emptyObject(){
		return null;
	}
	
	public static String getColumnNameByFieldName(String fieldName){
		StringBuilder sb = new StringBuilder();
		char[] chars = fieldName.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if(Character.isUpperCase(chars[i])){
				sb.append("_");
				sb.append(Character.toLowerCase(chars[i]));
			} else {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 根据数据库列名获取对应实体字体的描述信息 用于UKConstraintException
	 * @param columnName 列名
	 * @param clazz 实体
	 * @return
	 */
	public static String getFieldDescriptionByColumnName(String columnName,Class<?> beanClass){
		StringBuilder sb = new StringBuilder();
		char[] chars = columnName.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if(chars[i]=='_'){
				if(i+1<chars.length){
					chars[i+1] = Character.toUpperCase(chars[i+1]);
					continue;
				}
				continue;
			}
			sb.append(chars[i]);
		}
		return getFieldDescription(beanClass,sb.toString());
	}
	/**
	 * 获取字段描述信息
	 * @param beanClass
	 * @param fieldName
	 * @return
	 */
	public static String getFieldDescription(Class<?> beanClass, String fieldName){
		try {
			Field field = beanClass.getDeclaredField(fieldName);
			if(field.isAnnotationPresent(FieldDescription.class)){
				FieldDescription description = field.getAnnotation(FieldDescription.class);
				return description.value();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return fieldName;
	}
	public static void copyProperties(Object source,Object target, String... requiredCopys){
		Method[] sourceMethods = source.getClass().getMethods();
		Method[] targetMethods = target.getClass().getMethods();
		for (int i = 0; i < targetMethods.length; i++) {
			Method targetMethod = targetMethods[i];
			if(!targetMethod.getName().startsWith("set"))continue;
			String field = targetMethod.getName().substring(3);
			for (int j = 0; j < sourceMethods.length; j++) {
				Method sourceMethod = sourceMethods[j];
				if(!sourceMethod.getName().equals("get"+field))continue;
				try {
					Object result = sourceMethod.invoke(source);
					if(result==null && !Arrays.asList(requiredCopys).contains(field.substring(0,1).toLowerCase()+field.substring(1))) continue;
					targetMethod.invoke(target, result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(source.getClass().getSuperclass()!=Object.class && target.getClass().getSuperclass()!=Object.class){
			copyProperties(source.getClass().getSuperclass(),target.getClass().getSuperclass());
		}

	}
	public static void main(String[] args) {
		System.out.println(getColumnNameByFieldName("createId"));
	}
}
