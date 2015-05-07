package com.wiiy.pf.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.format.annotation.DateTimeFormat;

import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.pf.preferences.enums.LeaveTypeEnum;

/**
 * Entity: Leave
 *
 * @author aswan
 */
public class Leave extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String processInstanceId;
    private String userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date realityStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date realityEndTime;
    private Date applyTime;
    private LeaveTypeEnum leaveType;
    private String reason;
    
    private List<HistoricTaskInstance> historicTaskInstanceList;

    //-- 临时属性 --//

    // 流程任务
    private Task task;

    private Map<String, Object> variables;

    // 运行中的流程实例
    private ProcessInstance processInstance;

    // 历史的流程实例
    private HistoricProcessInstance historicProcessInstance;

    // 流程定义
    private ProcessDefinition processDefinition;
    
    //流程定义与实体关联
    private ProcessType processType;
    
    //附件
    private List<ContactBaseAtt> contactAtts;

    @Column
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_TIME")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_TIME")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Column
    public LeaveTypeEnum getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeEnum leaveType) {
        this.leaveType = leaveType;
    }

    @Column
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REALITY_START_TIME")
    public Date getRealityStartTime() {
        return realityStartTime;
    }

    public void setRealityStartTime(Date realityStartTime) {
        this.realityStartTime = realityStartTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REALITY_END_TIME")
    public Date getRealityEndTime() {
        return realityEndTime;
    }

    public void setRealityEndTime(Date realityEndTime) {
        this.realityEndTime = realityEndTime;
    }

    @Transient
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Transient
    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    @Transient
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    @Transient
    public HistoricProcessInstance getHistoricProcessInstance() {
        return historicProcessInstance;
    }

    public void setHistoricProcessInstance(HistoricProcessInstance historicProcessInstance) {
        this.historicProcessInstance = historicProcessInstance;
    }

    @Transient
    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }
    
    @Transient
	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public List<HistoricTaskInstance> getHistoricTaskInstanceList() {
		return historicTaskInstanceList;
	}

	@Transient
	public List<ContactBaseAtt> getContactAtts() {
		return contactAtts;
	}
	
	public void setContactAtts(List<ContactBaseAtt> contactAtts) {
		this.contactAtts = contactAtts;
	}

	public void setHistoricTaskInstanceList(
			List<HistoricTaskInstance> historicTaskInstanceList) {
		this.historicTaskInstanceList = historicTaskInstanceList;
	}

}
