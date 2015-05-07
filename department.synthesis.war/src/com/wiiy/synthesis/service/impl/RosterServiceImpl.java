package com.wiiy.synthesis.service.impl;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.ArchivesDao;
import com.wiiy.synthesis.dao.RosterDao;
import com.wiiy.synthesis.entity.Archives;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.preferences.enums.PositionConditionEnum;
import com.wiiy.synthesis.service.RosterService;

/**
 * @author my
 */
public class RosterServiceImpl implements RosterService{
	
	private ArchivesDao archivesDao;
	private RosterDao rosterDao;
	public void setRosterDao(RosterDao rosterDao) {
		this.rosterDao = rosterDao;
	}
	private void setModifier(BaseEntity entity){
		entity.setModifyTime(new Date());
		entity.setModifierId(SynthesisActivator.getSessionUser().getId());
		entity.setModifier(SynthesisActivator.getSessionUser().getRealName());
	}
	private void setCreatorModifier(BaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreatorId(SynthesisActivator.getSessionUser().getId());
		entity.setCreator(SynthesisActivator.getSessionUser().getRealName());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}
	@Override
	public Result update(User user, Archives archives) {
		Session session = null;
		Transaction tr = null;
		try {
			session = rosterDao.openSession();
			tr = session.beginTransaction();
			
			User dbUser= (User) session.get(User.class,user.getId());
			
			BeanUtil.copyProperties(user, dbUser);
			setModifier(dbUser);
			session.update(dbUser);
			
			if(archives.getId()==null){ 
				if(archives.getUserId()==null) {
					archives.setUserId(user.getId());
				}
				if(("").equals(archives.getName())){
						tr.rollback();
						return Result.failure("员工档案姓名不能为空"); 
				}else{
					archives.setName(archives.getName());
					if(archives.getStatus()==null) archives.setStatus(PositionConditionEnum.NO);
					if(("").equals(archives.getNationalityId()))archives.setNationalityId(null);
					if(("").equals(archives.getPoliticalId()))archives.setPoliticalId(null);
					if(("").equals(archives.getEthnicId()))archives.setEthnicId(null);
					session.save(archives);
				}
			}
			else{
				Archives dbArchives = (Archives) session.get(Archives.class,archives.getId());
				BeanUtil.copyProperties(archives, dbArchives);
				if(("").equals(dbArchives.getNationalityId()))dbArchives.setNationalityId(null);
				if(("").equals(dbArchives.getPoliticalId()))dbArchives.setPoliticalId(null);
				if(("").equals(dbArchives.getEthnicId()))dbArchives.setEthnicId(null);
				setModifier(dbArchives);
				session.update(dbArchives);
			}
			tr.commit();
			return Result.success(R.Archives.UPDATE_SUCCESS,archives);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),User.class)));
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.User.UPDATE_FAILURE);
		} finally {
			session.close();
		}
	}
	@Override
	public Result<Archives> save(Archives t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			archivesDao.save(t);
			return Result.success(R.Archives.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Archives.class)));
		} catch (Exception e) {
			return Result.failure(R.Archives.SAVE_FAILURE);
		}
	}
	@Override
	public Result<Archives> delete(Archives t) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Result<Archives> deleteById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Result<Archives> deleteByIds(String ids) {
		// TODO Auto-generated method stub
		return null;
	}
	public Result<Archives> update(Archives t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			archivesDao.update(t);
			return Result.success(R.Archives.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Archives.class)));
		} catch (Exception e) {
			return Result.failure(R.Archives.UPDATE_FAILURE);
		}
	}
	public Result<Archives> getBeanById(Serializable id) {
		try {
			return Result.value(archivesDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Archives.LOAD_FAILURE);
		}
	}
	public Result<Archives> getBeanByFilter(Filter filter) {
		try {
			return Result.value(archivesDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Archives.LOAD_FAILURE);
		}
	}
	@Override
	public Result<List<Archives>> getList() {
		try {
			return Result.value(archivesDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Archives.LOAD_FAILURE);
		}
	}
	@Override
	public Result<List<Archives>> getListByFilter(Filter filter) {
		try {
			return Result.value(archivesDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Archives.LOAD_FAILURE);
		}
	}
}
