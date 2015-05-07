package com.wiiy.pf.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import me.kafeitu.demo.activiti.service.activiti.WorkflowTraceService;
import me.kafeitu.demo.activiti.util.ProcessDefinitionCache;
import me.kafeitu.demo.activiti.util.WorkflowUtils;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.entity.BaseEntity;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.pf.dto.ProcessDto;
import com.wiiy.pf.entity.Leave;
import com.wiiy.pf.entity.ProcessType;
import com.wiiy.pf.preferences.enums.SuspensionStateEnum;
import com.wiiy.pf.service.LeaveService;
import com.wiiy.pf.service.ProcessTypeService;
import com.wiiy.pf.util.ProcessUtil;

public class ProcessAction extends JqGridBaseAction<ProcessDto> {

	private RepositoryService repositoryService;
	private RuntimeService runtimeService;
	private WorkflowTraceService traceService;
	private ProcessTypeService processTypeService;
	private LeaveService leaveService;
	private String resourceType;
	private ProcessType processType;
	private String type;
	private String id;
	private String pid;//流程实体Id
	public static String exportDir = "/resources/deployments";// 流程部署目录
	private String filePath;
	private String oldName;
	private String newName;
	@SuppressWarnings("rawtypes")
	private Result result;
	private String rootPath;
	private List<Map<String, Object>> activityInfos;
	
	
	public ProcessAction() {
		rootPath = System
				.getProperty("org.springframework.osgi.web.deployer.tomcat.workspace")
				+ "upload/";
	}

	public String list() {
		System.out.println(processTypeService);
		root = new ArrayList<ProcessDto>();
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery().orderByDeploymentId().desc();
		records = (int) (query.count());
		if (records > 0) {
			if (records % rows == 0) {
				total = records % rows;
			} else {
				total = records / rows + 1;
			}
		}
		List<ProcessDefinition> processList = query.listPage(page - 1, rows);
		records = processList.size();
		for (ProcessDefinition definition : processList) {
			String deploymentId = definition.getDeploymentId();
			Deployment deployment = repositoryService.createDeploymentQuery()
					.deploymentId(deploymentId).singleResult();
			ProcessDto dto = ProcessUtil.createDto(definition, deployment);
			root.add(dto);
		}
		return JSON;
	}

