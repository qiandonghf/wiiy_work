package com.wiiy.activiti.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.activiti.Activator;
import com.wiiy.activiti.dao.LeaveDao;
import com.wiiy.activiti.entity.Leave;
import com.wiiy.activiti.preferences.R;
import com.wiiy.activiti.service.LeaveService;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * 请假实体管理
 *
 * @author HenryYan
 */
public class LeaveServiceImpl  implements LeaveService{

    private LeaveDao leaveDao;

    

    public void setLeaveDao(LeaveDao leaveDao) {
        this.leaveDao = leaveDao;
    }

	@Override
	public Result<Leave> save(Leave t) {
		 try {
			if (t.getId() == null) {
			        t.setApplyTime(new Date());
			 }
			leaveDao.save(t);
			//Activator.getOperationLogService().logOP("新建请假申请【"+t.getId()+"】");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.failure(R.Leave.SAVE_FAILURE);
		}
	    
	    return Result.success(R.Leave.SAVE_SUCCESS);
		//return null;
	}

	@Override
	public Result<Leave> delete(Leave t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<Leave> deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<Leave> deleteByIds(String ids) {
		// TODO Auto-generated method stub
		return null;
	}


	
	@Override
	public Result<Leave> update(Leave t) {
		try {
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			leaveDao.update(t);
			Activator.getOperationLogService().logOP("更新请假申请【"+t.getId()+"】,流程编号：【"+t.getProcessInstanceId()+"】");
			return Result.success(R.Leave.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Leave.class)));
		} catch (Exception e) {
			return Result.failure(R.Leave.UPDATE_FAILURE);
		}
	}


	@Override
	public Result<Leave> getBeanById(Serializable id) {
		try {
			return Result.value(leaveDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Leave.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Leave> getBeanByFilter(Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<List<Leave>> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result<List<Leave>> getListByFilter(Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
