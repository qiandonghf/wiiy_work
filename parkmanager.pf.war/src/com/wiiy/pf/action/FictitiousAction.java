package com.wiiy.pf.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.entity.User;
import com.wiiy.core.service.export.DataDictInitService;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dto.ContactBaseDto;
import com.wiiy.pf.dto.InvestmentDto;
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.entity.Fictitious;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.enums.ProcessTypeEnum;
import com.wiiy.pf.service.ContactBaseAttService;
import com.wiiy.pf.service.FictitiousService;
import com.wiiy.pf.service.ProcessTypeService;
import com.wiiy.pf.util.ContactLogUtil;

/**
 * @author my
 */
public class FictitiousAction extends JqGridBaseAction<Fictitious>{
	
	private FictitiousService fictitiousService;
	private RuntimeService runtimeService;
	private IdentityService identityService;
	private RepositoryService repositoryService;
	private HistoryService historyService;
	private TaskService taskService;
	private ContactBaseAttService contactBaseAttService;
	private ProcessTypeService processTypeService;
	
	private List<DataDict> incubationRouteList;
	private List<DataDict> customerQualificationList;
	
	private Result result;
	private Fictitious fictitious;
	private String taskId;
	private Long id;
	private String ids;
	
	private String oldName;
	private String newName;
	private String rootPath;
	private String pid;
	
	private boolean start;
	private ContactBaseDto baseDto;
	private ProcessType processType;
	private String actionName;
	private String filePath;
	private boolean inpark;
	
	private boolean sendEmail = false;
	
	private Long investmentId;
	private InvestmentDto investmentDto;
	
	private String fileName;
	private InputStream inputStream;
	
	private List<ContactBaseAtt> contactAttList = new ArrayList<ContactBaseAtt>();
	
	public String print(){
		fileName = StringUtil.URLEncoderToUTF8("入驻审批单")+".doc";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			fictitiousService.print(id,taskId, out);
			inputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "print";
	}
	
	public FictitiousAction(){
		rootPath = System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"upload/";
	}
	
	public String addByInvestmentId(){
		String taskName = "FICTITIOUS";
		if(inpark){
			taskName = "INAPPROVAL";
		}
		String sql = "select * from act_process_type where form_name = '"+taskName+"'";
		List<Object> list = (List<Object>)fictitiousService.getListBySql(sql);
		for (Object object : list) {
			Object[] obj = (Object[])object;
			taskId = obj[1].toString();
		}
		sql = "select id,name,legal_person,reg_capital,business_scope,address from crm_investment where id="+investmentId;
		List<Object> investmentList = (List<Object>)fictitiousService.getListBySql(sql);
		investmentDto = new InvestmentDto();
		for (Object object : investmentList) {
			Object[] obj = (Object[])object;
			investmentDto.setId(investmentId);
			investmentDto.setName(obj[1].toString());
			if(obj[2]!=null){
				investmentDto.setLegalPerson(obj[2].toString());
			}
			if(obj[3]!=null){
				investmentDto.setRegCapital(BigDecimal.valueOf(Double.valueOf(obj[3].toString())));
			}
			if(obj[4]!=null){
				investmentDto.setBusinessScope(obj[4].toString());
			}
			if(obj[5]!=null){
				investmentDto.setAddress(obj[5].toString());
			}
		}
		return "addByInvestmentId";
	}
	
	public String add(){
		return "add";
	}
	
