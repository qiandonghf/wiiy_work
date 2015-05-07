package com.wiiy.estate.service;

import java.util.List;

import com.wiiy.commons.service.IService;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyPurchaseConfig;
import com.wiiy.estate.entity.SupplyPurchaseForm;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public interface SupplyPurchaseConfigService extends IService<SupplyPurchaseConfig> {

	Result save(SupplyPurchaseForm supplyPurchaseForm,
			SupplyPurchase supplyPurchase,
			SupplyPurchaseConfig supplyPurchaseConfig,
			String[] supplyIds,String[] amounts, String[] prices, String[] usages);

	Result update(List<SupplyPurchaseConfig> configList,
			List<SupplyPurchase> attList, SupplyPurchaseForm supplyForm);

	List<Object> getListBySql(String sql);

}
