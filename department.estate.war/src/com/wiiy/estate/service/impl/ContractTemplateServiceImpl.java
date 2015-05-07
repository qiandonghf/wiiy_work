package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.ContractTemplateDao;
import com.wiiy.estate.entity.EstateContractTemplate;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.ContractTemplateService;
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
	public Result<EstateContractTemplate> save(EstateContractTemplate t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contractTemplateDao.save(t);
			return Result.success(R.EstateContractTemplate.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractTemplate.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.SAVE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractTemplate> delete(EstateContractTemplate t) {
		try {
			contractTemplateDao.delete(t);
			EstateActivator.getResourcesService().delete(t.getNewName());
			return Result.success(R.EstateContractTemplate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractTemplate> deleteById(Serializable id) {
		try {
			EstateContractTemplate c = contractTemplateDao.getBeanById(id);
			String path = c.getNewName();
			contractTemplateDao.deleteById(id);
			EstateActivator.getResourcesService().delete(path);
			return Result.success(R.EstateContractTemplate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.DELETE_FAILURE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result<EstateContractTemplate> deleteByIds(String ids) {
		try {
			contractTemplateDao.deleteByIds(ids);
			List<String> newNames= contractTemplateDao.getObjectListByHql("select new_name from EstateContractTemplate where id in ("+ids+")");
			for(String newName:newNames){
				EstateActivator.getResourcesService().delete(newName);
			}
			return Result.success(R.EstateContractTemplate.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.DELETE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractTemplate> update(EstateContractTemplate t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			contractTemplateDao.update(t);
			return Result.success(R.EstateContractTemplate.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),EstateContractTemplate.class)));
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<EstateContractTemplate> getBeanById(Serializable id) {
		try {
			return Result.value(contractTemplateDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<EstateContractTemplate> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contractTemplateDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractTemplate>> getList() {
		try {
			return Result.value(contractTemplateDao.getList());
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<EstateContractTemplate>> getListByFilter(Filter filter) {
		try {
			return Result.value(contractTemplateDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.EstateContractTemplate.LOAD_FAILURE);
		}
	}

}
