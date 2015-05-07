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
import org.apache.ibatis.jdbc.Null;
import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.engineering.entity.EngineeringBillPlanRent;
import com.wiiy.engineering.service.EngineeringBillPlanRentService;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dto.BillRentDto;
import com.wiiy.pf.dto.BillTaskDto;
import com.wiiy.pf.dto.ContactBaseDto;
import com.wiiy.pf.entity.Bill;
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.enums.DepartmentEnum;
import com.wiiy.pf.preferences.enums.ProcessTypeEnum;
import com.wiiy.pf.service.BillService;
import com.wiiy.pf.service.ContactBaseAttService;
import com.wiiy.pf.service.ProcessTypeService;
import com.wiiy.pf.util.ContactLogUtil;

/**
 * @author my
 */
public class BillAction extends JqGridBaseAction<Bill>{
	
	private BillService billService;
	private RuntimeService runtimeService;
	private IdentityService identityService;
	private RepositoryService repositoryService;
	private HistoryService historyService;
	private TaskService taskService;
	private ContactBaseAttService contactBaseAttService;
	private ProcessTypeService processTypeService;
	private EngineeringBillPlanRentService engineeringService;
	
	@SuppressWarnings("rawtypes")
	private Result result;
	private Bill bill;
	private BillRentDto billRentDto;
	private Long id;
	private String ids;
	private String taskId;
	private DepartmentEnum department;
	private String processDefinitionKey;
	private Long contractId;
	
	private String oldName;
	private String newName;
	private String rootPath;
	private String filePath;
	private String pid;
	private User user;
	
	private boolean start;
	private ContactBaseDto baseDto;
	private ProcessType processType;
	private String actionName;
	private boolean sendEmail = false;
	
	private List<ContactBaseAtt> contactAttList = new ArrayList<ContactBaseAtt>();
	
	public BillAction() {
		rootPath = System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"upload/";
	}
	
	public String addByPlanId(){
		user = PfActivator.getSessionUser();
		billRentDto = new BillRentDto();
		billRentDto.setPlanId(id);
		billRentDto.setContractId(contractId);
		processDefinitionKey = department.toString().toLowerCase()+"Bill";
		billService.getDtoById(processDefinitionKey, department, billRentDto);
		return "addByPlanId";
	}
	
	public String add(){
		if (taskId != null) {
			ProcessDefinition definition = repositoryService.getProcessDefinition(taskId);
			if (definition.isSuspended()) {
				return "suspended";
			}
			billRentDto = new BillRentDto();
			billRentDto.setTaskName(definition.getName()+"V"+definition.getVersion());
		}
		return "add";
	}
	
	@SuppressWarnings("rawtypes")
	public String findCompleteFee() {
		String sql = "SELECT SUM(settlement_fee) From act_bill WHERE contract_id="+contractId;
		sql += " AND department='"+department+"'";
		sql += " AND paid='YES'";
		List list = (List) billService.getListBySql(sql).getValue();
		billRentDto = new BillRentDto();
		if (list != null && list.get(0) != null) {
			billRentDto.setCompletedFee(Double.parseDouble(list.get(0).toString()));
		}else {
			billRentDto.setCompletedFee(0d);
		}
		return JSON;
	}
	
	public String attList(){
		contactAttList = contactBaseAttService.getListByFilter(//
				new Filter(ContactBaseAtt.class).eq("contactId", id)).getValue();
		return "attList";
	}
	
	public String save(){
		User user = PfActivator.getSessionUser();
		bill.setUserId(user.getId().toString());
		bill.setApplyTime(new Date());
		if (bill.getPaid() == null || //
				(bill.getPaid() != null &&//
				"".equals(bill.getPaid()))) {
			bill.setPaid(BooleanEnum.NO);
		}
		result = billService.save(bill);
		Map<String, Object> variables = new HashMap<String, Object>();
		String businessKey = bill.getId().toString();
		identityService.setAuthenticatedUserId(user.getUsername());
		ProcessInstance processInstance = runtimeService.
				startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
		String instanceId = processInstance.getId();
		
		taskId = taskService.createTaskQuery().
				processInstanceId(instanceId).singleResult().getId();
		
		//taskService.setVariablesLocal(taskId, variables);
		
		bill.setProcessInstanceId(instanceId);
		billService.update(bill).getValue();
		bill = billService.getBeanByFilter(
				new Filter(Bill.class).
				eq("processInstanceId", instanceId)).getValue();
		List<ContactBaseAtt> atts = getListFromJSON(filePath);
		for (ContactBaseAtt att : atts) {
			att.setContactId(bill.getId());
			att.setProcessInstanceId(instanceId);
			contactBaseAttService.save(att);
		}
		identityService.setAuthenticatedUserId(null);
		ContactLogUtil.saveLog(bill.getId(), ProcessTypeEnum.BILL, user, taskId);
		return JSON;
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
		billRentDto = new BillRentDto();
		if (processDefinitionKey == null) {
			HistoricProcessInstance instance =  historyService. //
					createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
			processDefinitionKey = instance.getProcessDefinitionId();
		}
		billRentDto.setTaskId(processDefinitionKey);
		result = billService.getBeanById(id,billRentDto);
		return VIEW;
	}
	
