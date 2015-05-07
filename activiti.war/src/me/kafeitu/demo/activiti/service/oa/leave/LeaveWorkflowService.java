package me.kafeitu.demo.activiti.service.oa.leave;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.kafeitu.demo.activiti.util.Page;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiiy.activiti.Activator;
import com.wiiy.activiti.entity.Leave;
import com.wiiy.activiti.service.LeaveService;

/**
 * 请假流程Service
 *
 * @author aswan
 */
public class LeaveWorkflowService {

    private static Logger logger = LoggerFactory.getLogger(LeaveWorkflowService.class);

    private LeaveService leaveService;

    private RuntimeService runtimeService;

    protected TaskService taskService;

    protected HistoryService historyService;

    protected RepositoryService repositoryService;


    private IdentityService identityService;

    /**
     * 启动流程
     *
     * @param entity
     */
    public ProcessInstance startWorkflow(Leave entity, Map<String, Object> variables) {
    	
    	leaveService.save(entity);
        logger.debug("save entity: {}", entity);
        String businessKey = entity.getId().toString();

        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(entity.getUserId());

            processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey, variables);
            String processInstanceId = processInstance.getId();
            entity.setProcessInstanceId(processInstanceId);
            leaveService.update(entity);
            logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[]{"leave", businessKey, processInstanceId, variables});
            //Activator.getOperationLogService().logOP("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[]{"leave", businessKey, processInstanceId, variables});
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processInstance;
    }

    /**
     * 查询待办任务
     *
     * @param userId 用户ID
     * @return
     */
    public List<Leave> findTodoTasks(String userId, Page<Leave> page, int[] pageParams) {
        List<Leave> results = new ArrayList<Leave>();
        List<Task> tasks = new ArrayList<Task>();

        // 根据当前人的ID查询
        TaskQuery todoQuery = taskService.createTaskQuery().processDefinitionKey("leave").taskAssignee(userId).active().orderByTaskId().desc()
                .orderByTaskCreateTime().desc();
        List<Task> todoList = todoQuery.listPage(pageParams[0], pageParams[1]);

        // 根据当前人未签收的任务
        TaskQuery claimQuery = taskService.createTaskQuery().processDefinitionKey("leave").taskCandidateUser(userId).active().orderByTaskId().desc()
                .orderByTaskCreateTime().desc();
        List<Task> unsignedTasks = claimQuery.listPage(pageParams[0], pageParams[1]);

        // 合并
        tasks.addAll(todoList);
        tasks.addAll(unsignedTasks);

        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }
            //Leave leave = leaveManager.getLeave(new Long(businessKey));
            Leave leave = leaveService.getBeanById(new Long(businessKey)).getValue();
            leave.setTask(task);
            leave.setProcessInstance(processInstance);
            leave.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            results.add(leave);
        }

        page.setTotalCount(todoQuery.count() + claimQuery.count());
        page.setResult(results);
        return results;
    }

    /**
     * 读取运行中的流程
     *
     * @return
     */
    public List<Leave> findRunningProcessInstaces(Page<Leave> page, int[] pageParams) {
        List<Leave> results = new ArrayList<Leave>();
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey("leave").active().orderByProcessInstanceId().desc();
        List<ProcessInstance> list = query.listPage(pageParams[0], pageParams[1]);

        // 关联业务实体
        for (ProcessInstance processInstance : list) {
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }
          //Leave leave = leaveManager.getLeave(new Long(businessKey));
            Leave leave = leaveService.getBeanById(new Long(businessKey)).getValue();
            leave.setProcessInstance(processInstance);
            leave.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            results.add(leave);

            // 设置当前任务信息
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskCreateTime().desc().listPage(0, 1);
            leave.setTask(tasks.get(0));
        }

        page.setTotalCount(query.count());
        page.setResult(results);
        return results;
    }

    /**
     * 读取已结束中的流程
     *
     * @return
     */
    public List<Leave> findFinishedProcessInstaces(Page<Leave> page, int[] pageParams) {
        List<Leave> results = new ArrayList<Leave>();
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("leave").finished().orderByProcessInstanceEndTime().desc();
        List<HistoricProcessInstance> list = query.listPage(pageParams[0], pageParams[1]);

        // 关联业务实体
        for (HistoricProcessInstance historicProcessInstance : list) {
            String businessKey = historicProcessInstance.getBusinessKey();
          //Leave leave = leaveManager.getLeave(new Long(businessKey));
            Leave leave = leaveService.getBeanById(new Long(businessKey)).getValue();
            leave.setProcessDefinition(getProcessDefinition(historicProcessInstance.getProcessDefinitionId()));
            List<HistoricTaskInstance> l=historyService.createHistoricTaskInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();
            
            HistoricTaskInstance t=l.get(1);
            List<HistoricVariableInstance> instances=historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list();
            
            for(HistoricVariableInstance i:instances){
            	System.out
						.println("LeaveWorkflowService.findFinishedProcessInstaces():"+i.getVariableName()+"="+i.getValue());
            }
            //System.out
			//		.println("LeaveWorkflowService.findFinishedProcessInstaces():"+instances);
            leave.setHistoricTaskInstanceList(l);
            leave.setHistoricProcessInstance(historicProcessInstance);
            results.add(leave);
        }
        page.setTotalCount(query.count());
        page.setResult(results);
        return results;
    }

    /**
     * 查询流程定义对象
     *
     * @param processDefinitionId 流程定义ID
     * @return
     */
    protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

   
    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

	public void setLeaveService(LeaveService leaveService) {
		this.leaveService = leaveService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

}
