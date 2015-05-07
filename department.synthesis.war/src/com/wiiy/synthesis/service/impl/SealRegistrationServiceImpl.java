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
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.SealRegistrationDao;
import com.wiiy.synthesis.entity.SealRegistration;
import com.wiiy.synthesis.entity.SealRegistrationAtt;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.preferences.enums.SealApplyEnum;
import com.wiiy.synthesis.service.SealRegistrationService;

/**
 * @author my
 */
public class SealRegistrationServiceImpl implements SealRegistrationService{
	
	private SealRegistrationDao sealRegistrationDao;
	
	public void setSealRegistrationDao(SealRegistrationDao sealRegistrationDao) {
		this.sealRegistrationDao = sealRegistrationDao;
	}
	public void setcreatemodify(BaseEntity t){
		t.setCreateTime(new Date());
		t.setCreator(SynthesisActivator.getSessionUser().getRealName());
		t.setCreatorId(SynthesisActivator.getSessionUser().getId());
		t.setModifyTime(t.getCreateTime());
		t.setModifier(t.getCreator());
		t.setModifierId(t.getCreatorId());
		t.setEntityStatus(EntityStatus.NORMAL);
	}
	@Override
	public Result save(SealRegistration t, String attAddress) {
		Session session = null;
		Transaction tr = null;
		try {
			session = sealRegistrationDao.openSession();
			tr = session.beginTransaction();
			setcreatemodify(t);
			session.save(t);
			if(attAddress!=null && !("").equals(attAddress)){
				String[] files = attAddress.split(";");
				for (String string : files) {
					SealRegistrationAtt sealRegistrationAtt = new SealRegistrationAtt();
					sealRegistrationAtt.setName(string.split(",")[2]);
					sealRegistrationAtt.setSize(Long.parseLong(string.split(",")[1]));
					sealRegistrationAtt.setNewName(string.split(",")[0]);
					sealRegistrationAtt.setSealRegistrationId(t.getId());
					setcreatemodify(sealRegistrationAtt);
					session.save(sealRegistrationAtt);
				}
			}
			tr.commit();
			return Result.success(R.SealRegistration.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SealRegistration.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SealRegistration.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}
	@Override
	public Result update(SealRegistration t, String attAddress) {
		Session session = null;
		Transaction tr = null;
		try {
			session = sealRegistrationDao.openSession();
			tr = session.beginTransaction();
			List<SealRegistrationAtt> list = session.createQuery("from SealRegistrationAtt where sealRegistrationId = "+t.getId()).list();
			for (SealRegistrationAtt sealRegistrationAtt : list) {
				String path = sealRegistrationAtt.getNewName();
				session.delete(sealRegistrationAtt);
				SynthesisActivator.getResourcesService().delete(path);
			}
			if(attAddress!=null && !("").equals(attAddress)){
				String[] files = attAddress.split(";");
				for (String string : files) {
					SealRegistrationAtt sealRegistrationAtt = new SealRegistrationAtt();
					sealRegistrationAtt.setName(string.split(",")[2]);
					sealRegistrationAtt.setSize(Long.parseLong(string.split(",")[1]));
					sealRegistrationAtt.setNewName(string.split(",")[0]);
					sealRegistrationAtt.setSealRegistrationId(t.getId());
					setcreatemodify(sealRegistrationAtt);
					session.save(sealRegistrationAtt);
				}
			}
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			session.update(t);
			tr.commit();
			return Result.success(R.SealRegistration.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SealRegistration.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SealRegistration.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<SealRegistration> save(SealRegistration t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			sealRegistrationDao.save(t);
			return Result.success(R.SealRegistration.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SealRegistration.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistration.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SealRegistration> delete(SealRegistration t) {
		try {
			sealRegistrationDao.delete(t);
			return Result.success(R.SealRegistration.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistration.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SealRegistration> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = sealRegistrationDao.openSession();
			tr = session.beginTransaction();
			List<SealRegistrationAtt> list = session.createQuery("from SealRegistrationAtt where sealRegistrationId = "+id).list();
			for (SealRegistrationAtt sealRegistrationAtt : list) {
				String path = sealRegistrationAtt.getNewName();
				session.delete(sealRegistrationAtt);
				SynthesisActivator.getResourcesService().delete(path);
			}
			SealRegistration t = (SealRegistration)session.createQuery("from SealRegistration where id = "+id).list().get(0);
			session.delete(t);
			tr.commit();
			return Result.success(R.SealRegistration.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SealRegistration.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<SealRegistration> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = sealRegistrationDao.openSession();
			tr = session.beginTransaction();
			List<SealRegistrationAtt> list = session.createQuery("from SealRegistrationAtt where sealRegistrationId in ("+ids+")").list();
			for (SealRegistrationAtt sealRegistrationAtt : list) {
				String path = sealRegistrationAtt.getNewName();
				session.delete(sealRegistrationAtt);
				SynthesisActivator.getResourcesService().delete(path);
			}
			List<SealRegistration> tList = session.createQuery("from SealRegistration where id in ("+ids+")").list();
			for (SealRegistration sealRegistration : tList) {
				session.delete(sealRegistration);
			}
			tr.commit();
			return Result.success(R.SealRegistration.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SealRegistration.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<SealRegistration> update(SealRegistration t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			sealRegistrationDao.update(t);
			return Result.success(R.SealRegistration.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SealRegistration.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistration.UPDATE_FAILURE);
		}
	}
	@Override
	public Result agree(Long id) {
		try {
			SealRegistration t = getBeanById(id).getValue();
			t.setStatus(SealApplyEnum.SAGREE);
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			sealRegistrationDao.update(t);
			return Result.success(R.SealRegistration.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SealRegistration.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistration.UPDATE_FAILURE);
		}
	}
	@Override
	public Result disagree(Long id) {
		try {
			SealRegistration t = getBeanById(id).getValue();
			sealRegistrationDao.deleteById(t);
			return Result.success("退回成功");
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure("退回失败");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure("退回失败");
		}
	}


	@Override
	public Result<SealRegistration> getBeanById(Serializable id) {
		try {
			return Result.value(sealRegistrationDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistration.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SealRegistration> getBeanByFilter(Filter filter) {
		try {
			return Result.value(sealRegistrationDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistration.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SealRegistration>> getList() {
		try {
			return Result.value(sealRegistrationDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistration.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SealRegistration>> getListByFilter(Filter filter) {
		try {
			return Result.value(sealRegistrationDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SealRegistration.LOAD_FAILURE);
		}
	}

}
