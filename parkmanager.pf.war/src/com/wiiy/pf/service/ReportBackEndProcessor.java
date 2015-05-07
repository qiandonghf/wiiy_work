package com.wiiy.pf.service;

import java.util.Date;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;

import com.wiiy.pf.entity.Leave;


/**
 * 销假后处理器
 * <p>设置销假时间</p>
 * <p>使用Spring代理，可以注入Bean，管理事物</p>
 *
 * @author aswan
 */
public class ReportBackEndProcessor implements TaskListener {

    private static final long serialVersionUID = 1L;


    private LeaveService leaveService;


    private RuntimeService runtimeService;

    /* (non-Javadoc)
     * @see org.activiti.engine.delegate.TaskListener#notify(org.activiti.engine.delegate.DelegateTask)
     */
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Leave leave = leaveService.getBeanById(new Long(processInstance.getBusinessKey())).getValue();

        Object realityStartTime = delegateTask.getVariable("realityStartTime");
        leave.setRealityStartTime((Date) realityStartTime);
        Object realityEndTime = delegateTask.getVariable("realityEndTime");
        leave.setRealityEndTime((Date) realityEndTime); 

        leaveService.update(leave);
    }

	public void setLeaveService(LeaveService leaveService) {
		this.leaveService = leaveService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

}
