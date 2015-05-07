package com.wiiy.business.service.impl;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.dao.InvestmentRepeatedVisteDao;
import com.wiiy.business.dto.ClueDto;
import com.wiiy.business.entity.InvestmentContectInfo;
import com.wiiy.business.entity.InvestmentRepeatedViste;
import com.wiiy.business.preferences.R;
import com.wiiy.business.service.InvestmentRepeatedVisteService;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;

/**
 * @author my
 */
public class InvestmentRepeatedVisteServiceImpl implements InvestmentRepeatedVisteService{
	
	private InvestmentRepeatedVisteDao investmentRepeatedVisteDao;
	
	public void setInvestmentRepeatedVisteDao(InvestmentRepeatedVisteDao investmentRepeatedVisteDao) {
		this.investmentRepeatedVisteDao = investmentRepeatedVisteDao;
	}

	@Override
	public Result<InvestmentRepeatedViste> save(InvestmentRepeatedViste t) {
		//returnTime:最后联系时间   returnVisite：回访提醒   
		//remindTime：下次回访提醒  createTime：创建时间
		//investmentRepeatedViste 的创建时间改变investmentContectInfo的最后联系时间
		//investmentRepeatedViste 的下次回访提醒改变investmentContectInfo的回访提醒
		Session session = null;
		Transaction tr = null;
		try {
			session = investmentRepeatedVisteDao.openSession();
			tr = session.beginTransaction();
			InvestmentContectInfo info = (InvestmentContectInfo) session.get(InvestmentContectInfo.class, t.getInvestmentContectInfoId());
			t.setCreateTime(new Date());
			t.setCreator(BusinessActivator.getSessionUser().getRealName());
			t.setCreatorId(BusinessActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			info.setReturnTime(t.getStartTime());
			info.setReturnVisit(t.getRemindTime());
			session.update(info);
			session.save(t);
			tr.commit();
			return Result.success(R.InvestmentRepeatedViste.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentRepeatedViste.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.SAVE_FAILURE);
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public Result<InvestmentRepeatedViste> delete(InvestmentRepeatedViste t) {
		try {
			investmentRepeatedVisteDao.delete(t);
			return Result.success(R.InvestmentRepeatedViste.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentRepeatedViste> deleteById(Serializable id) {
		try {
			investmentRepeatedVisteDao.deleteById(id);
			return Result.success(R.InvestmentRepeatedViste.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentRepeatedViste> deleteByIds(String ids) {
		try {
			investmentRepeatedVisteDao.deleteByIds(ids);
			return Result.success(R.InvestmentRepeatedViste.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(BusinessActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.DELETE_FAILURE);
		}
	}

	@Override
	public Result<InvestmentRepeatedViste> update(InvestmentRepeatedViste t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = investmentRepeatedVisteDao.openSession();
			tr = session.beginTransaction();
			InvestmentContectInfo info = (InvestmentContectInfo) session.get(InvestmentContectInfo.class, t.getInvestmentContectInfoId());
			t.setModifyTime(new Date());
			t.setModifier(BusinessActivator.getSessionUser().getRealName());
			t.setModifierId(BusinessActivator.getSessionUser().getId());
			info.setReturnTime(t.getStartTime());
			info.setReturnVisit(t.getRemindTime());
			session.update(t);
			session.update(info);
			tr.commit();
			return Result.success(R.InvestmentRepeatedViste.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),InvestmentRepeatedViste.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.UPDATE_FAILURE);
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public Result<InvestmentRepeatedViste> getBeanById(Serializable id) {
		try {
			return Result.value(investmentRepeatedVisteDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.LOAD_FAILURE);
		}
	}

	@Override
	public Result<InvestmentRepeatedViste> getBeanByFilter(Filter filter) {
		try {
			return Result.value(investmentRepeatedVisteDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentRepeatedViste>> getList() {
		try {
			return Result.value(investmentRepeatedVisteDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<InvestmentRepeatedViste>> getListByFilter(Filter filter) {
		try {
			return Result.value(investmentRepeatedVisteDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.InvestmentRepeatedViste.LOAD_FAILURE);
		}
	}

	@Override
	public Map<Long, ClueDto> getTimes() {
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Map<Long,ClueDto> map = new HashMap<Long, ClueDto>();
			List<Object> objects = (List<Object>)investmentRepeatedVisteDao.getObjectListBySql("select t.investmentContectInfo_id,count(*),max(t.create_time),t.memo from (select * from business_investment_repeated_viste ORDER BY create_time DESC ) t GROUP BY t.investmentContectInfo_id");
			if(objects!=null && objects.size()>0){
				for (Object object : objects) {
					Object[] obj = (Object[])object;
					if(obj[0]!=null){
						ClueDto dto = new ClueDto();
						dto.setTimes(obj[1].toString());
						dto.setLastDate(dateFormat.parse(obj[2].toString()));
						dto.setMemo(obj[3].toString());
						map.put(Long.valueOf(obj[0].toString()), dto);
					}
				}
			}
			return map;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
