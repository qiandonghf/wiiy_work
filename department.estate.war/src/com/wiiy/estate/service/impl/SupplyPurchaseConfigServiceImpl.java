package com.wiiy.estate.service.impl;


import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.entity.Supply;
import com.wiiy.estate.entity.SupplyApply;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyPurchaseConfig;
import com.wiiy.estate.entity.SupplyPurchaseForm;
import com.wiiy.estate.dao.SupplyPurchaseConfigDao;
import com.wiiy.estate.service.SupplyPurchaseConfigService;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.preferences.enums.CarApplyStatusEnum;
import com.wiiy.estate.activator.EstateActivator;

/**
 * @author my
 */
public class SupplyPurchaseConfigServiceImpl implements SupplyPurchaseConfigService{
	private SupplyPurchaseConfigService configService;
	private SupplyPurchaseConfigDao supplyPurchaseConfigDao;
	public void setSupplyPurchaseConfigDao(SupplyPurchaseConfigDao supplyPurchaseConfigDao) {
		this.supplyPurchaseConfigDao = supplyPurchaseConfigDao;
	}
	private void setCreatorModifier(BaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreatorId(EstateActivator.getSessionUser().getId());
		entity.setCreator(EstateActivator.getSessionUser().getRealName());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}
	@Override
	public Result<SupplyPurchaseConfig> save(SupplyPurchaseConfig t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			supplyPurchaseConfigDao.save(t);
			return Result.success(R.SupplyPurchaseConfig.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyPurchaseConfig.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseConfig.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseConfig> delete(SupplyPurchaseConfig t) {
		try {
			supplyPurchaseConfigDao.delete(t);
			return Result.success(R.SupplyPurchaseConfig.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseConfig.DELETE_FAILURE);
		}
	}

	public Result<SupplyPurchaseConfig> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = supplyPurchaseConfigDao.openSession();
			tr = session.beginTransaction();
			List<SupplyPurchaseConfig> supplyPurchaseConfigs = (List<SupplyPurchaseConfig>)session.createQuery("from SupplyPurchaseConfig where supplyPurchaseFormId = "+id).list();
			for (SupplyPurchaseConfig supplyConfig : supplyPurchaseConfigs) {
				Long purchaseId = supplyConfig.getSupplyPurchaseId();
				session.delete(supplyConfig);
				SupplyPurchase supplyPurchase = (SupplyPurchase)session.createQuery("from SupplyPurchase where id = "+purchaseId).list().get(0);
				session.delete(supplyPurchase);
			}
			SupplyPurchaseForm supplyPurchaseForm = (SupplyPurchaseForm)session.createQuery("from SupplyPurchaseForm where id = "+id).list().get(0);
			session.delete(supplyPurchaseForm);
			tr.commit();
			return Result.success(R.SupplyPurchase.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SupplyPurchase.DELETE_FAILURE);
		}finally{
			session.close();
		}
	}
	@Override
	public Result<SupplyPurchaseConfig> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null; 
		try {
			session = supplyPurchaseConfigDao.openSession();
			tr = session.beginTransaction();
			List<SupplyPurchaseConfig> supplyPurchaseConfigs = (List<SupplyPurchaseConfig>)session.createQuery("from SupplyPurchaseConfig where supplyPurchaseFormId in ("+ids+")").list();
			String idss="";
			for (SupplyPurchaseConfig supplyConfig : supplyPurchaseConfigs) {
				if(!("").equals(idss)){
					idss =idss+","+supplyConfig.getSupplyPurchaseId();
				}else{
					idss =idss+supplyConfig.getSupplyPurchaseId();
				}
			}
			session.createQuery("DELETE FROM SupplyPurchaseConfig WHERE supplyPurchaseFormId in("+ids+")").executeUpdate();
			session.createQuery("DELETE FROM SupplyPurchase WHERE id in("+idss+")").executeUpdate();
			session.createQuery("DELETE FROM SupplyStockIn WHERE supplyPurchaseFormId in("+ids+")").executeUpdate();
			List<SupplyPurchaseForm> t = session.createQuery("from SupplyPurchaseForm where id in ("+ids+")").list();
			for (SupplyPurchaseForm supplyPurchaseForm : t) {
				session.delete(supplyPurchaseForm);
			}
			tr.commit();
			return Result.success(R.SupplyPurchase.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SupplyPurchase.DELETE_FAILURE);
		}finally{
			session.close();
		}
	}

	@Override
	public Result<SupplyPurchaseConfig> update(SupplyPurchaseConfig t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			supplyPurchaseConfigDao.update(t);
			return Result.success(R.SupplyPurchaseConfig.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyPurchaseConfig.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseConfig.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseConfig> getBeanById(Serializable id) {
		try {
			return Result.value(supplyPurchaseConfigDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SupplyPurchaseConfig> getBeanByFilter(Filter filter) {
		try {
			return Result.value(supplyPurchaseConfigDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SupplyPurchaseConfig>> getList() {
		try {
			return Result.value(supplyPurchaseConfigDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SupplyPurchaseConfig>> getListByFilter(Filter filter) {
		try {
			return Result.value(supplyPurchaseConfigDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseConfig.LOAD_FAILURE);
		}
	}

	@Override
	public Result save(SupplyPurchaseForm supplyPurchaseForm,
			SupplyPurchase supplyPurchase,
			SupplyPurchaseConfig supplyPurchaseConfig,
			String[] supplyIds,String[] amounts, String[] prices, String[] usagess) {
		Session session=null;
		Transaction tr= null;
		try{
			session = supplyPurchaseConfigDao.openSession();
			tr = session.beginTransaction();
			for(int i=0;i<supplyIds.length;i++){
				SupplyPurchase supplyPurchaseObj=new SupplyPurchase();
				String supplyId=supplyIds[i];
				supplyPurchaseObj.setSupplyId((Long.valueOf(supplyId)));
				String amount=amounts[i];
				supplyPurchaseObj.setAmount((Double.valueOf(amount)));
				String price=prices[i];
				supplyPurchaseObj.setPrice((Double.valueOf(price)));
				String usages=usagess[i];
				supplyPurchaseObj.setUsages(usages);
				Double totalPrices=supplyPurchaseObj.getAmount()*supplyPurchaseObj.getPrice();
				supplyPurchaseObj.setTotalPrice(totalPrices);
				if(supplyPurchaseObj.getApplyTime()==null){
					supplyPurchaseObj.setApplyTime(null);
				}
				if(supplyPurchaseObj.getMemo()==null){
					supplyPurchaseObj.setMemo(null);
				}
				setCreatorModifier(supplyPurchaseObj);
				session.save(supplyPurchaseObj);
				SupplyPurchaseConfig supplyPurchaseConfigObj=new SupplyPurchaseConfig();
				if(supplyPurchaseObj.getId()!=null){
					supplyPurchaseConfigObj.setSupplyPurchaseId(supplyPurchaseObj.getId());
					supplyPurchaseConfigObj.setSupplyPurchase(supplyPurchaseObj);
				}
				if(supplyPurchaseForm.getRequisitionerId()!=null && supplyPurchaseForm.getApplyTime()!=null){
					session.save(supplyPurchaseForm);
					supplyPurchaseConfigObj.setSupplyPurchaseFormId(supplyPurchaseForm.getId());
					supplyPurchaseConfigObj.setSupplyPurchaseForm(supplyPurchaseForm);
					setCreatorModifier(supplyPurchaseForm);
				}
				setCreatorModifier(supplyPurchaseConfigObj);
				session.save(supplyPurchaseConfigObj);
			}
			tr.commit();
			return Result.success(R.SupplyPurchaseConfig.SAVE_SUCCESS,supplyPurchaseConfig);
		}catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyPurchaseConfig.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SupplyPurchaseConfig.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}
	@Override
	public Result update(List<SupplyPurchaseConfig> configList,
			List<SupplyPurchase> attList,SupplyPurchaseForm supplyForm) {
		
		Session session=null;
		Transaction tr= null;
		try{
			session = supplyPurchaseConfigDao.openSession();
			tr = session.beginTransaction();
			boolean flag=true;
			for(SupplyPurchaseConfig config : configList){
				SupplyPurchase supplyPurchase =config.getSupplyPurchase();
				SupplyPurchaseForm forms = config.getSupplyPurchaseForm();
				for(SupplyPurchase list : attList){
					if(supplyPurchase.getId().equals(list.getId())){
						supplyPurchase.setAmount(list.getAmount());
						supplyPurchase.setPrice(list.getPrice());
						supplyPurchase.setUsages(list.getUsages());
						session.update(supplyPurchase);
					}
				}
				if(flag){
					forms.setName(supplyForm.getName());
					forms.setRequisitionerId(supplyForm.getRequisitionerId());
					forms.setApplyTime(supplyForm.getApplyTime());
					session.update(forms);
					flag=false;
				}
			}
			
			tr.commit();
			return Result.success(R.SupplyPurchaseConfig.SAVE_SUCCESS);
		}catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyPurchase.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SupplyPurchase.SAVE_FAILURE);
		} finally {
			session.close();
		}
		
	}
	@Override
	public List<Object> getListBySql(String sql) {
		try {
			return supplyPurchaseConfigDao.getObjectListBySql(sql);
		} catch (Exception e) {
			return null;
		}
	}
}
