package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dao.SaleBillPlanRentDao;
import com.wiiy.sale.entity.SaleBillPlanRent;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.service.SaleBillPlanRentService;

/**
 * @author my
 */
public class SaleBillPlanRentServiceImpl implements SaleBillPlanRentService{
	
	private SaleBillPlanRentDao saleBillPlanRentDao;
	
	public void setSaleBillPlanRentDao(SaleBillPlanRentDao saleBillPlanRentDao) {
		this.saleBillPlanRentDao = saleBillPlanRentDao;
	}

	@Override
	public Result<SaleBillPlanRent> save(SaleBillPlanRent t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			saleBillPlanRentDao.save(t);
			return Result.success(R.SaleBillPlanRent.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillPlanRent> delete(SaleBillPlanRent t) {
		try {
			saleBillPlanRentDao.delete(t);
			return Result.success(R.SaleBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillPlanRent> deleteById(Serializable id) {
		try {
			saleBillPlanRentDao.deleteById(id);
			return Result.success(R.SaleBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillPlanRent> deleteByIds(String ids) {
		try {
			saleBillPlanRentDao.deleteByIds(ids);
			return Result.success(R.SaleBillPlanRent.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillPlanRent> update(SaleBillPlanRent t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			saleBillPlanRentDao.update(t);
			return Result.success(R.SaleBillPlanRent.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleBillPlanRent.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SaleBillPlanRent> getBeanById(Serializable id) {
		try {
			return Result.value(saleBillPlanRentDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleBillPlanRent> getBeanByFilter(Filter filter) {
		try {
			return Result.value(saleBillPlanRentDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleBillPlanRent>> getList() {
		try {
			return Result.value(saleBillPlanRentDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleBillPlanRent>> getListByFilter(Filter filter) {
		try {
			return Result.value(saleBillPlanRentDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleBillPlanRent.LOAD_FAILURE);
		}
	}
	
	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SaleBillPlanRent billPlanRent = saleBillPlanRentDao.getBeanByFilter(//
				new Filter(SaleBillPlanRent.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = saleBillPlanRentDao.getRowCount(//
				new Filter(SaleBillPlanRent.class).//
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
			session = saleBillPlanRentDao.openSession();
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
