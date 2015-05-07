package com.wiiy.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseRepository<T> extends HibernateDaoSupport {
	
	protected Log logger = LogFactory.getLog(getClass());
	
    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
	protected BaseRepository() {
    	entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
	public Serializable save(T t){

		try {
			return getSession().save(t);
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to save " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to save " + entityClass, e);
			throw new RuntimeException(e);
		}
	}

	public void saveOrUpdate(T t){
		try {
			getSession().saveOrUpdate(t);
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to saveOrUpdate " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to saveOrUpdate " + entityClass, e);
			throw new RuntimeException(e);
		}
	}

	public void update(T t){
		try {
			getSession().update(t);
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to update " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to update " + entityClass, e);
			throw new RuntimeException(e);
		}
	}

	public void merge(T t){
		try {
			getSession().merge(t);
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to merge " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to merge " + entityClass, e);
			throw new RuntimeException(e);
		}
	}

	public void delete(T t){
		try {
			getSession().delete(t);
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to delete " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to delete " + entityClass, e);
			throw new RuntimeException(e);
		}
	}
	
	public void deleteById(final Serializable id){
		try {
			getSession().createQuery("delete from "+entityClass.getSimpleName()+" where id = "+id).executeUpdate();
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to delete " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to delete " + entityClass, e);
			throw new RuntimeException(e);
		}
	}
	
	public void deleteByIds(final Serializable ids){
		try {
			getSession().createQuery("delete from "+entityClass.getSimpleName()+" where id in ("+ids+")").executeUpdate();
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to delete " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to delete " + entityClass, e);
			throw new RuntimeException(e);
		}
	}

	public void executeUpdate(final String hql){
		try {
			getSession().createQuery(hql).executeUpdate();
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to update " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to update " + entityClass, e);
			throw new RuntimeException(e);
		}
	}

	public void executeSQLUpdate(final String sql){
		try {
			getSession().createSQLQuery(sql).executeUpdate();
		} catch (ConstraintViolationException e) {
			logger.debug("Failed to update " + entityClass, e);
			throw new FKConstraintException(e);
		} catch (Exception e) {
			logger.debug("Failed to update " + entityClass, e);
			throw new RuntimeException(e);
		}
	}

	public Integer getRowCount(){
		return Query.getRowCount(entityClass,getSession());
	}

	public Integer getRowCount(final Filter filter){
		return Query.getRowCount(filter, getSession());
	}

	public Double getProjectionResult(final Filter filter){
		return Query.getProjectionResult(filter,getSession());
	}
	
	@SuppressWarnings("unchecked")
	public T getBeanByFilter(final Filter filter){
		return (T) Query.getBeanByFilter(filter,getSession());
	}

	@SuppressWarnings("unchecked")
	public T getBeanByHql(final String hql){
		return (T) getSession().createQuery(hql).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T getBeanById(Serializable id){
		return (T) getSession().get(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList(){
		return (List<T>) Query.getList(entityClass, getSession());
	}

	public List<Object[]> getProjectionList(final Filter filter){
		return Query.getProjectionList(filter,getSession());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getListByFilter(final Filter filter){
		return (List<T>) Query.getListByFilter(filter,getSession());
	}

	@SuppressWarnings("unchecked")
	public List<T> getListByHql(final String hql){
		return getSession().createQuery(hql).list();
	}
		
	public List getObjectListByHql(final String hql){
		return getSession().createQuery(hql).list();
	}
	
	public List getObjectListBySql(final String sql){
		return getSession().createSQLQuery(sql).list();
	}
	
	public boolean isDuplicated(Long id, String propertyName, String propertyValue) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Property.forName(propertyName).eq(propertyValue));
		if (id.longValue() != -1L) {
			criteria.add(Property.forName("id").ne(id));
		}
		criteria.setProjection(Projections.count("id"));
		int count = (Integer)criteria.uniqueResult();
		return count > 0;
	}
}