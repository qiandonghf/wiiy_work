package com.wiiy.pf.service.impl;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.engineering.entity.EngineeringBillPlanRent;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.pf.entity.Bill;
import com.wiiy.pf.dao.BillDao;
import com.wiiy.pf.dto.BillRentDto;
import com.wiiy.pf.dto.BillTaskDto;
import com.wiiy.pf.service.BillService;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.preferences.enums.DepartmentEnum;
import com.wiiy.pf.preferences.enums.PaymentTypeEnum;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.synthesis.entity.SynthesisBillPlanRent;

/**
 * @author my
 */
public class BillServiceImpl implements BillService{
	
	private BillDao billDao;
	
	public void setBillDao(BillDao billDao) {
		this.billDao = billDao;
	}

	@Override
	public Result<Bill> save(Bill t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(PfActivator.getSessionUser().getRealName());
			t.setCreatorId(PfActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			String sql = null; 
			String state = null;
			if (t.getPayment() == PaymentTypeEnum.PAYMENT) {
				state = "OUTCHECKED";
			}else {
				state = "INCHECKED";
			}
			if (t.getDepartment() == DepartmentEnum.ENGINEERING) {
				sql = "UPDATE engineering_engineering_bill_plan_rent SET";
			}
			sql += " status='"+state+"' , approval_status='APPROVAL'";
			sql += " WHERE id="+t.getBillPlanRentId();
			session.createSQLQuery(sql).executeUpdate();
			tr.commit();
			return Result.success(R.Bill.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Bill.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Bill.SAVE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public Result<Bill> delete(Bill t) {
		try {
			billDao.delete(t);
			return Result.success(R.Bill.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Bill> deleteById(Serializable id) {
		try {
			billDao.deleteById(id);
			return Result.success(R.Bill.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Bill> deleteByIds(String ids) {
		try {
			billDao.deleteByIds(ids);
			return Result.success(R.Bill.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Bill> update(Bill t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			billDao.update(t);
			return Result.success(R.Bill.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Bill.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Bill> getBeanById(Serializable id) {
		try {
			return Result.value(billDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Bill> getBeanByFilter(Filter filter) {
		try {
			return Result.value(billDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Bill>> getList() {
		try {
			return Result.value(billDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Bill>> getListByFilter(Filter filter) {
		try {
			return Result.value(billDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Bill.LOAD_FAILURE);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result getBeanById(Long id, BillRentDto dto) {
		Session session = billDao.openSession();
		Bill bill = (Bill) session.get(Bill.class, id);
		String ID_ = dto.getTaskId();
		String sql = "SELECT NAME_,VERSION_ From act_re_procdef WHERE ID_='"+ID_+"'";
		List list = session.createSQLQuery(sql).list();
		if (list != null && list.get(0) != null) {
			Object[] objects = (Object[]) list.get(0);
			String taskName = objects[0].toString()+"V"+objects[1].toString();
			dto.setTaskName(taskName);
		}
		sql = "SELECT SUM(settlement_fee) From act_bill WHERE contract_id="+bill.getContractId();
		sql += " AND department='"+bill.getDepartment()+"'";
		sql += " AND paid='YES'";
		list = session.createSQLQuery(sql).list();
		if (list != null && list.get(0) != null) {
			dto.setCompletedFee(Double.parseDouble(list.get(0).toString()));
		}else {
			dto.setCompletedFee(0d);
		}
		if (bill != null) {
			User user =(User) session.get(User.class, Long.parseLong(bill.getUserId()));
			bill.setUser(user);
			DepartmentEnum department = bill.getDepartment();
			if (department == DepartmentEnum.ENGINEERING) {
				EngineeringBillPlanRent rent = (EngineeringBillPlanRent) //
						session.get(EngineeringBillPlanRent.class, bill.getBillPlanRentId());
				createDto(rent, dto);
			}else if (department == DepartmentEnum.SYNTHESIS) {
				SynthesisBillPlanRent rent = (SynthesisBillPlanRent) //
						session.get(SynthesisBillPlanRent.class, bill.getBillPlanRentId());
				createDto(rent, dto);
			}
		}
		if (session != null) {
			session.close();
		}
		return Result.value(bill);
	}
	
	private void createDto(EngineeringBillPlanRent rent,BillRentDto dto){
		dto.setPlanId(rent.getId());
		dto.setCode(rent.getCode());
		dto.setPlanPayDate(rent.getPlanPayDate());
		dto.setPlanFee(rent.getPlanFee());
		dto.setContractId(rent.getContractId());
		dto.setContractName(rent.getContract().getName());
		dto.setContractAmount(rent.getContract().getContractAmount());
	}
	private void createDto(SynthesisBillPlanRent rent,BillRentDto dto){
		dto.setPlanId(rent.getId());
		dto.setCode(rent.getCode());
		dto.setPlanPayDate(rent.getPlanPayDate());
		dto.setPlanFee(rent.getPlanFee());
		dto.setContractId(rent.getContractId());
		dto.setContractName(rent.getContract().getName());
		dto.setContractAmount(rent.getContract().getContractAmount());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result update(Bill t, DepartmentEnum department) {
		Session session = null;
		Transaction tr = null;
		try {
			session = billDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			session.update(t);
			String departName = department.toString().toLowerCase();
			StringBuilder builder = new StringBuilder("UPDATE ");
			builder.append(departName);
			builder.append("_");
			builder.append(departName);
			builder.append("_bill_plan_rent SET approval_status='AGREEMENT' WHERE id=");
			builder.append(t.getBillPlanRentId());
			session.createSQLQuery(builder.toString()).executeUpdate();
			tr.commit();
			return Result.success(R.Bill.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Bill.class)));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Bill.UPDATE_FAILURE);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getDtoById(String key,DepartmentEnum department, BillRentDto dto) {
		Session session = billDao.openSession();
		Long id = dto.getPlanId();
		String sql = "SELECT SUM(settlement_fee) From act_bill WHERE contract_id="+dto.getContractId();
		sql += " AND department='"+department+"'";
		sql += " AND paid='YES'";
		List list = session.createSQLQuery(sql).list();
		if (list != null && list.get(0) != null) {
			dto.setCompletedFee(Double.parseDouble(list.get(0).toString()));
		}else {
			dto.setCompletedFee(0d);
		}
		sql = "SELECT ID_,NAME_,VERSION_ From act_re_procdef WHERE KEY_='"+key+"'";
		sql += " AND SUSPENSION_STATE_=1";
		list = session.createSQLQuery(sql).list();
		List<BillTaskDto> taskDtos = new ArrayList<BillTaskDto>();
		for (int i = 0; i < list.size(); i++) {
			Object[] objects = (Object[]) list.get(i);
			BillTaskDto taskDto = new BillTaskDto();
			taskDto.setTaskId(objects[0].toString());
			taskDto.setTaskName(objects[1].toString()+"V"+objects[2].toString());
			taskDtos.add(taskDto);
		}
		dto.setTaskDtos(taskDtos);
		if (department == DepartmentEnum.ENGINEERING) {
			createDto((EngineeringBillPlanRent)session.get(//
					EngineeringBillPlanRent.class, id), dto);
		}else {
			createDto((EngineeringBillPlanRent)session.get(//
					EngineeringBillPlanRent.class, id), dto);
		}
		if (session != null) {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result viewById(Long id,BillRentDto dto) {
		Session session = billDao.openSession();
		Bill bill = (Bill) session.get(Bill.class, id);
		String departName = bill.getDepartment().toString();
		if (dto.getTaskId() == null) {
			
		}
		StringBuilder builder = new StringBuilder( //
				"SELECT r.code,r.plan_fee,r.plan_pay_date,c.name,c.moneyamount FROM ");
		builder.append(departName+"_"+departName);
		builder.append("_bill_plan_rent r JOIN");
		builder.append(departName+"_"+departName);
		builder.append("_contract c");
		builder.append(" ON r.contract_id = c.id WHERE r.id = ");
		builder.append(bill.getBillPlanRentId());
		List list = session.createSQLQuery(builder.toString()).list();
		if (list != null && list.size() > 0) {
			if (list.get(0) != null) {
				Object[] objects = (Object[]) list.get(0);
				dto.setCode(objects[0].toString());
				dto.setPlanFee(Double.parseDouble(objects[1].toString()));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					dto.setPlanPayDate(sdf.parse(objects[2].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dto.setContractName(objects[3].toString());
				dto.setContractAmount(Double.parseDouble(objects[4].toString()));
			}
			
		}
		if (session != null) {
			session.close();
		}
		return Result.value(bill);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Result getListBySql(String sql) {
		Session session = billDao.openSession();
		return Result.value(session.createSQLQuery(sql).list());
	}
}
