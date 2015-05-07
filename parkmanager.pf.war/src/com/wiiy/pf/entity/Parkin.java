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
 * Entity: Parkin入孵申请单
 *
 * @author aswan
 */
public class Parkin extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private String processInstanceId;
    private String startUserId;
    private String startUserName;
    private String projectName;
    private Long projectId;
    
    private Date applyTime;
    private String ywyOpinion;
    private Date startTime;
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

   
    
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
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

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getYwyOpinion() {
		return ywyOpinion;
	}

	public void setYwyOpinion(String ywyOpinion) {
		this.ywyOpinion = ywyOpinion;
	}

	public Date getStartTime() {
		return applyTime;
	}

	public void setStartTime(Date startTime) {
		this.applyTime = startTime;
	}

}
