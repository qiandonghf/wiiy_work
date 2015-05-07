package com.wiiy.synthesis.service;

import com.wiiy.commons.service.IService;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.entity.CarApply;

/**
 * @author my
 */
public interface CarApplyService extends IService<CarApply> {

	Result approve(Long id, Long approverId, String approverl);

	Result approveCarApply(Long id, String applyCheck);
}
