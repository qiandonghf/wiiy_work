package com.wiiy.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 * 查询器
 * @author my
 *
 */
public final class Query {
	private static final Log logger = LogFactory.getLog(Query.class);
	private Query() {
	}
	private static void debug(Object o){
		//logger.debug(o);
	}
	/**
	 * 查询全部结果集 
	 */
	public static List<?> getList(Class<?> c, Session session) {
		List<?> result = null;
		Criteria criteria = session.createCriteria(c);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		result = criteria.list();
		debug("select list from "+c.getName());
		return result;
	}
	
	/**
	 * 通过Filter查询结果集
	 */
	@SuppressWarnings("unchecked")
	public static List<?> getListByFilter(Filter filter, Session session) {
		if(filter.hasPager()){
			filter.setTotal(filter.getRowCount(session));
		}
		List<Object> result = null;
		Criteria criteria = session.createCriteria(filter.getBeanClass());
		/*if(!filter.hasOrder()){
			for (int i = 0; i < filter.getBeanClass().getDeclaredFields().length; i++) {
				if(filter.getBeanClass().getDeclaredFields()[i].getName().equals("createTime")){
					criteria.addOrder(Order.desc("createTime"));
					break;
				}
			}
		}*/
		filter.filter(criteria);
		filter.limit(criteria);
		if(filter.getIncludeFields().length>0){
			String[] properties = filter.getIncludeFields();
			ProjectionList projectionList = Projections.projectionList();
			for (int i = 0; i < properties.length; i++) {
				projectionList.add(Projections.property(properties[i]));
			}
			criteria.setProjection(projectionList);
			List<?> objects = criteria.list();
			result = new ArrayList<Object>();
			String[] fields = convertAliasToFields(filter.getAlias(), properties);
			for (int i = 0; i < objects.size(); i++) {
				try {
					Object obj = objects.get(i);
					Object[] results = null;
					if(obj.getClass().isArray()){
						results = (Object[])objects.get(i);
					}else{
						results = new Object[1];
						results[0] = obj;
					}
					Object object = filter.getBeanClass().newInstance();
					handleResult(object, fields, results);
					result.add(object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else 
			result = criteria.list();
		debug("select list from "+filter.getBeanClass().getName());
		return result;
	}
	
	private static String[] convertAliasToFields(Map<String,String> alias, String[] properties) {
		Map<String,String> convertAlias = new HashMap<String,String>();
		for (String key : alias.keySet()) {
			convertAlias.put(alias.get(key), key);
		}
		String[] fields = new String[properties.length];
		for (int i = 0; i < properties.length; i++) {
			String field = properties[i];
			if(properties[i].indexOf(".")>-1) {
				String[] pp = properties[i].split("\\.");
				String first = pp[0];
				String second = pp[1];
				field = second;
				String f = convertAlias.get(first);
				while(f.indexOf(".")>0) {
					pp = f.split("\\.");
					first = pp[0];
					second = pp[1];
					field = second + "." + field;
					f = convertAlias.get(first);
				}
				field = f + "." + second;
			} else if(convertAlias.containsKey(field)) {
				field = convertAlias.get(field);
			}
			fields[i] = field;
		}
		return fields;
	}
	
	private static void handleResult(Object object, String[] properties, Object[] results) throws Exception {
		for (int i = 0; i < properties.length; i++) {
			String property = properties[i];
			Object value = results[i];
			handleResult(object,property,value);
		}
	}
	
	private static void handleResult(Object object, String property, Object value) throws Exception {
		if(property.indexOf(".")>-1) {
			String first = property.split("\\.")[0];
			Object firstBean = invokeReadMethod(object, first);
			if(firstBean==null) {
				invokeWriteMethodInit(object, first);
				firstBean = invokeReadMethod(object, first);
			}
			handleResult(firstBean, property.substring(property.indexOf(".")+1), value);
		} else {
			invokeWriteMethod(object, property, value);
		}
	}

	private static Object invokeReadMethod(Object bean, String property) throws Exception {
		Class<?> clazz = bean.getClass();
		property = convertFirstToUpperCase(property);
		Method method = clazz.getDeclaredMethod("get"+property);
		return method.invoke(bean);
	}
	
	private static void invokeWriteMethodInit(Object bean, String property) throws Exception {
		Class<?> clazz = bean.getClass();
		Field field = clazz.getDeclaredField(property);
		invokeWriteMethod(bean, property, field.getType().newInstance());
	}
	
	private static void invokeWriteMethod(Object bean, String property, Object value){
		Class<?> clazz = bean.getClass();
		Field field;
		try {
			field = clazz.getDeclaredField(property);
			property = convertFirstToUpperCase(property);
			Method method = clazz.getDeclaredMethod("set"+property, field.getType());
			method.invoke(bean, value);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			Class<?> superClazz = clazz.getSuperclass();
			if(superClazz!=Object.class){
				superClazz(superClazz,property,value,bean);
			}else{
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void superClazz(Class<?> clazz, String property,
			Object value,Object bean) {
		try {
			Field field = clazz.getDeclaredField(property);
			property = convertFirstToUpperCase(property);
			Method method = clazz.getDeclaredMethod("set"+property, field.getType());
			method.invoke(bean, value);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			Class<?> superClazz = clazz.getSuperclass();
			if(superClazz!=Object.class){
				superClazz(superClazz,property,value,bean);
			}else{
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	private static String convertFirstToUpperCase(String s){
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}

	
	/**
	 * 通过HQL查询结果集
	 */
	public static List<?> getListByHql(String hql, Session session) {
		List<?> result = null;
		result = session.createQuery(hql).list();
		debug("select list use hql: "+hql);
		return result;
	}
	
	/**
	 * 通过SQL查询结果集
	 */
	public static List<?> getListBySql(String sql, Session session) {
		List<?> result = null;
		result = session.createSQLQuery(sql).list();
		debug("select list use sql: "+sql);
		return result;
	}
	
	/**
	 * 通过HQL查询结果集
	 * @param beanCreator 对象创建方式 如  new User(id,name,pass) from User 或者 from User;
	 * @param conditions 条件 不要加where只写where后面条件 如 name = '张三' && pass = 'ZhangSan';
	 * @param pager 传入分页条件 查询完成后 会将总条数总页数封装到pager中
	 * @return
	 */
	/*public static List<?> getListByHql(String beanCreator, String conditions, Pager pager, Session session) {
		List<?> result = null;
		String rowcount = "select count(*) from "+beanCreator.split(" ")[beanCreator.split(" ").length-1];
		Object count = session.createQuery(rowcount).uniqueResult();
		if(count != null)pager.setTotal((Integer)count);
		String hql = "select "+beanCreator+" where "+conditions;
		org.hibernate.Query query = session.createQuery(hql);
		if(pager!=null){
			if(pager.getPageIndex()>0)
				query.setFirstResult(pager.getPageSize()*(pager.getPageIndex()-1));
			query.setMaxResults(pager.getPageSize());
		}
		result = query.list();
		debug("select list use hql: "+hql);
		return result;
	}*/
	
	/**
	 * 通过Filter查询投影运算结果集
	 */
	@SuppressWarnings("unchecked")
	public static List<Object[]> getProjectionList(Filter filter, Session session) {
		List<Object[]> result = null;
		Criteria criteria = session.createCriteria(filter.getBeanClass());
		filter.filter(criteria);
		result = criteria.list();
		return result;
	}

	/**
	 * 通过Filter查询投影运算结果
	 * 如果运算没有结果则返回0
	 */
	public static Double getProjectionResult(Filter filter, Session session) {
		Double result = null;
		Criteria criteria = session.createCriteria(filter.getBeanClass());
		filter.filter(criteria);
		result = criteria.uniqueResult()==null ? 0 : (Double)criteria.uniqueResult();
		return result;
	}

	/**
	 * 通过Class查询总数
	 */
	public static int getRowCount(Class<?> clazz, Session session) {
		int rowCount = 0;
		Criteria criteria = session.createCriteria(clazz);
		criteria.setProjection(Projections.rowCount());
		Object result = criteria.uniqueResult();
		if(result != null)rowCount = Integer.valueOf(result.toString());
		debug("select rowCount from "+clazz.getName());
		return rowCount;
	}

	/**
	 * 通过Filter查询总数
	 */
	public static int getRowCount(Filter filter, Session session) {
		int rowCount = 0;
		Criteria criteria = session.createCriteria(filter.getBeanClass());
		criteria.setProjection(Projections.rowCount());
		boolean isGrouped = false;
		if(filter.getProjectionList().size()>0){
			ProjectionList groupProjectionList = Projections.projectionList();
			for (Projection projection : filter.getProjectionList()) {
				if(projection.isGrouped()){
					groupProjectionList.add(projection);
					isGrouped = true;
				}
			}
			if(isGrouped){
				criteria.setProjection(groupProjectionList);
			}
		}
		filter.criteria(criteria);
		Object result = criteria.uniqueResult();
		if(result != null)rowCount = Integer.valueOf(result.toString());
		debug("select rowCount from "+filter.getBeanClass().getName());
		return rowCount;
	}

	/**
	 * 通过Class Id查询对象
	 */
	public static Object getBeanById(Class<?> clazz, Serializable id, Session session) {
		Object result = null;
		result = session.get(clazz, id);
		debug("select bean from "+clazz.getName()+" id:"+id);
		return result;
	}
	
	/**
	 * 通过HQL查询对象
	 */
	public static Object getBeanByHql(String hql, Session session) {
		Object result = null;
		result = session.createQuery(hql).setMaxResults(1).uniqueResult();
		debug("select bean use hql: "+hql);
		return result;
	}
	
	/**
	 * 通过Filter查询对象
	 * 如果查到多个结果则返回第一个结果 如果没有查到结果则返回null
	 */
	@SuppressWarnings("unchecked")
	public static Object getBeanByFilter(Filter filter, Session session) {
		Object result = null;
		filter.maxResults(1);
		Criteria criteria = session.createCriteria(filter.getBeanClass());
		filter.filter(criteria);
		List<Object> list = new ArrayList<Object>();
		if(filter.getIncludeFields().length>0){
			String[] properties = filter.getIncludeFields();
			ProjectionList projectionList = Projections.projectionList();
			for (int i = 0; i < properties.length; i++) {
				projectionList.add(Projections.property(properties[i]));
			}
			criteria.setProjection(projectionList);
			List<?> objects = criteria.list();
			result = new ArrayList<Object>();
			for (int i = 0; i < objects.size(); i++) {
				try {
					Object[] results = (Object[]) objects.get(i);
					Object obj = filter.getBeanClass().newInstance();
					for (int j = 0; j < properties.length; j++) {
						String setMethod = "set"+properties[j].substring(0,1).toUpperCase()+properties[j].substring(1);
						for (int k = 0; k < obj.getClass().getMethods().length; k++) {
							Method method = obj.getClass().getMethods()[k];
							if(method.getName().equals(setMethod)){
								method.invoke(obj, results[j]);
							}
						}
					}
					list.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else 
			list = criteria.list();
		if(list!=null && list.size()>0)result = list.get(0);
		debug("select bean from "+filter.getBeanClass().getName());
		return result;
	}
}