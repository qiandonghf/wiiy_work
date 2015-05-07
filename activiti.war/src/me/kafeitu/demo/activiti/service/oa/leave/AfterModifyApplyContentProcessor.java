package me.kafeitu.demo.activiti.service.oa.leave;

import java.util.Date;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;

import com.wiiy.activiti.entity.Leave;
import com.wiiy.activiti.service.LeaveService;

/**
 * 调整请假内容处理器
 *
 * @author aswan
 */

public class AfterModifyApplyContentProcessor implements TaskListener {

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

        leave.setLeaveType((String) delegateTask.getVariable("leaveType"));
        leave.setStartTime((Date) delegateTask.getVariable("startTime"));
        leave.setEndTime((Date) delegateTask.getVariable("endTime"));
        leave.setReason((String) delegateTask.getVariable("reason"));

        leaveService.save(leave);
    }

	public void setLeaveService(LeaveService leaveService) {
		this.leaveService = leaveService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

}
