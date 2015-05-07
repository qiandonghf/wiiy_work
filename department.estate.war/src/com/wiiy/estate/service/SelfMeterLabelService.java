package com.wiiy.estate.service;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.SelfMeterLabel;

public interface SelfMeterLabelService extends IService<SelfMeterLabel>{

	void updateRecord(Long id, String oldIds, String newIds);

}
