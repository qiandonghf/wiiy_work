package com.wiiy.synthesis.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.external.service.SysEmailSenderPubService;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.CarApplyDao;
import com.wiiy.synthesis.entity.CarApply;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.preferences.enums.CarApplyStatusEnum;
import com.wiiy.synthesis.service.CarApplyService;

/**
 * @author my
 */
public class CarApplyServiceImpl implements CarApplyService{
	
	private CarApplyDao carApplyDao;
	
	public void setCarApplyDao(CarApplyDao carApplyDao) {
		this.carApplyDao = carApplyDao;
	}

	@Override
	public Result<CarApply> save(CarApply t) {
		try {
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			carApplyDao.save(t);
			return Result.success(R.CarApply.SAVE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CarApply.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.SAVE_FAILURE);
		}
	}

	@Override
	public Result<CarApply> delete(CarApply t) {
		try {
			carApplyDao.delete(t);
			return Result.success(R.CarApply.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CarApply> deleteById(Serializable id) {
		try {
			carApplyDao.deleteById(id);
			return Result.success(R.CarApply.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CarApply> deleteByIds(String ids) {
		try {
			carApplyDao.deleteByIds(ids);
			return Result.success(R.CarApply.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			e.printStackTrace();
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.DELETE_FAILURE);
		}
	}

	@Override
	public Result<CarApply> update(CarApply t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			carApplyDao.update(t);
			return Result.success(R.CarApply.UPDATE_SUCCESS);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CarApply.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CarApply> getBeanById(Serializable id) {
		try {
			return Result.value(carApplyDao.getBeanById(id));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.LOAD_FAILURE);
		}
	}

	@Override
	public Result<CarApply> getBeanByFilter(Filter filter) {
		try {
			return Result.value(carApplyDao.getBeanByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CarApply>> getList() {
		try {
			return Result.value(carApplyDao.getList());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CarApply>> getListByFilter(Filter filter) {
		try {
			return Result.value(carApplyDao.getListByFilter(filter));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.LOAD_FAILURE);
		}
	}
	@Override
	public Result<CarApply> approve(Long id, Long approverId, String approverl) {
		try {
			CarApply t = carApplyDao.getBeanById(id);
			t.setApproverId(approverId);
			t.setApprover(approverl);
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			carApplyDao.update(t);
			
			SysEmailSenderPubService sysEmailSenderPubService = SynthesisActivator.getService(SysEmailSenderPubService.class);
			User user = SynthesisActivator.getUserById(approverId);
			String content = SynthesisActivator.getAppConfig().getConfig("carApplyRemind").getParameter("email");
			content = content.replace("${approver}", user.getRealName());
			content = content.replace("${applicant}",SynthesisActivator.getSessionUser().getRealName());
			String path = ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+"/";
			String url = path+"parkmanager.oa/web/chiefadmin/Vehicle_applications_list.jsp";
			content = content.replace("${url}", url);
			String subject = "车辆审批提醒";
			if(sysEmailSenderPubService!=null) sysEmailSenderPubService.send(user.getEmail(), content,subject);
			SynthesisActivator.getOperationLogService().logOP("将id为【"+t.getId()+"】的车辆申请单提交审批");
			return Result.success(R.CarApply.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CarApply.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.UPDATE_FAILURE);
		}
	}

	@Override
	public Result<CarApply> approveCarApply(Long id, String applyCheck) {
		try {
			CarApply t = carApplyDao.getBeanById(id);
			if(applyCheck.equals("1")){
				t.setStatus(CarApplyStatusEnum.SAGREE);
			}else{
				t.setStatus(CarApplyStatusEnum.DISAGREE);
			}
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			carApplyDao.update(t);
			SysEmailSenderPubService sysEmailSenderPubService = SynthesisActivator.getService(SysEmailSenderPubService.class);
			User applyPerson = SynthesisActivator.getUserById(t.getCreatorId());
			String content = SynthesisActivator.getAppConfig().getConfig("carApprovalRemind").getParameter("email");
			content = content.replace("${applicant}",applyPerson.getRealName());
			String path = ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"+ServletActionContext.getRequest().getServerPort()+"/";
			String url = path+"parkmanager.oa/web/chiefadmin/Vehicle_applications_list.jsp";
			content = content.replace("${url}", url);
			String subject = "车辆审批完成提醒";
			if(sysEmailSenderPubService!=null) sysEmailSenderPubService.send(applyPerson.getEmail(), content,subject);
			SynthesisActivator.getOperationLogService().logOP("审批id为【"+t.getId()+"】的车辆申请单");
			return Result.success(R.CarApply.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			e.printStackTrace();
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),CarApply.class)));
		} catch (Exception e) {
			e.printStackTrace();
			return Result.failure(R.CarApply.UPDATE_FAILURE);
		}
	}

}
