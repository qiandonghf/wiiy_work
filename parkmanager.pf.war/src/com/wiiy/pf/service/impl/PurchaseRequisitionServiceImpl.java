package com.wiiy.pf.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.pf.entity.PurchaseRequisition;
import com.wiiy.pf.dao.PurchaseRequisitionDao;
import com.wiiy.pf.service.PurchaseRequisitionService;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.activator.PfActivator;

/**
 * @author my
 */
public class PurchaseRequisitionServiceImpl implements PurchaseRequisitionService{
	
	private PurchaseRequisitionDao purchaseRequisitionDao;
	
	public void setPurchaseRequisitionDao(PurchaseRequisitionDao purchaseRequisitionDao) {
		this.purchaseRequisitionDao = purchaseRequisitionDao;
	}

	@Override
	public Result<PurchaseRequisition> save(PurchaseRequisition t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(PfActivator.getSessionUser().getRealName());
			t.setCreatorId(PfActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			purchaseRequisitionDao.save(t);
			return Result.success(R.PurchaseRequisition.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),PurchaseRequisition.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.SAVE_FAILURE);
		}
	}

	@Override
	public Result<PurchaseRequisition> delete(PurchaseRequisition t) {
		try {
			purchaseRequisitionDao.delete(t);
			return Result.success(R.PurchaseRequisition.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.DELETE_FAILURE);
		}
	}

	@Override
	public Result<PurchaseRequisition> deleteById(Serializable id) {
		try {
			purchaseRequisitionDao.deleteById(id);
			return Result.success(R.PurchaseRequisition.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.DELETE_FAILURE);
		}
	}

	@Override
	public Result<PurchaseRequisition> deleteByIds(String ids) {
		try {
			purchaseRequisitionDao.deleteByIds(ids);
			return Result.success(R.PurchaseRequisition.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.DELETE_FAILURE);
		}
	}

	@Override
	public Result<PurchaseRequisition> update(PurchaseRequisition t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			purchaseRequisitionDao.update(t);
			return Result.success(R.PurchaseRequisition.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),PurchaseRequisition.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<PurchaseRequisition> getBeanById(Serializable id) {
		try {
			return Result.value(purchaseRequisitionDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.LOAD_FAILURE);
		}
	}

	@Override
	public Result<PurchaseRequisition> getBeanByFilter(Filter filter) {
		try {
			return Result.value(purchaseRequisitionDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<PurchaseRequisition>> getList() {
		try {
			return Result.value(purchaseRequisitionDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<PurchaseRequisition>> getListByFilter(Filter filter) {
		try {
			return Result.value(purchaseRequisitionDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PurchaseRequisition.LOAD_FAILURE);
		}
	}

}
