package com.wiiy.pf.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import me.kafeitu.demo.activiti.service.activiti.WorkflowTraceService;
import me.kafeitu.demo.activiti.util.Variable;
import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Pager;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dto.ContactBaseDto;
import com.wiiy.pf.dto.HistoricVariableDto;
import com.wiiy.pf.dto.TypesDto;
import com.wiiy.pf.dto.UserTaskDto;
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.entity.ContactBaseLog;
import com.wiiy.pf.entity.Fictitious;
import com.wiiy.pf.entity.Leave;
import com.wiiy.pf.entity.Parkin;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.enums.ProcessTypeEnum;
import com.wiiy.pf.service.ContactBaseAttService;
import com.wiiy.pf.service.ContactBaseLogService;
import com.wiiy.pf.service.FictitiousService;
import com.wiiy.pf.service.LeaveService;
import com.wiiy.pf.service.ParkinService;
import com.wiiy.pf.service.ProcessTypeService;
import com.wiiy.pf.util.ContactLogUtil;

/**
 * 在我发起的流程中 节点当key为：modifyApply reportBack时会显示办理操作，否则只显示查看
 * @author aswan
 *
 */
public class ContactAction extends JqGridBaseAction<ContactBaseDto> {

	private RepositoryService repositoryService;
	private RuntimeService runtimeService;
	private WorkflowTraceService traceService;
	private LeaveService leaveService;
	private ParkinService parkinService;
	private ProcessTypeService processTypeService;
	private HistoryService historyService;
	private IdentityService identityService;
	private ContactBaseLogService contactBaseLogService;
	private ContactBaseAttService contactBaseAttService;
	private FictitiousService fictitiousService;
	private TaskService taskService;
	private String resourceType;
	private ProcessType processType;
	private ContactBaseDto baseDto;
	private String type;
	private Pager pager;
	private Integer rows = 0;
	private Integer page = 0;
	private Integer total = 0;
	private Integer records = 0;
	private String id;
	private String pid;// 流程实体Id
	private String taskId;
	public static String exportDir = "/resources/deployments";// 流程部署目录
	private String filePath;
	private String oldName;
	private String newName;
	@SuppressWarnings("rawtypes")
	private Result result;
	private List<Map<String, Object>> activityInfos;
	private List<UserTaskDto> userTaskInfos;
	private String rootPath;
	private boolean start;
	private List<TypesDto> processTypeList;
	private String actionName;
	
	private String tabId;
	
	private List<ProcessDefinition> definitionList;
	
	private List<ContactBaseAtt> contactAttList = new ArrayList<ContactBaseAtt>(); 
	
	public ContactAction() {
		rootPath = System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"upload/";
	}
	
