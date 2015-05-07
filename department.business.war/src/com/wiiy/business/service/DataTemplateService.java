package com.wiiy.business.service;

import com.wiiy.commons.service.IService;
import com.wiiy.business.entity.DataTemplate;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface DataTemplateService extends IService<DataTemplate> {

	/**
	 * 保存模板属性配置
	 * @param id
	 * @param propertyIds
	 * @return
	 */
	Result saveConfig(Long templateId, String propertyIds);

	/**
	 * 加载模板属性配置
	 * @param id
	 * @return
	 */
	Result loadConfig(Long templateId);
}
