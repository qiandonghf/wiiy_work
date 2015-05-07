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
import com.wiiy.synthesis.dao.WorkArrangeDao;
import com.wiiy.synthesis.entity.WorkArrange;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.WorkArrangeService;

public class WorkArrangeServiceImpl implements WorkArrangeService{
	private WorkArrangeDao workArrangeDao;
	public void setWorkArrangeDao(WorkArrangeDao workArrangeDao) {
		this.workArrangeDao = workArrangeDao;
	}

	@Override
	public Result<WorkArrange> save(WorkArrange t) {
		try{
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			workArrangeDao.save(t);
			SynthesisActivator.getOperationLogService().logOP("新建id为【"+t.getId()+"】的排班");
			return Result.success(R.WorkArrange.SAVE_SUCCESS,t);
		}catch(UKConstraintException e){
			e.printStackTrace();
			return Result.failure(R.getFKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(), WorkArrange.class)));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkArrange.SAVE_FAILURE);
		}
	}

	@Override
	public Result<WorkArrange> update(WorkArrange t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			workArrangeDao.update(t);
			SynthesisActivator.getOperationLogService().logOP("更新id为【"+t.getId()+"】的排班");
			return Result.success(R.WorkArrange.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),WorkArrange.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.WorkArrange.UPDATE_FAILURE);
		}
	}
	
	@Override
	public Result<WorkArrange> delete(WorkArrange t) {
		try{
			workArrangeDao.delete(t);
			SynthesisActivator.getOperationLogService().logOP("删除id为【"+t.getId()+"】的排班");
			return Result.success(R.WorkArrange.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkArrange.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkArrange> deleteById(Serializable id) {
		try{
			workArrangeDao.deleteById(id);
			SynthesisActivator.getOperationLogService().logOP("删除id为【"+id+"】的排班");
			return Result.success(R.WorkArrange.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkArrange.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkArrange> deleteByIds(String ids) {
		try{
			workArrangeDao.deleteByIds(ids);
			for(String id:ids.split(",")){
				SynthesisActivator.getOperationLogService().logOP("删除id为【"+id+"】的排班");
			}			
			return Result.success(R.WorkArrange.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkArrange.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkArrange> getBeanByFilter(Filter filter) {
		try{
			return Result.value(workArrangeDao.getBeanByFilter(filter));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkArrange.LOAD_FAILURE);
		}
	}

	@Override
	public Result<WorkArrange> getBeanById(Serializable id) {
		try{
			return Result.value(workArrangeDao.getBeanById(id));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkArrange.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<WorkArrange>> getList() {
		try{
			return Result.value(workArrangeDao.getList());
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkArrange.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<WorkArrange>> getListByFilter(Filter filter) {
		try{
			return Result.value(workArrangeDao.getListByFilter(filter));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkArrange.LOAD_FAILURE);
		}
	}

}
