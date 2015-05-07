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
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.TaskLogDao;
import com.wiiy.synthesis.entity.TaskLog;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.TaskLogService;

/**
 * @author my
 */
public class TaskLogServiceImpl implements TaskLogService{
	
	private TaskLogDao taskLogDao;
	
	public void setTaskLogDao(TaskLogDao taskLogDao) {
		this.taskLogDao = taskLogDao;
	}

	@Override
	public Result<TaskLog> save(TaskLog t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			taskLogDao.save(t);
			return Result.success(R.TaskLog.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),TaskLog.class)));
		} catch (Exception e) {
			return Result.failure(R.TaskLog.SAVE_FAILURE);
		}
	}

	@Override
	public Result<TaskLog> delete(TaskLog t) {
		try {
			taskLogDao.delete(t);
			return Result.success(R.TaskLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.TaskLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<TaskLog> deleteById(Serializable id) {
		try {
			taskLogDao.deleteById(id);
			return Result.success(R.TaskLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.TaskLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<TaskLog> deleteByIds(String ids) {
		try {
			taskLogDao.deleteByIds(ids);
			return Result.success(R.TaskLog.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.TaskLog.DELETE_FAILURE);
		}
	}

	@Override
	public Result<TaskLog> update(TaskLog t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			taskLogDao.update(t);
			return Result.success(R.TaskLog.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),TaskLog.class)));
		} catch (Exception e) {
			return Result.failure(R.TaskLog.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<TaskLog> getBeanById(Serializable id) {
		try {
			return Result.value(taskLogDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.TaskLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<TaskLog> getBeanByFilter(Filter filter) {
		try {
			return Result.value(taskLogDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.TaskLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<TaskLog>> getList() {
		try {
			return Result.value(taskLogDao.getList());
		} catch (Exception e) {
			return Result.failure(R.TaskLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<TaskLog>> getListByFilter(Filter filter) {
		try {
			return Result.value(taskLogDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.TaskLog.LOAD_FAILURE);
		}
	}

	@Override
	public Result save(String memo, long taskId,Integer progress) {
		if(taskId == 0)return null;
		TaskLog log = new TaskLog();
		log.setTaskId(taskId);
		log.setExecuteTime(new Date());
		log.setExecuteUserId(SynthesisActivator.getSessionUser().getId());
		log.setExecuteUserName(SynthesisActivator.getSessionUser().getRealName());
		log.setMemo(memo);
		log.setProgress(progress);
		return save(log);
	}
	
	@Override
	public Result save(String memo, long taskId) {
		if(taskId == 0)return null;
		TaskLog log = new TaskLog();
		log.setTaskId(taskId);
		log.setExecuteTime(new Date());
		log.setExecuteUserId(SynthesisActivator.getSessionUser().getId());
		log.setExecuteUserName(SynthesisActivator.getSessionUser().getRealName());
		log.setMemo(memo);
		return save(log);
	}

	@Override
	public Result save(String memo, long id, Integer progress, Long userId) {
		if(id == 0)return null;
		TaskLog log = new TaskLog();
		log.setTaskId(id);
		log.setExecuteTime(new Date());
		log.setExecuteUserId(userId);
		log.setExecuteUserName(SynthesisActivator.getUserById(userId).getRealName());
		log.setMemo(memo);
		log.setProgress(progress);
		return save(log);
	}
}
