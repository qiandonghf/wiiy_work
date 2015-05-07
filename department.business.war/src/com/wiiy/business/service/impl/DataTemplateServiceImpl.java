package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.business.dao.DataTemplateDao;
import com.wiiy.business.entity.DataProperty;
import com.wiiy.business.entity.DataTemplate;
import com.wiiy.business.entity.DataTemplatePropertyConfig;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.DataTemplateService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.business.activator.BusinessActivator;

/**
 * @author my
 */
public class DataTemplateServiceImpl implements DataTemplateService{
	
	private DataTemplateDao dataTemplateDao;
	
	public void setDataTemplateDao(DataTemplateDao dataTemplateDao) {
		this.dataTemplateDao = dataTemplateDao;
	}

	@Override
	public Result<DataTemplate> save(DataTemplate t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			dataTemplateDao.save(t);
			return Result.success(R.DataTemplate.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DataTemplate.class)));
		} catch (Exception e) {
			return Result.failure(R.DataTemplate.SAVE_FAILURE);
		}
	}

	@Override
	public Result<DataTemplate> delete(DataTemplate t) {
		try {
			dataTemplateDao.delete(t);
			return Result.success(R.DataTemplate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DataTemplate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DataTemplate> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = dataTemplateDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("delete DataTemplatePropertyConfig where templateId = "+id).executeUpdate();
			session.createQuery("delete DataTemplate where id = "+id).executeUpdate();
			tr.commit();
			return Result.success(R.DataTemplate.DELETE_SUCCESS);
		} catch (ConstraintViolationException e) {
			tr.rollback();
			return Result.failure(BusinessActivator.getFKMessage(new FKConstraintException(e).getFK()));
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.DataTemplate.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<DataTemplate> deleteByIds(String ids) {
		try {
			dataTemplateDao.deleteByIds(ids);
			return Result.success(R.DataTemplate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DataTemplate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<DataTemplate> update(DataTemplate t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			dataTemplateDao.update(t);
			return Result.success(R.DataTemplate.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),DataTemplate.class)));
		} catch (Exception e) {
			return Result.failure(R.DataTemplate.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<DataTemplate> getBeanById(Serializable id) {
		try {
			return Result.value(dataTemplateDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.DataTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<DataTemplate> getBeanByFilter(Filter filter) {
		try {
			return Result.value(dataTemplateDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.DataTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DataTemplate>> getList() {
		try {
			return Result.value(dataTemplateDao.getList());
		} catch (Exception e) {
			return Result.failure(R.DataTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<DataTemplate>> getListByFilter(Filter filter) {
		try {
			return Result.value(dataTemplateDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.DataTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result saveConfig(Long templateId, String propertyIds) {
		Session session = null;
		Transaction tr = null;
		try {
			session = dataTemplateDao.openSession();
			tr = session.beginTransaction();
			session.createQuery("delete DataTemplatePropertyConfig where templateId="+templateId).executeUpdate();
			List<Long> ids = new ArrayList<Long>(Arrays.asList(StringUtil.splitToLongArray(propertyIds)));
			List<Long> addIds = new ArrayList<Long>();
			for (Long propertyId : ids) {
				DataProperty p = (DataProperty) session.get(DataProperty.class, propertyId);
				Long[] pIds = StringUtil.splitToLongArray(p.getParentIds());
				for (Long pId : pIds) {
					if(!ids.contains(pId) && !addIds.contains(pId)) addIds.add(pId);
				}
			}
			ids.addAll(addIds);
			for (Long propertyId : ids) {
				DataTemplatePropertyConfig pConfig = new DataTemplatePropertyConfig();
				pConfig.setTemplateId(templateId);
				pConfig.setPropertyId(propertyId);
				pConfig.setDisplayOrder(0);
				setCreatorModifier(pConfig);
				session.save(pConfig);
			}
			tr.commit();
			return Result.success("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure("保存失败");
		} finally {
			session.close();
		}
	}
	
	private void setCreatorModifier(BaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreatorId(BusinessActivator.getSessionUser().getId());
		entity.setCreator(BusinessActivator.getSessionUser().getRealName());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Result loadConfig(Long id) {
		List<Long> list = dataTemplateDao.getObjectListByHql("select propertyId from DataTemplatePropertyConfig where templateId="+id);
		StringBuilder sb = new StringBuilder();
		sb.append(",");
		for (Long propertyId : list) {
			sb.append(propertyId).append(",");
		}
		return Result.value(sb.toString());
	}

}