	public String viewById() {
		billRentDto = new BillRentDto();
		if (processDefinitionKey == null) {
			HistoricProcessInstance instance =  historyService. //
					createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
			processDefinitionKey = instance.getProcessDefinitionId();
		}
		billRentDto.setTaskId(processDefinitionKey);
		result = billService.getBeanById(id,billRentDto);
		return VIEW;
	}
	
	public String edit(){
		result = billService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		if (processDefinitionKey != null) {
			ProcessDefinition definition = repositoryService.getProcessDefinition(processDefinitionKey);
			if (definition.isSuspended()) {
				return "suspended";
			}
		}
		HttpServletRequest request=ServletActionContext.getRequest();
   	 	Variable var=new  Variable();
   	 	var.setKeys(request.getParameter("keys"));
   	 	var.setTypes(request.getParameter("types"));
   	 	var.setValues(request.getParameter("values"));
   	 	
   	 	Map<String, Object> variables = var.getVariableMap();
   	 	
   	 	taskService.setVariablesLocal(taskId, variables);
   	 	System.out.println("ContactAction.complete():taskId="+taskId+",variables="+variables);
       try {
    	   	String businessKey = bill.getId().toString();
    	   	ProcessInstance	processInstance = runtimeService.createProcessInstanceQuery().//
    	   			processInstanceBusinessKey(businessKey).singleResult();
    	   	String instanceId = processInstance.getId();
    	   	taskService.complete(taskId,variables);
    	   	if(filePath!=null && filePath.length()>0){
	   			List<ContactBaseAtt> atts = getListFromJSON(filePath);
	   			for (ContactBaseAtt att : atts) {
	   				att.setContactId(bill.getId());
	   				att.setProcessInstanceId(instanceId);
	   				contactBaseAttService.save(att);
	   			}
	   		}
    	   	
    		boolean cwcn = variables.containsKey("cwcnBackReason");
           	if (cwcn) {
				Bill dbBean = billService.getBeanById(bill.getId()).getValue();
				if ((bill.getPaid() != null && //
						"".equals(bill.getPaid())) || //
						bill.getPaid() == null) {
					dbBean.setPaid(BooleanEnum.NO);
				}else {
					dbBean.setPaid(bill.getPaid());
				}
				billService.update(dbBean,dbBean.getDepartment());
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
	
	public String handle() {
		if (processDefinitionKey != null) {
			ProcessDefinition definition = repositoryService.getProcessDefinition(processDefinitionKey);
			if (definition.isSuspended()) {
				return "suspended";
			}
		}
		
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
				processType = processTypeService.getBeanByFilter(
						new Filter(ProcessType.class).//
							eq("processDefId", proDefId)).getValue();
				actionName=processType.getFormName().name().toLowerCase();
				baseDto.setProcessType(processType);
				baseDto.setTask(task);
				baseDto.setProcessInstance(processInstance);
				HistoricProcessInstance hiProcessInstance =historyService.//
						createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
				baseDto.setHiProcessInstance(hiProcessInstance);
				ProcessDefinition processDefinition = repositoryService
						.createProcessDefinitionQuery()
						.processDefinitionId(proDefId).singleResult();
				baseDto.setProcessDefinition(processDefinition);
				
				baseDto.setTaskDefinitions(findTaskDefinitions(proDefId));
				
				result = Result.value(baseDto);
			}
		}
		contactAttList = contactBaseAttService.getListByFilter(//
				new Filter(ContactBaseAtt.class).eq("contactId", id)).getValue();
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
			result = billService.deleteById(id);
		} 
		return JSON;
	}
	
	
	public String findBillsByContractId(){
		Filter filter = new Filter(Bill.class);
		if (contractId != null && department != null) {
			filter.eq("contractId", contractId).eq("department", department);
		}
		filter.eq("paid", BooleanEnum.YES);
		filter.include("id").include("settlementFee").include("settlementDate");
		result = billService.getListByFilter(filter);
		return JSON;
	}
	
	public String list(){
		Filter filter = new Filter(Bill.class);
		if (contractId != null && department != null) {
			filter.eq("contractId", contractId).eq("department", department);
		}
		return refresh(filter);
	}
	
	@Override
	protected List<Bill> getListByFilter(Filter fitler) {
		return billService.getListByFilter(fitler).getValue();
	}
	
	
	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
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

	public void setBillService(BillService billService) {
		this.billService = billService;
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

	public void setProcessTypeService(ProcessTypeService processTypeService) {
		this.processTypeService = processTypeService;
	}

	public void setEngineeringService(
			EngineeringBillPlanRentService engineeringService) {
		this.engineeringService = engineeringService;
	}

	public String getTaskId() {
		return taskId;
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

	public BillRentDto getBillRentDto() {
		return billRentDto;
	}

	public void setBillRentDto(BillRentDto billRentDto) {
		this.billRentDto = billRentDto;
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

	public DepartmentEnum getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEnum department) {
		this.department = department;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public User getUser() {
		return user;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
}
