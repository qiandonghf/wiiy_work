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
import com.wiiy.synthesis.entity.SmsReceiver;
import com.wiiy.synthesis.dao.SmsReceiverDao;
import com.wiiy.synthesis.service.SmsReceiverService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class SmsReceiverServiceImpl implements SmsReceiverService{
	
	private SmsReceiverDao smsReceiverDao;
	
	public void setSmsReceiverDao(SmsReceiverDao smsReceiverDao) {
		this.smsReceiverDao = smsReceiverDao;
	}

	@Override
	public Result<SmsReceiver> save(SmsReceiver t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			smsReceiverDao.save(t);
			return Result.success(R.SmsReceiver.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SmsReceiver.class)));
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SmsReceiver> delete(SmsReceiver t) {
		try {
			smsReceiverDao.delete(t);
			return Result.success(R.SmsReceiver.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SmsReceiver> deleteById(Serializable id) {
		try {
			smsReceiverDao.deleteById(id);
			return Result.success(R.SmsReceiver.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SmsReceiver> deleteByIds(String ids) {
		try {
			smsReceiverDao.deleteByIds(ids);
			return Result.success(R.SmsReceiver.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SmsReceiver> update(SmsReceiver t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			smsReceiverDao.update(t);
			return Result.success(R.SmsReceiver.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SmsReceiver.class)));
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SmsReceiver> getBeanById(Serializable id) {
		try {
			return Result.value(smsReceiverDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SmsReceiver> getBeanByFilter(Filter filter) {
		try {
			return Result.value(smsReceiverDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SmsReceiver>> getList() {
		try {
			return Result.value(smsReceiverDao.getList());
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SmsReceiver>> getListByFilter(Filter filter) {
		try {
			return Result.value(smsReceiverDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.SmsReceiver.LOAD_FAILURE);
		}
	}

}
