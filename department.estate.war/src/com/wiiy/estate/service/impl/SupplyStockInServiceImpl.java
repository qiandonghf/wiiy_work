package com.wiiy.estate.service.impl;

import java.io.Serializable;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.SupplyStockInDao;
import com.wiiy.estate.entity.Supply;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyPurchaseConfig;
import com.wiiy.estate.entity.SupplyPurchaseForm;
import com.wiiy.estate.entity.SupplyStockIn;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.service.SupplyPurchaseService;
import com.wiiy.estate.service.SupplyStockInService;

public class SupplyStockInServiceImpl implements SupplyStockInService {
	private List<SupplyStockIn> stockInList;
	private SupplyStockInDao supplyStockInDao;
	private SupplyPurchaseService supplyPurchaseService;
	
	public void setSupplyStockInDao(SupplyStockInDao supplyStockInDao) {
		this.supplyStockInDao = supplyStockInDao;
	}

	@Override
	public Result<SupplyStockIn> save(SupplyStockIn t) {
		Session session = null;
		Transaction tr = null;
		try{
			session = supplyStockInDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			if(t.getAmount()==null){
				t.setAmount(0D);
			}
			if(t.getPrice()==null){
				t.setPrice(0D);
			}
			t.setCost(t.getAmount()*t.getPrice());
			Supply supply = (Supply)session.get(Supply.class, t.getSupplyId());
			supply.setStock(supply.getStock()+t.getAmount());
			session.update(supply);
			session.save(t);
			EstateActivator.getOperationLogService().logOP("添加办公用品入库清单,id为【"+t.getId()+"】");
			tr.commit();
			return Result.success(R.SupplyStockIn.SAVE_SUCCESS,t);
		}catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.SupplyStockIn.SAVE_FAILURE);
		}finally{
			session.close();
		}
	}

	@Override
	public Result<SupplyStockIn> delete(SupplyStockIn t) {
		try{
			EstateActivator.getOperationLogService().logOP("删除办公用品入库清单,id为【"+t.getId()+"】");
			supplyStockInDao.delete(t);
			return Result.success(R.SupplyStockIn.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.DocFolder.DELETE_FAILURE);
		}
	}
	@Override
	public Result<SupplyStockIn> deleteById(Serializable id) {
		try {
			EstateActivator.getOperationLogService().logOP("删除办公用品入库清单,id为【"+id+"】");
			supplyStockInDao.deleteById(id);
			return Result.success(R.SupplyStockIn.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SupplyStockIn.DELETE_FAILURE);
		}
	}
	@Override
	public Result<SupplyStockIn> deleteByIds(String ids) {
		Session session = null;
		Transaction tr = null;
		try {
			session = supplyStockInDao.openSession();
			tr = session.beginTransaction();
			Filter filter = new Filter(SupplyStockIn.class);
			String[] str=ids.split(",");
			Long[] idnums=new Long[str.length];
			for(int i=0;i<str.length;i++){
				idnums[i]=Long.parseLong(str[i]);
			}
			List<SupplyStockIn> stockInList=supplyStockInDao.getListByFilter(filter.in("id", idnums));
			List<Supply> supplyList = (List<Supply>)session.createQuery("from Supply").list();
			for(SupplyStockIn stockIns : stockInList){
				if(stockIns.getSupplyPurchaseFormId()!=null){
				SupplyPurchaseForm supplyForm = (SupplyPurchaseForm)session.get(SupplyPurchaseForm.class, stockIns.getSupplyPurchaseFormId());
				List<SupplyPurchaseConfig> supplyPurchaseConfigs = (List<SupplyPurchaseConfig>)session.createQuery("from SupplyPurchaseConfig where supplyPurchaseFormId ="+supplyForm.getId()).list();			
				List<SupplyPurchase> purchaseList=supplyPurchaseService.getList().getValue();
					for(Supply s : supplyList){
						for(SupplyPurchase list : purchaseList){
							if(list.getSupplyId() == s.getId()){
								s.setStock(s.getStock()-list.getAmount());
								session.update(s);
								break;
						}
					}
				}
					session.delete(stockIns);
				}
				if(stockIns.getSupplyId()!=null){
					for(Supply lists: supplyList){
						if(lists.getId()==stockIns.getSupplyId()){
							lists.setStock(lists.getStock()-stockIns.getAmount());
							session.update(lists);
							session.delete(stockIns);
						}
					}	
				}
			}
			tr.commit();
			return Result.success(R.SupplyStockIn.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.SupplyStockIn.DELETE_FAILURE);
		}finally{
			session.close();
		}
	}

	@Override
	public Result<SupplyStockIn> update(SupplyStockIn t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			if(t.getAmount()==null){
				t.setAmount(0D);
			}
			if(t.getPrice()==null){
				t.setPrice(0D);
			}
			t.setCost(t.getAmount()*t.getPrice());
			supplyStockInDao.update(t);
			EstateActivator.getOperationLogService().logOP("编辑id为【"+t.getId()+"】的办公用品入库清单");
			return Result.success(R.SupplyStockIn.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyStockIn.class)));
		} catch (Exception e) {
			return Result.failure(R.SupplyStockIn.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SupplyStockIn> getBeanById(Serializable id) {
		try {
			return Result.value(supplyStockInDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.DocFolder.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SupplyStockIn> getBeanByFilter(Filter filter) {
		try {
			return Result.value(supplyStockInDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.DocFolder.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SupplyStockIn>> getList() {
		try {
			return Result.value(supplyStockInDao.getList());
		} catch (Exception e) {
			return Result.failure(R.DocFolder.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SupplyStockIn>> getListByFilter(Filter filter) { 
		try {
			return Result.value(supplyStockInDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.DocFolder.LOAD_FAILURE);
		}
	}

	@Override
	public Result updateSupply(SupplyStockIn t, Double count) {
		Session session = null;
		Transaction tr = null;
		try {
			session = supplyStockInDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			t.setCost(t.getAmount()*t.getPrice());
			session.update(t);
			EstateActivator.getOperationLogService().logOP("编辑id为【"+t.getId()+"】的办公用品入库清单");
			
			Supply supply = (Supply)session.get(Supply.class, t.getSupplyId());
			supply.setStock(supply.getStock()+t.getAmount()-count);
			session.update(supply);
			
			tr.commit();
			return Result.success(R.SupplyStockIn.UPDATE_SUCCESS,t);
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.SupplyStockIn.UPDATE_FAILURE);
		}finally{
			session.close();
		}
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
	public Result save(SupplyStockIn t, List<Supply> supplyList,List<SupplyPurchase> supplyPurchase) {
		Session session = null;
		Transaction tr = null;
		try{
			session = supplyStockInDao.openSession();
			tr = session.beginTransaction();
			setCreatorModifier(t);
		if(t.getSupplyPurchaseFormId()!=null){
			if(supplyPurchase!=null && !"".equals(supplyPurchase)){
				for(SupplyPurchase list : supplyPurchase){
					for(Supply supplys : supplyList){
						if(supplys.getId()==list.getSupplyId()){
							supplys.setStock(supplys.getStock()+list.getAmount());				
							t.setCost(list.getAmount()*list.getPrice());
							list.setApplyTime(t.getInTime());
							list.setPurchaser(t.getPurchaser());
							setCreatorModifier(t);
							session.update(list);
							session.update(supplys);
							session.save(t);
							break;
						}
					}
				}
			}
		}else{
			if(t.getAmount()==null){
				t.setAmount(0D);
			}
			if(t.getPrice()==null){
				t.setPrice(0D);
			}
			Supply supply = (Supply)session.get(Supply.class, t.getSupplyId());
			t.setCost(t.getAmount()*t.getPrice());
			supply.setStock(supply.getStock()+t.getAmount());
			session.update(supply);
			session.save(t);
		}
			EstateActivator.getOperationLogService().logOP("添加办公用品入库清单,id为【"+t.getId()+"】");
			tr.commit();
			return Result.success(R.SupplyStockIn.SAVE_SUCCESS,t);
		}catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.SupplyStockIn.SAVE_FAILURE);
		}finally{
			session.close();
		}
	}

	@Override
	public Result deletes(SupplyStockIn t,Supply s, List<SupplyPurchase> purchaseList,
			List<Supply> supplyList) {
		Session session = null;
		Transaction tr = null;
		try{
			session = supplyStockInDao.openSession();
			tr = session.beginTransaction();
			setCreatorModifier(t);
			if(t.getSupplyPurchaseFormId()!=null){
				for(Supply lists: supplyList){
					for(SupplyPurchase supplys : purchaseList){
						if(supplys.getSupplyId()==lists.getId()){
							lists.setStock(lists.getStock()-supplys.getAmount());	
							setCreatorModifier(t);
							session.update(lists);
							break;
						}
					}
				}
			}
			if(t.getSupplyId()!=null){
				for(Supply lists: supplyList){
					if(lists.getId()==s.getId()){
						lists.setStock(lists.getStock()-t.getAmount());
						session.update(lists);
					}
				}
			}
			session.delete(t);
			tr.commit();
			return Result.success(R.SupplyStockIn.DELETE_SUCCESS,t);
		}catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.SupplyStockIn.SAVE_FAILURE);
		}finally{
			session.close();
	}
	}

	public List<SupplyStockIn> getStockInList() {
		return stockInList;
	}

	public void setStockInList(List<SupplyStockIn> stockInList) {
		this.stockInList = stockInList;
	}

	public SupplyStockInDao getSupplyStockInDao() {
		return supplyStockInDao;
	}

	public void setSupplyPurchaseService(SupplyPurchaseService supplyPurchaseService) {
		this.supplyPurchaseService = supplyPurchaseService;
	}

	@Override
	public Result save(List<Supply> supplyList, String[] supplyIds,
			String[] amounts, String[] prices, String[] usages) {
		Session session = null;
		Transaction tr = null;
		session = supplyStockInDao.openSession();
		tr = session.beginTransaction();
		SupplyStockIn t=new SupplyStockIn();
		setCreatorModifier(t);
		try {
			for (int i = 0; i < supplyIds.length; i++) {
				for(Supply list : supplyList){
					if(Long.parseLong(supplyIds[i])==(list.getId())){
						t.setSupply(list);
						t.setAmount(Double.parseDouble(amounts[i]));
						t.setPrice(Double.parseDouble(prices[i]));
						list.setStock(list.getStock()+Double.parseDouble(amounts[i]));
						t.setSupplyId(list.getId());
						t.setSupplyPurchaseFormId(null);
						setCreatorModifier(t);
						session.save(t);
						session.update(list);
						break;
					}
				}
			}
			tr.commit();
			return Result.success(R.SupplyStockIn.SAVE_SUCCESS);
		}catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.SupplyStockIn.SAVE_FAILURE);
		}finally{
			session.close();
		}
	}
}
	
