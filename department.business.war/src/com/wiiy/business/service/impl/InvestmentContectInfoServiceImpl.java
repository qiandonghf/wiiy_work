package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.InvestmentContectInfoDao;
import com.wiiy.business.entity.InvestmentContectInfo;
import com.wiiy.business.preferences.R;
import com.wiiy.business.preferences.enums.ContectInfoEnum;
import com.wiiy.business.preferences.enums.LevelEnum;
import com.wiiy.business.service.InvestmentContectInfoService;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class InvestmentContectInfoServiceImpl implements InvestmentContectInfoService{
	
	private InvestmentContectInfoDao investmentContectInfoDao;
	
	public void setInvestmentContectInfoDao(InvestmentContectInfoDao investmentContectInfoDao) {
		this.investmentContectInfoDao = investmentContectInfoDao;
	}

	@Override
	public Result<InvestmentContectInfo> save(InvestmentContectInfo t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			t.setAssociate(BooleanEnum.NO);
			investmentContectInfoDao.save(t);
			return Result.success(R.InvestmentContectInfo.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentContectInfo.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.SAVE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContectInfo> delete(InvestmentContectInfo t) {
		try {
			investmentContectInfoDao.delete(t);
			return Result.success(R.InvestmentContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContectInfo> deleteById(Serializable id) {
		try {
			investmentContectInfoDao.deleteById(id);
			return Result.success(R.InvestmentContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContectInfo> deleteByIds(String ids) {
		try {
			investmentContectInfoDao.deleteByIds(ids);
			return Result.success(R.InvestmentContectInfo.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContectInfo> update(InvestmentContectInfo t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			investmentContectInfoDao.update(t);
			return Result.success(R.InvestmentContectInfo.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentContectInfo.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContectInfo> getBeanById(Serializable id) {
		try {
			return Result.value(investmentContectInfoDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<InvestmentContectInfo> getBeanByFilter(Filter filter) {
		try {
			return Result.value(investmentContectInfoDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentContectInfo>> getList() {
		try {
			return Result.value(investmentContectInfoDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentContectInfo>> getListByFilter(Filter filter) {
		try {
			return Result.value(investmentContectInfoDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentContectInfo.LOAD_FAILURE);
		}
	}
	
	
	@Override
	public Result changeState(Long id){
		Session session = null;
		Transaction tr = null;
		try {
			session = investmentContectInfoDao.openSession();
			tr = session.beginTransaction();
			InvestmentContectInfo t = (InvestmentContectInfo) session.get(InvestmentContectInfo.class, id);
			if(t.getContectInfoStatus() == ContectInfoEnum.NORMAL){
				t.setContectInfoStatus(ContectInfoEnum.LOCKED);
			}else {
				t.setContectInfoStatus(ContectInfoEnum.NORMAL);
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
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(), InvestmentContectInfo.class)));
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> contectInfos(Boolean type) {
		Session session = null;
		try{
			Long userId = BusinessActivator.getSessionUser().getId();
			session = investmentContectInfoDao.openSession();
			Map<String, Integer> infos = new HashMap<String, Integer>();
			String sql = "SELECT level,count(*) FROM business_investment_contect_info";
			//判断所有、我的
			if(type == true){
				sql += " WHERE receiver_id = "+userId;
			}
			sql += " GROUP BY level";
			List<Object> objects = session.createSQLQuery(sql).list();
			for (Object object : objects) {
				Object[] obj = (Object[])object;
				infos.put(obj[0].toString(), Integer.valueOf(obj[1].toString()));
			}
			return infos;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
		/*Session session = investmentContectInfoDao.openSession();
		List<Integer> infos = new ArrayList<Integer>();
		char c = 'A';
		for (int i = 0; i < 4; i++) {
			String sql = "SELECT count(*) FROM business_investment_contect_info WHERE level='"+((char)(c+i)+"")+"'";
			@SuppressWarnings("rawtypes")
			List list = session.createSQLQuery(sql).list();
			if (list == null) {
				infos.add(0);
			}else {
				infos.add(Integer.parseInt(list.get(0).toString()));
			}
			sql = "SELECT count(*) FROM business_investment_contect_info WHERE level='"+((char)(c+i)+"")+"' AND receiver_id="+id;
			list = session.createSQLQuery(sql).list();
			if (list == null) {
				infos.add(0);
			}else {
				infos.add(Integer.parseInt(list.get(0).toString()));
			}
		}*/

	/*
	@Override
	public List<Integer> contects() {
		Long userId = BusinessActivator.getSessionUser().getId();
		List<InvestmentContectInfo> infos = investmentContectInfoDao.getList();
		List<Integer> contects = new ArrayList<Integer>(14);
		Long now = new Date().getTime();
		int[] days = new int[14];
		for (InvestmentContectInfo info : infos) {
			boolean isTrue = userId.equals(info.getReceiverId());
			//未联系
			if(info.getReturnTime() != null){
				Long time = now - info.getReturnTime().getTime();
				time = time /(24*3600*1000);
				if (info.getContectInfoStatus() == ContectInfoEnum.NORMAL) {
					setTime(days,time,0, isTrue);
				}
				if(time <= 7){
					days[6] += 1;//所有7天已联系
					if(isTrue){
						days[13] += 1;//我的7天已联系
					}
				}
			}
			//需联系
			if(info.getReturnVisit() != null && //
					info.getContectInfoStatus() == ContectInfoEnum.NORMAL){
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
*/
	@Override
	public Integer getRowCount(Filter filter) {
		try{
			Integer iCount = investmentContectInfoDao.getRowCount(filter);
			if(null == iCount )
				return 0;
			return iCount;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	
	
}
