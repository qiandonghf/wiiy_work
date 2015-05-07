package com.wiiy.sale.service.impl;


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
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dao.SaleProjectDao;
import com.wiiy.sale.entity.SaleProject;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.service.SaleProjectService;

/**
 * @author my
 */
public class SaleProjectServiceImpl implements SaleProjectService{
	
	private SaleProjectDao saleProjectDao;
	
	public void setSaleProjectDao(SaleProjectDao saleProjectDao) {
		this.saleProjectDao = saleProjectDao;
	}

	@Override
	public Result<SaleProject> save(SaleProject sale) {
		Session session = null;
		Transaction tr = null;
		try {
			session = saleProjectDao.openSession();
			tr = session.beginTransaction();
			setCreateAndModifyTime(sale);
			session.save(sale);
			Project project = new Project();
			BeanUtil.copyProperties(sale, project);
			project.setProjectId(sale.getId());
			project.setId(null);
			project.setDepartment(DepartmentEnum.SALE);
			session.save(project);
			tr.commit();
			return Result.success(R.SaleProject.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleProject.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleProject.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SaleProject> delete(SaleProject t) {
		try {
			saleProjectDao.delete(t);
			return Result.success(R.SaleProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleProject.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleProject> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = saleProjectDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SaleProject WHERE id ="+id).executeUpdate();
			session.createQuery("DELETE FROM Project WHERE department='SALE' and projectId ="+id).executeUpdate();
			tr.commit();
			return Result.success(R.SaleProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleProject.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SaleProject> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = saleProjectDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("DELETE FROM SaleProject WHERE id in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM Project WHERE department='SALE' and projectId in("+ids+")").executeUpdate();
			tr.commit();
			return Result.success(R.SaleProject.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleProject.DELETE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SaleProject> update(SaleProject t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = saleProjectDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			session.update(t);
			Project project = (Project) session.createQuery(//
					"From Project WHERE department='SALE' and projectId ="+t.getId()).uniqueResult();
			Long id = project.getId();
			BeanUtil.copyProperties(t, project);
			project.setId(id);
			project.setDepartment(DepartmentEnum.SALE);
			session.update(project);
			tr.commit();
			return Result.success(R.SaleProject.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleProject.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleProject.UPDATE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<SaleProject> getBeanById(Serializable id) {
		try {
			return Result.value(saleProjectDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleProject> getBeanByFilter(Filter filter) {
		try {
			return Result.value(saleProjectDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleProject>> getList() {
		try {
			return Result.value(saleProjectDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleProject>> getListByFilter(Filter filter) {
		try {
			return Result.value(saleProjectDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleProject.LOAD_FAILURE);
		}
	}

	@Override
	public Result<String> generateCode() {
		String code = "["+CalendarUtil.now().get(Calendar.YEAR)+"]";
		SaleProject project = saleProjectDao.getBeanByFilter(//
				new Filter(SaleProject.class).//
				ge("createTime", CalendarUtil.getEarliest(Calendar.YEAR)).//
				orderBy("id", Filter.DESC).maxResults(1));
		int count = saleProjectDao.getRowCount(//
				new Filter(SaleProject.class).//
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
		entity.setCreator(SaleActivator.getSessionUser().getRealName());
		entity.setCreatorId(SaleActivator.getSessionUser().getId());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}

}
