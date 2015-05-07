package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.ContractTemplateDao;
import com.wiiy.business.entity.ContractTemplate;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.ContractTemplateService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class ContractTemplateServiceImpl implements ContractTemplateService{
	
	private ContractTemplateDao contractTemplateDao;
	
	public void setContractTemplateDao(ContractTemplateDao contractTemplateDao) {
		this.contractTemplateDao = contractTemplateDao;
	}

	@Override
	public Result<ContractTemplate> save(ContractTemplate t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractTemplateDao.save(t);
			return Result.success(R.ContractTemplate.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContractTemplate.class)));
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ContractTemplate> delete(ContractTemplate t) {
		try {
			contractTemplateDao.delete(t);
			BusinessActivator.getResourcesService().delete(t.getNewName());
			return Result.success(R.ContractTemplate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContractTemplate> deleteById(Serializable id) {
		try {
			ContractTemplate c = contractTemplateDao.getBeanById(id);
			String path = c.getNewName();
			contractTemplateDao.deleteById(id);
			BusinessActivator.getResourcesService().delete(path);
			return Result.success(R.ContractTemplate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.DELETE_FAILURE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result<ContractTemplate> deleteByIds(String ids) {
		try {
			contractTemplateDao.deleteByIds(ids);
			List<String> newNames= contractTemplateDao.getObjectListByHql("select new_name from ContractTemplate where id in ("+ids+")");
			for(String newName:newNames){
				BusinessActivator.getResourcesService().delete(newName);
			}
			return Result.success(R.ContractTemplate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContractTemplate> update(ContractTemplate t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			contractTemplateDao.update(t);
			return Result.success(R.ContractTemplate.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContractTemplate.class)));
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ContractTemplate> getBeanById(Serializable id) {
		try {
			return Result.value(contractTemplateDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ContractTemplate> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractTemplateDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContractTemplate>> getList() {
		try {
			return Result.value(contractTemplateDao.getList());
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContractTemplate>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractTemplateDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.ContractTemplate.LOAD_FAILURE);
		}
	}

}
