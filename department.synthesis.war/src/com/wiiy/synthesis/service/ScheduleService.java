package com.wiiy.synthesis.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.Schedule;

public interface ScheduleService extends IService<Schedule>{

	Result save(List<Schedule> insertList);

}
