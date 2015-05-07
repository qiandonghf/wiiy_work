package com.wiiy.engineering.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.engineering.activator.EngineeringActivator;
import com.wiiy.engineering.dao.EngineeringBillPlanRentDao;
import com.wiiy.engineering.entity.EngineeringBillPlanRent;
import com.wiiy.engineering.preferences.R;
import com.wiiy.engineering.service.EngineeringBillPlanRentService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class EngineeringBillPlanRentServiceImpl implements EngineeringBillPlanRentService{
	
	private EngineeringBillPlanRentDao engineeringBillPlanRentDao;
	
	public void setEngineeringBillPlanRentDao(EngineeringBillPlanRentDao engineeringBillPlanRentDao) {
		this.engineeringBillPlanRentDao = engineeringBillPlanRentDao;
	}

	@Override
	public Result<EngineeringBillPlanRent> save(EngineeringBillPlanRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EngineeringActivator.getSessionUser().getRealName());
			t.setCreatorId(EngineeringActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			engineeringBillPlanRentDao.save(t);
			return Result.success(R.EngineeringBillPlanRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillPlanRent> delete(EngineeringBillPlanRent t) {
		try {
			engineeringBillPlanRentDao.delete(t);
			return Result.success(R.EngineeringBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillPlanRent> deleteById(Serializable id) {
		try {
			engineeringBillPlanRentDao.deleteById(id);
			return Result.success(R.EngineeringBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillPlanRent> deleteByIds(String ids) {
		try {
			engineeringBillPlanRentDao.deleteByIds(ids);
			return Result.success(R.EngineeringBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillPlanRent> update(EngineeringBillPlanRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EngineeringActivator.getSessionUser().getRealName());
			t.setModifierId(EngineeringActivator.getSessionUser().getId());
			engineeringBillPlanRentDao.update(t);
			return Result.success(R.EngineeringBillPlanRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillPlanRent> getBeanById(Serializable id) {
		try {
			return Result.value(engineeringBillPlanRentDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EngineeringBillPlanRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(engineeringBillPlanRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringBillPlanRent>> getList() {
		try {
			return Result.value(engineeringBillPlanRentDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringBillPlanRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(engineeringBillPlanRentDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringBillPlanRent.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		EngineeringBillPlanRent billPlanRent = engineeringBillPlanRentDao.getBeanByFilter(//
				new Filter(EngineeringBillPlanRent.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = engineeringBillPlanRentDao.getRowCount(//
				new Filter(EngineeringBillPlanRent.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			String oldCode = billPlanRent.getCode();
			count = Integer.parseInt(//
					oldCode.replace("["+CalendarUtil.now().get(Calendar.YEAR)+"]", ""));
		} catch (Exception e) {
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(6);
		code = code + nf.format(count+1);
		return Result.value(code);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result<List> getResultBySql(String sql) {
		Session session = null;
		try {
			session = engineeringBillPlanRentDao.openSession();
			List list = session.createSQLQuery(sql).list();
			return Result.value(list);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

}
