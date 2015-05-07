package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dao.SaleContractAttDao;
import com.wiiy.sale.entity.SaleContractAtt;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.service.SaleContractAttService;

/**
 * @author my
 */
public class SaleContractAttServiceImpl implements SaleContractAttService{
	
	private SaleContractAttDao saleContractAttDao;
	
	public void setSaleContractAttDao(SaleContractAttDao saleContractAttDao) {
		this.saleContractAttDao = saleContractAttDao;
	}

	@Override
	public Result<SaleContractAtt> save(SaleContractAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			saleContractAttDao.save(t);
			return Result.success(R.SaleContractAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SaleContractAtt> delete(SaleContractAtt t) {
		try {
			saleContractAttDao.delete(t);
			return Result.success(R.SaleContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleContractAtt> deleteById(Serializable id) {
		try {
			Result<SaleContractAtt> result = getBeanById(id);
			String path = result.getValue().getNewName();
			SaleActivator.getResourcesService().delete(path);
			saleContractAttDao.deleteById(id);
			return Result.success(R.SaleContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleContractAtt> deleteByIds(String ids) {
		try {
			saleContractAttDao.deleteByIds(ids);
			return Result.success(R.SaleContractAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleContractAtt> update(SaleContractAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			saleContractAttDao.update(t);
			return Result.success(R.SaleContractAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleContractAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SaleContractAtt> getBeanById(Serializable id) {
		try {
			return Result.value(saleContractAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleContractAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(saleContractAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleContractAtt>> getList() {
		try {
			return Result.value(saleContractAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleContractAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(saleContractAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleContractAtt.LOAD_FAILURE);
		}
	}

}
