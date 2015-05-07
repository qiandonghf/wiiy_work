package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.EstateBillPlanRentDao;
import com.wiiy.estate.entity.EstateBillPlanRent;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.EstateBillPlanRentService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class EstateBillPlanRentServiceImpl implements EstateBillPlanRentService{
	
	private EstateBillPlanRentDao estateBillPlanRentDao;
	
	public void setEstateBillPlanRentDao(EstateBillPlanRentDao estateBillPlanRentDao) {
		this.estateBillPlanRentDao = estateBillPlanRentDao;
	}

	@Override
	public Result<EstateBillPlanRent> save(EstateBillPlanRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			estateBillPlanRentDao.save(t);
			return Result.success(R.EstateBillPlanRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> delete(EstateBillPlanRent t) {
		try {
			estateBillPlanRentDao.delete(t);
			return Result.success(R.EstateBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> deleteById(Serializable id) {
		try {
			estateBillPlanRentDao.deleteById(id);
			return Result.success(R.EstateBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> deleteByIds(String ids) {
		try {
			estateBillPlanRentDao.deleteByIds(ids);
			return Result.success(R.EstateBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> update(EstateBillPlanRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			estateBillPlanRentDao.update(t);
			return Result.success(R.EstateBillPlanRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> getBeanById(Serializable id) {
		try {
			return Result.value(estateBillPlanRentDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateBillPlanRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(estateBillPlanRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateBillPlanRent>> getList() {
		try {
			return Result.value(estateBillPlanRentDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateBillPlanRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(estateBillPlanRentDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EstateBillPlanRent.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		EstateBillPlanRent billPlanRent = estateBillPlanRentDao.getBeanByFilter(//
				new Filter(EstateBillPlanRent.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = estateBillPlanRentDao.getRowCount(//
				new Filter(EstateBillPlanRent.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			/*String oldCode = billPlanRent.getCode();
			count = Integer.parseInt(//
					oldCode.replace("["+CalendarUtil.now().get(Calendar.YEAR)+"]", ""));*/
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
			session = estateBillPlanRentDao.openSession();
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
