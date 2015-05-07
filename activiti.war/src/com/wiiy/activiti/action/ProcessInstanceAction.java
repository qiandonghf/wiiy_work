package com.wiiy.activiti.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import me.kafeitu.demo.activiti.util.Page;
import me.kafeitu.demo.activiti.util.PageUtil;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.struts2.ServletActionContext;

//@Controller
//@RequestMapping(value = "/workflow/processinstance")
public class ProcessInstanceAction {


    private RuntimeService runtimeService;

    //@RequestMapping(value = "running")
    public String running() {
    	
    	HttpServletRequest request=ServletActionContext.getRequest();
        //ModelAndView mav = new ModelAndView("/workflow/running-manage");
        Page<ProcessInstance> page = new Page<ProcessInstance>(PageUtil.PAGE_SIZE);
        int[] pageParams = PageUtil.init(page, request);

        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> list = processInstanceQuery.listPage(pageParams[0], pageParams[1]);
        page.setResult(list);
        page.setTotalCount(processInstanceQuery.count());
        //mav.addObject("page", page);
        request.setAttribute("page", page);
        return "running-manage";
    }

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

//    /**
//     * 挂起、激活流程实例
//     */
//    @RequestMapping(value = "update/{state}/{processInstanceId}")
//    public String updateState(@PathVariable("state") String state, @PathVariable("processInstanceId") String processInstanceId,
//                              RedirectAttributes redirectAttributes) {
//        if (state.equals("active")) {
//            redirectAttributes.addFlashAttribute("message", "已激活ID为[" + processInstanceId + "]的流程实例。");
//            runtimeService.activateProcessInstanceById(processInstanceId);
//        } else if (state.equals("suspend")) {
//            runtimeService.suspendProcessInstanceById(processInstanceId);
//            redirectAttributes.addFlashAttribute("message", "已挂起ID为[" + processInstanceId + "]的流程实例。");
//        }
//        return "redirect:/workflow/processinstance/running";
//    }
}
