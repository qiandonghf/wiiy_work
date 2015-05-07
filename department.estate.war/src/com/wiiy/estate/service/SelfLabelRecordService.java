package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.SelfLabelRecord;
import com.wiiy.hibernate.Result;

public interface SelfLabelRecordService extends IService<SelfLabelRecord>{
	Result addMeter(Long labelId, List<String> existList, List<String> newList);
}
