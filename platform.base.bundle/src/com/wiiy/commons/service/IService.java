package com.wiiy.commons.service;

import java.io.Serializable;
import java.util.List;

import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;


public interface IService<T> {
	

	/**
	 * 保存实体
	 * @param t 要保存的实体
	 */
	public Result<T> save(T t);
	/**
	 * 删除实体
	 * @param t 要删除的实体
	 */
	public Result<T> delete(T t);
	/**
	 * 删除实体ById
	 * @param id 要删除的实体ID
	 */
	public Result<T> deleteById(Serializable id);
	/**
	 * 删除实体集ByIds
	 * @param ids 要删除的实体ID集合(字符串形式1,2,3...)
	 */
	public Result<T> deleteByIds(String ids);
	/**
	 * 更新实体
	 * @param t 要更新的实体
	 */
	public Result<T> update(T t);
	/**
	 * 通过id查找实体
	 * @param id
	 * @return 如果实体不存在 返回null
	 */
	public Result<T> getBeanById(Serializable id);
	/**
	 * 通过过虑器查找实体
	 * @param filter
	 * @return 如果不存在 返回null 如果存在多个符合条件的实体 则返回第一个
	 */
	public Result<T> getBeanByFilter(Filter filter);
	/**
	 * 查找所有数据
	 * @return 如果无数据 返回null
	 */
	public Result<List<T>> getList();
	/**
	 * 通过过虑器筛选数据
	 * @param filter
	 * @return 如果无数据 返回null
	 */
	public Result<List<T>> getListByFilter(Filter filter);
	
}
