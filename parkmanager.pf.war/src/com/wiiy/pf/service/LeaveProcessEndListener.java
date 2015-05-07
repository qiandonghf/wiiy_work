package com.wiiy.pf.service;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wiiy.pf.dao.ActivitiDao;

/**
 * 请假流程结束监听器
 *
 * @author: aswan
 */

public class LeaveProcessEndListener implements ExecutionListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    ActivitiDao activitiDao;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();

        int i = activitiDao.deleteFormPropertyByProcessInstanceId(processInstanceId);
        System.out.println("LeaveProcessEndListener.notify():清理了 "+i+" 条历史表单数据");
        logger.debug("清理了 {} 条历史表单数据", i);
    }

	public void setActivitiDao(ActivitiDao activitiDao) {
		this.activitiDao = activitiDao;
	}
}