	public String customerAdd(){
		incubationRouteList = new ArrayList<DataDict>();
		customerQualificationList = new ArrayList<DataDict>();
		DataDictInitService dataDictInitService = PfActivator.getDataDictInitService();
		if(dataDictInitService!=null){
			incubationRouteList = dataDictInitService.getDataDictByParentId("crm.0025");
			customerQualificationList = dataDictInitService.getDataDictByParentId("crm.0027");
		}
		result = fictitiousService.getInvestment(id);
		return "customerAdd";
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
				processType = processTypeService.getBeanByFilter(new Filter(ProcessType.class).
								eq("processDefId", proDefId)).getValue();
				actionName=processType.getFormName().name().toLowerCase();
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
		contactAttList = contactBaseAttService.getListByFilter(new Filter(ContactBaseAtt.class).eq("contactId", id)).getValue();
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
	
	private Map<String,String[]> formRoomRent(Map<String,String[]> roomRent, HttpServletRequest request){
		String[] roomIds = request.getParameterValues("roomId");
		String[] roomNames = request.getParameterValues("roomName");
		String[] roomAreas = request.getParameterValues("roomArea");
		String[] startDate = request.getParameterValues("startDate");
		String[] endDate = request.getParameterValues("endDate");
		String[] rentPrices = request.getParameterValues("rentPrice");
		String[] rentPriceUnit = request.getParameterValues("rentPriceUnit");
		/*String[] managePrice = request.getParameterValues("managePrice");
		String[] managePriceUnit = request.getParameterValues("managePriceUnit");*/
		String[] rebate = request.getParameterValues("rebate");
		String[] rebateRuleId = request.getParameterValues("rebateRuleId");
		String[] memo = request.getParameterValues("memo");
		
		roomRent.put("roomIds", roomIds);
		roomRent.put("roomNames", roomNames);
		roomRent.put("roomAreas", roomAreas);
		roomRent.put("startDate", startDate);
		roomRent.put("endDate", endDate);
		roomRent.put("rentPrices", rentPrices);
		roomRent.put("rentPriceUnit", rentPriceUnit);
		/*roomRent.put("managePrice", managePrice);
		roomRent.put("managePriceUnit", managePriceUnit);*/
		roomRent.put("rebate", rebate);
		roomRent.put("rebateRuleId", rebateRuleId);
		roomRent.put("memo", memo);
		return roomRent;
	}
	
	private Map<String, String[]> fromBillPlanRent(
			Map<String, String[]> billPlanRent, HttpServletRequest request) {
		String[] planRoomNames = request.getParameterValues("planRoomName");
		String[] planRoomIds = request.getParameterValues("planRoomId");
		String[] feeTypes = request.getParameterValues("feeType");
		String[] planFees = request.getParameterValues("planFee");
		String[] realFees = request.getParameterValues("realFee");
		String[] planStartDates = request.getParameterValues("planStartDate");
		String[] planEndDates = request.getParameterValues("planEndDate");
		String[] planPayDates = request.getParameterValues("planPayDate");
		String[] planStatus = request.getParameterValues("planStatus");
		String[] planMemos = request.getParameterValues("planMemo");
		
		billPlanRent.put("planRoomNames", planRoomNames);
		billPlanRent.put("planRoomIds", planRoomIds);
		billPlanRent.put("feeTypes", feeTypes);
		billPlanRent.put("planFees", planFees);
		billPlanRent.put("realFees", realFees);
		billPlanRent.put("planStartDates", planStartDates);
		billPlanRent.put("planEndDates", planEndDates);
		billPlanRent.put("planPayDates", planPayDates);
		billPlanRent.put("planStatus", planStatus);
		billPlanRent.put("planMemos", planMemos);
		return billPlanRent;
	}
	
	private Map<String, String[]> fromDeposit(
			Map<String, String[]> deposit, HttpServletRequest request) {
		String[] depositTypes = request.getParameterValues("depositType");
		String[] depositAmounts = request.getParameterValues("depositAmount");
		String[] depositMemos = request.getParameterValues("depositMemo");
		
		deposit.put("depositTypes", depositTypes);
		deposit.put("depositAmounts", depositAmounts);
		deposit.put("depositMemos", depositMemos);
		return deposit;
	}
	
	public String save() {
		User user = PfActivator.getSessionUser();
		fictitious.setApplyTime(new Date());
		fictitious.setCreateTime(new Date());
		fictitious.setCreator(user.getRealName());
		fictitious.setCreatorId(user.getId());
		fictitious.setModifier(user.getRealName());
		fictitious.setModifierId(user.getId());
		fictitious.setModifyTime(new Date());
		fictitious.setStartUserId(user.getUsername());
		fictitious.setStartUserName(user.getRealName());
		
		/*HttpServletRequest request = ServletActionContext.getRequest();
		
		Map<String,String[]> roomRent = new HashMap<String, String[]>();
		Map<String,String[]> billPlanRent = new HashMap<String, String[]>();
		Map<String,String[]> deposit = new HashMap<String, String[]>();
		
		roomRent = formRoomRent(roomRent,request);
		billPlanRent = fromBillPlanRent(billPlanRent,request);
		deposit = fromDeposit(deposit,request);
		
		result = fictitiousService.save(fictitious,roomRent,billPlanRent,deposit);*/
		
		result = fictitiousService.save(fictitious);
		Map<String, Object> variables = new HashMap<String, Object>();
		String businessKey = fictitious.getId().toString();
		identityService.setAuthenticatedUserId(user.getUsername());
		//runtimeService.start
		
		ProcessInstance processInstance = runtimeService.
				startProcessInstanceByKey("parkin", businessKey, variables);
		String instanceId = processInstance.getId();
		taskId = taskService.createTaskQuery().
				processInstanceId(instanceId).singleResult().getId();
		
		//taskService.setVariablesLocal(taskId, variables);
		
		
		fictitious.setProcessInstanceId(instanceId);
		fictitiousService.update(fictitious).getValue();
		fictitious = fictitiousService.getBeanByFilter(
				new Filter(Fictitious.class).
				eq("processInstanceId", instanceId)).getValue();
		if(filePath!=null && filePath.length()>0){
			List<ContactBaseAtt> atts = getListFromJSON(filePath);
			for (ContactBaseAtt att : atts) {
				att.setContactId(fictitious.getId());
				att.setProcessInstanceId(instanceId);
				contactBaseAttService.save(att);
			}
		}
		identityService.setAuthenticatedUserId(null);
		
		ContactLogUtil.saveLog(fictitious.getId(), ProcessTypeEnum.FICTITIOUS, user, taskId);
		fictitious = null;
		return JSON;
	}
	
	public String attList(){
		contactAttList = contactBaseAttService.getListByFilter(new Filter(ContactBaseAtt.class).eq("contactId", id)).getValue();
		return "attList";
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
				a.setSize(Long.valueOf(o.getString("size")));
				att.add(a);
			}
		}
		return att;
	}
	
	public String view(){
		result = fictitiousService.getBeanById(id);
		return VIEW;
	}
	
	public String edit(){
		result = fictitiousService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
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
    	   	String businessKey = fictitious.getId().toString();
    	   	ProcessInstance processInstance = runtimeService.
   				startProcessInstanceByKey("parkin", businessKey, variables);
    	   	String instanceId = processInstance.getId();
    	   	if(filePath!=null && filePath.length()>0){
	   			List<ContactBaseAtt> atts = getListFromJSON(filePath);
	   			for (ContactBaseAtt att : atts) {
	   				att.setContactId(fictitious.getId());
	   				att.setProcessInstanceId(instanceId);
	   				contactBaseAttService.save(att);
	   			}
	   		}
    	   	taskService.complete(taskId,variables);
           	if(request.getParameter("agreement")!=null && "true".equals(request.getParameter("agreement"))){
           		Fictitious dbBean = fictitiousService.getBeanById(fictitious.getId()).getValue();
       			BeanUtil.copyProperties(fictitious, dbBean);
    	   		result = fictitiousService.update(dbBean);
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
	
	 /** 
     * 下一个任务节点 
     * @param activityImpl 
     * @param activityId 
     * @param elString 
     * @return 
     */  
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId, String elString){  
            if("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())){  
                TaskDefinition taskDefinition = ((UserTaskActivityBehavior)activityImpl.getActivityBehavior()).getTaskDefinition();  
//              taskDefinition.getCandidateGroupIdExpressions().toArray();  
                return taskDefinition;  
            }else{  
                List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();  
                List<PvmTransition> outTransitionsTemp = null;  
                for(PvmTransition tr:outTransitions){    
                    PvmActivity ac = tr.getDestination(); //获取线路的终点节点    
                    if("exclusiveGateway".equals(ac.getProperty("type"))){  
                        outTransitionsTemp = ac.getOutgoingTransitions();  
                        if(outTransitionsTemp.size() == 1){  
                            return nextTaskDefinition((ActivityImpl)outTransitionsTemp.get(0).getDestination(), activityId, elString);  
                        }else if(outTransitionsTemp.size() > 1){  
                            for(PvmTransition tr1 : outTransitionsTemp){  
                                Object s = tr1.getProperty("conditionText");  
                                if(elString.equals(StringUtil.trim(s.toString(),""))){  
                                    return nextTaskDefinition((ActivityImpl)tr1.getDestination(), activityId, elString);  
                                }  
                            }  
                        }  
                    }else if("userTask".equals(ac.getProperty("type"))){  
                        return ((UserTaskActivityBehavior)((ActivityImpl)ac).getActivityBehavior()).getTaskDefinition();  
                    }else{  
                        logger.debug(ac.getProperty("type"));  
                    }  
                }   
            return null;  
        }  
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
		result = fictitiousService.deleteById(id);
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(Fictitious.class));
	}
	
	@Override
	protected List<Fictitious> getListByFilter(Filter fitler) {
		return fictitiousService.getListByFilter(fitler).getValue();
	}
	
	
	public Fictitious getFictitious() {
		return fictitious;
	}

	public void setFictitious(Fictitious fictitious) {
		this.fictitious = fictitious;
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

	public void setFictitiousService(FictitiousService fictitiousService) {
		this.fictitiousService = fictitiousService;
	}
	
	public Result getResult() {
		return result;
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

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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

	public void setResult(Result result) {
		this.result = result;
	}


	public List<ContactBaseAtt> getContactAttList() {
		return contactAttList;
	}


	public void setContactAttList(List<ContactBaseAtt> contactAttList) {
		this.contactAttList = contactAttList;
	}
	public boolean isSendEmail() {
		return sendEmail;
	}


	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public List<DataDict> getIncubationRouteList() {
		return incubationRouteList;
	}

	public void setIncubationRouteList(List<DataDict> incubationRouteList) {
		this.incubationRouteList = incubationRouteList;
	}

	public List<DataDict> getCustomerQualificationList() {
		return customerQualificationList;
	}

	public void setCustomerQualificationList(
			List<DataDict> customerQualificationList) {
		this.customerQualificationList = customerQualificationList;
	}

	public boolean isInpark() {
		return inpark;
	}

	public void setInpark(boolean inpark) {
		this.inpark = inpark;
	}
	public Long getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Long investmentId) {
		this.investmentId = investmentId;
	}
	public InvestmentDto getInvestmentDto() {
		return investmentDto;
	}

	public void setInvestmentDto(InvestmentDto investmentDto) {
		this.investmentDto = investmentDto;
	}
}
