package com.wiiy.estate.service;

import java.util.List;
import java.util.Map;

import com.wiiy.commons.service.IService;

import com.wiiy.hibernate.Result;
import com.wiiy.estate.entity.Supply;
import com.wiiy.estate.entity.SupplyPurchase;
import com.wiiy.estate.entity.SupplyStockIn;

public interface SupplyStockInService extends IService<SupplyStockIn> {

	Result updateSupply(SupplyStockIn supplyStockIn, Double count);

	Result save(SupplyStockIn supplyStockIn, List<Supply> supplyList, List<SupplyPurchase> supplyPurchaseList);

	Result deletes(SupplyStockIn supplyStockIn, Supply supply, List<SupplyPurchase> purchaseList,
			List<Supply> supplyList);

	Result save(List<Supply> supplyList,
			String[] supplyIds, String[] amounts, String[] prices,
			String[] usages);
}
