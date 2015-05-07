package com.wiiy.sale.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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
import com.wiiy.sale.activator.SaleActivator;
import com.wiiy.sale.dao.SaleCustomerDao;
import com.wiiy.sale.entity.SaleCustomer;
import com.wiiy.sale.entity.SaleCustomerLabelRef;
import com.wiiy.sale.preferences.R;
import com.wiiy.sale.service.SaleCustomerService;

/**
 * @author my
 */
public class SaleCustomerServiceImpl implements SaleCustomerService{
	
	private SaleCustomerDao customerDao;
	
	public void setCustomerDao(SaleCustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	private SaleCustomer emtpy(SaleCustomer t){
		if (t.getSourceId() != null && "".equals(t.getSourceId())) {
			t.setSourceId(null);
		}
		if (t.getEducationId() != null && "".equals(t.getEducationId())) {
			t.setEducationId(null);
		}
		if (t.getProfessionId() != null && "".equals(t.getProfessionId())) {
			t.setProfessionId(null);
		}
		if (t.getFamilyIncomeId() != null && "".equals(t.getFamilyIncomeId())) {
			t.setFamilyIncomeId(null);
		}
		if (t.getClientAreaId() != null && "".equals(t.getClientAreaId())) {
			t.setClientAreaId(null);
		}
		if (t.getHuxingId() != null && "".equals(t.getHuxingId())) {
			t.setHuxingId(null);
		}
		if (t.getResistanceId() != null && "".equals(t.getResistanceId())) {
			t.setResistanceId(null);
		}
		if (t.getMotivationId() != null && "".equals(t.getMotivationId())) {
			t.setMotivationId(null);
		}
		return t;
	}
	private void setCreatorModifier(BaseEntity entity){
		entity.setCreateTime(new Date());
		entity.setCreatorId(SaleActivator.getSessionUser().getId());
		entity.setCreator(SaleActivator.getSessionUser().getRealName());
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
		entity.setEntityStatus(EntityStatus.NORMAL);
	}
	@Override
	public Result save(SaleCustomer t, String labelIds) {
		Session session = null;
		Transaction tr = null;
		try {
			session = customerDao.openSession();
			tr = session.beginTransaction();
			emtpy(t);
			setCreatorModifier(t);
			session.save(t);
			if(labelIds!=null && labelIds.length()>0){
				labelIds = labelIds.replace(" ", "");
				if(labelIds.startsWith(","))labelIds = labelIds.substring(1);
				String[] ids = labelIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					SaleCustomerLabelRef ref = new SaleCustomerLabelRef();
					ref.setCustomerId(t.getId());
					ref.setLabelId(Long.parseLong(ids[i].trim()));
					setCreatorModifier(ref);
					session.save(ref);
				}
			}
			/*Customer customer = new Customer();
			BeanUtil.copyProperties(t, customer);
			customer.setDepartment(DepartmentEnum.SALE);
			customer.setCustomerId(t.getId());
			customer.setId(null);
			session.save(customer);*/
			tr.commit();
			return Result.success(R.SaleCustomer.SAVE_SUCCESS,t);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomer.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}
	
	private void setUpdateModifier(BaseEntity entity){
		entity.setModifyTime(entity.getCreateTime());
		entity.setModifier(entity.getCreator());
		entity.setModifierId(entity.getCreatorId());
	}
	
	@Override
	public Result update(SaleCustomer t, String labelIds) {
		Session session = null;
		Transaction tr = null;
		try {
			session = customerDao.openSession();
			tr = session.beginTransaction();
			SaleCustomer dbSaleCustomer = (SaleCustomer)session.get(SaleCustomer.class, t.getId());
			BeanUtil.copyProperties(t,dbSaleCustomer);
			emtpy(dbSaleCustomer);
			setUpdateModifier(dbSaleCustomer);
			session.update(dbSaleCustomer);
			if(labelIds!=null && labelIds.length()>0){
				labelIds = labelIds.replace(" ", "");
				if(labelIds.startsWith(","))labelIds = labelIds.substring(1);
				String[] ids = labelIds.split(",");
				session.createQuery("delete from SaleCustomerLabelRef where customerId = "+dbSaleCustomer.getId()).executeUpdate();
				for (int i = 0; i < ids.length; i++) {
					SaleCustomerLabelRef ref = new SaleCustomerLabelRef();
					ref.setCustomerId(dbSaleCustomer.getId());
					ref.setLabelId(Long.parseLong(ids[i].trim()));
					setCreatorModifier(ref);
					session.save(ref);
				}
			}else{
				session.createQuery("delete from SaleCustomerLabelRef where customerId = "+dbSaleCustomer.getId()).executeUpdate();
			}
			/*Customer customer = (Customer) session.createQuery(//
					"From Customer WHERE department='SALE' and customerId ="+t.getId()).uniqueResult();
			Long id = customer.getId();
			BeanUtil.copyProperties(t, customer);
			customer.setId(id);
			customer.setDepartment(DepartmentEnum.SALE);
			session.update(customer);*/
			tr.commit();
			return Result.success(R.SaleCustomer.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomer.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<SaleCustomer> save(SaleCustomer t) {
		try {
			emtpy(t);
			t.setCreateTime(new Date());
			t.setCreator(SaleActivator.getSessionUser().getRealName());
			t.setCreatorId(SaleActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			customerDao.save(t);
			return Result.success(R.SaleCustomer.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomer.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.SAVE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomer> delete(SaleCustomer t) {
		try {
			customerDao.delete(t);
			return Result.success(R.SaleCustomer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomer> deleteById(Serializable id) {
		try {
			customerDao.deleteById(id);
			return Result.success(R.SaleCustomer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomer> deleteByIds(String ids) {
		try {
			customerDao.deleteByIds(ids);
			return Result.success(R.SaleCustomer.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SaleActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomer> update(SaleCustomer t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SaleActivator.getSessionUser().getRealName());
			t.setModifierId(SaleActivator.getSessionUser().getId());
			customerDao.update(t);
			return Result.success(R.SaleCustomer.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SaleCustomer.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomer> getBeanById(Serializable id) {
		try {
			return Result.value(customerDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SaleCustomer> getBeanByFilter(Filter filter) {
		try {
			return Result.value(customerDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleCustomer>> getList() {
		try {
			return Result.value(customerDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SaleCustomer>> getListByFilter(Filter filter) {
		try {
			return Result.value(customerDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.SaleCustomer.LOAD_FAILURE);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> levels(Boolean type){
		Long userId = SaleActivator.getSessionUser().getId();
		Map<String, Integer> levels = new HashMap<String, Integer>();
		Session session = null;
		String sql = "SELECT level,count(*) FROM sale_sale_customer";
		if(type == true){
			sql += " WHERE user_id = "+userId;
		}
		sql += " GROUP BY level";
		try{
			session = customerDao.openSession();
			//判断所有、我的
			List<Object> objects = session.createSQLQuery(sql).list();
			for (Object object : objects) {
				Object[] obj = (Object[])object;
				levels.put(obj[0].toString(), Integer.valueOf(obj[1].toString()));
			}
			return levels;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		} finally{
			if(session!=null){
				session.close();
			}
		}
		
	}
}
