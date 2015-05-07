package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.sale.entity.ContectInfo;
import com.wiiy.sale.dao.ContectInfoDao;
import com.wiiy.sale.service.ContectInfoService;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.preferences.enums.InfoEnum;
import com.wiiy.sale.activator.SaleActivator;

/**
 * @author my
 */
public class ContectInfoServiceImpl implements ContectInfoService{
	
	private ContectInfoDao contectInfoDao;
	
	public void setContectInfoDao(ContectInfoDao contectInfoDao) {
		this.contectInfoDao = contectInfoDao;
	}

	@Override
	public Result<ContectInfo> save(ContectInfo t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			contectInfoDao.save(t);
			return Result.success(R.ContectInfo.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContectInfo.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.SAVE_FAILURE);
		}
	}

	@Override
	public Result<ContectInfo> delete(ContectInfo t) {
		try {
			contectInfoDao.delete(t);
			return Result.success(R.ContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContectInfo> deleteById(Serializable id) {
		try {
			contectInfoDao.deleteById(id);
			return Result.success(R.ContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContectInfo> deleteByIds(String ids) {
		try {
			contectInfoDao.deleteByIds(ids);
			return Result.success(R.ContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<ContectInfo> update(ContectInfo t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			contectInfoDao.update(t);
			return Result.success(R.ContectInfo.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),ContectInfo.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<ContectInfo> getBeanById(Serializable id) {
		try {
			return Result.value(contectInfoDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<ContectInfo> getBeanByFilter(Filter filter) {
		try {
			return Result.value(contectInfoDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContectInfo>> getList() {
		try {
			return Result.value(contectInfoDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<ContectInfo>> getListByFilter(Filter filter) {
		try {
			return Result.value(contectInfoDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.ContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public List<Integer> contects() {
		Long userId = SaleActivator.getSessionUser().getId();
		List<ContectInfo> infos = contectInfoDao.getList();
		List<Integer> contects = new ArrayList<Integer>(14);
		Long now = new Date().getTime();
		int[] days = new int[14];
		for (ContectInfo info : infos) {
			boolean isTrue = userId.equals(info.getReceiverId());
			//未联系
			if(info.getReturnTime() != null){
				Long time = now - info.getReturnTime().getTime();
				time = time /(24*3600*1000);
				setTime(days,time,0, isTrue);
				if(time <= 7){
					days[6] += 1;//所有7天已联系
					if(isTrue){
						days[13] += 1;//我的7天已联系
					}
				}
			}
			//需联系
			if(info.getReturnVisit() != null){
				Long time = info.getReturnVisit().getTime()- now ;
				time = time /(24*3600*1000);
				setTime(days,time,3, isTrue);
			}
		}
		
		for (int i : days) {
			contects.add(i);
		}
		
		return contects;
	}
	
	private void setTime(int[] days,Long time,int start,boolean isTrue){
		if(time >= 7 ){
			days[start] += 1;//所有7天未联系
			if(isTrue){
				days[start+7] += 1;//我的7天未联系
			}
		}else if(time >= 15){
			days[start+1] += 1;//所有15天未联系
			if(isTrue){
				days[start+8] += 1;//我的15天未联系
			}
		}else if(time >= 30){
			days[start+2] += 1;//所有30天未联系
			if(isTrue){
				days[start+9] += 1;//我的30天未联系
			}
		}
	}

	@Override
	public Result changeState(Long id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = contectInfoDao.openSession();
			tr = session.beginTransaction();
			ContectInfo t = (ContectInfo) session.get(ContectInfo.class, id);
			if(t.getInfoStatus() == InfoEnum.NORMAL){
				t.setInfoStatus(InfoEnum.LOCKED);
			}else {
				t.setInfoStatus(InfoEnum.NORMAL);
			}
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			session.update(t);
			tr.commit();
			return Result.success("更新状态成功");
		}catch(UKConstraintException e){
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(), ContectInfo.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure("更新状态失败");
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
}

