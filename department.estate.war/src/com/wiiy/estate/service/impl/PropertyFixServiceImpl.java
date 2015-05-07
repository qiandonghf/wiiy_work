package com.wiiy.estate.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.entity.PropertyFix;
import com.wiiy.estate.dao.PropertyFixDao;
import com.wiiy.estate.service.PropertyFixService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class PropertyFixServiceImpl implements PropertyFixService{
	
	private PropertyFixDao propertyFixDao;
	
	public void setPropertyFixDao(PropertyFixDao propertyFixDao) {
		this.propertyFixDao = propertyFixDao;
	}

	@Override
	public Result<PropertyFix> save(PropertyFix t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			propertyFixDao.save(t);
			return Result.success(R.PropertyFix.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),PropertyFix.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.SAVE_FAILURE);
		}
	}

	@Override
	public Result<PropertyFix> delete(PropertyFix t) {
		try {
			propertyFixDao.delete(t);
			return Result.success(R.PropertyFix.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.DELETE_FAILURE);
		}
	}

	@Override
	public Result<PropertyFix> deleteById(Serializable id) {
		try {
			propertyFixDao.deleteById(id);
			return Result.success(R.PropertyFix.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.DELETE_FAILURE);
		}
	}

	@Override
	public Result<PropertyFix> deleteByIds(String ids) {
		try {
			propertyFixDao.deleteByIds(ids);
			return Result.success(R.PropertyFix.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.DELETE_FAILURE);
		}
	}

	@Override
	public Result<PropertyFix> update(PropertyFix t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			propertyFixDao.update(t);
			return Result.success(R.PropertyFix.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),PropertyFix.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<PropertyFix> getBeanById(Serializable id) {
		try {
			return Result.value(propertyFixDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.LOAD_FAILURE);
		}
	}

	@Override
	public Result<PropertyFix> getBeanByFilter(Filter filter) {
		try {
			return Result.value(propertyFixDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<PropertyFix>> getList() {
		try {
			return Result.value(propertyFixDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<PropertyFix>> getListByFilter(Filter filter) {
		try {
			return Result.value(propertyFixDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.PropertyFix.LOAD_FAILURE);
		}
	}

		//订单号
		@Override
		public String getOrderNum() {
			String orderNum = "";
			int num = 1;
			String timeNum = DateUtil.format(new Date(),"yyyy")+DateUtil.format(new Date(),"MM")+DateUtil.format(new Date(),"dd")+"0";
			orderNum = timeNum+num;
			List<PropertyFix> list = getList().getValue();
			for(PropertyFix pf : list){
				if(pf.getOddNo()!=null && pf.getOddNo().equals(orderNum)){
					num++;
					orderNum = timeNum+num;
				}
			}
			return orderNum;
		}

}
