package com.wiiy.synthesis.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.entity.Appraisal;
import com.wiiy.synthesis.entity.AppraisalAtt;
import com.wiiy.synthesis.entity.SealRegistration;
import com.wiiy.synthesis.entity.SealRegistrationAtt;
import com.wiiy.synthesis.dao.AppraisalDao;
import com.wiiy.synthesis.service.AppraisalService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class AppraisalServiceImpl implements AppraisalService{
	
	private AppraisalDao appraisalDao;
	
	public void setAppraisalDao(AppraisalDao appraisalDao) {
		this.appraisalDao = appraisalDao;
	}

	@Override
	public Result<Appraisal> save(Appraisal t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			appraisalDao.save(t);
			return Result.success(R.Appraisal.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Appraisal.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Appraisal.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Appraisal> delete(Appraisal t) {
		try {
			appraisalDao.delete(t);
			return Result.success(R.Appraisal.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Appraisal.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Appraisal> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = appraisalDao.openSession();
			tr = session.beginTransaction();
			List<AppraisalAtt> appraisalAtts = (List<AppraisalAtt>)session.createQuery("from AppraisalAtt where appraisalId = "+id).list();
			for (AppraisalAtt appraisalAtt : appraisalAtts) {
				String path = appraisalAtt.getNewName();
				session.delete(appraisalAtt);
				SynthesisActivator.getResourcesService().delete(path);
			}
			Appraisal a = (Appraisal)session.createQuery("from Appraisal where id = "+id).list().get(0);
			session.delete(a);
			tr.commit();
			return Result.success(R.Appraisal.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Appraisal.DELETE_FAILURE);
		}finally{
			session.close();
		}
	}

	@Override
	public Result<Appraisal> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null; 
		try {
			session = appraisalDao.openSession();
			tr = session.beginTransaction();
			List<AppraisalAtt> appraisalAtts = (List<AppraisalAtt>)session.createQuery("from AppraisalAtt where appraisalId in ("+ids+")").list();
			for (AppraisalAtt appraisalAtt : appraisalAtts) {
				String path = appraisalAtt.getNewName();
				session.delete(appraisalAtt);
				SynthesisActivator.getResourcesService().delete(path);
			}
			List<Appraisal> t = session.createQuery("from Appraisal where id in ("+ids+")").list();
			for (Appraisal appraisal : t) {
				session.delete(appraisal);
			}
			tr.commit();
			return Result.success(R.Appraisal.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Appraisal.DELETE_FAILURE);
		}finally{
			session.close();
		}
	}

	@Override
	public Result<Appraisal> update(Appraisal t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			appraisalDao.update(t);
			return Result.success(R.Appraisal.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Appraisal.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Appraisal.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Appraisal> getBeanById(Serializable id) {
		try {
			return Result.value(appraisalDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Appraisal.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Appraisal> getBeanByFilter(Filter filter) {
		try {
			return Result.value(appraisalDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Appraisal.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Appraisal>> getList() {
		try {
			return Result.value(appraisalDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Appraisal.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Appraisal>> getListByFilter(Filter filter) {
		try {
			return Result.value(appraisalDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Appraisal.LOAD_FAILURE);
		}
	}

	@Override
	public Result save(Appraisal appraisal, List<AppraisalAtt> list) {
		Session session = null;
		Transaction tr = null;
		try {
			session = appraisalDao.openSession();
			tr = session.beginTransaction();
			setcreatemodify(appraisal);
			session.save(appraisal);
			for (AppraisalAtt att : list) {
				att.setAppraisalId(appraisal.getId());
				setcreatemodify(att);
				session.save(att);
			}
			tr.commit();
			return Result.success(R.Appraisal.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Appraisal.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Appraisal.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	private void setcreatemodify(BaseEntity t) {
		t.setCreateTime(new Date());
		t.setCreator(SynthesisActivator.getSessionUser().getRealName());
		t.setCreatorId(SynthesisActivator.getSessionUser().getId());
		t.setModifyTime(t.getCreateTime());
		t.setModifier(t.getCreator());
		t.setModifierId(t.getCreatorId());
		t.setEntityStatus(EntityStatus.NORMAL);
		
	}

	@Override
	public Result update(Appraisal dbBean, List<AppraisalAtt> list) {
		Session session = null;
		Transaction tr = null;
		try {
			session = appraisalDao.openSession();
			tr = session.beginTransaction();
			List<AppraisalAtt> appraisalAtts = (List<AppraisalAtt>)session.createQuery("from AppraisalAtt where appraisalId = "+dbBean.getId()).list();
			for (AppraisalAtt appraisalAtt : appraisalAtts) {
				session.delete(appraisalAtt);
				SynthesisActivator.getResourcesService().delete(appraisalAtt.getNewName());
			}
			for (AppraisalAtt att : list) {
				att.setAppraisalId(dbBean.getId());
				setcreatemodify(att);
				session.save(att);
			}
			dbBean.setModifyTime(new Date());
			dbBean.setModifier(SynthesisActivator.getSessionUser().getRealName());
			dbBean.setModifierId(SynthesisActivator.getSessionUser().getId());
			session.update(dbBean);
			tr.commit();
			return Result.success(R.Appraisal.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Appraisal.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Appraisal.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public List<Object> getListBySql(String sql) {
		return appraisalDao.getObjectListBySql(sql);
	}

}
