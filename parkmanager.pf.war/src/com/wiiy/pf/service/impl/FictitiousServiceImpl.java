package com.wiiy.pf.service.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.IOUtil;
import com.wiiy.commons.util.MicrosoftWordUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dao.FictitiousDao;
import com.wiiy.pf.dto.InvestmentDto;
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.entity.Fictitious;
import com.wiiy.pf.preferences.R;
import com.wiiy.pf.service.FictitiousService;

/**
 * @author my
 */
public class FictitiousServiceImpl implements FictitiousService{
	
	private FictitiousDao fictitiousDao;
	
	public void setFictitiousDao(FictitiousDao fictitiousDao) {
		this.fictitiousDao = fictitiousDao;
	}

	@Override
	public Result<Fictitious> save(Fictitious t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(PfActivator.getSessionUser().getRealName());
			t.setCreatorId(PfActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			fictitiousDao.save(t);
			return Result.success(R.Fictitious.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Fictitious.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Fictitious.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Fictitious> delete(Fictitious t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = fictitiousDao.openSession();
			tr = session.beginTransaction();
			List<ContactBaseAtt> list = session.createQuery(
					"from ContactBaseAtt where contactId = " + t.getId()).list();
			for (ContactBaseAtt contactBaseAtt : list) {
				String path = contactBaseAtt.getPath();
				PfActivator.getResourcesService().delete(path);
				session.delete(contactBaseAtt);
			}
			session.delete(t);
			tr.commit();
			return Result.success(R.Fictitious.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Fictitious.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Fictitious> deleteById(Serializable id) {
		Session session = null;
		Transaction tr = null;
		try {
			session = fictitiousDao.openSession();
			tr = session.beginTransaction();
			Fictitious t = (Fictitious) session.get(Fictitious.class, id);
			List<ContactBaseAtt> list = session.createQuery(
							"from ContactBaseAtt where contactId = " + id).list();
			for (ContactBaseAtt contactBaseAtt : list) {
				String path = contactBaseAtt.getPath();
				PfActivator.getResourcesService().delete(path);
				session.delete(contactBaseAtt);
			}
			session.delete(t);
			tr.commit();
			return Result.success(R.Fictitious.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.Fictitious.DELETE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public Result<Fictitious> deleteByIds(String ids) {
		try {
			fictitiousDao.deleteByIds(ids);
			return Result.success(R.Fictitious.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(PfActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Fictitious.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Fictitious> update(Fictitious t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(PfActivator.getSessionUser().getRealName());
			t.setModifierId(PfActivator.getSessionUser().getId());
			fictitiousDao.update(t);
			return Result.success(R.Fictitious.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Fictitious.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Fictitious.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<Fictitious> getBeanById(Serializable id) {
		try {
			return Result.value(fictitiousDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Fictitious.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Fictitious> getBeanByFilter(Filter filter) {
		try {
			return Result.value(fictitiousDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Fictitious.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Fictitious>> getList() {
		try {
			return Result.value(fictitiousDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Fictitious.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Fictitious>> getListByFilter(Filter filter) {
		try {
			return Result.value(fictitiousDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.Fictitious.LOAD_FAILURE);
		}
	}

	@Override
	public Result getInvestment(Long id) {
		Session session = null;
		try{
			session = fictitiousDao.openSession();
			Fictitious fictitious = (Fictitious)session.get(Fictitious.class, id);
			List<Object> objects = session.createSQLQuery("select id,name,technic_id,host_id,host_name,import_id,import_name,account,business_scope,memo from crm_investment where id = "+fictitious.getInvestmentId()).list();
			List<Object> infos = session.createSQLQuery("select reg_type_id,reg_time,currency_type_id,document_type_id,reg_capital,organization_number,business_number,tax_number,legal_person,document_number,reg_mail,cellphone,reg_address,business_expire_date from crm_investment_reg_info where id = "+fictitious.getInvestmentId()).list();
			InvestmentDto dto = new InvestmentDto();
			if(objects.size()>0){
				int i = 0;
				for (Object object : objects) {
					Object[] obj = (Object[])object;
					dto.setId(Long.valueOf(obj[i++].toString()));
					dto.setName(obj[i++].toString());
					if(obj[i++]!=null){
						dto.setTechnicId(obj[i-1].toString());
					}
					if(obj[i++]!=null){
						dto.setHostId(Long.valueOf(obj[i-1].toString()));
					}
					if(obj[i++]!=null){
						dto.setHostName(obj[i-1].toString());
					}
					if(obj[i++]!=null){
						dto.setImportId(Long.valueOf(obj[i-1].toString()));
					}
					if(obj[i++]!=null){
						dto.setImportName(obj[i-1].toString());
					}
					if(obj[i++]!=null){
						dto.setAccount(obj[i-1].toString());
					}
					if(obj[i++]!=null){
						dto.setBusinessScope(obj[i-1].toString());
					}
					if(obj[i++]!=null){
						dto.setMemo(obj[i-1].toString());
					}
				}
				if(infos.size()>0){
					i=0;
					for (Object object : infos) {
						Object[] obj = (Object[])object;
						if(obj[i++]!=null){
							dto.setRegTypeId(obj[i-1].toString()) ;
						}
						if(obj[i++]!=null){
							dto.setRegTime((Date)obj[i-1]) ;
						}
						if(obj[i++]!=null){
							dto.setCurrencyTypeId(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setDocumentTypeId(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setRegCapital(BigDecimal.valueOf(Double.valueOf(obj[i-1].toString())));
						}
						if(obj[i++]!=null){
							dto.setOrganizationNumber(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setBusinessNumber(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setTaxNumber(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setLegalPerson(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setDocumentNumber(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setRegMail(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setCellphone(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setRegAddress(obj[i-1].toString());
						}
						if(obj[i++]!=null){
							dto.setBusinessExpireDate((Date)obj[i-1]);
						}
					}
				}
			}
			return Result.value(dto);
		}catch(Exception e){
			e.printStackTrace();
			return Result.failure("项目获取失败");
		}
	}

	@Override
	public Result save(Fictitious t, Map<String, String[]> roomRent,
			Map<String, String[]> billPlanRent, Map<String, String[]> deposit) {
		Session session = null;
		Transaction tr = null;
		try {
			session = fictitiousDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(PfActivator.getSessionUser().getRealName());
			t.setCreatorId(PfActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			session.save(t);
			/*Map<Long,SubjectRent> subjectMap = new HashMap<Long, SubjectRent>();
			if(roomRent.get("roomIds")!=null){
				for (int i = 0; i < roomRent.get("roomIds").length; i++) {
					SubjectRent rent = new SubjectRent();
					rent.setContractId(contract.getId());
					rent.setStartDate(DateUtil.parse(roomRent.get("startDate")[i]));
					rent.setEndDate(DateUtil.parse(roomRent.get("endDate")[i]));
					rent.setManagePrice(Double.valueOf(roomRent.get("managePrice")[i]));
					rent.setManagePriceUnit(PriceUnitEnum.valueOf(roomRent.get("managePriceUnit")[i]));
					rent.setRentPrice(Double.valueOf(roomRent.get("rentPrices")[i]));
					rent.setRentPriceUnit(PriceUnitEnum.valueOf(roomRent.get("rentPriceUnit")[i]));
					rent.setRoomArea(Double.valueOf(roomRent.get("roomAreas")[i]));
					rent.setRoomId(Long.valueOf(roomRent.get("roomIds")[i]));
					rent.setRoomName(roomRent.get("roomNames")[i]);
					if(!("").equals(roomRent.get("rebateRuleId")[i])){
						rent.setRebateRuleId(roomRent.get("rebateRuleId")[i]);
					}else{
						rent.setRebateRuleId(null);
					}
					if(roomRent.get("rebate")!=null && roomRent.get("rebate")[i]!=null){
						rent.setRebate(BooleanEnum.valueOf(roomRent.get("rebate")[i]));
					}
					if(roomRent.get("memo")!=null && roomRent.get("memo")[i]!=null){
						rent.setMemo(roomRent.get("memo")[i]);
					}
					setcreatemodify(rent);
					session.save(rent);
					subjectMap.put(rent.getRoomId(), rent);
				}
			}
			if(billPlanRent.get("feeTypes")!=null){
				for (int i = 0; i < billPlanRent.get("feeTypes").length; i++) {
					BillPlanRent plan = new BillPlanRent();
					plan.setContractId(contract.getId());
					plan.setFeeType(BusinessFeeEnum.valueOf(billPlanRent.get("feeTypes")[i]));
					plan.setPlanFee(Double.valueOf(billPlanRent.get("planFees")[i]));
					plan.setRealFee(Double.valueOf(billPlanRent.get("realFees")[i]));
					plan.setPlanPayDate(DateUtil.parse(billPlanRent.get("planPayDates")[i]));
					plan.setStartDate(DateUtil.parse(billPlanRent.get("planStartDates")[i]));
					plan.setEndDate(DateUtil.parse(billPlanRent.get("planEndDates")[i]));
					if(billPlanRent.get("planStatus")!=null && billPlanRent.get("planStatus")[i]!=null && billPlanRent.get("planStatus")[i]!=""){
						plan.setStatus(BillPlanStatusEnum.valueOf(billPlanRent.get("planStatus")[i]));
					}else{
						plan.setStatus(BillPlanStatusEnum.UNCHECK);
					}
					if(billPlanRent.get("planMemos")!=null && billPlanRent.get("planMemos")[i]!=null){
						plan.setMemo(billPlanRent.get("planMemos")[i]);
					}
					if(billPlanRent.get("planRoomIds") !=null && subjectMap.get(billPlanRent.get("planRoomIds")[i])!=null){
						plan.setSubjectId(subjectMap.get(billPlanRent.get("planRoomIds")[i]).getId());
					}
					if(billPlanRent.get("planRoomIds")!=null && !("").equals(billPlanRent.get("planRoomIds")[i])){
						plan.setRoomName(billPlanRent.get("planRoomNames")[i]);
						plan.setRoomId(Long.valueOf(billPlanRent.get("planRoomIds")[i]));
					}
					plan.setAutoCheck(BooleanEnum.NO);
					setcreatemodify(plan);
					session.save(plan);
				}
			}	
			
			if(deposit.get("depositTypes")!=null){
				for (int i = 0; i < deposit.get("depositTypes").length; i++) {
					Deposit d = new Deposit();
					d.setAmount(Double.valueOf(deposit.get("depositAmounts")[i]));
					d.setType(DepositTypeEnum.valueOf(deposit.get("depositTypes")[i]));
					if(deposit.get("depositMemos")!=null && !("").equals(deposit.get("depositMemos")[i]) ){
						d.setMemo(deposit.get("depositMemos")[i]);
					}
					d.setContractId(contract.getId());
					d.setCustomerId(contract.getCustomerId());
					d.setStatus(BillPlanStatusEnum.UNCHECK);
					setcreatemodify(d);
					session.save(d);
				}
			}*/
			tr.commit();
			return Result.success(R.Fictitious.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Fictitious.class)));
		} catch (Exception e) {
			e.printStackTrace();
			tr.rollback();
			return Result.failure(R.Fictitious.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}

	@Override
	public List<Object> getListBySql(String sql) {
		try{
			return fictitiousDao.getObjectListBySql(sql);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void print(Long id, String taskId, ByteArrayOutputStream out) {
		
	}
}
