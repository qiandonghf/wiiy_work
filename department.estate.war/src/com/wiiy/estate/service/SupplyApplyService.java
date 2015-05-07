package com.wiiy.estate.service;

import com.wiiy.commons.service.IService;

import com.wiiy.hibernate.Result;
import com.wiiy.estate.entity.SupplyApply;

public interface SupplyApplyService extends IService<SupplyApply> {

	Result<SupplyApply> approve(Long id, Long approverId, String approverl);

	Result<SupplyApply> approveApply(SupplyApply t, String applyCheck);

	Result updateSupply(SupplyApply supplyApply, Double count);

}
