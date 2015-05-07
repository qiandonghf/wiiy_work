package com.wiiy.engineering.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.common.preferences.enums.DepartmentEnum;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.engineering.activator.EngineeringActivator;
import com.wiiy.engineering.dao.EngineeringProjectDao;
import com.wiiy.engineering.entity.EngineeringProject;
import com.wiiy.engineering.preferences.R;
import com.wiiy.engineering.service.EngineeringProjectService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.entity.Project;

/**
 * @author my
 */
public class EngineeringProjectServiceImpl implements EngineeringProjectService{
	
	private EngineeringProjectDao engineeringProjectDao;
	
	public void setEngineeringProjectDao(EngineeringProjectDao engineeringProjectDao) {
		this.engineeringProjectDao = engineeringProjectDao;
	}

	@Override
	public Result<EngineeringProject> save(EngineeringProject t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringProjectDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(t);
			session.save(t);
			Project project = new Project();
			BeanUtil.copyProperties(t, project);
			project.setProjectId(t.getId());
			project.setId(null);
			project.setDepartment(DepartmentEnum.ENGINEERING);
			session.save(project);
			tr.commit();
			return Result.success(R.EngineeringProject.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringProject.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EngineeringProject> delete(EngineeringProject t) {
		try {
			engineeringProjectDao.delete(t);
			return Result.success(R.EngineeringProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EngineeringProject> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringProjectDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM EngineeringProject WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Project WHERE department='ENGINEERING' and projectId ="+id).executeUpdate();
			tr.commit();
			return Result.success(R.EngineeringProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EngineeringProject> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringProjectDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM EngineeringProject WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Project WHERE department='ENGINEERING' and projectId in("+ids+")").executeUpdate();
			tr.commit();
			return Result.success(R.EngineeringProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(EngineeringActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EngineeringProject> update(EngineeringProject t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = engineeringProjectDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(EngineeringActivator.getSessionUser().getRealName());
			t.setModifierId(EngineeringActivator.getSessionUser().getId());
			session.update(t);
			Project project = (Project) session.createQuery(//
					"From Project WHERE department='ENGINEERING' and projectId ="+t.getId()).uniqueResult();			
			Long id = project.getId();
			BeanUtil.copyProperties(t, project);
			project.setId(id);
			project.setDepartment(DepartmentEnum.ENGINEERING);
			session.update(project);
			tr.commit();
			return Result.success(R.EngineeringProject.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EngineeringProject.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.UPDATE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<EngineeringProject> getBeanById(Serializable id) {
		try {
			return Result.value(engineeringProjectDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EngineeringProject> getBeanByFilter(Filter filter) {
		try {
			return Result.value(engineeringProjectDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringProject>> getList() {
		try {
			return Result.value(engineeringProjectDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EngineeringProject>> getListByFilter(Filter filter) {
		try {
			return Result.value(engineeringProjectDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.EngineeringProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		EngineeringProject project = engineeringProjectDao.getBeanByFilter(//
				new Filter(EngineeringProject.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = engineeringProjectDao.getRowCount(//
				new Filter(EngineeringProject.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)));
		try {
			String oldCode = project.getCode();
			count = Integer.parseInt(//
					oldCode.replace("["+CalendarUtil.now().get(Calendar.YEAR)+"]", ""));
		} catch (Exception e) {
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumIntegerDigits(6);
		code = code + nf.format(count+1);
		return Result.value(code);
	}
	
	private void setCreateAndModifyTime(BaseEntity entity) {
		entity.setCreateTime(new Date());
		entity.setCreator(EngineeringActivator.getSessionUser().getRealName());
		entity.setCreatorId(EngineeringActivator.getSessionUser().getId());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}

}