	public String checkSuspended() {
		result = Result.failure("该流程已被挂起，请先激活此流程！");
		if (pid != null) {
			ProcessDefinition definition = repositoryService.getProcessDefinition(pid);
			if (!definition.isSuspended()) {
				result = Result.success("流程可以运行");
			}
		}
		return JSON;
	}
	
	
	/**
	 * 待处理流程列表
	 * @return
	 */
	public String taskList() {
		User user = PfActivator.getSessionUser();
		List<Task> tasks = new ArrayList<Task>();
		
		List<ContactBaseDto> dtos = new ArrayList<ContactBaseDto>();
		
		// 根据当前人的ID查询
		TaskQuery todoQuery = taskService.createTaskQuery().taskAssignee(user.getUsername()).orderByTaskId().desc().orderByTaskCreateTime().desc();
		List<Task> todoList = todoQuery.list();
		// 根据当前人未签收的任务
		TaskQuery claimQuery = taskService.createTaskQuery()
				.taskCandidateUser(user.getUsername()).orderByTaskId().desc().orderByTaskCreateTime().desc();
		List<Task> unsignedTasks = claimQuery.list();
		// 合并
		tasks.addAll(todoList);
		tasks.addAll(unsignedTasks);
		total = tasks.size();
		setPager(total);
		// 根据流程的业务ID查询实体并关联
		ContactBaseDto dto = new ContactBaseDto();
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			HistoricProcessInstance hiProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			baseDto=new ContactBaseDto();
			user = PfActivator.getUserByHql("FROM User where userName='"+hiProcessInstance.getStartUserId()+"'");		
			processType = processTypeService.getBeanByFilter(new Filter(ProcessType.class).
					eq("processDefId", hiProcessInstance.getProcessDefinitionId())).getValue();
			processType.setProcessDefinition(repositoryService.getProcessDefinition(processType.getProcessDefId()));
			baseDto.setProcessType(processType);
			baseDto.setUser(user);
			//baseDto.setProcessInstance(instance);
			baseDto.setHiProcessInstance(hiProcessInstance);
			baseDto.setTask(task);
			dtos.add(baseDto);
		}
		dto.setProcessTypes(getAllProcessTypes());
		dto.setDtos(dtos);
		result = Result.value(dto);
		return "taskList";
	}
	/**
	 * 待处理流程列表
	 * @return
	 */
	public String taskList1() {
		processTypeList=getAllProcessTypes();
		User user = PfActivator.getSessionUser();
		List<Task> tasks = new ArrayList<Task>();
		
		List<ContactBaseDto> dtos = new ArrayList<ContactBaseDto>();
		
		// 根据当前人的ID查询
		TaskQuery todoQuery = taskService.createTaskQuery().taskAssignee(user.getUsername()).orderByTaskId().desc().orderByTaskCreateTime().desc();
		List<Task> todoList = todoQuery.list();
		// 根据当前人未签收的任务
		TaskQuery claimQuery = taskService.createTaskQuery()
				.taskCandidateUser(user.getUsername()).orderByTaskId().desc().orderByTaskCreateTime().desc();
		List<Task> unsignedTasks = claimQuery.list();
		// 合并
		tasks.addAll(todoList);
		tasks.addAll(unsignedTasks);
		total = tasks.size();
		setPager(total);
		// 根据流程的业务ID查询实体并关联
		ContactBaseDto dto = new ContactBaseDto();
		for (Task task : tasks) {
			String processInstanceId = task.getProcessInstanceId();
			HistoricProcessInstance hiProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			baseDto=new ContactBaseDto();
			user = PfActivator.getUserByHql("FROM User where userName='"+hiProcessInstance.getStartUserId()+"'");		
			processType = processTypeService.getBeanByFilter(new Filter(ProcessType.class).
					eq("processDefId", hiProcessInstance.getProcessDefinitionId())).getValue();
			processType.setProcessDefinition(repositoryService.getProcessDefinition(processType.getProcessDefId()));
			baseDto.setProcessType(processType);
			baseDto.setUser(user);
			//baseDto.setProcessInstance(instance);
			baseDto.setHiProcessInstance(hiProcessInstance);
			baseDto.setTask(task);
			dtos.add(baseDto);
		}
		dto.setProcessTypes(getAllProcessTypes());
		dto.setDtos(dtos);
		result = Result.value(dto);
		return "taskList1";
	}
	/**
	 * 已处理流程列表
	 * @return
	 */
	public String hiTaskList() {
		processTypeList=getAllProcessTypes();
		User user = PfActivator.getSessionUser();
		List<ContactBaseDto> dtos = new ArrayList<ContactBaseDto>();
		List<HistoricTaskInstance> historicTaskInstances=historyService.createHistoricTaskInstanceQuery().taskAssignee(user.getUsername()).finished().list();
		
		if (historicTaskInstances.size() > 0) {
			Set<String> instanceIds=new HashSet<String>();
			for(HistoricTaskInstance historicTaskInstance:historicTaskInstances){
				instanceIds.add(historicTaskInstance.getProcessInstanceId());
			}
			total=(int)historyService.createHistoricProcessInstanceQuery().processInstanceIds(instanceIds).finished().count();
			setPager(total);
			List<HistoricProcessInstance> instances  = historyService.createHistoricProcessInstanceQuery().processInstanceIds(instanceIds).orderByProcessInstanceEndTime().desc().listPage(page, pager.getRows());
			for (HistoricProcessInstance instance : instances) {
				
				baseDto=new ContactBaseDto();
				baseDto.setUser(user);
				processType = processTypeService.getBeanByFilter(new Filter(ProcessType.class).
						eq("processDefId", instance.getProcessDefinitionId())).getValue();
				processType.setProcessDefinition(repositoryService.getProcessDefinition(processType.getProcessDefId()));
				baseDto.setProcessType(processType);
				
				System.out.println("ContactAction.launchList():processType.isNew()="+processType.isNew());
				//;
				baseDto.setHiProcessInstance(instance);
				ProcessInstance processInstance = 
						runtimeService.createProcessInstanceQuery().
						processInstanceId(instance.getId()).singleResult();
				Task task = taskService.
						createTaskQuery().processInstanceId(instance.getId()).singleResult();
				baseDto.setTask(task);
				baseDto.setProcessDefinition(findProcessDefinition(instance.getProcessDefinitionId()));
				//processInstance.g
				//baseDto.getProcessDefinition().getVersion()
				baseDto.setProcessInstance(processInstance);
				dtos.add(baseDto);
			}
			processTypeList=getAllProcessTypes();
		}
		result = Result.value(dtos);
		return "hiTaskList";
	}
	
	
	/**
	 * 我发起的流程
	 * @return
	 */
	public String launchList(){
		User user = PfActivator.getSessionUser();
		total = (int) historyService.
				createHistoricProcessInstanceQuery().startedBy(user.getUsername()).count();
		
		setPager(total);
		List<HistoricProcessInstance> instances  = historyService.
				createHistoricProcessInstanceQuery().
				startedBy(user.getUsername()).orderByProcessInstanceStartTime().desc().listPage(page, pager.getRows());
		
		List<ContactBaseDto> dtos = new ArrayList<ContactBaseDto>();
		for (HistoricProcessInstance instance : instances) {
			
			baseDto=new ContactBaseDto();
			baseDto.setUser(user);
			processType = processTypeService.getBeanByFilter(new Filter(ProcessType.class).
							eq("processDefId", instance.getProcessDefinitionId())).getValue();
			processType.setProcessDefinition(repositoryService.//
					getProcessDefinition(processType.getProcessDefId()));
			actionName=processType.getFormName().name().toLowerCase();
			System.out.println("ContactAction.launchList():actionName="+actionName);
			baseDto.setProcessType(processType);
			
			System.out.println("ContactAction.launchList():processType.isNew()="+processType.isNew());
			baseDto.setHiProcessInstance(instance);
			
			
			ProcessInstance processInstance = 
					runtimeService.createProcessInstanceQuery().
				processInstanceId(instance.getId()).singleResult();
			Task task = taskService.
					createTaskQuery().processInstanceId(instance.getId()).singleResult();
			baseDto.setTask(task);
			baseDto.setProcessDefinition(findProcessDefinition(instance.getProcessDefinitionId()));
			//processInstance.g
			//baseDto.getProcessDefinition().getVersion()
			baseDto.setProcessInstance(processInstance);
			dtos.add(baseDto);
		}
		
		processTypeList=getAllProcessTypes();
		result = Result.value(dtos);
		return "launchList";
	}
	
	public String deleteByFilePath() {
		if(filePath!=null){
			File file = new File(rootPath + filePath);
			if (file.exists()) {
				file.delete();
			}
			result = Result.success("文件删除成功",null);
			result.setMsg("文件删除成功");
		}
		return JSON;
	}
	
	/**
	 * 获取所有的流程与Form集合
	 * @return
	 */
	private List<TypesDto> getAllProcessTypes() {
		List<DataDict> list = PfActivator.getDataDictInitService().getDataDictByParentId("pf.0001");
		List<TypesDto> processTypesDtos = new ArrayList<TypesDto>(list.size());
		for (DataDict dict : list) {
			TypesDto typesDto = new TypesDto();
			typesDto.setTypeName(dict.getDataValue());
			Filter filter = new Filter(ProcessType.class);
			filter.eq("type", dict.getId());
			Object[] dIds = createDefinitionIds();
			if (dIds.length > 0) {
				filter.notIn("processDefId", dIds);
			}
			List<ProcessType> types = processTypeService.getListByFilter(filter).getValue();
			typesDto.setProcessTypes(types);
			processTypesDtos.add(typesDto);
		}
		/*List<ProcessType> list=processTypeService.getList().getValue();
		for(ProcessType type:list){
			type.setProcessDefinition(repositoryService.getProcessDefinition(type.getProcessDefId()));
		}*/
		return processTypesDtos;
	}
	
	private Object[] createDefinitionIds(){
		List<ProcessDefinition> definitions = repositoryService.//
				createProcessDefinitionQuery().suspended().list();
		Object[] list = new Object[definitions.size()];
		if (definitions != null && definitions.size() > 0) {
			for (int i = 0; i < list.length; i++) {
				list[i] = definitions.get(i).getId();
			}
		}
		return list;
	}
	
	/**
	 * 获取资源中的userTask
	 * @return
	 */
	public String findDefinitions(){
		
		System.out.println("+++++++++++++++++++++++++++++++++++++++++ContactAction.findDefinitions():pid="+pid);
		start = true;
		List<TaskDefinition> list = findTaskDefinitions(id);
		result = Result.value(list);
		findUsers(list);
		return JSON;
	}
	
	/**
	 * 签收申请单
	 * @return
	 */
	public String claim() {
		User user = PfActivator.getSessionUser();
		Task task = taskService.
				createTaskQuery().taskId(taskId).singleResult();
		processType = processTypeService.
				getBeanByFilter(
						new Filter(ProcessType.class).
						eq("processDefId", task.getProcessDefinitionId())).getValue();
		ProcessTypeEnum typeEnum = processType.getFormName();
		ContactBaseLog log = ContactLogUtil.
				saveLog(Long.parseLong(id), user,taskId, "签收"+typeEnum.getTitle());
		log.setContactType(typeEnum);
		taskService.claim(taskId, user.getUsername());
		contactBaseLogService.save(log);
		result = Result.success("签收任务成功");
		return JSON;
	}
	
	/**
	 * 查看明细
	 * @return
	 */
	public String detail() {
		if ("leave".equals(type)) {
			result = leaveService.getBeanById(Long.parseLong(id));
			return JSON;
		}
		return JSON;
	}
	
	public String loadLeaveWithVars() {
        Leave leave = leaveService.getBeanById(Long.parseLong(id)).getValue();
        Map<String, Object> variables = taskService.getVariables(taskId);
        leave.setVariables(variables);
        result = Result.value(leave);
        return "json";
    }
	
	/**
	 * 完成任务
	 * @return
	 */
	public String complete() {
		HttpServletRequest request=ServletActionContext.getRequest();
   	 	Variable var=new  Variable();
   	 	var.setKeys(request.getParameter("keys"));
   	 	var.setTypes(request.getParameter("types"));
   	 	var.setValues(request.getParameter("values"));
   	 	
   	 	//Map<String, Object> variables1 =(Map<String, Object> ) ServletActionContext.getRequest().getParameterMap();
   	 	
   	 	Map<String, Object> variables = var.getVariableMap();
   	 	
   	 	//variables.put("taskId", taskId);
   	 	taskService.setVariablesLocal(taskId, variables);
   	 	//taskService.setVariableLocal(arg0, arg1, arg2)
   	 	System.out.println("ContactAction.complete():taskId="+taskId+",variables="+variables);
       try {
           taskService.complete(taskId,variables);
           result = Result.success("任务完成");
       } catch (Exception e) {
    	   e.printStackTrace();
    	   result = Result.failure("操作失败");
       }
		return JSON;
	}
	
	/**
	 * 办理流程
	 * @return
	 */
	public String handle() {
		myView();
		return "handle";
	}
	
	/**
	 * 查看我的联系单<br/>
	 * 包含:<br/>
	 * 1.当前查看的对应的任务<br/>
	 * 2.当前查看的对应流程实体<br/>
	 * 3.当前查看的对应流定义<br/>
	 * @return
	 */
	public String myView() {
		
		//应该改成根据instanceId查看，根据taskId会出现没有及时刷新空指针问题，task=null
		start = false;
		//Task task = taskService.
		//		createTaskQuery().taskId(taskId).singleResult();
		
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(pid).singleResult();
		HistoricProcessInstance hiProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
		String businessKey = hiProcessInstance.getBusinessKey();
		String proDefId = hiProcessInstance.getProcessDefinitionId();
		
		
		//historyService.createHistoricDetailQuery().processInstanceId(arg0)
		
		if (proDefId != null && proDefId.trim().length() > 0) {
			if (businessKey.equals(id)) {
				List<ContactBaseLog> logs = contactBaseLogService.
						getListByFilter(new Filter(ContactBaseLog.class).
								eq("contactId", Long.parseLong(id))).getValue();
				baseDto=new ContactBaseDto();
				//baseDto.setUser(user);
				processType = processTypeService.getBeanByFilter(new Filter(ProcessType.class).
								eq("processDefId", proDefId)).getValue();
				//baseDto = createBaseDto(proDefId,Long.valueOf(businessKey));
				actionName=processType.getFormName().name().toLowerCase();
				System.out.println("ContactAction.myView():actionName="+actionName);
				baseDto.setProcessType(processType);
				baseDto.setLogs(logs);
				
				if(processInstance!=null)
				{
						Task task = taskService.createTaskQuery().processDefinitionId(pid).taskDefinitionKey(processInstance.getActivityId()).singleResult();
						baseDto.setTask(task);
				}
				baseDto.setHiProcessInstance(hiProcessInstance);
				//hiProcessInstance
				baseDto.setProcessInstance(processInstance);
				baseDto.setProcessDefinition(findProcessDefinition(proDefId));
				baseDto.setTaskDefinitions(findTaskDefinitions(proDefId));
				//findUsers(baseDto.getTaskDefinitions());
				baseDto.setUserTaskInfos(userTaskInfos);
				
				
				result = Result.value(baseDto);
			}
		}
		return "myView";
	}
	
	/**
	 * 读取部署文件中的userTask
	 * @param id <strong style="color:green;">（processDefinitionId:流程定义的id）</strong>
	 * @return
	 */
	private List<TaskDefinition> findTaskDefinitions(String id){
		List<TaskDefinition> definitions = new ArrayList<TaskDefinition>();
		List<ActivityImpl> activityImpls = ((ProcessDefinitionEntity) 
				((RepositoryServiceImpl)repositoryService).
				getDeployedProcessDefinition(id)).getActivities();
		for (ActivityImpl activityImpl : activityImpls) {
			ActivityBehavior behavior = activityImpl.getActivityBehavior();
			if (behavior instanceof UserTaskActivityBehavior) {
				TaskDefinition definition = ((UserTaskActivityBehavior) behavior).getTaskDefinition();
				definitions.add(definition);
			}
		}
		return definitions;
	}
	
	/**
	 * 获取用户信息
	 * @param definitions
	 */
	private void findUsers(List<TaskDefinition> definitions){
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		userTaskInfos = new ArrayList<UserTaskDto>();
		for (TaskDefinition definition : definitions) {
			/**
			 * 用户信息:group(组)信息
			 */
			UserTaskDto dto=new UserTaskDto();
			
			if(pid!=null){
				List<HistoricTaskInstance> historicTaskInstanceList=historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(definition.getKey()).processInstanceId(pid).list();
				List<HistoricVariableDto> historicVariableDtos=new ArrayList<HistoricVariableDto>();
				if(historicTaskInstanceList!=null){
					for(HistoricTaskInstance historicTaskInstance:historicTaskInstanceList){
						String assignee=historicTaskInstance.getAssignee();
						if(assignee!=null){
							org.activiti.engine.identity.User  user=identityService.createUserQuery().userId(assignee).singleResult();
							
							UserEntity ue=new UserEntity();
							if(user!=null){
								ue.setId(user.getId());
								ue.setFirstName(user.getFirstName());
								dto.setDealUser(ue);
							}
						}
						
						List<HistoricVariableInstance> historicVariableInstances=historyService.createHistoricVariableInstanceQuery().processInstanceId(pid).taskId(historicTaskInstance.getId()).list();
						
						for(HistoricVariableInstance historicVariableInstance:historicVariableInstances){
							
							//String taskId=historicVariableInstance.getTaskId();
							//HistoricTaskInstance historicTaskInstance2=historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
							
							
							
							
							HistoricVariableDto dto2=new HistoricVariableDto();
							dto2.setTime(sdf.format(historicVariableInstance.getTime()));
							dto2.setValue(String.valueOf(historicVariableInstance.getValue()));
							dto2.setVariableName(historicVariableInstance.getVariableName());
							dto2.setVariableTypeName(historicVariableInstance.getVariableTypeName());
							
							
							historicVariableDtos.add(dto2);
							System.out.println("========================================ContactAction.findUsers():"+definition.getKey()+","+definition.getNameExpression().getExpressionText()+":"+historicVariableInstance.getVariableName()+"="+historicVariableInstance.getVariableTypeName()+","+historicVariableInstance.getValue()+","+historicVariableInstance.getTime());
							
						}
						
					}
				}
				//Collections.sort(historicVariableDtos);
				dto.setHistoricVariableDtos(historicVariableDtos);
			}
			
			
			boolean display=true;
			int order=1;
			Expression desc_exps =definition.getDescriptionExpression();
			
			if(desc_exps!=null){
				JSONObject jsonObject=JSONObject.fromObject(desc_exps.getExpressionText());
				System.out.println("ContactAction.findUsers():"+desc_exps.getExpressionText()+"++++++++++++++"+jsonObject);
				
				if(jsonObject!=null&&jsonObject.containsKey("display"))
					display=jsonObject.getBoolean("display");
				
				if(jsonObject!=null&&jsonObject.containsKey("order"))
					order=jsonObject.getInt("order");
			}
			
			dto.setDisplay(display);
			dto.setOrder(order);
			Set<Expression> exps = definition.getCandidateGroupIdExpressions();
			if (exps!=null&&exps.size() > 0) {
				Iterator<Expression> it = exps.iterator();
				if (it.hasNext()) {
					try {
						String expText=it.next().getExpressionText();
						Group group=identityService.createGroupQuery().groupId(expText).singleResult();
						if(group==null){//如果流程中指定的组不存在
							GroupEntity newGroup=new GroupEntity();
							//newGroup.setId(expText);
							newGroup.setName(expText);
							//newGroup.setType("assignment");
							//identityService.saveGroup(newGroup);
							group=newGroup;
						}
						else{
							GroupEntity newGroup=new GroupEntity();
							newGroup.setId(group.getId());
							newGroup.setType(group.getType());
							newGroup.setName(group.getName());
							group=newGroup;
						}
						dto.addCandidateGroupList(group);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}
			
			exps = definition.getCandidateUserIdExpressions();
			if (exps!=null&&exps.size() > 0) {
				Iterator<Expression> it = exps.iterator();
				if (it.hasNext()) {
					try {
						String expText=it.next().getExpressionText();
						org.activiti.engine.identity.User user=identityService.createUserQuery().userId(expText).singleResult();
						if(user==null){//如果流程中指定的用户不存在
							UserEntity newUser=new UserEntity();
							newUser.setFirstName(expText);
							user=newUser;
						}
						else{
							UserEntity newUser=new UserEntity();
							newUser.setId(user.getId());
							newUser.setFirstName(user.getFirstName());
							user=newUser;
						}
						dto.addCandidateUserList(user);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}

			
			Expression assignee_exp=definition.getAssigneeExpression();
			

			if (assignee_exp!=null) {
				String assigneeId = assignee_exp.getExpressionText();
				if (assigneeId.indexOf("$") == -1) {
					try {
						org.activiti.engine.identity.User user = identityService
								.createUserQuery().userId(assigneeId)
								.singleResult();
						if (user == null) {// 如果流程中指定的用户不存在
							UserEntity newUser = new UserEntity();
							newUser.setFirstName(assigneeId);
							user = newUser;
						}
						else{
							UserEntity newUser = new UserEntity();
							newUser.setId(user.getId());
							newUser.setFirstName(user.getFirstName());
							user = newUser;
						}
						dto.setAssignee(user);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				} else {
					if (start) {
						org.activiti.engine.identity.User user;
						User session_user = PfActivator.getSessionUser();
						if (pid != null) {
							try {
								String userId = historyService
										.createHistoricProcessInstanceQuery()
										.processInstanceId(pid)
										.singleResult().getStartUserId();
								user = identityService
										.createUserQuery().userId(userId)
										.singleResult();
							} catch (Exception e) {
								e.printStackTrace();
								break;
							}
						}else {
							user = identityService
									.createUserQuery()
									.userId(session_user.getUsername())
									.singleResult();
						}
						if (user == null) {// 如果流程中指定的用户不存在
							UserEntity newUser = new UserEntity();
							newUser.setFirstName(session_user.getRealName());
							user = newUser;
						}
						else{
							UserEntity newUser = new UserEntity();
							newUser.setId(user.getId());
							newUser.setFirstName(user.getFirstName());
							user = newUser;
						}
						dto.setAssignee(user);
					} else {
						// 从流程历史记录中查找出创建流程的人
						try {
							String instanceId = taskService.createTaskQuery()
									.taskId(taskId).singleResult()
									.getProcessInstanceId();
							String userId = historyService
									.createHistoricProcessInstanceQuery()
									.processInstanceId(instanceId)
									.singleResult().getStartUserId();
							
							org.activiti.engine.identity.User user = identityService
									.createUserQuery().userId(userId)
									.singleResult();
							if (user == null) {// 如果流程中指定的用户不存在
								UserEntity newUser = new UserEntity();
								newUser.setFirstName(assigneeId);
								user = newUser;
							}
							else{
								UserEntity newUser = new UserEntity();
								newUser.setId(user.getId());
								newUser.setFirstName(user.getFirstName());
								user = newUser;
							}
							dto.setAssignee(user);
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
				}
			}
			userTaskInfos.add(dto);
		}
		
		Collections.sort(userTaskInfos);
		
	}
	
	
	/**
	 * 根据流程类别,构成实体
	 * @param proDefId
	 * @param id
	 * @return
	 */
	private ContactBaseDto createBaseDto(String proDefId,Long id){
		
		if(processType!=null){
			String type = processType.getFormName().toString().toLowerCase();
			if ("leave".equals(type)) {
				Leave leave = leaveService.getBeanById(id).getValue();
				contactAttList = contactBaseAttService.
						getListByFilter(new Filter(ContactBaseAtt.class).
								eq("contactId", id)).getValue();
				leave.setContactAtts(contactAttList);
				baseDto.setEntity(Result.value(leave));
			}
			if ("parkin".equals(type)) {
				Parkin parkin = parkinService.getBeanById(id).getValue();
				contactAttList = contactBaseAttService.
						getListByFilter(new Filter(ContactBaseAtt.class).
								eq("contactId", id)).getValue();
				parkin.setContactAtts(contactAttList);
				baseDto.setEntity(Result.value(parkin));
			}
			if ("fictitious".equals(type)) {
				Fictitious fictitious = fictitiousService.getBeanById(id).getValue();
				contactAttList = contactBaseAttService.
						getListByFilter(new Filter(ContactBaseAtt.class).
								eq("contactId", id)).getValue();
				fictitious.setContactAtts(contactAttList);
				baseDto.setEntity(Result.value(fictitious));
			}
		}
		return baseDto;
	}
	
	private void setPager(int total){
		if(page!=0){
			pager = new Pager(page,15);
			pager.setRecords(total);
		} else {
			pager = new Pager(1,15);
			pager.setRecords(total);
		}
	}

	/**
	 * 查询流程定义对象
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	protected ProcessDefinition findProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setTraceService(WorkflowTraceService traceService) {
		this.traceService = traceService;
	}

	public void setProcessTypeService(ProcessTypeService processTypeService) {
		this.processTypeService = processTypeService;
	}

	@Override
	protected List<ContactBaseDto> getListByFilter(Filter fitler) {
		return null;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Map<String, Object>> getActivityInfos() {
		return activityInfos;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setLeaveService(LeaveService leaveService) {
		this.leaveService = leaveService;
	}
	public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }
	
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public void setContactBaseLogService(ContactBaseLogService contactBaseLogService) {
		this.contactBaseLogService = contactBaseLogService;
	}
	
	public void setContactBaseAttService(ContactBaseAttService contactBaseAttService) {
		this.contactBaseAttService = contactBaseAttService;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}


	public List<UserTaskDto> getUserTaskInfos() {
		return userTaskInfos;
	}

	public List<TypesDto> getProcessTypeList() {
		return processTypeList;
	}

	public void setParkinService(ParkinService parkinService) {
		this.parkinService = parkinService;
	}

	public List<ProcessDefinition> getDefinitionList() {
		return definitionList;
	}

	public String getActionName() {
		return actionName;
	}
	public void setFictitiousService(FictitiousService fictitiousService) {
		this.fictitiousService = fictitiousService;
	}

	public List<ContactBaseAtt> getContactAttList() {
		return contactAttList;
	}

	public void setContactAttList(List<ContactBaseAtt> contactAttList) {
		this.contactAttList = contactAttList;
	}
	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}
}
