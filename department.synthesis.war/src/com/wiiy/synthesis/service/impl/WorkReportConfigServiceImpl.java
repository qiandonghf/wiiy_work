package com.wiiy.synthesis.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.WorkReportConfigDao;
import com.wiiy.synthesis.entity.TaskProject;
import com.wiiy.synthesis.entity.WorkReportConfig;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.WorkReportConfigService;

public class WorkReportConfigServiceImpl implements WorkReportConfigService{
	private WorkReportConfigDao workReportConfigDao;
	@Override
	public Result<WorkReportConfig> save(WorkReportConfig t) {
		try{
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			workReportConfigDao.save(t);
			SynthesisActivator.getOperationLogService().logOP("添加报送人【"+SynthesisActivator.getSessionUser().getRealName()+"】");
			return Result.success(R.WorkReportConfig.SAVE_SUCCESS,t);
		}catch(UKConstraintException e){
			return Result.failure(R.getFKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(), TaskProject.class)));
		}catch(Exception e){
			return Result.failure(R.WorkReportConfig.SAVE_FAILURE);
		}
	}

	@Override
	public Result<WorkReportConfig> delete(WorkReportConfig t) {
		try{
			workReportConfigDao.delete(t);
			SynthesisActivator.getOperationLogService().logOP("删除报送人为【"+t.getCreator()+"】的相关信息");
			return Result.success(R.WorkReportConfig.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			return Result.failure(R.WorkReportConfig.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkReportConfig> deleteById(Serializable id) {
		try{
			workReportConfigDao.deleteById(id);
			SynthesisActivator.getOperationLogService().logOP("删除id为【"+id+"】的报送人相关信息");
			return Result.success(R.WorkReportConfig.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			return Result.failure(R.WorkReportConfig.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkReportConfig> deleteByIds(String ids) {
		try{
			workReportConfigDao.deleteByIds(ids);
			for (String id : ids.split(",")) {
				SynthesisActivator.getOperationLogService().logOP("删除id为【"+id+"】的报送人相关信息");
			}
			return Result.success(R.WorkReportConfig.DELETE_SUCCESS);
		}catch(FKConstraintException e){
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		}catch(Exception e){
			return Result.failure(R.WorkReportConfig.DELETE_FAILURE);
		}
	}

	@Override
	public Result<WorkReportConfig> update(WorkReportConfig t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			workReportConfigDao.update(t);
			SynthesisActivator.getOperationLogService().logOP("修改id为【"+t.getId()+"】的报送人相关信息");
			return Result.success(R.WorkReportConfig.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),TaskProject.class)));
		} catch (Exception e) {
			return Result.failure(R.WorkReportConfig.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<WorkReportConfig> getBeanById(Serializable id) {
		try{
			return Result.value(workReportConfigDao.getBeanById(id));
		}catch(Exception e){
			return Result.failure(R.WorkReport.LOAD_FAILURE);
		}
	}

	@Override
	public Result<WorkReportConfig> getBeanByFilter(Filter filter) {
		try{
			return Result.value(workReportConfigDao.getBeanByFilter(filter));
		}catch(Exception e){
			return Result.failure(R.WorkReportConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<WorkReportConfig>> getList() {
		try{
			return Result.value(workReportConfigDao.getList());
		}catch(Exception e){
			return Result.failure(R.WorkReportConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<WorkReportConfig>> getListByFilter(Filter filter) {
		try{
			return Result.value(workReportConfigDao.getListByFilter(filter));
		}catch(Exception e){
			return Result.failure(R.WorkReportConfig.LOAD_FAILURE);
		}
	}

	public WorkReportConfigDao getWorkReportConfigDao() {
		return workReportConfigDao;
	}

	public void setWorkReportConfigDao(WorkReportConfigDao workReportConfigDao) {
		this.workReportConfigDao = workReportConfigDao;
	}

	/**
	 * 设置报送人
	 */
	public Result configReporter(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = workReportConfigDao.openSession();
			tr = session.beginTransaction();
			//先删除表中的数据，然后在插入新的数据
			if(ids!=null){
				Long curLoginUserId = SynthesisActivator.getSessionUser().getId();
				session.createQuery("delete WorkReportConfig where reporterId="+curLoginUserId).executeUpdate();
				Long[] idArrays = StringUtil.splitToLongArray(ids);
			for (Long receiverId: idArrays) {				   				
					WorkReportConfig workReportConfig = new WorkReportConfig();			
					//接收人id
					workReportConfig.setReceiverId(receiverId);
					workReportConfig.setReceiver(SynthesisActivator.getUserById(receiverId));
					//报送人id
					workReportConfig.setReporterId(SynthesisActivator.getSessionUser().getId());
					workReportConfig.setReporter(SynthesisActivator.getSessionUser());
					//一些常规信息
					workReportConfig.setCreateTime(new Date());
					workReportConfig.setCreator(SynthesisActivator.getSessionUser().getRealName());
					workReportConfig.setCreatorId(SynthesisActivator.getSessionUser().getId());
					workReportConfig.setModifyTime(workReportConfig.getCreateTime());
					workReportConfig.setModifier(workReportConfig.getCreator());
					workReportConfig.setModifierId(workReportConfig.getCreatorId());
					workReportConfig.setEntityStatus(EntityStatus.NORMAL);
					session.save(workReportConfig);
					SynthesisActivator.getOperationLogService().logOP("添加报表接收人【"+SynthesisActivator.getUserById(receiverId)+"】");
				}
			}			
			tr.commit();
			return Result.success(R.WorkReportConfig.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),WorkReportConfig.class)));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.WorkReportConfig.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}
}
		
