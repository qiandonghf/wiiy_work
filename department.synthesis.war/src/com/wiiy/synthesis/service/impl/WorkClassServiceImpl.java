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
import com.wiiy.synthesis.dao.WorkClassDao;
import com.wiiy.synthesis.entity.WorkClass;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.WorkClassService;

public class WorkClassServiceImpl implements WorkClassService{
	private WorkClassDao workClassDao;
	public void setWorkClassDao(WorkClassDao workClassDao) {
		this.workClassDao = workClassDao;
	}

	@Override
	public Result<WorkClass> save(WorkClass t) {
		try{
			List<WorkClass> workClasses = getListByFilter(new Filter(WorkClass.class).eq("name", t.getName())).getValue();
			if(workClasses!=null&&workClasses.size()>0){
				return Result.failure("班次名称已存在，请换个名称");
			}
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			workClassDao.save(t);
			SynthesisActivator.getOperationLogService().logOP("添加班制【"+t.getName()+"】");
			return Result.success(R.WorkClass.SAVE_SUCCESS,t);
		}catch(UKConstraintException e){
			e.printStackTrace();
			return Result.failure(R.getFKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(), WorkClass.class)));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkClass.SAVE_FAILURE);
		}
	}

	@Override
	public Result<WorkClass> update(WorkClass t) {
		try {
			List<WorkClass> workClasses = getListByFilter(new Filter(WorkClass.class).eq("name", t.getName()).ne("id", t.getId())).getValue();
			if(workClasses!=null&&workClasses.size()>0){
				return Result.failure("班次名称已存在，请换个名称");
			}
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			workClassDao.update(t);
			SynthesisActivator.getOperationLogService().logOP("更新班制【"+t.getName()+"】");
			return Result.success(R.WorkClass.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),WorkClass.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.WorkClass.UPDATE_FAILURE);
		}
	}
	
	@Override
	public Result<WorkClass> delete(WorkClass t) {
		try{
			workClassDao.delete(t);
			SynthesisActivator.getOperationLogService().logOP("删除班次【"+t.getName()+"】");
			return Result.success(R.WorkClass.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkClass.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkClass> deleteById(Serializable id) {
		try{
			workClassDao.deleteById(id);
			SynthesisActivator.getOperationLogService().logOP("删除id为【"+id+"】的班次");
			return Result.success(R.WorkClass.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkClass.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkClass> deleteByIds(String ids) {
		try{
			workClassDao.deleteByIds(ids);
			for(String id:ids.split(",")){
				SynthesisActivator.getOperationLogService().logOP("删除id为【"+id+"】的班次");
			}			
			return Result.success(R.WorkClass.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkClass.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkClass> getBeanByFilter(Filter filter) {
		try{
			return Result.value(workClassDao.getBeanByFilter(filter));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkClass.LOAD_FAILURE);
		}
	}

	@Override
	public Result<WorkClass> getBeanById(Serializable id) {
		try{
			return Result.value(workClassDao.getBeanById(id));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkClass.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<WorkClass>> getList() {
		try{
			return Result.value(workClassDao.getList());
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkClass.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<WorkClass>> getListByFilter(Filter filter) {
		try{
			return Result.value(workClassDao.getListByFilter(filter));
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure(R.WorkClass.LOAD_FAILURE);
		}
	}

}
