package com.wiiy.pf.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.User;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.activator.PfActivator;
import com.wiiy.pf.dto.ContactBaseDto;
import com.wiiy.pf.entity.ContactBaseAtt;
import com.wiiy.pf.entity.Parkin;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.enums.ProcessTypeEnum;
import com.wiiy.pf.service.ContactBaseAttService;
import com.wiiy.pf.service.ParkinService;
import com.wiiy.pf.service.ProcessTypeService;
import com.wiiy.pf.util.ContactLogUtil;

/**
 * @author my
 */
public class ParkinAction extends JqGridBaseAction<Parkin> {

	private ParkinService parkinService;
	private RuntimeService runtimeService;
	private IdentityService identityService;
	private RepositoryService repositoryService;
	private HistoryService historyService;
	private TaskService taskService;
	private ContactBaseAttService contactBaseAttService;
	private Result result;
	private String taskId;
	private Parkin parkin;
	private Long id;
	private String filePath;
	private String oldName;
	private String newName;
	private String rootPath;
	private String ids;
	
	private String pid;
	
	private boolean start;
	private ContactBaseDto baseDto;
	private ProcessType processType;
	private String actionName;
	private ProcessTypeService processTypeService;
	
	public ParkinAction() {
		rootPath = System.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")+"upload/";
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

	public String save() {
		User user = PfActivator.getSessionUser();
		parkin.setApplyTime(new Date());
		parkin.setCreateTime(new Date());
		parkin.setCreator(user.getRealName());
		parkin.setCreatorId(user.getId());
		parkin.setModifier(user.getRealName());
		parkin.setModifierId(user.getId());
		parkin.setModifyTime(new Date());
		parkin.setStartUserId(user.getUsername());
		parkin.setStartUserName(user.getRealName());
		
		
		result = parkinService.save(parkin);
		Map<String, Object> variables = new HashMap<String, Object>();
		String businessKey = parkin.getId().toString();
		identityService.setAuthenticatedUserId(user.getUsername());
		//runtimeService.start
		
		ProcessInstance processInstance = runtimeService.
				startProcessInstanceByKey("hatch", businessKey, variables);
		String instanceId = processInstance.getId();
		taskId = taskService.createTaskQuery().
				processInstanceId(instanceId).singleResult().getId();
		
		//taskService.setVariablesLocal(taskId, variables);
		
		
		parkin.setProcessInstanceId(instanceId);
		parkinService.update(parkin).getValue();
		parkin = parkinService.getBeanByFilter(
				new Filter(Parkin.class).
				eq("processInstanceId", instanceId)).getValue();
		List<ContactBaseAtt> atts = getListFromJSON(filePath);
		for (ContactBaseAtt att : atts) {
			att.setContactId(parkin.getId());
			att.setProcessInstanceId(instanceId);
			contactBaseAttService.save(att);
		}
		identityService.setAuthenticatedUserId(null);
		
		ContactLogUtil.saveLog(parkin.getId(), ProcessTypeEnum.PARKIN, user, taskId);
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

	public String view() {
		result = parkinService.getBeanById(id);
		return VIEW;
	}

	public String edit() {
		result = parkinService.getBeanById(id);
		return EDIT;
	}

	public String update() {
		Parkin dbBean = parkinService.getBeanById(parkin.getId()).getValue();
		BeanUtil.copyProperties(parkin, dbBean);
		result = parkinService.update(dbBean);
		return JSON;
	}

	public String delete() {
		if (id != null) {
			result = parkinService.deleteById(id);
		} else if (ids != null) {
			result = parkinService.deleteByIds(ids);
		}
		return JSON;
	}

	public String list() {
		return refresh(new Filter(Parkin.class));
	}

	@Override
	protected List<Parkin> getListByFilter(Filter fitler) {
		return parkinService.getListByFilter(fitler).getValue();
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

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
	
	public void setContactBaseAttService(ContactBaseAttService contactBaseAttService) {
		this.contactBaseAttService = contactBaseAttService;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Result getResult() {
		return result;
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

	public void setParkinService(ParkinService parkinService) {
		this.parkinService = parkinService;
	}

	public Parkin getParkin() {
		return parkin;
	}

	public void setParkin(Parkin parkin) {
		this.parkin = parkin;
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

	public ContactBaseDto getBaseDto() {
		return baseDto;
	}

	public String getActionName() {
		return actionName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
