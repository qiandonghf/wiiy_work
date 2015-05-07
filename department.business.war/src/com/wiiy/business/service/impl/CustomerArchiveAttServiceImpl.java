package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.entity.CustomerArchiveAtt;
import com.wiiy.business.dao.CustomerArchiveAttDao;
import com.wiiy.business.service.CustomerArchiveAttService;
import com.wiiy.business.preferences.R;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class CustomerArchiveAttServiceImpl implements CustomerArchiveAttService{
	
	private CustomerArchiveAttDao customerArchiveAttDao;
	
	public void setCustomerArchiveAttDao(CustomerArchiveAttDao customerArchiveAttDao) {
		this.customerArchiveAttDao = customerArchiveAttDao;
	}

	@Override
	public Result<CustomerArchiveAtt> save(CustomerArchiveAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerArchiveAttDao.save(t);
			return Result.success(R.CustomerArchiveAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerArchiveAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<CustomerArchiveAtt> delete(CustomerArchiveAtt t) {
		try {
			customerArchiveAttDao.delete(t);
			return Result.success(R.CustomerArchiveAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerArchiveAtt> deleteById(Serializable id) {
		try {
			customerArchiveAttDao.deleteById(id);
			return Result.success(R.CustomerArchiveAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerArchiveAtt> deleteByIds(String ids) {
		try {
			customerArchiveAttDao.deleteByIds(ids);
			return Result.success(R.CustomerArchiveAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerArchiveAtt> update(CustomerArchiveAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			customerArchiveAttDao.update(t);
			return Result.success(R.CustomerArchiveAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerArchiveAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CustomerArchiveAtt> getBeanById(Serializable id) {
		try {
			return Result.value(customerArchiveAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CustomerArchiveAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerArchiveAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerArchiveAtt>> getList() {
		try {
			return Result.value(customerArchiveAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerArchiveAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerArchiveAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerArchiveAtt.LOAD_FAILURE);
		}
	}

	@Override
	public List<Object> getListBySql(String sql) {
		return customerArchiveAttDao.getObjectListBySql(sql);
	}

}
