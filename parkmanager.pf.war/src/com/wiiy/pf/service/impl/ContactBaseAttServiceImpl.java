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
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.dao.ContactBaseAttDao;
import com.wiiy.pf.service.ContactBaseAttService;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.activator.PfActivator;

/**
 * @author my
 */
public class ContactBaseAttServiceImpl implements ContactBaseAttService{
	
	private ContactBaseAttDao contactBaseAttDao;
	
	public void setContactBaseAttDao(ContactBaseAttDao contactBaseAttDao) {
		this.contactBaseAttDao = contactBaseAttDao;
	}

	@Override
	public Result<ContactBaseAtt> save(ContactBaseAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(PfActivator.getSessionUser().getRealName());
			t.setCreatorId(PfActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contactBaseAttDao.save(t);
			return Result.success(R.ContactBaseAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContactBaseAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseAtt> delete(ContactBaseAtt t) {
		try {
			String path = t.getPath();
			PfActivator.getResourcesService().delete(path);
			contactBaseAttDao.delete(t);
			return Result.success(R.ContactBaseAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseAtt> deleteById(Serializable id) {
		try {
			ContactBaseAtt t = getBeanById(id).getValue();
			PfActivator.getResourcesService().delete(t.getPath());
			contactBaseAttDao.deleteById(id);
			return Result.success(R.ContactBaseAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseAtt> deleteByIds(String ids) {
		try {
			contactBaseAttDao.deleteByIds(ids);
			return Result.success(R.ContactBaseAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseAtt> update(ContactBaseAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			contactBaseAttDao.update(t);
			return Result.success(R.ContactBaseAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContactBaseAtt.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseAtt> getBeanById(Serializable id) {
		try {
			return Result.value(contactBaseAttDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ContactBaseAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contactBaseAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContactBaseAtt>> getList() {
		try {
			return Result.value(contactBaseAttDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContactBaseAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(contactBaseAttDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactBaseAtt.LOAD_FAILURE);
		}
	}

}
