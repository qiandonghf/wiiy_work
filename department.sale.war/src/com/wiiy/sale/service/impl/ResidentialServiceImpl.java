package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.sale.entity.Residential;
import com.wiiy.sale.dao.ResidentialDao;
import com.wiiy.sale.service.ResidentialService;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.activator.SaleActivator;

/**
 * @author my
 */
public class ResidentialServiceImpl implements ResidentialService{
	
	private ResidentialDao residentialDao;
	
	public void setResidentialDao(ResidentialDao residentialDao) {
		this.residentialDao = residentialDao;
	}

	@Override
	public Result<Residential> save(Residential t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			residentialDao.save(t);
			return Result.success(R.Residential.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Residential.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Residential> delete(Residential t) {
		try {
			residentialDao.delete(t);
			return Result.success(R.Residential.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Residential> deleteById(Serializable id) {
		try {
			residentialDao.deleteById(id);
			return Result.success(R.Residential.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Residential> deleteByIds(String ids) {
		try {
			residentialDao.deleteByIds(ids);
			return Result.success(R.Residential.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Residential> update(Residential t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			residentialDao.update(t);
			return Result.success(R.Residential.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Residential.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Residential> getBeanById(Serializable id) {
		try {
			return Result.value(residentialDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Residential> getBeanByFilter(Filter filter) {
		try {
			return Result.value(residentialDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Residential>> getList() {
		try {
			return Result.value(residentialDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Residential>> getListByFilter(Filter filter) {
		try {
			return Result.value(residentialDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Residential.LOAD_FAILURE);
		}
	}

	@Override
	public Result changeState(Long id){
		Session session = null;
		Transaction tr = null;
		try {
			session = residentialDao.openSession();
			tr = session.beginTransaction();
			Residential t = (Residential) session.get(Residential.class, id);
			if (t.getEntityStatus() == EntityStatus.NORMAL) {
				t.setEntityStatus(EntityStatus.LOCKED);
			}else {
				t.setEntityStatus(EntityStatus.NORMAL);
			}
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			session.update(t);
			tr.commit();
			return Result.success("更新状态成功");
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Residential.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("更新状态失败");
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result getRowCount(Filter filter) {
		try{
			return Result.value(residentialDao.getRowCount(filter));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
}
