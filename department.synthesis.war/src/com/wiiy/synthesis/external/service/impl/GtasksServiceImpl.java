package com.wiiy.synthesis.external.service.impl;

import java.util.List;

import org.hibernate.Session;

import com.wiiy.external.service.GtasksService;
import com.wiiy.synthesis.dao.TaskDao;
import com.wiiy.synthesis.entity.Task;
import com.wiiy.synthesis.preferences.enums.SubTaskStatusEnum;

public class GtasksServiceImpl implements GtasksService{
	private  TaskDao taskDao;
	@Override
	public int gtasksCount(Long id) {
		Session session = null;
		session = taskDao.openSession();
		List<Task> taskList = session.createQuery("from Task where receiverId='"+id+"' and childStatus='"+SubTaskStatusEnum.SIGNED+"'").list();
		return taskList.size();
	}
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
}
