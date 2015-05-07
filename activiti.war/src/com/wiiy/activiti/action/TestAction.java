package com.wiiy.activiti.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.struts2.ServletActionContext;

public class TestAction {
	private ProcessEngineImpl processEngine;
	private RepositoryService repositoryService ;
	private RuntimeService runtimeService;
	private TaskService taskService;
	
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public ProcessEngineImpl getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngineImpl processEngine) {
		this.processEngine = processEngine;
	}

	public String test1(){
		//DbSchemaCreate.main(null);
		//System.out.println("TestAction.test1():processEngine="+processEngine);
		
		try {
			
			//部署流程
			String s=ServletActionContext.getRequest().getRealPath("resource/VacationRequest.bpmn20.xml");
			System.out.println("TestAction.test1():s="+s);
			//repositoryService.createDeployment().addInputStream("VacationRequest.bpmn20.xml", new FileInputStream(s)).deploy();
			repositoryService.createDeployment().addClasspathResource("resource/VacationRequest.bpmn20.png").addClasspathResource("resource/VacationRequest.bpmn20.xml").deploy();
			//repositoryService.createDeployment().addClasspathResource("resource/VacationRequest.bpmn20.xml").
			//启动流程
			/**
			 * 为了让Activiti引擎知道这个流程，我们必须先进行“发布”。 发布意味着引擎会把BPMN 2.0 xml解析成可以执行的东西， “发布包”中的所有流程定义都会添加到数据库中。 这样，当引擎重启时，它依然可以获得“已发布”的流程
			 */
			System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
			
			
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("employeeName", "Kermit");
			variables.put("numberOfDays", new Integer(4));
			variables.put("vacationMotivation", "I'm really tired!");

			/**
			 * 把流程定义发布到Activiti引擎后，我们可以基于它发起新流程实例。 对每个流程定义，都可以有很多流程实例。 流程定义是“蓝图”，流程实例是它的一个运行的执行。 
			 */
			//RuntimeService runtimeService = processEngine.getRuntimeService();
			ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);
			
			//runtimeService.signal(processInstance.get)
			
			// Verify that we started a new process instance
			System.out.println("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());

			/**
			 * 查询用户任务
			 */
			
			Task t=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			List<Task> tasks1 = taskService.createTaskQuery()
			         .taskAssignee("Kermit")
			         .processVariableValueEquals("numberOfDays", 5)
			         .orderByDueDate().asc()
			         .list();
			for (Task task : tasks1) {
				System.out.println("查询结果1：Task available: task.id="+task.getId()+"," + task.getName());
			}
			
			List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
			for (Task task : tasks) {
				System.out.println("查询结果2：Task available: task.id="+task.getId()+"," + task.getName());
			}
			
			
			
			/**
			 * 为了让流程实例继续运行，我们需要完成这个任务。对Activiti来说，就是需要complete任务。 下面的代码展示了如何做这件事： 
			 */
			
			Task task = tasks.get(0);
			//viewNode(ServletActionContext.getResponse(),task.getId());

			Map<String, Object> taskVariables = new HashMap<String, Object>();
			taskVariables.put("vacationApproved", "false");
			taskVariables.put("managerMotivation", "We have a tight deadline!");
			//taskService.
			//taskService.complete(task.getId(), taskVariables);
			
			viewNode(ServletActionContext.getResponse(),t.getId());
			/**
			 * 流程实例会进入到下一个环节。在这里例子中， 下一环节允许员工通过表单调整原始的请假申请。员工可以重新提交请假申请， 这会使流程重新进入到第一个任务。 
			 */
			
			//processEngine.getp
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**  
	   * 根据任务ID获得任务实例  
	   *   
	   * @param taskId  
	   *            任务ID  
	   * @return  
	   * @throws Exception  
	*/    
	private  TaskEntity findTaskById(String taskId) throws Exception {    
	     TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(    
	              taskId).singleResult();    
	    if (task == null) {    
	         throw new Exception("任务实例未找到!");    
	       }    
	       return task;    
	}    

	/**  
	    * 根据任务ID获取对应的流程实例  
	    *   
	    * @param taskId  
	    *            任务ID  
	    * @return  
	    * @throws Exception  
	    */    

	public  ProcessInstance findProcessInstanceByTaskId(String taskId)    
	            throws Exception {    
	        // 找到流程实例    
	       ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(    
	                        findTaskById(taskId).getProcessInstanceId())    
	                .singleResult();    
	        if (processInstance == null) {     
	            throw new Exception("流程实例未找到!");    
	        }    
	        return processInstance;    
	}    

	public void viewNode(HttpServletResponse response,String taskId) throws IOException{
		try {
			/**流程实例**/
			//RuntimeService runtimeService = processEngine.getRuntimeService();

			
			ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
			BpmnModel bpmnModel = processEngine.getRepositoryService()
					.getBpmnModel(processInstance.getProcessDefinitionId());
			List<String> activeActivityIds =  runtimeService.getActiveActivityIds(processInstance.getId());
			//processEngine.
			//ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
			Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
			ProcessDefinition processDefinition=repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
			//String diagramResourceName = processDefinition.getDiagramResourceName();
			
			RepositoryServiceImpl imp = (RepositoryServiceImpl)repositoryService;  
			ProcessDefinitionEntity entity2 = (ProcessDefinitionEntity)imp.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());  
			//InputStream imageStream =  ProcessDiagramGenerator.generateDiagram(bpmnModel,"png",activeActivityIds);  
			
			
			
			//ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
			//		.processInstanceId(processInstanceId).singleResult();
			//String processDefinitionId = processInstance.getProcessDefinitionId();
	 
			//ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
			//		.getDeployedProcessDefinition(processInstance.getId());
			String diagramResourceName = entity2.getDiagramResourceName();
			String deploymentId = entity2.getDeploymentId();
			//InputStream imageStream = repositoryService.getResourceAsStream(deploymentId, diagramResourceName);
			//InputStream imageStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), diagramResourceName);
			  
			/**得到图片输出流**/
			InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
			/**得到图片输出流**/
			OutputStream out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = imageStream.read(b, 0, 1024)) != -1) {
			    out.write(b, 0, len);
			}
			imageStream.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
