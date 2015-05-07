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
import com.wiiy.pf.entity.ContactForm;
import com.wiiy.pf.dao.ContactFormDao;
import com.wiiy.pf.service.ContactFormService;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.activator.PfActivator;

/**
 * @author my
 */
public class ContactFormServiceImpl implements ContactFormService{
	
	private ContactFormDao contactFormDao;
	
	public void setContactFormDao(ContactFormDao contactFormDao) {
		this.contactFormDao = contactFormDao;
	}

	@Override
	public Result<ContactForm> save(ContactForm t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(PfActivator.getSessionUser().getRealName());
			t.setCreatorId(PfActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contactFormDao.save(t);
			return Result.success(R.ContactForm.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContactForm.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ContactForm> delete(ContactForm t) {
		try {
			contactFormDao.delete(t);
			return Result.success(R.ContactForm.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactForm> deleteById(Serializable id) {
		try {
			contactFormDao.deleteById(id);
			return Result.success(R.ContactForm.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactForm> deleteByIds(String ids) {
		try {
			contactFormDao.deleteByIds(ids);
			return Result.success(R.ContactForm.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContactForm> update(ContactForm t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			contactFormDao.update(t);
			return Result.success(R.ContactForm.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContactForm.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ContactForm> getBeanById(Serializable id) {
		try {
			return Result.value(contactFormDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ContactForm> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contactFormDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContactForm>> getList() {
		try {
			return Result.value(contactFormDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContactForm>> getListByFilter(Filter filter) {
		try {
			return Result.value(contactFormDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContactForm.LOAD_FAILURE);
		}
	}

}