	/**
	 * 读取资源，通过部署ID
	 * 
	 * @param processDefinitionId
	 *            流程定义
	 * @param resourceType
	 *            资源类型(xml|image)
	 * @throws Exception
	 */
	public String resourceRead() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery().processDefinitionId(id)
					.singleResult();
			String resourceName = "";
			if (resourceType.equals("image")) {
				resourceName = processDefinition.getDiagramResourceName();
			} else if (resourceType.equals("xml")) {
				resourceName = processDefinition.getResourceName();
			}
			InputStream resourceAsStream = repositoryService
					.getResourceAsStream(processDefinition.getDeploymentId(),
							resourceName);
			byte[] b = new byte[1024];
			int len = -1;
			while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传文件部署流程
	 * @return
	 */
	public String deploy() {
		filePath = rootPath+filePath;
		try {
			File file = new File(filePath);
			Deployment deployment = null;
			if (file.exists()) {
				FileInputStream fileInputStream = new FileInputStream(file);
				String extension = FilenameUtils.getExtension(filePath);
				if (extension.equals("zip") || extension.equals("bar")) {
					ZipInputStream zip = new ZipInputStream(fileInputStream);
					deployment = repositoryService.createDeployment()
							.addZipInputStream(zip).deploy();

				} else {
					deployment = repositoryService.createDeployment()
							.addInputStream(filePath, fileInputStream).deploy();
				}
				List<ProcessDefinition> list = repositoryService
						.createProcessDefinitionQuery()
						.deploymentId(deployment.getId()).list();

				for (ProcessDefinition processDefinition : list) {
					WorkflowUtils.exportDiagramToFile(repositoryService,
							processDefinition, exportDir);
				}
				ProcessDefinition definition = repositoryService
						.createProcessDefinitionQuery()
						.deploymentId(deployment.getId()).singleResult();
				processType.setDeployId(deployment.getId());
				processType.setProcessVesion(definition.getVersion()+"");
				processType.setProcessDefId(definition.getId());
				processTypeService.save(processType);
				result = Result.success("部署成功!");
			} else {
				result = Result.failure("上传失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.failure("部署失败!");
		}

		return JSON;
	}

	/**
	 * 挂起、激活流程实例
	 */
	public String changeState() {
		ProcessDefinition definition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(id)
				.singleResult();
		boolean isSuspended = definition.isSuspended();
		if (isSuspended) {
			repositoryService.activateProcessDefinitionById(id, true, null);
			result = Result.success("流程已激活");
		} else {
			repositoryService.suspendProcessDefinitionById(id, true, null);
			result = Result.success("流程已挂起");
		}
		return JSON;
	}
	
	
	//删除上传的文件
	public String deleteByFilePath() {
		if(filePath!=null){
			File file = new File(rootPath + filePath);
			if (file.exists()) {
				file.delete();
			}
			result = Result.success("文件删除成功");
		}
		return JSON;
	}

	/**
	 * 删除部署的流程，级联删除流程实例
	 * 
	 * @param deploymentId
	 *            流程部署ID
	 */
	// @RequestMapping(value = "/process/delete")
	public String processDelete() {
		ProcessDefinition definition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(id)
				.singleResult();
		List<ProcessType> processTypes = processTypeService.getListByFilter(
				new Filter(ProcessType.class).eq("deployId", 
						definition.getDeploymentId())).getValue();
		List<ProcessInstance> instances = 
				runtimeService.createProcessInstanceQuery().
				processDefinitionId(id).list();
		Object[] objects = new Object[instances.size()];
		for (int i = 0; i < instances.size(); i++) {
			objects[i] = instances.get(i).getId();
		}
		
		if (objects.length > 0) {
			List<Leave> leaves = leaveService.getListByFilter(
					new Filter(Leave.class).in("processInstanceId", objects)).getValue();
			//删除
			if (leaves == null || leaves.size() == 0) {
				result = Result.failure("删除流程失败!");
				return JSON;
			}
			leaveService.deleteByIds(getIdsByList(leaves));
		}
		if (processTypes.size() > 0) {
			processTypeService.deleteByIds(getIdsByList(processTypes));
		}
		try {
			repositoryService.deleteDeployment(definition.getDeploymentId(), true);
			result = Result.success("删除流程成功!");
		} catch (Exception e) {
			result = Result.failure("删除流程失败!");
			e.printStackTrace();
		}
		return JSON;
	}
	
	@SuppressWarnings("rawtypes")
	private String getIdsByList(List list){
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			BaseEntity entity = (BaseEntity) list.get(i);
			builder.append(entity.getId());
			builder.append(",");
		}
		return builder.deleteCharAt(builder.length()-1).toString();
	}
	
	public String running() {
		root = new ArrayList<ProcessDto>();
		ProcessInstanceQuery processInstanceQuery = 
				runtimeService.createProcessInstanceQuery();
		records = (int) (processInstanceQuery.count());
		if (records > 0) {
			if (records % rows == 0) {
				total = records % rows;
			} else {
				total = records / rows + 1;
			}
		}
        List<ProcessInstance> list = 
        		processInstanceQuery.listPage(page-1, rows);
        records = list.size();
        for (ProcessInstance p : list) {
			ProcessDto dto = createDto(p);
			root.add(dto);
		}
		return JSON;
	}
	
	private ProcessDto createDto(ProcessInstance p){
		ProcessDto dto = new ProcessDto();
		dto.setId(p.getId());
		dto.setExecuteId(p.getId());
		dto.setDefinitionId(p.getProcessDefinitionId());
		dto.setInstanceId(p.getProcessInstanceId());
		ProcessDefinitionCache.setRepositoryService(repositoryService);
		String name = ProcessDefinitionCache.getActivityName(
				p.getProcessDefinitionId(), p.getActivityId());
		dto.setCurrentNodeName(name);
		if (p.isSuspended()) {
			dto.setState(SuspensionStateEnum.PENDING);
		} else {
			dto.setState(SuspensionStateEnum.ACTIVE);
		}
		return dto;
	}
	
	/**
	* 读取资源，通过流程ID
	*
	* @param resourceType      资源类型(xml|image)
	* @param processInstanceId 流程实例ID
	* @param response
	* @throws Exception
	*/
	public String processInstance() {
		HttpServletResponse response=ServletActionContext.getResponse();
		 try {
			InputStream resourceAsStream = null;
			 ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
			 ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId())
			         .singleResult();
			
			 String resourceName = "";
			 if (type.equals("image")) {
			     resourceName = processDefinition.getDiagramResourceName();
			 } else if (type.equals("xml")) {
			     resourceName = processDefinition.getResourceName();
			 }
			 resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
			 byte[] b = new byte[1024];
			 int len = -1;
			 while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
			     response.getOutputStream().write(b, 0, len);
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
	}
	
	
	/**
	* 输出跟踪流程信息
	*
	* @param processInstanceId
	* @return
	* @throws Exception
	*/
	public String traceProcess() {
	 try {
		activityInfos = traceService.traceProcess(pid);
	} catch (Exception e) {
		e.printStackTrace();
	}
	 return JSON;
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
	
	public void setLeaveService(LeaveService leaveService) {
		this.leaveService = leaveService;
	}

	@Override
	protected List<ProcessDto> getListByFilter(Filter fitler) {
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

}
