package com.wiiy.estate.service.impl;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.external.service.SysEmailSenderPubService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.estate.activator.EstateActivator;
import com.wiiy.estate.dao.SupplyApplyDao;
import com.wiiy.estate.entity.Supply;
import com.wiiy.estate.entity.SupplyApply;
import com.wiiy.estate.preferences.R;
import com.wiiy.estate.preferences.enums.CarApplyStatusEnum;
import com.wiiy.estate.service.SupplyApplyService;
import com.wiiy.estate.util.RemindUtil;

public class SupplyApplyServiceImpl implements SupplyApplyService{
	private SupplyApplyDao supplyApplyDao;
	@Override
	public Result<SupplyApply> save(SupplyApply t) {
		Session session = null;
		Transaction tr = null;
		try {
			session = supplyApplyDao.openSession();
			tr = session.beginTransaction();
			t.setCreateTime(new Date());
			t.setCreator(EstateActivator.getSessionUser().getRealName());
			t.setCreatorId(EstateActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			
			Supply supply = (Supply)session.get(Supply.class, t.getSupplyId());
			Double stockNum = supply.getStock();
			Double applyNum = t.getAmount();
			if(applyNum<=stockNum){
				supply.setStock(stockNum-applyNum);
				session.update(supply);
				session.save(t);
				tr.commit();
				return Result.success(R.SupplyApply.SAVE_SUCCESS, t);
			}
			return Result.failure("库存数不足");
		}catch (Exception e) {
			tr.rollback();
			e.printStackTrace();
			return Result.failure(R.SupplyApply.SAVE_FAILURE);
		} finally {
			session.close();
		}
	}


	@Override
	public Result<SupplyApply> delete(SupplyApply t) {
		try {
			EstateActivator.getOperationLogService().logOP("删除办公用品申请单,id为【"+t.getId()+"】");
			supplyApplyDao.delete(t);
			return Result.success(R.SupplyApply.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SupplyApply> deleteById(Serializable id) {
		try {
			EstateActivator.getOperationLogService().logOP("删除办公用品申请单,id为【"+id+"】");
			supplyApplyDao.deleteById(id);
			return Result.success(R.SupplyApply.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SupplyApply> deleteByIds(String ids) {
		try {
			for(String id : ids.split(",")){
				EstateActivator.getOperationLogService().logOP("删除办公用品申请单,id为【"+id+"】");
			}
			supplyApplyDao.deleteByIds(ids);
			return Result.success(R.SupplyApply.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(EstateActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.DELETE_FAILURE);
		}
	}

	@Override
	public Result<SupplyApply> update(SupplyApply t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			supplyApplyDao.update(t);
			EstateActivator.getOperationLogService().logOP("编辑id为【"+t.getId()+"】的办公用品申请单");
			return Result.success(R.SupplyApply.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyApply.class)));
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SupplyApply> getBeanById(Serializable id) {
		try {
			return Result.value(supplyApplyDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.LOAD_FAILURE);
		}
	}

	@Override
	public Result<SupplyApply> getBeanByFilter(Filter filter) {
		try {
			return Result.value(supplyApplyDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SupplyApply>> getList() {
		try {
			return Result.value(supplyApplyDao.getList());
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<SupplyApply>> getListByFilter(Filter filter) {
		try {
			return Result.value(supplyApplyDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.LOAD_FAILURE);
		}
	}

	public SupplyApplyDao getSupplyApplyDao() {
		return supplyApplyDao;
	}

	public void setSupplyApplyDao(SupplyApplyDao supplyApplyDao) {
		this.supplyApplyDao = supplyApplyDao;
	}

	@Override
	public Result<SupplyApply> approve(Long id, Long approverId,String approverl) {
		try {
			SupplyApply t = supplyApplyDao.getBeanById(id);
			t.setApproverId(approverId);
			t.setApprover(approverl);
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			supplyApplyDao.update(t);
			//发送邮件
			User user = EstateActivator.getUserById(approverId);
			SysEmailSenderPubService sysEmailSenderPubService = EstateActivator.getService(SysEmailSenderPubService.class);
			if(sysEmailSenderPubService!=null ){
				String content = "";
				StringBuffer data = RemindUtil.parseHTML("web/msgRemindModule/msgRemindModule.html");
				content = data.toString();
				String subject = "办公用品申请审批提醒";
				content = content.replace("${subject}", t.getSupply().getName());
				content = content.replace("${msgType}", "办公用品申请审批提醒");
				content = content.replace("${url}", RemindUtil.basePath()+"parkmanager.oa/supplyApply!view.action?id="+t.getId());
				content = content.replace("${receiver}",  user.getRealName());
				content = content.replace("${customerName}", user.getRealName());
				content = content.replace("${sender}",t.getApplyer());
				content = content.replace("${content}", t.getMemo());
				content = content.replace("${msgLink}", EstateActivator.getHttpSessionService().getRemindEmailLink());
				RemindUtil.sendMail( user.getRealName(), user.getEmail(),subject,content,sysEmailSenderPubService);
			}
			return Result.success(R.SupplyApply.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),SupplyApply.class)));
		} catch (Exception e) {
			return Result.failure(R.SupplyApply.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<SupplyApply> approveApply(SupplyApply t, String applyCheck) {
		Session session = null;
		Transaction tr = null;
		try {
			session = supplyApplyDao.openSession();
			tr = session.beginTransaction();
			if(applyCheck.equals("1")){
				t.setStatus(CarApplyStatusEnum.SAGREE);
			}else{
				t.setStatus(CarApplyStatusEnum.DISAGREE);
			}
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			session.update(t);
			
			/*//发送邮件提醒
			SysEmailSenderPubService sysEmailSenderPubService = EstateActivator.getService(SysEmailSenderPubService.class);
			User applyPerson = EstateActivator.getUserById(t.getCreatorId());
			String content = EstateActivator.getAppConfig().getConfig("supplyApprovalRemind").getParameter("email");
			content = content.replace("${applicant}",applyPerson.getRealName());
			String path = ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+"/";
			String url = path+"parkmanager.oa/web/chiefadmin/Vehicle_maintenance_list.jsp";
			content = content.replace("${url}", url);
			String subject = "办公用品审批完成提醒";
			if(sysEmailSenderPubService!=null) sysEmailSenderPubService.send(applyPerson.getEmail(), content,subject);
			EstateActivator.getOperationLogService().logOP("审批id为【"+t.getId()+"】的办公用品申请单");*/
			
			Supply supply = (Supply)session.get(Supply.class, t.getSupplyId());
			Double stockNum = supply.getStock();
			Double applyNum = t.getAmount();
			if(t.getStatus().equals(CarApplyStatusEnum.SAGREE) && applyNum<=stockNum){
				supply.setStock(stockNum-applyNum);
				session.update(supply);
				tr.commit();
				return Result.success(R.SupplyApply.UPDATE_SUCCESS);
			}else if(t.getStatus().equals(CarApplyStatusEnum.SAGREE) && applyNum>stockNum)
				tr.commit();
				return Result.success("库存数不足");
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.SupplyApply.UPDATE_FAILURE);
		} finally{
			session.close();
		}
	}	
	@Override
	public Result updateSupply(SupplyApply t, Double count) {
		Session session = null;
		Transaction tr = null;
		try {
			session = supplyApplyDao.openSession();
			tr = session.beginTransaction();
			t.setModifyTime(new Date());
			t.setModifier(EstateActivator.getSessionUser().getRealName());
			t.setModifierId(EstateActivator.getSessionUser().getId());
			session.update(t);
			EstateActivator.getOperationLogService().logOP("编辑id为【"+t.getId()+"】的办公用品申请单");
			
			Supply supply = (Supply)session.get(Supply.class, t.getSupplyId());
			supply.setStock(supply.getStock()-t.getAmount()+count);
			session.update(supply);
			
			tr.commit();
			return Result.success(R.SupplyApply.UPDATE_SUCCESS,t);
		} catch (Exception e) {
			tr.rollback();
			return Result.failure(R.SupplyApply.UPDATE_FAILURE);
		}finally{
			session.close();
		}
	}

}
