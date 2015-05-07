package com.wiiy.pf.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import me.kafeitu.demo.activiti.util.Variable;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.core.entity.User;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dto.ContactBaseDto;
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.entity.ContactForm;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.enums.ProcessTypeEnum;
import com.wiiy.pf.service.ContactBaseAttService;
import com.wiiy.pf.service.ContactFormService;
import com.wiiy.pf.service.ProcessTypeService;
import com.wiiy.pf.util.ContactLogUtil;

/**
 * @author my
 */
public class ContactFormAction extends JqGridBaseAction<ContactForm>{
	
	private ContactFormService contactFormService;
	private RuntimeService runtimeService;
	private IdentityService identityService;
	private RepositoryService repositoryService;
	private HistoryService historyService;
	private TaskService taskService;
	private ContactBaseAttService contactBaseAttService;
	private ProcessTypeService processTypeService;
	private Result result;
	private ContactForm contactForm;
	private Long id;
	private String ids;
	private String taskId;
	
	private String oldName;
	private String newName;
	private String rootPath;
	private String filePath;
	private String pid;
	
	private boolean start;
	private ContactBaseDto baseDto;
	private ProcessType processType;
	private String actionName;
	private boolean sendEmail = false;
	
	private String type;
	
	private List<ContactBaseAtt> contactAttList = new ArrayList<ContactBaseAtt>();
	
	public ContactFormAction() {
		rootPath = System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"upload/";
	}
	
	public String add(){
		return "add";
	}
	
	public String attList(){
		contactAttList = contactBaseAttService.getListByFilter(//
				new Filter(ContactBaseAtt.class).eq("contactId", id)).getValue();
		return "attList";
	}
	
	public String save(){
		User user = PfActivator.getSessionUser();
		contactForm.setUserId(user.getId().toString());
		contactForm.setApplyTime(new Date());
		result = contactFormService.save(contactForm);
		Map<String, Object> variables = new HashMap<String, Object>();
		String businessKey = contactForm.getId().toString();
		identityService.setAuthenticatedUserId(user.getUsername());
		ProcessInstance processInstance = runtimeService.
				startProcessInstanceByKey(type, businessKey, variables);
		String instanceId = processInstance.getId();
		
		taskId = taskService.createTaskQuery().
				processInstanceId(instanceId).singleResult().getId();
		
		//taskService.setVariablesLocal(taskId, variables);
		
		contactForm.setProcessInstanceId(instanceId);
		contactFormService.update(contactForm).getValue();
		contactForm = contactFormService.getBeanByFilter(
				new Filter(ContactForm.class).
				eq("processInstanceId", instanceId)).getValue();
		List<ContactBaseAtt> atts = getListFromJSON(filePath);
		for (ContactBaseAtt att : atts) {
			att.setContactId(contactForm.getId());
			att.setProcessInstanceId(instanceId);
			contactBaseAttService.save(att);
		}
		identityService.setAuthenticatedUserId(null);
		ContactLogUtil.saveLog(contactForm.getId(), ProcessTypeEnum.BILL, user, taskId);
		return JSON;
	}
	
