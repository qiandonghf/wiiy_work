package com.wiiy.estate.service;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.Meter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface MeterService extends IService<Meter> {
	Result updateMeter(Meter dbean, Boolean change);
}
