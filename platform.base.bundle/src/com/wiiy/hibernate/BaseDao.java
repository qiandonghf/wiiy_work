package com.wiiy.hibernate;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.stat.Statistics;

public class BaseDao<T> {
	
	private static final Log logger = LogFactory.getLog(BaseDao.class);
	protected SessionFactory sessionFactory;
	public Class<?> c;
	
	@SuppressWarnings("unchecked")
	public BaseDao() {
		c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void setSessionService(SessionService sessionService) {
		sessionFactory = sessionService.getSessionFactory();
//		logger.debug(c.getName()+" SessionFactory was set");
	}
	
	public Session openSession(){
		Session session=sessionFactory.openSession();
		Statistics s=sessionFactory.getStatistics();
		System.out.println("BaseDao.openSession():ConnectCount="+s.getConnectCount()
				+",TransactionCount="+s.getTransactionCount()
				+",SessionOpenCount="+s.getSessionOpenCount()
				+",SessionCloseCount="+s.getSessionCloseCount());
		return session;
	}
	private void printException(Throwable e){
		StringWriter sw= new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		logger.debug(sw.getBuffer().toString());
	}
	public Serializable save(T t){
		debug("save: " + t.getClass());
		Serializable id = null;
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			id = session.save(t);
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new UKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			logger.debug(e.getLocalizedMessage());
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return id;
	}

	public void saveOrUpdate(T t){
		debug("saveOrUpdate: " + t.getClass());
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.saveOrUpdate(t);
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new UKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void update(T t){
		debug("update: " + t.getClass());
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.update(t);
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new UKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void merge(T t){
		debug("merge: " + t.getClass());
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.merge(t);
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new UKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void delete(T t){
		debug("delete: " + t.getClass());
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.delete(t);
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new FKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
	
	public void deleteById(final Serializable id){
		debug("deleteById: " + c.getClass());
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			//session.delete(session.get(c, id));
			org.hibernate.Query query = session.createQuery("delete from "+c.getSimpleName()+" where id = ?");
			if (id instanceof String) {
				query.setString(0, (String)id);
			} else if (id instanceof Integer) {
				query.setInteger(0, (Integer)id);
			} else {
				query.setLong(0, (Long)id);
			}
			query.executeUpdate();
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new FKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
	
	public void deleteByIds(final Serializable ids){
		debug("deleteByIds: " + c.getClass());
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.createQuery("delete from "+c.getSimpleName()+" where id in ("+ids+")").executeUpdate();
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new FKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void executeUpdate(final String hql){
		debug("execute HQL: " + hql);
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.createQuery(hql).executeUpdate();
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new FKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void executeSQLUpdate(final String sql){
		debug("execute SQL: " + sql);
		Session session = openSession();
		Transaction tr = session.beginTransaction();
		try {
			session.createSQLQuery(sql).executeUpdate();
			tr.commit();
		} catch (ConstraintViolationException e) {
			printException(e);
			tr.rollback();
			throw new FKConstraintException(e);
		} catch (Exception e) {
			printException(e);
			tr.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public Integer getRowCount(){
		Integer count = null;
		Session session = openSession();
		try {
			count = Query.getRowCount(c,session);
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return count;
	}

	public Integer getRowCount(final Filter filter){
		Integer count = null;
		Session session = openSession();
		try {
			count = Query.getRowCount(filter,session);
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return count;
	}

	public Double getProjectionResult(final Filter filter){
		Double result = null;
		Session session = openSession();
		try {
			result = Query.getProjectionResult(filter,session);
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public T getBeanByFilter(final Filter filter){
		T result = null;
		Session session = openSession();
		try {
			result = (T) Query.getBeanByFilter(filter,session);
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public T getBeanByHql(final String hql){
		T result = null;
		Session session = openSession();
		try {
			result = (T) session.createQuery(hql).uniqueResult();
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public T getBeanById(Serializable id){
		T result = null;
		Session session = openSession();
		try {
			result = (T) session.get(c, id);
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(){
		List<T> result = null;
		Session session = openSession();
		try {
			result = (List<T>) Query.getList(c, session);
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}

	public List<Object[]> getProjectionList(final Filter filter){
		List<Object[]> result = null;
		Session session = openSession();
		try {
			result = Query.getProjectionList(filter,session);
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getListByFilter(final Filter filter){
		List<T> result = null;
		Session session = openSession();
		try {
			result = (List<T>) Query.getListByFilter(filter,session);
		} catch (Exception e) {
			e.printStackTrace();
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<T> getListByHql(final String hql){
		List<T> result = null;
		Session session = openSession();
		try {
			result = session.createQuery(hql).list();
		} catch (Exception e) {
			printException(e);
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}
	
	public List getObjectListByHql(final String hql){
		List result = null;
		Session session = openSession();
		try {
			result = session.createQuery(hql).list();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}
	
	public List getObjectListBySql(final String sql){
		List result = null;
		Session session = openSession();
		try {
			result = session.createSQLQuery(sql).list();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * ------------------------------------------------------------------------
	 **/

	private void debug(Object o) {
		//logger.debug(o);
	}

}
