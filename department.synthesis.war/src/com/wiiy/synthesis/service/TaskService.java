package com.wiiy.synthesis.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.dto.TaskDto;
import com.wiiy.synthesis.entity.Task;
import com.wiiy.synthesis.entity.TaskAtt;

/**
 * @author my
 */
public interface TaskService extends IService<Task> {

	Result<Task> save(Task t, List<TaskAtt> sessionTaskAttList, String[] departIds, String sendIds);

	Result<Task> update(Task task, String[] departIds);
	
	Result<Task> update(Task task, String[] departIds, String sendIds);

	Result setDepart(Long id, String ids);

	Result taskSend(Long id, String ids);
	
	Result taskMove(Long id,Long moveId);

	Result save(List<Task> insertList);

	Result sqlQuery(String sql);
	
	Result<Integer> getRowCount(Filter filter);
	
	Result<List<TaskDto>> getPendingTask();
}
