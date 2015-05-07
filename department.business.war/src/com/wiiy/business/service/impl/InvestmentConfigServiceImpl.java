package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.InvestmentConfigDao;
import com.wiiy.business.entity.InvestmentConfig;
import com.wiiy.business.entity.InvestmentContectInfo;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.InvestmentConfigService;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class InvestmentConfigServiceImpl implements InvestmentConfigService{
	
	private InvestmentConfigDao investmentConfigDao;
	
	public void setInvestmentConfigDao(InvestmentConfigDao investmentConfigDao) {
		this.investmentConfigDao = investmentConfigDao;
	}
	private void setCreatorModifier(BaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreatorId(BusinessActivator.getSessionUser().getId());
		entity.setCreator(BusinessActivator.getSessionUser().getRealName());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}
	@Override
	public Result save(Long id, String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = investmentConfigDao.openSession();
			tr = session.beginTransaction();
			String[] contectInfoIds = ids.split(",");
			for (String contectInfoId : contectInfoIds) {
				InvestmentConfig config = new InvestmentConfig();
				config.setInvestmentId(id);
				Long infoId = Long.valueOf(contectInfoId);
				config.setInvestmentContectInfoId(infoId);
				setCreatorModifier(config);
				session.save(config);
				InvestmentContectInfo contectInfo = (InvestmentContectInfo)session.get(InvestmentContectInfo.class, infoId);
				contectInfo.setAssociate(BooleanEnum.YES);
			}
			tr.commit();
			return Result.success("线索关联成功");
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentConfig.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("线索关联失败");
		} finally {
			session.close();
		}
	}

	
	@Override
	public Result<InvestmentConfig> save(InvestmentConfig t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			investmentConfigDao.save(t);
			return Result.success(R.InvestmentConfig.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentConfig.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.SAVE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentConfig> delete(InvestmentConfig t) {
		try {
			investmentConfigDao.delete(t);
			return Result.success(R.InvestmentConfig.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.DELETE_FAILURE);
		}
	}
	
	@Override
	public Result deleteById(Long id, Long investmentId) {
		Session session = null;
		Transaction tr = null;
		try {
			session = investmentConfigDao.openSession();
			tr = session.beginTransaction();
			session.createSQLQuery("delete from business_investment_config where investmentContectInfo_id = "+id+" and investment_id = "+investmentId).executeUpdate();
			tr.commit();
			return Result.success("线索关联删除成功");
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("线索关联删除失败");
		} finally {
			session.close();
		}
	}
	@Override
	public Result deleteByIds(String ids, Long investmentId) {
		Session session = null;
		Transaction tr = null;
		try {
			session = investmentConfigDao.openSession();
			tr = session.beginTransaction();
			session.createSQLQuery("delete from business_investment_config where investmentContectInfo_id in ("+ids+") and investment_id = "+investmentId).executeUpdate();
			tr.commit();
			return Result.success("线索关联删除成功");
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("线索关联删除失败");
		} finally {
			session.close();
		}
	}

	@Override
	public Result<InvestmentConfig> deleteById(Serializable id) {
		try {
			investmentConfigDao.deleteById(id);
			return Result.success(R.InvestmentConfig.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentConfig> deleteByIds(String ids) {
		try {
			investmentConfigDao.deleteByIds(ids);
			return Result.success(R.InvestmentConfig.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentConfig> update(InvestmentConfig t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			investmentConfigDao.update(t);
			return Result.success(R.InvestmentConfig.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentConfig.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentConfig> getBeanById(Serializable id) {
		try {
			return Result.value(investmentConfigDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result<InvestmentConfig> getBeanByFilter(Filter filter) {
		try {
			return Result.value(investmentConfigDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentConfig>> getList() {
		try {
			return Result.value(investmentConfigDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentConfig>> getListByFilter(Filter filter) {
		try {
			return Result.value(investmentConfigDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentConfig.LOAD_FAILURE);
		}
	}

}
