package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.TaskDepart;

public interface TaskDepartService extends IService<TaskDepart>{

	Result<TaskDepart> relatedDepartments(Long id, Long orgId);

}
