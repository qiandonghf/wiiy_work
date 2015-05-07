package com.wiiy.business.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.DataProperty;
import com.wiiy.business.entity.DataTemplatePropertyConfig;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface DataTemplatePropertyConfigService extends IService<DataTemplatePropertyConfig> {

	Result<List<DataProperty>> getTemplatePropertys(Long templateId);
}
