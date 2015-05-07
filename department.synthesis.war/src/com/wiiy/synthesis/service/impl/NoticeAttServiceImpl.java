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
import com.wiiy.synthesis.entity.NoticeAtt;
import com.wiiy.synthesis.dao.NoticeAttDao;
import com.wiiy.synthesis.service.NoticeAttService;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.activator.SynthesisActivator;

/**
 * @author my
 */
public class NoticeAttServiceImpl implements NoticeAttService{
	
	private NoticeAttDao noticeAttDao;
	
	public void setNoticeAttDao(NoticeAttDao noticeAttDao) {
		this.noticeAttDao = noticeAttDao;
	}

	@Override
	public Result<NoticeAtt> save(NoticeAtt t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			noticeAttDao.save(t);
			return Result.success(R.NoticeAtt.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),NoticeAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.SAVE_FAILURE);
		}
	}

	@Override
	public Result<NoticeAtt> delete(NoticeAtt t) {
		try {
			noticeAttDao.delete(t);
			return Result.success(R.NoticeAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<NoticeAtt> deleteById(Serializable id) {
		try {
			noticeAttDao.deleteById(id);
			return Result.success(R.NoticeAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<NoticeAtt> deleteByIds(String ids) {
		try {
			noticeAttDao.deleteByIds(ids);
			return Result.success(R.NoticeAtt.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.DELETE_FAILURE);
		}
	}

	@Override
	public Result<NoticeAtt> update(NoticeAtt t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			noticeAttDao.update(t);
			return Result.success(R.NoticeAtt.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),NoticeAtt.class)));
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<NoticeAtt> getBeanById(Serializable id) {
		try {
			return Result.value(noticeAttDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<NoticeAtt> getBeanByFilter(Filter filter) {
		try {
			return Result.value(noticeAttDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<NoticeAtt>> getList() {
		try {
			return Result.value(noticeAttDao.getList());
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<NoticeAtt>> getListByFilter(Filter filter) {
		try {
			return Result.value(noticeAttDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.NoticeAtt.LOAD_FAILURE);
		}
	}

}
