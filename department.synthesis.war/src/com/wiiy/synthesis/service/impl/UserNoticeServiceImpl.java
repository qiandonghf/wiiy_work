package com.wiiy.synthesis.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.entity.UserNotice;
import com.wiiy.synthesis.dao.UserNoticeDao;
import com.wiiy.synthesis.service.UserNoticeService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class UserNoticeServiceImpl implements UserNoticeService{
	
	private UserNoticeDao userNoticeDao;
	
	public void setUserNoticeDao(UserNoticeDao userNoticeDao) {
		this.userNoticeDao = userNoticeDao;
	}

	@Override
	public Result<UserNotice> save(UserNotice t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			userNoticeDao.save(t);
			return Result.success(R.UserNotice.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),UserNotice.class)));
		} catch (Exception e) {
			return Result.failure(R.UserNotice.SAVE_FAILURE);
		}
	}

	@Override
	public Result<UserNotice> delete(UserNotice t) {
		try {
			userNoticeDao.delete(t);
			return Result.success(R.UserNotice.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.UserNotice.DELETE_FAILURE);
		}
	}

	@Override
	public Result<UserNotice> deleteById(Serializable id) {
		try {
			userNoticeDao.deleteById(id);
			return Result.success(R.UserNotice.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.UserNotice.DELETE_FAILURE);
		}
	}

	@Override
	public Result<UserNotice> deleteByIds(String ids) {
		try {
			userNoticeDao.deleteByIds(ids);
			return Result.success(R.UserNotice.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.UserNotice.DELETE_FAILURE);
		}
	}

	@Override
	public Result<UserNotice> update(UserNotice t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			userNoticeDao.update(t);
			return Result.success(R.UserNotice.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),UserNotice.class)));
		} catch (Exception e) {
			return Result.failure(R.UserNotice.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<UserNotice> getBeanById(Serializable id) {
		try {
			return Result.value(userNoticeDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.UserNotice.LOAD_FAILURE);
		}
	}

	@Override
	public Result<UserNotice> getBeanByFilter(Filter filter) {
		try {
			return Result.value(userNoticeDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.UserNotice.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<UserNotice>> getList() {
		try {
			return Result.value(userNoticeDao.getList());
		} catch (Exception e) {
			return Result.failure(R.UserNotice.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<UserNotice>> getListByFilter(Filter filter) {
		try {
			return Result.value(userNoticeDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.UserNotice.LOAD_FAILURE);
		}
	}

}
