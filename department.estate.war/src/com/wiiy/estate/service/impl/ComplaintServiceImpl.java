package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.entity.Complaint;
import com.wiiy.estate.dao.ComplaintDao;
import com.wiiy.estate.service.ComplaintService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class ComplaintServiceImpl implements ComplaintService{
	
	private ComplaintDao complaintDao;
	
	public void setComplaintDao(ComplaintDao complaintDao) {
		this.complaintDao = complaintDao;
	}

	@Override
	public Result<Complaint> save(Complaint t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			complaintDao.save(t);
			return Result.success(R.Complaint.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Complaint.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Complaint> delete(Complaint t) {
		try {
			complaintDao.delete(t);
			return Result.success(R.Complaint.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Complaint> deleteById(Serializable id) {
		try {
			complaintDao.deleteById(id);
			return Result.success(R.Complaint.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Complaint> deleteByIds(String ids) {
		try {
			complaintDao.deleteByIds(ids);
			return Result.success(R.Complaint.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Complaint> update(Complaint t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			complaintDao.update(t);
			return Result.success(R.Complaint.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Complaint.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Complaint> getBeanById(Serializable id) {
		try {
			return Result.value(complaintDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Complaint> getBeanByFilter(Filter filter) {
		try {
			return Result.value(complaintDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Complaint>> getList() {
		try {
			return Result.value(complaintDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Complaint>> getListByFilter(Filter filter) {
		try {
			return Result.value(complaintDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Complaint.LOAD_FAILURE);
		}
	}

}
