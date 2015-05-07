package com.wiiy.park.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wiiy.common.activator.ProjectActivator;
import com.wiiy.common.preferences.R;
import com.wiiy.common.preferences.enums.BillRemindStatusEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.park.dao.BillRemindDao;
import com.wiiy.park.entity.BillRemind;
import com.wiiy.park.service.BillRemindService;

/**
 * @author my
 */
public class BillRemindServiceImpl implements BillRemindService{
	
	private BillRemindDao businessBillRemindDao;
	
	public void setBillRemindDao(BillRemindDao businessBillRemindDao) {
		this.businessBillRemindDao = businessBillRemindDao;
	}

	@Override
	public Result<BillRemind> save(BillRemind t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(ProjectActivator.getSessionUser().getRealName());
			t.setCreatorId(ProjectActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			businessBillRemindDao.save(t);
			return Result.success(R.BillRemind.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BillRemind.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.SAVE_FAILURE);
		}
	}

	@Override
	public Result<BillRemind> delete(BillRemind t) {
		try {
			businessBillRemindDao.delete(t);
			return Result.success(R.BillRemind.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BillRemind> deleteById(Serializable id) {
		try {
			businessBillRemindDao.deleteById(id);
			return Result.success(R.BillRemind.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BillRemind> deleteByIds(String ids) {
		try {
			businessBillRemindDao.deleteByIds(ids);
			return Result.success(R.BillRemind.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(ProjectActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.DELETE_FAILURE);
		}
	}

	@Override
	public Result<BillRemind> update(BillRemind t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			businessBillRemindDao.update(t);
			return Result.success(R.BillRemind.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),BillRemind.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<BillRemind> getBeanById(Serializable id) {
		try {
			return Result.value(businessBillRemindDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.LOAD_FAILURE);
		}
	}

	@Override
	public Result<BillRemind> getBeanByFilter(Filter filter) {
		try {
			return Result.value(businessBillRemindDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BillRemind>> getList() {
		try {
			return Result.value(businessBillRemindDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<BillRemind>> getListByFilter(Filter filter) {
		try {
			return Result.value(businessBillRemindDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.BillRemind.LOAD_FAILURE);
		}
	}

	@Override
	public Result getRowCountByFeeType(String department) {
		try{
			Map<String,Integer> map = new HashMap<String, Integer>();
			List<Object> objects = null;
			if(department!=null){
				objects = (List<Object>)businessBillRemindDao.getObjectListBySql("select type_id,count(*) from park_bill_remind where type_id like '"+department+"%' and remind_status = 'UNCALLEDBILL' GROUP BY type_id");
			}else{
				objects = (List<Object>)businessBillRemindDao.getObjectListBySql("select type_id,count(*) from park_bill_remind where remind_status = 'UNCALLEDBILL'");
			}
			if(objects!=null && objects.size()>0){
				for (Object object : objects) {
					Object[] obj = (Object[])object;
					if(obj[0]!=null){
						map.put(obj[0].toString(), Integer.valueOf(obj[1].toString()));
					}
				}
			}
			return Result.value(map);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Result remind(Long id) {
		try{
			BillRemind t = getBeanById(id).getValue();
			t.setModifyTime(new Date());
			t.setModifier(ProjectActivator.getSessionUser().getRealName());
			t.setModifierId(ProjectActivator.getSessionUser().getId());
			t.setRemindStatus(BillRemindStatusEnum.CALLEDBILL);
			businessBillRemindDao.update(t);
			return Result.success("设置已催缴成功");
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("设置已催缴失败");
		}
	}

}
