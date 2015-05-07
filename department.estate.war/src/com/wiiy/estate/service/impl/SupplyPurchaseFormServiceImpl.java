package com.wiiy.estate.service.impl;


import java.io.Serializable;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.entity.SupplyPurchaseForm;
import com.wiiy.estate.dao.SupplyPurchaseFormDao;
import com.wiiy.estate.service.SupplyPurchaseFormService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class SupplyPurchaseFormServiceImpl implements SupplyPurchaseFormService{
	
	private SupplyPurchaseFormDao supplyPurchaseFormDao;
	
	public void setSupplyPurchaseFormDao(SupplyPurchaseFormDao supplyPurchaseFormDao) {
		this.supplyPurchaseFormDao = supplyPurchaseFormDao;
	}
	@Override
	public Result<String> generateCode() {
		String name = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SupplyPurchaseForm form = supplyPurchaseFormDao.getBeanByFilter(//
				new Filter(SupplyPurchaseForm.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = supplyPurchaseFormDao.getRowCount(//
				new Filter(SupplyPurchaseForm.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			String oldCode = form.getCode();
			count = Integer.parseInt(//
					oldCode.replace("["+CalendarUtil.now().get(Calendar.YEAR)+"]", ""));
		} catch (Exception e) {
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(6);
		name = name + nf.format(count+1);
		return Result.value(name);
	}
	@Override
	public Result<SupplyPurchaseForm> save(SupplyPurchaseForm t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			supplyPurchaseFormDao.save(t);
			return Result.success(R.SupplyPurchaseForm.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyPurchaseForm.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseForm> delete(SupplyPurchaseForm t) {
		try {
			supplyPurchaseFormDao.delete(t);
			return Result.success(R.SupplyPurchaseForm.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseForm> deleteById(Serializable id) {
		try {
			supplyPurchaseFormDao.deleteById(id);
			return Result.success(R.SupplyPurchaseForm.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseForm> deleteByIds(String ids) {
		try {
			supplyPurchaseFormDao.deleteByIds(ids);
			return Result.success(R.SupplyPurchaseForm.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseForm> update(SupplyPurchaseForm t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			supplyPurchaseFormDao.update(t);
			return Result.success(R.SupplyPurchaseForm.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyPurchaseForm.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseForm> getBeanById(Serializable id) {
		try {
			return Result.value(supplyPurchaseFormDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseForm> getBeanByFilter(Filter filter) {
		try {
			return Result.value(supplyPurchaseFormDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SupplyPurchaseForm>> getList() {
		try {
			return Result.value(supplyPurchaseFormDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SupplyPurchaseForm>> getListByFilter(Filter filter) {
		try {
			return Result.value(supplyPurchaseFormDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseForm.LOAD_FAILURE);
		}
	}

}
