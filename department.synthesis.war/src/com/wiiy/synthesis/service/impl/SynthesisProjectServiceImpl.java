package com.wiiy.synthesis.service.impl;


import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.entity.Project;
import com.wiiy.common.preferences.enums.DepartmentEnum;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.SynthesisProjectDao;
import com.wiiy.synthesis.entity.SynthesisProject;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.SynthesisProjectService;

/**
 * @author my
 */
public class SynthesisProjectServiceImpl implements SynthesisProjectService{
	
	private SynthesisProjectDao synthesisProjectDao;
	
	public void setSynthesisProjectDao(SynthesisProjectDao synthesisProjectDao) {
		this.synthesisProjectDao = synthesisProjectDao;
	}

	@Override
	public Result<SynthesisProject> save(SynthesisProject t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisProjectDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(t);
			session.save(t);
			Project project = new Project();
			BeanUtil.copyProperties(t, project);
			project.setProjectId(t.getId());
			project.setId(null);
			project.setDepartment(DepartmentEnum.SYNTHESIS);
			session.save(project);
			tr.commit();
			return Result.success(R.SynthesisProject.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisProject.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SynthesisProject> delete(SynthesisProject t) {
		try {
			synthesisProjectDao.delete(t);
			return Result.success(R.SynthesisProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SynthesisProject> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisProjectDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SynthesisProject WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Project WHERE department='SYNTHESIS' and projectId ="+id).executeUpdate();
			tr.commit();
			return Result.success(R.SynthesisProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SynthesisProject> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisProjectDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SynthesisProject WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Project WHERE department='SYNTHESIS' and projectId in("+ids+")").executeUpdate();
			tr.commit();
			return Result.success(R.SynthesisProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SynthesisProject> update(SynthesisProject t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = synthesisProjectDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			session.update(t);
			Project project = (Project) session.createQuery(//
					"From Project WHERE department='SYNTHESIS' and projectId ="+t.getId()).uniqueResult();
			Long id = project.getId();
			BeanUtil.copyProperties(t, project);
			project.setId(id);
			project.setDepartment(DepartmentEnum.SYNTHESIS);
			session.update(project);
			tr.commit();
			return Result.success(R.SynthesisProject.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SynthesisProject.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.UPDATE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SynthesisProject> getBeanById(Serializable id) {
		try {
			return Result.value(synthesisProjectDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SynthesisProject> getBeanByFilter(Filter filter) {
		try {
			return Result.value(synthesisProjectDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisProject>> getList() {
		try {
			return Result.value(synthesisProjectDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SynthesisProject>> getListByFilter(Filter filter) {
		try {
			return Result.value(synthesisProjectDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SynthesisProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SynthesisProject project = synthesisProjectDao.getBeanByFilter(//
				new Filter(SynthesisProject.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = synthesisProjectDao.getRowCount(//
				new Filter(SynthesisProject.class).//
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
		entity.setCreator(SynthesisActivator.getSessionUser().getRealName());
		entity.setCreatorId(SynthesisActivator.getSessionUser().getId());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}

}
