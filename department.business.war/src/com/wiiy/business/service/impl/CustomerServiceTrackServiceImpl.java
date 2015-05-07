package com.wiiy.business.service.impl;


import java.io.Serializable;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.business.dao.CustomerServiceTrackDao;
import com.wiiy.business.entity.CustomerService;
import com.wiiy.business.entity.CustomerServiceTrack;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.CustomerServiceTrackService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class CustomerServiceTrackServiceImpl implements CustomerServiceTrackService{
	
	private CustomerServiceTrackDao customerServiceTrackDao;
	
	public void setCustomerServiceTrackDao(CustomerServiceTrackDao customerServiceTrackDao) {
		this.customerServiceTrackDao = customerServiceTrackDao;
	}

	@Override
	public Result<CustomerServiceTrack> save(CustomerServiceTrack t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerServiceTrackDao.save(t);
			
			return Result.success(R.CustomerServiceTrack.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerServiceTrack.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CustomerServiceTrack.SAVE_FAILURE);
		}
	}

	@Override
	public Result<CustomerServiceTrack> delete(CustomerServiceTrack t) {
		try {
			customerServiceTrackDao.delete(t);
			return Result.success(R.CustomerServiceTrack.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerServiceTrack.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerServiceTrack> deleteById(Serializable id) {
		try {
			customerServiceTrackDao.deleteById(id);
			return Result.success(R.CustomerServiceTrack.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerServiceTrack.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerServiceTrack> deleteByIds(String ids) {
		try {
			customerServiceTrackDao.deleteByIds(ids);
			return Result.success(R.CustomerServiceTrack.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.CustomerServiceTrack.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CustomerServiceTrack> update(CustomerServiceTrack t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			customerServiceTrackDao.update(t);
			return Result.success(R.CustomerServiceTrack.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CustomerServiceTrack.class)));
		} catch (Exception e) {
			return Result.failure(R.CustomerServiceTrack.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CustomerServiceTrack> getBeanById(Serializable id) {
		try {
			return Result.value(customerServiceTrackDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.CustomerServiceTrack.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CustomerServiceTrack> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerServiceTrackDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.CustomerServiceTrack.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerServiceTrack>> getList() {
		try {
			return Result.value(customerServiceTrackDao.getList());
		} catch (Exception e) {
			return Result.failure(R.CustomerServiceTrack.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CustomerServiceTrack>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerServiceTrackDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.CustomerServiceTrack.LOAD_FAILURE);
		}
	}

	@Override
	public Result save(CustomerServiceTrack t,List<CustomerService> customerServiceList) {
		Session session = null;
		Transaction tr = null;
		try{
			session = customerServiceTrackDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			//CustomerService c = (CustomerService)session.get(CustomerService.class,t.getServiceId());
//			for(CustomerService list : customerServiceList){
//				if(list.getId().equals(t.getServiceId())){
//					t.setService(list);
//					session.save(t);
//					
//				}
//			}
			session.save(t);
			tr.commit();
			return Result.success(R.CustomerServiceTrack.SAVE_SUCCESS,t);
		}catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.CustomerServiceTrack.SAVE_FAILURE);
		}finally{
			session.close();
		}

	}

	
}
