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
import com.wiiy.sale.entity.ContectInfo;
import com.wiiy.sale.entity.RepeatedViste;
import com.wiiy.sale.dao.RepeatedVisteDao;
import com.wiiy.sale.service.RepeatedVisteService;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.activator.SaleActivator;

/**
 * @author my
 */
public class RepeatedVisteServiceImpl implements RepeatedVisteService{
	
	private RepeatedVisteDao repeatedVisteDao;
	
	public void setRepeatedVisteDao(RepeatedVisteDao repeatedVisteDao) {
		this.repeatedVisteDao = repeatedVisteDao;
	}

	@Override
	public Result<RepeatedViste> save(RepeatedViste t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = repeatedVisteDao.openSession();
			tr = session.beginTransaction();
			ContectInfo c = (ContectInfo) session.get(ContectInfo.class, t.getContectInfoId());
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			c.setReturnTime(t.getStartTime());
			c.setReturnVisit(t.getRemindTime());
			session.update(c);
			session.save(t);
			tr.commit();
			return Result.success(R.RepeatedViste.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RepeatedViste.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.SAVE_FAILURE);
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public Result<RepeatedViste> delete(RepeatedViste t) {
		try {
			repeatedVisteDao.delete(t);
			return Result.success(R.RepeatedViste.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RepeatedViste> deleteById(Serializable id) {
		try {
			repeatedVisteDao.deleteById(id);
			return Result.success(R.RepeatedViste.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RepeatedViste> deleteByIds(String ids) {
		try {
			repeatedVisteDao.deleteByIds(ids);
			return Result.success(R.RepeatedViste.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.DELETE_FAILURE);
		}
	}

	@Override
	public Result<RepeatedViste> update(RepeatedViste t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = repeatedVisteDao.openSession();
			tr = session.beginTransaction();
			ContectInfo c = (ContectInfo) session.get(ContectInfo.class, t.getContectInfoId());
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			c.setReturnTime(t.getStartTime());
			c.setReturnVisit(t.getRemindTime());
			session.update(c);
			session.update(t);
			tr.commit();
			return Result.success(R.RepeatedViste.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),RepeatedViste.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.UPDATE_FAILURE);
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public Result<RepeatedViste> getBeanById(Serializable id) {
		try {
			return Result.value(repeatedVisteDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.LOAD_FAILURE);
		}
	}

	@Override
	public Result<RepeatedViste> getBeanByFilter(Filter filter) {
		try {
			return Result.value(repeatedVisteDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RepeatedViste>> getList() {
		try {
			return Result.value(repeatedVisteDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<RepeatedViste>> getListByFilter(Filter filter) {
		try {
			return Result.value(repeatedVisteDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.RepeatedViste.LOAD_FAILURE);
		}
	}

}
