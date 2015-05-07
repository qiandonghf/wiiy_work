package com.wiiy.activiti.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import me.kafeitu.demo.activiti.service.activiti.WorkflowProcessDefinitionService;
import me.kafeitu.demo.activiti.service.activiti.WorkflowTraceService;
import me.kafeitu.demo.activiti.util.Page;
import me.kafeitu.demo.activiti.util.PageUtil;
import me.kafeitu.demo.activiti.util.UserUtil;
import me.kafeitu.demo.activiti.util.WorkflowUtils;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程管理控制器
 *
 * @author aswan
 */
public class WorkflowAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected WorkflowProcessDefinitionService workflowProcessDefinitionService;

    protected RepositoryService repositoryService;
    
    private HistoryService historyService;

    private File file;
    private String fileContentType;
    private String fileFileName;
    

    public static String exportDir="/resources/deployments";//流程部署目录

	protected RuntimeService runtimeService;

    protected TaskService taskService;

    protected WorkflowTraceService traceService;

    protected static Map<String, ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String, ProcessDefinition>();


    private String deploymentId;
    private String processDefinitionId;
    private String state;
    private String resourceType;
    
    private String pid;
    private String type;
    
    private String message;
    
    private List<Map<String, Object>> activityInfos ;
    
    
    private List<Map<String, Object>> todoList;
    /**
     * 流程定义列表
     *
     * @return
     */
   public String processList() {
	   	HttpServletRequest request=ServletActionContext.getRequest();
       // ModelAndView mav = new ModelAndView("workflow/process-list");

    /*
     * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
     */
        List<Object[]> objects = new ArrayList<Object[]>();

        Page<Object[]> page = new Page<Object[]>(PageUtil.PAGE_SIZE);
        int[] pageParams = PageUtil.init(page, request);

        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(pageParams[0], pageParams[1]);
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            objects.add(new Object[]{processDefinition, deployment});
        }

        page.setTotalCount(processDefinitionQuery.count());
        page.setResult(objects);
        //mav.addObject("page", page);
        request.setAttribute("page", page);
        return "process-list";
    }
   //上传文件部署流程
	 //@RequestMapping(value = "/deploy")
	 public String deploy() {
	
	     //String fileName = file.getName();
	
	     try {
	         InputStream fileInputStream = new FileInputStream(file);// file.getInputStream();
	         Deployment deployment = null;
	
	         String extension = FilenameUtils.getExtension(fileFileName);
	         if (extension.equals("zip") || extension.equals("bar")) {
	             ZipInputStream zip = new ZipInputStream(fileInputStream);
	             deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
	             
	         } else {
	        	 deployment = repositoryService.createDeployment().addInputStream(fileFileName, fileInputStream).deploy();
	         }
	
	         List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
	
	         for (ProcessDefinition processDefinition : list) {
	             WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
	         }
	
	     } catch (Exception e) {
	         logger.error("error on deploy process, because of file input stream", e);
	     }
	
	     return processList();
	 }
	   /**
	   * 删除部署的流程，级联删除流程实例
	   *
	   * @param deploymentId 流程部署ID
	   */
	  //@RequestMapping(value = "/process/delete")
	  public String processDelete() {
	      repositoryService.deleteDeployment(deploymentId, true);
	      return processList();
	  }
	  
    /**
    * 读取资源，通过部署ID
    *
    * @param processDefinitionId 流程定义
    * @param resourceType        资源类型(xml|image)
    * @throws Exception
    */
  // @RequestMapping(value = "/resource/read")
   public String resourceRead() {

       try {
		HttpServletResponse response=ServletActionContext.getResponse();
		   ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		   String resourceName = "";
		   if (resourceType.equals("image")) {
		       resourceName = processDefinition.getDiagramResourceName();
		   } else if (resourceType.equals("xml")) {
		       resourceName = processDefinition.getResourceName();
		   }
		   InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
		   byte[] b = new byte[1024];
		   int len = -1;
		   while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
		       response.getOutputStream().write(b, 0, len);
		   }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       return null;
   }
	 /**
	 * 挂起、激活流程实例
	 */
	//@RequestMapping(value = "processdefinition/update/{state}/{processDefinitionId}")
	public String processdefinition_updateState() {
		
	    if (state.equals("active")) {
	    	message= "已激活ID为[" + processDefinitionId + "]的流程定义。";
	        repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
	    } else if (state.equals("suspend")) {
	        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
	        message= "已挂起ID为[" + processDefinitionId + "]的流程定义。";
	    }
	    return processList();
	}
	/**
	* 读取资源，通过流程ID
	*
	* @param resourceType      资源类型(xml|image)
	* @param processInstanceId 流程实例ID
	* @param response
	* @throws Exception
	*/
	//@RequestMapping(value = "/resource/process-instance")
	public String processInstance() {
		HttpServletResponse response=ServletActionContext.getResponse();
		 try {
			InputStream resourceAsStream = null;
			HistoricProcessInstance hi_processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
			 ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(hi_processInstance.getProcessDefinitionId())
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	public String hi_processInstance() {
		HttpServletResponse response=ServletActionContext.getResponse();
		 try {
			InputStream resourceAsStream = null;
			 HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(pid).singleResult();
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
			// TODO Auto-generated catch block
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
		//@RequestMapping(value = "/process/trace")
		//@ResponseBody
		public String traceProcess() {
		 try {
			//HistoricTaskInstance historicTaskInstance= historyService.createHistoricTaskInstanceQuery().processDefinitionId(pid).singleResult();
			//historicTaskInstance.
			activityInfos = traceService.traceProcess(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "json";
		}
		public String hi_traceProcess() {
			 try {
				//HistoricTaskInstance historicTaskInstance= historyService.createHistoricTaskInstanceQuery().processDefinitionId(pid).singleResult();
				//historicTaskInstance.
				 List<HistoricActivityInstance> activitiList=historyService.createHistoricActivityInstanceQuery().processInstanceId(pid).list();
				 List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
			        
				activityInfos = traceService.traceProcess(pid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return "json";
			}

//
//    /**
//     * 部署全部流程
//     *
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/redeploy/all")
//    public String redeployAll(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir) throws Exception {
//        workflowProcessDefinitionService.deployAllFromClasspath(exportDir);
//        return "redirect:/workflow/process-list";
//    }
//
//    /**
//     * 读取资源，通过部署ID
//     *
//     * @param processDefinitionId 流程定义
//     * @param resourceType        资源类型(xml|image)
//     * @throws Exception
//     */
//    @RequestMapping(value = "/resource/read")
//    public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
//                                 HttpServletResponse response) throws Exception {
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
//        String resourceName = "";
//        if (resourceType.equals("image")) {
//            resourceName = processDefinition.getDiagramResourceName();
//        } else if (resourceType.equals("xml")) {
//            resourceName = processDefinition.getResourceName();
//        }
//        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
//        byte[] b = new byte[1024];
//        int len = -1;
//        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
//            response.getOutputStream().write(b, 0, len);
//        }
//    }
//
//    /**
//     * 读取资源，通过流程ID
//     *
//     * @param resourceType      资源类型(xml|image)
//     * @param processInstanceId 流程实例ID
//     * @param response
//     * @throws Exception
//     */
//    @RequestMapping(value = "/resource/process-instance")
//    public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, HttpServletResponse response)
//            throws Exception {
//        InputStream resourceAsStream = null;
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId())
//                .singleResult();
//
//        String resourceName = "";
//        if (resourceType.equals("image")) {
//            resourceName = processDefinition.getDiagramResourceName();
//        } else if (resourceType.equals("xml")) {
//            resourceName = processDefinition.getResourceName();
//        }
//        resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
//        byte[] b = new byte[1024];
//        int len = -1;
//        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
//            response.getOutputStream().write(b, 0, len);
//        }
//    }
//
//    /**
//     * 删除部署的流程，级联删除流程实例
//     *
//     * @param deploymentId 流程部署ID
//     */
//    @RequestMapping(value = "/process/delete")
//    public String delete(@RequestParam("deploymentId") String deploymentId) {
//        repositoryService.deleteDeployment(deploymentId, true);
//        return "redirect:/workflow/process-list";
//    }
//
//    /**
//     * 输出跟踪流程信息
//     *
//     * @param processInstanceId
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/process/trace")
//    @ResponseBody
//    public List<Map<String, Object>> traceProcess(@RequestParam("pid") String processInstanceId) throws Exception {
//        List<Map<String, Object>> activityInfos = traceService.traceProcess(processInstanceId);
//        return activityInfos;
//    }
//
//    /**
//     * 读取带跟踪的图片
//     */
//    @RequestMapping(value = "/process/trace/auto/{executionId}")
//    public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response)
//            throws Exception {
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
//        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
//        List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
//        // 不使用spring请使用下面的两行代码
////    ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
////    Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());
//
//        // 使用spring注入引擎请使用下面的这行代码
//        Context.setProcessEngineConfiguration(processEngine.getProcessEngineConfiguration());
//
//        InputStream imageStream = ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
//
//        // 输出资源内容到相应对象
//        byte[] b = new byte[1024];
//        int len;
//        while ((len = imageStream.read(b, 0, 1024)) != -1) {
//            response.getOutputStream().write(b, 0, len);
//        }
//    }
//
//    @RequestMapping(value = "/deploy")
//    public String deploy(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir, @RequestParam(value = "file", required = false) MultipartFile file) {
//
//        String fileName = file.getOriginalFilename();
//
//        try {
//            InputStream fileInputStream = file.getInputStream();
//            Deployment deployment = null;
//
//            String extension = FilenameUtils.getExtension(fileName);
//            if (extension.equals("zip") || extension.equals("bar")) {
//                ZipInputStream zip = new ZipInputStream(fileInputStream);
//                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
//            } else {
//                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
//            }
//
//            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
//
//            for (ProcessDefinition processDefinition : list) {
//                WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir);
//            }
//
//        } catch (Exception e) {
//            logger.error("error on deploy process, because of file input stream", e);
//        }
//
//        return "redirect:/workflow/process-list";
//    }
//
//    @RequestMapping(value = "/process/convert-to-model/{processDefinitionId}")
//    public String convertToModel(@PathVariable("processDefinitionId") String processDefinitionId)
//            throws UnsupportedEncodingException, XMLStreamException {
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .processDefinitionId(processDefinitionId).singleResult();
//        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
//                processDefinition.getResourceName());
//        XMLInputFactory xif = XMLInputFactory.newInstance();
//        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
//        XMLStreamReader xtr = xif.createXMLStreamReader(in);
//        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);
//
//        BpmnJsonConverter converter = new BpmnJsonConverter();
//        ObjectNode modelNode = converter.convertToJson(bpmnModel);
//        Model modelData = repositoryService.newModel();
//        modelData.setKey(processDefinition.getKey());
//        modelData.setName(processDefinition.getResourceName());
//        modelData.setCategory(processDefinition.getDeploymentId());
//
//        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
//        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
//        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
//        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
//        modelData.setMetaInfo(modelObjectNode.toString());
//
//        repositoryService.saveModel(modelData);
//
//        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
//
//        return "redirect:/workflow/model/list";
//    }
//
		
	    /**
	     * 待办任务--Portlet
	     */
	    //@RequestMapping(value = "/task/todo/list")
	   // @ResponseBody
	    public String todoList(){
	    	
	    	HttpServletRequest request=ServletActionContext.getRequest();
	    	HttpSession session =request.getSession();
	    	
	        User user = UserUtil.getUserFromSession(session);
	        todoList = new ArrayList<Map<String, Object>>();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	        // 已经签收的任务
	        List<Task> t_todoList = taskService.createTaskQuery().taskAssignee(user.getId()).active().list();
	        for (Task task : t_todoList) {
	            String processDefinitionId = task.getProcessDefinitionId();
	            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
	
	            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
	            singleTask.put("status", "todo");
	            todoList.add(singleTask);
	        } 
	 
	        // 等待签收的任务
	        List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(user.getId()).active().list();
	        for (Task task : toClaimList) {
	            String processDefinitionId = task.getProcessDefinitionId();
	            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
	
	            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
	            singleTask.put("status", "claim");
	            todoList.add(singleTask);
	        }
	
	        return "json";
	    }
//    /**
//     * 待办任务--Portlet
//     */
//    @RequestMapping(value = "/task/todo/list")
//    @ResponseBody
//    public List<Map<String, Object>> todoList(HttpSession session) throws Exception {
//        User user = UserUtil.getUserFromSession(session);
//        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//
//        // 已经签收的任务
//        List<Task> todoList = taskService.createTaskQuery().taskAssignee(user.getId()).active().list();
//        for (Task task : todoList) {
//            String processDefinitionId = task.getProcessDefinitionId();
//            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
//
//            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
//            singleTask.put("status", "todo");
//            result.add(singleTask);
//        }
//
//        // 等待签收的任务
//        List<Task> toClaimList = taskService.createTaskQuery().taskCandidateUser(user.getId()).active().list();
//        for (Task task : toClaimList) {
//            String processDefinitionId = task.getProcessDefinitionId();
//            ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
//
//            Map<String, Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
//            singleTask.put("status", "claim");
//            result.add(singleTask);
//        }
//
//        return result;
//    }
//
    private Map<String, Object> packageTaskInfo(SimpleDateFormat sdf, Task task, ProcessDefinition processDefinition) {
        Map<String, Object> singleTask = new HashMap<String, Object>();
        singleTask.put("id", task.getId());
        singleTask.put("name", task.getName());
        singleTask.put("createTime", sdf.format(task.getCreateTime()));
        singleTask.put("pdname", processDefinition.getName());
        singleTask.put("pdversion", processDefinition.getVersion());
        singleTask.put("pid", task.getProcessInstanceId());
        return singleTask;
    }

    private ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
        if (processDefinition == null) {
            processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
        }
        return processDefinition;
    }
//
//    /**
//     * 挂起、激活流程实例
//     */
//    @RequestMapping(value = "processdefinition/update/{state}/{processDefinitionId}")
//    public String updateState(@PathVariable("state") String state, @PathVariable("processDefinitionId") String processDefinitionId,
//                              RedirectAttributes redirectAttributes) {
//        if (state.equals("active")) {
//            redirectAttributes.addFlashAttribute("message", "已激活ID为[" + processDefinitionId + "]的流程定义。");
//            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
//        } else if (state.equals("suspend")) {
//            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
//            redirectAttributes.addFlashAttribute("message", "已挂起ID为[" + processDefinitionId + "]的流程定义。");
//        }
//        return "redirect:/workflow/process-list";
//    }
//
//    /**
//     * 导出图片文件到硬盘
//     *
//     * @return
//     */
//    @RequestMapping(value = "export/diagrams")
//    @ResponseBody
//    public List<String> exportDiagrams(@Value("#{APP_PROPERTIES['export.diagram.path']}") String exportDir) throws IOException {
//        List<String> files = new ArrayList<String>();
//        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
//
//        for (ProcessDefinition processDefinition : list) {
//            files.add(WorkflowUtils.exportDiagramToFile(repositoryService, processDefinition, exportDir));
//        }
//
//        return files;
//    }

    public void setWorkflowProcessDefinitionService(WorkflowProcessDefinitionService workflowProcessDefinitionService) {
        this.workflowProcessDefinitionService = workflowProcessDefinitionService;
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

   public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

   public void setTraceService(WorkflowTraceService traceService) {
        this.traceService = traceService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }




	public void setFile(File file) {
		this.file = file;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setType(String type) {
		this.type = type;
	}
	public List<Map<String, Object>> getActivityInfos() {
		return activityInfos;
	}
	public List<Map<String, Object>> getTodoList() {
		return todoList;
	}
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}



}