	public String handle() {
		start = false;
		Task task = taskService.
				createTaskQuery().taskId(taskId).singleResult();
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).active()
				.singleResult();
		String businessKey = processInstance.getBusinessKey();
		String proDefId = processInstance.getProcessDefinitionId();
		if (proDefId != null && proDefId.trim().length() > 0) {
			if (businessKey.equals(String.valueOf(id))) {
				
				baseDto=new ContactBaseDto();
				//List<ContactBaseLog> logs = contactBaseLogService.
				//		getListByFilter(new Filter(ContactBaseLog.class).
				//				eq("contactId", Long.parseLong(id))).getValue();
				//baseDto.setLogs(logs);
				processType = processTypeService.getBeanByFilter(new Filter(ProcessType.class).
								eq("processDefId", proDefId)).getValue();
				actionName=processType.getFormName().name().toLowerCase();
				System.out.println("ContactAction.myView():actionName="+actionName);
				baseDto.setProcessType(processType);
				
				baseDto.setTask(task);
				baseDto.setProcessInstance(processInstance);
				HistoricProcessInstance hiProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
				baseDto.setHiProcessInstance(hiProcessInstance);
				ProcessDefinition processDefinition = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionId(proDefId).singleResult();
				baseDto.setProcessDefinition(processDefinition);
				baseDto.setTaskDefinitions(findTaskDefinitions(proDefId));
				result = Result.value(baseDto);
			}
		}
		return "handle";
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
	 * 解析从js中发回的JSON格式的数据
	 * @param json
	 * @return
	 */
	private List<ContactBaseAtt> getListFromJSON(Object json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<ContactBaseAtt> att = new ArrayList<ContactBaseAtt>();
		if (jsonArray.size() > 0) {
			for (int i = 0; i <jsonArray.size(); i++) {
				JSONObject o = jsonArray.getJSONObject(i);
				String oldName = o.getString("fileName");
				try {
					oldName = URLDecoder.decode(oldName, "utf-8");
					oldName = URLDecoder.decode(oldName, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				ContactBaseAtt a = new ContactBaseAtt();
				a.setOldName(oldName);
				a.setNewName(o.getString("filePath"));
				a.setPath(o.getString("filePath"));
				a.setType(o.getString("type"));
				att.add(a);
			}
		}
		return att;
	}
	
	public String view(){
		result = contactFormService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = contactFormService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		HttpServletRequest request=ServletActionContext.getRequest();
   	 	Variable var=new  Variable();
   	 	var.setKeys(request.getParameter("keys"));
   	 	var.setTypes(request.getParameter("types"));
   	 	var.setValues(request.getParameter("values"));
   	 	
   	 	Map<String, Object> variables = var.getVariableMap();
   	 	
   	 	taskService.setVariablesLocal(taskId, variables);
   	 	System.out.println("ContactAction.complete():taskId="+taskId+",variables="+variables);
       try {
    	   	String businessKey = contactForm.getId().toString();
    	   	ProcessInstance	processInstance = runtimeService.createProcessInstanceQuery().//
    	   			processInstanceBusinessKey(businessKey).singleResult();
    	   	String instanceId = processInstance.getId();
    	   	taskService.complete(taskId,variables);
    	   	if(filePath!=null && filePath.length()>0){
	   			List<ContactBaseAtt> atts = getListFromJSON(filePath);
	   			for (ContactBaseAtt att : atts) {
	   				att.setContactId(contactForm.getId());
	   				att.setProcessInstanceId(instanceId);
	   				contactBaseAttService.save(att);
	   			}
	   		}
    	   	
    		boolean cwcn = variables.containsKey("cwcnBackReason");
           	if (cwcn) {
           		ContactForm dbBean = contactFormService.getBeanById(contactForm.getId()).getValue();
           		contactFormService.update(dbBean);
			}
           	//发送短信
           	if(sendEmail){
           		try{
               		List<User> users = nextTaskDefinition(request.getParameter("pid").toString());
               		SMSPubService smsPubService = PfActivator.getService(SMSPubService.class);
               		String[] receiverMobiles = new String[]{};
               		String[] receiverNames = new String[]{};
               		String[] contents = new String[]{};
               		if(users!=null && users.size()>0){
               			int i=0,j=0,k=0;
               			for (User user : users) {
               				receiverMobiles = Arrays.copyOf(receiverMobiles, receiverMobiles.length+1);
            				receiverNames = Arrays.copyOf(receiverNames, receiverNames.length+1);
            				contents = Arrays.copyOf(contents, contents.length+1);
            				contents[i++] = "您好！您有一份联系单需要处理："+request.getParameter("content");
            				receiverMobiles[j++] = user.getMobile();
            				receiverNames[k++] = user.getRealName();
    					}
                   		smsPubService.send(receiverMobiles, contents, receiverNames);
                   		System.out.println("===============短信发送成功!");
               		}else{
               			HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
               			UserQuery userQuery = identityService.createUserQuery();
               			List<org.activiti.engine.identity.User> activitiUsers = userQuery.userId(hi.getStartUserId()).list();
               			for (org.activiti.engine.identity.User user : activitiUsers) {
               				User u = PfActivator.getUserByHql("from User where username = '"+user.getId()+"'");
               				if(u!=null){
               					String receiverMobile = u.getMobile();
               	           		String receiverName = u.getRealName();
               	           		String content = "您好！您有一份联系单需要处理："+request.getParameter("content");;
               	           		smsPubService.send(receiverMobile, content, receiverName);
               	           		System.out.println("======发送短信给流程发起人=========短信发送成功!");
               				}
    					}
               		}
               	}catch(Exception e){
               		System.err.println("===============短信发送失败");
               		e.printStackTrace();
               	}
           	}
	   		result = Result.success("任务完成");
       } catch (Exception e) {
    	   e.printStackTrace();
    	   result = Result.failure("操作失败");
       }
       return JSON;
	}
	/**
	 * 获取下一环节人员信息
	 * @return
	 */
	private List<User> nextTaskDefinition(String procInstId){
		try{
			String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult().getProcessDefinitionId();  
	        
	        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)repositoryService).getDeployedProcessDefinition(processDefinitionId);  
	        //执行实例  
	        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();  
	        //当前实例的执行到哪个节点  
	        String activitiId = execution.getActivityId();
	        //获得当前任务的所有节点  
	        List<ActivityImpl> activitiList = def.getActivities();  
	        String id = null; 
	        TaskDefinition taskDefinition = null;
	        List<User> users = new ArrayList<User>();
	        for(ActivityImpl activityImpl:activitiList){    
	            id = activityImpl.getId();
	            if(activitiId.equals(id)){
	                System.out.println("当前任务："+activityImpl.getProperty("name"));  
	                taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();
	                //taskDefinition =  nextTaskDefinition(activityImpl, activityImpl.getId(),"${iscorrect==1}");
	                Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
	                //当前用户角色id
	                String groupId = "";
	                for (Expression expression : candidateGroupIdExpressions) {
	                	groupId = expression.getExpressionText();
					}
	                UserQuery userQuery = identityService.createUserQuery();
	                List<org.activiti.engine.identity.User> activitiUsers = userQuery.memberOfGroup(groupId).list();
	                for (org.activiti.engine.identity.User user : activitiUsers) {
	                	User u = PfActivator.getUserByHql("from User where username = '"+user.getId()+"'");
	                	if(u!=null){
	                		users.add(u);
	                	}
					}
	            }  
	        }
	        return users;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String delete(){
		try{
			runtimeService.deleteProcessInstance(pid, "");
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			historyService.deleteHistoricProcessInstance(pid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(id!=null){
			result = contactFormService.deleteById(id);
		} else if(ids!=null){
			result = contactFormService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(ContactForm.class));
	}
	
	@Override
	protected List<ContactForm> getListByFilter(Filter fitler) {
		return contactFormService.getListByFilter(fitler).getValue();
	}
	
	
	public ContactForm getContactForm() {
		return contactForm;
	}

	public void setContactForm(ContactForm contactForm) {
		this.contactForm = contactForm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setContactFormService(ContactFormService contactFormService) {
		this.contactFormService = contactFormService;
	}
	
	public Result getResult() {
		return result;
	}
	
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setContactBaseAttService(ContactBaseAttService contactBaseAttService) {
		this.contactBaseAttService = contactBaseAttService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setProcessTypeService(ProcessTypeService processTypeService) {
		this.processTypeService = processTypeService;
	}

	public String getTaskId() {
		return taskId;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public ContactBaseDto getBaseDto() {
		return baseDto;
	}

	public void setBaseDto(ContactBaseDto baseDto) {
		this.baseDto = baseDto;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getOldName() {
		return oldName;
	}

	public List<ContactBaseAtt> getContactAttList() {
		return contactAttList;
	}

	public void setContactAttList(List<ContactBaseAtt> contactAttList) {
		this.contactAttList = contactAttList;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public ProcessType getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public boolean isSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}
}
